package com.antlersoft.util;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.SurfaceHolder.Callback;

public class MySurfaceView extends SurfaceView implements Callback, Runnable {

	private Thread th;
	private SurfaceHolder sfh;
	private Canvas canvas;
	private Paint paint;
	private boolean flag;
	//�̶�ҡ�˱���Բ�ε�X,Y�����Լ��뾶
	private int RockerCircleX = 300;
	private int RockerCircleY = 750;
	private int RockerCircleR = 200;
	//ҡ�˵�X,Y�����Լ�ҡ�˵İ뾶
	private float SmallRockerCircleX = 300;
	private float SmallRockerCircleY = 750;
	private float SmallRockerCircleR = 70; 
	private double LimitR = 0;  
	private double tempRad = 0;

	//
	Context mainA = null;
	
	private int dir = 0;
	//

	
	
	
	//
	public MySurfaceView(Context context) {
		super(context);
		Log.v("Himi", "MySurfaceView");
		this.setKeepScreenOn(true);
		sfh = this.getHolder();
		sfh.addCallback(this);
		paint = new Paint();
		paint.setAntiAlias(true);
		setFocusable(true);
		setFocusableInTouchMode(true);
	}
	
	public MySurfaceView(Context context, AttributeSet attrs) {
		super(context, attrs);
		Log.v("Himi", "MySurfaceView");
		this.setKeepScreenOn(true);
		sfh = this.getHolder();
		sfh.addCallback(this);
		paint = new Paint();
		paint.setAntiAlias(true);
		setFocusable(true);
		setFocusableInTouchMode(true);
		// TODO Auto-generated constructor stub
		}

		protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub

	
		}
		
		
	public void surfaceCreated(SurfaceHolder holder) {
		th = new Thread(this);
		
		flag = true;
		th.start();
	}

	/***
	 * �õ�����֮��Ļ���
	 */
	public double getRad(float px1, float py1, float px2, float py2) {
		//�õ�����X�ľ���
		float x = px2 - px1;
		//�õ�����Y�ľ���
		float y = py1 - py2;
		//���б�߳�
		float xie = (float) Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
		//�õ�����Ƕȵ�����ֵ��ͨ�����Ǻ����еĶ��� ���ڱ�/б��=�Ƕ�����ֵ��
		float cosAngle = x / xie;
		//ͨ�������Ҷ����ȡ����ǶȵĻ���
		float rad = (float) Math.acos(cosAngle);
		//ע�⣺��������λ��Y����<ҡ�˵�Y��������Ҫȡ��ֵ-0~-180
		if (py2 < py1) {
			rad = -rad;
		}
		return rad;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {

		if (event.getAction() == MotionEvent.ACTION_DOWN || event.getAction() == MotionEvent.ACTION_MOVE ) {
			LimitR = Math.sqrt(Math.pow((RockerCircleX - (int) event.getX()), 2) + Math.pow((RockerCircleY - (int) event.getY()), 2));
			// �����������ڻ��Χ��
			if (LimitR >= RockerCircleR - 100 && LimitR <= RockerCircleR + 200) {
				//�õ�ҡ���봥�������γɵĽǶ�
				tempRad = getRad(RockerCircleX, RockerCircleY, event.getX(), event.getY());
				//��֤�ڲ�СԲ�˶��ĳ�������
				getXY(RockerCircleX, RockerCircleY, RockerCircleR, tempRad);
			} else if (LimitR <= RockerCircleR - SmallRockerCircleR )  {//���С�����ĵ�С�ڻ�����������û��������ƶ�����
				SmallRockerCircleX = (int) event.getX();
				SmallRockerCircleY = (int) event.getY();
			}
		} else if (event.getAction() == MotionEvent.ACTION_UP) {
			//���ͷŰ���ʱҡ��Ҫ�ָ�ҡ�˵�λ��Ϊ��ʼλ��
			SmallRockerCircleX = 300;
			SmallRockerCircleY = 750;
		}
		judgeDirection();
		return true;
	}

	/**
	 * 
	 * @param R
	 *            Բ���˶�����ת��
	 * @param centerX
	 *            ��ת��X
	 * @param centerY
	 *            ��ת��Y
	 * @param rad
	 *            ��ת�Ļ���
	 */
	public void getXY(float centerX, float centerY, float R, double rad) {
		//��ȡԲ���˶���X���� 
		SmallRockerCircleX = (float) ((R-75) * Math.cos(rad)) + centerX;
		//��ȡԲ���˶���Y����
		SmallRockerCircleY = (float) ((R-75) * Math.sin(rad)) + centerY;
	}

	public void draw() {
		try {
			//Log.e("2", "2");
			canvas = sfh.lockCanvas();
			
			canvas.drawColor(0);

			//����͸����
			paint.setColor(0xFFFFFFFF);
			//����ҡ�˱���
			canvas.drawCircle(RockerCircleX, RockerCircleY, RockerCircleR, paint);
			paint.setColor(0xFFff0000);
			//����ҡ��
			canvas.drawCircle(SmallRockerCircleX, SmallRockerCircleY, SmallRockerCircleR, paint);
			
//			switch(dir){
//			case 1:canvas.drawCircle(300, 100, SmallRockerCircleR, paint);break;
//			case 2:canvas.drawCircle(300, 200, SmallRockerCircleR, paint);break;
//			case 3:canvas.drawCircle(200, 150, SmallRockerCircleR, paint);break;
//			case 4:canvas.drawCircle(400, 150, SmallRockerCircleR, paint);break;
//			}
			
			
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			try {
				if (canvas != null)
					sfh.unlockCanvasAndPost(canvas);
			} catch (Exception e2) {

			}
		}
	}

	public int judgeDirection(){
		
		double Rad = tempRad;
		//������ת�����ȱ���
		if (tempRad < 0){
			Rad = Rad + 2*Math.PI;
		}
		
		if (SmallRockerCircleX==300 && SmallRockerCircleY==750){
			dir = 0;
		} else if (tempRad > -Math.PI * 0.75 && tempRad <= -Math.PI/4){//��
			dir = 1;
		} else if (tempRad > Math.PI/4 && tempRad <= Math.PI * 0.75){//��
			dir = 2;
		} else if (tempRad > -Math.PI/4 && tempRad < Math.PI * 0.75){//��
			dir = 4;
		} else if (Rad > Math.PI *0.75 && Rad <= Math.PI * 1.25){//��
			dir = 3;
		}else {//�ص�����
			
		}
		
		return dir;
			
	}
	
	
	public void run() {
		// TODO Auto-generated method stub
		Log.e("1", "1");
		while (flag) {
			draw();
			try {
				Thread.sleep(50);
			} catch (Exception ex) {
			}
		}
	}

	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
		Log.v("Himi", "surfaceChanged");
	}

	public void surfaceDestroyed(SurfaceHolder holder) {
		flag = false;
		Log.v("Himi", "surfaceDestroyed");
	}
}