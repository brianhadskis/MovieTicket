/**
 * 
 */
$("document").ready(function() {
	
	$('#timeSelector').hide();
	
	$('#selectMovieDate').html("<option selected disabled>Select date</option>");
	
	$(document).on("keydown", "form", function(event){
	    if(event.keyCode == 13) {
	      event.preventDefault();
//	      $('#selectMovieTitle').val($('#selectMovieList'));
	    }
	  });
//	$('#selectMovieTitle').on("keydown", function(event) {
//		if(event.keyCode == 13) {
//			$('#selectMovieTitle').val($('#selectMovieList'));
//		}
//	});
	$('#selectMovieList').load("JQueryHandler", {
		command: "GET_MOVIE_LIST",
		movieTitle: $('#selectMovieTitle').val()
	});
	
	$('#browseContent').load("JQueryHandler", {
		command: "GET_BROWSE",
		filter: "ALL"
	});
	
	$('#selectMovieTitle').on('input', function(e) {
		var $input = $(this);
	       var val = $input.val();
	       var list = $input.attr('list');
	       var match = $('#'+list + ' option').filter(function() {
	           return ($(this).val() === val);
	       });

	    if(match.length > 0) {
    		$('#selectMovieDate').prop('disabled', false);
    		$('#selectMovieDate').load("JQueryHandler", {
    			command: "GET_MOVIE_DATES",
    			movieTitle: $('#selectMovieTitle').val()
    		});
	    } else {
	    	$('#selectMovieDate').html("<option selected disabled>Select date</option>");
	    	$('#selectMovieDate').prop('disabled', true);
	    	$('#selectMovieDate').prop('selectedIndex',0);
	    }
		
	  });
	
	$('#selectMovieDate').on('change', function() {
		$('#timeSelector').load("JQueryHandler", {
			command: "GET_MOVIE_TIMES",
			movieTitle: $('#selectMovieTitle').val(),
			movieDate: $('#selectMovieDate').val()
		});
		
		$('#timeSelector').show();
		
		$('#movieSelector').animate({left: '-200px'});
	});
	
	$('#browseAll').on("click", function() {
		$('#browseContent').load("JQueryHandler", {
			command: "GET_BROWSE",
			filter: "ALL"
		});
	});
	
	$('#browsePlaying').on("click", function() {
		$('#browseContent').load("JQueryHandler", {
			command: "GET_BROWSE",
			filter: "PLAYING"
		});
	});
	
	$('#browseComingSoon').on("click", function() {
		$('#browseContent').load("JQueryHandler", {
			command: "GET_BROWSE",
			filter: "COMING_SOON"
		});
	});
	
	$('.browse').on("click", function() {
		var movieTitle = $(this).prop('name');
		
		$('#selectMovieTitle').val(movieTitle);
	});
	
});