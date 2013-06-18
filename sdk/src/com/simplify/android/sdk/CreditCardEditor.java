/*
 * Copyright (c) 2013, Asynchrony Solutions, Inc.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *     * Redistributions of source code must retain the above copyright
 *       notice, this list of conditions and the following disclaimer.
 *
 *     * Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the distribution.
 *
 *     * Neither the name of Asynchrony nor the names of its contributors may
 *       be used to endorse or promote products derived from this software
 *       without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL ASYNCHRONY SOLUTIONS, INC. BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package com.simplify.android.sdk;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import com.simplify.android.sdk.api.card.Card;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p></p>Android UI component for entering credit card and address details, per the <strong>Simplify.com</strong>
 * API spec.</p>
 * <p>To use the card editor, incorporate it into your application's layout XML:<pre>
 &lt;?xml version="1.0" encoding="utf-8"?&gt;
 &nbsp;&nbsp;&lt;LinearLayout
 &nbsp;&nbsp;&nbsp;&nbsp;xmlns:android="http://schemas.android.com/apk/res/android"
 &nbsp;&nbsp;&nbsp;&nbsp;xmlns:simplify="http://schemas.android.com/apk/res/com.simplify.android.sdk"
 &nbsp;&nbsp;&nbsp;&nbsp;android:orientation="vertical"
 &nbsp;&nbsp;&nbsp;&nbsp;android:layout_width="fill_parent"
 &nbsp;&nbsp;&nbsp;&nbsp;android:layout_height="fill_parent"&gt;

 <i></strong>&nbsp;&nbsp;&lt;com.simplify.android.sdk.CreditCardEditor
 &nbsp;&nbsp;&nbsp;&nbsp;android:id="@+id/credit_card"
 &nbsp;&nbsp;&nbsp;&nbsp;simplify:showAddress="true"
 &nbsp;&nbsp;&nbsp;&nbsp;android:layout_gravity="center_horizontal"
 &nbsp;&nbsp;&nbsp;&nbsp;android:layout_width="wrap_content"
 &nbsp;&nbsp;&nbsp;&nbsp;android:layout_height="wrap_content" /&gt;</i>

 &lt;/LinearLayout&gt;
 * </pre></p>
 * <p>The <i>showAddress</i> parameter can be either <code>true</code> or <code>false</code> to
 * indicate whether address editing fields should appear in the GUI.</p>
 * <p>The credit card editor component will fire complete/incomplete events as is values are
 * edited and return a <code>Card</code> object for use with <strong>Simplify.com</strong> API calls.
 * The component will also fire brand change events.</p>
 */
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
    private EditText cvc;
    private EditText addressName;
    private EditText addressLine1;
    private EditText addressLine2;
    private EditText addressCity;
    private EditText addressState;
    private EditText addressZip;
    private EditText addressCountry;
    private ImageView imageView;
    private List<BrandChangedListener> brandChangedListeners;
    private List<EntryCompleteListener> entryCompleteListeners;
    private FixedLengthTextWatcher cardTextWatcher;
    private FixedLengthTextWatcher cvcWatcher;
    private FixedLengthTextWatcher zipWatcher;
    private String threeDigitCvcHint;
    private String fourDigitCvcHint;
    private boolean showAddress;

    public CreditCardEditor(Context context) {
        super(context);
        init(context, null);
    }

    public CreditCardEditor(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public CreditCardEditor(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.CreditCardEditor, 0, 0);
        try {
            showAddress = a.getBoolean(R.styleable.CreditCardEditor_showAddress, false);
        } finally {
            a.recycle();
        }

        createLayout(context, showAddress);

        EntryCompleteFieldWatcher entryCompleteWatcher = new EntryCompleteFieldWatcher();
        brandChangedListeners = new ArrayList<BrandChangedListener>();
        entryCompleteListeners = new ArrayList<EntryCompleteListener>();
        threeDigitCvcHint = context.getResources().getString(R.string.three_digit_cvc_hint);
        fourDigitCvcHint = context.getResources().getString(R.string.four_digit_cvc_hint);

        cardTextWatcher = new FixedLengthTextWatcher(ccEditorView, Card.Brand.UNKNOWN.getMaxLength());
        cardTextWatcher.setEntryCompleteListener(new EntryCompleteListener() {
            @Override
            public void entryComplete(View editorView) {
                expMonth.requestFocus();
            }

            @Override
            public void entryIncomplete(View editorView) {
            }
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
                cvc.requestFocus();
            }

            @Override
            public void entryIncomplete(View editorView) {
            }
        });
        expYear.addTextChangedListener(expYearWatcher);
        expYear.addTextChangedListener(entryCompleteWatcher);
        expYear.setNextFocusForwardId(R.id.cc_cvc);

        cvcWatcher = new FixedLengthTextWatcher(cvc, 3);
        cvc.addTextChangedListener(entryCompleteWatcher);
        cvc.addTextChangedListener(cvcWatcher);

        zipWatcher = new FixedLengthTextWatcher(addressZip, 5);
    }

    private void createLayout(Context context, boolean showAddress) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.cc_editor, this, true);
        LinearLayout ccAddressView = (LinearLayout) view.findViewById(R.id.cc_address_fields);
        ccAddressView.setVisibility(showAddress ? View.VISIBLE : View.GONE);

        ccEditorView = (EditText) view.findViewById(R.id.cc_field);
        imageView = (ImageView) view.findViewById(R.id.cc_icon);
        expMonth = (EditText) view.findViewById(R.id.cc_exp_month);
        expYear = (EditText) view.findViewById(R.id.cc_exp_year);
        cvc = (EditText) view.findViewById(R.id.cc_cvc);

        addressName = (EditText)view.findViewById(R.id.cc_address_name);
        addressLine1 = (EditText)view.findViewById(R.id.cc_address_line1);
        addressLine2 = (EditText)view.findViewById(R.id.cc_address_line2);
        addressCity = (EditText)view.findViewById(R.id.cc_address_city);
        addressState = (EditText)view.findViewById(R.id.cc_address_state);
        addressZip = (EditText)view.findViewById(R.id.cc_address_zip);
        addressCountry = (EditText)view.findViewById(R.id.cc_address_country);
    }

    public Card getCard() {
        Card card = new Card(ccEditorView.getText().toString(),
                cvc.getText().toString(),
                toInt(expMonth.getText().toString()),
                toInt(expYear.getText().toString()));
        if (showAddress) {
            card.setName(addressName.getText().toString());
            card.setAddressLine1(addressLine1.getText().toString());
            card.setAddressLine2(addressLine2.getText().toString());
            card.setAddressCity(addressCity.getText().toString());
            card.setAddressState(addressState.getText().toString());
            card.setAddressZip(addressZip.getText().toString());
            card.setAddressCountry(addressCountry.getText().toString());
        }
        return card;
    }

    private int toInt(String strValue) {
        int value = 0;
        try {
            value = Integer.parseInt(strValue);
        } catch (NumberFormatException ignored) {
        }
        return value;
    }

    public void setCard(Card card) {
        ccEditorView.setText(card.getNumber());
        cvc.setText(card.getCvc());
        expMonth.setText(String.format("%02d", card.getExpMonth()));
        expYear.setText(String.format("%02d", card.getExpYear()));
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
            cvcWatcher.setMaxFieldLength(brand.getCvcLength());
            cvc.setHint(brand.getCvcLength() == 4 ? fourDigitCvcHint : threeDigitCvcHint);
            imageView.setImageResource(brandToImageResourceMap.get(brand));
            fireBrandChange(brand);
        }
    }

    private class EntryCompleteFieldWatcher implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

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
