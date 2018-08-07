$(function() {
	$("#roomSelect")
			.change(
					function() {
						var selectedRoom = $('#roomSelect').find(":selected");
						var roomId = selectedRoom.attr("data-id");
						// When select selected room
						if (!roomId) {
							$("#roomDetails").empty();
						} else {

							var roomName = selectedRoom.text();
							var roomSize = selectedRoom.attr("data-size");
							var reserverName = selectedRoom
									.attr("data-reserverName");
							var removeReserveForm = "";
							var removeRoomForm = "";
							console.log(reserverName);
							if (reserverName == null) {
								reserverName = "No Resereve"
								removeRoomForm = ' <form action= "removeRoom" method=post>'
										+ '	<input value ='
										+ roomId
										+ ' name ="roomId" type = "hidden">'
										+ '	<input type = "submit" value="Remove room">'
										+ '		</form> '
							} else {
								removeReserveForm = '<form action="removeReserve" method=post>'
										+ '	<input value ="'
										+ roomId
										+ '" name ="roomId" type = "hidden">'
										+ '	<input type = "submit" value="Remove Reserve">'
										+ '</form>';
							}

							$("#roomDetails")
									.empty()
									.append(
											'<form action="updateRoom" method=post>'
													+ 'Name: <input name="name" type="text" value='
													+ roomName
													+ '>'
													+ '<br> Size: <input name="size" type="text" value='
													+ roomSize
													+ '>'
													+ ' <br> Reserve:'
													+ reserverName
													+ '<br>'
													+ '	<input value ="'
													+ roomId
													+ '"name ="updateRoomId" type = "hidden">'
													+ '	<input type = "submit" value="Update room information">'
													+ '</form>'
													+ removeReserveForm
													+ removeRoomForm)
						}
					});

	$("#emptyRoomSelect")
			.change(
					function() {
						var selectedRoom = $('#emptyRoomSelect').find(
								":selected");
						var roomId = selectedRoom.attr("data-id");
						if (!roomId) {
							$("#roomDetails").empty();
						} else {
							var roomName = selectedRoom.text();
							var roomSize = selectedRoom.attr("data-size");
							$("#roomDetails")
									.empty()
									.append(
											'<form action="reserveRoom" method=post>'
													+ 'Name: '
													+ roomName
													+ '<br> Size: '
													+ roomSize
													+ '	<input name ="reserverName" type = "text"><br>'
													+ 'Reserver Name: '
													+ '	<input value ="'
													+ roomId
													+ '"name ="updateRoomId" type = "hidden">'
													+ '	<input type = "submit" value="Reserve Room">'
													+ '</form> ')
						}
					});
});