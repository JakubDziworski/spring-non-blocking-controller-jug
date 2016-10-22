package com.jakubdziworski;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.stream.Stream;

/**
 * Created by 'Jakub Dziworski' on 19.10.16

 * This endpoint gathers all database calls descriptions
 * and execute them at the end optimizing it by
 * reducing duplicated queries and batching it into one call.
 *
 * It also does not block tomcat thread and dispatches the actual query
 * to other executor
 */
@RestController
public class FutureQueriesBatchingController {


    private final Executor dbExecutor;

    @Autowired
    public FutureQueriesBatchingController(@Qualifier("databaseExecutor") Executor dbExecutor) {
        this.dbExecutor = dbExecutor;
    }

    @RequestMapping("/futureCombinedQueries")
    public CompletableFuture<String> asyncEndpoint() {
        DBAction firstDbCall = getVeryImportantStuffFromDB();
        DBAction duplicatedDBCall = getVeryImportantStuffFromDB();
        DBAction thirdDbCall = getLessImportantStuffFromDB();
        DBAction otherStuffDbCall = getOtherStuffFromDB();
        return Stream.of(firstDbCall, duplicatedDBCall, thirdDbCall, otherStuffDbCall)
                .reduce((dbCall1, dbCall2) -> dbCall1.combine(dbCall2))
                .map(d -> d.executeBatched(dbExecutor))
                .orElse(CompletableFuture.completedFuture("No queries"));
    }

    private DBAction getLessImportantStuffFromDB() {
        return new DBAction("select * from less_important_stuff");
    }

    private DBAction getOtherStuffFromDB() {
        return new DBAction("select * from other_stuff");
    }

    private DBAction getVeryImportantStuffFromDB() {
        return new DBAction("select * from important_stuff");
    }
}
