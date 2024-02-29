package com.example.demo.producer;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.config.RabbitProperty;
import com.example.demo.converter.MoneyTransferRequest;
@Service
public class ProducerService {
	@Autowired
	private RabbitProperty rabbitPerperty;
	@Autowired
	private  DirectExchange exchange;
	@Autowired
    private  AmqpTemplate rabbitTemplate;
    
    
	

	public void transferMoneyProduce(MoneyTransferRequest transferRequest){
        rabbitTemplate.convertAndSend(exchange.getName(), rabbitPerperty.getFirstRoute(), transferRequest);
    }
 
	public void sendReceiverProduce(MoneyTransferRequest transferRequest) {
        rabbitTemplate.convertAndSend(exchange.getName(), rabbitPerperty.getSecondRoute(), transferRequest);
	}
	
	public void finalizeTransferProduce(MoneyTransferRequest transferRequest) {
        rabbitTemplate.convertAndSend(exchange.getName(), rabbitPerperty.getThirdRoute(), transferRequest);
	}
}

