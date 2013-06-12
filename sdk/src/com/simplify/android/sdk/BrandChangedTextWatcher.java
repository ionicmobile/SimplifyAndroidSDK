package com.simplify.android.sdk;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;

/**
 * @author Paul S. Hawke (paul.hawke@gmail.com)
 *         On: 6/11/13 at 2:12 AM
 */
public class BrandChangedTextWatcher implements TextWatcher {
    private Card.Brand brand;
    private BrandChangedListener listener;
    private EditText view;

    public BrandChangedTextWatcher(EditText view, BrandChangedListener listener) {
        Log.e("SIMP", "### BrandChangedTextWatcher::BrandChangedTextWatcher");

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
        Log.e("SIMP", "###");
        Log.e("SIMP", "### BrandChangedTextWatcher::afterTextChanged - brandInField="+brandInField);
        Log.e("SIMP", "### BrandChangedTextWatcher::afterTextChanged - brand="+brand);
        Log.e("SIMP", "###");
        if (!brand.equals(brandInField)) {
            this.brand = brandInField;
            listener.brandChanged(view, brand);
        }
    }
}
