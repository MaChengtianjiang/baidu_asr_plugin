import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:fluttertoast/fluttertoast.dart';
import 'package:permission_handler/permission_handler.dart';

import 'AsrDialog.dart';

class BaiduAsrPlugin {
  //录音中的对话框
  static AsrDialog _dialog = new AsrDialog();
  static const MethodChannel _channel = const MethodChannel('asr_plugin');

  static Future<String> start(BuildContext context, {Map params}) async {

    showDialog(
      context: context,
      barrierDismissible: false,
      builder: (_) => _dialog,
    );
    debugPrint("开始录音");
    try {
      return await _channel.invokeMethod('start', params ?? {});
    } catch (e) {
      //没有声音的时候会报错 走取消
      await _checkPermissionMsg();
      return await _channel.invokeMethod('cancel');
    }
  }

  /// 停止录音
  static Future<void> stop(
      BuildContext context,
      ) async {
    debugPrint("停止录音");
    return await _channel.invokeMethod('stop');
  }

  /// 取消录音
  static Future<void> cancel(BuildContext context) async {
    debugPrint("取消录音");
    return await _channel.invokeMethod('cancel');
  }

  /// 录音，无对话框
  static Future<String> startAndNoDialog(BuildContext context,
      {Map params}) async {
    debugPrint("开始录音");
    try {
      return await _channel.invokeMethod('start', params ?? {});
    } catch (e) {
      //没有声音的时候会报错 走取消
      return await _channel.invokeMethod('cancel');
    }
  }

  // 是不是没有权限 没权限给提示
  static Future<void> _checkPermissionMsg() async {

    PermissionStatus permission = await Permission.microphone.request();
    if (permission != PermissionStatus.granted) {
      Fluttertoast.showToast(
          msg: "手机麦克风已被禁用,请打开麦克风权限", gravity: ToastGravity.CENTER, timeInSecForIosWeb: 2);

    }
  }
}
