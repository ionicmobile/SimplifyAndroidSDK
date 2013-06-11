package com.simplify.android.sdk;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
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
    private FixedLengthTextWatcher expMonthWatcher;
    private FixedLengthTextWatcher expYearWatcher;
    private FixedLengthTextWatcher cvvWatcher;
    private BrandChangedTextWatcher brandChangedWatcher;
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
        brandChangedListeners = new ArrayList<BrandChangedListener>();
        threeDigitCvvHint = getResources().getString(R.string.three_digit_cvv_hint);
        fourDigitCvvHint = getResources().getString(R.string.four_digit_cvv_hint);

        Resources resources = context.getResources();

        ccEditorView = createTextView(context, R.id.cc_field, resources.getString(R.string.cc_field_hint));
        cardTextWatcher = new FixedLengthTextWatcher(ccEditorView, Card.Brand.UNKNOWN.getMaxLength());
        brandChangedWatcher = new BrandChangedTextWatcher(ccEditorView, new BrandChangedHandler());
        ccEditorView.addTextChangedListener(cardTextWatcher);
        ccEditorView.addTextChangedListener(brandChangedWatcher);
        ccEditorView.setPadding((int) (ccEditorView.getPaddingLeft() + resources.getDimension(R.dimen.edit_field_icon_space)),
                ccEditorView.getPaddingTop(), ccEditorView.getPaddingRight(), ccEditorView.getPaddingBottom());

        imageView = createIconView(context);
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        lp.addRule(RelativeLayout.ALIGN_BOTTOM, ccEditorView.getId());
        imageView.setPadding((int) resources.getDimension(R.dimen.icon_top_padding), 0, 0,
                (int) resources.getDimension(R.dimen.icon_bottom_padding));
        addView(imageView, lp);

        lp = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        lp.addRule(RelativeLayout.ALIGN_PARENT_TOP, imageView.getId());
        lp.addRule(RelativeLayout.ALIGN_PARENT_LEFT, imageView.getId());
        addView(ccEditorView, lp);
        ccEditorView.setNextFocusForwardId(R.id.cc_exp_month);

        expMonth = createTextView(context, R.id.cc_exp_month, resources.getString(R.string.cc_exp_month_hint));
        expMonthWatcher = new IntegerValueTextWatcher(expMonth, 2, MIN_MONTH, MAX_MONTH);
        expMonth.addTextChangedListener(expMonthWatcher);
        lp = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        lp.addRule(RelativeLayout.BELOW, ccEditorView.getId());
        lp.addRule(RelativeLayout.ALIGN_LEFT, ccEditorView.getId());
        addView(expMonth, lp);
        expMonth.setNextFocusForwardId(R.id.cc_exp_year);

        TextView slash = new TextView(context);
        slash.setTextSize(resources.getDimension(R.dimen.expiration_date_separator_text_size));
        slash.setText(R.string.slash);
        slash.setId(R.id.cc_exp_separator);
        slash.setPadding((int) resources.getDimension(R.dimen.slash_top_padding), 0, 0,
                (int) resources.getDimension(R.dimen.slash_bottom_padding));
        lp = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        lp.addRule(RelativeLayout.ALIGN_BOTTOM, expMonth.getId());
        lp.addRule(RelativeLayout.RIGHT_OF, expMonth.getId());
        addView(slash, lp);

        expYear = createTextView(context, R.id.cc_exp_year, getResources().getString(R.string.cc_exp_year_hint));
        expYearWatcher = new FixedLengthTextWatcher(expYear, 2);
        expYear.addTextChangedListener(expYearWatcher);
        lp = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        lp.addRule(RelativeLayout.BELOW, ccEditorView.getId());
        lp.addRule(RelativeLayout.RIGHT_OF, slash.getId());
        addView(expYear, lp);
        expYear.setNextFocusForwardId(R.id.cc_cvv);

        cvv = createTextView(context, R.id.cc_cvv, threeDigitCvvHint);
        cvvWatcher = new FixedLengthTextWatcher(cvv, 3);
        cvv.addTextChangedListener(cvvWatcher);
        lp = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        lp.addRule(RelativeLayout.BELOW, ccEditorView.getId());
        lp.addRule(RelativeLayout.ALIGN_RIGHT, ccEditorView.getId());
        addView(cvv, lp);

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

    private ImageView createIconView(Context context) {
        ImageView imageView = new ImageView(context);
        imageView.setId(R.id.cc_icon);
        imageView.setImageResource(R.drawable.brand_unknown);
        imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        return imageView;
    }

    private EditText createTextView(Context context, int fieldId, String hint) {
        EditText text = new EditText(context);
        text.setId(fieldId);
        text.setImeOptions(EditorInfo.IME_ACTION_NEXT);
        text.setInputType(EditorInfo.TYPE_CLASS_NUMBER);
        text.setHint(hint);
        text.setSingleLine(true);
        return text;
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
