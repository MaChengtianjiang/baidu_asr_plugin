# baidu_asr_plugin

## 百度语音识别插件

参考文章: [https://www.jianshu.com/p/299c02f2cc3c]

### 事前准备

```
"请在官网新建应用，配置包名，并在此填写应用的 api key, secret key, appid(即appcode)"
```

### IOS


###### 请在自己info.plist中添加以下权限

```
	<key>NSMicrophoneUsageDescription</key>
	<string>获取麦克风权限，用于语音识别。</string>
```

###### 请修改ios目录下的[AsrManager.m],并填写 api key, secret key, appid

```
const NSString* API_KEY = @"api key";
const NSString* SECRET_KEY = @"secret key";
const NSString* APP_ID = @"appid";
```

---

### Android

###### 请在自己项目中添加以下权限

```
<uses-permission android:name="android.permission.RECORD_AUDIO" />
```

###### 请在自己项目中的[AndroidManifest.xml]的application标签中添加以下内容:

       <!--百度语音相关配置-->
       <meta-data
           android:name="com.baidu.speech.APP_ID"
           android:value="appid" />
       <meta-data
           android:name="com.baidu.speech.API_KEY"
           android:value="api key" />
       <meta-data
           android:name="com.baidu.speech.SECRET_KEY"
           android:value="secret key" />
    
       <service
           android:name="com.baidu.speech.VoiceRecognitionService"
           android:exported="false" />
       <!--百度语音相关配置-->
