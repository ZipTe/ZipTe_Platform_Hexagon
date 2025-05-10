package com.zipte.platform.core.util;

import com.zipte.platform.server.domain.property.PropertyAddress;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
    GPT 코드
 */
public class AddressParser {

    public static PropertyAddress parseAddress(String streetAddress) {
        String city = null, district = null, dong = null;

        // 1. 시, 구/군/시, 동이 모두 있는 경우
        Pattern fullPattern = Pattern.compile("(\\S+?시)\\s(\\S+?(구|군|시))\\s(\\S+?동)");
        Matcher matcher = fullPattern.matcher(streetAddress);
        if (matcher.find()) {
            city = matcher.group(1);
            district = matcher.group(2);
            dong = matcher.group(4);
        } else {
            // 2. 시, 구/군/시만 있는 경우
            Pattern cityDistrictPattern = Pattern.compile("(\\S+?시)\\s(\\S+?(구|군|시))");
            matcher = cityDistrictPattern.matcher(streetAddress);
            if (matcher.find()) {
                city = matcher.group(1);
                district = matcher.group(2);
            } else {
                // 3. 시, 동만 있는 경우
                Pattern cityDongPattern = Pattern.compile("(\\S+?시)\\s(\\S+?동)");
                matcher = cityDongPattern.matcher(streetAddress);
                if (matcher.find()) {
                    city = matcher.group(1);
                    dong = matcher.group(2);
                } else {
                    // 4. 구/군/시, 동만 있는 경우
                    Pattern districtDongPattern = Pattern.compile("(\\S+?(구|군|시))\\s(\\S+?동)");
                    matcher = districtDongPattern.matcher(streetAddress);
                    if (matcher.find()) {
                        district = matcher.group(1);
                        dong = matcher.group(3);
                    } else {
                        // 5. 시만 있는 경우
                        Pattern onlyCityPattern = Pattern.compile("(\\S+?시)");
                        matcher = onlyCityPattern.matcher(streetAddress);
                        if (matcher.find()) {
                            city = matcher.group(1);
                        }
                    }
                }
            }
        }

        return PropertyAddress.builder()
                .city(city)
                .district(district)
                .dong(dong)
                .build();
    }
}
