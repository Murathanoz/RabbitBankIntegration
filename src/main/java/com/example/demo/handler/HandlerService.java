package com.example.demo.handler;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.converter.MoneyTransferRequest;
import com.example.demo.service.IUserService;
@Service
public class HandlerService {
	@Autowired
	private IUserService userService;
	
	
	 @RabbitListener(queues = "${sr.rabbit.first.queue.name}")
     public void transferMoneyMessageHandler(MoneyTransferRequest transferRequest) {
        userService.transferMoneyMessage(transferRequest);
     }
	
	 @RabbitListener(queues = "${sr.rabbit.second.queue.name}")
	    public void updateReceiverAccountHandler(MoneyTransferRequest transferRequest) {
		 userService.updateReceiverAccount(transferRequest);
	 }
	 
	 @RabbitListener(queues = "${sr.rabbit.third.queue.name}")
	    public void finalizeTransferHandler(MoneyTransferRequest transferRequest) {
		 userService.finalizeTransfer(transferRequest);
	 }
	

}
