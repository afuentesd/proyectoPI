angular.module('notes')
.factory("notesFactory", ['$http',function($http){
	var url = 'https://localhost:8443/notes/rest/notes/';
    var notesInterface = {
    	getNotes: function(){
    		return $http.get(url)
    			.then(function(response){
    				return response.data;
    			});
    	},
    	getNote : function(id){
    		var urlid = url + id;
            return $http.get(urlid)
            	.then(function(response){
            		return response.data;
         		});
    	},
    	getNotesArchived : function(){
    		var urlid = url + 'archived';
            return $http.get(urlid)
            	.then(function(response){
            		return response.data;
         		});
    	},
    	getNotesSearch : function(search){
    		var urlid = url +'search/'+search;
            return $http.get(urlid)
            	.then(function(response){
            		return response.data;
         		});
    	},
    	putNote : function(note){
    		var urlid = url+note.idn;
            return $http.put(urlid, note)
            	.then(function(response){
      				 return response.status;
  				});                   
    	},
    	postNote:  function(note, usernote){    		
    		return $http.post(url,note)
            	.then(function(response){
            		return response.status;
     			});
    	}, 
        deleteNote : function(id){
        	var urlid = url+id;
            return $http.delete(urlid)
            	.then(function(response){
            		return response.status;
            	});
        }				  
    }
    return notesInterface;
}])