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

import com.simplify.android.sdk.api.card.Brand;
import junit.framework.TestCase;

public class BrandDiscoveryTest extends TestCase {
    public void testJCB() {
        assertRange(3528, 3589, Brand.JCB);

        assertTrue(Brand.JCB.useLuhn());
        assertEquals(3, Brand.DISCOVER.getCvcLength());
        assertEquals(16, Brand.DISCOVER.getMaxLength());
    }

    public void testVisa() {
        assertEquals(Brand.VISA, Brand.lookup("4"));
        assertEquals(Brand.VISA, Brand.lookup("41"));
        assertEquals(Brand.VISA, Brand.lookup("411"));

        assertTrue(Brand.VISA.useLuhn());
        assertEquals(3, Brand.VISA.getCvcLength());
        assertEquals(16, Brand.VISA.getMaxLength());
    }

    public void testChinaUnionPay() {
        assertEquals(Brand.CHINA_UNIONPAY, Brand.lookup("62"));
        assertEquals(Brand.CHINA_UNIONPAY, Brand.lookup("621"));

        assertFalse(Brand.CHINA_UNIONPAY.useLuhn());
        assertEquals(3, Brand.CHINA_UNIONPAY.getCvcLength());
        assertEquals(19, Brand.CHINA_UNIONPAY.getMaxLength());
    }

    public void testDinersClub() {
        assertEquals(Brand.DINERS_CLUB, Brand.lookup("36"));
        assertEquals(Brand.DINERS_CLUB, Brand.lookup("361"));

        assertEquals(Brand.DINERS_CLUB, Brand.lookup("300"));
        assertEquals(Brand.DINERS_CLUB, Brand.lookup("301"));
        assertEquals(Brand.DINERS_CLUB, Brand.lookup("302"));
        assertEquals(Brand.DINERS_CLUB, Brand.lookup("303"));
        assertEquals(Brand.DINERS_CLUB, Brand.lookup("304"));
        assertEquals(Brand.DINERS_CLUB, Brand.lookup("305"));

        assertTrue(Brand.DINERS_CLUB.useLuhn());
        assertEquals(3, Brand.DINERS_CLUB.getCvcLength());
        assertEquals(16, Brand.DINERS_CLUB.getMaxLength());
    }

    public void testAmex() {
        assertEquals(Brand.AMEX, Brand.lookup("34"));
        assertEquals(Brand.AMEX, Brand.lookup("341"));
        assertEquals(Brand.AMEX, Brand.lookup("37"));
        assertEquals(Brand.AMEX, Brand.lookup("371"));

        assertTrue(Brand.AMEX.useLuhn());
        assertEquals(4, Brand.AMEX.getCvcLength());
        assertEquals(15, Brand.AMEX.getMaxLength());
    }

    public void testMastercard() {
        assertEquals(Brand.MASTERCARD, Brand.lookup("51"));
        assertEquals(Brand.MASTERCARD, Brand.lookup("52"));
        assertEquals(Brand.MASTERCARD, Brand.lookup("53"));
        assertEquals(Brand.MASTERCARD, Brand.lookup("54"));
        assertEquals(Brand.MASTERCARD, Brand.lookup("55"));

        assertEquals(Brand.MASTERCARD, Brand.lookup("511"));
        assertEquals(Brand.MASTERCARD, Brand.lookup("521"));
        assertEquals(Brand.MASTERCARD, Brand.lookup("531"));
        assertEquals(Brand.MASTERCARD, Brand.lookup("541"));
        assertEquals(Brand.MASTERCARD, Brand.lookup("551"));

        assertTrue(Brand.MASTERCARD.useLuhn());
        assertEquals(3, Brand.MASTERCARD.getCvcLength());
        assertEquals(16, Brand.MASTERCARD.getMaxLength());
    }

    public void testDiscover() {
        assertEquals(Brand.DISCOVER, Brand.lookup("65"));
        assertEquals(Brand.DISCOVER, Brand.lookup("6011111111111111"));
        assertRange(644, 649, Brand.DISCOVER);
        assertRange(622126, 622925, Brand.DISCOVER);

        assertTrue(Brand.DISCOVER.useLuhn());
        assertEquals(3, Brand.DISCOVER.getCvcLength());
        assertEquals(16, Brand.DISCOVER.getMaxLength());
    }

    private void assertRange(int min, int max, Brand expected) {
        for (int i = min; i <= max; i++) {
            String prefix = String.valueOf(i);
            assertEquals(expected, Brand.lookup(prefix));
            assertEquals(expected, Brand.lookup(prefix + "123"));
        }
    }
}
