package com.example.and;

import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TCPServer {
	//�����˿ں�
	int port=15000;
	ServerSocket mServerSocket;
	//Socket�������������ӵ�Socket
	private Set<Socket> allSockets;
	//�̳߳������̵߳�����
	private ExecutorService mExecutorService;

	public TCPServer(){
		try{
			//��������socket����
			mServerSocket=new ServerSocket(port);
			//��ʼ����������������ӵ�����Socket
			allSockets=new HashSet<Socket>();
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	

	//�����������˵ķ���
	public void init(){
		try{
			//����һ���̳߳�
			mExecutorService=Executors.newCachedThreadPool();
			System.out.println("TCP���������������������˿ڣ�"+port);
			//ѭ���ȴ��ͻ�������
			while(true){
				Socket mSocket =mServerSocket.accept();
				//����һ���ͻ�����
				if(mSocket!=null){
					//��ȡ��ǰ����
					String date=getDateByFormat(new Date(),"yyyy-MM-dd");
					System.out.println(mSocket.getInetAddress()+"����"+"\t["+date+"]");
					allSockets.add(mSocket);
					//����һ���ͻ����̣߳�����Ѿ�����������
					mExecutorService.execute(new ServerThread(mSocket));
				}
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	public String getDateByFormat(Date strDate, String format) {
		String curTime="";
		SimpleDateFormat ss=new SimpleDateFormat(format);
		curTime=ss.format(strDate);
		return curTime;
	}

	public static void main(String[] args){
		new TCPServer().init();
	}
}
