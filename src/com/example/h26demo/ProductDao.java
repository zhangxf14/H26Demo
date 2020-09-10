package com.example.h26demo;

import java.util.ArrayList;
import java.util.Date;



import com.example.h26demo.db.DBManager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

/**
 * 药品信息数据库操作
 * @author Sai
 *
 */
public class ProductDao {
	private static ProductDao instance;
	private Context context;
	private static String table = Constants.TB_PRODUCT;

	public ProductDao(Context context) {
		this.context = context;
	}

	public static ProductDao getInstance(Context context) {
		if (instance == null) {
			instance = new ProductDao(context);
		}
		return instance;
	}

	public ContentValues objectToValue(ProductInfo data, boolean webData) {
		ContentValues values = new ContentValues();
		values.put(Constants.UID_OUTPUT, data.getUid());
		values.put(Constants.USERCODE_OUTPUT, "admin");
		//区别是写标签数据还是网络数据
		//网络数据
		if (webData) {
			values.put(Constants.ID_OUTPUT, data.getId());
			values.put(Constants.TRANKINGNO_OUTPUT, data.getTrackingNo());
			values.put(Constants.ISSUEDATE_OUTPUT, data.getIssueDate());
			values.put(Constants.DELIVER_OUTPUT, data.getDeliver());
			values.put(Constants.RECEIVER_OUTPUT, data.getReceiver());
			values.put(Constants.TRANSPORT_OUTPUT, data.getTransport());
			values.put(Constants.GOODSNAME_OUTPUT, data.getGoodsName());
			values.put(Constants.GOODSQUANTITY_OUTPUT, data.getGoodsQuantity());
			values.put(Constants.TRANSPORTDAY_OUTPUT, data.getTransportDay());
			values.put(Constants.GOODSSTATE_OUTPUT, data.getGoodsState());
			values.put(Constants.INITDATE_OUTPUT, data.getInitDate());
			values.put(Constants.INTOSTORAGEDATE_OUTPUT,data.getIntoStorageDate());
			values.put(Constants.OUTSTORGEDATE_OUTPUT, data.getOutStorgeDate());
			values.put(Constants.SIGNDATE_OUTPUT, data.getSignDate());
			values.put(Constants.SPECIFICATION_OUTPUT, data.getSpecification());
			values.put(Constants.BATCHNO_OUTPUT, data.getBatchNo());
			values.put(Constants.EXPIRYDATE_OUTPUT, data.getExpiryDate());
			values.put(Constants.PRODUCEDATE_OUTPUT, data.getProduceDate());
			values.put(Constants.APPROVALNO_OUTPUT, data.getApprovalNo());
			values.put(Constants.PIATS, data.getPIATS());
//			values.put(Constants.DATETIME_OUTPUT, data.getDate());
		} else {//标签数据
			values.put(Constants.GOODSNAME_OUTPUT,data.getGoodsName());
			values.put(Constants.SIGNDATE_OUTPUT, data.getSignDate());
			values.put(Constants.DATETIME_OUTPUT, data.getDate());
			
			values.put(Constants.INTERVAL, data.getInterval());
			values.put(Constants.MAXTEMPERATURE, data.getTemperatureMax());
			values.put(Constants.MINTEMPERATURE, data.getTemperatureMin());
			values.put(Constants.TEMPERATURE_OUTPUT, data.getTemperature());
			values.put(Constants.BATTERYVOLTAGE, data.getBatteryVoltage());
			values.put(Constants.STATUS, data.getStatus());
			values.put(Constants.ACTIVE, String.valueOf(data.isActive()));
			values.put(Constants.NUMMEASUREMENTS, data.getNumMeasurements());
			values.put(Constants.NUMEXCEEDED, data.getNumExceeded());
			values.put(Constants.TEMPERATUREMAX, data.getMaxTemperature());
			values.put(Constants.TEMPERATUREMIN, data.getMinTemperature());
			values.put(Constants.TEMPERATUREMEAN, data.getMeanTemperature());
			values.put(Constants.MKT, data.getMKT());
			values.put(Constants.STORAGERULE, data.getStorageRule());
			values.put(Constants.LOGGINGFORM, data.getLoggingForm());
			values.put(Constants.HIGHTEMPDURATION, data.getHighTempDuration());
			values.put(Constants.LOWTEMPDURATION, data.getLowTempDuration());
			values.put(Constants.ALLTEMPDURATION, data.getAllTempDuration());
			values.put(Constants.MAXTEMPTIME, data.getMaxTempTime().getTime());
			values.put(Constants.MINTEMPTIME, data.getMinTempTime().getTime());
			values.put(Constants.STOPTIME, data.getStopTime().getTime());
			values.put(Constants.FIRSTLOGTIME, data.getFirstLogTime().getTime());
			values.put(Constants.LASTLOGTIME, data.getLastLogTime().getTime());
			values.put(Constants.STARTTIME, data.getStartTime().getTime());
		}
		return values;
	}

