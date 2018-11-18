package com.isha.mycalculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class MainActivity extends AppCompatActivity {

    TextView calculation, answer;
    String sCalculation = "", sAnswer = "", number_one = "", number_two = "", current_oprator = "", prev_ans = "";
    Double Result = 0.0, numberOne = 0.0, numberTwo = 0.0, temp = 0.0;

    NumberFormat format, longformate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        calculation=findViewById(R.id.calculation);

        calculation.setMovementMethod(new ScrollingMovementMethod());

        answer=findViewById(R.id.answer);

//        set the answer upto four decimal
        format = new DecimalFormat("#.####");

//        reformat answer if it's long
        longformate = new DecimalFormat("0.#E0");
    }

    public void onClickNumber(View v) {

        Button bn = (Button)v;
        sCalculation+=bn.getText();
        number_one+=bn.getText();
        numberOne = Double.parseDouble(number_one);

        switch (current_oprator) {

            case "":
                temp = Result + numberOne;
                sAnswer = format.format(temp).toString();
                break;

            case "+":
                temp = Result + numberOne;
                sAnswer = format.format(temp).toString();
                break;

            case "-":
                temp = Result - numberOne;
                sAnswer = format.format(temp).toString();
                break;

            case "x":
                temp = Result * numberOne;
                sAnswer = format.format(temp).toString();
                break;

            case "/":
//                divided by 0 case
                try {

                    temp = Result / numberOne;
                    sAnswer = format.format(temp).toString();

                } catch (Exception e){

                    sAnswer=e.getMessage();
                }
                break;
        }

        updateCalculation();
    }


    public void onClickOprator(View v) {

        Button ob = (Button) v;
        //if sAnswer is null means no calculation needed
        if(sAnswer != "") {

            sCalculation += "\n"+ob.getText();
            number_one = "";
            numberOne = 0.0;
            Result = temp;
            temp = 0.0;
            sAnswer =format.format(Result).toString();
            current_oprator = ob.getText().toString();
            updateCalculation();

        }

    }

    public void onClickClear(View v) {
        sCalculation = "";
        sAnswer = "";
        current_oprator = "";
        number_one = "";
//        number_two = "";
//        prev_ans = "";
        Result = 0.0;
        numberOne = 0.0;
//        numberTwo = 0.0;
        temp = 0.0;
        updateCalculation();
    }

    public void updateCalculation() {
        calculation.setText(sCalculation);
        answer.setText(sAnswer);
    }
}
