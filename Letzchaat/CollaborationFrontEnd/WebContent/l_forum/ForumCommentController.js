'use strict';

app.controller('ForumCommentController', ['$scope','$location', '$rootScope','ForumService', function($scope,$location,$rootScope, ForumService){
	var self=this;
	self.forumcomment={
			id:'',
			forum_id:'',
			forum_comment:'',
			username:'',
			comment_date:'',
			errorCode:'',
			errorMessage:''
		};
	
	self.forumcomments=[];
	
console.log('INSIDE forum COMMENT CONTROLLER')
	self.submit=function(forum_id){
	
	self.forumcomment.forum_id=forum_id;
	console.log('forumid vlaue:',self.forumcomment.forum_id)
		
		console.log('Saving new forum comment',self.forumcomment);
		self.createForumComment(self.forumcomment,forum_id);
	}
	self.createForumComment=function(forumcomment,forum_id){
		
		console.log(' Inside creatforumComment method in forumComment Controller ')
		if(!$rootScope.loggedIn)
			{
			alert('Please login to post comments');
			console.log(' User not loggedin,cannot post comments')
			$location.path('/login')
			return
			
			}	
		console.log('create forums comment...',self.forumcomment);
		ForumService.createForumComment(forumcomment,forum_id)
		.then(
				function(d){
    				alert('Successfully created the forum comment')
    				$location.path("/");
    			},
				function(errResponse){
					console.error('Error while creating forum comment.....');
				}
				
		);
		
	};
	
	console.log('fetchallforumscomment')
	self.fetchAllForumsCommentNew=function(forum_id){
		console.log(' Inside FetchAllforums Comment method in forumComment Controller ')
		ForumService.fetchAllForumsCommentNew(forum_id)
		.then(function(d){
			self.forumcomments=d;
			
			console.log('value in forumcomments',self.forumcomments)
			
		},
		
		function(errResponse){
			console.error('Error while fetching the data');
		}
		
		);
	};
	
	console.log('fetchallforumscomment')
	self.fetchAllForumsComment=function(){
		console.log(' Inside FetchAllforums Comment method in forumComment Controller ')
		ForumService.fetchAllForumsComment()
		.then(function(d){
			self.forumcomments=d;
			
			console.log('value in forumcomments',self.forumcomments)
			
		},
		
		function(errResponse){
			console.error('Error while fetching the data');
		}
		
		);
	};
	
	 self.reset=function(){
      	  console.log('resetting the form',self.forumcomment);
      	  self.forumcomment={
      			id:'',
    			forum_id:'',
    			forum_comment:'',
    			username:'',
    			comment_date:'',
    			errorCode:'',
    			errorMessage:''
      			  
      	  };
      	  $scope.myForm.$setPristine();//reset form
        };
        
        self.updateForumComment=function(forum_id){
        	ForumService.updateForumComment(forum_id)
        	.then(
        		self.fetchAllForumsComment,
        		function(errResponse){
        			console.error('Error while updating forum comment');
        		}
        	);
        	 };
        	 self.edit=function(forum_id){
        		 console.log('forumid to be edited',forum_id);
        		 for(var i = 0; i < self.forumcomments.length; i++){
       	            if(self.forumcomments[i].forum_id === forum_id) {
       	                self.forumcomment = angular.copy(self.forumcomments[i]);
       	                break;
       	            }
       	            }
        	 }
        	 self.deleteForumComment = function(forum_id){
       			ForumService.deleteForumComment(forum_id)
       			.then(
       					self.fetchAllForumsComment,
       					function(errResponse){
       						console.error("Error while deleting forum Comment");
       					});
       		};
       		console.log('forums Comment value',self.forumcomments)
       		
       		//calling the method when it will be exceute
	
       		
       		//self.fetchAllForumsCommentNew(selectedforum.forum_id);
       		
      	//self.fetchAllForumsComment();
       		//self.getSelectedForumComment();
       		
       		self.getSelectedForumComment = getForumComment
       		function getForumComment(id){
          		console.log("--.getting forum comment:"+id)
          		ForumService.getForumComment(id)
          		.then(function(d){
          			//self.forum=d;
          			console.log('getSelectedforum Comment in forumCommentController',self.forumcomment)
          			$location.path('/viewforum');
          			
          		},
          		function(errResponse){
          			console.error("Error while fetching forumsComment");
          		}
          	);
          		};
          		
          		
	
}]);