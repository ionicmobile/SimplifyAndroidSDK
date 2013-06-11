package com.simplify.android.sdk;

import android.view.View;

/**
* @author Paul S. Hawke (paul.hawke@gmail.com)
*         On: 6/11/13 at 2:13 AM
*/
public interface BrandChangedListener {
    void brandChanged(View sourceView, Card.Brand brand);
}
