#!/bin/bash

android create lib-project --name SimplifyAndroidSDK --target 1 --path ./sdk --package com.simplify.android.sdk

android create project --target 1 --name SimplifyAndroidSdkExample --path ./app --activity MainActivity --package com.simplify.android.sdk

android update project --target 1 --path ./app --library ./sdk

android create test-project -m ./app -n unittest -p ./unittest

android create test-project -m ./app -n integrationtest -p ./integrationtest

