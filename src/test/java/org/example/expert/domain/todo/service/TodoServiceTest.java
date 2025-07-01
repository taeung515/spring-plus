package org.example.expert.domain.todo.service;

import org.example.expert.domain.todo.dto.request.SearchTodoCondition;
import org.example.expert.domain.todo.dto.response.SearchTodoResponse;
import org.example.expert.domain.todo.repository.TodoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class TodoServiceTest {

    @InjectMocks
    TodoService todoService;
    @Mock
    TodoRepository todoRepository;

    @Test
    void 페이지_TODO_조건_검색() {
        // given
        PageRequest pageRequest = PageRequest.of(0, 10, Sort.Direction.DESC, "createdAt");
        SearchTodoCondition cond = createTodoCondition();
        Page<SearchTodoResponse> content = createSearchTodoResponse(10, pageRequest);
        given(todoRepository.findByCondition(any(), any())).willReturn(content);

        // when
        Page<SearchTodoResponse> content2 = todoService.searchTodo(cond, pageRequest);

        //then
        assertEquals(content.getContent(), content2.getContent());
        assertEquals(content.getTotalElements(), content2.getTotalElements());
    }

    private Page<SearchTodoResponse> createSearchTodoResponse(int i, PageRequest pageRequest) {

        String title = "testTitle";

        List<SearchTodoResponse> todos = new ArrayList<>();
        for (int k = 0; k < i; k++) {
            SearchTodoResponse searchTodoResponse = SearchTodoResponse.builder()
                    .title(title + k)
                    .managersCount(1L)
                    .commentsCount(5L)
                    .build();
            todos.add(searchTodoResponse);
        }

        return new PageImpl<>(todos, pageRequest, todos.size());
    }

    private SearchTodoCondition createTodoCondition() {
        return SearchTodoCondition.builder()
                .title("testTitle")
                .startCreatedDatetime(LocalDateTime.now().minusDays(1))
                .endCreatedDatetime(LocalDateTime.now())
                .nickname("testNickname").build();
    }
}