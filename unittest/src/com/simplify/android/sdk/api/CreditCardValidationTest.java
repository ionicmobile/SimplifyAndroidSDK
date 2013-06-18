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
