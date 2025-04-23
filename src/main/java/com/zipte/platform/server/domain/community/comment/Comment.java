package com.zipte.platform.server.domain.community.comment;

import com.zipte.platform.server.domain.BaseDomain;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Comment extends BaseDomain {

    private Long postId;

    private Long postOwnerId;

    private Long writerId;

    private Long commentId;

    private String comment;

    private LocalDateTime occurredAt;

}
