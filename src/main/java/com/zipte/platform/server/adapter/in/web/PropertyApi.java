package com.zipte.platform.server.adapter.in.web;

import com.zipte.platform.core.response.ApiResponse;
import com.zipte.platform.core.response.pageable.PageRequest;
import com.zipte.platform.core.response.pageable.PageResponse;
import com.zipte.platform.server.adapter.in.web.dto.response.PropertyDetailResponse;
import com.zipte.platform.server.adapter.in.web.dto.response.PropertyListResponse;
import com.zipte.platform.server.adapter.in.web.dto.request.PropertyRequest;
import com.zipte.platform.server.application.in.property.*;
import com.zipte.platform.server.domain.property.Property;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/property")
public class PropertyApi {

    private final CreatePropertyUseCase createService;
    private final ListPropertiesUseCase listService;
    private final GetPropertyDetailsUseCase getService;
    private final UpdatePropertyUseCase updateService;
    private final DeletePropertyUseCase deleteService;

    // 매물 생성
    @PostMapping
    public ApiResponse<PropertyDetailResponse> create(@Valid @RequestBody PropertyRequest request) {

        Property property = createService.create(request);

        return ApiResponse.created(PropertyDetailResponse.from(property));
    }

    // 최신 매물 목록 조회
    @GetMapping("/list")
    public ApiResponse<PageResponse<PropertyListResponse>> listProperty(PageRequest pageRequest) {
        Pageable pageable = org.springframework.data.domain.PageRequest.of(
                pageRequest.getPage() - 1,
                pageRequest.getSize(),
                Sort.by(Sort.Direction.DESC, "id")
        );

        Page<Property> result = listService.getList(pageable);
        List<PropertyListResponse> dtoList = PropertyListResponse.from(result.getContent());

        return ApiResponse.ok(new PageResponse<>(dtoList, pageRequest, result.getTotalElements()));
    }

    // 조회수 높은 매물 목록 조회
    @GetMapping("/list/view")
    public ApiResponse<PageResponse<PropertyListResponse>> listPropertyByView(PageRequest pageRequest) {
        Pageable pageable = org.springframework.data.domain.PageRequest.of(
                pageRequest.getPage() - 1,
                pageRequest.getSize(),
                Sort.by(Sort.Direction.DESC, "statistic.viewCount") // 조회수 기준 정렬
        );

        Page<Property> result = listService.getListByViewCount(pageable);
        List<PropertyListResponse> dtoList = PropertyListResponse.from(result.getContent());

        return ApiResponse.ok(new PageResponse<>(dtoList, pageRequest, result.getTotalElements()));
    }

    // 좋아요 많은 매물 목록 조회
    @GetMapping("/list/like")
    public ApiResponse<PageResponse<PropertyListResponse>> listPropertyByLike(PageRequest pageRequest) {
        Pageable pageable = org.springframework.data.domain.PageRequest.of(
                pageRequest.getPage() - 1,
                pageRequest.getSize(),
                Sort.by(Sort.Direction.DESC, "statistic.likeCount") // 좋아요 기준 정렬
        );

        Page<Property> result = listService.getListByLikeCount(pageable);
        List<PropertyListResponse> dtoList = PropertyListResponse.from(result.getContent());

        return ApiResponse.ok(new PageResponse<>(dtoList, pageRequest, result.getTotalElements()));
    }

    // 매물 상세 조회
    @GetMapping("/{id}")
    public ApiResponse<PropertyDetailResponse> getDetailOne(@PathVariable Long id) {
        Property property = getService.getPropertyById(id);

        return ApiResponse.ok(PropertyDetailResponse.from(property));
    }

    // 매물 삭제
    @DeleteMapping("/{id}")
    public ApiResponse<String> delete(@PathVariable Long id) {
        deleteService.deleteProperty(id);
        return ApiResponse.ok("매물(ID: " + id + ")이 성공적으로 삭제되었습니다.");
    }

}
