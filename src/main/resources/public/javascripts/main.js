$(function() {
	$("#roomSelect")
			.change(
					function() {
						var selectedRoom = $('#roomSelect').find(":selected");
						var roomId = selectedRoom.attr("data-id");
						var roomName = selectedRoom.text();
						var roomSize = selectedRoom.attr("data-size");
						console.log(roomId);
						$("#roomDetails")
								.empty()
								.append(
										'Name: '
												+ roomName
												+ ' <br> Size: '
												+ roomSize
												+ ' <br> Reservion: No reservion <br>'
												+ ' <form action= "removeRoom" method=post>'
												+ '	<input value ='
												+ roomId
												+ ' name ="roomId" type = "hidden">'
												+ '	<input type = "submit" value="Remove room">'
												+ '		</form> ')
					});
});