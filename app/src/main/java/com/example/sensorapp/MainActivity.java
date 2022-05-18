package com.example.sensorapp;

import android.content.ComponentName;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

    TextView textLIGHT_available, textLIGHT_reading,xTest,yTest,zTest,accs,result;
    float lightValues;
    float accValueX;
    float accValueY;
    float accValueZ;
    SensorManager mySensorManager;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textLIGHT_available
                = (TextView)findViewById(R.id.LIGHT_available);
        textLIGHT_reading
                = (TextView)findViewById(R.id.LIGHT_reading);
        xTest
                = (TextView)findViewById(R.id.xTest);
        yTest
                = (TextView)findViewById(R.id.yTest);
        zTest
                = (TextView)findViewById(R.id.zTest);
       accs
                = (TextView)findViewById(R.id.accs);
        result
                = (TextView)findViewById(R.id.result);

        result.setText("Telefon Durumu");
        SensorManager mySensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);

        Sensor lightSensor = mySensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        Sensor accSensor=mySensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        if(lightSensor != null){
            textLIGHT_available.setText("Sensor.TYPE_LIGHT Available");
            mySensorManager.registerListener(
                    lightSensorListener,
                    lightSensor,
                    SensorManager.SENSOR_DELAY_NORMAL);

        } else {
            textLIGHT_available.setText("Sensor.TYPE_LIGHT NOT Available");
        }
        if(accSensor != null){
            accs.setText("Sensor.TYPE_ACCS Available");
            mySensorManager.registerListener(
                    accSensorListener,
                    accSensor,
                    SensorManager.SENSOR_DELAY_NORMAL);

        } else {
            Toast.makeText(this, "Acc = NULL", Toast.LENGTH_SHORT).show();
            accs.setText("Sensor.TYPE_ACC NOT Available");
        }
    }

   private void info(){



       if (lightValues != 0 && (accValueX > 1 && accValueY > 1)) {
           result.setText("Telefon Hareketli");

       }
       else if(lightValues < 20 && (accValueX < 1 && accValueY <1)){
           result.setText("Telefon Masada");

       } else if(lightValues == 0 && (accValueX > 1 && accValueY > 1)){
           result.setText("Telefon Cepte Hareketli");

       } else if(lightValues == 0 && (accValueX > 1 && accValueY > 1)){
           result.setText("Telefon Cepte Hareketsiz");

       }
       Intent intent=new Intent();
       ComponentName cn=new ComponentName("com.example.musicapp","com.example.musicapp.BroadcastReciever");
       intent.putExtra("result",result.getText());
       intent.setComponent(cn);
       sendBroadcast(intent);
   }



    private final SensorEventListener lightSensorListener
            = new SensorEventListener(){

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onSensorChanged(SensorEvent event) {
            if(event.sensor.getType() == Sensor.TYPE_LIGHT){
                lightValues=event.values[0];
                textLIGHT_reading.setText("LIGHT: " + event.values[0]);
                info();
            }
        }

    };
    private final SensorEventListener accSensorListener
            = new SensorEventListener(){

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onSensorChanged(SensorEvent event) {
            if(event.sensor.getType() == Sensor.TYPE_ACCELEROMETER){
                accValueX=event.values[0];
                accValueY=event.values[1];
                accValueZ=event.values[2];

                xTest.setText("X: " + event.values[0]);
                yTest.setText("Y: " + event.values[1]);
                zTest.setText("Z: " + event.values[2]);
                info();
            }
        }

    };

    @Override
    protected void onDestroy() {
        mySensorManager.unregisterListener(accSensorListener);
        mySensorManager.unregisterListener(lightSensorListener);
        super.onDestroy();
    }
}


