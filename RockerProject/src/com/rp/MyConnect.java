package com.rp;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;

import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;

public class MyConnect implements Runnable{
	
	protected static final String TAG = "MyConnect";
	//�ͻ�������Socket
    Socket mSocket = null;
    //������
    BufferedReader mBufferedReader = null;
    //�����
    PrintWriter mPrintWriter= null;
    //ipaddress
    String ipconfig = "0.0.0.0";
    //port
    int mport = 15000;
    //���ӳɹ�flag
    boolean issuccess = false;
    
    public MyConnect(String ipaddr, int port){
    	this.ipconfig = ipaddr;
    	this.mport = port;
    }
    
    

	public void run() {
		// TODO Auto-generated method stub
    	try {
    		Log.v(TAG,ipconfig + ":"+mport);
    		mSocket = new Socket();
    		mSocket.connect(new InetSocketAddress(ipconfig, mport), 5000);  		
    		//ȡ��������������
    		mBufferedReader = new BufferedReader(new InputStreamReader(mSocket.getInputStream(),"GB2312"));
    		mPrintWriter = new PrintWriter(mSocket.getOutputStream(),true);
    		//��¼���ӳɹ�
    		Log.i(TAG, "Connect successfully!");
    		issuccess = true;
    		//msgText.setText("���ӳɹ���");
    		//sendBtn.setTag("����");
    	} catch (SocketTimeoutException aa) {
    		
    		//��¼����ʧ��
    		//msgText.setText("����ʧ�ܣ�");
    		Log.w(TAG, "Connect failed!");
    		issuccess = false;
    	} catch(Exception e){
    		//��¼����ʧ��
    		//msgText.setText("����ʧ�ܣ�");
    		Log.w(TAG, "Connect failed!");
    		issuccess = false;
    		e.printStackTrace(); 
    	}

    	//������Ƶ����Ӧ��д���·�������������
    	
	}

	//�������ӽ��
	public boolean ifConnected() {
		return issuccess;
	}
	
	
	//���䷽������
	public void sendDirection(String dir) {
		try{
			//msgText.append("��");
			mPrintWriter.println(dir);
			//mPrintWriter.println();
			mPrintWriter.flush();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	//���ݴ������
	public void sendTest() {
		try{
			//msgText.append("��");
			mPrintWriter.println("�յ������ֻ���������Ϣ��");
			mPrintWriter.println();
			mPrintWriter.flush();
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	
	
}
