angular.module('signUp', [])
.factory("signUpFactory", ['$http',function($http){
	var url = 'https://localhost:8443/notes/rest/users/';
	 var signUpInterface = {
		addUser : function(user){
    		return $http.post(url,user)
        	.then(function(response){
        		return response.status;
 			});
	}	 
	}
    return signUpInterface;
	 
}])
.controller('registerCtrl', function(signUpFactory,$window,$location){
    var registerVM = this;
    registerVM.user={};
    registerVM.error=null;
    registerVM.functions = {
    	addUser: function() {
    		signUpFactory.addUser(registerVM.user)
    			.then(function(response){
					console.log("Creating User. Response:", response);
					$window.location.href="https://localhost:8443/notes/";
    			}, function(response){
    				console.log("Error creating the User");
    			});    			
    	},
    
     
    };
   
})
