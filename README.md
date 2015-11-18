Xposed-OnePlus-ReverseAlertSlider (with Vibrate mode)
=====================================================

This is a fork of a repo owned by @gavinhungry - credit goes to him for initial work.

Xposed module to reverse the direction of the OnePlus 2 Alert Slider and to make the switch be
Normal - Vibrate - None rather than Normal - Priority - None. This can be acheived with VibrateMode
on the app store, but required setting up each app to be a priority notification. This isn't required
with this module.

This module also disables the slider toast notification, as well as the
notification mode panel in the volume control popup.

Build
-----

    $ ant build

Install
-------

    $ adb install dist/ReverseAlertSlider.apk

License
-------
Released under the terms of the
[MIT license](http://tldrlegal.com/license/mit-license). See **LICENSE**.
