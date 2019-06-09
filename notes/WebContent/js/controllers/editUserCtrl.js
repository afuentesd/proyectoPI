angular.module('notes')
.controller('editUserCtrl', ['usersFactory','$routeParams','$location','$window',
			function(usersFactory,$routeParams,$location,$window){
	var editUserVM = this;
	editUserVM.user={};
	editUserVM.functions = {
   		where : function(route){
   			return $location.path() == route;
   		},
		readUser : function() {
			usersFactory.getUser()
				.then(function(response){
					editUserVM.user = response;
					console.log("Reading user with id: ",response.id," Response: ", response);
    			}, function(response){
    				console.log("Error reading user data");
    			})
		},		
		updateUser : function() {
			usersFactory.putUser(editUserVM.user, editUserVM.user.idu)
				.then(function(response){
					console.log("Updating user with id:",editUserVM.user.idu," Response:", response);
    			}, function(response){
    				console.log("Error updating user");
    			})
		},
		deleteUser : function() {
			usersFactory.deleteUser(editUserVM.user.idu)
				.then(function(response){
					console.log("Deleting user with id:",editUserVM.user.idu," Response:", response);
					$window.location = "../LogoutServlet";
				}, function(response){
					console.log("Error deleting user");
				})
		}
		
	}
	editUserVM.functions.readUser();
}]);