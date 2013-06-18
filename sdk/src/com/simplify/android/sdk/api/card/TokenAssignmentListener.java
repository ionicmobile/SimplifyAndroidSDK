package com.simplify.android.sdk.api.card;

import com.simplify.android.sdk.api.ErrorHandler;

/**
 * Callback interface when requesting a <code>CardToken</code> from the <strong>Simplify.com</strong> API.
 * @see com.simplify.android.sdk.api.card.Card
 */
public interface TokenAssignmentListener extends ErrorHandler {
    void tokenAssigned(CardToken token);
}
