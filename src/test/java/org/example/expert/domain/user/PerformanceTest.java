package org.example.expert.domain.user;

import org.example.expert.domain.user.entity.User;
import org.example.expert.domain.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@ActiveProfiles("test")
class PerformanceTest {

    private static final int BATCH_SIZE = 1000;
    @Autowired
    private UserRepository userRepository;

    @Test
    void 백만유저_더미_생성시간_테스트() {
        long createUsersStart = System.currentTimeMillis();
        createDummyUsers(1_000_000);
        System.out.println("유저생성시간: " + (System.currentTimeMillis() - createUsersStart) + " ms");
    }

    @Test
    void 백만요청_닉네임_검색성능_테스트() {
        createDummyUsers(1_000_000);

        long queryStart = System.currentTimeMillis();
        userRepository.findUserByNickname("test 500000");
        System.out.println("소요시간: " + (System.currentTimeMillis() - queryStart) + " ms");
    }

    private void createDummyUsers(int totalCount) {
        List<User> users = new ArrayList<>();
        while (totalCount-- > 0) {
            User user = User.builder()
                    .nickname("test " + totalCount)
                    .build();
            users.add(user);
            if (totalCount % BATCH_SIZE == 0) {
                userRepository.saveAll(users);
                users.clear();
            }
        }
    }
}