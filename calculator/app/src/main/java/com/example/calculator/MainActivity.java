package com.example.calculator;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText result;
    private EditText newNumber;
    private TextView displayOperation;

    private Double operand1 = null;
    private Double operand2 = null;
    private String pendingOperation = "=";
    private static final String STATE_PENDING_OPERATION = "pending operation";
    private static final String STATE_OPERAND1 = "Operand1";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        result = (EditText)findViewById(R.id.result);
        newNumber = (EditText)findViewById(R.id.newNumber);
        displayOperation = (TextView)findViewById(R.id.operation);

        Button button0 = (Button)findViewById(R.id.button0);
        Button button1 = (Button)findViewById(R.id.button1);
        Button button2 = (Button)findViewById(R.id.button2);
        Button button3 = (Button)findViewById(R.id.button3);
        Button button4 = (Button)findViewById(R.id.button4);
        Button button5 = (Button)findViewById(R.id.button5);
        Button button6 = (Button)findViewById(R.id.button6);
        Button button7 = (Button)findViewById(R.id.button7);
        Button button8 = (Button)findViewById(R.id.button8);
        Button button9 = (Button)findViewById(R.id.button9);
        Button buttonAdd = (Button)findViewById(R.id.buttonAdd);
        Button buttonSubtract = (Button)findViewById(R.id.buttonSubstract);
        Button buttonDivide = (Button)findViewById(R.id.buttonDivide);
        Button buttonMultiply = (Button)findViewById(R.id.buttonMultiply);
        Button buttonDot = (Button)findViewById(R.id.buttonDot);
        Button buttonEqual = (Button)findViewById(R.id.buttonEqual);
        Button buttonNeg = (Button)findViewById(R.id.buttonNeg);

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button b = (Button) v;
                newNumber.append(b.getText().toString());

            }
        };
        button0.setOnClickListener(listener);
        button1.setOnClickListener(listener);
        button2.setOnClickListener(listener);
        button3.setOnClickListener(listener);
        button4.setOnClickListener(listener);
        button5.setOnClickListener(listener);
        button6.setOnClickListener(listener);
        button7.setOnClickListener(listener);
        button8.setOnClickListener(listener);
        button9.setOnClickListener(listener);
        buttonDot.setOnClickListener(listener);

        View.OnClickListener opListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button b = (Button) v;
                String op = b.getText().toString();
                String value = newNumber.getText().toString();
                try {
                    Double doubleValue = Double.valueOf(value);
                    performOperation(doubleValue, op);
                }catch (NumberFormatException e){
                    newNumber.setText("");
                }
                displayOperation.setText(op);
                pendingOperation = op;
            }
        };
        buttonEqual.setOnClickListener(opListener);
        buttonAdd.setOnClickListener(opListener);
        buttonSubtract.setOnClickListener(opListener);
        buttonMultiply.setOnClickListener(opListener);
        buttonDivide.setOnClickListener(opListener);


        View.OnClickListener negListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button b = (Button) v;
                String value = newNumber.getText().toString();
                if(value.length() == 0){
                        newNumber.setText("-");
                }else{
                    try{
                        Double doubleValue = Double.valueOf(value) * -1;
                        newNumber.setText(doubleValue.toString());
                    }catch (NumberFormatException e) {
                        newNumber.setText("");
                    }
                }

            }
        };
        buttonNeg.setOnClickListener(negListener);
    }



    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putString( STATE_PENDING_OPERATION, pendingOperation);
        if (operand1 != null){
            outState.putDouble(STATE_OPERAND1, operand1);
        }
        super.onSaveInstanceState(outState);
    }


    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        pendingOperation = savedInstanceState.getString(STATE_PENDING_OPERATION);
        operand1 = savedInstanceState.getDouble(STATE_OPERAND1);
        displayOperation.setText(pendingOperation);
    }

    private void performOperation(Double value, String opt){
        displayOperation.setText(opt);
        if (operand1 == null){
            operand1 = value;
        } else{
            operand2 = value;

            if(pendingOperation.equals("equal")){
                pendingOperation = opt;
            }
            switch (pendingOperation){
                case "=":
                    operand1 = operand2;
                    break;

                case "+":
                    operand1 += operand2;
                    break;
                case "-":
                    operand1 -= operand2;
                    break;
                case "x":
                    operand1 *= operand2;
                    break;
                case "/":
                    if (operand2 == 0){
                        operand1 = 0.0;
                    }else{
                        operand1 /= operand2;
                    }

            }
        }
        result.setText(operand1.toString());
        newNumber.setText("");



    }
}