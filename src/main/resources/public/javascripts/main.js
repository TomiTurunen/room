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
							var roomSize = selectedRoom.attr("data-size") ? selectedRoom.attr("data-size") : "";
							var reserverName = selectedRoom
									.attr("data-reserverName");
							var removeReserveForm = "";
							var removeRoomForm = "";
							if (reserverName == null) {
								reserverName = "No Resereve"
								removeRoomForm = ' <form action= "removeRoom" method=post>'
										+ '	<input value ='
										+ roomId
										+ ' name ="roomId" type = "hidden">'
										+ '	<input class= "margin" type = "submit" value = "Remove room">'
										+ '		</form> '
							} else {
								removeReserveForm = '<form action= "removeReserve" method=post>'
										+ '	<input value ="'
										+ roomId
										+ '" name = "roomId" type = "hidden">'
										+ '	<input class= "margin" type = "submit" value="Remove Reserve">'
										+ '</form>';
							}

							$("#roomDetails")
									.empty()
									.append(
											'<form action="updateRoom" method=post>'
													+ '<table><tr><td>Name: </td><td><input name="name" type="text" value='
													+ roomName
													+ '></td></tr><tr><td>'
													+ 'Size: </td><td><input name="size" type="text" value='
													+ roomSize
													+ '></td></tr><tr><td>'
													+ 'Reserve:</td><td>'
													+ reserverName
													+ '</td></tr><tr><td>'
													+ '	<input value ="'
													+ roomId
													+ '"name ="updateRoomId" type = "hidden">'
													+ '	<input type = "submit" value="Update room information"></td></tr></table>'
													+ '</form><br>'
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
							var roomSize = selectedRoom.attr("data-size") ? selectedRoom.attr("data-size") : "";
							$("#roomDetails")
									.empty()
									.append(
											'<br><br><form action="reserveRoom" method=post>'
													+ '<table><tr><td>Name: </td><td>'
													+ roomName
													+ '</td></tr><tr><td> Size: </td><td>'
													+ roomSize
													+ '	</td></tr><tr><td>Reserver Name: </td>'
													+ '<td><input name ="reserverName" type = "text">'
													+ '	<input value ="'
													+ roomId
													+ '"name ="updateRoomId" type = "hidden"></td></tr>'
													+ '	<tr><td><input type = "submit" value="Reserve Room"></td></tr></table>'
													+ '</form> ')
						}
					});
});