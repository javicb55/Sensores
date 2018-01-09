package es.vcarmen.sensores5;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class Sensores5 extends AppCompatActivity implements SensorEventListener{

    double contador = 0;

    double azimut = 0;
    double vertical = 0;
    double lateral = 0;
    String orientacion;
    SensorManager sensorManager;
    Sensor orienta;

    TextView azi,ver,lat,ori,cont;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensores5);


        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        orienta = sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);
        sensorManager.registerListener(this, orienta, sensorManager.SENSOR_DELAY_NORMAL);


        azi = (TextView) findViewById(R.id.azimut);
        ver = (TextView) findViewById(R.id.verticalidad);
        lat = (TextView) findViewById(R.id.lateralidad);
        ori = (TextView) findViewById(R.id.orientacion);
        cont = (TextView) findViewById(R.id.lecturas);


        new MiAsyncTask().execute();
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, orienta, sensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        azimut = sensorEvent.values[0];
        vertical = sensorEvent.values[1];
        lateral = sensorEvent.values[2];
        contador++;
        if(azimut > 22) orientacion = "NORTE";
        else if(azimut < 67) orientacion = "NORESTE";
        else if(azimut < 112) orientacion = "ESTE";
        else if(azimut < 157) orientacion = "SURESTE";
        else if(azimut < 202) orientacion = "SUR";
        else if(azimut < 247) orientacion = "SUROESTE";
        else if(azimut < 292) orientacion = "OESTE";
        else if(azimut < 337) orientacion = "NORESTE";
        else orientacion = "NORTE";

        if(vertical < -50) orientacion = "VERTICAL ARRIBA";
        if(vertical >  50) orientacion = "VERTICAL ABAJO";
        if(vertical >  50) orientacion = "VERTICAL IZQUIERDA";
        if(vertical <  50) orientacion = "VERTICAL DERECHO";
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    class MiAsyncTask extends AsyncTask<Void,Void,Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            while (true){
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                contador++;
                publishProgress();
            }

        }
        protected void onProgressUpdate(Void... progress){
            azi.setText("" + azimut);
            ver.setText("" + vertical);
            lat.setText("" + lateral);
            ori.setText("" + orientacion);
            cont.setText("" + contador);
        }
    }
}
