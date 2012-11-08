Kiip PhoneGap Plugin v2.0.0beta
===============================

A Phonegap plugin for the Kiip SDK. Supports iOS and Android.

Installation Instructions for Android
------------------------------

1. This guide assumes you've already installed and setup the PhoneGap SDK in your Android project. Take a look at the [Getting Started Guide] [phonegap-guide-android] for more help.

2. You need to download the Kiip SDK from the [developer area] [docs.kiip.me]  and copy the files as directed. Don't worry about editing the Android Manifest or anything after that.

3. Download the Android plugin (it consists of a javascript and a java file). You will need to copy those into the exact same directories as they are currently in. You should have something like this:

    ```
    /assets/www/kiipPlugin.js
    /assets/www/cordova-{version}.js
    /assets/www/index.html
    /res/drawable/*/kp*.png
    /libs/cordova-{version}.jar
    /libs/kiip-{version}.jar
    /libs/android-support-v4.jar
    /src/me/kiip/api/phonegap/KiipPhoneGapPlugin.java
    ```

4. Make sure both the Kiip and Cordova library are included in your build path.

5. Add the following line to your `/res/xml/config.xml`:

   <plugin name="KiipPlugin" value="me.kiip.api.phonegap.KiipPhoneGapPlugin" />

6. In your `index.html` include the `kiipPlugin.js` file in your documents `<head>`:

    <script type="text/javascript" charset="utf-8" src="kiipPlugin.js"></script>

7. On the [resume] [resume] callback you will need to call `kiip.startSession(success, failure)`. Calling startSession before initialization will cause crashes.

8. On the [pause] [pause] callback you will need to call `kiip.endSession(success, failure)`.

Installation Instructions for iOS
----------------------------------

1. This guide assumes you've already installed and setup the PhoneGap SDK in your iOS project. Take a look at the [Getting Started Guide] [phonegap-guide-ios] for more help.

2. You need to download the Kiip SDK from the [developer area] [docs.kiip.me] and copy the files as directed into XCode.

3. Download the iOS plugin (it consists of a javascript file, obj-c header and implementation file).

4. Install the iOS plugin.

Usage Instructions
===================

1. You should have the Kiip SDK and Plugin all installed now. To use the SDK you will need to initialize Kiip by doing:

    kiip.init(api_key, api_secret, successCallback, failureCallback);

2. You can save a moment by doing:

    kiip.saveMoment(moment_id, score, successCallback, failureCallback);

3. You can also listen in for virtual currency or swarms by implementing the following callbacks:

    kiip.listenContent(onContentCallback);
    kiip.listenSwarm(onSwarmCallback);


Help!
========

This plugin is officially supported by the Kiip Support team. If you need a hand you can reach us at [kiip.me] [http://docs.kiip.com/#support].


[phonegap-guide-android]: http://docs.phonegap.com/en/2.2.0/guide_getting-started_android_index.md.html#Getting%20Started%20with%20Android
[phonegap-guide-ios]: http://docs.phonegap.com/en/2.2.0/guide_getting-started_ios_index.md.html#Getting%20Started%20with%20iOS
[docs.kiip.me]: https://docs.kiip.me/
[resume]: http://docs.phonegap.com/en/2.2.0/cordova_events_events.md.html#resume
[pause]: http://docs.phonegap.com/en/2.2.0/cordova_events_events.md.html#pause
[kiip.me]: http://docs.kiip.com/#support

Licence
------------------------------

The MIT License

Copyright (c) 2012 Kiip Inc.

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
