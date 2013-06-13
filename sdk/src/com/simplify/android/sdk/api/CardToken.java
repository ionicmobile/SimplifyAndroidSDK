package com.simplify.android.sdk.api;

import java.util.Date;

/**
 * {
 *   id=acd21bde-cc06-4023-9ee6-acfddaef2976,
 *   used=false,
 *   card={
 *     id=EnKBX8,
 *     type=VISA,
 *     last4=1111,
 *     expMonth=10.0,
 *     expYear=16.0,
 *     dateCreated=1.371153542826E12
 *   }
 * }
 */
public class CardToken {
    private String id;
    private boolean used;
    private CardDetails card;

    public CardToken() {
        this.card = new CardDetails();
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

    public String getCardId() {
        return card.getId();
    }

    public void setCardId(String cardId) {
        this.card.setId(cardId);
    }

    public String getCardType() {
        return card.getType();
    }

    public void setCardType(String cardType) {
        this.card.setType(cardType);
    }

    public String getCardLast4() {
        return card.getLast4();
    }

    public void setCardLast4(String cardLast4) {
        this.card.setLast4(cardLast4);
    }

    public int getCardExpMonth() {
        return card.getExpMonth();
    }

    public void setCardExpMonth(int cardExpMonth) {
        this.card.setExpMonth(cardExpMonth);
    }

    public int getCardExpYear() {
        return card.getExpYear();
    }

    public void setCardExpYear(int cardExpYear) {
        this.card.setExpYear(cardExpYear);
    }

    public Date getCardDateCreated() {
        return new Date(card.getDateCreated());
    }

    public void setCardDateCreated(Date cardDateCreated) {
        this.card.setDateCreated(cardDateCreated.getTime());
    }

    public static class CardDetails {
        String id;
        String type;
        String last4;
        int expMonth;
        int expYear;
        long dateCreated;

        public CardDetails() {}

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getLast4() {
            return last4;
        }

        public void setLast4(String last4) {
            this.last4 = last4;
        }

        public int getExpMonth() {
            return expMonth;
        }

        public void setExpMonth(int expMonth) {
            this.expMonth = expMonth;
        }

        public int getExpYear() {
            return expYear;
        }

        public void setExpYear(int expYear) {
            this.expYear = expYear;
        }

        public long getDateCreated() {
            return dateCreated;
        }

        public void setDateCreated(long dateCreated) {
            this.dateCreated = dateCreated;
        }
    }
}
