package com.simplify.android.sdk;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

public class CreditCardEditor extends LinearLayout {
    private EditText ccEditorView;
    private List<BrandChangedListener> brandChangedListeners;
    private FixedLengthTextWatcher cardTextWatcher;
    private BrandChangedTextWatcher brandChangedWatcher;

    public CreditCardEditor(Context context) {
        super(context);
        init(context);
    }

    public CreditCardEditor(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CreditCardEditor(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init(Context context) {
        brandChangedListeners = new ArrayList<BrandChangedListener>();
        this.setOrientation(HORIZONTAL);
        ccEditorView = createTextView(context);
        cardTextWatcher = new FixedLengthTextWatcher(ccEditorView, Card.Brand.UNKNOWN.getMaxLength());
        brandChangedWatcher = new BrandChangedTextWatcher(ccEditorView, new BrandChangedHandler());
        ccEditorView.addTextChangedListener(cardTextWatcher);
        ccEditorView.addTextChangedListener(brandChangedWatcher);

        addView(ccEditorView, new LayoutParams(600, LayoutParams.WRAP_CONTENT, 0));
    }

    public void addBrandChangedListener(BrandChangedListener listener) {
        brandChangedListeners.add(listener);
    }

    private EditText createTextView(Context context) {
        EditText text = new EditText(context);
        text.setInputType(EditorInfo.TYPE_CLASS_NUMBER);
        text.setHint("0000 0000 0000 0000");
        text.setSingleLine(true);
        return text;
    }

    public String getCardNumber() {
        return ccEditorView.getText().toString();
    }

    public void setCardNumber(String cardNumber) {
        ccEditorView.setText(cardNumber);
    }

    private class BrandChangedHandler implements BrandChangedListener {
        public void brandChanged(View sourceView, Card.Brand brand) {
            cardTextWatcher.setMaxFieldLength(brand.getMaxLength());
            for (BrandChangedListener brandChangedListener : brandChangedListeners) {
                brandChangedListener.brandChanged(CreditCardEditor.this, brand);
            }
        }
    }
}
