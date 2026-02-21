package com.ncnn;

import android.content.Context;
import android.graphics.PixelFormat;
import android.os.Build;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.SeekBar;
import android.app.Activity;
import android.os.Bundle;
import android.graphics.Color;
import android.widget.Switch;
import android.animation.ValueAnimator;
import android.animation.ArgbEvaluator;
import android.widget.Toast;
import android.graphics.drawable.GradientDrawable;
/*

下面有调用样例子

*/

public class framui {
    private static Context context;
    private WindowManager windowManager;
    private FrameLayout floatLayout;
    private WindowManager.LayoutParams params;
	public static Activity ckx;

	private Switch gyroSwitch;
	private TextView gyroSwitchtext;
	private View divider ;
	private TextView windowtextView;
	private TextView Btn;
	private TextView xMultiplierText;
	private SeekBar xMultiplierSeekBar;
	private TextView creattextView;
	private TextView Btnbar;
	GradientDrawable circleDrawable;
	public static int Floatbarcolar=Color.RED;//悬浮球颜色
	public static int btnbarcolar=0xCC1E88E5;  //收缩区域颜色
	

    public framui(Context context) 
	{
		ckx=(Activity)context;
        this.context = context;
        initFloatWindow();
    }

	static Object lock = new Object();


	//延时
	public static void sleep(int time)
	{
		synchronized (lock) {
			try {

				lock.wait(time); // 最多等待2秒


			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	};
	
	
	
    private void initFloatWindow() {
        // 获取WindowManager服务
        windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);

        // 创建WindowManager.LayoutParams参数
        params = new WindowManager.LayoutParams();

        // 设置窗口类型（根据Android版本）
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            params.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        } else {
            params.type = WindowManager.LayoutParams.TYPE_PHONE;
        }

        // 设置窗口标志
        params.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
			| WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
			| WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN
			| WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS;

        // 设置窗口格式
        params.format = PixelFormat.RGBA_8888;

        // 设置窗口大小和位置
        params.width = WindowManager.LayoutParams.WRAP_CONTENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        params.gravity = Gravity.TOP | Gravity.START;
        params.x = 100;
        params.y = 100;

        // 创建FrameLayout
        floatLayout = new FrameLayout(context);

        // 设置FrameLayout背景（可选）
        //floatLayout.setBackgroundColor(0x80000000); // 半透明黑色
        floatLayout.setBackgroundColor(0x00000000); // 半透明黑色
		
        // 设置FrameLayout大小
        floatLayout.setLayoutParams(new FrameLayout.LayoutParams(
										FrameLayout.LayoutParams.WRAP_CONTENT,
										FrameLayout.LayoutParams.WRAP_CONTENT
									));

        // 添加拖拽功能
        floatLayout.setOnTouchListener(new View.OnTouchListener() {
				private int initialX;
				private int initialY;
				private float initialTouchX;
				private float initialTouchY;

				@Override
				public boolean onTouch(View v, MotionEvent event) {
					switch (event.getAction()) {
						case MotionEvent.ACTION_DOWN:
							initialX = params.x;
							initialY = params.y;
							initialTouchX = event.getRawX();
							initialTouchY = event.getRawY();
							return true;
						case MotionEvent.ACTION_MOVE:
							params.x = initialX + (int) (event.getRawX() - initialTouchX);
							params.y = initialY + (int) (event.getRawY() - initialTouchY);
							
							
							// 限制边界（可选）
							int maxX = v.getResources().getDisplayMetrics().widthPixels - v.getWidth();
							int maxY = v.getResources().getDisplayMetrics().heightPixels - v.getHeight();
							params.x = Math.max(0, Math.min(params.x, maxX));
							params.y = Math.max(0, Math.min(params.y, maxY));
							
							
							
							windowManager.updateViewLayout(floatLayout, params);
							return true;
						case MotionEvent.ACTION_UP:
							// 点击事件处理
							if (Math.abs(event.getRawX() - initialTouchX) < 5 
								&& Math.abs(event.getRawY() - initialTouchY) < 5) {
								// 点击事件
								onClick();
							}
							return true;
					}
					return false;
				}
			});
			
			
			
			
			
			
    }

	
    // 向悬浮窗添加视图
    public void addView(View view) {
        floatLayout.addView(view);
    }


