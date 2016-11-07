package android_2016.ifmo.ru.calculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.TextView;


public class CalcActivity extends AppCompatActivity {

    Double op1 = 0.0;
    Double op2 = 0.0;
    Double currRes = 0.0;
    String action = "";
    String number = "";
    boolean lastEquals = false;
    boolean p = false;

    TextView result, precalc;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calc);
        result = (TextView) findViewById(R.id.result);
        result.addTextChangedListener(new CalculatorTextWatcher((HorizontalScrollView) findViewById(R.id.enterScroll)));
        precalc = (TextView) findViewById(R.id.precalc);
        precalc.addTextChangedListener(new CalculatorTextWatcher((HorizontalScrollView) findViewById(R.id.resultScroll)));
        clearText();
    }

    public void onNumberClick(View v) {
        if (lastEquals) {
            clearText();
        }
        String s = ((Button) v).getText().toString();
        if (s.equals("00") && number.isEmpty()) {
            number += "0";
            result.append("0");
            return;
        }
        if ((s.equals("0") || s.equals("00")) && !number.isEmpty() && Double.parseDouble(number) == 0.0 && !p) return;
        if (!number.isEmpty() && Double.parseDouble(number) == 0.0 && !p) cutOneSymbol();
        number += s;
        result.append(s);
        if (!action.isEmpty()) {
            calc();
        }
    }

    public void checkPoint(View v) {
        if (!p) {
            if (lastEquals) {
                clearText();
            }
            String s = ((Button) v).getText().toString();
            if (number.isEmpty()) {
                number += "0" + s;
                result.append(number);
            } else {
                number += s;
                result.append(s);
            }
            p = true;
            if (!action.isEmpty()) {
                calc();
            }
        }
    }

    public void onBinaryOperationClick(View v) {
        if (lastEquals) {
            clearText();
        }
        if (!number.isEmpty() && action.isEmpty()) {
            op1 = Double.parseDouble(number);
            number = "";
            p = false;
        } else if (!number.isEmpty() && !action.isEmpty()) {
            calc();
            op1 = currRes;
            result.setText(print(op1));
            number = "";
            p = false;
        } else if (!action.isEmpty()) {
            cutOneSymbol();
        }
        action = ((Button) v).getText().toString();
        result.append(action);
    }

    public void onClickClear(View v) {
        clearText();
    }

    public void onClickDel(View v) {
        if (result.getText().toString().isEmpty()) return;
        cutOneSymbol();
        if (number.isEmpty() && !action.isEmpty()) {
            number = print(op1);
            action = "";
            op1 = 0.0;
        } else if(number.isEmpty() && action.isEmpty()) {
            number = print(op1);
            number = number.substring(0, number.length() - 1);
            op1 = 0.0;
        } else if (!number.isEmpty() && !action.isEmpty()) {
            number = number.substring(0, number.length() - 1);
            calc();
        } else if (!number.isEmpty() && action.isEmpty()){
            number = number.substring(0, number.length() - 1);
            if (number.isEmpty()) clearText();
        }
    }

    public void onClickEquals(View v) {
        if (lastEquals || number.isEmpty()) {
            result.setText(print(op1));

        } else if (!number.isEmpty() && action.isEmpty()) {
            result.setText(number);
            precalc.setText(number);
        } else {
            result.append(number);
            if (op2 == 0.0 && action.equals("/")) {
                calc();
                result.setText("Error");
                lastEquals = true;
                return;
            }
            op1 = currRes;
            result.setText(print(op1));
            number = "";
            action = "";
            p = false;
        }
        lastEquals = true;
    }

    private void clearText() {
        result.setText("");
        precalc.setText("");
        op1 = 0.0;
        op2 = 0.0;
        currRes = 0.0;
        action = "";
        lastEquals = false;
        number = "";
        p = false;
    }

    private void calc() {
        op2 = (!number.isEmpty()) ? Double.parseDouble(number) : 0.0;
        if (op2 == 0.0 && action.equals("/")) {
            precalc.setText("Error");
            return;
        }
        switch (action) {
            case "+":
                currRes = op1 + op2;
                break;
            case "-":
                currRes = op1 - op2;
                break;
            case "*":
                currRes = op1 * op2;
                break;
            case "/":
                currRes = op1 / op2;
                break;
        }
        precalc.setText(print(currRes));
    }

    private String print(double n) {
        if (((long) n) == n) {
            return Long.valueOf((long) n).toString();
        } else {
            return Double.valueOf(n).toString();
        }
    }

    private void cutOneSymbol() {
        if (result.getText().charAt(result.getText().length() - 1) =='.') {
            p = false;
        }
        result.setText(result.getText().subSequence(0, result.getText().length() - 1));
    }

    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("enter_value", result.getText().toString());
        outState.putString("result_value", precalc.getText().toString());
        outState.putBoolean("p", p);
        outState.putDouble("op1", op1);
        outState.putDouble("op2", op2);
        outState.putDouble("currRes", currRes);
        outState.putString("action", action);
        outState.putBoolean("lastEquals", lastEquals);
        outState.putString("number", number);
    }

    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        result.setText(savedInstanceState.getString("enter_value"));
        precalc.setText(savedInstanceState.getString("result_value"));
        p = savedInstanceState.getBoolean("p");
        op1 = savedInstanceState.getDouble("op1");
        op2 = savedInstanceState.getDouble("op2");
        currRes = savedInstanceState.getDouble("currRes");
        action = savedInstanceState.getString("action");
        lastEquals = savedInstanceState.getBoolean("lastEquals");
        number = savedInstanceState.getString("number");
    }
}
