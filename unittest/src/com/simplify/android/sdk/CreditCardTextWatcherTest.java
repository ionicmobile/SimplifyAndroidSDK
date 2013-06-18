/*
 * Copyright (c) 2013, Asynchrony Solutions, Inc.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *     * Redistributions of source code must retain the above copyright
 *       notice, this list of conditions and the following disclaimer.
 *
 *     * Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the distribution.
 *
 *     * Neither the name of Asynchrony nor the names of its contributors may
 *       be used to endorse or promote products derived from this software
 *       without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL ASYNCHRONY SOLUTIONS, INC. BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
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