    // 显示悬浮窗
    public void show() {
        if (floatLayout.getParent() == null) {
            windowManager.addView(floatLayout, params);
        }
    }

    // 隐藏悬浮窗
    public void hide() {
        if (floatLayout.getParent() != null) {
            windowManager.removeView(floatLayout);
        }
    }

    // 更新悬浮窗位置
    public void updatePosition(int x, int y) {
        params.x = x;
        params.y = y;
        windowManager.updateViewLayout(floatLayout, params);
    }

    // 点击事件处理
    private void onClick() {
        // 处理点击事件
        System.out.println("悬浮窗被点击");
    }

    // 销毁悬浮窗
    public void destroy() {
        hide();
        floatLayout.removeAllViews();
    }
	
	public static int dpToPx(int dp) 
	{
        if (ckx == null) return dp;
        float density = ckx.getResources().getDisplayMetrics().density;
        return Math.round(dp * density);
    }
	
	//创建容器
	public TextView creatwindow(int colar,String text,int w,int h,int spc)
	{
		
		windowtextView = new TextView(context);
        windowtextView.setText(text);
        windowtextView.setTextSize(16);
        windowtextView.setTextColor(0xFFFFFFFF);
        windowtextView.setPadding(dpToPx(spc), dpToPx(0), dpToPx(10), dpToPx(5));//rect坐标
		
        windowtextView.setBackgroundColor(colar);
		
		floatLayout.addView(windowtextView, new FrameLayout.LayoutParams(dpToPx(w), dpToPx(h)));//布局

		
		
		
		divider = new View(ckx);
        divider.setBackgroundColor(Color.parseColor("#66FFFFFF"));
		divider.setY(dpToPx(22));

        floatLayout.addView(divider, new LinearLayout.LayoutParams(dpToPx(w),dpToPx(1)));
		
								
		Btnbar = new TextView(ckx);
        //Btn2.setGravity(Gravity.CENTER);
        //Btn2.setPadding(dpToPx(0), dpToPx(0), dpToPx(8), dpToPx(0));
        Btnbar.setBackgroundColor(btnbarcolar);
		//Btn2.setX(dpToPx(0));
		//Btn2.setY(dpToPx(0));
		floatLayout.addView(Btnbar,new FrameLayout.LayoutParams(dpToPx(22), dpToPx(22)));
	   
		
		
		


        // 设置小球大小为30dp
        int sizeInPx = dpToPx(30);

        // 创建圆形红色背景
        circleDrawable = new GradientDrawable();
        circleDrawable.setShape(GradientDrawable.OVAL);
        circleDrawable.setColor(Floatbarcolar);
        circleDrawable.setSize(sizeInPx, sizeInPx);

        // 设置背景
        //Btnbar.setBackground(circleDrawable);
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		// 添加拖拽功能
        Btnbar.setOnTouchListener(new View.OnTouchListener() {
				private int initialX;
				private int initialY;
				private float initialTouchX;
				private float initialTouchY;

				@Override
				public boolean onTouch(View v, MotionEvent event) {
					switch (event.getAction()) {
						case MotionEvent.ACTION_DOWN:
							initialX = params.x;
							initialY = params.y;
							initialTouchX = event.getRawX();
							initialTouchY = event.getRawY();
							return true;
						case MotionEvent.ACTION_MOVE:
							params.x = initialX + (int) (event.getRawX() - initialTouchX);
							params.y = initialY + (int) (event.getRawY() - initialTouchY);


							// 限制边界（可选）
							int maxX = v.getResources().getDisplayMetrics().widthPixels - v.getWidth();
							int maxY = v.getResources().getDisplayMetrics().heightPixels - v.getHeight();
							params.x = Math.max(0, Math.min(params.x, maxX));
							params.y = Math.max(0, Math.min(params.y, maxY));



							windowManager.updateViewLayout(floatLayout, params);
							return true;
						case MotionEvent.ACTION_UP:
							// 点击事件处理
							if (Math.abs(event.getRawX() - initialTouchX) < 5 
								&& Math.abs(event.getRawY() - initialTouchY) < 5) {
								// 点击事件
								onClick();
								
								btnclick();
								
								
							}
							return true;
					}
					return false;
				}
			});
		
		
								
								
			return windowtextView;					
								
		
	}
	
