package com.example.h26demo;

import java.io.IOException;






import java.text.DecimalFormat;
import java.util.ArrayList;


import java.util.Date;
import java.util.List;

import com.jjoe64.graphview.CustomLabelFormatter;
import com.jjoe64.graphview.GraphViewSeries;
import com.jjoe64.graphview.LineGraphView;
import com.jjoe64.graphview.GraphView.GraphViewData;
import com.jjoe64.graphview.GraphView.LegendAlign;
import com.jjoe64.graphview.GraphViewSeries.GraphViewSeriesStyle;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentFilter.MalformedMimeTypeException;
import android.graphics.Color;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.IsoDep;
import android.nfc.tech.MifareClassic;
import android.nfc.tech.MifareUltralight;
import android.nfc.tech.Ndef;
import android.nfc.tech.NdefFormatable;
import android.nfc.tech.NfcA;
import android.nfc.tech.NfcB;
import android.nfc.tech.NfcF;
import android.nfc.tech.NfcV;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.provider.Settings;

@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
@SuppressLint("DefaultLocale")
public class MainActivity extends ActionBarActivity {
	private NfcA nfca;
	private static final String TAG = "debug";
	NfcAdapter mNfcAdapter;
	PendingIntent mNfcPendingIntent;
	IntentFilter[] mWriteTagFilters;
	IntentFilter[] mNdefExchangeFilters;
	private String[][] techListsArray;// ++
	Thermometer thermo ;
	private LinearLayout layout;
	private TextView tvTemperature,tvResult;
	private Button btnReadMCU,btnSetTemperatureParas,btnStartMeasurement,btnStopMeasurement,btnReadTemperature;
	private EditText etLength;
	private byte cntByteH=-1;
	private byte cntByteL=-1;
	private ArrayList<TagTemperature> mDatas = new ArrayList<TagTemperature>();
	private String uid;
	private ProductInfo mData;
	private byte[] read=new byte[16];
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		/*test*/
		byte[] rd={0x01,(byte) 0xF6,0x0A,0x2E,0x01,(byte) 0xF5,0x0A,(byte) 0xF4,0x01,(byte) 0xF4,0x0B,(byte) 0xDD,0x01,(byte) 0xF4,0x0B,0x00};
		String  rStr="01F60A2E01F50AF401F40BDD01F40B00";
		byte[] rd1=HexString2Bytes(rStr);
		double temperature=temperatureDo(rd1);
		initViews();
		initEvent();		
		tvTemperature.setText(String.valueOf(temperature));
		
		mNfcAdapter = NfcAdapter.getDefaultAdapter(this);// 设备注册
		if (mNfcAdapter == null) {// 判断设备是否可用
			toast("设备不支持nfc!");
			return;
		}
		if (!mNfcAdapter.isEnabled()) {
			Toast.makeText(this, "请在系统设置中先启用NFC功能！", Toast.LENGTH_SHORT).show();
			startActivity(new Intent(Settings.ACTION_NFC_SETTINGS));
			finish();
			return;
		}
		mNfcPendingIntent = PendingIntent.getActivity(this, 0, new Intent(this,
				getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);

		IntentFilter ndefDetected = new IntentFilter(
				NfcAdapter.ACTION_NDEF_DISCOVERED);
		try {
			ndefDetected.addDataType("*/*");// text/plain
		} catch (MalformedMimeTypeException e) {
		}

		IntentFilter td = new IntentFilter(NfcAdapter.ACTION_TAG_DISCOVERED);
		IntentFilter ttech = new IntentFilter(NfcAdapter.ACTION_TECH_DISCOVERED);
		mNdefExchangeFilters = new IntentFilter[] { ndefDetected, ttech, td };
		techListsArray = new String[][] {
				new String[] { NfcF.class.getName() },
				new String[] { NfcA.class.getName() },
				new String[] { NfcB.class.getName() },
				new String[] { NfcV.class.getName() },
				new String[] { Ndef.class.getName() },
				new String[] { NdefFormatable.class.getName() },		
				new String[] { IsoDep.class.getName() },
				new String[] { MifareClassic.class.getName() },
				new String[] { MifareUltralight.class.getName() } };
		setContent(false);
		
	}
	
