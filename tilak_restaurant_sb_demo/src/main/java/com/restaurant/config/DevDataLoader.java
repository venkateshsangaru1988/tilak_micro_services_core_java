package com.restaurant.config;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("dev")
public class DevDataLoader {

    public DevDataLoader() {
        System.out.println("DEV Data Loader started @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ ");
    }
}
