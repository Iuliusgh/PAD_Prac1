package es.ucm.fdi.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private Calculator calculator;
    private EditText editTextX, editTextY;
    private TextView text;
    private static final String TAG = "MainActivity";
    private int count = 0;

    private int countOp = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "Inicializando variables");
        calculator = new Calculator();
        editTextX = findViewById(R.id.editTextNumberX);
        editTextY = findViewById(R.id.editTextNumberY);
        text = findViewById(R.id.textView);
        text.setText("");
        Button button = findViewById(R.id.sumar);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                Log.i(TAG, "Boton pulsado");
                addXandY();
            }
        });

        Button one = findViewById(R.id.number1);
        one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                Log.i(TAG, "Boton 1 pulsado");
                text.setText(text.getText().toString() + "1");
            }
        });
        Button two = findViewById(R.id.number2);
        two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                Log.i(TAG, "Boton 2 pulsado");
                text.setText(text.getText().toString() + "2");
            }
        });
        Button three = findViewById(R.id.number3);
        three.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                Log.i(TAG, "Boton 3 pulsado");
                text.setText(text.getText().toString() + "3");
            }
        });
        Button four = findViewById(R.id.number4);
        four.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                Log.i(TAG, "Boton 4 pulsado");
                text.setText(text.getText().toString() + "4");
            }
        });
        Button five = findViewById(R.id.number5);
        five.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                Log.i(TAG, "Boton 5 pulsado");
                text.setText(text.getText().toString() + "5");
            }
        });
        Button six = findViewById(R.id.number6);
        six.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                Log.i(TAG, "Boton 6 pulsado");
                text.setText(text.getText().toString() + "6");
            }
        });
        Button seven = findViewById(R.id.number7);
        seven.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                Log.i(TAG, "Boton 7 pulsado");
                text.setText(text.getText().toString() + "7");
            }
        });
        Button eight = findViewById(R.id.number8);
        eight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                Log.i(TAG, "Boton 8 pulsado");
                text.setText(text.getText().toString() + "8");
            }
        });
        Button nine = findViewById(R.id.number9);
        nine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                Log.i(TAG, "Boton 9 pulsado");
                text.setText(text.getText().toString() + "9");
            }
        });

        Button zero = findViewById(R.id.number0);
        zero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                Log.i(TAG, "Boton 0 pulsado");
                text.setText(text.getText().toString() + "0");

            }
        });

        Button add = findViewById(R.id.add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                Log.i(TAG, "Boton + pulsado");
                String aux = text.getText().toString();
                if(countOp == 0 && aux.length() > 0 && !aux.contains("+") && Character.isDigit(aux.charAt(aux.length() - 1))) {
                    text.setText(aux + "+");
                    countOp++;
                }
            }
        });

        Button equal = findViewById(R.id.equal);
        equal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                Log.i(TAG, "Boton = pulsado");
                String aux = text.getText().toString();
                if(aux.length() > 0) {
                    double res, a, b;
                    String[] array = aux.split("\\+");
                    a = Double.parseDouble(array[0]);
                    if(array.length > 1){
                        b = Double.parseDouble(array[1]);
                    }
                    else{
                        b = 0;
                    }
                    Log.i(TAG, "Realizando suma");
                    res = a + b;
                    result(Double.toString(res));
                }
            }
        });

        Button dot = findViewById(R.id.dot);
        dot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                Log.i(TAG, "Boton . pulsado");
                String aux = text.getText().toString();
                if(aux.length() > 0) {
                    if(!aux.contains("+")){
                        if(!aux.contains(".")){
                            text.setText(aux + ".");
                        }
                    }
                    else if(count == 0 && Character.isDigit(aux.charAt(aux.length() - 1))){
                        text.setText(aux + ".");
                        count++;
                    }
                }
            }
        });
    }

    private void addXandY(){
        Log.i(TAG, "Capturando los numeros");
        String first = editTextX.getText().toString();
        double a = (first.equals("")) ? 0 : Double.parseDouble(first);
        String second = editTextY.getText().toString();
        double b = (second.equals("")) ? 0 : Double.parseDouble(second);
        Log.i(TAG, "Realizando calculo");
        double res = Calculator.add(a, b);
        Intent intent = new Intent(this, CalculatorResultActivity.class);
        Log.i(TAG, "Pasando el resultado");
        intent.putExtra("Resultado", Double.toString(res));
        startActivity(intent);
    }

    private void result(String res){
        Intent intent = new Intent(this, CalculatorResultActivity.class);
        Log.i(TAG, "Pasando el resultado");
        intent.putExtra("Resultado", res);
        startActivity(intent);
    }
}