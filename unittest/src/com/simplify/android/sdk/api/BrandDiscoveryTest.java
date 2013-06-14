package com.simplify.android.sdk.api;

import com.simplify.android.sdk.api.card.Card;
import junit.framework.TestCase;

/**
 * @author Paul S. Hawke (paul.hawke@gmail.com)
 *         On: 6/7/13 at 9:32 PM
 */
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
