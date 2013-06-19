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
package com.simplify.android.sdk.api.card;

import java.util.Calendar;

/**
 * Model a credit card, to expose the <strong>Simplify.com</strong> API.
 */
public class Card extends BasicCardDetails {

    private String id;
    private Brand brand = Brand.UNKNOWN;

    public Card() {
        this("", "", 0, 0);
    }

    public Card(String number, String cvc, int expirationMonth, int expirationYear) {
        setNumber(number);
        setCvc(cvc);
        setExpMonth(expirationMonth);
        setExpYear(expirationYear);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    /**
     * Note: setting the card number will also compute the card <code>Brand</code> based on the
     * number provided.
     * @param number
     */
    public void setNumber(String number) {
        super.setNumber(number);
        setBrand(Brand.lookup(number));
    }

    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }

    /**
     * Validity check for a card.
     * @return <code>true</code> if the card number is valid (according to a Luhn check), the CVC is
     * filled in, and the card isnt expired.
     */
    public boolean isValid() {
        return number != null && number.length() <= brand.getMaxLength() && validateNumber(number) &&
                cvc != null && cvc.length() == brand.getCvcLength() && withinExpiration();
    }

    private boolean withinExpiration() {
        if (expMonth < 1 || expMonth > 12) {
            return false;
        }
        Calendar expire = Calendar.getInstance();
        expire.set(Calendar.MONTH, expMonth - 1);
        expire.set(Calendar.YEAR, 2000 + expYear);
        expire.roll(Calendar.MONTH, 1);
        Calendar now = Calendar.getInstance();
        return now.before(expire);
    }

    /**
     * <p>Returns true if the card number passes a Luhn formula check.</p>
     * <p>Note: if the input card number is <code>null</code>, an empty string or composed
     * entirely of whitespace it will be flagged as invalid.</p>
     *
     * @param cardNumber the card number to check.
     * @return <code>true</code> if the card number passes the Luhn check.
     */
    public static boolean validateNumber(String cardNumber) {
        if (cardNumber == null || cardNumber.trim().length() == 0) {
            return false;
        }

        int length = cardNumber.length();
        int sum = 0;
        for (int i = length - 2; i >= 0; i -= 2) {
            char c = cardNumber.charAt(i);
            if (c < '0' || c > '9') return false;

            // Multiply digit by 2.
            int v = (c - '0') << 1;

            // Add each digit independently.
            sum += v > 9 ? 1 + v - 10 : v;
        }

        // Add the rest of the non-doubled digits
        for (int i = length - 1; i >= 0; i -= 2) sum += cardNumber.charAt(i) - '0';

        // Double check that the Luhn check-digit at the end brings us to a neat multiple of 10
        return sum % 10 == 0;
    }

    /**
     * <p>Invoke the <strong>Simplify.com</strong> API server to request a <code>CardToken</code>.</p>
     * <p>The server will be invoked on a background thread, and results returned (on the main application
     * thread) by invoking the passed-in listener.</p>
     * @param listener Listener callback to invoke when a <code>CardToken</code> has been assigned, or an
     *                 error occured.
     * @return <code>true</code> if the asynchronous process was successfully started.  If the card is
     * invalid the method will return <code>false</code> and not start the asynchronous
     * server API call.
     */
    public boolean requestToken(TokenAssignmentListener listener) {
        if (!isValid()) {
            return false;
        }

        TokenAssignmentRequest request = new TokenAssignmentRequest(listener);
        request.execute(this);
        return true;
    }

}
