package com.example.h26demo;

import java.io.IOException;

import android.nfc.tech.NfcA;
import android.nfc.tech.NfcV;
/**
 * H26 NFC tag
 * @author Administrator
 *
 */
public class H26Tag {
	/** ISO14443 optional read single block command command code. */
	static final byte READ_SINGLE_BLOCK_CC = (byte) 0x30;
	
	/** ISO14443 optional 读 MCU 状态 command command code. */
	static final byte READ_MCU_STATE_CC = (byte) 0xA1;
	/** ISO14443 optional 配置温度测试参数 command command code. */
	static final byte SET_TEMPERATURE_PARA_CC = (byte) 0xA2;
	/** ISO14443 optional 开始循环温度测量 command command code. */
	static final byte START_TEMPERATURE_MEASUREMENT_CC = (byte) 0xA3;
	/** ISO14443 optional 停止循环温度测量 command command code. */
	static final byte STOP_TEMPERATURE_MEASUREMENT_CC = (byte) 0xA4;
	/** ISO14443 optional 读取温度数据 command command code. */
	static final byte READ_TEMPERATURE_CC = (byte) 0xA5;
	
	
	/** ISO14443 reply error flag. */
	static final byte ISO14443_ERROR_FLAG = 0x01;
	
	static final String TAG = "H26Tag";
	protected NfcA nfca;
	
	public H26Tag(NfcA nfca) {
		super();
		this.nfca = nfca;
	}
	/**
	 * ISO14443 read single block command.
	 * 
	 * @param blockNumber Block number of the block to read.
	 * @return Content of tag memory block \a blockNumber.
	 * @throws IOException
	 */
	public byte[] readSingleBlock(int blockNumber) throws IOException {
		if ((blockNumber < 0) || (blockNumber > 255))
			throw new IllegalArgumentException("block number must be within 0-255");
		byte[] parameter = new byte[1];
		parameter[0] = (byte) (blockNumber & 0xFF);
		byte result[] = transceive(READ_SINGLE_BLOCK_CC, parameter);
		return result;
	}
	/**
	 * 读取MCU状态
	 * @return
	 * @throws IOException
	 */
	public byte[] readMCUState() throws IOException {
		
		byte[] parameter = new byte[1];		
		parameter[0]=0x00;
		byte result[] = transceive(READ_MCU_STATE_CC, parameter);
		return result;
	}
	/**
	 * 配置温度测试参数
	 * @param length
	 * @param data
	 * @return
	 * @throws IOException
	 */
	public byte[] setTemperaturePara(byte[] data) throws IOException {
			
			byte[] parameter = new byte[3];
			parameter[0] = (byte) (2 & 0xFF);
			parameter[1] =data[0];
			parameter[2] =data[1];
			byte result[] = transceive(SET_TEMPERATURE_PARA_CC, parameter);
			return result;
	}
	/**
	 * 开始循环温度测量
	 * @return
	 * @throws IOException
	 */
	public byte[] startTemperatureMeasurement() throws IOException {
		
		byte[] parameter = new byte[1];		
		parameter[0]=0x00;
		byte result[] = transceive(START_TEMPERATURE_MEASUREMENT_CC, parameter);
		return result;
	}
	/**
	 * 停止循环温度测量
	 * @return
	 * @throws IOException
	 */
	public byte[] stopTemperatureMeasurement() throws IOException {
		
		byte[] parameter = new byte[1];	
		parameter[0]=0x00;
		byte result[] = transceive(STOP_TEMPERATURE_MEASUREMENT_CC, parameter);
		return result;
	}
	/**
	 * 读取温度数据
	 * @param length
	 * @param data
	 * @return 
	 * @throws IOException
	 */
	public byte[] readTemperatures(byte[] data,int length) throws IOException {
		
		byte[] parameter = new byte[4];
		parameter[0] = (byte) (3 & 0xFF);
		parameter[1] =data[0];
		parameter[2] =data[1];
		parameter[3] =(byte)(length & 0xFF);
		byte result[] = transceive(READ_TEMPERATURE_CC, parameter);
		return result;
	}
	
	
	
	protected byte[] transceive(byte command) throws IOException {
		byte[] parameter = new byte[0];		
		return transceive(command, parameter);
	}

	protected byte[] transceive(byte command, byte[] parameter) throws IOException {
		byte[] nfcVCommand = new byte[1 + parameter.length];
		nfcVCommand[0] = command;		
		System.arraycopy(parameter, 0, nfcVCommand, 1, parameter.length);
		
		return nfca.transceive(nfcVCommand);
	}

}
