package org.example.expert.domain.todo.dto.request;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class SearchTodoCondition {

    private String title;

    private LocalDateTime startCreatedDatetime;

    private LocalDateTime endCreatedDatetime;

    private String nickname;

}
