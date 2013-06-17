package com.simplify.android.sdk.api.payment;

import com.simplify.android.sdk.api.AsyncApiRequest;
import com.simplify.android.sdk.api.card.Card;
import org.apache.http.client.HttpResponseException;

/**
 * Used by a <code>Payment</code> to makes an asynchronous call to the <strong>Simplify.com</strong>
 * API to submit a payment on the given <code>Card</code>, returning a receipt to application code via a listener interface.
 *
 * @see com.simplify.android.sdk.api.card.Card
 * @see com.simplify.android.sdk.api.payment.Payment
 */
public class PaymentByCardRequest extends AsyncApiRequest<Card, PaymentReceipt, PaymentReceivedListener> {

    public PaymentByCardRequest(PaymentReceivedListener listener) {
        super(listener);
    }

    @Override
    protected PaymentReceipt callServer(Card... params) throws HttpResponseException {
        return null;
    }

    @Override
    protected void notifyDataReceived(PaymentReceivedListener listener, PaymentReceipt returnData) {
        listener.paymentReceived(returnData);
    }
}
