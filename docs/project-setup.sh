#!/bin/bash

~/android-sdk-macosx/tools/android create lib-project --name SimplifyAndroidSDK --target 1 --path ~/AndroidProjects/SimplifyAndroidSDK/sdk --package com.simplify.android.sdk

~/android-sdk-macosx/tools/android create project --target 1 --name SimplifyAndroidSdkExample --path ~/AndroidProjects/SimplifyAndroidSDK/app --activity MainActivity --package com.simplify.android.sdk

~/android-sdk-macosx/tools/android update project --target 1 --path ~/AndroidProjects/SimplifyAndroidSDK/app --library ~/AndroidProjects/SimplifyAndroidSDK/sdk

~/android-sdk-macosx/tools/android create test-project -m ~/AndroidProjects/SimplifyAndroidSDK/app -n unittest -p ~/AndroidProjects/SimplifyAndroidSDK/unittest

~/android-sdk-macosx/tools/android create test-project -m ~/AndroidProjects/SimplifyAndroidSDK/app -n integrationtest -p ~/AndroidProjects/SimplifyAndroidSDK/integrationtest

