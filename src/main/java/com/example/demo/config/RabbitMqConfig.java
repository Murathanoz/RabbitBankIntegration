package com.example.demo.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {
	
	@Autowired
	private RabbitProperty rabbitPerperty;
   
    @Bean
    DirectExchange exchange() {
        return new DirectExchange(rabbitPerperty.getExchange());
    }

    @Bean
    Queue firstStepQueue(){
        return new Queue(rabbitPerperty.getFirstStepQueue(), false);
    }

    @Bean
    Queue secondStepQueue() {
        return new Queue(rabbitPerperty.getSecondStepQueue(), true);
    }

    @Bean
    Queue thirdStepQueue() {
        return new Queue(rabbitPerperty.getThirdStepQueue(), true);
    }

    @Bean
    Binding binding(Queue firstStepQueue, DirectExchange exchange){
        return BindingBuilder.bind(firstStepQueue).to(exchange).with(rabbitPerperty.getFirstRoute());
    }

    @Bean
    Binding secondBinding(Queue secondStepQueue, DirectExchange exchange){
        return BindingBuilder.bind(secondStepQueue).to(exchange).with(rabbitPerperty.getSecondRoute());
    }

    @Bean
    Binding thirdBinding(Queue thirdStepQueue, DirectExchange exchange){
        return BindingBuilder.bind(thirdStepQueue).to(exchange).with(rabbitPerperty.getThirdRoute());
    }

    @Bean
    public MessageConverter jsonMessageConverter(){
        return new Jackson2JsonMessageConverter();
    }

	


}
