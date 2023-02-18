package es.ucm.fdi.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    private Calculator calculator;
    private EditText editTextX,editTextY;
    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d(TAG,"onCreate");

        calculator = new Calculator();
        editTextX = findViewById(R.id.editTextNumberDecimal);
        editTextY = findViewById(R.id.editTextNumberDecimal2);
        Button bot = findViewById(R.id.button);

        bot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG,"addXandY llamado");
                addXandY();
            }
        });
    }

   private void addXandY(){
        Log.v(TAG,"addXandY");
        int x = Integer.parseInt(editTextX.getText().toString());
        int y = Integer.parseInt(editTextY.getText().toString());
        int resultant = calculator.suma(x,y);
        Log.d(TAG,"Resultado:"+resultant);
        Intent i = new Intent(this,CalculatorResultActivity.class);
        i.putExtra("Resultado",resultant);
        startActivity(i);
   }
}