	int bal2=0;
	
	private void btnclick()
	{
		
		//Toast.makeText(ckx, "0", 0).show();
		
		;
		
		
		//Toast.makeText(ckx, "88", 0).show();
		//FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) windowtextView.getLayoutParams();
		//FrameLayout.LayoutParams params2 = (FrameLayout.LayoutParams) divider1.getLayoutParams();
		//FrameLayout.LayoutParams params3 = (FrameLayout.LayoutParams) floatLayout.getLayoutParams();

		bal2=bal2+1;
		if(bal2>1)
		{
			bal2=0;
		}

		if(bal2>0)
		{
		
             Btnbar.setBackground(circleDrawable);
			//params2.width = dpToPx(25); 
			//divider1.setLayoutParams(params2);

			//params.width = dpToPx(25); 
			//params.height = dpToPx(20);
			//windowtextView.setLayoutParams(params);


			// Toast.makeText(ckx, "1", 0).show();
			if(windowtextView!=null)
				{
					windowtextView.setVisibility(View.GONE);
				}
			if(divider!=null)
				{
					divider.setVisibility(View.GONE);
				}
			if(gyroSwitchtext!=null)
				{
					gyroSwitchtext.setVisibility(View.GONE);
				}
				
			if(gyroSwitch!=null)
				{
					gyroSwitch.setVisibility(View.GONE);
				}
			if(Btn!=null)
				{
					Btn.setVisibility(View.GONE);
				}
			if(xMultiplierSeekBar!=null)
				{
					xMultiplierSeekBar.setVisibility(View.GONE);
				}
			
			
		    if(xMultiplierText!=null)
				{
					xMultiplierText.setVisibility(View.GONE);
				}
			if(creattextView!=null)
				{
					creattextView.setVisibility(View.GONE);
				}
			
			
            
			
            
			
			
			
		}
		else{
			Btnbar.setBackgroundColor(btnbarcolar);

			//params2.width = dpToPx(w1); 
			//divider1.setLayoutParams(params2);

			//params.width = dpToPx(w1); // 添加额外边距
			//params.height = dpToPx(h1);
			//textView1.setLayoutParams(params);
		     //Toast.makeText(ckx,"88:"+Integer.toString(bal2),0).show();
		    if(windowtextView!=null)
			{
				windowtextView.setVisibility(View.VISIBLE);
			}
			if(divider!=null)
			{
				divider.setVisibility(View.VISIBLE);
			}
			if(gyroSwitchtext!=null)
			{
				gyroSwitchtext.setVisibility(View.VISIBLE);
			}

			if(gyroSwitch!=null)
			{
				gyroSwitch.setVisibility(View.VISIBLE);
			}
			if(Btn!=null)
			{
				Btn.setVisibility(View.VISIBLE);
			}
			if(xMultiplierSeekBar!=null)
			{
				xMultiplierSeekBar.setVisibility(View.VISIBLE);
			}


		    if(xMultiplierText!=null)
			{
				xMultiplierText.setVisibility(View.VISIBLE);
			}
			if(creattextView!=null)
			{
				creattextView.setVisibility(View.VISIBLE);
			}
			//windowtextView.setVisibility(View.VISIBLE);
		    //divider.setVisibility(View.VISIBLE);
		    //gyroSwitch.setVisibility(View.VISIBLE);
			//gyroSwitchtext.setVisibility(View.VISIBLE);
			//Btn.setVisibility(View.VISIBLE);
			//xMultiplierSeekBar.setVisibility(View.VISIBLE);
			//xMultiplierText.setVisibility(View.VISIBLE);
			//creattextView.setVisibility(View.VISIBLE);
			
		
		}
		
		
		//Toast.makeText(ckx,Integer.toString(bal2),0).show();
	}
	
	
	
	
	
	
	
	
	
	
	
	//创建分割线
	public void divider(int y,int w,int size)
	{
		divider = new View(ckx);
        divider.setBackgroundColor(Color.parseColor("#66FFFFFF"));
		divider.setY(dpToPx(y));
		
        floatLayout.addView(divider, new LinearLayout.LayoutParams(
							  dpToPx(w),
							  dpToPx(size)));
		
	}
	
	
	
	
	
