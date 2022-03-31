# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

# Please add these rules to your existing keep rules in order to suppress warnings.
# This is generated automatically by the Android Gradle plugin.
-dontwarn org.bouncycastle.jsse.BCSSLParameters
-dontwarn org.bouncycastle.jsse.BCSSLSocket
-dontwarn org.bouncycastle.jsse.provider.BouncyCastleJsseProvider
-dontwarn org.conscrypt.Conscrypt$Version
-dontwarn org.conscrypt.Conscrypt
-dontwarn org.openjsse.javax.net.ssl.SSLParameters
-dontwarn org.openjsse.javax.net.ssl.SSLSocket
-dontwarn org.openjsse.net.ssl.OpenJSSE
-dontwarn org.slf4j.impl.StaticLoggerBinder

-keepattributes LineNumberTable,SourceFile
-renamesourcefileattribute SourceFile

#Membuat laporan kode yang dihapus (atau dipertahankan)
-printusage mappings/usage.txt
#ingin melihat laporan titik entri yang ditentukan R8 dari aturan keep project Anda
-printseeds mappings/seeds.txt
-printmapping mappings/mapping.txt
-printconfiguration mappings/config.txt

#Flipper
-keep class com.facebook.jni.** { *; }
-keep class com.facebook.flipper.** { *; }
-dontwarn com.facebook.litho.**
-dontwarn com.facebook.flipper.**
-dontwarn com.facebook.yoga.**
-dontwarn org.mozilla.**
-dontwarn  com.facebook.fbui.**

-keepclassmembernames class androidx.appcompat.view.WindowCallbackWrapper {
    android.view.Window$Callback mWrapped;
}
