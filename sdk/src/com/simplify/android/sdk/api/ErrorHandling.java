package com.simplify.android.sdk.api;

/**
 * All listener interfaces must extend <code>ErrorHandling</code> to provide feedback
 * when asynchronous calls to the <strong>Simplify.com</strong> API fail in some way.
 */
public interface ErrorHandling {
    void handleError(int statusCode, String message);
}
