package com.simplify.android.sdk;

/**
 * @author Paul S. Hawke (paul.hawke@gmail.com)
 *         On: 6/7/13 at 8:59 PM
 */
public class Card {
    private String number;

    public Card() {
        this("");
    }

    public Card(String number) {
        this.number = number;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Brand getBrand() {
        return Brand.lookup(number);
    }

    public boolean isValid() {
        return validate(getNumber());
    }

    /**
     * Returns true if the card # passes Luhn's formula.
     */
    public static boolean validate(String cardNumber) {
        if (cardNumber == null || cardNumber.trim().length() == 0) {
            return false;
        }

        int length = cardNumber.length();
        int sum = 0;
        for (int i = length - 2; i >= 0; i -= 2) {
            char c = cardNumber.charAt(i);
            if (c < '0' || c > '9') return false;

            // Multiply digit by 2.
            int v = (c - '0') << 1;

            // Add each digit independently.
            sum += v > 9 ? 1 + v - 10 : v;
        }

        // Add the rest of the non-doubled digits
        for (int i = length - 1; i >= 0; i -= 2) sum += cardNumber.charAt(i) - '0';

        // Double check that the Luhn check-digit at the end brings us to a neat multiple of 10
        return sum % 10 == 0;
    }

    public enum Brand {
        VISA("4"),
        MASTERCARD("51", "52", "53", "54", "55"),
        DISCOVER("65", "6011", "644", "645", "646", "647", "648", "649"),
        AMEX("34", "37"),
        UNKNOWN();
        private final String[] prefixes;

        private Brand(String... prefixes) {
            this.prefixes = prefixes;
        }

        public static Brand lookup(String cardNumber) {
            if (cardNumber == null || cardNumber.trim().length() == 0) {
                return UNKNOWN;
            }

            for (Brand brand : values()) {
                for (String prefix : brand.prefixes) {
                    if (cardNumber.startsWith(prefix)) {
                        return brand;
                    }
                }
            }
            return UNKNOWN;
        }
    }
}
