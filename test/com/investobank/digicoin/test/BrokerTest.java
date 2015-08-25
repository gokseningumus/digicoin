package com.investobank.digicoin.test;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.ArrayList;

import org.junit.Test;

import com.investobank.digicoin.account.Broker;
import com.investobank.digicoin.common.Commission;

public class BrokerTest {

	@Test
	public void testBrokerTotalCostFixed() {
		
		ArrayList<String> detail = new ArrayList<String>();
		detail.add("5");
		
		Broker broker = new Broker("Göksenin",new BigDecimal("1.49"), Commission.FIXED_COMMISION, detail);
		
		assertEquals("15.645", broker.getTotalCost(10).toString());
	}
	
	@Test
	public void testBrokerTotalCostBlocked() {
		
		ArrayList<String> detail = new ArrayList<String>();
		detail.add("10-40-3");
		detail.add("50-80-2.5");
		detail.add("90-100-2");
		
		Broker broker = new Broker("Göksenin",new BigDecimal("1.52"), Commission.BLOCKED_COMMISION, detail);
		
		assertEquals("77.9000", broker.getTotalCost(50).toString());
	}

}
