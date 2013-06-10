package com.simplify.android.sdk;

import com.simplify.android.sdk.Card;
import junit.framework.TestCase;

/**
 * @author Paul S. Hawke (paul.hawke@gmail.com)
 *         On: 6/7/13 at 9:32 PM
 */
public class BrandDiscoveryTest extends TestCase {
    public void testVisa() {
        assertEquals(Card.Brand.VISA, Card.Brand.lookup("4"));
        assertEquals(Card.Brand.VISA, Card.Brand.lookup("41"));
        assertEquals(Card.Brand.VISA, Card.Brand.lookup("411"));
    }

    public void testAmex() {
        assertEquals(Card.Brand.AMEX, Card.Brand.lookup("34"));
        assertEquals(Card.Brand.AMEX, Card.Brand.lookup("341"));
        assertEquals(Card.Brand.AMEX, Card.Brand.lookup("37"));
        assertEquals(Card.Brand.AMEX, Card.Brand.lookup("371"));
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
    }

    public void testDiscover() {
        assertEquals(Card.Brand.DISCOVER, Card.Brand.lookup("65"));
        assertEquals(Card.Brand.DISCOVER, Card.Brand.lookup("6011111111111111"));
        assertEquals(Card.Brand.DISCOVER, Card.Brand.lookup("644"));
        assertEquals(Card.Brand.DISCOVER, Card.Brand.lookup("645"));
        assertEquals(Card.Brand.DISCOVER, Card.Brand.lookup("646"));
        assertEquals(Card.Brand.DISCOVER, Card.Brand.lookup("647"));
        assertEquals(Card.Brand.DISCOVER, Card.Brand.lookup("648"));
        assertEquals(Card.Brand.DISCOVER, Card.Brand.lookup("649"));

        assertEquals(Card.Brand.DISCOVER, Card.Brand.lookup("651"));
        assertEquals(Card.Brand.DISCOVER, Card.Brand.lookup("6441"));
        assertEquals(Card.Brand.DISCOVER, Card.Brand.lookup("6451"));
        assertEquals(Card.Brand.DISCOVER, Card.Brand.lookup("6461"));
        assertEquals(Card.Brand.DISCOVER, Card.Brand.lookup("6471"));
        assertEquals(Card.Brand.DISCOVER, Card.Brand.lookup("6481"));
        assertEquals(Card.Brand.DISCOVER, Card.Brand.lookup("6491"));
    }
}
