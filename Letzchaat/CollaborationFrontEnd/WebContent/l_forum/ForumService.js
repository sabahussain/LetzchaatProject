'use strict';


app.factory('ForumService',['$http', '$q', '$rootScope', function($http, $q, $rootScope){
	
	var REST_SERVICE_URI = 'http://localhost:8080/CollaborationPortalBackend';
	return{
		
		fetchAllForums:function(){
			console.log('fetchallforms method in forumservice!');
			return $http.get(REST_SERVICE_URI+'/getallforums/')
			.then(
					function(response){
						console.log("success in forumservice",response.data);
						return response.data;
						},
						function(errResponse){
							console.error("error while fetching forum");
							return $q.reject(errRespinse);
						}
					);
		},
		createForum:function(forum){
			console.log('createForum in ForumService');
			return $http.post(REST_SERVICE_URI+'/createforum/',forum)
			.then(
					function(response){
						return response.data;
					},
					function(errResponse){
						console.log("Error while creating forum");
						return $q.reject(errResponse);
					}
					);
		},
		
		getForum:function(forum_id){
			return $http.get(REST_SERVICE_URI+'/getforum/'+forum_id)
			.then(
					function(response){
						$rootScope.selectedforum=response.data;
						return response.data;
					},
					function(errResponse){
						console.log("Error while fetching forum");
						return $q.reject(errResponse);
					});
		},
		
		deleteForum:function(forum_id){
			return $http.del(REST_SERVICE_URI+'/deleteforum/'+forum_id)
			.then(
					function(response){
						return response.data;
					},
					function(errResponse){
						console.log('Error while deleting the forum');
						return $q.reject(errResponse);
					});
		},
		
		updateForum:function(forum_id){
			return $http.put(REST_SERVICE_URI+'/updateforum/'+forum_id)
			.then(
					function(response){
						return response.data;
					},
					function(errResponse){
						console.log('Error while updating the forum');
						return $q.reject(errResponse);
					});
		},
		
		createForumComment:function(forumcomment,forum_id){
			return $http.post(REST_SERVICE_URI+'/createforumcomment/'+forum_id,forumcomment)
			.then(
					function(response){
						return response.data;
					},
					function(errResponse){
						console.log("Error while creating the FOrum")
					});
		},
		
		updateForumComment:function(forum_id){
			 return $http.put(REST_SERVICE_URI+'/updateforumcomment/'+forum_id)
			 .then(function(response){
				 return response.data;
			 },
			 function(errResponse){
				 console.error('Error while updating the forum comment');
				 return $q.reject(errResponse);
			 }
					 
			 );
		 
		 },
		 
		 deleteForumComment:function(forum_id){
			 return $http.del(REST_SERVICE_URI+'/deleteforumcomment/'+forum_id)
			 .then(function(response){
				return response.data; 
			 },
			 function(errResponse){
				 console.error('Error while deleting the forumcomment');
				 return $q.reject(errResponse);
			 }
			 
			 );
		 },
		 
		 
		 
		 fetchAllForumsCommentNew:function(forum_id){
			 console.log('fetchAllforums comment Method in forumServices')
			 return $http.get(REST_SERVICE_URI+'/getallforumcommentsnew/'+forum_id)
			 .then(
			function(response){
				console.log('success in forumService',response.data)
				$rootScope.fetchComments1=response.data;
				return response.data;
			},
			function(errResponse){
				console.error('Error while fetching the forumsComment');
				return $q.reject(errResponse);
			}
			 );
			 
		 },
		 
		 fetchAllForumsComment:function(){
			 console.log('fetchAllforums comment Method in forumServices')
			 return $http.get(REST_SERVICE_URI+'/getallforumcomments/')
			 .then(
			function(response){
				console.log('success in forumService',response.data)
				$rootScope.fetchComments=response.data;
				return response.data;
			},
			function(errResponse){
				console.error('Error while fetching the forumsComment');
				return $q.reject(errResponse);
			}
			 );
			 
		 },
		 
		 getForumComment:function(forum_id){
			 return $http.get(REST_SERVICE_URI+'/getforumcomment/'+forum_id)
			 .then(function(response){
				 
				 $rootScope.selectedForumComment=response.data;
				 return response.data;
			 },
			 function(errResponse){
				 console.error('Error while getting forumcomment');
				 return $q.reject(errResponse);
			 }
			 
			 );
		 }
		 
	}
	
}]);