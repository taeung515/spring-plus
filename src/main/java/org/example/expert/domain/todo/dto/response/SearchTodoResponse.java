package org.example.expert.domain.todo.dto.response;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Getter;

@Getter
public class SearchTodoResponse {

    private final String title;
    private final Long managersCount;
    private final Long commentsCount;

    @Builder
    @QueryProjection
    public SearchTodoResponse(String title, Long managersCount, Long commentsCount) {
        this.title = title;
        this.managersCount = managersCount;
        this.commentsCount = commentsCount;
    }
}