package com.investobank.digicoin.common;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;

public class Commission {

	public static final int FIXED_COMMISION = 0;
	public static final int BLOCKED_COMMISION = 1;
	
	private Commission(){ }

	public static BigDecimal getCommisionCost(int amount, int commisionType, ArrayList<String> commisionDetail) {
		
		BigDecimal commisionCost = BigDecimal.ZERO;
		BigDecimal commisionRate = BigDecimal.ZERO;
		
		double rateDouble = 0d;
		
		switch(commisionType){
		
		case FIXED_COMMISION:
			String rate_str = (String) commisionDetail.get(0);
			rateDouble = Double.parseDouble(rate_str);
			break;
			
		case BLOCKED_COMMISION:
			rateDouble = getCommisionRate(amount,commisionDetail);
			break;
		}
		
		commisionRate = BigDecimal.valueOf(rateDouble);
		commisionCost = commisionRate.multiply(new BigDecimal(amount)).divide(new BigDecimal(100));
		
		return commisionCost;	
	}
	
	public static double getCommisionRate(int amount, ArrayList<String> commisionDetail) {
		
		Iterator<String> it = commisionDetail.iterator();
		
		int range0 = 0;
		int range1 = 0;
		double rate = 0d;
		double commision_rate = 0d;
		
		while (it.hasNext()){
			
			String rate_str = (String)it.next();
			String[] parts = rate_str.split("-");
			
			range0 = Integer.parseInt(parts[0]);
			range1 = Integer.parseInt(parts[1]);
			rate = Double.parseDouble(parts[2]);
			
			if(amount >= range0 && amount <= range1){
				commision_rate = rate;
				break;
			}
		}
		
		return commision_rate;
	}
}
