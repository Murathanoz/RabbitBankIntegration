package com.example.demo.entity;

import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="Account")
public class Account implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
    private String id;

    private String customerId;
    private Double balance;
    private City city;
    private Currency currency;
    
    
	public Account() {
	}
	
	public Account(String id, String customerId, Double balance, City city, Currency currency) {
		super();
		this.id = id;
		this.customerId = customerId;
		this.balance = balance;
		this.city = city;
		this.currency = currency;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public Double getBalance() {
		return balance;
	}
	public void setBalance(Double balance) {
		this.balance = balance;
	}
	public City getCity() {
		return city;
	}
	public void setCity(City city) {
		this.city = city;
	}
	public Currency getCurrency() {
		return currency;
	}
	public void setCurrency(Currency currency) {
		this.currency = currency;
	}

    
}
