package com.zipte.platform.server.application.in.task;


import com.zipte.platform.server.domain.property.Property;

public interface PropertyNotificationTask {

    // 매물 생성 이후, 알림 센터에 넣을 것
    void createNotification(Property property);

    // 매물 삭제 이후, 알림 센터에서 삭제할 것
    void removeNotification(Property property);
}
