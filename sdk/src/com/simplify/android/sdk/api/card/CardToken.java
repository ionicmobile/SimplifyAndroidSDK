package com.simplify.android.sdk.api.card;

/**
 * {
 * id=acd21bde-cc06-4023-9ee6-acfddaef2976,
 * used=false,
 * card={
 * id=EnKBX8,
 * type=VISA,
 * last4=1111,
 * expMonth=10.0,
 * expYear=16.0,
 * dateCreated=1.371153542826E12
 * }
 * }
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
