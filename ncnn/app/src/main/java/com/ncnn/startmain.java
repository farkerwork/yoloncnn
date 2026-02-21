package com.ncnn;
import com.tencent.ncnnyolox.NcnnYolox;
import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;
import android.widget.LinearLayout;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.graphics.Color;
import android.view.View;
import android.content.Intent;
import android.widget.Toast;
import android.transition.AutoTransition;
import android.os.*;
import android.view.ViewDebug.IntToString;
import android.content.pm.PackageManager;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.util.Log;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
public class startmain 
{
	public static final int REQUEST_CAMERA = 100;

    private NcnnYolox ncnnyolox = new NcnnYolox();
    private static int facing = 0;

    private Spinner spinnerModel;
    private Spinner spinnerCPUGPU;
    private int current_model = 0;
    private int current_cpugpu = 0;

    private SurfaceView cameraView;
    
    public static void yolo(Activity activity)
	{
		framui ui= new framui(activity);


		ui.creatwindow(0xCC1E88E5,"悬浮窗口",250,350,40);

	

	    TextView button1=ui.creatbutton(Color.BLUE,"打开相机",3,25,60,35);

        button1.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View arg0) {

					int new_facing = 1 - facing;

				    NcnnYolox yolox=new NcnnYolox();

					yolox.closeCamera();

					yolox.openCamera(new_facing);

					facing = new_facing;
				}
			});

		ui.show();

		
		
		
		
		
		
		
		

		
		
		
	}
   
	
	
	
	
	
	public static int dpToPx(Activity activit,int dp) 
	{
        if (activit == null) return dp;
        float density = activit.getResources().getDisplayMetrics().density;
        return Math.round(dp * density);
		}
		
		
		
		
		
		
		
}
