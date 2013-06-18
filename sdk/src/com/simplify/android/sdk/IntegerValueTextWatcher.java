package com.simplify.android.sdk;

import android.text.Editable;
import android.widget.EditText;

class IntegerValueTextWatcher extends FixedLengthTextWatcher {
    private final int minValue;
    private final int maxValue;

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
            } catch (NumberFormatException ignored) {
            }
        }
        return false;
    }
}
