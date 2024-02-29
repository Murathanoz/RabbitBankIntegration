package com.example.demo.service.impl;

import java.util.Optional;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.converter.MoneyTransferRequest;
import com.example.demo.entity.Account;
import com.example.demo.producer.ProducerService;
import com.example.demo.repository.AccountRepository;
import com.example.demo.service.IUserService;

@Service
public class UserServiceImpl implements IUserService {
		@Autowired
		private ProducerService producerService;
		@Autowired
	    private AccountRepository accountRepository;	
		@Autowired
		private DirectExchange exchange;
		@Autowired
	    private AmqpTemplate rabbitTemplate;
		
	   @Override
	   public void transferMoneyMessage(MoneyTransferRequest transferRequest) {
	        Optional<Account> accountOptional = accountRepository.findById(transferRequest.getFromId());
	        accountOptional.ifPresentOrElse(account -> {
	            if (account.getBalance() > transferRequest.getAmount()) {
	                account.setBalance(account.getBalance() - transferRequest.getAmount());
	                accountRepository.save(account);
	                producerService.sendReceiverProduce(transferRequest);
	            } else {
	                System.out.println("Insufficient funds -> accountId: " + transferRequest.getFromId() + " balance: " + account.getBalance() + " amount: " + transferRequest.getAmount());
	            }},
	            () -> System.out.println("Account not found")
	        );
	    }
	   
	   @Override
	    public void updateReceiverAccount(MoneyTransferRequest transferRequest) {
	        Optional<Account> accountOptional = accountRepository.findById(transferRequest.getToId());
	        accountOptional.ifPresentOrElse(account -> {
	                        account.setBalance(account.getBalance() + transferRequest.getAmount());
	                        accountRepository.save(account);
	                        rabbitTemplate.convertAndSend(exchange.getName(), "thirdRoute", transferRequest);
	                        },
	                () -> {
	                    System.out.println("Receiver Account not found");
	                    Optional<Account> senderAccount = accountRepository.findById(transferRequest.getFromId());
	                    senderAccount.ifPresent(sender -> {
	                        System.out.println("Money charge back to sender");
	                        sender.setBalance(sender.getBalance() + transferRequest.getAmount());
	                        accountRepository.save(sender);
	                    });
	                }
	        );
	    }

	   @Override
	    public void finalizeTransfer(MoneyTransferRequest transferRequest) {

	    	 Optional<Account> accountOptional = accountRepository.findById(transferRequest.getFromId());
			accountOptional.ifPresentOrElse(account ->
			        {
			            System.out.println("Sender(" + account.getId() +") new account balance: " + account.getBalance());
			        }, () -> System.out.println("Account not found")
			);

			Optional<Account> accountToOptional = accountRepository.findById(transferRequest.getToId());
			accountToOptional.ifPresentOrElse(account ->
			{
			    System.out.println("Receiver(" + account.getId() +") new account balance: " + account.getBalance());
			},
			        () -> System.out.println("Account not found")
			);	      

	    }
}
