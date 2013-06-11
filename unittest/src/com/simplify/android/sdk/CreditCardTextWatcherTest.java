package com.simplify.android.sdk;

import android.test.AndroidTestCase;
import android.widget.EditText;

public class CreditCardTextWatcherTest extends AndroidTestCase {
    public void testFieldLimitedToSixteenDigits() {
        EditText text = new EditText(getContext());
        text.addTextChangedListener(new FixedLengthTextWatcher(text, 16));

        text.append("0123");
        assertEquals("0123", text.getText().toString());
        text.append("4567");
        assertEquals("01234567", text.getText().toString());
        text.append("8901");
        assertEquals("012345678901", text.getText().toString());

        // Should stop at "2345" since "6" is one digit too many
        text.append("23456");
        assertEquals("0123456789012345", text.getText().toString());
    }

    public void testFieldLengthCanChangeAtRuntime() {
        EditText text = new EditText(getContext());
        FixedLengthTextWatcher watcher = new FixedLengthTextWatcher(text, 16);
        text.addTextChangedListener(watcher);

        text.setText("0123456789012345");
        assertEquals("0123456789012345", text.getText().toString());

        watcher.setMaxFieldLength(5);
        assertEquals("01234", text.getText().toString());
    }
}
