package com.investobank.digicoin.test;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;
import com.investobank.digicoin.common.Commission;

public class CommisionTest {

	@Test
	public void testCommisionRate() {

		ArrayList<String> detail = new ArrayList<String>();
		detail.add("10-30-3");
		detail.add("40-70-2");
		detail.add("80-100-1");
		
		assertEquals(2.0d, Commission.getCommisionRate(50, detail), 0.0d);	
	}
	
	@Test
	public void testCommisionRateOverlapingLevels() {

		ArrayList<String> detail = new ArrayList<String>();
		detail.add("10-50-3");
		detail.add("40-70-2");
		detail.add("60-100-1");
		
		assertEquals(3.0d, Commission.getCommisionRate(50, detail), 0.0d);	
	}
	
	@Test
	public void testCommisionRateMissingOne() {

		ArrayList<String> detail = new ArrayList<String>();
		detail.add("10-40-3");
		detail.add("50-70-2");
		detail.add("60-80-1");
		
		assertEquals(0.0d, Commission.getCommisionRate(90, detail), 0.0d);
	}
	
	@Test
	public void testBlockedCommisionCost() {

		ArrayList<String> detail = new ArrayList<String>();
		detail.add("10-40-3");
		detail.add("50-70-2");
		detail.add("60-80-1");
		
		assertEquals("0.3", Commission.getCommisionCost(10, Commission.BLOCKED_COMMISION, detail).toString());
	}
	
	@Test
	public void testFixedCommisionCost() {

		ArrayList<String> detail = new ArrayList<String>();
		detail.add("3");
		
		assertEquals("0.3", Commission.getCommisionCost(10, Commission.FIXED_COMMISION, detail).toString());
	}

}
