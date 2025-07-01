package org.example.expert.domain.log.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.expert.domain.log.LogAction;
import org.example.expert.domain.log.entity.Log;
import org.example.expert.domain.log.repository.LogRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class LogService {

    private final LogRepository logRepository;

    public void saveLog(LogAction action, Long requesterId) {
        log.info("action: {}, requesterId: {}", action.name(), requesterId);

        Log log = Log.builder()
                .action(action)
                .requesterId(requesterId)
                .build();

        logRepository.save(log);
    }
}

