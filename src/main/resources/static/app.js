var stompClient = null;
var uid = Math.floor(Math.random() * 10000000000000001);

function connect() {
    var socket = new SockJS('/gs-guide-websocket');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/chat', function (message) {
        	msg = JSON.parse(message.body);
            showMessage(msg.content, msg.user == uid);
        });
    });
}

function sendName() {
    stompClient.send("/app/postmessage", {}, JSON.stringify(
    				{'text': $("#send").val(),
    				 'user': uid}));
}

function showMessage(message, ownmessage) {
	if (ownmessage) {
		$("#chathistory").append(`
				<li class="clearfix">
                     <div class="message-data">
                         <span class="message-data-time">10:15 AM, Today</span>
                     </div>
                     <div class="message my-message">${message}</div>
                </li>`);
    } else {
    	$("#chathistory").append(`
    	        <li class="clearfix">
                      <div class="message-data text-right">
                         <span class="message-data-time">10:10 AM, Today</span>
                      </div>
                      <div class="message other-message float-right">${message}</div>
                </li>`);
    }
}

$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    connect();
    $( "#send" ).keypress(function(e) {
        if(e.which == 13) {
        	sendName();
        	$("#send").val("");
        }
    });
});