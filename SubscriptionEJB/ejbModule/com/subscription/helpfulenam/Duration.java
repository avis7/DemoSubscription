package com.subscription.helpfulenam;



public enum Duration {
month("month", 1),
two_month("twoMonth", 2), 
half_year("halfAYear", 6),
year("year", 12);
private final String lable;
private final int amount;

public int getAmount() {
	return amount;
}

public String getLable() {
	return lable;
}

private Duration(String lable, int amount) {
	this.lable = lable;
	this.amount=amount;
}


}
