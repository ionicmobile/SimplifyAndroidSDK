package com.simplify.android.sdk;

import java.util.Calendar;

/**
 * @author Paul S. Hawke (paul.hawke@gmail.com)
 *         On: 6/7/13 at 8:59 PM
 */
public class Card {
    private Brand brand = Brand.UNKNOWN;
    private String number;
    private String cvv;
    private int expirationMonth;
    private int expirationYear;

    public Card() {
        this("", "", 0, 0);
    }

    public Card(String number, String cvv, int expirationMonth, int expirationYear) {
        setNumber(number);
        setCvv(cvv);
        setExpirationMonth(expirationMonth);
        setExpirationYear(expirationYear);
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
        this.brand = Brand.lookup(number);
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }

    public int getExpirationMonth() {
        return expirationMonth;
    }

    public void setExpirationMonth(int expirationMonth) {
        this.expirationMonth = expirationMonth;
    }

    public int getExpirationYear() {
        return expirationYear;
    }

    public void setExpirationYear(int expirationYear) {
        this.expirationYear = expirationYear;
    }

    public Brand getBrand() {
        return brand;
    }

    public boolean isValid() {
        return number != null && number.length() <= brand.getMaxLength() && validateNumber(number) &&
                cvv != null && cvv.length() == brand.getCvvLength() && withinExpiration();
    }

    private boolean withinExpiration() {
        if (expirationMonth < 1 || expirationMonth > 12) {
            return false;
        }
        Calendar expire = Calendar.getInstance();
        expire.set(Calendar.MONTH, expirationMonth-1);
        expire.set(Calendar.YEAR, 2000 + expirationYear);
        expire.roll(Calendar.MONTH, 1);
        Calendar now = Calendar.getInstance();
        return now.before(expire);
    }

    /**
     * Returns true if the card # passes Luhn's formula.
     */
    public static boolean validateNumber(String cardNumber) {
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
        VISA(16,3, "4"),
        MASTERCARD(16,3, "51", "52", "53", "54", "55"),
        DISCOVER(16,3, "65", "6011", "644", "645", "646", "647", "648", "649"),
        AMEX(15,4, "34", "37"),
        UNKNOWN(16, 3);
        private final String[] prefixes;
        private final int length;
        private final int cvvLength;

        private Brand(int length, int cvvLength, String... prefixes) {
            this.length = length;
            this.cvvLength = cvvLength;
            this.prefixes = prefixes;
        }

        public int getMaxLength() {
            return length;
        }

        public int getCvvLength() {
            return cvvLength;
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