	private void initViews() {
		layout = (LinearLayout) findViewById(R.id.loContent);
		tvResult=(TextView)findViewById(R.id.tvResult);
		tvTemperature=(TextView)findViewById(R.id.tvTemperature);
		
		btnReadMCU=(Button)findViewById(R.id.btnReadMCU);
		btnSetTemperatureParas=(Button)findViewById(R.id.btnSetTemperatureParas);
		btnStartMeasurement=(Button)findViewById(R.id.btnStartMeasurement);
		btnStopMeasurement=(Button)findViewById(R.id.btnStopMeasurement);
		btnReadTemperature=(Button)findViewById(R.id.btnReadTemperature);
		
		etLength=(EditText)findViewById(R.id.etLength);
		
	}
	
	private void initEvent() {
		btnReadMCU.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (nfca == null) {
					Toast.makeText(MainActivity.this, "请将标签靠近手机，再操作！", Toast.LENGTH_SHORT).show();
					return;
				}

				try {
					nfca.connect();	
					H26Tag h26tag=new H26Tag(nfca);
					byte[] rd=h26tag.readMCUState();
					cntByteH=rd[4];
					cntByteL=rd[5];
//					StringBuilder sb=new StringBuilder();
//					sb.append("Read Result:\n");
//					sb.append(Bytes2HexString(rd).toUpperCase()+"\n");
//					sb.append("CmdID");
					tvResult.setText("Read Result:\n"+Bytes2HexString(rd).toUpperCase());
				} catch (IOException e) {
					Toast.makeText(MainActivity.this, "NFC已断开或标签不在范围内！\n", Toast.LENGTH_SHORT).show();
//					System.out.println(e.getMessage().toString());
				}finally{
					try {
						nfca.close();
					} catch (IOException e) {				
						e.printStackTrace();
					}	
				}
			}
		});
		
		btnSetTemperatureParas.setOnClickListener(new OnClickListener() {	
			@Override
			public void onClick(View v) {
				if (nfca == null) {
					Toast.makeText(MainActivity.this, "请将标签靠近手机，再操作！", Toast.LENGTH_SHORT).show();
					return;
				}

				try {
					nfca.connect();	
					H26Tag h26tag=new H26Tag(nfca);
					byte[] data = new byte[2];
					data[0]=(byte)0x01;
					data[1]=(byte)0x01;
					
					byte[] rd=h26tag.setTemperaturePara(data);
//					StringBuilder sb=new StringBuilder();
//					sb.append("Read Result:\n");
//					sb.append(Bytes2HexString(rd).toUpperCase()+"\n");
//					sb.append("CmdID");
					cntByteH=rd[2];
					cntByteL=rd[3];
					tvResult.setText("Read Result:\n"+Bytes2HexString(rd).toUpperCase());
					mData.setInterval(1000*60);
				} catch (IOException e) {
					Toast.makeText(MainActivity.this, "NFC已断开或标签不在范围内！\n", Toast.LENGTH_SHORT).show();
//					System.out.println(e.getMessage().toString());
				}finally{
					try {
						nfca.close();
					} catch (IOException e) {				
						e.printStackTrace();
					}	
				}
			}
		});
		
		btnStartMeasurement.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (nfca == null) {
					Toast.makeText(MainActivity.this, "请将标签靠近手机，再操作！", Toast.LENGTH_SHORT).show();
					return;
				}

				try {
					nfca.connect();	
					H26Tag h26tag=new H26Tag(nfca);
					byte[] rd=h26tag.startTemperatureMeasurement();
//					StringBuilder sb=new StringBuilder();
//					sb.append("Read Result:\n");
//					sb.append(Bytes2HexString(rd).toUpperCase()+"\n");
//					sb.append("CmdID");
					tvResult.setText("Read Result:\n"+Bytes2HexString(rd).toUpperCase());
				} catch (IOException e) {
					Toast.makeText(MainActivity.this, "NFC已断开或标签不在范围内！\n", Toast.LENGTH_SHORT).show();
//					System.out.println(e.getMessage().toString());
				}finally{
					try {
						nfca.close();
					} catch (IOException e) {				
						e.printStackTrace();
					}	
				}
			}
		});
		
		btnStopMeasurement.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (nfca == null) {
					Toast.makeText(MainActivity.this, "请将标签靠近手机，再操作！", Toast.LENGTH_SHORT).show();
					return;
				}

				try {
					nfca.connect();	
					H26Tag h26tag=new H26Tag(nfca);
					byte[] rd=h26tag.stopTemperatureMeasurement();
//					StringBuilder sb=new StringBuilder();
//					sb.append("Read Result:\n");
//					sb.append(Bytes2HexString(rd).toUpperCase()+"\n");
//					sb.append("CmdID");
					tvResult.setText("Read Result:\n"+Bytes2HexString(rd).toUpperCase());
				} catch (IOException e) {
					Toast.makeText(MainActivity.this, "NFC已断开或标签不在范围内！\n", Toast.LENGTH_SHORT).show();
//					System.out.println(e.getMessage().toString());
				}finally{
					try {
						nfca.close();
					} catch (IOException e) {				
						e.printStackTrace();
					}	
				}
			}
		});
		
		btnReadTemperature.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (nfca == null) {
					Toast.makeText(MainActivity.this, "请将标签靠近手机，再操作！", Toast.LENGTH_SHORT).show();
					return;
				}
                if(etLength.getText().toString().equals("")){
                	Toast.makeText(MainActivity.this, "请输入要读取温度块的长度！", Toast.LENGTH_SHORT).show();
					return;	
                }
				try {
					nfca.connect();	
					H26Tag h26tag=new H26Tag(nfca);
					byte[] rdMCU=h26tag.readMCUState();
					cntByteH=rdMCU[4];
					cntByteL=rdMCU[5];
					byte[] data=new byte[2];
					int length=0;
					int count=((cntByteH & 0xFF <<8)|cntByteL)==0? Integer.parseInt(etLength.getText().toString()):((cntByteH & 0xFF <<8)|cntByteL);//;
					etLength.setText(String.valueOf(count));
					byte[] rd=new byte[count*4];
					byte[] temp=null;
					double[] temperatures=null;
					if(count>16)
					{
					  int cn=count/16;
					  int res=count%16;
					  byte[] rdd =null;
					  for(int i=0;i<cn;i++){						  	
						  length=0x10;
						  if(i<16){
							  data[0]=0x00;
							  data[1]=(byte)(0x10*i);
						  }else if(i==16){
							  data[0]=0x01;
							  data[1]=0x00;
						  }else if(i>16){
							  data[1]=(byte)(0x10*(i%16));
						  }
						  temp=h26tag.readTemperatures(data, length);	
						  rdd=new byte[temp.length-3];
						  System.arraycopy(temp, 2, rdd, 0, temp.length-3);
						  System.arraycopy(rdd, 0, rd, i*rdd.length, rdd.length);
					  }	
					  if(res!=0){
						  data[0]=cn>=16? (byte) (0x01*cn/16):(byte)0x00;
						  data[1]=(byte) (0x10*cn);
						  temp=h26tag.readTemperatures(data, res);	
//						  rdd=new byte[temp.length-3];
//						  System.arraycopy(temp, 2, rdd, 0, temp.length-3);
						  System.arraycopy(temp, 2, rd, cn*rdd.length, temp.length-3);  
					  }

					  tvResult.setText("Read Result:\n"+Bytes2HexString(rd).toUpperCase());
					}else{
						data[0]=0x00;
						data[1]=0x00;	
						length=count;
						temp=h26tag.readTemperatures(data, length);
						System.arraycopy(temp, 2, rd, 0, temp.length-3);	
						tvResult.setText("Read Result:\n"+Bytes2HexString(temp).toUpperCase());
					}
					temperatures=toDoTemperatures(rd,count);
					
					
//		********************测试数据*****************			
//					double[] temperatures= new double[10];
//					for (int i=0;i<10;i++){
//						temperatures[i]=Math.random()*20;
//					}
//					********************测试数据*****************	
					mData.setAllTemperature(temperatures);
					mData.setTemperatureMax(8.0);
					mData.setTemperatureMin(2.0);
					mData.setStartTime(new Date());
					mData.setInterval(5);
					// 当前温度
					mData.setTemperature(25.0);
					// 按照格式封装温度
					ArrayList<TagTemperature> mTemperatrues=new ArrayList<TagTemperature>();
					mTemperatrues=packageTagTemperatureInfo();
					// 温度列表保存到数据库
					TagTemperatureDao.getInstance(getApplicationContext()).insert(mTemperatrues);
					
//					StringBuilder sb=new StringBuilder();
//					sb.append("Read Result:\n");
//					sb.append(Bytes2HexString(rd).toUpperCase()+"\n");
//					sb.append("CmdID");
					
					mData.setBatteryVoltage(1.5);
					mData.setActive(false);
					// 标签上面没有数据
					mData.setNumMeasurements(0);
					mData.setNumExceeded(0);
					mData.setStorageRule("nomarl");
					mData.setLoggingForm("dense");
					mData.setStatus(Constants.STATUS_NORMAL);
					
					mData.setMaxTempTime(mData.getStartTime());
					mData.setMinTempTime(mData.getStartTime());
					mData.setHighTempDuration(0);
					mData.setLowTempDuration(0);
					mData.setAllTempDuration(0);
					mData.setStopTime(mData.getStartTime());
					mData.setLastLogTime(mData.getStartTime());
					mData.setFirstLogTime(mData.getStartTime());
					mData.setMKT(0.0);
					// 读取标签时的时间
					mData.setDate(new Date().getTime());
					// 标签基本资料保存到数据库
					ProductDao.getInstance(getApplicationContext()).insert(mData, false);
					
					mDatas.clear();
					mDatas.addAll(TagTemperatureDao.getInstance(
							getApplicationContext()).queryAll(uid));
					setContent(false);
				} catch (IOException e) {
					Toast.makeText(MainActivity.this, "NFC已断开或标签不在范围内！\n", Toast.LENGTH_SHORT).show();
//					System.out.println(e.getMessage().toString());
					e.printStackTrace();
				}finally{
					try {
						nfca.close();
					} catch (IOException e) {				
						e.printStackTrace();
					}	
				}
			}
		});
		
	}
	/**
	 * 将读取的温度字节转换为温度数据
	 * @param rd  读取温度的字节
	 * @param count 有多少条温度数据
	 * @return
	 */
	protected double[] toDoTemperatures(byte[] rd,int count) {
		double[] temperatures=new double[count];
		byte[] rdTemp=new byte[4];
		
		List<byte[]> listArray=getListByteArray(rd,4);
		for(int j=0;j<listArray.size();j++){
			rdTemp=listArray.get(j);	
			System.arraycopy(rdTemp, 0, read, 12, 4);//拷贝数组
			temperatures[j]=temperatureDo(read);				
		}		
		return temperatures;
	}
	
	/**
	 * tyy
	 * @param srcArray  要拆分的数组
	 * @param size   每一个拆分的数组的大小
	 * @return
	 */
	private static List<byte[]> getListByteArray(byte[] srcArray, int size) {
		List<byte[]> listArray = new ArrayList<byte[]>();
		// tyy 取整代表可以拆分的数组个数
		int arrayNum = srcArray.length / size;
		for (int i = 0; i < arrayNum; i++) {
			byte[] subArray = new byte[size];
			for (int j = 0; j < size; j++) {
				subArray[j] = srcArray[j + i * size];
			}
			listArray.add(subArray);
		}
		return listArray;
	}
	/**
	 * 封装温度信息
	 * @return
	 */
	private ArrayList<TagTemperature> packageTagTemperatureInfo() {

		ArrayList<TagTemperature> temperatureList = new ArrayList<TagTemperature>();
		int total = mData.getAllTemperature().length;
		
		for (int i = 0; i < total; i++) {
			
			TagTemperature tag = new TagTemperature();
			tag.setTagId(mData.getUid());
					
			tag.setDate(new Date(mData.getStartTime().getTime()
					+ mData.getInterval() * 1000*i
					));				
			
			tag.setTemperature(mData.getAllTemperature()[i]);
			tag.setTemperatureMax(mData.getTemperatureMax());
			tag.setTemperatureMin(mData.getTemperatureMin());
			tag.setExceededStatus(Constants.STATUS_NORMAL);
			if (tag.getTemperature() > tag.getTemperatureMax()) {
				tag.setExceededStatus(tag.getExceededStatus() | Constants.STATUS_EXCEEDED_MAX);
			} else if (tag.getTemperature() < tag.getTemperatureMin()){
				tag.setExceededStatus(tag.getExceededStatus() | Constants.STATUS_EXCEEDED_MIN);
			} else {
			}
			temperatureList.add(tag);
		}
		return temperatureList;
	}

	/**
	 * 
	 */
	@Override
	public void onResume() {
		super.onResume();
		mNfcAdapter.enableForegroundDispatch(this, mNfcPendingIntent,
				null, null);// ++
	}
	/**
	 * 
	 */
	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		handleIntent(intent);
	}
	/**
	 * 
	 * @param intent
	 */
	protected void handleIntent(Intent intent) {
		Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
		if (tag == null) {
			return;
		}
		
		String uidString = new String();
		byte[] uid = tag.getId();
	    for (int index = uid.length - 1; index >= 0; --index) {
			uidString += String.format("%02x", uid[index]);
		}
	    uidString=uidString.toUpperCase();
        this.uid=uidString;
        mData = ProductDao.getInstance(getApplicationContext())
				.query(uidString);
		if (mData == null) {
			mData = new ProductInfo();
			mData.setUid(uidString);
			mData.setGoodsName("未知");
		}
	    nfca = android.nfc.tech.NfcA.get(tag);	

		if (nfca == null) {
			return;
		}

		try {
			nfca.connect();	
			toast("nfc已连接！");
			H26Tag h26tag=new H26Tag(nfca);
			byte[] rd=h26tag.readSingleBlock(0);
			read =rd;
			double temperature=temperatureDo(rd);
			tvTemperature.setText(String.valueOf(DataUtil.formatTemperature(temperature)));
			tvResult.setText("Read Result:\n"+Bytes2HexString(rd).toUpperCase());
		} catch (IOException e) {
			System.out.println(e.getMessage().toString());
		}finally{
			try {
				nfca.close();
			} catch (IOException e) {				
				e.printStackTrace();
			}	
		}
	}
	
	private void setContent(boolean enable){
		layout.removeAllViews();
		if(mDatas.isEmpty())return;
		/*
		 * use Date as x axis label
		 */
		GraphViewData[] dataTemperature = new GraphViewData[mDatas.size()];		
		GraphViewData[] dataTemperatureMax = new GraphViewData[mDatas.size()];
		GraphViewData[] dataTemperatureMin = new GraphViewData[mDatas.size()];
		for (int count = 0; count < mDatas.size(); count++) {
			 
			dataTemperature[count] = new GraphViewData(count, mDatas.get(count)
					.getTemperature());			
			dataTemperatureMax[count] = new GraphViewData(count, mDatas.get(
					count).getTemperatureMax());
			dataTemperatureMin[count] = new GraphViewData(count, mDatas.get(
					count).getTemperatureMin());
		}
		GraphViewSeries exampleSeriesTemperature = new GraphViewSeries(
				"温度趋势图",
				new GraphViewSeriesStyle(Color.rgb(137, 190, 34), 3),
				dataTemperature);		

		GraphViewSeries exampleSeriesTemperatureMax = new GraphViewSeries(
				DataUtil.formatTemperature(Integer.parseInt(new DecimalFormat("0").format(mDatas.get(0).getTemperatureMax()))), new GraphViewSeriesStyle(
						Color.rgb(255, 0, 0), 5), dataTemperatureMax);
		GraphViewSeries exampleSeriesTemperatureMin = new GraphViewSeries(
				DataUtil.formatTemperature(Integer.parseInt(new DecimalFormat("0").format(mDatas.get(0).getTemperatureMin()))), new GraphViewSeriesStyle(
						Color.rgb(10, 35, 58), 5), dataTemperatureMin);
		//LineGraphView graphView = new LineGraphView(getActivity(), "温度曲线图");
		LineGraphView graphView = new LineGraphView(MainActivity.this,"温度曲线图");
		// ((LineGraphView) graphView).setDrawBackground(true);
		graphView.addSeries(exampleSeriesTemperature); // data
		graphView.addSeries(exampleSeriesTemperatureMax); // data
		graphView.addSeries(exampleSeriesTemperatureMin); // data
		/*
		 * date as label formatter
		 */
		graphView.setCustomLabelFormatter(new CustomLabelFormatter() {
			@Override
			public String formatLabel(double value, boolean isValueX) {
				if (isValueX) {
					if (value >= mDatas.size()) {
						return null;
					} else {
						return TimeUtil.getTimeWithoutYear(mDatas.get((int) value)
								.getDate());// 底部时间
					}
				}
				return null; // let graphview generate Y-axis label for us
			}
		});
		// set legend #89BE22,#2B7EDE,#0A233A
		graphView.setShowLegend(true);
		graphView.setLegendAlign(LegendAlign.TOP);// 标识是在哪个位置
		graphView.getGraphViewStyle().setLegendBorder(20);
		graphView.getGraphViewStyle().setLegendSpacing(30);
		graphView.getGraphViewStyle().setLegendWidth(150);// 标识背景长度

		graphView.getGraphViewStyle().setNumVerticalLabels(10);
		// graphView.setVerticalLabels(new String[] {"50", "40", "30",
		// "20","10","0"});
		graphView.getGraphViewStyle().setNumHorizontalLabels(3);

		int len = mDatas.size();
		if (len <= 10) {
			graphView.setViewPort(0, 0);// 前者与从第几页开始显示，后者与视图一页有多少数据有关
			graphView.setScrollable(false);// 可拖动，跟上面关联			
		} else {
			graphView.setViewPort(0, 10);
			graphView.setScrollable(true);// 可拖动，跟上面关联			
		}
		//是否画出小圆圈
//		graphView.setDrawDataPoints(true);
		if( mDatas.size()<20){
			graphView.setDrawDataPoints(true);
		}else{
			graphView.setDrawDataPoints(false);
		}
		//
		graphView.setScalable(true);// 可缩放
		layout.addView(graphView);
	}
	/**
	 * 处理读取出来的温度数据
	 * @param rd
	 */
	private double temperatureDo(byte[] rd) {
		double HW7,HW6,HW5,HW4,HW3,HW2,HW1,HW0;
		HW7=(int)(((rd[0]&0xFF)<<8) |(rd[1]&0xFF));
		HW6=(int)(((rd[2]&0xFF)<<8) |(rd[3]&0xFF));
		HW5=(int)(((rd[4]&0xFF)<<8) |(rd[5]&0xFF));
		HW4=(int)(((rd[6]&0xFF)<<8) |(rd[7]&0xFF));
		HW3=(int)(((rd[8]&0xFF)<<8) |(rd[9]&0xFF));
		HW2=(int)(((rd[10]&0xFF)<<8) |(rd[11]&0xFF));
		HW1=(int)(((rd[12]&0xFF)<<8) |(rd[13]&0xFF));
		HW0=(int)(((rd[14]&0xFF)<<8) |(rd[15]&0xFF));
		double R40,R38,R36,Rx,Km,Kp;
		R40=HW6/HW7;
		R38=HW4/HW5;
		R36=HW2/HW3;
		Rx=HW0/HW1;

		Km=(R38-R40)/20;
		Kp=(R36-R38)/20;
		
		double R=R38,T=38.0;
		if(R38>Rx){
			R=R-Km;
		    while(true){
	    	    if(R<Rx){
					return T;
				}else{
					T=T+0.1;
					R=R-Km;							
				}
    	   }
		}else if(R38<Rx){
			R=R+Kp;
		    while(true){
	    		if(R>Rx){		    
					return T;
				}else{
					T=T-0.1;
					R=R+Kp;							
				}
            }
		}else{
			return T;
		}
	}
	/** 
     * Convert hex string to byte[] 
     * @param hexString the hex string 
     * @return byte[] 
     */  
    public static byte[] HexString2Bytes(String hexString) {  
        if (hexString == null || hexString.equals("")) {  
            return null;  
        }  
        hexString = hexString.toUpperCase();  
        int length = hexString.length() / 2;  
        char[] hexChars = hexString.toCharArray();  
        byte[] d = new byte[length];  
        for (int i = 0; i < length; i++) {  
            int pos = i * 2;  
            d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));  
        }  
        return d;  
    }  
    
    /** 
     * Convert char to byte 
     * @param c char 
     * @return byte 
     */  
     private static byte charToByte(char c) {  
        return (byte) "0123456789ABCDEF".indexOf(c);  
    } 
   
     /**
      * byte[]转16进制字符串
      * @param data
      * @return
      */
     public static String Bytes2HexString(byte[] data)
     {
     	StringBuilder stringBuilder = new StringBuilder("");  
         if (data == null || data.length <= 0) {  
             return null;  
         }  
         for (int i = 0; i < data.length; i++) {  
             int v = data[i] & 0xFF;  
             String hv = Integer.toHexString(v);  
             if (hv.length() < 2) {  
                 stringBuilder.append(0);  
             }  
             stringBuilder.append(hv);  
         }      
         return stringBuilder.toString();  
     }
	/**
	 * 
	 * @param text
	 */
	private void toast(String text) {
		Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			Intent intent = new Intent(MainActivity.this, AboutAppAct.class);
//			intent.putExtra("TAG_STATE", true);		
			startActivity(intent);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			return rootView;
		}
	}

}
