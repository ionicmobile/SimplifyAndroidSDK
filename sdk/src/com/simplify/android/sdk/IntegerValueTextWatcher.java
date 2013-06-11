package com.simplify.android.sdk;

import android.text.Editable;
import android.widget.EditText;

/**
 * Created with IntelliJ IDEA.
 * User: paul.hawke
 * Date: 6/11/13
 * Time: 2:33 PM
 * To change this template use File | Settings | File Templates.
 */
public class IntegerValueTextWatcher extends FixedLengthTextWatcher {
    private int minValue;
    private int maxValue;

    public IntegerValueTextWatcher(EditText watched, int maxFieldLength, int minValue, int maxValue) {
        super(watched, maxFieldLength);
        this.minValue = minValue;
        this.maxValue = maxValue;
    }

    @Override
    protected boolean shouldFireCompleted(Editable text) {
        if (super.shouldFireCompleted(text)) {
            try {
                int value = Integer.parseInt(text.toString());
                if (value >= minValue && value <= maxValue) {
                    return true;
                }
            } catch (NumberFormatException nfe){}
        }
        return false;
    }
}
