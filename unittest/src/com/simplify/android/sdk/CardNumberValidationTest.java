package com.simplify.android.sdk;

import com.simplify.android.sdk.Card;
import junit.framework.TestCase;

/**
 * @author Paul S. Hawke (paul.hawke@gmail.com)
 *         On: 6/7/13 at 10:32 PM
 */
public class CardNumberValidationTest extends TestCase {
    public void testInvalidCardNumbers() {
        assertFalse(Card.validate("1231231231"));

        Card card = new Card("1231231231");
        assertFalse(card.isValid());
    }

    public void testValidCardNumber() {
        assertTrue(Card.validate("11111218"));

        Card card = new Card("11111218");
        assertTrue(card.isValid());
    }

    public void testNullCardNumberIsTreatedAsInvalid() {
        assertFalse(Card.validate(null));

        Card card = new Card(null);
        assertFalse(card.isValid());
    }

    public void testEmptyCardNumberIsTreatedAsInvalid() {
        assertFalse(Card.validate(""));

        Card card = new Card("");
        assertFalse(card.isValid());
    }
}
