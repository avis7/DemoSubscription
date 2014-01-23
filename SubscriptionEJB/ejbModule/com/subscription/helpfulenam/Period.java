package com.subscription.helpfulenam;

public enum Period {
daily("daily", 0),

weekly("weekly", 0),
twice_per_month("twicePerMonth", 0),
monthly("monthly",1), 
yearly("oncePerYear",12); 
private final String lable;
int additional;


public int getAdditional() {
	return additional;
}

public void setAdditional(int additional) {
	this.additional = additional;
}

public String getLable() {
	return lable;
}

private Period(String lable, int additional) {
	this.lable = lable;
	this.additional = additional;
}

}
