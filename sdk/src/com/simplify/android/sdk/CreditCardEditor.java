package com.simplify.android.sdk;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CreditCardEditor extends RelativeLayout {
    public static final int MIN_MONTH = 1;
    public static final int MAX_MONTH = 12;
    private Map<Card.Brand, Integer> brandToImageResourceMap =
            new HashMap<Card.Brand, Integer>() {{
                put(Card.Brand.UNKNOWN, R.drawable.brand_unknown);
                put(Card.Brand.AMEX, R.drawable.brand_amex);
                put(Card.Brand.DISCOVER, R.drawable.brand_discover);
                put(Card.Brand.MASTERCARD, R.drawable.brand_mastercard);
                put(Card.Brand.VISA, R.drawable.brand_visa);
            }};
    private EditText ccEditorView;
    private EditText expMonth;
    private EditText expYear;
    private EditText cvv;
    private ImageView imageView;
    private List<BrandChangedListener> brandChangedListeners;
    private FixedLengthTextWatcher cardTextWatcher;
    private FixedLengthTextWatcher cvvWatcher;
    private String threeDigitCvvHint;
    private String fourDigitCvvHint;

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
        createLayout(context);

        brandChangedListeners = new ArrayList<BrandChangedListener>();
        threeDigitCvvHint = context.getResources().getString(R.string.three_digit_cvv_hint);
        fourDigitCvvHint = context.getResources().getString(R.string.four_digit_cvv_hint);

        cardTextWatcher = new FixedLengthTextWatcher(ccEditorView, Card.Brand.UNKNOWN.getMaxLength());
        BrandChangedTextWatcher brandChangedWatcher = new BrandChangedTextWatcher(ccEditorView, new BrandChangedHandler());
        ccEditorView.addTextChangedListener(cardTextWatcher);
        ccEditorView.addTextChangedListener(brandChangedWatcher);
        ccEditorView.setNextFocusForwardId(R.id.cc_exp_month);

        FixedLengthTextWatcher expMonthWatcher = new IntegerValueTextWatcher(expMonth, 2, MIN_MONTH, MAX_MONTH);
        expMonth.addTextChangedListener(expMonthWatcher);
        expMonth.setNextFocusForwardId(R.id.cc_exp_year);

        FixedLengthTextWatcher expYearWatcher = new FixedLengthTextWatcher(expYear, 2);
        expYear.addTextChangedListener(expYearWatcher);
        expYear.setNextFocusForwardId(R.id.cc_cvv);

        cvvWatcher = new FixedLengthTextWatcher(cvv, 3);
        cvv.addTextChangedListener(cvvWatcher);

        cardTextWatcher.setEntryCompleteListener(new FixedLengthTextWatcher.EntryCompleteListener() {
            @Override
            public void entryComplete(View editorView) {
                expMonth.requestFocus();
            }
        });
        expMonthWatcher.setEntryCompleteListener(new FixedLengthTextWatcher.EntryCompleteListener() {
            @Override
            public void entryComplete(View editorView) {
                expYear.requestFocus();
            }
        });
        expYearWatcher.setEntryCompleteListener(new FixedLengthTextWatcher.EntryCompleteListener() {
            @Override
            public void entryComplete(View editorView) {
                cvv.requestFocus();
            }
        });
    }

    private void createLayout(Context context) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.cc_editor, this, true);
        ccEditorView = (EditText) view.findViewById(R.id.cc_field);
        imageView = (ImageView) view.findViewById(R.id.cc_icon);
        expMonth = (EditText) view.findViewById(R.id.cc_exp_month);
        expYear = (EditText) view.findViewById(R.id.cc_exp_year);
        cvv = (EditText) view.findViewById(R.id.cc_cvv);
    }

    public void addBrandChangedListener(BrandChangedListener listener) {
        brandChangedListeners.add(listener);
    }

    public String getCardNumber() {
        return ccEditorView.getText().toString();
    }

    public void setCardNumber(String cardNumber) {
        ccEditorView.setText(cardNumber);
    }

    public String getExpirationMonth() {
        return expMonth.getText().toString();
    }

    public void setExpMonth(String month) {
        expMonth.setText(month);
    }

    public String getExpirationYear() {
        return expYear.getText().toString();
    }

    public void setExpYear(String year) {
        expYear.setText(year);
    }

    public String getCvv() {
        return cvv.getText().toString();
    }

    public void setCvv(String cvvValue) {
        cvv.setText(cvvValue);
    }

    private class BrandChangedHandler implements BrandChangedListener {
        public void brandChanged(View sourceView, Card.Brand brand) {
            cardTextWatcher.setMaxFieldLength(brand.getMaxLength());
            cvvWatcher.setMaxFieldLength(brand.getCvvLength());
            cvv.setHint(brand.getCvvLength() == 4 ? fourDigitCvvHint : threeDigitCvvHint);
            imageView.setImageResource(brandToImageResourceMap.get(brand));
            for (BrandChangedListener brandChangedListener : brandChangedListeners) {
                brandChangedListener.brandChanged(CreditCardEditor.this, brand);
            }
        }
    }
}
