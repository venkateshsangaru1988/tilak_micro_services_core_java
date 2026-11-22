package com.restaurant.scheduler;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SchedulerOperation {

	@Value("${scheduler.enabled}")
	private String schedulerOperation;

	public String schedulerIsActive() {
		return schedulerOperation;
	}

}
