package com.example.geoexam;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class MainActivity extends AppCompatActivity {

    private EditText user_input;
    private Button main_btn;
    private TextView user_history;
    private TextView result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        user_input = findViewById(R.id.user_input);
        main_btn = findViewById(R.id.main_btn);
        user_history = findViewById(R.id.user_history);
        result = findViewById(R.id.result);

        StringBuilder history_string = new StringBuilder();

        main_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(user_input.getText().toString().equals("")){
                    String no_user_input = "Введите пожалуйсто запрос";
                    Toast.makeText(MainActivity.this, no_user_input, Toast.LENGTH_LONG).show();
                }
                else{
                    String qury = user_input.getText().toString();
                    String key = "cea4eb40-3ffa-4cca-a365-7cac27e48127";
                    String url = "https://suggest-maps.yandex.ru/v1/suggest?text=" + qury + "&lang=ru&apikey=" + key;

                    history_string.append(qury).append(", ");

                    user_history.setText(history_string);

                    new GetDataUrl().execute(url);
                }
            }
        });
    }

    protected class GetDataUrl extends AsyncTask<String, String, String> implements com.example.geoexam.GetDataUrl {

        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            result.setText("Загрузка данных...");
        }


        @Override
        protected String doInBackground(String... strings) {
            HttpURLConnection connection = null;
            BufferedReader reader = null;

            try {
                URL url = new URL(strings[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();

                InputStream stream = connection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(stream));

                StringBuilder buffer = new StringBuilder();
                String line = "";

                while ((line = reader.readLine()) != null){
                    buffer.append(line).append("\n");
                }

                return buffer.toString();
            } catch (MalformedURLException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        @Override
        protected void onPostExecute(String resulst){
            super.onPostExecute(resulst);

            try {
                JSONObject jsonObject = new JSONObject(resulst);
                JSONArray jsonArray = jsonObject.getJSONArray("results");
                JSONObject jsonObjectItem = jsonArray.getJSONObject(0);
                result.setText(jsonObjectItem.getJSONObject("title").getString("text") + ", " + jsonObjectItem.getJSONObject("subtitle").getString("text"));
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }
    }
}