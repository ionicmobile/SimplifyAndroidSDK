package com.simplify.android.sdk.api.card;

import com.simplify.android.sdk.api.ErrorHandling;

public interface TokenAssignmentListener extends ErrorHandling {
    void tokenAssigned(CardToken token);
}
