'use strict';
 
app.controller('FriendController', ['$scope', 'FriendService','$rootScope','$location','$cookieStore','$http', function($scope, FriendService,$rootScope,$location,$cookieStore,$http) {
    var self = this;
    self.friend={
    		id:'',
    		username:'',
    		friend_name:'',
    		friend_request:'',
    		is_online:'',
    		errorcode:'',
    		errormessage:''
    		
    
    
    };
    self.friends=[];
    
    console.log('INSIDE friend CONTROLLER')
    
    self.fetch2=function(){
		self.fetchAllPendingFriends();
	}
    
    self.fetchAllPendingFriends = function() {
		console.log("fetchAllPendingFriends...")
		FriendService.fetchAllPendingFriends()
				.then(
						function(d) {
							self.friends = d;
							console.log('fetchAllPendingFriends details iin FriendController',self.friends)
						},
						function(errResponse) {
							console.error('Error while fetchAllPendingFriends friends');
						}
						);
	};
	
	self.fetch1=function(){
		self.fetchAllAcceptedFriends();
	}
	
	 self.fetchAllAcceptedFriends = function() {
			console.log("fetchAllAcceptedFriends...")
			FriendService.fetchAllAcceptedFriends()
					.then(
							function(d) {
								self.friends = d;
								console.log('fetchAllAcceptedFriends details in FriendController.js',self.friends)
								
								
							},
							function(errResponse) {
								console.error('Error while fetchAllAcceptedFriends friends');
								
							});
		};
		
		self.myProfile = function(friend_name) {
			console.log("myProfile...")
			FriendService.myProfile(friend_name)
					.then(
							function(d) {
								console.log('Friendname',friend_name)
								self.friends = d;
								$location.path("/friend_details")
							},
							function(errResponse) {
								console.error('Error while fetch profile.');
								console.log('Friendname',friend_name)
							}
							);
		};
		
		self.accept = function(username) {
			console.log("accept friend request in Friend Controller...")
			FriendService.accept(username)
					.then(
							function(d) {
								self.friend = d;
								alert(self.friend.errormessage)
								
							},
							
							function(errResponse) {
								console.error('Error while accepting friend request.');
							});
		};
		
		self.reject = function( friendname) {
			console.log("reject friend request in Friend Controller......")
			
			FriendService.reject(friendname)
					.then(
							function(d) {
								self.friend = d;
								self.fetchAllPendingFriends
								alert(self.friend.errormessage)
								$location.path('/home') //?
								
							},
							function(errResponse) {
								console.error('Error while rejecting friend request.');
							});
		};

		
		
		self.reset = function() {
			self.friend = {
					id:'',
		    		username:'',
		    		friend_name:'',
		    		friend_request:'',
		    		is_online:'',
		    		errorcode:'',
		    		errormessage:''
			}
		};
	    	
     
}]); 