package com.restaurant.scheduler;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.restaurant.constants.ApplicationUtilsConstants;
import com.restaurant.entity.Restaurant;
import com.restaurant.repository.RestaurantRepository;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class RestaurantScheduler {

	private final SchedulerOperation schedulerOperation;

	private final RestaurantRepository restaurantRepository;

	RestaurantScheduler(RestaurantRepository restaurantRepository, SchedulerOperation schedulerOperation) {
		this.restaurantRepository = restaurantRepository;
		this.schedulerOperation = schedulerOperation;
	}

	@Scheduled(cron = "${restaurant.count.every.one.min.scheduler.cron}")
	public void getRestuarantCounts() {

		if (ApplicationUtilsConstants.TRUE_CONDITION.equalsIgnoreCase(schedulerOperation.schedulerIsActive())) {
			List<Restaurant> restaurantCountList = restaurantRepository.findAll();

			log.info("----------Count is {} and time is {}----------", restaurantCountList.size(), LocalDateTime.now());
		}

	}

	@Scheduled(cron = "${restaurant.count.every.two.mins.scheduler.cron}")
	public void getRestuarantCountsEveryThreeMins() {
		if (ApplicationUtilsConstants.TRUE_CONDITION.equalsIgnoreCase(schedulerOperation.schedulerIsActive())) {

			List<Restaurant> restaurantCountList = restaurantRepository.findAll();

			log.info("----------getRestuarantCountsEveryThreeMins method Count is {} and time is {}----------",
					restaurantCountList.size(), LocalDateTime.now());
		}

	}

}
