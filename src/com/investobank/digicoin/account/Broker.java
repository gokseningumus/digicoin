package com.investobank.digicoin.account;

import java.math.BigDecimal;
import java.util.ArrayList;

import com.investobank.digicoin.common.Commission;

public class Broker {

	private String name = "";
	private int commisionType = 0;
	private BigDecimal rate = BigDecimal.ZERO;
	private ArrayList<String> commisionDetails = new ArrayList<String>();
	
	public Broker(String name, BigDecimal rate, int commisionType, ArrayList<String> commisionDetails){
		this.name = name;
		this.rate = rate;
		this.commisionType = commisionType;
		this.commisionDetails = commisionDetails;
	}

	private BigDecimal getCommisionCost (int amount) {

		return Commission.getCommisionCost(amount, this.commisionType, this.commisionDetails);
	}
	
	public BigDecimal getTotalCost(int amount) {

		return getCommisionCost(amount).add(new BigDecimal(amount)).multiply(this.rate);
	}

	/*
	 * GETTERS & SETTERS
	 */
	
	public String getName() {
		return name;
	}

	public int getCommisionType() {
		return commisionType;
	}

	public BigDecimal getRate() {
		return rate;
	}

	public ArrayList<String> getCommisionDetails() {
		return commisionDetails;
	}
}
