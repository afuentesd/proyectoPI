angular.module('notes')
.controller('noteHandlerCtrl', ['notesFactory','usersFactory', 'usersnotesFactory','$routeParams','$location',
			function(notesFactory,usersFactory,usersnotesFactory,$routeParams,$location){
	var notesHandlerVM = this;
	notesHandlerVM.note={};
	notesHandlerVM.usernote={};
	notesHandlerVM.functions = {
   		where : function(route){
   			return $location.path() == route;
   		},
		readNote : function(id) {
			notesFactory.getNote(id)
				.then(function(response){
					console.log("Reading note with id: ", id," Response: ", response);
					notesHandlerVM.note = response;
				}, function(response){
					console.log("Error reading note");					
				})			
		},
		updateNote : function() {
			notesFactory.putNote(notesHandlerVM.note, notesHandlerVM.note.idn)
				.then(function(response){
					console.log("Updating note with id:",notesHandlerVM.note.idn," Response:", response);
					
    			}, function(response){
    				console.log("Error updating note");
    			})
		},	
		createNote : function() {
	        notesFactory.postNote(notesHandlerVM.note, notesHandlerVM.usernote)
				.then(function(response){
					console.log("Creating note. Response:", response);
					
    			}, function(response){    				
    				console.log("Error creating the note");
    			})
		},
		deleteNote : function(id) {
			notesFactory.deleteNote(notesHandlerVM.note.idn)
				.then(function(response){
					console.log("Deleting note with id:",id," Response:", response);
					$location.path('/');
				}, function(response){
					console.log("Error deleting note");
				})
		},
		readUserNote : function(id) {
			usersnotesFactory.getUserNote(id)
				.then(function(response){
					console.log("Reading usernote with id: ", id," Response: ", response);
					notesHandlerVM.usernote = response;
				}, function(response){
					console.log("Error reading usernote");
					$location.path('/');
				})
		},
		createUserNote : function() {
	        usersnotesFactory.postUserNote(notesHandlerVM.usernote)
				.then(function(response){
					console.log("Creating usernote. Response:", response);					
    			}, function(response){    				
    				console.log("Error creating the usernote");
    			})
		},
		updateUserNote : function() {
			usersnotesFactory.putUserNote(notesHandlerVM.usernote)
				.then(function(response){
					console.log("Updating usernote with id:",notesHandlerVM.usernote.idn," Response:", response);
    			}, function(response){
    				console.log("Error updating usernote");
    			})
		},
		noteHandlerSwitcher : function(){
			if (notesHandlerVM.functions.where('/newNote')){
				console.log($location.path());				
				notesHandlerVM.functions.createNote();

			}
			else if (notesHandlerVM.functions.where('/editNote/'+notesHandlerVM.note.idn)){
				console.log($location.path());
				if(notesHandlerVM.usernote.archived ==undefined){
					notesHandlerVM.usernote.archived=0;
				}
				if(notesHandlerVM.usernote.pinned ==undefined){
					notesHandlerVM.usernote.pinned=0;
				}
				notesHandlerVM.functions.updateNote();
				notesHandlerVM.functions.updateUserNote();
				
			}
			else if (notesHandlerVM.functions.where('/deleteNote/'+notesHandlerVM.note.idn)){
				console.log($location.path());
				notesHandlerVM.functions.deleteNote(notesHandlerVM.note.idn);
			}
			else {
			console.log($location.path());
			}
			$location.path('/');
		}
	}
   	console.log("Entering noteHandlerCtrl with $routeParams.ID=",$routeParams.ID);
   	if ($routeParams.ID!=undefined){
   		notesHandlerVM.functions.readNote($routeParams.ID);
   		notesHandlerVM.functions.readUserNote($routeParams.ID);
   	}
}]);