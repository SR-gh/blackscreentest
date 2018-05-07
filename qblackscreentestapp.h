#ifndef QBLACKSCREENTESTAPP_H
#define QBLACKSCREENTESTAPP_H
#include <QGuiApplication>
#include <QAndroidActivityResultReceiver>
#include <memory>

class QBlackScreenTestApp : public QGuiApplication
{
    Q_OBJECT
    Q_PROPERTY(QString value MEMBER aValue NOTIFY valueChanged)

    struct QBSTAARR : public QAndroidActivityResultReceiver
    {
        static constexpr int RESULT_OK = -1;
        QBSTAARR(QBlackScreenTestApp * p_caller) : caller(p_caller) {}
        QBlackScreenTestApp * caller = nullptr;
        void handleActivityResult(int receiverRequestCode, int resultCode, const QAndroidJniObject &data) override;
    };

public:
#ifdef Q_QDOC
    QBlackScreenTestApp(int &argc, char **argv);
#else
    QBlackScreenTestApp(int &argc, char **argv, int af = ApplicationFlags);
#endif

    Q_INVOKABLE void startActivity();

signals:
    void valueChanged();
    void updateValue(QString newValue);

private:
    void onUpdateValue(QString newValue);

private:
    QString aValue = "42";
    std::unique_ptr<QBSTAARR> activityReceiver = std::unique_ptr<QBSTAARR>(new QBSTAARR(this));

};

#endif // QBLACKSCREENTESTAPP_H
