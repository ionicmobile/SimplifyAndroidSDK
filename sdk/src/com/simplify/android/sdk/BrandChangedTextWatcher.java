package com.simplify.android.sdk;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import com.simplify.android.sdk.api.card.Card;

class BrandChangedTextWatcher implements TextWatcher {
    private Card.Brand brand;
    private final BrandChangedListener listener;
    private final EditText view;

    public BrandChangedTextWatcher(EditText view, BrandChangedListener listener) {
        this.view = view;
        this.listener = listener;
        this.brand = Card.Brand.UNKNOWN;
    }

    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    public void onTextChanged(CharSequence s, int start, int before, int count) {
    }

    public void afterTextChanged(Editable s) {
        Card.Brand brandInField = Card.Brand.lookup(s.toString());
        if (!brand.equals(brandInField)) {
            this.brand = brandInField;
            listener.brandChanged(view, brand);
        }
    }
}
