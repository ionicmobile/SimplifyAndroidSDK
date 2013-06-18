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
