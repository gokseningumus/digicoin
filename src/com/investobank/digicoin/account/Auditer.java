package com.investobank.digicoin.account;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import com.investobank.digicoin.model.Transaction;

public class Auditer {

	private ArrayList<Transaction> auditedList = null;
	
	public Auditer(){
		this.auditedList = new ArrayList<Transaction>();
	}
	
	public void doAudit(Transaction transaction){
		this.auditedList.add(transaction);
	}
	
	public ArrayList<Transaction> getAuditList(){
		return this.auditedList;
	}
	
	public ArrayList<Transaction> getAuditListByBroker(Broker broker){
		ArrayList<Transaction> filteredList = new ArrayList<Transaction>(); 
		Iterator<Transaction> it = auditedList.iterator();
		
		while (it.hasNext()){
			Transaction item = (Transaction) it.next();
			if(item.getBroker().getName().equalsIgnoreCase(broker.getName())){
				filteredList.add(item);
			}
		}
		
		return filteredList;
	}
	
	public ArrayList<Transaction> getAuditListByClient(Client client){
		ArrayList<Transaction> filteredList = new ArrayList<Transaction>(); 
		Iterator<Transaction> it = auditedList.iterator();
		
		while (it.hasNext()){
			Transaction item = (Transaction) it.next();
			if(item.getOrder().getClient().getName().equalsIgnoreCase(client.getName())){
				filteredList.add(item);
			}
		}
		
		return filteredList;
	}
	
	public HashMap<String,String> getReportByClient(ArrayList<Client> clientList){
		HashMap<String,String> report = new HashMap<String,String>();
		
		BigDecimal clientTotalCost = BigDecimal.ZERO;
		ArrayList<Transaction> list = null;
		Transaction transaction = null;
		Client client = null;
		Iterator<Client> it = clientList.iterator();
		
		while(it.hasNext()){
			clientTotalCost = BigDecimal.ZERO;
			
			client = it.next();
			list = getAuditListByClient(client);
			
			Iterator<Transaction> transactionIt = list.iterator();
			while(transactionIt.hasNext()){
				transaction = transactionIt.next();
				
				if(transaction.getOrder().getOperation().trim().equalsIgnoreCase("buy")){
					clientTotalCost = clientTotalCost.add(transaction.getCost());
				}else{
					clientTotalCost = clientTotalCost.subtract(transaction.getCost());
				}
			}
			report.put(client.getName(), clientTotalCost.toString());
		}
		
		
		return report;
	}
	
	public HashMap<String,String> getReportByBroker(ArrayList<Broker> brokerList){
		HashMap<String,String> report = new HashMap<String,String>();
		
		int brokerTotalQuote = 0;
		ArrayList<Transaction> list = null;
		Transaction transaction = null;
		Broker broker = null;
		Iterator<Broker> it = brokerList.iterator();
		
		while(it.hasNext()){
			brokerTotalQuote = 0;
			
			broker = it.next();
			list = getAuditListByBroker(broker);
			
			Iterator<Transaction> transactionIt = list.iterator();
			while(transactionIt.hasNext()){
				transaction = transactionIt.next();
				
				brokerTotalQuote += transaction.getOrder().getAmount();
			}
			report.put(broker.getName(), brokerTotalQuote+"");
		}
		
		
		return report;
	}
}
