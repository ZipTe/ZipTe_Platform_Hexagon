package com.zipte.platform.server.adapter.out.kafka.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentEvent {

    /*
        Kafka로 받을 event 객체
     */

    private EventType type;
    private Long postId;
    private Long postOwnerId;
    private Long writerId;
    private Long commentId;
    private String comment;
    private LocalDateTime occurredAt;

    //echo '{"type":"ADD","postId":1,"postOwnerId":1,"writerId":1,"commentId":1,"comment":"hello","occurredAt":"2025-02-10T23:50:00Z"}'| kafka-console-producer --broker-list localhost:9092 --topic comment
}
