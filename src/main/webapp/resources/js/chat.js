var room ="test";
var user ="julian";

$(document).ready(function () {
    connectToChatserver();
});

function connectToChatserver() {
    wsocket = new WebSocket(serviceLocation + room + '/' + user);
    wsocket.onmessage = onMessageReceived;
}
function getMessageArea(sendButton) {
    var parentTabElement = sendButton.parentNode.parentNode.parentNode.parentNode.parentNode.parentNode.parentNode;
    var messageAreaId = parentTabElement.id.slice(0, parentTabElement.id.lastIndexOf(":")) + ":messageArea";
    return document.getElementById(messageAreaId);
}

function send(sendButton) {
    var messageArea = getMessageArea(sendButton);
    var messageString = messageArea.value;
    if (messageString !== "") {
        var messageJson = convertMessageStringToJson(messageString);
        sendMessage(messageJson);
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
    var author = msg.author;
    var content = msg.content;
    var timestamp = msg.timestamp;
    
    alert();
}


function convertMessageStringToJson(messageText) {
    author = '"author":"' + user + '"';
    content = '"content":"' + messageText + '"';
    timestamp = '"timestamp":"' + (new Date()).toUTCString() + '"';
    return '{' + author + ', ' + content  + ', ' + timestamp + '}';
}