	public ProductInfo cursorToValue(Cursor mCursor) {
		ProductInfo data = new ProductInfo();
		data.setUid(mCursor.getString(mCursor
				.getColumnIndex(Constants.UID_OUTPUT)));
		data.setId(mCursor.getString(mCursor
				.getColumnIndex(Constants.ID_OUTPUT)));
		data.setTrackingNo(mCursor.getString(mCursor
				.getColumnIndex(Constants.TRANKINGNO_OUTPUT)));
		data.setIssueDate(mCursor.getLong(mCursor
				.getColumnIndex(Constants.ISSUEDATE_OUTPUT)));
		data.setDeliver(mCursor.getString(mCursor
				.getColumnIndex(Constants.DELIVER_OUTPUT)));
		data.setReceiver(mCursor.getString(mCursor
				.getColumnIndex(Constants.RECEIVER_OUTPUT)));
		data.setTransport(mCursor.getString(mCursor
				.getColumnIndex(Constants.TRANSPORT_OUTPUT)));
		data.setGoodsName(mCursor.getString(mCursor
				.getColumnIndex(Constants.GOODSNAME_OUTPUT)));
		data.setGoodsQuantity(mCursor.getInt(mCursor
				.getColumnIndex(Constants.GOODSQUANTITY_OUTPUT)));
		data.setTransportDay(mCursor.getInt(mCursor
				.getColumnIndex(Constants.TRANSPORTDAY_OUTPUT)));
		data.setGoodsState(mCursor.getInt(mCursor
				.getColumnIndex(Constants.GOODSSTATE_OUTPUT)));
		data.setInitDate(mCursor.getLong(mCursor
				.getColumnIndex(Constants.INITDATE_OUTPUT)));
		data.setIntoStorageDate(mCursor.getLong(mCursor
				.getColumnIndex(Constants.INTOSTORAGEDATE_OUTPUT)));
		data.setOutStorgeDate(mCursor.getLong(mCursor
				.getColumnIndex(Constants.OUTSTORGEDATE_OUTPUT)));
		data.setSignDate(mCursor.getLong(mCursor
				.getColumnIndex(Constants.SIGNDATE_OUTPUT)));
		data.setSpecification(mCursor.getString(mCursor
				.getColumnIndex(Constants.SPECIFICATION_OUTPUT)));
		data.setBatchNo(mCursor.getString(mCursor
				.getColumnIndex(Constants.BATCHNO_OUTPUT)));
		data.setExpiryDate(mCursor.getLong(mCursor
				.getColumnIndex(Constants.EXPIRYDATE_OUTPUT)));
		data.setProduceDate(mCursor.getLong(mCursor
				.getColumnIndex(Constants.PRODUCEDATE_OUTPUT)));
		data.setApprovalNo(mCursor.getString(mCursor
				.getColumnIndex(Constants.APPROVALNO_OUTPUT)));
		data.setPIATS(mCursor.getString(mCursor.getColumnIndex(Constants.PIATS)));
		data.setDate(mCursor.getLong(mCursor
				.getColumnIndex(Constants.DATETIME_OUTPUT)));
		data.setInterval(mCursor.getInt(mCursor
				.getColumnIndex(Constants.INTERVAL)));
		data.setTemperatureMax(mCursor.getDouble(mCursor
				.getColumnIndex(Constants.MAXTEMPERATURE)));
		data.setTemperatureMin(mCursor.getDouble(mCursor
				.getColumnIndex(Constants.MINTEMPERATURE)));
		data.setTemperature(mCursor.getDouble(mCursor
				.getColumnIndex(Constants.TEMPERATURE_OUTPUT)));
		data.setBatteryVoltage(mCursor.getDouble(mCursor
				.getColumnIndex(Constants.BATTERYVOLTAGE)));
		data.setStatus(mCursor.getInt(mCursor.getColumnIndex(Constants.STATUS)));
		data.setActive(Boolean.valueOf(mCursor.getString(mCursor
				.getColumnIndex(Constants.ACTIVE))));
		data.setNumMeasurements(mCursor.getInt(mCursor
				.getColumnIndex(Constants.NUMMEASUREMENTS)));
		data.setNumExceeded(mCursor.getInt(mCursor
				.getColumnIndex(Constants.NUMEXCEEDED)));
		data.setMaxTemperature(mCursor.getDouble(mCursor
				.getColumnIndex(Constants.TEMPERATUREMAX)));
		data.setMinTemperature(mCursor.getDouble(mCursor
				.getColumnIndex(Constants.TEMPERATUREMIN)));
		data.setMeanTemperature(mCursor.getDouble(mCursor
				.getColumnIndex(Constants.TEMPERATUREMEAN)));
		data.setStorageRule(mCursor.getString(mCursor
				.getColumnIndex(Constants.STROAGERULE)));
		data.setLoggingForm(mCursor.getString(mCursor
				.getColumnIndex(Constants.LOGGINGFORM)));
		data.setMKT(mCursor.getDouble(mCursor
				.getColumnIndex(Constants.MKT)));
		data.setHighTempDuration(mCursor.getLong(mCursor
				.getColumnIndex(Constants.HIGHTEMPDURATION)));
		data.setLowTempDuration(mCursor.getLong(mCursor
				.getColumnIndex(Constants.LOWTEMPDURATION)));
		data.setAllTempDuration(mCursor.getLong(mCursor
				.getColumnIndex(Constants.ALLTEMPDURATION)));
		data.setMaxTempTime(new Date(mCursor.getLong(mCursor
				.getColumnIndex(Constants.MAXTEMPTIME))));
		data.setMinTempTime(new Date(mCursor.getLong(mCursor
				.getColumnIndex(Constants.MINTEMPTIME))));
		data.setStopTime(new Date(mCursor.getLong(mCursor
				.getColumnIndex(Constants.STOPTIME))));
		data.setFirstLogTime(new Date(mCursor.getLong(mCursor
				.getColumnIndex(Constants.FIRSTLOGTIME))));
		data.setLastLogTime(new Date(mCursor.getLong(mCursor
				.getColumnIndex(Constants.LASTLOGTIME))));
		data.setStartTime(new Date(mCursor.getLong(mCursor
				.getColumnIndex(Constants.STARTTIME))));
		return data;
	}

