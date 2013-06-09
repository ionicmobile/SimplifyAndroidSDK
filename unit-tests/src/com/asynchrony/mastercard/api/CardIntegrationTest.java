package com.asynchrony.mastercard.api;

import junit.framework.TestCase;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Paul S. Hawke (paul.hawke@gmail.com)
 *         On: 6/8/13 at 2:45 PM
 */
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
        for (Card.Brand brand : data.keySet()){
            String[] cards = data.get(brand);
            for (String cardNumber : cards) {
                assertTrue(Card.validate(cardNumber));
                Card card = new Card(cardNumber);
                assertTrue(card.isValid());
            }
        }
    }

    public void testCardBrandValidity() {
        for (Card.Brand brand : data.keySet()){
            String[] cards = data.get(brand);
            for (String cardNumber : cards) {
                Card card = new Card(cardNumber);
                assertEquals(brand, Card.Brand.lookup(cardNumber));
                assertEquals(brand, card.getBrand());
            }
        }
    }


}
