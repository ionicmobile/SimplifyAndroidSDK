package com.simplify.android.sdk.api.card;

/**
 * Enumerate the various supported brands, and pull together brand differences in such
 * a way as the rest of the code can be largely brand agnostic.
 */
public enum Brand {
    VISA(16, 3, "4"),
    MASTERCARD(16, 3, "51-55"),
    DISCOVER(16, 3, "622126-622925", "65", "6011", "644-649"),
    AMEX(15, 4, "34", "37"),
    DINERS_CLUB(16, 3, "300-305", "36"),
    CHINA_UNIONPAY(false, 19, 3, "62"),
    JCB(16, 3, "3528-3589"),
    UNKNOWN(16, 3);

    private final boolean useLuhn;
    private final int length;
    private final int cvcLength;
    private PrefixChecker[] prefixCheckers;

    Brand(int length, int cvcLength, String... prefixes) {
        this(true, length, cvcLength, prefixes);
    }

    Brand(boolean useLuhn, int length, int cvcLength, String... prefixes) {
        this.useLuhn = useLuhn;
        this.length = length;
        this.cvcLength = cvcLength;

        prefixCheckers = new PrefixChecker[prefixes.length];
        for (int i = 0; i < prefixes.length; i++) {
            int dash = prefixes[i].indexOf('-');
            prefixCheckers[i] = (dash > 0) ? new RangeChecker(prefixes[i]) : new SingleValueChecker(prefixes[i]);
        }
    }

    /**
     * @return the maximum length of a card number for this brand.
     */
    public int getMaxLength() {
        return length;
    }

    /**
     * @return the security code length for the current brand.
     */
    public int getCvcLength() {
        return cvcLength;
    }

    /**
     * @return whether the current brand requires a Luhn check for validity.
     */
    public boolean useLuhn() {
        return useLuhn;
    }

    /**
     * Given a credit card number, detect the brand of card.
     *
     * @return the <code>Brand</code> that corresponds to the card number passed in.
     */
    public static Brand lookup(String cardNumber) {
        if (cardNumber == null || cardNumber.trim().length() == 0) {
            return UNKNOWN;
        }

        for (Brand brand : values()) {
            for (PrefixChecker checker : brand.prefixCheckers) {
                if (checker.isMatch(cardNumber)) {
                    return brand;
                }
            }
        }
        return UNKNOWN;
    }

    private interface PrefixChecker {
        boolean isMatch(String cardNumber);
    }

    private class SingleValueChecker implements PrefixChecker {
        private final String prefix;

        private SingleValueChecker(String prefix) {
            this.prefix = prefix;
        }

        @Override
        public boolean isMatch(String cardNumber) {
            return cardNumber.startsWith(prefix);
        }
    }

    private class RangeChecker implements PrefixChecker {
        private int start;
        private int end;
        private int prefixLength;

        private RangeChecker(String prefix) {
            int dash = prefix.indexOf('-');
            String prefixStr = prefix.substring(0, dash);
            this.start = Integer.parseInt(prefixStr);
            this.end = Integer.parseInt(prefix.substring(dash + 1, prefix.length()));
            this.prefixLength = prefixStr.length();
        }

        @Override
        public boolean isMatch(String cardNumber) {
            String cardPrefixStr = cardNumber.substring(0, Math.min(cardNumber.length(), prefixLength));
            int card = Integer.parseInt(cardPrefixStr);
            return (card >= start && card <= end);
        }
    }
}
