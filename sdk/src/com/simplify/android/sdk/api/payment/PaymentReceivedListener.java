package com.simplify.android.sdk.api.payment;

import com.simplify.android.sdk.api.ErrorHandling;

/**
 * Callback interface when making a payment through the <strong>Simplify.com</strong> API.
 * @see com.simplify.android.sdk.api.payment.Payment
 */public interface PaymentReceivedListener extends ErrorHandling {
    void paymentReceived(PaymentReceipt receipt);
}
