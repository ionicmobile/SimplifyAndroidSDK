package com.simplify.android.sdk;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.*;
import com.simplify.android.sdk.api.Card;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CreditCardEditor extends RelativeLayout {
    private static final int MIN_MONTH = 1;
    private static final int MAX_MONTH = 12;
    private final Map<Card.Brand, Integer> brandToImageResourceMap =
            new HashMap<Card.Brand, Integer>() {{
                put(Card.Brand.UNKNOWN, R.drawable.brand_unknown);
                put(Card.Brand.AMEX, R.drawable.brand_amex);
                put(Card.Brand.DISCOVER, R.drawable.brand_discover);
                put(Card.Brand.MASTERCARD, R.drawable.brand_mastercard);
                put(Card.Brand.VISA, R.drawable.brand_visa);
                put(Card.Brand.DINERS_CLUB, R.drawable.brand_diners_club);
                put(Card.Brand.CHINA_UNIONPAY, R.drawable.brand_china_union_pay);
                put(Card.Brand.JCB, R.drawable.brand_jcb);
            }};
    private EditText ccEditorView;
    private EditText expMonth;
    private EditText expYear;
    private EditText cvv;
    private ImageView imageView;
    private List<BrandChangedListener> brandChangedListeners;
    private List<EntryCompleteListener> entryCompleteListeners;
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

        EntryCompleteFieldWatcher entryCompleteWatcher = new EntryCompleteFieldWatcher();
        brandChangedListeners = new ArrayList<BrandChangedListener>();
        entryCompleteListeners = new ArrayList<EntryCompleteListener>();
        threeDigitCvvHint = context.getResources().getString(R.string.three_digit_cvv_hint);
        fourDigitCvvHint = context.getResources().getString(R.string.four_digit_cvv_hint);

        cardTextWatcher = new FixedLengthTextWatcher(ccEditorView, Card.Brand.UNKNOWN.getMaxLength());
        cardTextWatcher.setEntryCompleteListener(new EntryCompleteListener() {
            @Override
            public void entryComplete(View editorView) {
                expMonth.requestFocus();
            }

            @Override
            public void entryIncomplete(View editorView) {}
        });
        ccEditorView.addTextChangedListener(entryCompleteWatcher);
        ccEditorView.addTextChangedListener(cardTextWatcher);
        ccEditorView.addTextChangedListener(new BrandChangedTextWatcher(ccEditorView, new BrandChangedHandler()));
        ccEditorView.setNextFocusForwardId(R.id.cc_exp_month);

        IntegerValueTextWatcher exMonthWatcher = new IntegerValueTextWatcher(expMonth, 2, MIN_MONTH, MAX_MONTH);
        exMonthWatcher.setEntryCompleteListener(new EntryCompleteListener() {
            @Override
            public void entryComplete(View editorView) {
                expYear.requestFocus();
            }

            @Override
            public void entryIncomplete(View editorView) {
            }
        });
        expMonth.addTextChangedListener(exMonthWatcher);
        expMonth.addTextChangedListener(entryCompleteWatcher);
        expMonth.setNextFocusForwardId(R.id.cc_exp_year);

        FixedLengthTextWatcher expYearWatcher = new FixedLengthTextWatcher(expYear, 2);
        expYearWatcher.setEntryCompleteListener(new EntryCompleteListener() {
            @Override
            public void entryComplete(View editorView) {
                cvv.requestFocus();
            }

            @Override
            public void entryIncomplete(View editorView) {
            }
        });
        expYear.addTextChangedListener(expYearWatcher);
        expYear.addTextChangedListener(entryCompleteWatcher);
        expYear.setNextFocusForwardId(R.id.cc_cvv);

        cvvWatcher = new FixedLengthTextWatcher(cvv, 3);
        cvv.addTextChangedListener(entryCompleteWatcher);
        cvv.addTextChangedListener(cvvWatcher);
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

    Card getCard() {
        return new Card(ccEditorView.getText().toString(),
                cvv.getText().toString(),
                toInt(expMonth.getText().toString()),
                toInt(expYear.getText().toString()));
    }

    private int toInt(String strValue) {
        int value = 0;
        try {
            value = Integer.parseInt(strValue);
        } catch (NumberFormatException ignored) {}
        return value;
    }

    public void setCard(Card card) {
        ccEditorView.setText(card.getNumber());
        cvv.setText(card.getCvv());
        expMonth.setText(String.format("%02d", card.getExpirationMonth()));
        expYear.setText(String.format("%02d", card.getExpirationYear()));
    }

    public void addBrandChangedListener(BrandChangedListener listener) {
        brandChangedListeners.add(listener);
    }

    public void addEntryCompleteListener(EntryCompleteListener listener) {
        this.entryCompleteListeners.add(listener);
    }


    private void fireBrandChange(Card.Brand brand) {
        for (BrandChangedListener brandChangedListener : brandChangedListeners) {
            brandChangedListener.brandChanged(this, brand);
        }
    }

    private void fireIncomplete() {
        for (EntryCompleteListener listener : entryCompleteListeners) {
            listener.entryIncomplete(this);
        }
    }

    private void fireComplete() {
        for (EntryCompleteListener listener : entryCompleteListeners) {
            listener.entryComplete(this);
        }
    }

    private class BrandChangedHandler implements BrandChangedListener {
        public void brandChanged(View sourceView, Card.Brand brand) {
            cardTextWatcher.setMaxFieldLength(brand.getMaxLength());
            cvvWatcher.setMaxFieldLength(brand.getCvvLength());
            cvv.setHint(brand.getCvvLength() == 4 ? fourDigitCvvHint : threeDigitCvvHint);
            imageView.setImageResource(brandToImageResourceMap.get(brand));
            fireBrandChange(brand);
        }
    }

    private class EntryCompleteFieldWatcher implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {}

        @Override
        public void afterTextChanged(Editable s) {
            Card card = getCard();
            if (card.isValid()) {
                fireComplete();
            } else {
                fireIncomplete();
            }
        }
    }
}
