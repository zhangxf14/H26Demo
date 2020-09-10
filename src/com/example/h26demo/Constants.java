package com.example.h26demo;

/**
 *@author Sai
 *Created on 2014年9月10日 下午22:10:39
 *类说明：数据库中常量
 */
public class Constants {
	public static String DBNAME="DB_H26DEMO";//数据库名
	public static int VERSION=2;//数据库版本
	public static String TB_TAG_BASEINFO="TB_TAG_BASEINFO";//表名，保存标签基本信息列表
	public static String TB_TAG_CHECKLOG="TB_TAG_CHECKLOG";//表名，标签检测记录
	public static String TB_USER="TB_USER";//表名，历史用户表
	public static String TB_PRODUCT="TB_PRODUCT_H26";//表名，商品表
	public static String TB_TEMPERATURE="TB_TEMPERATURE_H26";//表名，TAG对应温度表
	public static String TB_DEVICE="TB_DEVICE";//表名，蓝牙设备表
	public static String TB_PARAMETERSET="TB_PARAMETERSET";//表名，参数设置表
	
	public static String PARA_STARTTYPE="startType"; //启动方式
	public static String PARA_DELAYTIME="delayTime"; //延迟时间
	public static String PARA_LOGINTERVAL="logInterval";//采集间隔
	public static String PARA_UPPERLIMIT="upperlimit";//温度上限
	public static String PARA_LOWERLIMIT="lowerlimit";//温度下限
	public static String PARA_STORAGERULE="storageRule";//存储规则
	public static String PARA_LOGFORM="logForm";//记录形式
	
	public static String DEVICE_NAME="deviceName";//设备名称
	public static String DEVICE_ADDRESS="deviceAddress";//设备地址
	
