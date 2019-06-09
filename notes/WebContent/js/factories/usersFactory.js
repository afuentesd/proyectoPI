angular.module('notes')
.factory('usersFactory',['$http', function($http){
	var url = 'https://localhost:8443/notes/rest/users/';
    var usersInterface = {
    	getUser : function(){
    		url = url ;
            return $http.get(url)
              	.then(function(response){
        			 return response.data;
               	});
    	},
    	getUserId : function(idu){
        	var urlid = url+idu;
            return $http.get(urlid)
            	.then(function(response){
            		return response.data;
            	});
        },
        postUser:  function(user){
    		return $http.post(url,user)
            	.then(function(response){
            		return response.status;
     			});
    	},
    	putUser : function(user){
    		var urlid = url+user.idu;
            return $http.put(urlid, user)
            	.then(function(response){
      				 return response.status;
  				});                   
    	},
    	deleteUser : function(idu){
        	var urlid = url+idu;
            return $http.delete(urlid)
            	.then(function(response){
            		return response.status;
            	});
        }
    	
    }
    return usersInterface;
}])