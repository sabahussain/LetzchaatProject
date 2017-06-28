var app=angular.module('myApp',['ngRoute','ui.bootstrap','ngCookies']);


app.config(function($routeProvider){
	console.log("APP.js ");
	
$routeProvider

.when('/',{
	templateUrl : 'l_common/body.html',
	
	
})
.when('/aboutus',{
	templateUrl : 'l_common/aboutus.html',
	})
	
.when('/contactus',{
templateUrl : 'l_common/contactus.html',
})
	
.when('/home',{
templateUrl : 'l_common/body.html',
controller : 'UserController'
})

.when('/collapse1',{
templateUrl : 'l_home/home.html',

})

.when('/collapse2',{
templateUrl : 'l_home/home.html',

})

/*
 * 
 * Admin Mapping*/

.when('/manage_users',{
templateUrl : 'l_admin/manage_users.html',
controller : 'UserController'
})
.when('/manage_blogs',{
templateUrl : 'l_admin/manage_blog.html',
controller : 'BlogController'
})
.when('/admin_menu',{
templateUrl : 'l_admin/admin_menu.html',

})

.when('/manage_applied_jobs',{
templateUrl : 'l_admin/manage_applied_jobs.html',
controller : 'JobController'
})

/*
 * 
 * User Mapping*/


.when('/login',{
templateUrl : 'l_user/login.html',
controller : 'UserController'
})

.when('/logout',{
templateUrl : '/',
controller : 'UserController'
})

.when('/registration',{
templateUrl : 'l_user/registration.html',
controller : 'UserController'
})

.when('/myprofile',{
templateUrl : 'l_user/myprofile.html',
controller : 'UserController'
})


/*
 * Blog Mapping
 * */

.when('/createBlog',{
templateUrl : 'l_blog/createBlog.html',
controller : 'BlogController'
})

.when('/listBlog',{
templateUrl : 'l_blog/listBlog.html',
controller : 'BlogController'
})
.when('/viewBlog',{
templateUrl : 'l_blog/viewBlog.html',
controller : 'BlogController'
})
.when('/blogcomment',{
	templateUrl : 'l_blog/blogcomment.html',
	})




/*
 * Forum Mapping
 * */

.when('/createforum',{
templateUrl : 'l_forum/createforum.html',
controller : 'ForumController'
})

.when('/viewforum',{
templateUrl : 'l_forum/viewforum.html',
controller : 'ForumController'
})
.when('/listforum',{
templateUrl : 'l_forum/listforum.html',
controller : 'ForumController'
})
.when('/forumcomment',{
	templateUrl : 'l_forum/forumcomment.html',
	})

/*
 * 
 * Friend Mapping*/
.when('/search_friend',{
templateUrl : 'l_friend/search_friend.html',
controller : 'UserController'
})
.when('/friend_list',{
templateUrl : 'l_friend/friend_list.html',
controller : 'FriendController'
})
.when('/pending_request',{
templateUrl : 'l_friend/pending_request.html',
controller : 'FriendController'
})
.when('/friend_details',{
templateUrl : 'l_friend/friend_details.html',
controller : 'FriendController'
})

/*
 * 
 * jOB Mapping*/

.when('/post_job',{
templateUrl : 'l_job/post_job.html',
controller : 'JobController'
})
.when('/view_applied_job',{
templateUrl : 'l_job/view_applied_job.html',
controller : 'JobController'
})
.when('/viewJobdetails',{
templateUrl : 'l_job/view_jobdetails.html',
controller : 'JobController'
})
.when('/search_job',{
templateUrl : 'l_job/search_job.html',
controller : 'JobController'
})

/*
 * 
 * Chat Mapping*/

.when('/chat',{
templateUrl : 'l_chat/Chat.html',

})


.otherwise({redirectTo: '/'});
});