	public Long insert(ProductInfo data, boolean webData) {
		ProductInfo tempInfo=query(data.getUid());
		if(tempInfo!=null){
			String selection = Constants.UID_OUTPUT + "=? AND "
					+ Constants.USERCODE_OUTPUT + "=?";//关联到用户的usercode
			String[] selectionArgs = { data.getUid(), "admin" };
			return (long) DBManager.getInstance(context).update(table, objectToValue(data, webData), selection, selectionArgs);
		}
		return DBManager.getInstance(context)
				.insert(table, objectToValue(data, false));
	}

	public ArrayList<ProductInfo> queryAll() {
		String selection =Constants.USERCODE_OUTPUT + "=?";//关联到用户的usercode
		String[] selectionArgs = { "admin" };
		ArrayList<ProductInfo> mDatas = new ArrayList<ProductInfo>();
		Cursor mCursor = DBManager.getInstance(context).query(table, null,
				selection, selectionArgs, null, null, null);
		while (mCursor.moveToNext()) {
			mDatas.add(cursorToValue(mCursor));
		}
		mCursor.close();
		DBManager.getInstance(context).closeDatabase();
		return mDatas;
	}

	public ProductInfo query(String uid) {
		String selection = Constants.UID_OUTPUT + "=? AND "
				+ Constants.USERCODE_OUTPUT + "=?";//关联到用户的usercode
		String[] selectionArgs = { uid, "admin" };
		ProductInfo mData = null;
		Cursor mCursor = DBManager.getInstance(context).query(table, null,
				selection, selectionArgs, null, null, null);
		while (mCursor.moveToNext()) {
			mData = new ProductInfo();
			mData = cursorToValue(mCursor);
		}
		mCursor.close();
		DBManager.getInstance(context).closeDatabase();
		return mData;
	}

	public ArrayList<ProductInfo> search(String key) {
//		String selection = Constants.GOODSNAME_OUTPUT + " like ? AND "
//				+ Constants.USERCODE_OUTPUT + "=?";//关联到用户的usercode
		String selection = Constants.TRANKINGNO_OUTPUT + " like ? AND "
				+ Constants.USERCODE_OUTPUT + "=?";//关联到用户的usercode
		String[] selectionArgs = { "%" + key + "%",
				"admin" };
		ArrayList<ProductInfo> mDatas = new ArrayList<ProductInfo>();
		Cursor mCursor = DBManager.getInstance(context).query(table, null,
				selection, selectionArgs, null, null, null);
		while (mCursor.moveToNext()) {
			mDatas.add(cursorToValue(mCursor));
		}
		mCursor.close();
		DBManager.getInstance(context).closeDatabase();
		return mDatas;
	}
}
