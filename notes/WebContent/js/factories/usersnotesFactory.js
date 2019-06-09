angular.module('notes')
.factory("usersnotesFactory", ['$http',function($http){
	var url = 'https://localhost:8443/notes/rest/usersnotes/';
    var usersnotesInterface = {
    	getUsersNotes: function(){
    		return $http.get(url)
    			.then(function(response){
    				return response.data;
    			});
    	},
    	getUserNote : function(id){
    		var urlid = url + id;
            return $http.get(urlid)
            	.then(function(response){
            		return response.data;
         		});
    	},
    	putUserNote : function(usernote){
    		var urlid = url + usernote.idn;
            return $http.put(urlid, usernote)
            	.then(function(response){
      				 return response.status;
  				});                   
    	},
    	postUserNote:  function(id,usernote){
    		var urlid = url + id;
    		return $http.post(urlid,usernote)
            	.then(function(response){
            		return response.status;
     			});
    	}, 
        deleteUserNote : function(id){
        	var urlid = url+id;
            return $http.delete(urlid)
            	.then(function(response){
            		return response.status;
            	});
        }				  
    }
    return usersnotesInterface;
}])