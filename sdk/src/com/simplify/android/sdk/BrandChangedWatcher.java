package com.simplify.android.sdk;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;

/**
 * @author Paul S. Hawke (paul.hawke@gmail.com)
 *         On: 6/11/13 at 2:12 AM
 */
public class BrandChangedWatcher implements TextWatcher {
    private Card.Brand brand;
    private EditText text;
    private BrandChangedListener listener;
    private EditText view;

    public BrandChangedWatcher(EditText view, BrandChangedListener listener) {
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
