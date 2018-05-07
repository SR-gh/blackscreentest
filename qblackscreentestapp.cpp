#include "qblackscreentestapp.h"
#include <QtAndroid>
#include <QDebug>

#ifdef Q_QDOC
    QBlackScreenTestApp::QBlackScreenTestApp(int &argc, char **argv) : QGuiApplication(argc, argv)
#else
    QBlackScreenTestApp::QBlackScreenTestApp(int &argc, char **argv, int af) : QGuiApplication(argc, argv, af)
#endif
{
        connect(this, &QBlackScreenTestApp::updateValue, this, &QBlackScreenTestApp::onUpdateValue);
}

void QBlackScreenTestApp::startActivity()
{
    QAndroidJniObject jItent("android/content/Intent");
    jItent.callObjectMethod("setAction", "(Ljava/lang/String;)Landroid/content/Intent;", QAndroidJniObject::fromString("org.renan.android.dev.blackscreentest.intent.action.TEST").object());
    jItent.callObjectMethod("putExtra", "(Ljava/lang/String;Ljava/lang/String;)Landroid/content/Intent;", QAndroidJniObject::fromString("anExtraStringValue").object(), QAndroidJniObject::fromString(aValue).object());
    QtAndroid::startActivity(jItent, 7777, activityReceiver.get());
}

void QBlackScreenTestApp::onUpdateValue(QString newValue)
{
    aValue = newValue;
    emit valueChanged();
}

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

