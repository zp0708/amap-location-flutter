## 0.2.0
* 适配 Flutter 3.35.7：迁移到 null safety，升级 Android/iOS 工程模板（AGP 8.9.1、Gradle 8.12、iOS Deployment Target 12.0）
* Android 端依赖升级为 `com.amap.api:location:11.2.100`（Maven Central），移除内置的 4.9.0 jar
* iOS 端依赖升级为 `AMapLocation ~> 2.12.2`
* 按新版 SDK 要求，在创建定位客户端前调用隐私合规接口 updatePrivacyShow/updatePrivacyAgree
* 修复 Android 端 locationMode/geoLanguage 直接按枚举下标取值在新版 SDK 下错位的问题，改为显式映射

## 0.1.4
* add platform condition for method getSystemAccuracyAuthorization to avoid android crash;

## 0.1.3
* fix Android cityCode and adCode return error
* add iOS cityCode

## 0.1.2
* update the dependent version number of the fluent SDK

## 0.1.1
* new features of adaptive ios14 positioning

## 0.1.0
* init release


