package com.jakubdziworski;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.WebAsyncTask;

import java.util.concurrent.Callable;

/**
 * Created by 'Jakub Dziworski' on 19.10.16
 */
@RestController
public class CallableController {

    @RequestMapping("/async")
    public WebAsyncTask<String> asyncEndpoint() {
        Callable<String> callable = () -> getExpensiveInfoFromDB();
        return new WebAsyncTask<>(15000L,"databaseExecutor",callable);
    }

    @RequestMapping("/sync")
    public String syncEndpoint() throws InterruptedException {
        return getExpensiveInfoFromDB();
    }

    private String getExpensiveInfoFromDB() throws InterruptedException {
        Thread.sleep(8000);
        return "Important stuff!";
    }
}
