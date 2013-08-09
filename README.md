SimplifyAndroidSDK
==================

Android SDK for Mastercard API. Download the binary [here](http://www.asynchrony.com/wp-content/themes/asynchrony3/simplify-androidsdk.zip). To get started, ensure that the AndroidSDK is in your path, and run the docs/project_setup.sh script.

Project Organization
--------------------

* ```docs``` - documents, project setup scripts, etc
* ```sdk``` - Android Library Project containing SDK code
* ```app``` - an Android Application using the SDK
* ```unittest``` - unit tests for the library

Using the card editor in an application
---------------------------------------

```xml
 <?xml version="1.0" encoding="utf-8"?>
   <LinearLayout
     xmlns:android="http://schemas.android.com/apk/res/android"
     xmlns:simplify="http://schemas.android.com/apk/res/com.simplify.android.sdk"
     android:orientation="vertical"
     android:layout_width="fill_parent"
     android:layout_height="fill_parent">

   <com.simplify.android.sdk.CreditCardEditor
     android:id="@+id/credit_card"
     simplify:showAddress="true"
     android:layout_gravity="center_horizontal"
     android:layout_width="wrap_content"
     android:layout_height="wrap_content" />

 </LinearLayout>
```

