package com.simplify.android.sdk.api.payment;

import com.simplify.android.sdk.api.ErrorHandling;

public interface PaymentReceivedListener extends ErrorHandling {
    void paymentReceived(PaymentReceipt receipt);
}
