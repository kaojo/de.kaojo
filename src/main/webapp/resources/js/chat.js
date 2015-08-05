var serviceLocation = "ws://" + window.location.href.split("/")[2] + "/kaojo/chatrooms/";
var websockets = [];
var chatRooms = [];

$(document).ready(function () {
  getChatRooms();
  connectToChatRooms();
});

function getChatRooms() {
  $("#tabview\\:chatTabView [href][tabindex='-1']").each(function () {
    chatRooms.push($(this).text());
  });
}

function connectToChatRooms() {
  websockets = chatRooms.map(connectToChatRoom);
}

function connectToChatRoom(chatRoom) {
  var wsocket = new WebSocket(serviceLocation + chatRoom);
  wsocket.onmessage = onMessageReceived;
  return wsocket;
}
function onMessageReceived(evt) {
  var msg = JSON.parse(evt.data);
  var author = msg.author;
  var content = msg.content;
  var date = new Date(msg.timestamp);
  var timestamp = getNicelyFormatedTime(date);
  var url = evt.currentTarget.url;
  var room = url.split("/")[5];
  var tabId = $("#tabview\\:chatTabView [href]:contains(" + room + ")").attr("href");
  displayMessageOnChatBoard(room, timestamp, author, content);
}

function displayMessageOnChatBoard(chatRoom, timestamp, author, content) {
  var chatBoard = $(escape(chatRoom + ':chatBoard'));
  chatBoard.children("div:first")
          .append("<div>").children("div:last").addClass('ui-grid-row')
          .append("<div>").children("div:last").addClass('ui-grid-col-3 contentDiv')
          .append("<table>").children("table:last").attr("cellpadding", "1")
          .append("<tbody>").children("tbody:last")
          .append("<tr>").children("tr:last")
          .append("<td>").children("td:last").text(author)
          .parent().parent()
          .append("<tr>").children("tr:last")
          .append("<td>").children("td:last").text(timestamp)
          .parent().parent().parent().parent().parent()
          .append("<div>").children("div:last").addClass("ui-grid-col-9 contentDiv").text(content);
  chatBoard.scrollTop(chatBoard.prop("scrollHeight"));
}

function send(chatRoom) {
  var messageArea = $(escape(chatRoom + ':messageArea'));
  var messageString = messageArea.val();
  if (messageString !== "") {
    var messageJson = convertMessageStringToJson(messageString);
    sendMessage(messageJson, chatRoom);
    messageArea.val("");
    messageArea.focus();
  }
}

function sendWithEnter(event, messageArea, chatRoom) {
  if (event.keyCode === 13) {
    send(chatRoom);
    messageArea.value = "";
    return false;
  } else {
    return true;
  }
}

function sendMessage(message, chatRoom) {
  websockets.filter(function (wsocket) {
    if (chatRoom === wsocket.url.split("/")[5])
    {
      wsocket.send(message);
    }
  });
}

function convertMessageStringToJson(messageText) {
  author = '"author":"' + "" + '"';
  content = '"content":"' + messageText + '"';
  timestamp = '"timestamp":"' + (new Date()).getTime() + '"';
  return '{' + author + ', ' + content + ', ' + timestamp + '}';
}

function escape(myid) {

  return "#" + myid.replace(/(:|\.|\[|\]|,)/g, "\\$1");

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
