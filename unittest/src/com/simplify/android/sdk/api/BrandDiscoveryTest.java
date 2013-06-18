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

public class BrandDiscoveryTest extends TestCase {
    public void testJCB() {
        assertRange(3528, 3589, Card.Brand.JCB);

        assertTrue(Card.Brand.JCB.useLuhn());
        assertEquals(3, Card.Brand.DISCOVER.getCvcLength());
        assertEquals(16, Card.Brand.DISCOVER.getMaxLength());
    }

    public void testVisa() {
        assertEquals(Card.Brand.VISA, Card.Brand.lookup("4"));
        assertEquals(Card.Brand.VISA, Card.Brand.lookup("41"));
        assertEquals(Card.Brand.VISA, Card.Brand.lookup("411"));

        assertTrue(Card.Brand.VISA.useLuhn());
        assertEquals(3, Card.Brand.VISA.getCvcLength());
        assertEquals(16, Card.Brand.VISA.getMaxLength());
    }

    public void testChinaUnionPay() {
        assertEquals(Card.Brand.CHINA_UNIONPAY, Card.Brand.lookup("62"));
        assertEquals(Card.Brand.CHINA_UNIONPAY, Card.Brand.lookup("621"));

        assertFalse(Card.Brand.CHINA_UNIONPAY.useLuhn());
        assertEquals(3, Card.Brand.CHINA_UNIONPAY.getCvcLength());
        assertEquals(19, Card.Brand.CHINA_UNIONPAY.getMaxLength());
    }

    public void testDinersClub() {
        assertEquals(Card.Brand.DINERS_CLUB, Card.Brand.lookup("36"));
        assertEquals(Card.Brand.DINERS_CLUB, Card.Brand.lookup("361"));

        assertEquals(Card.Brand.DINERS_CLUB, Card.Brand.lookup("300"));
        assertEquals(Card.Brand.DINERS_CLUB, Card.Brand.lookup("301"));
        assertEquals(Card.Brand.DINERS_CLUB, Card.Brand.lookup("302"));
        assertEquals(Card.Brand.DINERS_CLUB, Card.Brand.lookup("303"));
        assertEquals(Card.Brand.DINERS_CLUB, Card.Brand.lookup("304"));
        assertEquals(Card.Brand.DINERS_CLUB, Card.Brand.lookup("305"));

        assertTrue(Card.Brand.DINERS_CLUB.useLuhn());
        assertEquals(3, Card.Brand.DINERS_CLUB.getCvcLength());
        assertEquals(16, Card.Brand.DINERS_CLUB.getMaxLength());
    }

    public void testAmex() {
        assertEquals(Card.Brand.AMEX, Card.Brand.lookup("34"));
        assertEquals(Card.Brand.AMEX, Card.Brand.lookup("341"));
        assertEquals(Card.Brand.AMEX, Card.Brand.lookup("37"));
        assertEquals(Card.Brand.AMEX, Card.Brand.lookup("371"));

        assertTrue(Card.Brand.AMEX.useLuhn());
        assertEquals(4, Card.Brand.AMEX.getCvcLength());
        assertEquals(15, Card.Brand.AMEX.getMaxLength());
    }

    public void testMastercard() {
        assertEquals(Card.Brand.MASTERCARD, Card.Brand.lookup("51"));
        assertEquals(Card.Brand.MASTERCARD, Card.Brand.lookup("52"));
        assertEquals(Card.Brand.MASTERCARD, Card.Brand.lookup("53"));
        assertEquals(Card.Brand.MASTERCARD, Card.Brand.lookup("54"));
        assertEquals(Card.Brand.MASTERCARD, Card.Brand.lookup("55"));

        assertEquals(Card.Brand.MASTERCARD, Card.Brand.lookup("511"));
        assertEquals(Card.Brand.MASTERCARD, Card.Brand.lookup("521"));
        assertEquals(Card.Brand.MASTERCARD, Card.Brand.lookup("531"));
        assertEquals(Card.Brand.MASTERCARD, Card.Brand.lookup("541"));
        assertEquals(Card.Brand.MASTERCARD, Card.Brand.lookup("551"));

        assertTrue(Card.Brand.MASTERCARD.useLuhn());
        assertEquals(3, Card.Brand.MASTERCARD.getCvcLength());
        assertEquals(16, Card.Brand.MASTERCARD.getMaxLength());
    }

    public void testDiscover() {
        assertEquals(Card.Brand.DISCOVER, Card.Brand.lookup("65"));
        assertEquals(Card.Brand.DISCOVER, Card.Brand.lookup("6011111111111111"));
        assertRange(644, 649, Card.Brand.DISCOVER);
        assertRange(622126, 622925, Card.Brand.DISCOVER);

        assertTrue(Card.Brand.DISCOVER.useLuhn());
        assertEquals(3, Card.Brand.DISCOVER.getCvcLength());
        assertEquals(16, Card.Brand.DISCOVER.getMaxLength());
    }

    private void assertRange(int min, int max, Card.Brand expected) {
        for (int i = min; i <= max; i++) {
            String prefix = String.valueOf(i);
            assertEquals(expected, Card.Brand.lookup(prefix));
            assertEquals(expected, Card.Brand.lookup(prefix + "123"));
        }
    }
}
