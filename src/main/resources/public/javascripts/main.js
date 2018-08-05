	$(function(){
	    $("#roomSelect").change(function() {
	    	var roomId = $('#roomSelect').find(":selected").text();
	      console.log('conceptName');
	      $("#roomDetails").empty().append('Name: 901 <br> Size: 2 <br> Reservion: No reservion <br>' +
	      		' <form action= "removeRoom" method=post>' +   
	      		'	<input value ='+roomId+' name ="roomId" type = "hidden">' +
	      		'	<input type = "submit" value="Remove room">' +
	      		'		</form> ')	      
	    });
	});