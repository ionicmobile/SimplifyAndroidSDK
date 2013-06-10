package com.simplify.android.sdk;

import android.test.AndroidTestCase;
import android.widget.EditText;

public class CreditCardTextWatcherTest extends AndroidTestCase {
    public void testFieldLimitedToSixteenDigits() {
        EditText text = new EditText(getContext());
        text.addTextChangedListener(new CreditCardTextWatcher(text));

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
}
