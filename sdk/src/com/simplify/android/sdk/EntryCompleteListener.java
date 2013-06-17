package com.simplify.android.sdk;

import android.view.View;

/**
 * Receives notifications that data entry in an attached <code>EditText</code> is
 * either complete, or incomplete.
 */
public interface EntryCompleteListener {
    void entryComplete(View editorView);

    void entryIncomplete(View editorView);
}
