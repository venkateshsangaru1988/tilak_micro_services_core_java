package com.restaurant.scheduler;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.restaurant.constants.ApplicationUtilsConstants;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class TilakScheduler {

	private final SchedulerOperation schedulerOperation;

	TilakScheduler(SchedulerOperation schedulerOperation) {
		this.schedulerOperation = schedulerOperation;
	}

	@Scheduled(fixedRate = 10000)
	public void firstSchedular() {
		if (ApplicationUtilsConstants.TRUE_CONDITION.equalsIgnoreCase(schedulerOperation.schedulerIsActive())) {
			log.info("******************** First Schedular executed ***************");
		}
	}

	@Scheduled(fixedDelay = 5000)
	public void firstSchedularFixedDelay() {
		if (ApplicationUtilsConstants.TRUE_CONDITION.equalsIgnoreCase(schedulerOperation.schedulerIsActive())) {
			log.info("******************** First Schedular Fixed Delay executed @@@@ ***************");
		}
	}

	@Scheduled(fixedRate = 20000)
	public void secondSchedular() {
		if (ApplicationUtilsConstants.TRUE_CONDITION.equalsIgnoreCase(schedulerOperation.schedulerIsActive())) {
			log.info("******************** Second Schedular executed ***************");
		}
	}

	@Scheduled(fixedDelay = 8000)
	public void secondSchedularFixedDelay() {
		if (ApplicationUtilsConstants.TRUE_CONDITION.equalsIgnoreCase(schedulerOperation.schedulerIsActive())) {
			log.info("******************** Second Schedular Fixed Delay executed @@@@ ***************");
		}
	}

}
