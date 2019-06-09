angular.module('notes')
.controller('listCtrl', ['notesFactory','$location',function(notesFactory,$location){
    var listVM = this;
    listVM.notes=[];
    listVM.functions = {
    	where : function(route){
       			return $location.path() == route;
       		},
    	readNotes : function() {
    		notesFactory.getNotes()
				.then(function(response){
	    			console.log("Reading all the notes: ", response);
	    			listVM.notes = response;
	    		}, function(response){
	    			console.log("Error reading notes");
	    		})
		},    
		readNotesArchived : function() {
			notesFactory.getNotesArchived()
				.then(function(response){
					console.log("Reading all the notes: ", response);
					listVM.notes = response;
				}, function(response){
					console.log("Error reading notes");
				})
		}
    }
	if ($location.path()=='/archivedNotes'){
		console.log("obteniendo notas archivdas",$location.path());
	    listVM.functions.readNotesArchived(); 		
   	}else{
   		console.log("obteniendo notas ",$location.path());
		listVM.functions.readNotes();
   	}
}])