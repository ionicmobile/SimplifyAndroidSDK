package com.asynchrony.mastercard.api;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class CreditCardEditorView extends LinearLayout {
    EditText ccView1,ccView2,ccView3,ccView4;
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
        ccView2 = createTextView(context);
        ccView3 = createTextView(context);
        ccView4 = createTextView(context);

        ccView1.setHint("0000");
        ccView2.setHint("0000");
        ccView3.setHint("0000");
        ccView4.setHint("0000");
    }

    private EditText createTextView(Context context) {
        EditText tv = new EditText(context);
        tv.setInputType(EditorInfo.TYPE_CLASS_NUMBER);
        addView(tv, new LayoutParams(140, LayoutParams.WRAP_CONTENT, 0));
        return tv;
    }


}
