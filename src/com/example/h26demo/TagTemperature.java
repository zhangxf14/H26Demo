package com.example.h26demo;

import java.util.Date;
import java.util.Hashtable;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

/**
 * 单次温度
 * @author Sai
 *
 */
public class TagTemperature implements KvmSerializable {
	private String tagId;// 标签号
	private double temperature;
	private Date date;
	private double temperatureMax;// 最大温度
	private double temperatureMin;// 最小温度
	private int exceededStatus;
	
	public String getTagId() {
		return tagId;
	}

	public void setTagId(String tagId) {
		this.tagId = tagId;
	}

	public double getTemperature() {
		return temperature;
	}

	public void setTemperature(double temperature) {
		this.temperature = temperature;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public double getTemperatureMax() {
		return temperatureMax;
	}

	public void setTemperatureMax(double temperatureMax) {
		this.temperatureMax = temperatureMax;
	}

	public double getTemperatureMin() {
		return temperatureMin;
	}

	public void setTemperatureMin(double temperatureMin) {
		this.temperatureMin = temperatureMin;
	}

	public int getExceededStatus() {
		return exceededStatus;
	}

	public void setExceededStatus(int exceededStatus) {
		this.exceededStatus = exceededStatus;
	}

	@Override
	public Object getProperty(int index) {
		switch (index) {
		case 0:
			return date;
		case 1:
			return temperature;
		}
		return null;
	}

	@Override
	public int getPropertyCount() {
		return 2;
	}

	@Override
	public void getPropertyInfo(int index, Hashtable table, PropertyInfo info) {
		switch (index) {
		case 0:
			info.type = PropertyInfo.OBJECT_CLASS;
			info.name = Constants.DATE_OUTPUT;
			break;
		case 1:
			info.type = PropertyInfo.OBJECT_CLASS;
			info.name = Constants.TEMPERATURE_OUTPUT;
			break;
		}
		
	}

	@Override
	public void setProperty(int index, Object value) {
		switch (index) {
		case 0:
			date = TimeUtil.getDate2(value.toString());
			break;
		case 1:
			temperature = Double.parseDouble(value.toString());
			break;
		}
		
	}
}
