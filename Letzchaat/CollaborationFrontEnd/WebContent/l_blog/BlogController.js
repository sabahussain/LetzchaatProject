'use strict';

app.controller('BlogController', ['$scope','$location', 'BlogService', function($scope,$location, BlogService){
	var self=this;
	self.blog={
			blog_id:'',
			blogTitle:'',
			description:'',
			date_of_creation:'',
			username:'',
			status:'',
			reason:'',
			errorcode:'',
			errormessage:''
			
			};
	self.blogs=[];

	
	
	console.log('INSIDE BLOG CONTROLLER')
	
	self.submit=function(){
		
		console.log('Saving new blog',self.blog);
		self.createBlog(self.blog);
		
	}
	self.createBlog=function(blog){
		console.log('create blogs...',self.blog);
		BlogService.createBlog(blog)
		.then(
				function(d){
    				alert('Successfully created the blog')
    				$location.path("/");
    			},
				function(errResponse){
					console.error('Error while creating blog.....');
				}
				
		);
		
	};
	
	console.log('fetchallblogs')
	self.fetchAllBlogs=function(){
		console.log(' Inside FetchAllBlogs method in Blog Controller ')
		BlogService.fetchAllBlogs()
		.then(function(d){
			self.blogs=d;
			console.log('value in blogs',self.blogs)
		},
		
		function(errResponse){
			console.error('Error while fetching the data');
		}
		
		);
	};

	
self.updateBlog=function(blog_id){
	BlogService.updateBlog(blog_id) 
	.then(
		self.fetchAllBlogs,
		function(errResponse){
			console.error('Error while updating Blog');
		}
	);
	 };
	 self.edit=function edit(blog_id){
		 console.log('blogid to be edited',blog_id);
		 for(var i = 0; i < self.blogs.length; i++){
	            if(self.blogs[i].blog_id === blog_id) {
	                self.blog = angular.copy(self.blogs[i]);
	                break;
	            }
	            }
	 }
	 self.deleteBlog = function(blog_id){
			BlogService.deleteBlog(blog_id)
			.then(
					self.fetchAllBlogs,
					function(errResponse){
						console.error("Error while deleting Blog");
					});
		}; 
		
		 self.accept = function(blog_id) {
				console.log("accept...")
				BlogService.accept(blog_id)
						.then(
								function(d) {
									self.blog = d;
									self.fetchAllBlogs
									$location.path("/manage_blogs")
									console.log('Error Message is',self.blog.errormessage)
									alert(self.blog.errormessage)
									
									
								},
								
								function(errResponse) {
									console
											.error('Error while updating Blog.');
								});
			};
			
			self.reject = function(blog_id) {
				console.log("reject...")
				var reason = prompt("Please enter the reason");
				BlogService
						.reject(blog_id,reason)
						.then(
								function(d) {
									self.blog = d;
									self.fetchAllBlogs
									$location.path("/manage_blogs")
									alert(self.blog.errormessage)
									
								},
								null );
			};
		       		
		console.log('blogs value',self.blogs)
		
		//calling the method when it will be exceute
self.fetchAllBlogs();
		
		self.getSelectedBlog = getBlog
		function getBlog(id){
  		console.log("--.getting blog:"+id)
  		BlogService.getBlog(id)
  		.then(function(d){
  			//self.blog=d;
  			console.log('getSelectedBlog in BlogController',self.blog)
  			$location.path('/viewBlog');
  			
  		},
  		function(errResponse){
  			console.error("Error while fetching Blogs");
  		}
  	);
  		};

}]);


	
	
	
	
	
	
