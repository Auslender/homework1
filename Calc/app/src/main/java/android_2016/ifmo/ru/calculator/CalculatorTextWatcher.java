package android_2016.ifmo.ru.calculator;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.HorizontalScrollView;
import android.widget.ScrollView;

/**
 * Created by maria on 21.10.16.
 */
class CalculatorTextWatcher implements TextWatcher {
    HorizontalScrollView scrollView;

    CalculatorTextWatcher(HorizontalScrollView scrollView) {
        this.scrollView = scrollView;
    }

    @Override
    public void afterTextChanged(Editable arg0) {
        scrollView.fullScroll(ScrollView.FOCUS_RIGHT);
    }

    @Override
    public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {}

    @Override
    public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
        scrollView.fullScroll(ScrollView.FOCUS_RIGHT);
    }
}