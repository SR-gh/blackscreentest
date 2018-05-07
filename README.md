# blackscreentest
Demonstrates an issue whent starting an activity from Qt on Android

# Summary
When using Qt to launch an Activity, you may find yourself with the Qt Activity displaying a black screen.
The following Proof Of Concept has been made using Qt 5.10.1, NDK r10d.

# Details
When starting an Android Activity in order to get a result, you may use this kind of Java code :

        Intent i = new Intent();
        i.setAction(CUSTOM_INTENT);
        i.putExtra("anExtraStringValue", aValue);
        startActivityForResult(i, 1234);

Or, in C++, with Qt :

    QAndroidJniObject jItent("android/content/Intent");
    jItent.callObjectMethod("setAction", "(Ljava/lang/String;)Landroid/content/Intent;", QAndroidJniObject::fromString("org.renan.android.dev.blackscreentest.intent.action.TEST").object());
    jItent.callObjectMethod("putExtra", "(Ljava/lang/String;Ljava/lang/String;)Landroid/content/Intent;", QAndroidJniObject::fromString("anExtraStringValue").object(), QAndroidJniObject::fromString(aValue).object());
    QtAndroid::startActivity(jItent, 7777, activityReceiver.get());

Then, you can have the called Activity returning a result and your calling Activity to be called back through onActivityResult().

Here is the kind of code that handles this callback, in Java :

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1234)
        {
            Log.i("result", "onActivityResult: "+resultCode);
            aValue = data.getStringExtra("theStringResult");
            TextView text = (TextView) findViewById(R.id.aText);
            text.setText("Hello - " + aValue);
        }
    }

Or, in C++, with Qt :

    void QBlackScreenTestApp::QBSTAARR::handleActivityResult(int receiverRequestCode, int resultCode, const QAndroidJniObject &data)
    {
        qInfo() << "handleActivityResult(receiverRequestCode=" << receiverRequestCode << ", resultCode=" << resultCode << ",data=" << reinterpret_cast<const void*>(&data) << ")";
        if (7777 == receiverRequestCode)
        {
            QAndroidJniObject val = data.callObjectMethod("getStringExtra", "(Ljava/lang/String;)Ljava/lang/String;", QAndroidJniObject::fromString("theStringResult").object());
            QString valueAsString = val.toString();
            qInfo() << valueAsString;
            emit caller->updateValue(valueAsString);
        }
    }

It happens that this code works well in Java, but not in C++. If you pause your application (for example by typing the menu button), and, then, put it back to front, then you will get a black background with Qt and the calling Activity background with Java.

It looks like a bug. The question is : is it a bug using AndroidExtras in this example code or is it a bug in AndroidExtras ?

# How to run it ?
This repository contains three applications :
- the Qt C++ at the root, as a caller
- the two Java, in the java directory, one as a caller, the other as a callee

Calling is done using a custom Intent.

First, check that you have installed Qt, Qt Creator, Android NDK and Android SDK. The Android parts can be automatically downloaded by Android Studio.

You can open the .pro file in Qt Creator.

You can open the java project in the java directory in Android Studio.

In Qt Creator, choose an Android kit.

You probably need to change values in the following files :
- local.properties
- gradle.properties

Tip: If you don't know these values, you can use the « Create Templates » button in the « Projects view > Build Steps > Build Android APK > Android » section.

Build the called application (blackscreenlaunched). Run it in order to deploy it. Check in Android Studio that the run configuration associated with this application does run nothing, with the « Edit Configurations… » choice in the combo box, otherwise you will get an error about not finding the default Activity.

Run either of the caller applications (app in Java and BlackScreenTest in C++). It will start an Activity with a button. Press the button to start the called Activity.

If, at this moment, you press menu and go back to the application, you may experience a black screen with the Qt version and you probably won't with the Java version.

If you want to play a bit more, in the called Activity, press the button to get a result back to the caller and observe the change in the displayed text.
