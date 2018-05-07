import QtQuick 2.9
import QtQuick.Window 2.2
import QtQuick.Controls 2.2

Window {
    visible: true
    width: 640
    height: 480
    title: qsTr("Qt Activity")
    Column
    {
        anchors.centerIn: parent
        spacing: name.height
        Button
        {
            text: "Start Activity"
            onPressed: myApp.startActivity()
        }
        Text
        {
            id: name
            text: "Value = " + myApp.value
        }
    }
}
