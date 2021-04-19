package com.nxhope.baidu_asr_plugin;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.annotation.Nullable;

import io.flutter.plugin.common.MethodChannel.Result;

public class ResultStateful implements Result {
    private final static String TAG = "ResultStateful";
    private Result result;
    private boolean called;

    public static ResultStateful of(Result result) {

        return new ResultStateful(result);
    }

    private ResultStateful(Result result) {
        this.result = result;

    }

    @Override
    public void success(@Nullable final Object o) {
        if (called) {
            printError();
            return;
        }
        called = true;

        result.success(o);


    }

    @Override
    public void error(final String s, @Nullable final String s1, @Nullable final Object o) {
        if (called) {
            printError();
            return;
        }
        called = true;

        result.error(s, s1, o);


    }

    @Override
    public void notImplemented() {
        if (called) {
            printError();
            return;
        }
        called = true;
        result.notImplemented();

    }

    private void printError() {
        Log.e(TAG, "error:result called");
    }
}