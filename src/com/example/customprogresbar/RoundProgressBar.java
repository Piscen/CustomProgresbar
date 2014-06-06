package com.example.customprogresbar;

import com.example.customprogresbar.R;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;


/**
 * @author wu_zhang
 * @2014-5-28上午11:10:38
 * @TODO 
 * 自定义圆形进度条，可以设置颜色，进度，文字，
 * 
 */

public class RoundProgressBar extends View {
	/**
	 * 画笔对象的引�?
	 */
	private Paint paint;

	/**
	 * 圆环的颜�?
	 */
	private int roundColor;

	/**
	 * 圆环进度的颜�?
	 */
	private int roundProgressColor;

	/**
	 * 中间进度百分比的字符串的颜色
	 */
	private int textColor;

	/*
	 * 圆心坐标
	 */
	private int centreX,centreY;


	/**
	 * 中间进度百分比的字符串的字体
	 */
	private float textSize;

	/**
	 * 圆环的宽�?
	 */
	private float roundWidth;

	/**
	 * �?��进度
	 */
	private int max;

	/**
	 * 当前进度
	 */
	private int progress;
	/**
	 * 是否显示中间的进�?
	 */
	private boolean textIsDisplayable;

	/**
	 * 进度的风格，实心或�?空心
	 */
	private int style;

	public static final int STROKE = 0;
	public static final int FILL = 1;

	public static final int SUCCES = 01;
	public static final int FAILD = 02;
	public static final int CONTINUE = 03;

	private int state = 01 ;
	private int [] images = {R.drawable.succes,R.drawable.faild,R.drawable.contin};
	public RoundProgressBar(Context context) {
		this(context, null);
	}

	public RoundProgressBar(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public RoundProgressBar(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		paint = new Paint();
		TypedArray mTypedArray = context.obtainStyledAttributes(attrs,
				R.styleable.RoundProgressBar);
		//获取自定义属性和默认�?
		roundColor = mTypedArray.getColor(R.styleable.RoundProgressBar_roundColor, Color.RED);
		roundProgressColor = mTypedArray.getColor(R.styleable.RoundProgressBar_roundProgressColor, Color.GREEN);
		textColor = mTypedArray.getColor(R.styleable.RoundProgressBar_textColor, Color.GREEN);
		textSize = mTypedArray.getDimension(R.styleable.RoundProgressBar_textSize, 7);
		roundWidth = mTypedArray.getDimension(R.styleable.RoundProgressBar_roundWidth, 5);
		max = mTypedArray.getInteger(R.styleable.RoundProgressBar_max, 100);
		textIsDisplayable = mTypedArray.getBoolean(R.styleable.RoundProgressBar_textIsDisplayable, true);
		style = mTypedArray.getInt(R.styleable.RoundProgressBar_style, 0);

		mTypedArray.recycle();
	}


	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		/**
		 * 画最外层的大圆环
		 */
		centreX= getWidth()/2; //获取圆心的x坐标
		centreY= getHeight()/2;
		//		int centre = (int) 24.5;
		int radius = (int) (centreX - roundWidth/2); //圆环的半�?
		paint.setColor(roundColor); //设置圆环的颜�?
		paint.setStyle(Paint.Style.STROKE); //设置空心
		paint.setStrokeWidth(roundWidth); //设置圆环的宽�?
		paint.setAntiAlias(true);  //消除锯齿 
		canvas.drawCircle(centreX, centreX, radius, paint); //画出圆环

		/*
		 * 中间白色�?
		 */
		paint.setColor(Color.WHITE); //设置中间圆的颜色
		paint.setStyle(Paint.Style.FILL); //设置实心
		paint.setAntiAlias(true);  //消除锯齿 
		int radius1 = (int) (radius- roundWidth/2);
		canvas.drawCircle(centreX, centreX, radius1, paint); //画出中间白色�?

		//		Log.e("log", centre + "");
		/**
		 * 画进度百分比
		 */
		paint.setStrokeWidth(0); 
		paint.setColor(textColor);
		paint.setTextSize(textSize);
		paint.setTypeface(Typeface.DEFAULT_BOLD); //设置字体

		//		int percent = (int)(((float)progress / (float)max) * 100);  //中间的进度百分比，先转换成float在进行除法运算，不然都为0
		//		float textWidth = paint.measureText(percent + "%");   //测量字体宽度，我们需要根据字体的宽度设置在圆环中�?

		if(textIsDisplayable  && style == STROKE){
			//			canvas.drawText(percent + "%", centre - textWidth / 2, centre + textSize/2, paint); //画出进度百分�?
			drawImage(state ,canvas, centreX - textSize/2, centreX - textSize/2,paint);

		}
		/**
		 * 画圆�?，画圆环的进�?
		 */

		//设置进度是实心还是空�?
		paint.setStrokeWidth(roundWidth); //设置圆环的宽�?
		paint.setColor(roundProgressColor);  //设置进度的颜�?
		RectF oval = new RectF(centreX - radius, centreX - radius, centreX
				+ radius, centreX + radius);  //用于定义的圆弧的形状和大小的界限

		switch (style) {
		case STROKE:{
			paint.setStyle(Paint.Style.STROKE);
			canvas.drawArc(oval, -90, 360 * progress / max, false, paint);  //根据进度画圆�?
			break;
		}
		case FILL:{
			paint.setStyle(Paint.Style.FILL_AND_STROKE);
			if(progress !=0)
				canvas.drawArc(oval, -90, 360 * progress / max, true, paint);  //根据进度画圆�?
			break;
		}
		}

	}

