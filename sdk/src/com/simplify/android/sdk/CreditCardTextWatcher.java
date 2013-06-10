package com.simplify.android.sdk;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

public class CreditCardTextWatcher implements TextWatcher {
    private final int maxFieldLength = 16;
    private boolean currentlyChanging = false;
    private EditText watched;

    CreditCardTextWatcher(EditText watched) {
        this.watched = watched;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
    }

    @Override
    public void afterTextChanged(Editable s) {
        if (currentlyChanging) {
            return;
        }

        if (s.length() > maxFieldLength) {
            currentlyChanging = true;
            int selection = watched.getSelectionStart();
            if (selection > maxFieldLength) {
                selection = maxFieldLength;
            }
            watched.setText(s.subSequence(0, maxFieldLength));
            watched.setSelection(selection);
            currentlyChanging = false;
        }

    }
}
