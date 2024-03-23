package com.example.calculator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.os.Bundle;
import android.view.View;

import com.example.calculator.databinding.ActivityMainBinding;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    //Initializations
    ActivityMainBinding binding;
    String ansVal = ""; //valuse on answer screen
    AppCompatButton btn; //btn clicked
    String btnText; //value of btn clicked
    ArrayList<Float> operands = new ArrayList<>(); //for operands
    ArrayList<String> operators = new ArrayList<>(); //for operators
    int noOfCalculations = 0; //no of numbers on screen
    int k = 0;
    String tempOperand = ""; //helping variable for storing operand in operands arraylist
    float ans = 0; //ans after calculations
    int noOfOperators = operators.size();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //Clear button
        binding.clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ansVal = "";
                binding.answer.setText(ansVal);
            }
        });

        //Equals button
        binding.equal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                noOfCalculations++;
                operators.clear();
                operands.clear();
                tempOperand = "";
                ans = 0;

                //filling operands and operators arraylist
                for (int j = 0; j < ansVal.length(); j++) {
                    if (ansVal.charAt(j) == '+' || ansVal.charAt(j) == '-' || ansVal.charAt(j) == '/' || ansVal.charAt(j) == 'x') {
                        if (!(binding.answer.getText().toString().equals(""))) {
                            binding.answer.setBackground(getResources().getDrawable(R.drawable.answer_border));
                            operands.add(Float.parseFloat(tempOperand));
                            tempOperand = "";
                            operators.add(String.valueOf(ansVal.charAt(j)));
                            k = j + 1;
                        } else {
                            binding.answer.setBackground(getResources().getDrawable(R.drawable.answer_invalid_border));
                        }
                    } else {
                        tempOperand = tempOperand + ansVal.charAt(j);
                        if (j == ansVal.length() - 1) {
                            tempOperand = "";
                            for (int l = k; l < ansVal.length(); l++) {
                                tempOperand = tempOperand + ansVal.charAt(l);
                            }
                            operands.add(Float.parseFloat(tempOperand));
                        }
                    }
                }

                noOfOperators = operators.size();
                //calculations of higher priority / and x
                for (int i = 0; i < noOfOperators; i++) {
                    if (String.valueOf(operators.get(i)).equals("/") || String.valueOf(operators.get(i)).equals("x")) {
                        if (String.valueOf(operators.get(i)).equals("/")) {
                            ans = Float.parseFloat(String.valueOf(operands.get(i) / operands.get(i + 1)));
                        } else {
                            ans = Float.parseFloat(String.valueOf(operands.get(i) * operands.get(i + 1)));
                        }
                        operators.remove(i);
                        operands.remove(i);
                        operands.remove(i);
                        noOfOperators = operators.size();
                        if (operands.size() >= 1) {
                            operands.add(i, ans);
                        }
                        i = -1;
                    }
                }

                noOfOperators = operators.size();
                //calculations of higher priority + and -
                for (int i = 0; i < noOfOperators; i++) {
                    if (String.valueOf(operators.get(i)).equals("+") || String.valueOf(operators.get(i)).equals("-")) {
                        if (String.valueOf(operators.get(i)).equals("+")) {
                            ans = Float.parseFloat(String.valueOf(operands.get(i) + operands.get(i + 1)));
                        } else {
                            ans = Float.parseFloat(String.valueOf(operands.get(i) - operands.get(i + 1)));
                        }
                        operators.remove(i);
                        operands.remove(i);
                        operands.remove(i);
                        noOfOperators = operators.size();
                        if (operands.size() >= 1) {
                            operands.add(i, ans);
                        }
                        i = -1;
                    }
                }
                operands.clear();
                operators.clear();
                ansVal = "";
                if(ans == (int)ans){
                binding.answer.setText(String.valueOf((int)ans));
                }else{
                    binding.answer.setText(String.valueOf(ans));
                }
            }
        });

        //Back button
        binding.backspace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ansVal != "") {
                    ansVal = binding.answer.getText().toString().substring(0, binding.answer.getText().toString().length() - 1);
                    binding.answer.setText(ansVal);
                }
            }
        });
    }

    //function called when operands or operators are clicked
    public void showOnScreen(View view) {
        binding.answer.setBackground(getResources().getDrawable(R.drawable.answer_border));
        btn = findViewById(view.getId());
        btnText = "";
        btnText = btn.getText().toString();
        //for limiting inputs on screen
        if ((binding.answer.getText().toString().length()) > 30) {
            binding.answer.setBackground(getResources().getDrawable(R.drawable.answer_invalid_border));
        } else {
            //conditions if screen is blank
            if (binding.answer.getText().toString().equals("")) {
                if (btnText.equals("x") || btnText.equals("/") || btnText.equals("+") || btnText.equals("-")) {
                    binding.answer.setBackground(getResources().getDrawable(R.drawable.answer_invalid_border));
                } else if (btnText.equals(".")) {
                    ansVal = "0" + btnText;
                    binding.answer.setText(ansVal);
                } else {
                    binding.answer.setBackground(getResources().getDrawable(R.drawable.answer_border));
                    ansVal = ansVal + btnText;
                    binding.answer.setText(ansVal);
                }
            } else if ((binding.answer.getText().toString().charAt(binding.answer.getText().toString().length() - 1) == '/' || binding.answer.getText().toString().charAt(binding.answer.getText().toString().length() - 1) == 'x' || binding.answer.getText().toString().charAt(binding.answer.getText().toString().length() - 1) == '+' || binding.answer.getText().toString().charAt(binding.answer.getText().toString().length() - 1) == '-') && (btnText.equals("x") || btnText.equals("/") || btnText.equals("+") || btnText.equals("-"))) {
                //if input is operators on operators
                binding.answer.setBackground(getResources().getDrawable(R.drawable.answer_invalid_border));
            } else if (binding.answer.getText().toString().charAt(binding.answer.getText().toString().length() - 1) == '.' && btnText.equals(".")) {
                //if input is . on .
                binding.answer.setBackground(getResources().getDrawable(R.drawable.answer_invalid_border));
            } else if ((binding.answer.getText().toString().charAt(binding.answer.getText().toString().length() - 1) == '/' || binding.answer.getText().toString().charAt(binding.answer.getText().toString().length() - 1) == 'x' || binding.answer.getText().toString().charAt(binding.answer.getText().toString().length() - 1) == '+' || binding.answer.getText().toString().charAt(binding.answer.getText().toString().length() - 1) == '-') && (btnText.equals("."))) {
                //if input is . on operator
                ansVal = ansVal + "0" + btnText;
                binding.answer.setText(ansVal);
            } else if ((binding.answer.getText().toString().charAt(binding.answer.getText().toString().length() - 1) == '.') && (btnText.equals("+") || btnText.equals("/") || btnText.equals("-") || btnText.equals("x"))) {
                //if input is operator on .
                binding.answer.setBackground(getResources().getDrawable(R.drawable.answer_invalid_border));
            } else {
                ansVal = ansVal + btnText;
                binding.answer.setText(ansVal);
            }
        }
    }
}