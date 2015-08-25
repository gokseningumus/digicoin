package com.investobank.digicoin.test;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.ArrayList;

import org.junit.Test;

import com.investobank.digicoin.account.Broker;
import com.investobank.digicoin.account.Client;
import com.investobank.digicoin.common.Commission;
import com.investobank.digicoin.controller.TransactionController;
import com.investobank.digicoin.model.Order;


public class TransactionControllerTest {

	@Test
	public void testExecuteOrderAtBestOfferWithSplit() {
		TransactionController tc = new TransactionController();
		
		ArrayList<String> detail1 = new ArrayList<String>();
		detail1.add("5");
		
		Broker broker1 = new Broker("Göksenin",new BigDecimal("2"), Commission.FIXED_COMMISION, detail1);
		
		ArrayList<String> detail2 = new ArrayList<String>();
		detail2.add("2");
		
		Broker broker2 = new Broker("Göksenin",new BigDecimal("1"), Commission.FIXED_COMMISION, detail2);
		
		ArrayList<Broker> brokers = new ArrayList<Broker>();
		brokers.add(broker1);
		brokers.add(broker2);
		
		Order order = new Order(new Client("Göksenin"),"buy",10);
		
		try{
			assertEquals(order.getId()+"-10.2",tc.executeOrderAtBestOfferWithSplit(order, brokers));
		}catch (Exception e){}
		
	}

	@Test
	public void testExecuteOrder() {
		TransactionController tc = new TransactionController();
		
		ArrayList<String> detail1 = new ArrayList<String>();
		detail1.add("5");

		Broker broker = new Broker("Göksenin",new BigDecimal("2"), Commission.FIXED_COMMISION, detail1);
		
		Order order = new Order(new Client("Göksenin"),"buy",10);
		
		assertEquals(0, tc.getAuditer().getAuditList().size());
		
		try{
			tc.executeOrder(order, broker);
		}catch (Exception e){}
		
		assertEquals(1, tc.getAuditer().getAuditList().size());
	}

	@Test
	public void testValidateOrder() {
		TransactionController tc = new TransactionController();
		
		Order order = new Order(new Client("Göksenin"),"buy",10);
		ArrayList<String> detail1 = new ArrayList<String>();
		detail1.add("5");

		Broker broker = new Broker("Göksenin",new BigDecimal("2"), Commission.FIXED_COMMISION, detail1);
		
		try{
			tc.executeOrder(order,broker);
		}catch (Exception e){
			fail("Should not throw exception");
		}
	}
	
	@Test
	public void testValidateOrderMoreDigicoins() {
		TransactionController tc = new TransactionController();
		
		Order order = new Order(new Client("Göksenin"),"buy",1000);
		ArrayList<String> detail1 = new ArrayList<String>();
		detail1.add("5");

		Broker broker = new Broker("Göksenin",new BigDecimal("2"), Commission.FIXED_COMMISION, detail1);
		
		try{
			tc.executeOrder(order,broker);
			fail("More digicoins cannot be executed");
		}catch (Exception e){}
	}
	
	@Test
	public void testValidateOrderMultiplyByTen() {
		TransactionController tc = new TransactionController();
		
		Order order = new Order(new Client("Göksenin"),"buy",16);
		ArrayList<String> detail1 = new ArrayList<String>();
		detail1.add("5");

		Broker broker = new Broker("Göksenin",new BigDecimal("2"), Commission.FIXED_COMMISION, detail1);
		
		try{
			tc.executeOrder(order,broker);
			fail("Multiply by 10 rule");
		}catch (Exception e){}
	}

}
