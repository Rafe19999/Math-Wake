# MathWake Android V1

Native Java Android alarm app with a 3-question Class 10 math dismissal challenge.

## Features
- Pick one alarm time; schedules the next occurrence using AlarmManager.setAlarmClock.
- Persistent ringing foreground service and lock-screen challenge/full-screen alarm notification.
- Five randomized integer-answer question types: linear equation, distributive algebra, percentage, Pythagorean theorem and difference of squares.
- Exactly three correct answers dismiss the alarm; practice mode in main screen.
- Re-schedules pending one-time alarm after reboot or app update.

## Build (Android Studio)
1. Install Android Studio with Android SDK Platform 35 and JDK 17+.
2. Open the `MathWakeAndroid` directory as an existing Gradle project.
3. Let Android Studio sync/download Android Gradle Plugin 8.7.3.
4. Choose Build > Build Bundle(s) / APK(s) > Build APK(s).
5. Find `app/build/outputs/apk/debug/app-debug.apk` and install it on your Android phone.
6. Grant Notifications and ensure full-screen notification permission is allowed if your phone requires it.
7. Test a 1-minute-ahead alarm with the phone locked. Set a backup alarm until proven reliable on your model.

## Limitations
- One **non-repeating** alarm at a time; no snooze, history, custom sounds or difficulty options yet.
- Physical hardware power-off, force stop, Do Not Disturb, OEM battery optimizations, or disabled notification/full-screen permissions can prevent expected behavior.
- Android may allow users to silence alarms through OS controls; app cannot make an alarm physically impossible to bypass.
- Alarm may re-schedule to next day after reboot if the planned alarm passed while phone was off.
- Not compiled or device-tested in this environment: Android SDK and Gradle are not installed here.
