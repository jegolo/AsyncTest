package de.lostuxos.labs.AsyncTest;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;

import java.util.concurrent.Future;

import static java.lang.Thread.*;

@Slf4j
@Component
public class Calculator {

    @SneakyThrows
    @Async
    public Future<String> calculate(String value) {
        log.info("Start");
        sleep(15000);
        log.info("Finish");
        return new AsyncResult<String>(value);
    }
}
