phonegap-plugin
===============

A Phonegap plugin for the Kiip SDK. Currently supports Android only.

Usage Instructions for Android
------------------------------

1. This guide assumes you've already installed and setup the PhoneGap SDK in your Android project. Take a look at the [Getting Started Guide] [phonegap-guide] for more help.

2. You need to download the Kiip SDK from the [developer area] [app.kiip.me]  and copy the files as directed. Don't worry about editing the Android Manifest or anything after that.

3. Download the Android plugin (it consists of a javascript and a java file). You will need to copy those into the exact same directories as they are currently in. You should have something like this:

    ```
    /assets/www/kiipPlugin.js
    /assets/www/cordova-{version}.js
    /assets/www/index.html
    /res/drawable/*/kp*.png
    /libs/cordova-{version}.jar
    /libs/kiip-{version}.jar
    /src/me/kiip/api/phonegap/KiipPhoneGapPlugin.java
    ```

4. Make sure both the Kiip and Cordova library are included in your build path.

5. Add the following line to your `/res/xml/plugins.xml`:

   ```
   <plugin name="KiipPlugin" value="me.kiip.api.phonegap.KiipPhoneGapPlugin" />
   ```

6. In your `index.html` include the `kiipPlugin.js` file in your documents `<head>`:

    ```
    <script type="text/javascript" charset="utf-8" src="kiipPlugin.js"></script>
    ```

7. You should have the Kiip SDK and Plugin all installed now. To use the SDK you will need to initialize Kiip on the [deviceready] [devready] callback by doing:

    ``` javascript
    kiip.init(api_key, api_secret, successCallback, failureCallback);
    ```

8. You can unlock an achievement or leaderboard by doing:

    ``` javascript
    kiip.unlockAchievement(achievement_id, successCallback, failureCallback);
    kiip.saveLeaderboard(leaderboard_id, score, successCallback, failureCallback);
    ```

9. On application close you should close the Kiip session by doing:

    ``` javascript
    kiip.endSession(successCallback, failureCallback);
    ```

[phonegap-guide]: http://docs.phonegap.com/en/1.7.0/guide_getting-started_android_index.md.html#Getting%20Started%20with%20Android
[app.kiip.me]: https://app.kiip.me/
[devready]: http://docs.phonegap.com/en/1.7.0/cordova_events_events.md.html#deviceready

Licence
------------------------------

The MIT License

Copyright (c) 2012 Kiip Inc.

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.