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
    Boolean dot_present = false, number_allow = true, root_present = false, invert_allow = true, power_present = false;
    Boolean factorial_present = false, constant_present = false, function_present = false, value_inverted = false;

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

        if (number_allow) {
            Button bn = (Button)v;
            sCalculation+=bn.getText();
            number_one+=bn.getText();
            numberOne = Double.parseDouble(number_one);

            //check rook is present or not
            if (root_present) {
                numberOne = Math.sqrt(numberOne);
            }

            switch (current_oprator) {

                case ""://if current oprator is null
                    if (power_present) {
                        temp = Result + Math.pow(numberTwo, numberOne);
                    } else {
                        temp = Result + numberOne;
                    }
                    break;

                case "+":
                    if (power_present) {
                        temp = Result + Math.pow(numberTwo, numberOne);
                    } else {
                        temp = Result + numberOne;
                    }
                    break;

                case "-":
                    if (power_present) {
                        temp = Result - Math.pow(numberTwo, numberOne);
                    } else {
                        temp = Result - numberOne;
                    }
                    break;

                case "x":
                    if (power_present) {
                        temp = Result * Math.pow(numberTwo, numberOne);
                    } else {
                        temp = Result * numberOne;
                    }
                    break;

                case "/":
                    try {
                        // divided by 0 cause execption
                        if (power_present) {
                            temp = Result / Math.pow(numberTwo, numberOne);
                        } else {
                            temp = Result / numberOne;
                        }
                    } catch (Exception e) {
                        sAnswer = e.getMessage();
                    }
                    break;

            }

            sAnswer = format.format(temp).toString();
            updateCalculation();
        }
    }

    public void onClickOprator(View v) {

        Button ob = (Button) v;
        //if sAnswer is null means no calculation needed
        if(sAnswer != "") {

            //check last char is operator or not
            if (current_oprator != "") {
                char c = getcharfromLast(sCalculation, 2);// 2 is the char from last because our las char is " "
                if (c == '+' || c == '-' || c == 'x' || c == '/') {
                    sCalculation = sCalculation.substring(0, sCalculation.length() - 3);
                }
            }
            sCalculation += "\n"+ob.getText();
            number_one = "";
            numberOne = 0.0;
            Result = temp;
            temp = 0.0;
            sAnswer =format.format(Result).toString();
            current_oprator = ob.getText().toString();
            updateCalculation();

//          when operator cjick dot is not present in the number_one
            number_two="";
            numberTwo=0.0;
            dot_present = false;
            number_allow = true;
            root_present = false;
            invert_allow = true;
            power_present = false;
            function_present = false;
            constant_present = false;
        }

    }

    private char getcharfromLast(String s, int i) {
        char c = s.charAt(s.length() - i);
        return c;
    }

    public void onClickClear(View v) {
        sCalculation = "";
        sAnswer = "";
        current_oprator = "";
        number_one = "";
        number_two = "";
        prev_ans = "";
        Result = 0.0;
        numberOne = 0.0;
        numberTwo = 0.0;
        temp = 0.0;
        updateCalculation();
        dot_present = false;
        number_allow = true;
        root_present = false;
        invert_allow = true;
        power_present = false;
        function_present = false;
        constant_present = false;
    }

    public void updateCalculation() {
        calculation.setText(sCalculation);
        answer.setText(sAnswer);
    }

    public void onDotClick(View view) {
        //create boolean dot_present check if dot is present or not.
        if (!dot_present) {
            //check length of numberone
            if (number_one.length() == 0) {
                number_one = "0.";
                sCalculation += "0.";
                sAnswer = "0.";
                dot_present = true;
                updateCalculation();
            } else {
                number_one += ".";
                sCalculation += ".";
                sAnswer += ".";
                dot_present = true;
                updateCalculation();
            }
        }
    }

    public void onClickEqual(View view) {
        showresult();
    }

    public void showresult() {
        if (sAnswer != "" && sAnswer != prev_ans) {
            sCalculation += "\n= " + sAnswer + "\n----------\n" + sAnswer + " ";
            number_one = "";
            number_two = "";
            numberTwo = 0.0;
            numberOne = 0.0;
            Result = temp;
            prev_ans = sAnswer;
            updateCalculation();
            //we  don't allow to edit our ans so
            dot_present = true;
            power_present = false;
            number_allow = false;
            factorial_present = false;
            constant_present = false;
//            function_present = false;
//            value_inverted = false;
        }
    }

    public void onModuloClick(View view) {

        if (sAnswer != "" && getcharfromLast(sCalculation, 1) != ' ') {
            sCalculation += "% ";
            //value of temp will change according to the operator
            switch (current_oprator) {
                case "":
                    temp = temp / 100;
                    break;
                case "+":
                    temp = Result + ((Result * numberOne) / 100);
                    break;
                case "-":
                    temp = Result - ((Result * numberOne) / 100);
                    break;
                case "x":
                    temp = Result * (numberOne / 100);
                    break;
                case "/":
                    try {
                        temp = Result / (numberOne / 100);
                    } catch (Exception e) {
                        sAnswer = e.getMessage();
                    }
                    break;
            }
            sAnswer = format.format(temp).toString();
            if (sAnswer.length() > 9) {
                sAnswer = longformate.format(temp).toString();
            }
            Result = temp;
            //now we show the result
            showresult();

        }
    }

    public void onPorMClick(View view) {
        if (invert_allow) {
            if (sAnswer != "" && getcharfromLast(sCalculation, 1) != ' ') {
                numberOne = numberOne * (-1);
                number_one = format.format(numberOne).toString();
                switch (current_oprator) {
                    case "":
                        temp = numberOne;
                        sCalculation = number_one;
                        break;
                    case "+":
                        temp = Result + numberOne;
                        //we need to add - sign in the starting of the string
                        removeuntilchar(sCalculation, ' ');
                        sCalculation += number_one;
                        break;
                    case "-":
                        temp = Result - numberOne;
                        //we need to add - sign in the starting of the string
                        removeuntilchar(sCalculation, ' ');
                        sCalculation += number_one;
                        break;
                    case "*":
                        temp = Result * numberOne;
                        //we need to add - sign in the starting of the string
                        removeuntilchar(sCalculation, ' ');
                        sCalculation += number_one;
                        break;
                    case "/":
                        try {
                            temp = Result / numberOne;
                            //we need to add - sign in the starting of the string
                            removeuntilchar(sCalculation, ' ');
                            sCalculation += number_one;
                        } catch (Exception e) {
                            sAnswer = e.getMessage();
                        }
                        break;
                }
                sAnswer = format.format(temp).toString();
//                value_inverted = value_inverted ? false : true;
                updateCalculation();
            }
        }
    }

    public void removeuntilchar(String str, char chr) {
        char c = getcharfromLast(str, 1);
        if (c != chr) {
            //remove last char
            str = removechar(str, 1);
            sCalculation = str;
            updateCalculation();
            removeuntilchar(str, chr);
        }
    }

    public String removechar(String str, int i) {
        char c = str.charAt(str.length() - i);
        //we need to check if dot is removed or not
        if (c == '.' && !dot_present) {
            dot_present = false;
        }
        if (c == '^') {
            power_present = false;
        }
        if (c == ' ') {
            return str.substring(0, str.length() - (i - 1));
        }
        return str.substring(0, str.length() - i);
    }

    public void onRootClick(View view) {
        Button root = (Button) view;
        //first check if root is present or not
        if (sAnswer == "" && Result == 0 && !root_present) {
            sCalculation = root.getText().toString();
            root_present = true;
            invert_allow = false;
            updateCalculation();
        } else if (getcharfromLast(sCalculation, 1) == ' ' && current_oprator != "" && !root_present) {
            sCalculation += root.getText().toString();
            root_present = true;
            invert_allow = false;
            updateCalculation();
        }

    }

    public void onPowerClick(View view) {
        Button power = (Button) view;
        if (sCalculation != "" && !root_present && !power_present) {
            if (getcharfromLast(sCalculation, 1) != ' ') {
                sCalculation += power.getText().toString();
                //Need second variable for the power
                number_two = number_one;
                numberTwo = numberOne;
                number_one = "";
                power_present = true;
                updateCalculation();
            }
        }
    }

    public void onSquareClick(View view) {

        if (sCalculation != "" && sAnswer != "") {
            if (!root_present  && !power_present && getcharfromLast(sCalculation, 1) != ' ' && getcharfromLast(sCalculation, 1) != ' ') {
                numberOne = numberOne * numberOne;
                number_one = format.format(numberOne).toString();
                if (current_oprator == "") {
                    if (number_one.length() > 9) {
                        number_one = longformate.format(numberOne);
                    }
                    sCalculation = number_one;
                    temp = numberOne;
                } else {
                    switch (current_oprator) {
                        case "+":
                            temp = Result + numberOne;
                            break;
                        case "-":
                            temp = Result - numberOne;
                            break;
                        case "x":
                            temp = Result * numberOne;
                            break;
                        case "/":
                            try {
                                temp = Result / numberOne;
                            } catch (Exception e) {
                                sAnswer = e.getMessage();
                            }
                            break;
                    }
                    removeuntilchar(sCalculation, ' ');
                    if (number_one.length() > 9) {
                        number_one = longformate.format(numberOne);
                    }
                    sCalculation += number_one;
                }
                sAnswer = format.format(temp);
                if (sAnswer.length() > 9) {
                    sAnswer = longformate.format(temp);
                }
                updateCalculation();
            }
        }
    }

    public void onclickFactorial(View view) {

        if (!sAnswer.equals("") && !factorial_present && !root_present && !dot_present && !power_present && !function_present) {
            if (getcharfromLast(sCalculation, 1) != ' ') {
                for (int i = 1; i < Integer.parseInt(number_one); i++) {
                    numberOne *= i;
                }
                if (numberOne.equals(0.0)) {
                    numberOne = 1.0;
                }
                number_one = format.format(numberOne).toString();
                switch (current_oprator) {
                    case "":
                        Result = numberOne;
                        break;
                    case "+":
                        Result += numberOne;
                        break;
                    case "-":
                        Result -= numberOne;
                        break;
                    case "x":
                        Result *= numberOne;
                        break;
                    case "/":
                        try {
                            Result /= numberOne;
                        } catch (Exception e) {
                            sAnswer = e.getMessage();
                        }

                        break;
                }
                sAnswer = Result.toString();
                temp = Result;
                sCalculation += "! ";
                factorial_present = true;
                number_allow = false;
                updateCalculation();
            }
        }
    }

    public void onclickInverse(View view) {

        if (!sAnswer.equals("") && !factorial_present && !root_present && !dot_present && !power_present && !function_present) {
            if (getcharfromLast(sCalculation, 1) != ' ') {
                numberOne = Math.pow(numberOne, -1);
                number_one = format.format(numberOne).toString();

                switch (current_oprator) {
                    case "":
                        temp = numberOne;
                        sCalculation = number_one;
                        break;
                    case "+":
                        temp = Result + numberOne;
                        removeuntilchar(sCalculation, ' ');
                        sCalculation += number_one;
                        break;
                    case "-":
                        temp = Result - numberOne;
                        removeuntilchar(sCalculation, ' ');
                        sCalculation += number_one;
                        break;
                    case "x":
                        temp = Result * numberOne;
                        removeuntilchar(sCalculation, ' ');
                        sCalculation += number_one;
                        break;
                    case "/":
                        try {
                            temp = Result / numberOne;
                            removeuntilchar(sCalculation, ' ')
                            sCalculation += number_one;
                        } catch (Exception e) {
                            sAnswer = e.getMessage();
                        }

                        break;
                }
                sAnswer = format.format(temp).toString();
                updateCalculation();
            }
        }
    }

    public void onClickPIorE(View view) {

        Button btn_PIorE = (Button) view;
        number_allow = false;
        if (!root_present && !dot_present && !power_present && !factorial_present && !constant_present && !function_present) {
            String str_PIorE = btn_PIorE.getText().toString() + " ";

            if (!str_PIorE.equals("e ")) {
                str_PIorE = "\u03A0" + " "
            }

            if (sCalculation == "") {
                number_one = str_PIorE;
                if (str_PIorE.equals("e ")) {
                    numberOne = Math.E;
                } else {
                    numberOne = Math.PI
                }
                temp = numberOne;
            } else {
                if (str_PIorE.equals("e ")) {
                    //use ternary operation
                    numberOne = getcharfromLast(sCalculation, 1) == ' ' ? Math.E : Double.parseDouble(number_one) * Math.E;
                } else {
                    numberOne = getcharfromLast(sCalculation, 1) == ' ' ? Math.PI : Double.parseDouble(number_one) * Math.PI;
                }
                switch (current_oprator) {

                    case "":
                        temp = Result + numberOne;
                        break;

                    case "+":
                        temp = Result + numberOne;
                        break;

                    case "-":
                        temp = Result - numberOne;
                        break;

                    case "x"://we use x instedof * so change it in another function if you not.
                        temp = Result * numberOne;
                        break;

                    case "/":
                        try {
                            temp = Result / numberOne;
                        } catch (Exception e) {
                            sAnswer = e.getMessage();
                        }
                        break;
                }
            }
            sCalculation += str_PIorE;
            sAnswer = format.format(temp).toString();
            updateCalculation();
            constant_present = true;
        }
    }

}
