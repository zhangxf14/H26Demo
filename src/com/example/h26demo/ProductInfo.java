package com.example.h26demo;

import java.util.Date;

import org.ksoap2.serialization.SoapObject;
import android.annotation.SuppressLint;
import android.util.Log;

/**
 * 药品
 * 
 * @author Sai
 * 
 */
@SuppressLint("DefaultLocale") 
public class ProductInfo {
	private String id;// 记录ID
	private String uid;// 标签UID
	private String trackingNo;// 运单号
	private long issueDate;// 发货日期
	private String deliver;// 发货单位
	private String receiver;// 接收单位
	private String transport;// 运输单位
	private String goodsName;// 货物名称
	private double goodsQuantity;// 货物数量
	private int transportDay;// 运输天数
	private int goodsState;// 货物(标签)状态 0: 已制卡 1: 已入库 2: 已出库 3: 已签收 4: 已拒收
	private long initDate;// 制卡日期
	private long intoStorageDate;// 入库日期
	private long outStorgeDate;// 出库日期
	private long signDate;// 签收/拒收日期/芯片结束检测时间
	private String specification;// 规格
	private String batchNo;// 批号
	private long expiryDate;// 失效日期
	private long produceDate;// 生产日期
	private String approvalNo;// 批准文号
	private String PIATS;// 电子监管码
	private long date;// 检测的时间，即最新读取标签的时间
	private int interval;// 检测频率
	private String storageRule;// 存储规则
	private String loggingForm;// 记录形式
	private double temperatureMax;// 温度上限
	private double temperatureMin;// 温度下限
	private double temperature;// 当前温度
	private double batteryVoltage;// 当前电量
	private int status;// 超限状态
	private boolean active; // data collection status  数据采集状态，相当于标签是否工作
	private int numMeasurements; // collection times采集总数
	private int numExceeded; // limit exceeded times超限次数
	
	private long highTempDuration; //高温持续时长
	private long lowTempDuration; //低温持续时长
	private long allTempDuration; //总持续时长
	
	private boolean measureExceeded;//采集是否超限
	private Date startTime;//采集开始时间
	private Date maxTempTime;//最高温记录点时间
	private Date minTempTime;//最低温记录点时间
	private Date stopTime;//采集结束时间
	private Date firstLogTime;//第一记录点时间
	private Date lastLogTime;//最后记录点时间
	
	private double[] allTemperature;//检测的温度集合
	
	private double maxTemperature;//  最大温度
	private double minTemperature;//  最小温度
	private double meanTemperature;// 平均温度
	private double MKT;// 平均动力学温度
	
	
	public void parse(SoapObject sObj) {
		setId(sObj.getPropertySafelyAsString(Constants.ID_OUTPUT).toString());
		setUid(sObj.getPropertySafelyAsString(Constants.UID_OUTPUT).toString());
		setTrackingNo(sObj.getPropertySafelyAsString(Constants.TRANKINGNO_OUTPUT).toString());
		setIssueDate(TimeUtil.getWebTimeToLong(sObj.getPropertySafelyAsString(
				Constants.ISSUEDATE_OUTPUT).toString()));
		setDeliver(sObj.getPropertySafelyAsString(Constants.DELIVER_OUTPUT)
				.toString());
		setReceiver(sObj.getPropertySafelyAsString(Constants.RECEIVER_OUTPUT)
				.toString());
		setTransport(sObj.getPropertySafelyAsString(Constants.TRANSPORT_OUTPUT)
				.toString());
		setGoodsName(sObj.getPropertySafelyAsString(Constants.GOODSNAME_OUTPUT)
				.toString());
		setGoodsQuantity(Double.valueOf(sObj.getPropertySafelyAsString(
				Constants.GOODSQUANTITY_OUTPUT, 0.0).toString()));
		setTransportDay(Integer.valueOf(sObj.getPropertySafelyAsString(
				Constants.TRANSPORTDAY_OUTPUT, 0.0).toString()));
		setGoodsState(Integer.valueOf(sObj.getPropertySafelyAsString(
				Constants.GOODSSTATE_OUTPUT, 0.0).toString()));
		setInitDate(TimeUtil.getWebTimeToLong(sObj.getPropertySafelyAsString(
				Constants.INITDATE_OUTPUT, 0.0).toString()));
		setIntoStorageDate(TimeUtil.getWebTimeToLong(sObj
				.getPropertySafelyAsString(Constants.INTOSTORAGEDATE_OUTPUT,
						0.0).toString()));
		setOutStorgeDate(TimeUtil.getWebTimeToLong(sObj
				.getPropertySafelyAsString(Constants.OUTSTORGEDATE_OUTPUT, 0.0)
				.toString()));
		setSignDate(TimeUtil.getWebTimeToLong(sObj.getPropertySafelyAsString(
				Constants.SIGNDATE_OUTPUT, 0.0).toString()));
		setSpecification(String.valueOf(sObj.getPropertySafelyAsString(
				Constants.SPECIFICATION_OUTPUT, 0.0).toString()));
		setBatchNo(String.valueOf(sObj.getPropertySafelyAsString(
				Constants.BATCHNO_OUTPUT, 0.0).toString()));
		setExpiryDate(TimeUtil.getWebTimeToLong(sObj.getPropertySafelyAsString(
				Constants.EXPIRYDATE_OUTPUT, 0.0).toString()));
		setProduceDate(TimeUtil.getWebTimeToLong(sObj
				.getPropertySafelyAsString(Constants.PRODUCEDATE_OUTPUT, 0.0)
				.toString()));
		setApprovalNo(String.valueOf(sObj.getPropertySafelyAsString(
				Constants.APPROVALNO_OUTPUT, 0.0).toString()));
		setPIATS(String.valueOf(sObj.getPropertySafelyAsString(Constants.PIATS,
				0.0).toString()));
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}
	
