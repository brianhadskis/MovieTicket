/**
 * 
 */
$("document").ready(function() {
	
	$('#body_test').load("JQueryHandler", {
		command: "TEST"
	});
	
	$('#testButton').on("submit", function() {
		alert("Hi");
		$('#body_test').load("JQueryHandler", {
			command: "OTHER"
		});
	});
	
});