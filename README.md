# DLStringUtil
字符串和字节数据转换工具[![](https://jitpack.io/v/D10NGYANG/DLStringUtil.svg)](https://jitpack.io/#D10NGYANG/DLStringUtil)

## 使用
1 Add it in your root build.gradle at the end of repositories:
```gradle
allprojects {
  repositories {
    ...
    maven { url 'https://jitpack.io' }
  }
}
```
2 Add the dependency
```gradle
dependencies {
        implementation 'com.github.D10NGYANG:DLStringUtil:1.2'
}
```
3 混淆
```properties
-keep class com.d10ng.stringlib.** {*;}
-dontwarn com.d10ng.stringlib.**
```
