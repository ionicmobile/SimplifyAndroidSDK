package com.simplify.android.sdk;

import android.widget.EditText;

/**
* @author Paul S. Hawke (paul.hawke@gmail.com)
*         On: 6/11/13 at 2:13 AM
*/
public interface BrandChangedListener {
    void brandChanged(EditText view, Card.Brand brand);
}
