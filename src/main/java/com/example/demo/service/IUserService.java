package com.example.demo.service;

import com.example.demo.converter.MoneyTransferRequest;

public interface IUserService {
	void transferMoneyMessage(MoneyTransferRequest transferRequest);
	void updateReceiverAccount(MoneyTransferRequest transferRequest);
	void finalizeTransfer(MoneyTransferRequest transferRequest);
	
}
