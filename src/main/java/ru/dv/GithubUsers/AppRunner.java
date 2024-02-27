package ru.dv.GithubUsers;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Component
@Slf4j
@AllArgsConstructor
public class AppRunner implements CommandLineRunner {
    private final UserService userService;

    @Override
    public void run(String... args) throws Exception {
        // Start the clock
        long start = System.currentTimeMillis();

        // Kick of multiple, asynchronous lookups
        CompletableFuture<User> page1 = userService.findUser("PivotalSoftware");
        CompletableFuture<User> page2 = userService.findUser("CloudFoundry");
        CompletableFuture<User> page3 = userService.findUser("Spring-Projects");
        CompletableFuture<User> page4 = userService.findUser("Dimets");

        // Wait until they are all done
        CompletableFuture.allOf(page1,page2,page3,page4).join();

        // Print results, including elapsed time
        log.info("Async Elapsed time: {}", (System.currentTimeMillis() - start));
        log.info("--> {}", page1.get());
        log.info("--> {}", page2.get());
        log.info("--> {}", page3.get());

        start = System.currentTimeMillis();
        User user1 = userService.findUserOne("PivotalSoftware");
        User user2 = userService.findUserOne("CloudFoundry");
        User user3 = userService.findUserOne("Spring-Projects");
        User user4 = userService.findUserOne("Dimets");
        log.info("Simple Elapsed time: {}", (System.currentTimeMillis() - start));
        log.info("--> {}", user1);
        log.info("--> {}", user2);
        log.info("--> {}", user3);
        log.info("--> {}", user4);

    }
}
