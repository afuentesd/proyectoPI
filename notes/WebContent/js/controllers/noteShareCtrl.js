angular.module('notes')
.controller('noteShareCtrl', ['notesFactory','usersnotesFactory','$routeParams','$location',
			function(notesFactory,usersnotesFactory,$routeParams,$location){
    var noteShareVM = this;
    noteShareVM.user={};
    noteShareVM.functions = {
    	createUserNote : function(id) {
    	        usersnotesFactory.postUserNote($routeParams.ID,noteShareVM.user.username)    	        
    				.then(function(response){
    					console.log("Creating usernote. Response:", response);
    					$location.path('/');
        			}, function(response){   
        				console.log("Error creating the usernote");
        			})
    		}
    }
    
}])