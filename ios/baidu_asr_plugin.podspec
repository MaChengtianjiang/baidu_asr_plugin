#
# To learn more about a Podspec see http://guides.cocoapods.org/syntax/podspec.html.
# Run `pod lib lint baidu_asr_plugin.podspec` to validate before publishing.
#
Pod::Spec.new do |s|
  s.name             = 'baidu_asr_plugin'
  s.version          = '0.0.1'
  s.summary          = '百度语音插件'
  s.description      = <<-DESC
A new Flutter plugin for baidu_asr SDK.
                       DESC
  s.homepage         = 'http://www.nxhope.com.cn/'
  s.license          = { :file => '../LICENSE' }
  s.author           = { '希望' => 'tottimctj@163.com' }
  s.source           = { :path => '.' }
  s.frameworks = ["SystemConfiguration","CoreLocation", "CoreTelephony","CFNetwork","AVFoundation","AudioToolbox", "GLKit"]
  s.source_files = 'Classes/**/*'
  s.public_header_files = '**/*.h'
  s.static_framework = true
  s.vendored_frameworks = '**/*.framework'
  s.libraries = [
        "c++"
  ]
  s.resources =   ['**/*.bundle', '**/*.dat']
  s.preserve_paths = '**/*.a'
  s.vendored_libraries = "**/*.a"



  s.dependency 'Flutter'
  s.platform = :ios, '9.0'


  # Flutter.framework does not contain a i386 slice.

  s.pod_target_xcconfig = { 'DEFINES_MODULE' => 'YES', 'EXCLUDED_ARCHS[sdk=iphonesimulator*]' => 'i386' }


end
