package net.edrose.xposed.oneplus.ReverseVibrateSwitch;

import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XC_MethodReplacement;

import android.app.AndroidAppHelper;
import android.content.Context;
import android.media.AudioManager;

public class ReverseAlertSlider implements IXposedHookLoadPackage {
  public void handleLoadPackage(final LoadPackageParam lpparam) throws Throwable {
    if (lpparam.packageName.equals("com.android.systemui")) {
      try {

        XposedHelpers.findAndHookMethod("com.android.systemui.statusbar.policy.ZenModeControllerImpl", lpparam.classLoader,
          "setZen", int.class, new XC_MethodHook() {

          @Override
          protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
            // 1 - No interruptions
            // 2 - Priority interruptions only
            // 3 - All notifications
            int state = (Integer) param.args[0];
            Context moduleContext = AndroidAppHelper.currentApplication().createPackageContext("net.edrose.xposed.oneplus.ReverseVibrateSwitch", Context.CONTEXT_IGNORE_SECURITY);
            AudioManager manager = (AudioManager) moduleContext.getSystemService(Context.AUDIO_SERVICE);
            if (state == 1) {
              param.args[0] = 3;
              //Remove vibration
              manager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
            } else if (state == 2){
              //Set volume to vibrate
              manager.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
              param.args[0] = 3;
            } else if (state == 3) {
              param.args[0] = 1;
            }
          }
        });

        XposedHelpers.findAndHookMethod("com.android.systemui.volume.ZenModePanel", lpparam.classLoader,
          "handleUpdateZen", int.class, XC_MethodReplacement.DO_NOTHING);

        XposedHelpers.findAndHookMethod("com.android.systemui.volume.VolumePanel", lpparam.classLoader,
          "setZenPanelVisible", boolean.class, new XC_MethodHook() {

          @Override
          protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
            param.args[0] = false;
          }
        });

        XposedHelpers.findAndHookMethod("com.android.systemui.volume.ZenToast", lpparam.classLoader,
          "handleShow", int.class, String.class, XC_MethodReplacement.DO_NOTHING);

      } catch(Throwable t) {
        XposedBridge.log(t);
      }
    }
  }
}
