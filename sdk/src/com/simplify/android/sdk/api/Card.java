package com.simplify.android.sdk.api;

import java.util.Calendar;

/**
 * @author Paul S. Hawke (paul.hawke@gmail.com)
 *         On: 6/7/13 at 8:59 PM
 */
public class Card extends BasicCardDetails {
    private String id;
    private Brand brand = Brand.UNKNOWN;

    public Card() {
        this("", "", 0, 0);
    }

    public Card(String number, String cvc, int expirationMonth, int expirationYear) {
        setNumber(number);
        setCvc(cvc);
        setExpMonth(expirationMonth);
        setExpYear(expirationYear);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        super.setNumber(number);
        setBrand(Brand.lookup(number));
    }

    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }

    public boolean isValid() {
        return number != null && number.length() <= brand.getMaxLength() && validateNumber(number) &&
                cvc != null && cvc.length() == brand.getCvcLength() && withinExpiration();
    }

    private boolean withinExpiration() {
        if (expMonth < 1 || expMonth > 12) {
            return false;
        }
        Calendar expire = Calendar.getInstance();
        expire.set(Calendar.MONTH, expMonth - 1);
        expire.set(Calendar.YEAR, 2000 + expYear);
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
        VISA(16, 3, "4"),
        MASTERCARD(16, 3, "51-55"),
        DISCOVER(16, 3, "622126-622925", "65", "6011", "644-649"),
        AMEX(15, 4, "34", "37"),
        DINERS_CLUB(16, 3, "300-305", "36"),
        CHINA_UNIONPAY(false, 19, 3, "62"),
        JCB(16, 3, "3528-3589"),
        UNKNOWN(16, 3);
        private final String[] prefixes;
        private final boolean useLuhn;
        private final int length;
        private final int cvcLength;

        private Brand(int length, int cvcLength, String... prefixes) {
            this(true, length, cvcLength, prefixes);
        }

        private Brand(boolean useLuhn, int length, int cvcLength, String... prefixes) {
            this.useLuhn = useLuhn;
            this.length = length;
            this.cvcLength = cvcLength;
            this.prefixes = prefixes;
        }

        public int getMaxLength() {
            return length;
        }

        public int getCvcLength() {
            return cvcLength;
        }

        public boolean useLuhn() {
            return useLuhn;
        }

        public static Brand lookup(String cardNumber) {
            if (cardNumber == null || cardNumber.trim().length() == 0) {
                return UNKNOWN;
            }

            for (Brand brand : values()) {
                for (String prefix : brand.prefixes) {
                    int dash = prefix.indexOf('-');
                    if (dash > 0) {
                        int startValue = Integer.parseInt(prefix.substring(0, dash));
                        int endValue = Integer.parseInt(prefix.substring(dash + 1, prefix.length()));
                        for (int prefixValue = startValue; prefixValue <= endValue; prefixValue++) {
                            if (cardNumber.startsWith(String.valueOf(prefixValue))) {
                                return brand;
                            }
                        }
                    } else if (cardNumber.startsWith(prefix)) {
                        return brand;
                    }
                }
            }
            return UNKNOWN;
        }
    }

    public boolean requestToken(TokenAssignmentListener listener) {
        if (!isValid()) {
            return false;
        }

        TokenAssignmentRequest request = new TokenAssignmentRequest(listener);
        request.execute(this);
        return true;
    }

}
