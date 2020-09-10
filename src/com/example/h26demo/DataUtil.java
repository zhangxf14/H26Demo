package com.example.h26demo;

import java.text.DecimalFormat;
import java.util.Locale;

import android.content.Context;

public class DataUtil {

	/**
	 * Format double number 
	 * 
	 * @param num number which to format
	 * @param format eg."0.00" or "0.0"
	 * @return string after format
	 */
	public static String formatNumber(int number) {
		return String.format(Locale.getDefault(), "%4s", number);
	}
	
	public static String formatDouble (double num, String format) {
		DecimalFormat decimalFormat = new DecimalFormat(format);
		return  decimalFormat.format(num);
	}
	
	public static String formatTemperature(double temperature) {
		return String.format(Locale.getDefault(), "%.1f ℃", temperature);
	}
	
	public static String formatTemperature2(double temperature) {
		return String.format(Locale.getDefault(), "%.1f℃", temperature);
	}
	
	public static String formatTemperature(int temperature) {
		return String.format(Locale.getDefault(), "%1d ℃", temperature);
	}
	
	public static String formatVoltage(double voltage) {
		return String.format(Locale.getDefault(), "%.2f V", voltage);
	}
	
	public static String formatInterval(Context context, int interval) {
		if(interval % 60 == 0)
		   return interval/60+"分钟";
		else
		   return  interval+"秒";
		
	}
	
//	public static int convertecondToMinute(int second) {
//		if (second % 60 == 0) {

//			return second / 60; 
//		} else {

//			return second ;
//		}
//	}

	public static String byteArrayToString(byte[] byteArray) {
		StringBuffer sb = new StringBuffer();
		for(byte b :byteArray) {
			sb.append((""+(byte) ((b >> 7) & 0x1) + (byte) ((b >> 6) & 0x1)
	                + (byte) ((b >> 5) & 0x1) + (byte) ((b >> 4) & 0x1)
	                + (byte) ((b >> 3) & 0x1) + (byte) ((b >> 2) & 0x1)
	                + (byte) ((b >> 1) & 0x1) + (byte) ((b >> 0) & 0x1)));
		}
	
		String s =  sb.toString();
		return s;
	}
}
