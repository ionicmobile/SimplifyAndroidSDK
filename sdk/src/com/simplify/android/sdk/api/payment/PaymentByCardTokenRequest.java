package com.simplify.android.sdk.api.payment;

import com.simplify.android.sdk.api.AsyncApiRequest;
import com.simplify.android.sdk.api.card.CardToken;
import org.apache.http.client.HttpResponseException;

/**
 * Used by a <code>Payment</code> to makes an asynchronous call to the <strong>Simplify.com</strong>
 * API to submit a payment on the given <code>Card</code> represented by the server-assigned token,
 * returning a receipt to application code via a listener interface.
 *
 * @see com.simplify.android.sdk.api.card.CardToken
 * @see com.simplify.android.sdk.api.payment.Payment
 */
class PaymentByCardTokenRequest extends AsyncApiRequest<CardToken, PaymentReceipt, PaymentReceivedListener> {

    public PaymentByCardTokenRequest(PaymentReceivedListener listener) {
        super(listener);
    }

    @Override
    protected PaymentReceipt callServer(CardToken... params) throws HttpResponseException {
        return null;
    }

    @Override
    protected void notifyDataReceived(PaymentReceivedListener listener, PaymentReceipt returnData) {
        listener.paymentReceived(returnData);
    }
}
