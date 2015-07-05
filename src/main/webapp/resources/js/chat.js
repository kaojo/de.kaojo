var room = "test";
var user = "julian";

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

function send(sendButton, chatRoom) {
    var messageArea = getMessageArea(sendButton);
    var messageString = messageArea.value;
    if (messageString !== "") {
        var messageJson = convertMessageStringToJson(messageString);
        sendMessage(messageJson, chatRoom);
        messageArea.value = "";
        messageArea.focus();
    }
}

function sendMessage(message, chatRoom) {
    wsocket.send(message);
}

var wsocket;
var serviceLocation = "ws://localhost:8080/kaojo/chatrooms/";

function onMessageReceived(evt) {
    var msg = JSON.parse(evt.data);
    var author = msg.author;
    var content = msg.content;
    var date = new Date(msg.timestamp);
    var timestamp = getNicelyFormatedTime(date);
    var url = evt.currentTarget.url;
    var room = url.split("/")[5];
    var user = url.split("/")[6];
    var tabId = $("#tabview\\:chatTabView [href]:contains(" + room + ")").attr("href");
    displayMessageOnChatBoard(room, timestamp, author, content);
}


function convertMessageStringToJson(messageText) {
    author = '"author":"' + user + '"';
    content = '"content":"' + messageText + '"';
    timestamp = '"timestamp":"' + (new Date()).getTime() + '"';
    return '{' + author + ', ' + content + ', ' + timestamp + '}';
}

function escape(myid) {

    return "#" + myid.replace(/(:|\.|\[|\]|,)/g, "\\$1");

}

function displayMessageOnChatBoard(room, timestamp, author, content) {
    $(escape(room + ":chatBoard")).children("div:first")
            .append("<div>").children("div:last").addClass('ui-grid-row')
            .append("<div>").children("div:last").addClass('ui-grid-col-4 contentDiv')
            .append("<table>").children("table:last").attr("cellpadding", "1")
            .append("<tbody>").children("tbody:last")
            .append("<tr>").children("tr:last")
            .append("<td>").children("td:last").text(author)
            .parent().parent()
            .append("<tr>").children("tr:last")
            .append("<td>").children("td:last").text(timestamp)
            .parent().parent().parent().parent().parent()
            .append("<div>").children("div:last").addClass("ui-grid-col-8 contentDiv").text(content);
}


function getNicelyFormatedTime(date) {
    var D, M, Y, h, m;
    var day, month, year, hour, minute;
    D = date.getDate();
    M = date.getMonth();
    Y = date.getFullYear();
    h = date.getHours();
    m = date.getMinutes();
    day = D < 10 ? ('0' + D) : D;
    month = (M + 1) < 10 ? ('0' + M) : M;
    year = (Y + "").slice(-2);
    hour = h < 10 ? ('0' + h) : h;
    minute = m < 10 ? ('0' + m) : m;

    return day + "." + month + "." + year + " " + hour + ":" + minute;
}