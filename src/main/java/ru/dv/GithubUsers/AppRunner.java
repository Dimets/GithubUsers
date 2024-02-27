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

        // Wait until they are all done
        CompletableFuture.allOf(page1,page2,page3).join();

        // Print results, including elapsed time
        log.info("Elapsed time: {}", (System.currentTimeMillis() - start));
        log.info("--> {}", page1.get());
        log.info("--> {}", page2.get());
        log.info("--> {}", page3.get());
    }
}
