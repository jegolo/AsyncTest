package de.lostuxos.labs.AsyncTest;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Future;

@Controller
public class AsyncController {

    private Map<String, Future<String>> asyncMap = new ConcurrentHashMap<>();

    @Autowired
    private Calculator calculator;

    @GetMapping(path="start")
    public @ResponseBody String startRequest(@RequestParam String value) {
        var uid = UUID.randomUUID().toString();
        asyncMap.put(uid, calculator.calculate(value));
        return uid;
    }

    @SneakyThrows
    @GetMapping(path="result")
    public  ResponseEntity<String> result(@RequestParam String id) {
        var resultValue = "Waiting";
        if (asyncMap.containsKey(id)) {
            var result = asyncMap.get(id);
            if (result.isDone()) {
                resultValue=result.get();
                asyncMap.remove(id);
            }
        } else {
            return new ResponseEntity<>("Not found", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(resultValue, HttpStatus.OK);
    }

}

