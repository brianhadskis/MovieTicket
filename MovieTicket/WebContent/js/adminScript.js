/**
 * 
 */

$("document").ready(function() {
	
	if ($("#add_movie_rating").length) {
		$("#add_movie_rating").load("AdminJQuery", {
			command: "GET_RATING_LIST"
		});
	}
	
	if ($("#add_schedule_theatre").length) {
		$("#add_schedule_theatre").load("AdminJQuery", {
			command: "GET_THEATRE_LIST"
		});
	}
	
	if ($("#add_schedule_movie").length) {
		$("#add_schedule_movie").load("AdminJQuery", {
			command: "GET_MOVIE_LIST"
		});
	}
	
	if ($("#add_ticket_ticketType").length) {
		$("#add_ticket_ticketType").load("AdminJQuery", {
			command: "GET_TICKET_TYPE_LIST"
		});
	}
	
	if ($("#update_movie_rating").length) {
		var movieId = $('#movieId').prop('value');
		$("#update_movie_rating").load("AdminJQuery", {
			command: "GET_RATING_LIST_SELECTED",
			theMovieId: movieId
		});
	}
	if ($("#update_schedule_theatre").length) {
		var theatreId = $('#theatreId').prop('value');
		$("#update_schedule_theatre").load("AdminJQuery", {
			command: "GET_THEATRE_LIST_SELECTED",
			theTheatreId: theatreId
		});
	}
	
	if ($("#update_schedule_movie").length) {
		var movieId = $('#movieId').prop('value');
		$("#update_schedule_movie").load("AdminJQuery", {
			command: "GET_MOVIE_LIST_SELECTED",
			theMovieId: movieId
		});
	}
	
	if ($("#update_ticket_ticketType").length) {
		var ticketTypeId = $('#ticketTypeId').prop('value');
		$("#update_ticket_ticketType").load("AdminJQuery", {
			command: "GET_TICKET_TYPE_LIST_SELECTED",
			theTicketTypeId: ticketTypeId
		});
	}
	
});