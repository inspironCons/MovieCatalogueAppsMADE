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
#custom view
 -keep class made.dicoding.moviecatalogueapps.core.component.ErrorState**
 -keepclassmembers class made.dicoding.moviecatalogueapps.core.component.ErrorState** {*;}

  # Proguard rules that are applied to your test apk/code.
 -ignorewarnings

 -keepattributes *Annotation*

 -dontnote junit.framework.**
 -dontnote junit.runner.**

 -dontwarn android.test.**
 -dontwarn android.support.test.**
 -dontwarn org.junit.**
 -dontwarn org.hamcrest.**
 -dontwarn com.squareup.javawriter.JavaWriter
 # Uncomment this if you use Mockito
 -dontwarn org.mockito.**