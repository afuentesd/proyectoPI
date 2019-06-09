angular.module('notes', ['ngRoute'])
.config(function($routeProvider){
	$routeProvider
    	.when("/", {
    		controller: "listCtrl",
    		controllerAs: "listVM",
    		templateUrl: "notas.html",
    		resolve: {
    			// produce 500 miliseconds (0,5 seconds) of delay that should be enough to allow the server
    			//does any requested update before reading the orders.
    			// Extracted from script.js used as example on https://docs.angularjs.org/api/ngRoute/service/$route
    			delay: function($q, $timeout) {
    			var delay = $q.defer();
    			$timeout(delay.resolve, 500);
    			return delay.promise;
    			}
    		}
    	})
    	.when("/editUser", {
    		controller: "editUserCtrl",
    		controllerAs: "editUserVM",
    		templateUrl: "registro.html"
        })
        .when("/newNote", {
    		controller: "noteHandlerCtrl",
    		controllerAs: "noteHandlerVM",
    		templateUrl: "newnota.html"
        })
        .when("/editNote/:ID", {
        	controller: "noteHandlerCtrl",
    		controllerAs: "noteHandlerVM",
    		templateUrl: "newnota.html"
        })
        .when("/shareNote/:ID", {
        	controller: "noteShareCtrl",
    		controllerAs: "noteShareVM",
    		templateUrl: "compartir.html"
        })
        .when("/archivedNotes", {
        	controller: "listCtrl",
    		controllerAs: "listVM",
    		templateUrl: "notas.html",
        })
        .when("/searchNotes", {
        	controller: "searchCtrl",
    		controllerAs: "searchVM",
    		templateUrl: "buscar.html",
        })
      
        
    	
       
       
})