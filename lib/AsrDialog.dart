import 'package:flutter/material.dart';


import 'baidu_asr_plugin.dart';

// 录音等待框
class AsrDialog extends StatefulWidget {
  AsrDialog({
    Key key,
  }) : super(key: key);

  @override
  State<StatefulWidget> createState() {
    return _AsrDialogState();
  }
}

class _AsrDialogState extends State<AsrDialog> {
  @override
  Widget build(BuildContext context) {
    return GestureDetector(
      onTap: () {
        hide();
      },
      child: Container(
        child: new Material(
          type: MaterialType.transparency,
          child: Center(
            //保证控件居中效果
            child: new SizedBox(
              width: 120,
              height: 140,
              child: new Container(
                decoration: ShapeDecoration(
                  shadows: [
                    BoxShadow(
                        color: Colors.grey,
                        offset: Offset(2, 2),
                        blurRadius: 2,
                        spreadRadius: 2)
                  ],
                  color: Color(0xffffffff),
                  shape: RoundedRectangleBorder(
                    borderRadius: BorderRadius.all(
                      Radius.circular(8.0),
                    ),
                  ),
                ),
                child: new Column(
                  mainAxisAlignment: MainAxisAlignment.center,
                  crossAxisAlignment: CrossAxisAlignment.center,
                  children: <Widget>[
                    Icon(
                      Icons.mic,
                      size: 80,
                      color: Colors.blueAccent,
                    ),
                    new Padding(
                      padding: const EdgeInsets.only(
                        top: 20.0,
                      ),
                      child: Column(children: [
                        new Text(
                          "正在录音...",
                          textScaleFactor: 1.0,
                          style: new TextStyle(fontSize: 12.0),
                        ),
                        new Text(
                          "(点击结束)",
                          textScaleFactor: 1.0,
                          style: new TextStyle(fontSize: 8.0),
                        ),
                      ]),
                    ),
                  ],
                ),
              ),
            ),
          ),
        ),
      ),
    );
  }

  void hide() {
    BaiduAsrPlugin.stop(context);
    Navigator.pop(context);
  }
}
