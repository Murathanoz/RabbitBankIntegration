package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.converter.MoneyTransferRequest;
import com.example.demo.producer.ProducerService;

@Controller
@RequestMapping("/v1/")
@EnableAsync
public class AccountController {
	
	@Autowired
	private ProducerService producerService;
	
	 @PutMapping("/transfer")
	    public ResponseEntity<String> transferMoney(@RequestBody MoneyTransferRequest user) {
		 
		 producerService.transferMoneyProduce(user);
		 
	        return ResponseEntity.ok("İşleminiz başarıyla alınmıştır!");
	    } 
	
	

}
