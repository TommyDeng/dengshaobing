package com.tom.dengshaobing.common;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class Pulse {

	@Scheduled(fixedRate = 3600000)
	public void printPulse() {
		log.info("Server Pulse.");
	}
}
