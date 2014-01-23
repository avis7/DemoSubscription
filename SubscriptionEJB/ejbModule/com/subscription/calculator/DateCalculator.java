package com.subscription.calculator;

import java.util.Calendar;
import java.util.Date;

public class DateCalculator {
	/**
	   * Adds a number of months to a date returning a new object.
	   * The original date object is unchanged.
	   *
	   * @param date  the date, not null
	   * @param amount  the amount to add, may be negative
	   * @return the new date object with the amount added
	   * @throws IllegalArgumentException if the date is null
	   */
	public static Date addMonths(Date date, int amount) {
	      return add(date, Calendar.MONTH, amount);
	}
	public static Date addDays(Date date, int amount) {
	      return add(date, Calendar.DAY_OF_WEEK, amount);
	}
	/**
	   * Adds to a date returning a new object.
	   * The original date object is unchanged.
	   *
	   * @param date  the date, not null
	   * @param calendarField  the calendar field to add to
	   * @param amount  the amount to add, may be negative
	   * @return the new date object with the amount added
	   * @throws IllegalArgumentException if the date is null
	   */
	private static Date add(Date date, int calendarField, int amount) {
	      if (date == null) {
	          throw new IllegalArgumentException("The date must not be null");
	      }
	      Calendar c = Calendar.getInstance();
	      c.setTime(date);
	      c.add(calendarField, amount);
	      return c.getTime();
	  }
	 /** 
	   * Returns number of days between startDate and endDate<p> 
	   *  
	   * @param java.util.Date startDate
	   * @param java.util.Date endDate
	   * @param boolean includeStartDate<p>
	   *  
	   */ 
	    public static int daysInterval (Date startDate, Date endDate,
	        boolean includeStartDate ) {
	    
	    startDate = removeTime(startDate);
	    Calendar start = Calendar.getInstance();
	    start.setTime(startDate);   

	    endDate = removeTime(endDate);    
	    Calendar end = Calendar.getInstance();
	    end.setTime(endDate);
	    
	    if (includeStartDate) {
	      start.add(Calendar.DATE, -1);
	    }
	    
	    int days = 0;
	    while (start.before(end)) {
	      days++;
	      start.add(Calendar.DATE,1);
	    }
	    return days;
	  }
	    /**
	     * Puts hours, minutes, seconds and milliseconds to zero. <p>
	     * 
	     * @return The same date sent as argument (a new date is not created). If null
	     *      if sent a null is returned.
	     */
	    public static Date removeTime(Date date) {
	        if (date == null) return null;
	        Calendar cal = Calendar.getInstance();
	        cal.setTime(date);
	        cal.set(Calendar.HOUR_OF_DAY, 0); 
	        cal.set(Calendar.MINUTE, 0);    
	        cal.set(Calendar.SECOND, 0);
	        cal.set(Calendar.MILLISECOND, 0);
	        date.setTime(cal.getTime().getTime());
	        return date;
	      }
	    /**
	     * <p>Checks if the first date is after the second date ignoring time.</p>
	     * @param date1 the first date, not altered, not null
	     * @param date2 the second date, not altered, not null
	     * @return true if the first date day is after the second date day.
	     * @throws IllegalArgumentException if the date is <code>null</code>
	     */
	    public static boolean isAfterDay(Date date1, Date date2) {
	        if (date1 == null || date2 == null) {
	            throw new IllegalArgumentException("The dates must not be null");
	        }
	        Calendar cal1 = Calendar.getInstance();
	        cal1.setTime(date1);
	        Calendar cal2 = Calendar.getInstance();
	        cal2.setTime(date2);
	        return isAfterDay(cal1, cal2);
	    }
	    /**
	     * <p>Checks if the first calendar date is after the second calendar date ignoring time.</p>
	     * @param cal1 the first calendar, not altered, not null.
	     * @param cal2 the second calendar, not altered, not null.
	     * @return true if cal1 date is after cal2 date ignoring time.
	     * @throws IllegalArgumentException if either of the calendars are <code>null</code>
	     */
	    public static boolean isAfterDay(Calendar cal1, Calendar cal2) {
	        if (cal1 == null || cal2 == null) {
	            throw new IllegalArgumentException("The dates must not be null");
	        }
	        if (cal1.get(Calendar.ERA) < cal2.get(Calendar.ERA)) return false;
	        if (cal1.get(Calendar.ERA) > cal2.get(Calendar.ERA)) return true;
	        if (cal1.get(Calendar.YEAR) < cal2.get(Calendar.YEAR)) return false;
	        if (cal1.get(Calendar.YEAR) > cal2.get(Calendar.YEAR)) return true;
	        return cal1.get(Calendar.DAY_OF_YEAR) > cal2.get(Calendar.DAY_OF_YEAR);
	    }
	    
}
