package com.nxhope.baidu_asr_plugin;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.util.Log;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.Map;

import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.embedding.engine.plugins.activity.ActivityAware;
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;
import io.flutter.plugin.common.PluginRegistry.Registrar;


import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.nxhope.baidu_asr_plugin.*;

/**
 * BaiduAsrPlugin
 */
public class BaiduAsrPlugin implements FlutterPlugin, MethodCallHandler, ActivityAware {
    /// The MethodChannel that will the communication between Flutter and native Android
    ///
    /// This local reference serves to register the plugin with the Flutter Engine and unregister it
    /// when the Flutter Engine is detached from the Activity
    private MethodChannel channel;

    private Activity activity;
    private ResultStateful resultStateful;
    private AsrManager asrManager;

    private final static String TAG = "AsrPlugin";

    @Override
    public void onAttachedToEngine(@NonNull FlutterPluginBinding flutterPluginBinding) {
        channel = new MethodChannel(flutterPluginBinding.getBinaryMessenger(), "asr_plugin");


        channel.setMethodCallHandler(this);
    }

    @Override
    public void onAttachedToActivity(@NonNull ActivityPluginBinding binding) {
        this.activity = binding.getActivity();


    }

    @Override
    public void onDetachedFromActivityForConfigChanges() {
        // TODO: the Activity your plugin was attached to was
        // destroyed to change configuration.
        // This call will be followed by onReattachedToActivityForConfigChanges().
    }

    @Override
    public void onReattachedToActivityForConfigChanges(ActivityPluginBinding activityPluginBinding) {
        // TODO: your plugin is now attached to a new Activity
        this.activity = activityPluginBinding.getActivity();
        // after a configuration change.
    }

    @Override
    public void onDetachedFromActivity() {
        // TODO: your plugin is no longer associated with an Activity.
        // Clean up references.
    }

    @Override
    public void onMethodCall(@NonNull MethodCall call, @NonNull Result result) {

        initPermission();
        switch (call.method) {
            case "start":
                resultStateful = ResultStateful.of(result);
                start(call, resultStateful);
                break;
            case "stop":
                stop(call, result);
                break;
            case "cancel":
                cancel(call, result);
                break;
            default:
                result.notImplemented();
                break;
        }
    }

    @Override
    public void onDetachedFromEngine(@NonNull FlutterPluginBinding binding) {
        channel.setMethodCallHandler(null);
    }


    private void start(MethodCall call, ResultStateful result) {
        if (activity == null) {
            Log.e(TAG, "Ignored start, current activity is null.");
            result.error("Ignored start, current activity is null.", null, null);
            return;
        }
        if (getAsrManager() != null) {
            getAsrManager().start(call.arguments instanceof Map ? (Map) call.arguments : null);
        } else {
            Log.e(TAG, "Ignored start, current getAsrManager is null.");
            result.error("Ignored start, current getAsrManager is null.", null, null);
        }
    }

    private void stop(MethodCall call, MethodChannel.Result result) {
        if (asrManager != null) {
            asrManager.stop();
        }
    }

    private void cancel(MethodCall call, MethodChannel.Result result) {
        if (asrManager != null) {
            asrManager.cancel();
        }
    }

    @Nullable
    private AsrManager getAsrManager() {
        if (asrManager == null) {
            if (activity != null && !activity.isFinishing()) {
                asrManager = new AsrManager(activity, onAsrListener);
            }
        }
        return asrManager;
    }

    /**
     * android 6.0 以上需要动态申请权限
     */
    private void initPermission() {
        String permissions[] = {Manifest.permission.RECORD_AUDIO,
                Manifest.permission.ACCESS_NETWORK_STATE,
                Manifest.permission.INTERNET,
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        };

        ArrayList<String> toApplyList = new ArrayList<String>();

        for (String perm : permissions) {
            if (PackageManager.PERMISSION_GRANTED != ContextCompat.checkSelfPermission(activity, perm)) {
                toApplyList.add(perm);
                //进入到这里代表没有权限.

            }
        }
        String tmpList[] = new String[toApplyList.size()];
        if (!toApplyList.isEmpty()) {
            ActivityCompat.requestPermissions(activity, toApplyList.toArray(tmpList), 123);
        }

    }

    private OnAsrListener onAsrListener = new OnAsrListener() {
        @Override
        public void onAsrReady() {

        }

        @Override
        public void onAsrBegin() {

        }

        @Override
        public void onAsrEnd() {

        }

        @Override
        public void onAsrPartialResult(String[] results, RecogResult recogResult) {

        }

        @Override
        public void onAsrOnlineNluResult(String nluResult) {

        }

        @Override
        public void onAsrFinalResult(String[] results, RecogResult recogResult) {
            if (resultStateful != null) {
                resultStateful.success(results[0]);
            }
        }

        @Override
        public void onAsrFinish(RecogResult recogResult) {

        }

        @Override
        public void onAsrFinishError(int errorCode, int subErrorCode, String descMessage, RecogResult recogResult) {
            if (resultStateful != null) {
                resultStateful.error(descMessage, null, null);
            }
        }

        @Override
        public void onAsrLongFinish() {

        }

        @Override
        public void onAsrVolume(int volumePercent, int volume) {

        }

        @Override
        public void onAsrAudio(byte[] data, int offset, int length) {

        }

        @Override
        public void onAsrExit() {

        }

        @Override
        public void onOfflineLoaded() {

        }

        @Override
        public void onOfflineUnLoaded() {

        }
    };
}
