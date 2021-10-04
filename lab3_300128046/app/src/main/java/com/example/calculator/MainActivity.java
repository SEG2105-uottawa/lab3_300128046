package com.example.calculator;

import androidx.appcompat.app.AppCompatActivity;
import android.widget.Button;
import android.view.View;
import android.os.Bundle;
import android.widget.TextView;
public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    //Set numeric button
    Button btn_0, btn_1, btn_2, btn_3, btn_4, btn_5, btn_6, btn_7, btn_8, btn_9, btn_pt, btn_negative;
    //Set algorthim button
    Button btn_add, btn_minus, btn_mult, btn_divde;
    Button btn_clr, btn_del, btn_eq;
    TextView et_input;
    TextView display;
    boolean clr;
    private void initView() {
        btn_0 = (Button) findViewById(R.id.btn_0);
        btn_1 = (Button) findViewById(R.id.btn_1);
        btn_2 = (Button) findViewById(R.id.btn_2);
        btn_3 = (Button) findViewById(R.id.btn_3);
        btn_4 = (Button) findViewById(R.id.btn_4);
        btn_5 = (Button) findViewById(R.id.btn_5);
        btn_6 = (Button) findViewById(R.id.btn_6);
        btn_7 = (Button) findViewById(R.id.btn_7);
        btn_8 = (Button) findViewById(R.id.btn_8);
        btn_9 = (Button) findViewById(R.id.btn_9);
        btn_pt = (Button) findViewById(R.id.btn_pt);
        btn_negative = (Button) findViewById(R.id.btn_negative);
        btn_add = (Button) findViewById(R.id.btn_add);
        btn_minus = (Button) findViewById(R.id.btn_minus);
        btn_mult = (Button) findViewById(R.id.btn_mult);
        btn_divde = (Button) findViewById(R.id.btn_divde);
        btn_eq = (Button) findViewById(R.id.btn_eq);
        btn_clr = (Button) findViewById(R.id.btn_clr);
        btn_del = (Button) findViewById(R.id.btn_del);
        et_input = (TextView) findViewById(R.id.et_input);

        //set click

        btn_0.setOnClickListener(this);
        btn_1.setOnClickListener(this);
        btn_2.setOnClickListener(this);
        btn_3.setOnClickListener(this);
        btn_4.setOnClickListener(this);
        btn_5.setOnClickListener(this);
        btn_6.setOnClickListener(this);
        btn_7.setOnClickListener(this);
        btn_8.setOnClickListener(this);
        btn_9.setOnClickListener(this);
        btn_pt.setOnClickListener(this);
        btn_negative.setOnClickListener(this);
        btn_add.setOnClickListener(this);
        btn_minus.setOnClickListener(this);
        btn_mult.setOnClickListener(this);
        btn_divde.setOnClickListener(this);
        btn_clr.setOnClickListener(this);
        btn_del.setOnClickListener(this);
        btn_eq.setOnClickListener(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //instance objects
        initView();

    }


    public void onClick(View v) {
        String str = et_input.getText().toString();
        switch (v.getId()) {
            case R.id.btn_0:
            case R.id.btn_1:
            case R.id.btn_2:
            case R.id.btn_3:
            case R.id.btn_4:
            case R.id.btn_5:
            case R.id.btn_6:
            case R.id.btn_7:
            case R.id.btn_8:
            case R.id.btn_9:
            case R.id.btn_pt:
                if (clr) {
                    clr = false;
                    str = "";
                    et_input.setText("");
                }
                et_input.setText(str + ((Button) v).getText());
                break;
            case R.id.btn_negative:
                if (clr) {
                    clr = false;
                    str = "";
                    et_input.setText("");
                }
                if(str.length() == 0) break; // button does not work if pressed before a number
                else if(Character.isDigit(str.charAt(str.length()-1))){ // if the last character is a number
                    str = str.substring(0, str.length()-1) + "(-" + str.charAt(str.length()-1) + ")";
                }
                et_input.setText(str);
                break;
            case R.id.btn_add:
            case R.id.btn_minus:
            case R.id.btn_divde:
            case R.id.btn_mult:
                if (clr) {
                    clr = false;
                    str = "";
                    et_input.setText("");
                }
                if (str.contains("+") || (str.contains("-") && !str.contains("(")) || str.contains("x") || str.contains("÷")) {
                    str = str.substring(0, str.indexOf(" "));
                }
                et_input.setText(str + " " + ((Button) v).getText() + " ");
                break;
            case R.id.btn_clr:
                if (clr) {
                    clr = false;
                }
                str = "";
                et_input.setText("");
                break;
            case R.id.btn_del:
                if (clr) {
                    clr = false;
                    str = "";
                    et_input.setText("");
                } else if (str != null && !str.equals("")) {
                    et_input.setText(str.substring(0, str.length() - 1));
                }
                break;
            case R.id.btn_eq:
                Result();
                break;
        }
    }

    private void Result() {

        String exp = et_input.getText().toString(); // the entire expression
        String s1, s2, op; // operands and operator
        boolean firstOpNegative = false; // flag for the first operand being negative

        if(exp==null||exp.equals("")) return ;

        if(!exp.contains(" ")){
            return ;
        }
        if(clr){
            clr=false;
            return;
        }
        clr=true;

        // finding the first operand
        if(exp.contains("(-")) { // finding a negative first operand
            s1 = exp.substring(exp.indexOf("(")+1, exp.indexOf(")"));
            firstOpNegative = true;
        } else { // finding a non-negative first operand
            s1 = exp.substring(0,exp.indexOf(" "));
        }

        // finding the operator
        op = exp.substring(exp.indexOf(" ")+1,exp.indexOf(" ")+2);

        // finding the second operand
        if(exp.substring(4).contains("(-") && firstOpNegative) { // finding a negative second operand after a negative first one
            s2 = exp.substring(exp.indexOf("(")+8, exp.indexOf(")")+7);
        } else if(exp.contains("(-") && !firstOpNegative){ // finding a negative second operand after a non-negative first one
            s2 = exp.substring(exp.indexOf("(")+1, exp.indexOf(")"));
        } else { // finding a non-negative second operand
            s2 = exp.substring(exp.indexOf(" ")+3);
        }

        double cnt = 0;

        if(!s1.equals("")&&!s2.equals("")){ // for both operands
            double d1=Double.parseDouble(s1); // convert the operands to doubles
            double d2=Double.parseDouble(s2);
            if(op.equals("+")){ // addition
                cnt=d1+d2;
            }
            if(op.equals("-")){ // subtraction
                cnt=d1-d2;
            }
            if(op.equals("X")){ // multiplication
                cnt=d1*d2;
            }
            if(op.equals("÷")){ // division
                if(d2==0) cnt=0;
                else cnt=d1/d2;
            }
            if(!s1.contains(".")&&!s2.contains(".")&&!op.equals("÷")) { // operands are not floats and the operation is not division
                int res = (int) cnt;
                et_input.setText(res+""); // the output is displayed as an int to be consistent with the input
            }else { // case with float operand(s) or a division
                et_input.setText(cnt+"");} // the output is displayed as a double to be consistent with the input
        }

        else if(!s1.equals("")&&s2.equals("")){ // for only the first operand
            double d1=Double.parseDouble(s1);
            if(op.equals("+")){
                cnt=d1;
            }
            if(op.equals("-")){
                cnt=d1;
            }
            if(op.equals("X")){
                cnt=0;
            }
            if(op.equals("÷")){
                cnt=0;
            }
            if(!s1.contains(".")) { // int conversion if necessary (as above)
                int res = (int) cnt;
                et_input.setText(res+"");
            }else {
                et_input.setText(cnt+"");}
        }

        else if(s1.equals("")&&!s2.equals("")){ // for only the second operand
            double d2=Double.parseDouble(s2);
            if(op.equals("+")){
                cnt=d2;
            }
            if(op.equals("-")){
                cnt=0-d2;
            }
            if(op.equals("X")){
                cnt=0;
            }
            if(op.equals("÷")){
                cnt=0;
            }
            if(!s2.contains(".")) { // int conversion if necessary (as above)
                int res = (int) cnt;
                et_input.setText(res+"");
            }else {
                et_input.setText(cnt+"");}
        }
        else {
            et_input.setText("");
        }
    }
}



