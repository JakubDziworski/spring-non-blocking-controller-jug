package com.jakubdziworski;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean("databaseExecutor")
	public Executor databaseExecutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(500);
		executor.setThreadNamePrefix("DatabaseQuery-");
		executor.initialize();
		return executor;
	}

	@Bean("simpleAsyncTaskExecutor")
	public Executor defaultExecutor() {
		SimpleAsyncTaskExecutor simpleAsyncTaskExecutor = new SimpleAsyncTaskExecutor();
		simpleAsyncTaskExecutor.setThreadGroupName("SimpleAsyncTaskExecutor-");
		return simpleAsyncTaskExecutor;
	}


	@Bean("elasticSearchExecutor")
	public Executor elastiSearchExecutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		//....
		return executor;
	}



	//..some other executors


}
