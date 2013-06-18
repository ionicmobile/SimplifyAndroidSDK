package com.simplify.android.sdk.api;

/**
 * All listener interfaces must extend <code>ErrorHandler</code> to provide feedback
 * when asynchronous calls to the <strong>Simplify.com</strong> API fail in some way.
 */
public interface ErrorHandler {
    void handleError(int statusCode, String message);
}
