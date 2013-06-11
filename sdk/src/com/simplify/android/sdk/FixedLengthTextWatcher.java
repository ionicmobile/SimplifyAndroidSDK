package com.simplify.android.sdk;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

public class FixedLengthTextWatcher implements TextWatcher {
    private int maxFieldLength = 16;
    private boolean currentlyChanging = false;
    private EditText watched;

    public FixedLengthTextWatcher(EditText watched, int maxFieldLength) {
        this.watched = watched;
        this.maxFieldLength = maxFieldLength;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
    }

    public void setMaxFieldLength(int maxFieldLength) {
        this.maxFieldLength = maxFieldLength;
        setTextPreservingCursorLocation(watched.getText());
    }

    @Override
    public void afterTextChanged(Editable s) {
        if (currentlyChanging) {
            return;
        }

        if (s.length() > maxFieldLength) {
            currentlyChanging = true;
            setTextPreservingCursorLocation(s);
            currentlyChanging = false;
        }

    }

    private void setTextPreservingCursorLocation(Editable s) {
        int selection = watched.getSelectionStart();
        if (selection > maxFieldLength) {
            selection = maxFieldLength;
        }
        watched.setText(s.subSequence(0, Math.min(maxFieldLength, s.length())));
        watched.setSelection(selection);
    }
}
