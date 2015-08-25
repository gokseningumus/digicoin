package com.investobank.digicoin.account;

import com.investobank.digicoin.model.Order;

public class Client {
	
	private String name = "";
	
	public Client(String name){
		this.name = name;
	}

	
	public Order requestOrder(String operation, int amount){
		
		return new Order(this, operation, amount);
	}

	/*
	 * GETTERS & SETTERS
	 */
	
	public String getName() {
		return name;
	}
}
