package es.ucm.fdi.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.TextView;

public class CalculatorResultActivity extends AppCompatActivity {

    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator_result);
        textView = findViewById(R.id.result);
        Intent intent = getIntent();
        if(intent != null){
            String result;
            if(!TextUtils.isEmpty(result = intent.getStringExtra("Resultado"))){
                textView.setText(result);
            }
        }
    }
}