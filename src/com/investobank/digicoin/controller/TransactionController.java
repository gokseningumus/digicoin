package com.investobank.digicoin.controller;

import java.math.BigDecimal;
import java.util.ArrayList;

import com.investobank.digicoin.account.Auditer;
import com.investobank.digicoin.account.Broker;
import com.investobank.digicoin.model.Order;
import com.investobank.digicoin.model.Transaction;

public class TransactionController {

	private Auditer auditer = null;
	
	public TransactionController(){
		this.auditer = new Auditer();
	}

	/*
	 * This method finds the best offer between two brokers for related order
	 * This method works only for 1 broker or 2 broker case.
	 * Split functionality does not work for more the 2 brokers.
	 */
	public String executeOrderAtBestOfferWithSplit(Order order, ArrayList<Broker> brokerList) throws Exception{
		
		BigDecimal totalCost = BigDecimal.ZERO;
		int amount = order.getAmount();
		
		if(brokerList.size() == 1) {
			executeOrder(order, brokerList.get(0));
			totalCost = brokerList.get(0).getTotalCost(order.getAmount());
		}
		else{

			Broker broker1 = brokerList.get(0);
			Broker broker2 = brokerList.get(1);
						
			BigDecimal totalCostCompared = BigDecimal.ZERO;
			int finalAmountB1 = 0, finalAmountB2 = 0;
			int amountB1 = 0, amountB2 = 0;
			
			for(int i=0; i<=amount; i=i+10){
				amountB1 = amount - i;
				amountB2 = amount - amountB1;
				
				if(amountB1<0 || amountB2<0)
					break;
				if(amountB1>100 || amountB2>100)
					continue;
				
				if(amountB1 == 0){
					totalCostCompared = broker2.getTotalCost(amountB2);
					
					if(totalCost.compareTo(BigDecimal.ZERO) == 0){
						totalCost = totalCostCompared;
						finalAmountB1 = amountB1;
						finalAmountB2 = amountB2;
					}else if(totalCostCompared.compareTo(totalCost) < 0){
						totalCost = totalCostCompared;
						finalAmountB1 = amountB1;
						finalAmountB2 = amountB2;
						
					}
				} else if (amountB2 == 0){
					totalCostCompared = broker1.getTotalCost(amountB1);
					
					if(totalCost.compareTo(BigDecimal.ZERO) == 0){
						totalCost = totalCostCompared;
						finalAmountB1 = amountB1;
						finalAmountB2 = amountB2;
					}else if(totalCostCompared.compareTo(totalCost) < 0){
						totalCost = totalCostCompared;
						finalAmountB1 = amountB1;
						finalAmountB2 = amountB2;
					}
				}else{
					totalCostCompared = broker1.getTotalCost(amountB1)
							.add(broker2.getTotalCost(amountB2));
					
					if(totalCost.compareTo(BigDecimal.ZERO) == 0){
						totalCost = totalCostCompared;
						finalAmountB1 = amountB1;
						finalAmountB2 = amountB2;
					}else if(totalCostCompared.compareTo(totalCost) < 0){
						totalCost = totalCostCompared;
						finalAmountB1 = amountB1;
						finalAmountB2 = amountB2;
					}
				}
			}
			
			if(finalAmountB1 > 0){
				Order order1 = new Order(order.getClient(),order.getOperation(),finalAmountB1);
				order1.setId(order.getId());
				executeOrder(order1, broker1);
			}
			if(finalAmountB2 > 0){
				Order order2 = new Order(order.getClient(),order.getOperation(),finalAmountB2);
				order2.setId(order.getId());
				executeOrder(order2, broker2);
			}
			if(finalAmountB1 <= 0 && finalAmountB2 <= 0) {
				throw new Exception("You've requested too much! Each broker can execute an order of 100 digicoins at max. at a time.");
			}
		}
		return order.getId()+"-"+totalCost;
	}
	
	/*
	 * Simply executes the order with pre-selected broker
	 * Order execution means 
	 * 	- creating the transaction
	 *  - storing the transaction into Auditing system
	 */
	public void executeOrder (Order order, Broker broker) throws Exception{
		
		if(validateOrder(order)){
			auditer.doAudit(new Transaction(order, broker));
		}
	}
	
	public boolean validateOrder(Order order) throws Exception{

		if (order.getAmount() % 10 != 0){
			throw new Exception("Brokers only support deals in multiply of 10 DigiCoins!");
		}
		if (order.getAmount() > 100){
			throw new Exception("Broker can execute an order of 100 digicoins at max. at a time!");
		}
		return true;
	}
	
	public Auditer getAuditer(){
		return this.auditer;
	}

}
