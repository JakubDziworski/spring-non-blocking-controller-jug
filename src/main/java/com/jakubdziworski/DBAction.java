package com.jakubdziworski;

import lombok.EqualsAndHashCode;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.stream.Collectors;

import static com.sun.tools.javac.jvm.ByteCodes.ret;

/**
 * Created by 'Jakub Dziworski' on 19.10.16
 */
@EqualsAndHashCode
class DBAction {
    private final List<String> queries;

    public DBAction(String query) {
        queries = Collections.singletonList(query);
    }

    public DBAction(List<String> queries) {
        this.queries = Collections.unmodifiableList(queries);
    }

    public static DBAction empty() {
        return new DBAction(Collections.emptyList());
    }

    public DBAction combine(DBAction dbAction) {
        ArrayList<String> mergedQueries = new ArrayList<>(dbAction.queries);
        mergedQueries.addAll(queries);
        return new DBAction(mergedQueries);
    }

    public CompletableFuture<String> executeBatched(Executor executor) {
        String batchedQueries = queries.stream().distinct().collect(Collectors.joining(",","optimized batched queries [","]!"));
        return CompletableFuture.supplyAsync(() -> {
            sleep(batchedQueries.length()*20); //simulate actual query being performed
            return "Executed " + batchedQueries;
        },executor);
    }

    private void sleep(int duration) {
        try {
            Thread.sleep(duration);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
