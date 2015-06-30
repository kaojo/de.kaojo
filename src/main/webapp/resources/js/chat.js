function getMessageArea(sendButton) {
    var parentTabElement = sendButton.parentNode.parentNode.parentNode.parentNode.parentNode.parentNode.parentNode;
    var messageAreaId = parentTabElement.id.slice(0, parentTabElement.id.lastIndexOf(":")) + ":messageArea";
    return document.getElementById(messageAreaId);
}

function send(sendButton) {
    var messageArea = getMessageArea(sendButton);
    var message = messageArea.value;
    if (message !== "") {
        sendMessage(message);
        messageArea.value = "";
        messageArea.focus();
    }
}

function sendMessage(message) {
    wsocket.send(message);
}

var wsocket;
var serviceLocation = "ws://localhost:8080/kaojo/chatrooms/";

function onMessageReceived(evt) {
    //var msg = eval('(' + evt.data + ')');
    var msg = JSON.parse(evt.data); // native API
    alert(msg);
}

function connectToChatserver() {
    room = "test";
    wsocket = new WebSocket(serviceLocation + room);
    wsocket.onmessage = onMessageReceived;
}

$(document).ready(function () {
    connectToChatserver();
});
