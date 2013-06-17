package com.simplify.android.sdk.api.card;

/**
 * Model a credit card token, to expose the <strong>Simplify.com</strong> API.
 */

public class CardToken {
    private String id;
    private boolean used;
    private BasicCardDetails card;

    public CardToken() {
        this.card = new BasicCardDetails();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isUsed() {
        return used;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }

    public BasicCardDetails getCard() {
        return card;
    }

    public void setCard(BasicCardDetails card) {
        this.card = card;
    }
}