	public TextView creattext(String text,int x,int y)
	{
		
		// 创建TextView显示文本
		creattextView = new TextView(ckx);
		creattextView.setText(text);
		creattextView.setTextColor(Color.WHITE);
		creattextView.setTextSize(14);
		creattextView.setPadding(0, 0, dpToPx(8), 0); 
		creattextView.setX(dpToPx(x)+dpToPx(10));
		creattextView.setY(dpToPx(y));
		floatLayout.addView(creattextView);
		return creattextView;
		
	}
	// 颜色渐变方法
	public void animateTextColor(int startColor, int endColor, long duration,final TextView v) {
		ValueAnimator colorAnimator = ValueAnimator.ofObject(new ArgbEvaluator(), startColor, endColor);
		colorAnimator.setDuration(duration);

		colorAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
				@Override
				public void onAnimationUpdate(ValueAnimator animator) {
					v.setBackgroundColor((int) animator.getAnimatedValue());
					
				}
			});

		colorAnimator.start();
	}
	
	//创建按钮
	public TextView creatbutton(int color,String text,int x,int y,int w,int h)
	{
		Btn = new TextView(ckx);
        Btn.setText(text);
        Btn.setTextSize(14);
        Btn.setTextColor(Color.WHITE);
        Btn.setGravity(Gravity.CENTER);
        Btn.setPadding(dpToPx(0), dpToPx(0), dpToPx(8), dpToPx(0));
        Btn.setBackgroundColor(color);
		Btn.setX(dpToPx(x));
		Btn.setY(dpToPx(y));
		floatLayout.addView(Btn,new FrameLayout.LayoutParams(dpToPx(w), dpToPx(h)));
		final TextView Btn2 =Btn;
		final int color2=color;
		Btn.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) 
				{
					
					// 从白色渐变到红色
					animateTextColor(Color.WHITE, color2, 80,Btn2);
					
					
			
					
				}
			});
		
		return Btn;
	}
	
	
	//滑块开关
	public  TextView creatswich(String text,int x,int y,int spc)
	{
		
		
		// 创建TextView显示文本
		gyroSwitchtext = new TextView(ckx);	
		
		gyroSwitchtext.setText(text);
		gyroSwitchtext.setTextColor(Color.WHITE);
		gyroSwitchtext.setTextSize(14);
		gyroSwitchtext.setPadding(0, 0, dpToPx(8), 0); 
		gyroSwitchtext.setX(dpToPx(x)+dpToPx(10));
		gyroSwitchtext.setY(dpToPx(y)+dpToPx(3));// 文本和Switch之间的y间距
		floatLayout.addView(gyroSwitchtext);
		
		gyroSwitch = new Switch(ckx);
		gyroSwitch.setText("");
        gyroSwitch.setTextColor(Color.WHITE);
        gyroSwitch.setChecked(false);
        gyroSwitch.setPadding(dpToPx(10), dpToPx(5), dpToPx(0), dpToPx(5));
		gyroSwitch.setGravity(Gravity.CENTER_VERTICAL | Gravity.START);

		
		gyroSwitch.setX(dpToPx(x));
		gyroSwitch.setY(dpToPx(y));
		floatLayout.addView(gyroSwitch, new FrameLayout.LayoutParams(dpToPx(spc), dpToPx(20)));//控件宽和高	
        
		return gyroSwitch;
	}
	
	
	//创建滑块条
	public SeekBar creatseekbar(String text,int x,int y,int w,int spac,int min,int max)
	{

        xMultiplierText = new TextView(ckx);
        xMultiplierText.setText(text+"0");
        xMultiplierText.setTextSize(14);
        xMultiplierText.setTextColor(Color.WHITE);
        xMultiplierText.setPadding(0, 0, dpToPx(8), 0);
		
        xMultiplierText.setX(dpToPx(x));
	    xMultiplierText.setY(dpToPx(y)-dpToPx(2));

        xMultiplierSeekBar = new SeekBar(ckx);
		xMultiplierSeekBar.setMin(min);
        xMultiplierSeekBar.setMax(max); // 0-20
        xMultiplierSeekBar.setProgress(0); // 默认1倍
		//xMultiplierSeekBar.setBackgroundColor(Color.RED);
		
        xMultiplierSeekBar.setX(dpToPx(x+20)+dpToPx(spac));
	    xMultiplierSeekBar.setY(dpToPx(y));
		

		
		floatLayout.addView(xMultiplierText);
		
		floatLayout.addView(xMultiplierSeekBar,new FrameLayout.LayoutParams(dpToPx(w),dpToPx(20)));
		
		
		
		final TextView xMultiplierText2=xMultiplierText;
		final String text2=text;
		
		xMultiplierSeekBar.setOnSeekBarChangeListener(new android.widget.SeekBar.OnSeekBarChangeListener() {
				@Override
				public void onProgressChanged(android.widget.SeekBar seekBar, int progress, boolean fromUser) {
					int multiplier = progress;
				
					xMultiplierText2.setText(text2+ multiplier);
					
				}

				@Override
				public void onStartTrackingTouch(android.widget.SeekBar seekBar) {}

				@Override
				public void onStopTrackingTouch(android.widget.SeekBar seekBar) {}
			});
		
		
		
		
		
		
		return xMultiplierSeekBar;
		
		
		
	}
	
	
	
	
	
	
	// 创建并添加示例TextView
	
    public  void addSampleTextView()
	{ 
		
		
		creatwindow(Color.BLUE,"悬浮窗口",150,250,40);
        
		creatswich("陀螺仪开关1",0,40,140);
		
        creattext("陀螺仪开关2",0,70);
		
		
	    creatbutton(Color.BLUE,"开始",10,100,50,20);
		
		
		creatseekbar("x:",10,140,100,0,1,100);
		
		
		
    }
    
	
	//样例模板
	public static void run(Activity activit)
	{

		framui ui = new framui(activit);


		ui.creatwindow(Color.GREEN,"悬浮窗口",150,250,40);

		ui.creatswich("陀螺仪开关1",0,40,140);

        ui.creattext("陀螺仪开关2",0,70);


	    ui.creatbutton(Color.BLUE,"开始",10,100,50,20);


		ui.creatseekbar("x:",10,140,100,0,1,100);


		ui.show();



		framui ui2 = new framui(activit);


		ui2.creatwindow(Color.BLUE,"悬浮窗口",150,250,40);

		ui2.creatswich("陀螺仪开关1",0,40,140);

        ui2.creattext("陀螺仪开关2",0,70);


	    ui2.creatbutton(Color.BLUE,"开始",10,100,50,20);


		ui2.creatseekbar("x:",10,140,100,0,1,100);


		ui2.show();
		
		//调试信息窗口
		framui.Totext();
		framui.str1="你好，世界";
		
		
		


	};
	
	public static String str1="";
	public static String str2="";
	public static String str3="";
	public static String str4="";
	public static String str5="";
	public static String str6="";
	public static String str7="";
	public static String str8="";
	public static String str9="";
	public static String str10="";
	public static String str11="";
	public static String str12="";
	public static String str13="";
	public static String str14="";
	public static String str15="";
	public static String str16="";
	public static String str17="";
	public static String str18="";
	public static String str19="";
	public static String str20="";
	private static String tex="         调试信息";
	private static TextView texview;
	public static void Totext()
	{
		
		
		framui ui = new framui(ckx);
		
		
		texview=ui.creatwindow(0x80000000,tex,150,250,0);
		//texview=ui.creattext(tex,95,2);
	
		ui.show();
		//texview.setText(tex+":"+Integer.toString(888));
		new threadx().start();
		
		
		
	};
	
	private static class threadx extends Thread
	{
		int i2=0;
		
		@Override
		public void run()
		{
			while(texview!=null)
				{
					i2=i2+1;
					framui.sleep(100);
					if(i2==11)
					{
						i2=0;

					}

					if(texview!=null)
					{
						//tex=":"+Integer.toString(i2);

						texview.setText(tex+": "+Integer.toString(i2)+"\n\n"+"   "+str1+"   "+str2+"   "+str3+"   "+str4+"   "+str5+"   "+str6+"   "+str7+"   "+str8+"   "+str9+"   "+str10+"   "+str11+"   "+str12+"   "+str13+"   "+str14+"   "+str15+"   "+str16+"   "+str17+"   "+str18+"   "+str19+"   "+str20);
						
					}
					
				}
			
			
			
			
		}
		
		
	};
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}



