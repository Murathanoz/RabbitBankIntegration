package com.example.demo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitProperty {
	   @Value("${spring.rabbitmq.exchange}")
	   public String exchange;
	   
	   @Value("${sr.rabbit.first.queue.name}")
	   private String firstStepQueue;
	   
	   @Value("${sr.rabbit.second.queue.name}")
	   private String secondStepQueue;
	   
	   @Value("${sr.rabbit.third.queue.name}")
	   private String thirdStepQueue;
	   
	   @Value("${sr.rabbit.first.routing.name}")
	   private String firstRoute;
	   
	   @Value("${sr.rabbit.second.routing.name}")
	   private String secondRoute;
	   
	   @Value("${sr.rabbit.third.routing.name}")
	   private String thirdRoute;
	   
	   public String getExchange() {
			return exchange;
		}
		public String getFirstStepQueue() {
			return firstStepQueue;
		}

		public String getSecondStepQueue() {
			return secondStepQueue;
		}

		public String getThirdStepQueue() {
			return thirdStepQueue;
		}	

		public String getFirstRoute() {
			return firstRoute;
		}

		public String getSecondRoute() {
			return secondRoute;
		}

		public String getThirdRoute() {
			return thirdRoute;
		}
}
