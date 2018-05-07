#include <QGuiApplication>
#include <QQmlApplicationEngine>
#include "qblackscreentestapp.h"
#include <QQmlContext>

int main(int argc, char *argv[])
{
    QCoreApplication::setAttribute(Qt::AA_EnableHighDpiScaling);

    QBlackScreenTestApp app(argc, argv);

    QQmlApplicationEngine engine;
    engine.rootContext()->setContextProperty("myApp", &app);
    engine.load(QUrl(QStringLiteral("qrc:/main.qml")));
    if (engine.rootObjects().isEmpty())
        return -1;

    return app.exec();
}
