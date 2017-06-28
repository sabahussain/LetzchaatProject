'use strict'
app.controller('JobController',['$scope','$location','$rootScope','JobService' , function($scope,$location,$rootScope,JobService){
                 var self=this;
			self.job={
					jobid:'',
					job_title:'',
					qualification:'',
					status:'',
					description:'',
					date_time:'',
					errorcode:'',
					errormessage:''
			};
			
			self.jobs=[];
			
			console.log('Inside Job Controller');
			
			self.submit=function(){
				console.log('Saving new job',self.job);
				self.postJob(self.job);
				}
			self.postJob=function(job){
				console.log("Post Jobs..."+self.job);
				JobService.postJob(job)
				.then(
						function(d){
							alert('Successfully Posted the JOB...')
						},
						function(errResponse){
							
							console.error('Error while Posting the JOB');
						});
			};
			
			self.fetch1=function(){
				self.fetchAllJobs();
			}
			
			console.log('Fetch All Jobs...');
			self.fetchAllJobs=function(){
				console.log('Inside FetchALLjobs in job controller');
				JobService.fetchAllJobs()
				.then(
						function(d){
							self.jobs=d;
							console.log('value in jobs',self.jobs);
						},
						function(errResponse){
							console.error('Error while fetching the data');
						}
				);
			};
			
			console.log('getAllAppliedJobs');
			self.getAllAppliedJobs=function(){
				console.log("inside getALLAPPLIEDJOBS in Job Controller");
				JobService.getAllAppliedJobs()
				.then(
						function(d){
							self.jobs=d;
							console.log('value in GetAllAppliedJobs',self.jobs);
							
						},
						function(errResponse){
							console.log('error while fetching all jobs...');
						}
				);
			};
                         
              self.getJobDetails=function(jobid){
            	  console.log('inside getalljobdetails in jobcontroller');
            	  JobService.getJobDetails(jobid)
            	  .then(
            			  function(d){
            				  self.jobs=d;
            				  $location.path('/viewJobdetails')
            				  console.log('value in Jobs',self.jobs);
            			  },
            			  function(errResponse){
            				  console.error('Error while fetching the data');
            			  }
            	  );
              };
              
            self.applyForJob=function(jobid){
            	console.log('inside applyForJob method in jobcontroller',jobid);
            	if(!$rootScope.loggedIn)
            		{
            			alert('please log in to apply for Job');
            			console.log('user not logged in! cannot apply for job');
            			$location.path('/login')
            			return
            		}
            	console.log($rootScope.loggedIn,'applying for a job',jobid);
            	JobService.applyForJob(jobid)
            	.then(
            			function(d){
            				self.job=d;
            				console.log('value in job',self.job)
            				if(self.job.errorcode=='404'){
            					alert('You have already applied for this job!',self.job.errormessage)
            					$location.path('/home')
            				}
            				else{
            					alert('you have successfully applied for this Job',self.job.errormessage)
            					$location.path('/home')
            				}
            			},
            	function(errResponse){
            				console.error('Error while applying for this Job!')
            			}		
            	);
            	
            }; 
             
            self.appliedJobs=function(){
            	self.getMyAppliedJobs();
            }
            
            self.getMyAppliedJobs=function(){
            	console.log('Inside getmyappliedjobs in job controller')
            	JobService.getMyAppliedJobs()
            	.then(
            			function(d){
            				self.jobs=d;
            				console.log('value in Jobs',self.jobs)
            			},
            			function(errResponse){
            				console.error("Error while fetching MyAppliedJobs")
            			}
            			);
            };
            
            self.rejectJob=function(username,jobid){
            	JobService.rejectJob(username,jobid)
            	.then(
            			function(d){
            				self.jobs=d;
            				alert('You have successfully rejected the job application');
            				console.log('You have successfully rejected the job application',self.jobs)
            				$location.path('/manage_applied_jobs')
            			},
            			
            			function(errResponse){
            				console.error('Error while rejecting the job application');
            			}
            			);
            };
            
            self.callForInterview=function(username,jobid){
            	JobService.callForInterview(username,jobid)
            	.then(
            			function(d){
            				self.jobs=d;
            				alert('Application status changed as call for interview');
            				console.log('Application status changed as call for interview',self.jobs)
            				$location.path('/manage_applied_jobs')

            			},
            			function(errResponse)
            			{
            				console.error('Error while changing the status as call for interview in job application')
            			}
            			
            	);
            };
            
            self.selectUser=function(username,jobid){
            	JobService.selectUser(username,jobid)
        		.then(function(d){
        			
        			self.jobs=d;
        			alert('Application status changed as Selected');
        			console.log('Application status changed as Selected',self.jobs)
        			$location.path('/manage_applied_jobs')
        		},
        		function(errResponse)
        		{
        			console.error('Error while changing the status as Selected in job application')
        		}
        				
        		);
            };
            
            self.job={
					jobid:'',
					job_title:'',
					qualification:'',
					status:'',
					description:'',
					date_time:'',
					errorcode:'',
					errormessage:''
			};
            


            
self.getSelectedJob = getJob
	function getJob(id){
		console.log("--.getting job:"+id)
		JobService.getJob(id)
		.then(function(d){
			console.log('getSelectedjob in jobController',self.job)
			$location.path('/viewJobdetails');
			
		},
		function(errResponse){
			console.error("Error while fetching jobs");
		}
	);
		};
	          
            
}]);