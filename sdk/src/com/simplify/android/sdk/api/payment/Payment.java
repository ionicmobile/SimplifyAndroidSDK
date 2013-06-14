package com.simplify.android.sdk.api.payment;

import com.simplify.android.sdk.api.card.BasicCardDetails;
import com.simplify.android.sdk.api.card.Card;
import com.simplify.android.sdk.api.card.CardToken;

public class Payment {
    private long amount;
    private String currency;
    private BasicCardDetails card;
    private String customer;
    private String description;
    private String token;

    public Payment(long amount) {
        this(amount, "USD");
    }

    public Payment(long amount, String currency) {
        this.amount = amount;
        this.currency = currency;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public BasicCardDetails getCard() {
        return card;
    }

    public void setCard(BasicCardDetails card) {
        this.card = card;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public boolean isValid() {
        return amount > 0;
    }

    public boolean submitPayment(Card card, PaymentReceivedListener listener) {
        this.token = null;
        this.card = card;

        if (!isValid()) {
            return false;
        }

        PaymentByCardRequest request = new PaymentByCardRequest(listener);
        request.execute(card);
        return true;
    }

    public boolean submitPayment(CardToken token, PaymentReceivedListener listener) {
        this.token = token.getId();
        this.card = null;

        if (!isValid()) {
            return false;
        }

        PaymentByCardTokenRequest request = new PaymentByCardTokenRequest(listener);
        request.execute(token);
        return true;
    }
}
