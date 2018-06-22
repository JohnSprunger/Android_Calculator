package com.example.johnl.calculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private EditText result;
    private EditText newNumber;
    private TextView displayOperation;

    // Variables to hold the operands and the type of calculations
    private Double operand1 = null;
    private String pendingOp = "=";

    private static final String STATE_PENDING_OPERATION = "PendingOp";
    private static final String STATE_OPERAND1 = "Operand1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        result = (EditText) findViewById(R.id.result);
        newNumber = (EditText) findViewById(R.id.newNumber);
        displayOperation = (TextView) findViewById(R.id.operation);

        // Number Buttons
        Button button0 = (Button) findViewById(R.id.button0);
        Button button1 = (Button) findViewById(R.id.button1);
        Button button2 = (Button) findViewById(R.id.button2);
        Button button3 = (Button) findViewById(R.id.button3);
        Button button4 = (Button) findViewById(R.id.button4);
        Button button5 = (Button) findViewById(R.id.button5);
        Button button6 = (Button) findViewById(R.id.button6);
        Button button7 = (Button) findViewById(R.id.button7);
        Button button8 = (Button) findViewById(R.id.button8);
        Button button9 = (Button) findViewById(R.id.button9);
        Button buttonDot = (Button) findViewById(R.id.buttonDot);

        // Operation Buttons
        Button buttonAdd = (Button) findViewById(R.id.buttonAdd);
        Button buttonDiv = (Button) findViewById(R.id.buttonDiv);
        Button buttonEql = (Button) findViewById(R.id.buttonEql);
        Button buttonMin = (Button) findViewById(R.id.buttonMin);
        Button buttonMul = (Button) findViewById(R.id.buttonMul);
        Button buttonNeg = (Button) findViewById(R.id.buttonNeg);

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button b = (Button) v;
                newNumber.append(b.getText().toString());
            }
        };

        // Set on click listeners for numerical buttons
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
                try{
                    Double doubleValue = Double.valueOf(value);
                    performOperation(doubleValue, op);
                } catch(NumberFormatException e){
                    newNumber.setText("");
                }
                pendingOp = op;
                displayOperation.setText(pendingOp);
            }
        };

        // Set on click listeners for operation buttons
        buttonAdd.setOnClickListener(opListener);
        buttonDiv.setOnClickListener(opListener);
        buttonEql.setOnClickListener(opListener);
        buttonMin.setOnClickListener(opListener);
        buttonMul.setOnClickListener(opListener);

        buttonNeg.setOnClickListener(new View.OnClickListener(){
           @Override
           public void onClick(View view){
               String value = newNumber.getText().toString();
               if(value.length() == 0){
                   newNumber.setText("-");
               }
               else{
                   try{
                       Double doubleValue = Double.valueOf(value);
                       doubleValue *= -1;
                       newNumber.setText(doubleValue.toString());
                   } catch(NumberFormatException e){
                       newNumber.setText("");
                   }
               }
           }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString(STATE_PENDING_OPERATION, pendingOp);
        if(operand1 != null){
            outState.putDouble(STATE_OPERAND1, operand1);
        }
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        pendingOp = savedInstanceState.getString(STATE_PENDING_OPERATION);
        operand1 = savedInstanceState.getDouble(STATE_OPERAND1);
        displayOperation.setText(pendingOp);
    }

    private void performOperation(Double value, String op) {
        if (null == operand1) {
            operand1 = value;
        } else {
            if (pendingOp.equals('=')) {
                pendingOp = op;
            }

            switch (pendingOp) {
                case "=":
                    operand1 = value;
                    break;
                case "/":
                    if (value == 0) {
                        operand1 = 0.0;
                    } else {
                        operand1 /= value;
                    }
                    break;
                case "*":
                    operand1 *= value;
                    break;
                case "+":
                    operand1 += value;
                    break;
                case "-":
                    operand1 -= value;
                    break;
            }
        }

        result.setText(operand1.toString());
        newNumber.setText("");
    }





}
