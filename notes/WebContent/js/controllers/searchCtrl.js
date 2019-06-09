angular.module('notes')
.controller('searchCtrl', ['notesFactory','$location',function(notesFactory,$location){
    var searchVM = this;
    searchVM.notes=[];
    searchVM.search=null;
    searchVM.functions = {
    	where : function(route){
       			return $location.path() == route;
       		},
    	readNotes : function() {
    		notesFactory.getNotesSearch(searchVM.search)
				.then(function(response){
					console.log("buscando por ", searchVM.search);
	    			console.log("Reading all the notes: ", response);
	    			$location.path('/searchNotes');
	    			searchVM.notes = response;
	    		}, function(response){
	    			console.log("Error reading notes",searchVM.search);
	    		})
		}   
		
    }
	
}])