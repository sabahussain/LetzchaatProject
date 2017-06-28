'use strict'


app.factory('BlogService', ['$http', '$q','$rootScope', function($http, $q,$rootScope){
 
    var REST_SERVICE_URI = 'http://localhost:8080/CollaborationPortalBackend';
 return{
	 
	 fetchAllBlogs:function(){
	 console.log('fetchAllBlogs Method in BlogServices')
	 return $http.get(REST_SERVICE_URI+'/getAllblogs/')
	 .then(
	function(response){
		console.log('success in BlogService',response.data)
		return response.data;
	},
	function(errResponse){
		console.error('Error while fetching the Blogs');
		return $q.reject(errResponse);
	}
	 );
	 
 },
 
 createBlog:function(blog){
	 console.log('Blog service')
	 return $http.post(REST_SERVICE_URI+'/createblog/',blog)
	 .then(function(response){
		 return response.data;
		 
	 },
	 function(errResponse){
		 console.error('Error while creating the blog');
		 return $q.reject(errResponse);
		 
	 }
			 
	 );
 },
 getBlog:function(id){
	 return $http.get(REST_SERVICE_URI+'/getblog/'+id)
	 .then(function(response){
		 
		 $rootScope.selectedBlog=response.data;
		 return response.data;
	 },
	 function(errResponse){
		 console.error('Error while getting blog');
		 return $q.reject(errResponse);
	 }
	 
	 );
 },
 deleteBlog:function(id){
	 return $http.del(REST_SERVICE_URI+'/deleteblog/'+id)
	 .then(function(response){
		return response.data; 
	 },
	 function(errResponse){
		 console.error('Error while deleting the blog');
		 return $q.reject(errResponse);
	 }
	 
	 );
 },
 updateBlog:function(blog_id){
	 return $http.put(REST_SERVICE_URI+'/updateblog/'+blog_id)
	 .then(function(response){
		 return response.data;
	 },
	 function(errResponse){
		 console.error('Error while updating the blog');
		 return $q.reject(errResponse);
	 }
			 
	 );
 },
 
 createBlogComment:function(blogcomment,blog_id){
	 console.log('Blog Comment service')
	 return $http.post(REST_SERVICE_URI+'/createblogcomment/'+blog_id,blogcomment)
	 .then(function(response){
		 return response.data;
		 
	 },
	 function(errResponse){
		 console.error('Error while creating the blogcomment');
		 return $q.reject(errResponse);
		 
	 }
			 
	 );
	 
	 
 },
 updateBlogComment:function(blog_id){
	 return $http.put(REST_SERVICE_URI+'/updateblogcomment/'+blog_id)
	 .then(function(response){
		 return response.data;
	 },
	 function(errResponse){
		 console.error('Error while updating the blog comment');
		 return $q.reject(errResponse);
	 }
			 
	 );
 
 },
 deleteBlogComment:function(id){
	 return $http.del(REST_SERVICE_URI+'/deleteblogcomment/'+id)
	 .then(function(response){
		return response.data; 
	 },
	 function(errResponse){
		 console.error('Error while deleting the blogcomment');
		 return $q.reject(errResponse);
	 }
	 
	 );
 },
 fetchAllBlogsComment:function(){
	 console.log('fetchAllBlogs comment Method in BlogServices')
	 return $http.get(REST_SERVICE_URI+'/getblogcomments/')
	 .then(
	function(response){
		console.log('success in BlogService',response.data)
		$rootScope.fetchComments=response.data;
		return response.data;
	},
	function(errResponse){
		console.error('Error while fetching the BlogsComment');
		return $q.reject(errResponse);
	}
	 );
	 
 },
 
 fetchAllBlogsCommentNew:function(blog_id){
	 console.log('fetchAllBlogs comment Method in BlogServices')
	 return $http.get(REST_SERVICE_URI+'/getblogcommentsnew/'+blog_id)
	 .then(
	function(response){
		console.log('success in BlogService',response.data)
		$rootScope.fetchComments1=response.data;
		return response.data;
	},
	function(errResponse){
		console.error('Error while fetching the BlogsCommentnew');
		return $q.reject(errResponse);
	}
	 );
	 
 },
 accept: function(blog_id) {
  	console.log("calling approve ")
          return $http.get(REST_SERVICE_URI+'/acceptb/'+blog_id)
                  .then(
                          function(response){
                              return response.data;
                          }, 
                          function(errResponse){
                              console.error('Error while accept blog');
                             
                          }
                  );
  },
  reject: function(blog_id, reason) {
  	console.log("calling reject ")
          return $http.get(REST_SERVICE_URI+'/rejectb/'+blog_id+'/'+reason)
                  .then(
                          function(response){
                              return response.data;
                          }, 
                          function(errResponse){
                              console.error('Error while reject blog');
                              return $q.reject(errResponse);
                          }
                  );
  },
 getBlogComment:function(id){
	 return $http.get(REST_SERVICE_URI+'/getblogcomment/'+id)
	 .then(function(response){
		 
		 $rootScope.selectedBlogComment=response.data;
		 return response.data;
	 },
	 function(errResponse){
		 console.error('Error while getting blogcomment');
		 return $q.reject(errResponse);
	 }
	 
	 );
 }
 
 
 
 }
 }]);