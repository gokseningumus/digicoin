package com.investobank.digicoin.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import com.investobank.digicoin.account.Broker;
import com.investobank.digicoin.account.Client;
import com.investobank.digicoin.common.Commission;
import com.investobank.digicoin.model.Order;
import com.investobank.digicoin.model.Transaction;

public class DigicoinController {
	
	//Transactions are controlled by TransactionController
	private TransactionController tc = null;
	
	//Client and Broker list
	private ArrayList<Client> clientList = new ArrayList<Client>();
	private ArrayList<Broker> brokerList = new ArrayList<Broker>();
	
	public DigicoinController() {
		this.tc = new TransactionController();
		
		init();
	}
	
	/*
	 * This starts process of creating the order and forwarding to TransactionControllor to be executed
	 * @return String "OrderID-TotalCost"
	 */
	public String processOrder(String clientName, String operation, String amount) throws Exception{

		validateSetup();
		
		Order order = getClientByName(clientName)
				.requestOrder(operation, Integer.parseInt(amount));

		return tc.executeOrderAtBestOfferWithSplit(order, brokerList);
	}
	
	public ArrayList<Client> getClients(){
		return this.clientList;
	}
	
	public Client getClientByName(String name){
		Client result = null;
		Iterator<Client> it = getClients().iterator();
		while(it.hasNext()){
			Client client = it.next();
			if(client.getName().equalsIgnoreCase(name)){
				result = client;
			}
		}
		return result;
	}
	
	public Broker getBrokerByName(String name){
		Broker result = null;
		Iterator<Broker> it = getBrokers().iterator();
		while(it.hasNext()){
			Broker broker = it.next();
			if(broker.getName().equalsIgnoreCase(name)){
				result = broker;
			}
		}
		return result;
	}
	
	public ArrayList<Broker> getBrokers(){
		return this.brokerList;
	}
	
	public String addClient (String name){
		clientList.add(new Client(name));
		return name;
	}
	
	public String addBroker (String name, String rate, int commisionType, ArrayList<String> commisionDetails) {
		brokerList.add(new Broker(name, new BigDecimal(rate), commisionType, commisionDetails));
		return name;
	}
	
	public HashMap<String,String> getReportByClient(){
		return this.tc.getAuditer().getReportByClient(clientList);
	}
	
	public HashMap<String,String> getReportByBroker(){
		return this.tc.getAuditer().getReportByBroker(brokerList);
	}
	
	public ArrayList<Transaction> getReport(){
		return this.tc.getAuditer().getAuditList();
	}
	
	/*
	 * App initialization done here
	 *  - 3 clients will be created by default: Client A, Client B, Client C
	 *  - 2 brokers will be created by default: Broker 1, Broker 2
	 *  - Broker commision rate initialization:
	 *  	-- broker commision rate format : "range1-range2-rate" eg. "10-40-2.5"
	 *  		That means this broker gets 2.5 commision rate for the amounts in between 10 and 40. 
	 */
	private void init() {

		addClient("Client A");
		addClient("Client B");
		addClient("Client C");

		ArrayList<String> broker1CommisionDetails = new ArrayList<String>();
		broker1CommisionDetails.add("5");
		addBroker("Broker 1", "1.49", Commission.FIXED_COMMISION, broker1CommisionDetails);
		
		ArrayList<String> broker2CommisionDetails = new ArrayList<String>();
		broker2CommisionDetails.add("10-40-3");
		broker2CommisionDetails.add("50-80-2.5");
		broker2CommisionDetails.add("90-100-2");
		addBroker("Broker 2", "1.52", Commission.BLOCKED_COMMISION, broker2CommisionDetails);
	}

	/*
	 * Initial setup including clients and brokers and their commission rates and formats are being validated here.
	 */
	public void validateSetup() throws Exception{
		
		if(clientList.isEmpty()){
			throw new Exception("There is no registered client in the system.");
		}
		if(brokerList.isEmpty()){
			throw new Exception("There is no registered broker in the system.");
		}
		else {
			if(brokerList.size() > 2){
				throw new Exception("There are more than 2 brokers registered. Best Offer with Split Broker functionality supports max. 2 brokers!");	
			}
			
			Iterator<Broker> it = brokerList.iterator();
			while(it.hasNext()){
				Broker broker = it.next();
				
				if(broker.getCommisionType() == Commission.FIXED_COMMISION){
					
					if(broker.getCommisionDetails().size() > 1){
						throw new Exception("Brokers who applies fixed commision needs only fixed commision rate as commision detail.");
					}else{
						String detail = broker.getCommisionDetails().get(0);
						try{
							double d = Double.parseDouble(detail);
						}
						catch(NumberFormatException nfe){
							throw new Exception("Broker commision rates need to be in number format");
						}
					}
				}else{
					
					Iterator<String> it_broker = broker.getCommisionDetails().iterator();
					
					while (it.hasNext()){
						
						String rate_str = (String)it_broker.next();
						String[] parts = rate_str.split("-");
						
						if(parts.length == 3){
							try{
								int range0 = Integer.parseInt(parts[0]);
								int range1 = Integer.parseInt(parts[1]);
								double rate = Double.parseDouble(parts[2]);
							}
							catch(NumberFormatException nfe){
								throw new Exception("Broker commision rates need to be in number format");
							}
						}else{
							throw new IllegalArgumentException("Given commision rate details do not match the format: range0-range1-rate");
						}	
					}
				}
			}
		}
	}
}
