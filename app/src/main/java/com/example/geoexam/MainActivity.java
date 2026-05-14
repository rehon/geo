package com.example.geoexam;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

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
    }
}