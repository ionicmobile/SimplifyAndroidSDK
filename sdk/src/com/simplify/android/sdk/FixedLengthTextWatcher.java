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

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;

class FixedLengthTextWatcher implements TextWatcher {
    private int maxFieldLength = 16;
    private boolean currentlyChanging = false;
    private final EditText watched;
    private EntryCompleteListener entryCompleteListener;

    public FixedLengthTextWatcher(EditText watched, int maxFieldLength) {
        this.watched = watched;
        this.maxFieldLength = maxFieldLength;
    }

    public void setEntryCompleteListener(EntryCompleteListener entryCompleteListener) {
        this.entryCompleteListener = entryCompleteListener;
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

        if (shouldFireCompleted(watched.getText())) {
            entryCompleteListener.entryComplete(watched);
        } else if (shouldFireIncomplete(watched.getText())) {
            entryCompleteListener.entryIncomplete(watched);
        }
    }

    boolean shouldFireCompleted(Editable text) {
        return entryCompleteListener != null && text.length() == maxFieldLength;
    }

    boolean shouldFireIncomplete(Editable text) {
        return entryCompleteListener != null && text.length() < maxFieldLength;
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
