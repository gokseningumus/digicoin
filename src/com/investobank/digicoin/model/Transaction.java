package com.investobank.digicoin.model;

import java.math.BigDecimal;

import java.util.Calendar;
import java.util.Date;

import com.investobank.digicoin.account.Broker;

public class Transaction {

	private int id = 0;
	private Order order = null;
	private Broker broker = null;
	private BigDecimal cost = BigDecimal.ZERO;
	private Date time = null;
	
	public Transaction(Order order, Broker broker){
		this.order = order;
		this.broker = broker;
		this.cost = broker.getTotalCost(order.getAmount());
		this.time = Calendar.getInstance().getTime();
		this.id = order.getId();
	}

	public int getId() {
		return id;
	}

	public Order getOrder() {
		return order;
	}

	public Broker getBroker() {
		return broker;
	}

	public BigDecimal getCost() {
		return cost;
	}

	public Date getTime() {
		return time;
	}
	
}
