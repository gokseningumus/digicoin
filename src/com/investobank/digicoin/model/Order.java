package com.investobank.digicoin.model;

import java.util.concurrent.atomic.AtomicInteger;

import com.investobank.digicoin.account.Client;

public class Order {
	
	// unique id creator for order IDs.
	static AtomicInteger nextId = new AtomicInteger();
	
	private int id;
	private Client client = null;
	private String operation = "";
	private int amount = 0;
	
	public Order(Client client, String operation, int amount){
		
		this.id = nextId.incrementAndGet();
		this.client = client;
		this.operation = operation;
		this.amount = amount;
	}
	
	
	/*
	 * GETTERS & SETTERS
	 */
	
	public int getId() {
		return id;
	}
	
	public void setId(int id){
		this.id = id;
	}
	
	public Client getClient() {
		return client;
	}

	public String getOperation() {
		return operation;
	}

	public int getAmount() {
		return amount;
	}
	
	public void setAmount(int amount){
		this.amount = amount;
	}

}