	/**
	 * {tags}
	 * @TODO 图片缩放
	 */
	private  Bitmap small(Bitmap bitmap) {
		Matrix matrix = new Matrix(); 
		matrix.postScale(0.5f,0.5f); //长和宽放大缩小的比例
		Bitmap resizeBmp = Bitmap.createBitmap(bitmap,0,0,bitmap.getWidth(),bitmap.getHeight(),matrix,true);
		return resizeBmp;
	}

	/**
	 * {tags}
	 * @TODO 画中心图�?
	 */
	private void drawImage(int state,Canvas canvas,float f,float g,Paint paint){
		Bitmap bitmap = null;
		switch (state) {
		case 01:

			bitmap = BitmapFactory.decodeResource(getResources(), images[0]);
			setProgress(100);
			break;
		case 02:
//			bitmap = small(BitmapFactory.decodeResource(getResources(), images[1]));
			bitmap = BitmapFactory.decodeResource(getResources(), images[1]);
			
			setCricleColor(Color.RED);
			setCricleProgressColor(Color.RED);
			break;
		case 03:
//			bitmap = small(BitmapFactory.decodeResource(getResources(), images[2]));
			bitmap = BitmapFactory.decodeResource(getResources(), images[2]);
			
			break;
		default:
			break;
		}
		int  x = centreX - bitmap.getWidth()/2;
		int  y = centreY - bitmap.getHeight()/2;
		canvas.drawBitmap(bitmap, x,y, paint);
	}

	public synchronized int getState() {
		return state;
	}

	public synchronized void setState(int state) {
		if(max < 0){
			throw new IllegalArgumentException("max not less than 0");
		}
		this.state = state;
		postInvalidate();

	}

	public  void initColors(){
		if(progress<100){
			paint.setColor(roundProgressColor);
		}
		else{
			paint.setColor(Color.RED);
		}
	}

	public synchronized int getMax() {
		return max;
	}

	/**
	 * 设置进度的最大�?
	 * @param max
	 */
	public synchronized void setMax(int max) {
		if(max < 0){
			throw new IllegalArgumentException("max not less than 0");
		}
		this.max = max;
	}

	/**
	 * 获取进度.�?��同步
	 * @return
	 */
	public synchronized int getProgress() {
		return progress;
	}

	/**
	 * 设置进度，此为线程安全控件，由于考虑多线的问题，�?��同步
	 * 刷新界面调用postInvalidate()能在非UI线程刷新
	 * @param progress
	 */
	public synchronized void setProgress(int progress) {
		if(progress < 0){
			throw new IllegalArgumentException("progress not less than 0");
		}
		if(progress > max){
			progress = max;
		}
		if(progress <= max){
			this.progress = progress;

			if(progress == 100){
				state = SUCCES;
			}
			if(progress <100&progress>0){
				state = CONTINUE;
			}
			if(progress == 0){
				state = FAILD;
			}
			postInvalidate();
		}
	}


	public int getCricleColor() {
		return roundColor;
	}

	public void setCricleColor(int cricleColor) {
		this.roundColor = cricleColor;
		postInvalidate();
	}

	public int getCricleProgressColor() {
		return roundProgressColor;
	}

	public void setCricleProgressColor(int cricleProgressColor) {
		this.roundProgressColor = cricleProgressColor;
		postInvalidate();
	}

	public int getTextColor() {
		return textColor;
	}

	public void setTextColor(int textColor) {
		this.textColor = textColor;
	}

	public float getTextSize() {
		return textSize;
	}

	public void setTextSize(float textSize) {
		this.textSize = textSize;
	}

	public float getRoundWidth() {
		return roundWidth;
	}

	public void setRoundWidth(float roundWidth) {
		this.roundWidth = roundWidth;
	}

}

