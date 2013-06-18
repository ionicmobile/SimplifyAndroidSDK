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
import java.util.HashMap;
import java.util.Map;

public class CardIntegrationTest extends TestCase {
    Map<Card.Brand, String[]> data = new HashMap<Card.Brand, String[]>() {{
        put(Card.Brand.VISA, new String[]{
                "4012888888881881",
                "4222222222222",
                "4111111111111111"
        });
        put(Card.Brand.AMEX, new String[]{
                "378282246310005",
                "371449635398431",
                "378734493671000"
        });
        put(Card.Brand.MASTERCARD, new String[]{
                "5555555555554444",
                "5105105105105100"
        });
        put(Card.Brand.DISCOVER, new String[]{
                "6011111111111117",
                "6011000990139424"
        });
    }};

    public void testCardNumberValidity() {
        for (Card.Brand brand : data.keySet()) {
            String[] cards = data.get(brand);
            for (String cardNumber : cards) {
                assertTrue(Card.validateNumber(cardNumber));
                Card card = createValidCard(brand, cardNumber);
                assertTrue(card.isValid());
            }
        }
    }

    public void testCardBrandValidity() {
        for (Card.Brand brand : data.keySet()) {
            String[] cards = data.get(brand);
            for (String cardNumber : cards) {
                Card card = createValidCard(brand, cardNumber);
                assertEquals(brand, Card.Brand.lookup(cardNumber));
                assertEquals(brand, card.getBrand());
            }
        }
    }

    private Card createValidCard(Card.Brand brand, String cardNumber) {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR) % 100 + 1;
        int month = 1;
        return new Card(cardNumber, brand.equals(Card.Brand.AMEX) ? "0000" : "000", month, year);
    }
}
