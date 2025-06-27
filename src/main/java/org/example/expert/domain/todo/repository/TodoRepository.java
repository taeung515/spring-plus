package org.example.expert.domain.todo.repository;

import org.example.expert.domain.todo.entity.Todo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Optional;

public interface TodoRepository extends JpaRepository<Todo, Long>, QTodoRepository {

    @Query("SELECT t FROM Todo t " +
            "JOIN FETCH t.user u " +
            "WHERE (:weather IS NULL OR t.weather = :weather) " +
            "AND (:startModifiedDatetime IS NULL OR t.modifiedAt >= :startModifiedDatetime) " +
            "AND (:endModifiedDatetime IS NULL OR t.modifiedAt <= :endModifiedDatetime)"
    )
    Page<Todo> findTodosByWeatherAndModifiedAt(
            @Param("weather") String weather,
            @Param("startModifiedDatetime") LocalDateTime startModifiedDatetime,
            @Param("endModifiedDatetime") LocalDateTime endModifiedDatetime,
            Pageable pageable
    );
}
