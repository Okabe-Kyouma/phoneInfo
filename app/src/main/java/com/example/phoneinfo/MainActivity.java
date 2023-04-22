package com.example.phoneinfo;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.ApplicationErrorReport;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.Camera;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.BatteryManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Debug;
import android.os.Environment;
import android.os.StatFs;
import android.telephony.TelephonyManager;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity {


    //Declaring TextViews:

    TextView manufacturer,model_Name,model_Number,total_Ram,available_Ram,total_Storage,free_Storage,battery,android_Version
            ,cpu,gpu,gyroscope,accelerometer,rotation_Vector,proximity,ambient_Light_Sensor;


    //This is for Battery level:

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL,0);

            battery.setText(String.valueOf(level) + "%");

        }
    };

    @SuppressLint({"MissingInflatedId", "SetTextI18n"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //Initializing fields

        manufacturer = findViewById(R.id.manufacturer);
        model_Name = findViewById(R.id.Model_name);
        model_Number = findViewById(R.id.Model_number);
        total_Ram = findViewById(R.id.Total_ram);
        available_Ram = findViewById(R.id.Available_ram);
        total_Ram = findViewById(R.id.Total_ram);
        total_Storage = findViewById(R.id.Total_storage);
        free_Storage = findViewById(R.id.Free_Storage);
        battery = findViewById(R.id.Battery_level);
        android_Version = findViewById(R.id.Android_version);
        cpu = findViewById(R.id.cpu);
        gpu  = findViewById(R.id.gpu);
        gyroscope = findViewById(R.id.gyroscope);
        accelerometer = findViewById(R.id.accelerometer);
        rotation_Vector = findViewById(R.id.Rotation_vector);
        proximity = findViewById(R.id.proximity);
        ambient_Light_Sensor = findViewById(R.id.Ambient_light);




        //Setting up Battery,Ram,Storage,Sensors



        //For Ram:

        ActivityManager activityManager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);

        ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();

        activityManager.getMemoryInfo(memoryInfo);



        //For External Storage:

        StatFs statFs = new StatFs(Environment.getExternalStorageDirectory().getPath());

        this.registerReceiver(this.broadcastReceiver,new IntentFilter(Intent.ACTION_BATTERY_CHANGED));



        //For Sensors:

        SensorManager sensorManager;

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        String gyro = null,accel = null,vector = null,pro = null,light = null;

        if(sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE)!=null){
            Sensor sot = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
          gyro = String.valueOf(sot.getPower());
        }

        if(sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)!=null){
            Sensor sot = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            accel = String.valueOf(sot.getPower());
        }

        if(sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR)!=null){
            Sensor sot = sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);
            vector = String.valueOf(sot.getPower());
        }

        if(sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY)!=null){
            Sensor sot = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
            pro = String.valueOf(sot.getPower());
        }

        if(sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE)!=null){
            Sensor sot = sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);
            light = String.valueOf(sot.getPower());
        }




        //Setting Text to their respective fields

        manufacturer.setText(Build.BRAND);
        model_Name.setText(Build.MODEL);
        model_Number.setText(Build.DEVICE);
       total_Ram.setText(String.valueOf(memoryInfo.totalMem).substring(0,1) + "." + String.valueOf(memoryInfo.totalMem).substring(1,2) + " GB");
       available_Ram.setText(String.valueOf(memoryInfo.availMem).substring(0,1) + "." + String.valueOf(memoryInfo.availMem).substring(1,2) + " GB");
       total_Storage.setText(String.valueOf(statFs.getTotalBytes()).substring(0,2) + " GB");
       free_Storage.setText(String.valueOf(statFs.getAvailableBytes()).substring(0,2) + " GB");
       android_Version.setText(Build.VERSION.RELEASE);
       cpu.setText(Build.SOC_MODEL);
       gpu.setText(Build.SUPPORTED_ABIS[0]);
       gyroscope.setText(gyro);
       accelerometer.setText(accel);
       rotation_Vector.setText(vector);
       proximity.setText(pro);
       ambient_Light_Sensor.setText(light);






    }




}