package com.example.h26demo;

import java.util.ArrayList;
import java.util.Date;



import com.example.h26demo.db.DBManager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
/**
 * 对应标签的温度集合操作
 * @author Sai
 *
 */
public class TagTemperatureDao {
	private static TagTemperatureDao instance;
	private Context context;
	private static String table = Constants.TB_TEMPERATURE;

	public TagTemperatureDao(Context context) {
		this.context = context;
	}

	public static TagTemperatureDao getInstance(Context context) {
		if (instance == null) {
			instance = new TagTemperatureDao(context);
		}
		return instance;
	}
	public ContentValues objectToValue(TagTemperature data){
		ContentValues values=new ContentValues();
		values.put(Constants.UID_OUTPUT, data.getTagId());
		values.put(Constants.TEMPERATURE_OUTPUT, data.getTemperature());
		values.put(Constants.MAXTEMPERATURE, data.getTemperatureMax());
		values.put(Constants.MINTEMPERATURE, data.getTemperatureMin());
		values.put(Constants.DATETIME_OUTPUT, data.getDate().getTime());
		values.put(Constants.STATUS, data.getExceededStatus());
		return values;
	}
	public TagTemperature cursorToValue(Cursor mCursor){
		TagTemperature data = new TagTemperature();
		data.setTagId(mCursor.getString(mCursor.getColumnIndex(Constants.UID_OUTPUT)));
		data.setTemperature(mCursor.getDouble(mCursor.getColumnIndex(Constants.TEMPERATURE_OUTPUT)));
		data.setTemperatureMax(mCursor.getDouble(mCursor.getColumnIndex(Constants.MAXTEMPERATURE)));
		data.setTemperatureMin(mCursor.getDouble(mCursor.getColumnIndex(Constants.MINTEMPERATURE)));
		data.setDate(new Date(mCursor.getLong(mCursor.getColumnIndex(Constants.DATETIME_OUTPUT))));
		data.setExceededStatus(mCursor.getInt(mCursor.getColumnIndex(Constants.STATUS)));
		return data;
	}
	public int insert(ArrayList<TagTemperature> datas) {
		if(datas.isEmpty())return 0;
		//先删除以前的数据
		String selection=Constants.UID_OUTPUT+"=?";
		String[] selectionArgs={datas.get(0).getTagId()};
		DBManager.getInstance(context).delete(table, selection, selectionArgs);
		//插入数据
		ArrayList<ContentValues> values=new ArrayList<ContentValues>();
		for(TagTemperature data:datas){
			values.add(objectToValue(data));
		}
		return DBManager.getInstance(context).insert(table, values);
	}
	
	public void delete(String uid) {		
	   //先删除以前的数据
		String selection=Constants.UID_OUTPUT+"=?";
		String[] selectionArgs={uid};
		DBManager.getInstance(context).delete(table, selection, selectionArgs);	
		
	}
	
	public ArrayList<TagTemperature> queryAll(String uid){
		ArrayList<TagTemperature> mDatas=new ArrayList<TagTemperature>();
		String selection=Constants.UID_OUTPUT+"=?";
		String[] selectionArgs={uid};
		Cursor mCursor = DBManager.getInstance(context).query(table, null, selection,
				selectionArgs, null, null, Constants.DATETIME_OUTPUT+" ASC");
		while (mCursor.moveToNext()) {
			TagTemperature data = cursorToValue(mCursor);
			mDatas.add(data);
		}
		mCursor.close();
		DBManager.getInstance(context).closeDatabase();
		return mDatas;
	}
	public ArrayList<TagTemperature> queryAll(String uid,String orderBy){
		ArrayList<TagTemperature> mDatas=new ArrayList<TagTemperature>();
		String selection=Constants.UID_OUTPUT+"=?";
		String[] selectionArgs={uid};
		Cursor mCursor = DBManager.getInstance(context).query(table, null, selection,
				selectionArgs, null, null, Constants.DATETIME_OUTPUT+" "+orderBy);
		while (mCursor.moveToNext()) {
			TagTemperature data = cursorToValue(mCursor);
			mDatas.add(data);
		}
		mCursor.close();
		DBManager.getInstance(context).closeDatabase();
		return mDatas;
	}
	public ArrayList<TagTemperature> queryExceededAll(String uid){
		ArrayList<TagTemperature> mDatas=new ArrayList<TagTemperature>();
		String selection=Constants.UID_OUTPUT+"=? AND "+Constants.STATUS+" !=?";
		String[] selectionArgs={uid,String.valueOf(Constants.STATUS_NORMAL)};
		Cursor mCursor = DBManager.getInstance(context).query(table, null, selection,
				selectionArgs, null, null, Constants.DATETIME_OUTPUT+" desc");
		while (mCursor.moveToNext()) {
			TagTemperature data = cursorToValue(mCursor);
			mDatas.add(data);
		}
		mCursor.close();
		DBManager.getInstance(context).closeDatabase();
		return mDatas;
	}
	public long queryMaxTime(String uid){
		long time=0;
		String[] selectionArgs={uid};
		String sql="select max("+Constants.DATETIME_OUTPUT+") from "+table +" where "+Constants.UID_OUTPUT+"=?";
		Cursor mCursor = DBManager.getInstance(context).rawQuery(sql, selectionArgs);
		while (mCursor.moveToNext()) {
			time=mCursor.getLong(0);
		}
		mCursor.close();
		DBManager.getInstance(context).closeDatabase();
		return time;
	}
	public long queryMinTime(String uid){
		long time=0;
		String[] selectionArgs={uid};
		String sql="select min("+Constants.DATETIME_OUTPUT+") from "+table +" where "+Constants.UID_OUTPUT+"=?";
		Cursor mCursor = DBManager.getInstance(context).rawQuery(sql, selectionArgs);
		while (mCursor.moveToNext()) {
			time=mCursor.getLong(0);
		}
		mCursor.close();
		DBManager.getInstance(context).closeDatabase();
		return time;
	}
	public ArrayList<TagTemperature> queryBetweenTime(String uid,long minTime,long maxTime){
		ArrayList<TagTemperature> mDatas=new ArrayList<TagTemperature>();
		String selection=Constants.UID_OUTPUT+"=? AND ("+Constants.DATETIME_OUTPUT+" between ? AND ?)";
		String[] selectionArgs={uid,String.valueOf(minTime),String.valueOf(maxTime)};
		Cursor mCursor = DBManager.getInstance(context).query(table, null, selection,
				selectionArgs, null, null, Constants.DATETIME_OUTPUT+" ASC");
		while (mCursor.moveToNext()) {
			TagTemperature data = cursorToValue(mCursor);
			mDatas.add(data);
		}
		mCursor.close();
		DBManager.getInstance(context).closeDatabase();
		return mDatas;
	}
	public ArrayList<TagTemperature> queryBetweenCount(String uid,int minCount,int maxCount){
		ArrayList<TagTemperature> mDatas=new ArrayList<TagTemperature>();
		String selection=Constants.UID_OUTPUT+"=?";
		String[] selectionArgs={uid};
		Cursor mCursor = DBManager.getInstance(context).query(table, null, selection,
				selectionArgs, null, null, Constants.DATETIME_OUTPUT+" ASC",minCount+","+maxCount);
		while (mCursor.moveToNext()) {
			TagTemperature data = cursorToValue(mCursor);
			mDatas.add(data);
		}
		mCursor.close();
		DBManager.getInstance(context).closeDatabase();
		return mDatas;
	}
}