	public String getTrackingNo() {
		return trackingNo;
	}

	public void setTrackingNo(String trackingNo) {
		if(trackingNo!=null&&trackingNo.equals(Constants.ANYTYPE)){
			trackingNo="";
		}
		this.trackingNo = trackingNo;
	}

	public long getIssueDate() {
		return issueDate;
	}

	public void setIssueDate(long issueDate) {
		this.issueDate = issueDate;
	}

	public String getDeliver() {
		return deliver;
	}

	public void setDeliver(String deliver) {
		if(deliver!=null&&deliver.equals(Constants.ANYTYPE)){
			deliver="";
		}
		this.deliver = deliver;
	}

	public String getReceiver() {
		return receiver;
	}

	public void setReceiver(String receiver) {
		if(receiver!=null&&receiver.equals(Constants.ANYTYPE)){
			receiver="";
		}
		this.receiver = receiver;
	}

	public String getTransport() {
		return transport;
	}

	public void setTransport(String transport) {
		if(transport!=null&&transport.equals(Constants.ANYTYPE)){
			transport="";
		}
		this.transport = transport;
	}

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		if(goodsName!=null&&goodsName.equals(Constants.ANYTYPE)){
			goodsName="";
		}
		this.goodsName = goodsName;
	}

	public double getGoodsQuantity() {
		return goodsQuantity;
	}

	public void setGoodsQuantity(double goodsQuantity) {
		this.goodsQuantity = goodsQuantity;
	}

	public int getTransportDay() {
		return transportDay;
	}

	public void setTransportDay(int transportDay) {
		this.transportDay = transportDay;
	}

	public int getGoodsState() {
		return goodsState;
	}

	public void setGoodsState(int goodsState) {
		this.goodsState = goodsState;
	}

	public long getInitDate() {
		return initDate;
	}

	public void setInitDate(long initDate) {
		this.initDate = initDate;
	}

	public long getIntoStorageDate() {
		return intoStorageDate;
	}

	public void setIntoStorageDate(long intoStorageDate) {
		this.intoStorageDate = intoStorageDate;
	}

	public long getOutStorgeDate() {
		return outStorgeDate;
	}

	public void setOutStorgeDate(long outStorgeDate) {
		this.outStorgeDate = outStorgeDate;
	}

	public long getSignDate() {
		return signDate;
	}

	public void setSignDate(long signDate) {
		this.signDate = signDate;
	}

	public String getSpecification() {
		return specification;
	}

	public void setSpecification(String specification) {
		if(specification!=null&&specification.equals(Constants.ANYTYPE)){
			specification="";
		}
		this.specification = specification;
	}

	public String getBatchNo() {
		return batchNo;
	}

	public void setBatchNo(String batchNo) {
		if(batchNo!=null&&batchNo.equals(Constants.ANYTYPE)){
			batchNo="";
		}
		this.batchNo = batchNo;
	}

	public long getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(long expiryDate) {
		this.expiryDate = expiryDate;
	}

	public long getProduceDate() {
		return produceDate;
	}

	public void setProduceDate(long produceDate) {
		this.produceDate = produceDate;
	}

	public String getApprovalNo() {
		return approvalNo;
	}

	public void setApprovalNo(String approvalNo) {
		if(approvalNo!=null&&approvalNo.equals(Constants.ANYTYPE)){
			approvalNo="";
		}
		this.approvalNo = approvalNo;
	}

	public String getPIATS() {
		return PIATS;
	}

	public void setPIATS(String pIATS) {
		if(pIATS!=null&&pIATS.equals(Constants.ANYTYPE)){
			pIATS="";
		}
		this.PIATS = pIATS;
	}

	public long getDate() {
		return date;
	}

	public void setDate(long date) {
		this.date = date;
	}
	public int getInterval() {
		return interval;
	}

	public void setInterval(int interval) {
		this.interval = interval;
	}
	
	public String getStorageRule() {
		return storageRule;
	}

	public void setStorageRule(String storageRule) {
		this.storageRule = storageRule;
	}
	
	public String getLoggingForm() {
		return loggingForm;
	}

	public void setLoggingForm(String loggingForm) {
		this.loggingForm = loggingForm;
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

	public double getTemperature() {
		return temperature;
	}

	public void setTemperature(double temperature) {
		this.temperature = temperature;
	}

	public double getBatteryVoltage() {
		return batteryVoltage;
	}

	public void setBatteryVoltage(double batteryVoltage) {
		this.batteryVoltage = batteryVoltage;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public int getNumMeasurements() {
		return numMeasurements;
	}

	public void setNumMeasurements(int numMeasurements) {
		this.numMeasurements = numMeasurements;
	}

	public int getNumExceeded() {
		return numExceeded;
	}
	
	public void setNumExceeded(int numExceeded) {
		this.numExceeded = numExceeded;
	}
	
	public Date getStartTime() {
		return startTime;
	}
	
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	
	public double[] getAllTemperature() {
		return allTemperature;
	}
	
	public void setAllTemperature(double[] allTemperature) {
		this.allTemperature = allTemperature;
	}
	
	public boolean getMeasureExceeded() {
		return measureExceeded;
	}
	
	public void setMeasureExceeded(boolean measureExceeded) {
		this.measureExceeded = measureExceeded;
	}
	
	public double getMaxTemperature() {
		return maxTemperature;
	}

	public void setMaxTemperature(double maxTemperature) {
		this.maxTemperature = maxTemperature;
	}
	
	public double getMinTemperature() {
		return minTemperature;
	}

	public void setMinTemperature(double minTemperature) {
		this.minTemperature = minTemperature;
	}
	
	public double getMeanTemperature() {
		return meanTemperature;
	}

	public void setMeanTemperature(double meanTemperature) {
		this.meanTemperature = meanTemperature;
	}
	
	public double getMKT() {
		return MKT;
	}

	public void setMKT(double MKT) {
		this.MKT = MKT;
	}

	public long getHighTempDuration() {
		return highTempDuration;
	}

	public void setHighTempDuration(long highTempDuration) {
		this.highTempDuration = highTempDuration;
	}
	
	public long getLowTempDuration() {
		return lowTempDuration;
	}

	public void setLowTempDuration(long lowTempDuration) {
		this.lowTempDuration = lowTempDuration;
	}
	
	public long getAllTempDuration() {
		return allTempDuration;
	}

	public void setAllTempDuration(long allTempDuration) {
		this.allTempDuration = allTempDuration;
	}
	
	public Date getMaxTempTime() {
		return maxTempTime;
	}

	public void setMaxTempTime(Date maxTempTime) {
		this.maxTempTime = maxTempTime;
	}
	
	public Date getMinTempTime() {
		return minTempTime;
	}

	public void setMinTempTime(Date minTempTime) {
		this.minTempTime = minTempTime;
	}
	
	public Date getStopTime() {
		return stopTime;
	}

	public void setStopTime(Date stopTime) {
		this.stopTime = stopTime;
	}
	
	public Date getFirstLogTime() {
		return firstLogTime;
	}

	public void setFirstLogTime(Date firstLogTime) {
		this.firstLogTime = firstLogTime;
	}
	
	public Date getLastLogTime() {
		return lastLogTime;
	}

	public void setLastLogTime(Date lastLogTime) {
		this.lastLogTime = lastLogTime;
	}
}

