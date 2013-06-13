package com.simplify.android.sdk.api;

/**
 * Created with IntelliJ IDEA.
 * User: paul.hawke
 * Date: 6/13/13
 * Time: 4:24 PM
 * To change this template use File | Settings | File Templates.
 */
public interface ErrorHandling {
    void handleError(int statusCode, String message);
}
