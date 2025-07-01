package org.example.expert.domain.todo.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.example.expert.domain.todo.dto.request.SearchTodoCondition;
import org.example.expert.domain.todo.dto.response.QSearchTodoResponse;
import org.example.expert.domain.todo.dto.response.SearchTodoResponse;
import org.example.expert.domain.todo.entity.Todo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;

import static org.example.expert.domain.comment.entity.QComment.comment;
import static org.example.expert.domain.manager.entity.QManager.manager;
import static org.example.expert.domain.todo.entity.QTodo.todo;
import static org.example.expert.domain.user.entity.QUser.user;

@Repository
@RequiredArgsConstructor
public class QTodoRepositoryImpl implements QTodoRepository {

    private final JPAQueryFactory query;

    @Override
    public Optional<Todo> findByIdWithUser(Long todoId) {
        return Optional.ofNullable(query.selectFrom(todo)
                .join(todo.user, user).fetchJoin()
                .where(todo.id.eq(todoId))
                .fetchOne());
    }

    @Override
    public Page<SearchTodoResponse> findByCondition(SearchTodoCondition cond, Pageable pageable) {

        BooleanBuilder condition = createCondition(cond);

        List<SearchTodoResponse> todos = query.select(
                        new QSearchTodoResponse(
                                todo.title,
                                manager.countDistinct().as("managerCount"),
                                comment.countDistinct().as("commentCount")
                        )
                )
                .from(todo)
                .leftJoin(todo.managers, manager)
                .leftJoin(todo.comments, comment)
                .where(condition)
                .groupBy(todo.id)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        return new PageImpl<>(todos, pageable, todos.size());
    }

    private BooleanBuilder createCondition(SearchTodoCondition condition) {
        BooleanBuilder booleanBuilder = new BooleanBuilder();

        if (StringUtils.hasText(condition.getTitle())) {
            booleanBuilder.and(todo.title.containsIgnoreCase(condition.getTitle()));
        }

        if (condition.getStartCreatedDatetime() != null) {
            booleanBuilder.and(todo.createdAt.goe(condition.getStartCreatedDatetime()));
        }

        if (condition.getEndCreatedDatetime() != null) {
            booleanBuilder.and(todo.createdAt.loe(condition.getEndCreatedDatetime()));
        }

        if (StringUtils.hasText(condition.getNickname())) {
            booleanBuilder.and(todo.user.nickname.containsIgnoreCase(condition.getNickname()));
        }

        return booleanBuilder;
    }

}
