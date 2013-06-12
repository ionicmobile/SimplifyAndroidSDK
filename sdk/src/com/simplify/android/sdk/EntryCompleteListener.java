package com.simplify.android.sdk;

import android.view.View;

/**
* Created with IntelliJ IDEA.
* User: paul.hawke
* Date: 6/12/13
* Time: 11:33 AM
* To change this template use File | Settings | File Templates.
*/
public interface EntryCompleteListener {
    void entryComplete(View editorView);
    void entryIncomplete(View editorView);
}