	public static String NAME="name";//标签名称
	public static String MAXTEMPERATURE="maxTemperature";//温度上限
	public static String MINTEMPERATURE="minTemperature";//温度下限
	public static String TEMPERATUREMAX="temperatureMax";//最大温度
	public static String TEMPERATUREMIN="temperatureMin";//最小温度
	public static String TEMPERATUREMEAN="temperatureMean";//平均温度
	public static String INTERVAL="interval";//频率
	public static String BATTERYVOLTAGE="batteryVoltage";//电量
	public static String TEMPERATURE_OUTPUT="Temperature";//检测度数
	public static String DATETIME_OUTPUT="DateTime";//时间
	public static String STATUS="STATUS";//状态，是否超出范围
	public static final int STATUS_NORMAL = 0;// 未曾超限
	public static final int STATUS_EXCEEDED_MIN = 1;// 曾经只超下限
	public static final int STATUS_EXCEEDED_MAX = 2;// 曾经只超上限
	public static final int STATUS_EXCEEDED_BOTH = 3;// 曾经超上限和下限
	public static String LIMITEXCEEDED="limitExceeded";//
	public static String ACTIVE="active";//
	public static String NUMMEASUREMENTS="numMeasurements";//
	public static String NUMEXCEEDED="numExceeded";//
	public static String USERNAME_OUTPUT="UserName";//昵称
	public static String USERCODE_INPUT="userCode";//账户
	public static String USERCODE_OUTPUT="UserCode";//账户
	public static String PASSWORD_INPUT="password";//密码
	public static String PASSWORD_OUTPUT="Password";//密码
	public static String ISADMIN_OUTPUT="IsAdmin";//是否管理员
	public static String ISSUCCESS="IsSuccess";//判断WebService是否正确返回
	public static String ERRORMESSAGE="ErrorMessage";//WebService错误提示
	public static String DATA="Data";//WebService内容
	public static String REMARK_OUTPUT="Remark";//备注
	public static String AUTHORITY_OUTPUT="Authority";//功能权限点
	public static String ADDRESS_OUTPUT="Address";//签收地址
	public static String ID_OUTPUT="Id";//记录ID
	public static String UID_OUTPUT="UID";//标签UID
	public static String ISSUEDATE_OUTPUT="IssueDate";//发货日期
	public static String DELIVER_OUTPUT="Deliver";//发货单位
	public static String RECEIVER_OUTPUT="Receiver";//接收单位
	public static String TRANSPORT_OUTPUT="transport";//运输单位
	public static String TRANKINGNO_OUTPUT="TrackingNo";//运单号
	public static String GOODSNAME_OUTPUT="GoodsName";//货物名称
	public static String GOODSQUANTITY_OUTPUT="GoodsQuantity";//货物数量
	public static String TRANSPORTDAY_OUTPUT="TransportDay";//运输天数
	public static String GOODSSTATE_OUTPUT="GoodsState";//货物(标签)状态 0: 已制卡 1: 已入库 2: 已出库 3: 已签收 4: 已拒收
	public static String INITDATE_OUTPUT="InitDate";//制卡日期
	public static String INTOSTORAGEDATE_OUTPUT="IntoStorageDate";//入库日期
	public static String OUTSTORGEDATE_OUTPUT="OutStorgeDate";//出库日期
	public static String SIGNDATE_OUTPUT="SignDate";//签收/拒收日期
	public static String DATE_OUTPUT = "Date";
	public static String SPECIFICATION_OUTPUT="Specification";//规格
	public static String BATCHNO_OUTPUT="BatchNo";//批号
	public static String EXPIRYDATE_OUTPUT="ExpiryDate";//失效日期
	public static String PRODUCEDATE_OUTPUT="ProduceDate";//生产日期
	public static String APPROVALNO_OUTPUT="ApprovalNo";//批准文号
	public static String PIATS="PIATS";//电子监管码
	public static String CURRENTUSER_INPUT="currentUser";//用户信息
	public static String UID_INPUT="uid";//
	public static String TAB_POSITION="TAB_POSITION";//控制药品详情展示哪个分页
	public static String TAGTEMPERATUREMAPPINT_OUT = "TagTemperatureMapping";
	public static String COLLECTTEMPERATURELIST_INPUT = "collectTempertureList";
	public static String CHECKFROMINTERNET = "checkFromInternet";
	public static String ANYTYPE = "anyType{}";
	
	public static String ONLINESTATUS = "onlinestatus";
	
	public static final int RESULT_WEB_SUCCESS=200;
	public static final int RESULT_WEB_FAILMSG=204;
	public static final int RESULT_WEB_ERROR=-1;
	public static final int ONLINE =1;
	public static final int OFFLINE=0;
	/*
	 * Nicklaus Ng
	 */
	// collection inactive
	public static final int INTERVAL_ZERO = 0;
	
	public static final int TAB_PROFUCT = 0;
	public static final int TAB_EXCEEDED = 1;
	public static final int TAB_DATA = 2;
	public static final int TAB_DIAGRAM = 3;
	
	public static final String NEWTAG = "GET_NEW_TAG";
	public static  String MKT = "MKT";
	public static  String STROAGERULE = "storageRule";
	public static  String LOGGINGFORM = "loggingForm";
 
	public static String DELAY="delay";//
    public static String DELAYTIME="delaytime";
    public static String LOGINTERVAL="loginterval";
    public static String UPPERLIMIT="upperlimit";
    public static String LOWERLIMIT="lowerlimit";
    public static String STORAGERULE="storagerule";
    public static String LOGFORM="logform";
	public static String HIGHTEMPDURATION="highTempDuration";
	public static String LOWTEMPDURATION="lowTempDuration";
	public static String ALLTEMPDURATION="allTempDuration";
	public static String MAXTEMPTIME="maxTempTime";
	public static String MINTEMPTIME="minTempTime";
	public static String STOPTIME="stopTime";
	public static String FIRSTLOGTIME="firstLogTime";
	public static String LASTLOGTIME="lastLogTime";
	public static String STARTTIME="startTime";
	
}