app.controller('CarouselDemoCtrl', ['$scope',function($scope) {
	 
	  $scope.myInterval = 10000;
	  $scope.slides = [
	     { image1: 'lib/image/banner1.jpg' },
	    { image1: 'lib/image/banner2.jpg'},
	    { image1: 'lib/image/banner3.jpg' },
	    { image1: 'lib/image/banner4.jpg' },
	    
	  ];
}]);

app.controller('ImageDemoCtrl', ['$scope',function($scope) {
	 
	  $scope.myInterval = 10000;
	  $scope.slides = [
	     { image1: 'lib/image/img1.jpg' },
	    { image1: 'lib/image/img2.jpg'},
	    { image1: 'lib/image/img3.jpg' }
	  
	    
	  ];
	  

}]);




app.run( function ($rootScope, $location,$cookieStore, $http) {

	//$on is monitoring
	 $rootScope.$on('$locationChangeStart', function (event, next, current) {
		 console.log("$locationChangeStart")
		 //http://localhost:8080/Collaboration/addjob
	        // redirect to login page if not logged in and trying to access a restricted page
		 //$.inArray(x,A)==1 =>it will return 1(true) or -1(false).If the x is there in array A it will return true otherwise false.
		 //Without Login they can access these pages.
		 //If the location.path is not equal to /search_job or '/view_blog' then this is restricted page.
		 
		 
		 var restrictedPages=['','/','/x','/y','/search_job','/admin_menu','/chat','/viewJobdetails','/view_applied_job','/viewBlog','/login','/logout','/myprofile', '/registration','/listBlog','/createBlog','/create_forum','/view_forum','/list_forum','/search_friend','/pending_request','/friend_details','/friend_list','/home','/collapse1','/collapse2'];
		 var userRestrictedPages=['/manage_users','/manage_blogs','/manage_applied_jobs','/post_job'];
		 var currentPage=$location.path();
		var isRestrictedPage=$.inArray(currentPage,restrictedPages)==1;
		var isUserRestrictedPage=$.inArray(currentPage,userRestrictedPages)==1;
		
		 
		 console.log('userRestrictedPages=',userRestrictedPages);
		 console.log('isUserRestrictedPages=',isUserRestrictedPage);
		 console.log('isRestrictedPages=',isRestrictedPage);
		 
		 
		 
		 
		 console.log("Navigating to page :" + $location.path())
	        console.log("restrictedPage:" +restrictedPages)
	        console.log("currentUser:" +$rootScope.currentUser.username)
	        var loggedIn = $rootScope.currentUser.username;
		 $rootScope.loggedIn=loggedIn;
		/* var loggedInRole=$rootScope.currentUser.role;
		 if(loggedInRole=='admin')
			 {$rootScope.loggedInRole=loggedInRole;}*/
		 
		 console.log('value of loggedin',$rootScope.loggedIn)
		 
		 console.log('Value of role in loggedIn',$rootScope.currentUser.role)
	        
	        console.log("loggedIn:" +loggedIn)
	        
	        if(!loggedIn)
	        	{
	        	
	        	 if (isRestrictedPage) {
	        		// alert("You cannot access these pages because you have not logged in");
	        			$location.path("/");
		                }
	        	}
	        
			 else //logged in
	        	{
	        	
				 var role = $rootScope.currentUser.role;
				 $rootScope.role=role;
				 console.log('role=',role)
				 if((role!="Admin")&& isUserRestrictedPage)
					{
					alert("You are not admin...You cannot do these operations....");
					$location.path("/");	
					}
				 /*var userRestrictedPage = $.inArray($location.path(), ["/post_job"]) == 0;
				 if(userRestrictedPage && role!='admin' )
					 {
					  alert("You can not do this operation as you are logged as : " + role )
					   $location.path('/login');
					 }*/
				     
	        	
	        	}
	        
	 }
 );
	 
	 
 $rootScope.currentUser = $cookieStore.get('currentUser') || {};
 if ($rootScope.currentUser) {
     $http.defaults.headers.common['Authorization'] = 'Basic ' + $rootScope.currentUser; 
 }

});

