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

        Button clear = findViewById(R.id.clear);
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                Log.i(TAG, "Boton C pulsado");
                text.setText("");
                count = 0;
            }
        });

        Button delete = findViewById(R.id.delete);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                Log.i(TAG, "Boton Del pulsado");
                String aux = text.getText().toString();
                if(aux.length() > 0) {
                    text.setText(aux.substring(0, aux.length() - 1));
                    if(aux.charAt(aux.length() - 1) == '.' && count > 0)
                            count--;
                }
            }
        });

        Button add = findViewById(R.id.add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                Log.i(TAG, "Boton + pulsado");
                String aux = text.getText().toString();
                if(aux.length() > 0 && !aux.contains("+") && Character.isDigit(aux.charAt(aux.length() - 1))) {
                    text.setText(aux + "+");
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
                    String res;
                    if(aux.contains("+"))
                        res = op2(1);
                    else if(aux.contains("-"))
                        res = op2(2);
                    else if(aux.contains("*"))
                        res = op2(3);
                    else{
                        res = op2(4);
                    }
                    text.setText(aux + "=");
                    result(res);
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
                    if(!aux.contains("+") && !aux.contains("-") && !aux.contains("*")){
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

        Button sub = findViewById(R.id.subtract);
        sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                Log.i(TAG, "Boton - pulsado");
                String aux = text.getText().toString();
                if(aux.length() > 0 && !aux.contains("-") && Character.isDigit(aux.charAt(aux.length() - 1))) {
                    text.setText(aux + "-");
                }
            }
        });

        Button mul = findViewById(R.id.multiply);
        mul.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                Log.i(TAG, "Boton * pulsado");
                String aux = text.getText().toString();
                if(aux.length() > 0 && !aux.contains("*") && Character.isDigit(aux.charAt(aux.length() - 1))) {
                    text.setText(aux + "*");
                }
            }
        });

        Button div = findViewById(R.id.divide);
        div.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                Log.i(TAG, "Boton / pulsado");
                String aux = text.getText().toString();
                if(aux.length() > 0 && !aux.contains("*") && Character.isDigit(aux.charAt(aux.length() - 1))) {
                    text.setText(aux + "/");
                }
            }
        });
    }

    private void addXandY(){
        Log.i(TAG, "Capturando los numeros");
        String first = editTextX.getText().toString();
        Double a = (first.equals("")) ? 0 : Double.parseDouble(first);
        String second = editTextY.getText().toString();
        Double b = (second.equals("")) ? 0 : Double.parseDouble(second);
        Log.i(TAG, "Realizando calculo");
        Double res = Calculator.add(a, b);
        Intent intent = new Intent(this, CalculatorResultActivity.class);
        Log.i(TAG, "Pasando el resultado");
        intent.putExtra("Resultado", Double.toString(a) + "+" + Double.toString(b) + "=" + res);
        startActivity(intent);
    }

    private String op2(int op){
        double res, a, b;
        Log.i(TAG, "Capturando los numeros");
        if(op == 1){
            String[] array = text.getText().toString().split("\\+");
            a = Double.parseDouble(array[0]);
            b = Double.parseDouble(array[1]);
            Log.i(TAG, "Realizando suma");
            res = a + b;
        }
        else if(op == 2){
            String[] array = text.getText().toString().split("\\-");
            a = Double.parseDouble(array[0]);
            b = Double.parseDouble(array[1]);
            Log.i(TAG, "Realizando resta");
            res = a - b;
        }
        else if(op == 3){
            String[] array = text.getText().toString().split("\\*");
            a = Double.parseDouble(array[0]);
            b = Double.parseDouble(array[1]);
            Log.i(TAG, "Realizando multiplicacion");
            res = a * b;
        }
        else{
            String[] array = text.getText().toString().split("\\/");
            a = Double.parseDouble(array[0]);
            b = Double.parseDouble(array[1]);
            Log.i(TAG, "Realizando division");
            if(b == 0)
                return "Division por cero";
            else res = a / b;
        }
        return Double.toString(res);
    }
    private void result(String res){
        Intent intent = new Intent(this, CalculatorResultActivity.class);
        Log.i(TAG, "Pasando el resultado");
        intent.putExtra("Resultado", text.getText().toString() + res);
        startActivity(intent);
    }
}