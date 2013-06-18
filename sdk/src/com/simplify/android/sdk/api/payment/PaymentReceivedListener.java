package com.simplify.android.sdk.api.payment;

import com.simplify.android.sdk.api.ErrorHandler;

/**
 * Callback interface when making a payment through the <strong>Simplify.com</strong> API.
 * @see com.simplify.android.sdk.api.payment.Payment
 */
public interface PaymentReceivedListener extends ErrorHandler {
    void paymentReceived(PaymentReceipt receipt);
}
