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
package com.simplify.android.sdk.api.payment;

import com.simplify.android.sdk.api.card.BasicCardDetails;
import com.simplify.android.sdk.api.card.Card;
import com.simplify.android.sdk.api.card.CardToken;

/**
 * Model a credit card payment, to expose the <strong>Simplify.com</strong> API.
 */
public class Payment {
    public static final String DEFAULT_CURRENCY = "USD";

    private long amount;
    private String currency;
    private BasicCardDetails card;
    private String customer;
    private String description;
    private String token;

    public Payment(long amount) {
        this(amount, DEFAULT_CURRENCY);
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

    /**
     * Make a payment, using the given <code>Card</code>.
     * @param card the card to charge
     * @param listener callback interface, for the resulting payment receipt
     * @return <code>true</code> if the asynchronous call was started.
     */
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

    /**
     * Make a payment, using the given <code>CardToken</code>.
     * @param token a <code>CardToken</code> representing the card to charge
     * @param listener callback interface, for the resulting payment receipt
     * @return <code>true</code> if the asynchronous call was started.
     */
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
