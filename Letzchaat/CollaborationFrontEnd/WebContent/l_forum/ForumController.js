'use strict';

app.controller('ForumController',['$scope','$location','ForumService', function($scope,$location,ForumService){
        var self=this;

			self.forum={
					forum_id:'',
					forum_topic:'',
					date_created:'',
					forum_question:'',
					username:'',
					errormessage:'',
					errorcode:''
						
			};
			self.forums=[];
			
			console.log('Inside Forum Controller');
			self.submit=function(){
				console.log("saving new forum",self.forum);
				self.createForum(self.forum);
			}
			self.createForum=function(forum){
				console.log("creating forum...",self.forum);
				ForumService.createForum(forum)
				.then(
				function(d){
					alert('Forum created Successfully!')
					$location.path('/')
				},
				function(errResponse){
					console.error("Error while creating the forum...");
				}
				);
			};
			
			console.log("fetchAllForums");
			self.fetchAllForums=function(){
				console.log('Inside FetchALLForums in ForumCOntroller!');
				ForumService.fetchAllForums()
				.then(
						function(d){
							self.forums=d;
							console.log("Value in Forums"+self.forums);
						},
						function(errResponse){
							console.log("Error while fetching the forums...")
						}
				);
			};
			self.fetchAllForums();
			
			self.reset=function(){
				console.log('resetting the Forum',self.forum);
				self.forum={
						forum_id:'',
						forum_topic:'',
						date_created:'',
						forum_question:'',
						username:'',
						errormessage:'',
						errorcode:''
						
				};
				
				$scope.myForm.$setPristine();//reset form
				
			};
			
			self.updateForum=function(forum_id){
				ForumService.updateForum(forum_id)
				.then(
						self.fetchAllForums(),
						function(errResponse){
							console.log("Error while updating Forum...")
							
						});
				
			};
			
			self.editForum=function(forum_id){
				console.log('inside forumedit function in FORUMCONTROLLER',forum_id);
				for(var i=0; i < self.forums.lenth; i++){
					if(self.forums[i].forum_id==forum_id){
						self.forum=angular.copy(self.forums[i]);
						break;
					}
				}
			}
			self.deleteForum=function(forum_id){
				ForumService.deleteForum(forum_id)
				.then(
						self.fetchAllForums,
						function(errResponse){
							console.log("error while deleting Forum...")
						}
						);
			};
			
			console.log('forums value',self.forums)
       		self.getSelectedForum = getForum 
       		function getForum(forum_id){
          		console.log("--.getting forum:"+forum_id)
          		ForumService.getForum(forum_id)
          		.then(function(d){
          			self.forums=d;
          			console.log(' in forumController',self.forums)
          			$location.path('/viewforum');
          			
          		},
          		function(errResponse){
          			console.error("Error while fetching forums");
          		}
          	);
          		};
                                  
}]);