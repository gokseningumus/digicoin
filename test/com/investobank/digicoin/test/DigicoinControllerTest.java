package com.investobank.digicoin.test;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import com.investobank.digicoin.controller.DigicoinController;
import com.investobank.digicoin.common.Commission;

public class DigicoinControllerTest {

	@Test
	public void testCommisionRateAsString() {
		
		DigicoinController controller = new DigicoinController();
		ArrayList<String> detail = new ArrayList<String>();
		detail.add("sss");
		controller.addBroker("Göksenin", "1.4", Commission.FIXED_COMMISION, detail);

		try{
			controller.validateSetup();
			fail("Should throw an Exception: commision rate as String");
		} catch (Exception e){}
	}
	
	@Test
	public void testMoreCommisionRateInFixed() {
		
		DigicoinController controller = new DigicoinController();
		ArrayList<String> detail = new ArrayList<String>();
		detail.add("1.5");
		detail.add("1.7");
		controller.addBroker("Göksenin", "1.4", Commission.FIXED_COMMISION, detail);

		try{
			controller.validateSetup();
			fail("Should throw an Exception: more commision rate in fixed commision");
		} catch (Exception e){}
	}
	
	@Test
	public void testSingleCommisionRateInBlocked() {
		
		DigicoinController controller = new DigicoinController();
		ArrayList<String> detail = new ArrayList<String>();
		detail.add("1.5");
		controller.addBroker("Göksenin", "1.4", Commission.BLOCKED_COMMISION, detail);

		try{
			controller.validateSetup();
			fail("Should throw an Exception: single commision rate in Blocked commision");
		} catch (Exception e){}
	}
	
	@Test
	public void testStringCommisionRateInBlocked() {
		
		DigicoinController controller = new DigicoinController();
		ArrayList<String> detail = new ArrayList<String>();
		detail.add("aaa");
		controller.addBroker("Göksenin", "1.4", Commission.BLOCKED_COMMISION, detail);

		try{
			controller.validateSetup();
			fail("Should throw an Exception: string commision rate in Blocked commision");
		} catch (Exception e){}
	}
	
	@Test
	public void testMoreElementsAsCommisionRateInBlocked() {
		
		DigicoinController controller = new DigicoinController();
		ArrayList<String> detail = new ArrayList<String>();
		detail.add("1.2-3.4-5.6-54-22-11");
		controller.addBroker("Göksenin", "1.4", Commission.BLOCKED_COMMISION, detail);

		try{
			controller.validateSetup();
			fail("Should throw an Exception: more elements as commision rate in Blocked commision");
		} catch (Exception e){}
	}
	
	@Test
	public void testEmptyCommisionRateInBlocked() {
		
		DigicoinController controller = new DigicoinController();
		ArrayList<String> detail = new ArrayList<String>();
		
		controller.addBroker("Göksenin", "1.4", Commission.BLOCKED_COMMISION, detail);

		try{
			controller.validateSetup();
			fail("Should throw an Exception: empty commision rate in Blocked commision");
		} catch (Exception e){}
	}
	
	@Test
	public void testOnlyDelimeterAsCommisionRateInBlocked() {
		
		DigicoinController controller = new DigicoinController();
		ArrayList<String> detail = new ArrayList<String>();
		detail.add("-");
		controller.addBroker("Göksenin", "1.4", Commission.BLOCKED_COMMISION, detail);

		try{
			controller.validateSetup();
			fail("Should throw an Exception: Only delimeter as commision rate in Blocked commision");
		} catch (Exception e){}
	}

}
