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
package com.simplify.android.sdk.api;

import com.simplify.android.sdk.api.card.Card;
import junit.framework.TestCase;

import java.util.Calendar;

public class CreditCardValidationTest extends TestCase {
    public void testInvalidCardNumbers() {
        assertFalse(Card.validateNumber("1231231231"));

        Card card = createValidCard("1231231231", "000");
        assertFalse(card.isValid());
    }

    public void testValidCardNumber() {
        assertTrue(Card.validateNumber("4111111111111111"));

        Card card = createValidCard("4111111111111111", "000");
        assertTrue(card.isValid());
    }

    public void testValidCardNumberWithInvalidCvc() {
        Card card = createValidCard("4111111111111111", "");
        assertFalse(card.isValid());
    }

    public void testAmexCvcIsValidWithFourDigits() {
        Card card = createValidCard("378282246310005", "1234");
        assertTrue(card.isValid());

        card.setCvc("123");
        assertFalse(card.isValid());
    }

    public void testNullCardNumberIsTreatedAsInvalid() {
        assertFalse(Card.validateNumber(null));

        Card card = createValidCard(null, "000");
        assertFalse(card.isValid());
    }

    public void testEmptyCardNumberIsTreatedAsInvalid() {
        assertFalse(Card.validateNumber(""));

        Card card = createValidCard("", "000");
        assertFalse(card.isValid());
    }

    public void testExpiredCardFromLastYear() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR) % 100 - 1;
        Card card = createValidCard("4111111111111111", "000");
        card.setExpYear(year);
        assertFalse(card.isValid());
    }

    public void testExpiredCardFromLastMonth() {
        Calendar calendar = Calendar.getInstance();
        calendar.roll(Calendar.MONTH, -1);
        int year = calendar.get(Calendar.YEAR) % 100;
        int month = calendar.get(Calendar.MONTH) + 1;
        Card card = createValidCard("4111111111111111", "000");
        card.setExpYear(year);
        card.setExpMonth(month);
        assertFalse(card.isValid());
    }

    private Card createValidCard(String cardNumber, String cvc) {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR) % 100 + 1;
        // Card set to expire January next year
        return new Card(cardNumber, cvc, 1, year);
    }
}
