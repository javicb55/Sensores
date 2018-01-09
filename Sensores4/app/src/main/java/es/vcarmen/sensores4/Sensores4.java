package es.vcarmen.sensores4;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class Sensores4 extends AppCompatActivity implements SensorEventListener{

    int contador = 0;
    boolean continuar = true;
    double x = 0;
    double y = 0;
    double z = 0;
    double a = 0;
    double amax = 0;
    SensorManager sensorManager;

    double campoTierraMax = SensorManager.MAGNETIC_FIELD_EARTH_MAX;
    double campoTierraMin = SensorManager.MAGNETIC_FIELD_EARTH_MIN;

    TextView tvax,tvay, tvaz, tva, tvaMax, tvG, cont;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensores4);

        tvax   = (TextView) findViewById(R.id.campo_x);
        tvay   = (TextView) findViewById(R.id.campo_y);
        tvaz   = (TextView) findViewById(R.id.campo_z);
        tva    = (TextView) findViewById(R.id.campo_magnetico);
        tvaMax = (TextView) findViewById(R.id.campo_terrestre);
        tvG    = (TextView) findViewById(R.id.campo_tierra);
        cont   = (TextView) findViewById(R.id.contador);

        sensorManager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
        Sensor campo = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        sensorManager.registerListener(this, campo, sensorManager.SENSOR_DELAY_NORMAL);

        new MiAsyncTask().execute();
    }

    protected void onResume(){
        super.onResume();
        continuar = true;
        new MiAsyncTask().execute();
    }

    @Override
    protected void onPause() {
        super.onPause();
        continuar = false;
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {

        y = sensorEvent.values[0];
        x = sensorEvent.values[1];
        z = sensorEvent.values[2];
        a = Math.sqrt(x * x + y * y + z * z);

        if (a > amax) amax = a;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    class MiAsyncTask extends AsyncTask<Void,Void,Void>{

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
            tvax.setText("" + x);
            tvay.setText("" + y);
            tvaz.setText("" + z);
            tva.setText("" + a);
            tvaMax.setText("" + amax);
            tvG.setText ("" + campoTierraMin + "-" +campoTierraMax);
            cont.setText("contador" + contador);
        }
    }
}
