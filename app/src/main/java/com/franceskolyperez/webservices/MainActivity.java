package com.franceskolyperez.webservices;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    Button cargar;
    TextView texto;
    ProgressBar progressBar;
    List<MyTask> taskList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cargar = (Button) findViewById(R.id.cargar_datos_btn);
        texto = (TextView) findViewById(R.id.texto_edt);
        progressBar=(ProgressBar) findViewById(R.id.progress_circular_pb);
        texto.setMovementMethod(new ScrollingMovementMethod());

        taskList = new ArrayList<>();

        cargar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                MyTask myTask = new MyTask();
                //Hilos trabajando en forma paralela
                myTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

            }
        });
    }

    private void cargarDatos(String datos) {
        texto.append(datos + "\n");

    }

    private class MyTask extends AsyncTask<String, String, String>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            cargarDatos("Inicio de carga");
            if(taskList.size()==0) {
                progressBar.setVisibility(View.VISIBLE);
            }
            taskList.add(this);
        }

        @Override
        protected String doInBackground(String... strings) {
                for(int i=1;i<=15;i++){
                    publishProgress("numero: "+i);
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            return "Terminado";
        }


        @Override
        protected void onProgressUpdate(String... values) {
            cargarDatos(values[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            cargarDatos(s);
            taskList.remove(this);

            if(taskList.size()==0) {
                progressBar.setVisibility(View.GONE);
            }
        }


    }

}
