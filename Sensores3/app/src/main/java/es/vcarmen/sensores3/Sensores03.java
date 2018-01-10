package es.vcarmen.sensores3;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.Serializable;

public class Sensores03 extends AppCompatActivity implements SensorEventListener, Serializable{

    int contador;

    double x = 0;
    double y = 0;
    double z = 0;

    TextView tvax, tvay, tvaz, cont;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensores03);

        tvax = (TextView) findViewById(R.id.texto_x);
        tvay = (TextView) findViewById(R.id.texto_y);
        tvaz = (TextView) findViewById(R.id.texto_z);
        cont = (TextView) findViewById(R.id.contador);

        SensorManager sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        Sensor gyro = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        sensorManager.registerListener(this, gyro, SensorManager.SENSOR_DELAY_FASTEST);

        new MiAsyncTask().execute();

    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        double x = event.values[0];
        double y = event.values[0];
        double z = event.values[0];
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    class MiAsyncTask extends AsyncTask<Void,Void,Void>{

        @Override
        protected Void doInBackground(Void... voids) {
            while(true) {
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
            cont.setText("Contador" + contador);
        }
    }
}
