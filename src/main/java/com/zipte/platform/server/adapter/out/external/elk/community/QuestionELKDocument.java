package com.zipte.platform.server.adapter.out.external.elk.community;

import com.zipte.platform.server.domain.community.Question;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.*;

import java.time.LocalDateTime;

@Getter
@Document(indexName = "question", createIndex = true)
@Setting(settingPath = "elasticsearch/question-setting.json")
@Mapping(mappingPath = "elasticsearch/question-mapping.json")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class QuestionELKDocument {

    @Id
    @Field(type = FieldType.Long)
    private Long id;

    @Field(type = FieldType.Long)
    private Long userId;

    @Field(type = FieldType.Text)
    private String kaptCode;

    @Field(type = FieldType.Text)
    private String title;

    @Field(type = FieldType.Text)
    private String content;

    @Field(type = FieldType.Date, format = {DateFormat.date_hour_minute_second_millis, DateFormat.epoch_millis})
    private LocalDateTime createdAt;

    @Field(type = FieldType.Date, format = {DateFormat.date_hour_minute_second_millis, DateFormat.epoch_millis})
    private LocalDateTime updatedAt;

    @Builder
    public QuestionELKDocument(Long id, Long userId, String kaptCode, String title, String content, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.userId = userId;
        this.kaptCode = kaptCode;
        this.title = title;
        this.content = content;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    /// 정적 팩토리 메서드
    public static QuestionELKDocument from(Question question) {
        return QuestionELKDocument.builder()
                .id(question.getId())
                .kaptCode(question.getKaptCode())
                .title(question.getTitle())
                .content(question.getContent())
                .createdAt(question.getCreatedAt())
                .updatedAt(question.getUpdatedAt())
                .build();
    }

    /// 도메인 수정
    public Question toDomain() {
        return Question.builder()
                .id(id)
                .kaptCode(kaptCode)
                .title(title)
                .content(content)
                .createdAt(createdAt)
                .updatedAt(updatedAt)
                .build();
    }

}
