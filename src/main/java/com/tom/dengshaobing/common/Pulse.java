package com.tom.dengshaobing.common;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class Pulse {
	@Scheduled(fixedRate = 5000)
	public void printPulse() {
		System.out.println("*********************************************");
	}
}
