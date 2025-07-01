package org.example.expert.domain.log.entity;

import jakarta.persistence.*;
import lombok.*;
import org.example.expert.domain.log.LogAction;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "log")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
@Builder
@AllArgsConstructor
public class Log {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private LogAction action;

    @Column(nullable = false)
    private Long requesterId;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

}
