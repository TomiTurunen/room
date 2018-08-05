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
								.append('<form action="updateRoom" method=post>'
										+ 'Name: <input name="name" type="text" value='+ roomName + '>'
												+ '<br> Size: <input name="size" type="text" value='+ roomSize + '>'
												+ ' <br> Reservion: No reservion <br>'
												+ '	<input value ="'+ roomId + '"name ="updateRoomId" type = "hidden">'
												+ '	<input type = "submit" value="Update room information">'
												+ '</form>'
												+ ' <form action= "removeRoom" method=post>'
												+ '	<input value ='
												+ roomId
												+ ' name ="roomId" type = "hidden">'
												+ '	<input type = "submit" value="Remove room">'
												+ '		</form> ')
					});
	$("#emptyRoomSelect")
		.change(
			function() {
				var selectedRoom = $('#emptyRoomSelect').find(":selected");
				var roomId = selectedRoom.attr("data-id");
				var roomName = selectedRoom.text();
				var roomSize = selectedRoom.attr("data-size");
				console.log(roomId);
				$("#roomDetails")
						.empty()
						.append('<form action="reserveRoom" method=post>'
								+ 'Name: '+ roomName
										+ '<br> Size: ' + roomSize
										+ ' <br> Reservion: No reservion <br>'
										+ '	<input name ="reserverName" type = "text"><br>'
										+ 'Reserver Name: '
										+ '	<input value ="'+ roomId + '"name ="updateRoomId" type = "hidden">'
										+ '	<input type = "submit" value="Reserve Room">'
										+ '</form> ')
			});
});