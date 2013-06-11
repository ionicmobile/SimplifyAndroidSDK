package com.simplify.android.sdk;

import android.content.Context;
import android.util.AttributeSet;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

public class CreditCardEditorView extends LinearLayout {
    private EditText ccView1;
    private List<BrandChangedListener> brandChangedListeners;

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
        brandChangedListeners = new ArrayList<BrandChangedListener>();
        this.setOrientation(HORIZONTAL);
        ccView1 = createTextView(context);
        addView(ccView1, new LayoutParams(600, LayoutParams.WRAP_CONTENT, 0));
    }

    public void addBrandChangedListener(BrandChangedListener listener) {
        brandChangedListeners.add(listener);
    }

    private EditText createTextView(Context context) {
        EditText text = new EditText(context);
        text.setInputType(EditorInfo.TYPE_CLASS_NUMBER);
        text.setHint("0000 0000 0000 0000");
        text.setSingleLine(true);
        text.addTextChangedListener(new CreditCardTextWatcher(text));
        text.addTextChangedListener(new BrandChangedWatcher(text, new BrandChangedListener() {
            public void brandChanged(EditText view, Card.Brand brand) {
                for (BrandChangedListener brandChangedListener : brandChangedListeners) {
                    brandChangedListener.brandChanged(view, brand);
                }
            }
        }));
        return text;
    }

}
