package com.simplify.android.sdk;

import android.view.View;
import com.simplify.android.sdk.api.card.Card;

/**
 * Notified when the <code>Brand</code> associated with the card number being entered
 * has changed.
 * @see com.simplify.android.sdk.api.card.Card
 */
public interface BrandChangedListener {
    void brandChanged(View sourceView, Card.Brand brand);
}
