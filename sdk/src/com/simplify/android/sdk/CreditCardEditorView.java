package com.simplify.android.sdk;

import android.content.Context;
import android.util.AttributeSet;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;

public class CreditCardEditorView extends LinearLayout {
    EditText ccView1;

    public CreditCardEditorView(Context context) {
        super(context);
        init(context);
    }

    public CreditCardEditorView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CreditCardEditorView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init(Context context) {
        this.setOrientation(HORIZONTAL);
        ccView1 = createTextView(context);
        addView(ccView1, new LayoutParams(600, LayoutParams.WRAP_CONTENT, 0));
    }

    private EditText createTextView(Context context) {
        EditText text = new EditText(context);
        text.setInputType(EditorInfo.TYPE_CLASS_NUMBER);
        text.setHint("0000 0000 0000 0000");
        text.setSingleLine(true);
        text.addTextChangedListener(new CreditCardTextWatcher(text));
        return text;
    }

}
