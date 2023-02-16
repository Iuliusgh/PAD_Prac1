package es.ucm.fdi.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class CalculatorResultActivity extends AppCompatActivity {

    private TextView res;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator_result);

        res = findViewById(R.id.textView);
        Intent i = getIntent();
        if(i != null){
            int r = i.getIntExtra("Resultado",0);
            res.setText(Integer.toString(r));
        }
    }
}