(function(window){
	//register the interceptor as a service
	mainApp.factory('perfInterceptor', ['$q', '$rootScope', function($q, $rootScope) {
	  var loadingCount = 0;
	  return {
	    'request': function(config) {
    		if(++loadingCount === 1)
	            $rootScope.$broadcast('loading:progress');	
	        config.headers = config.headers || {};
	        return config;
	    },
	    'requestError': function(rejection) {
	        return $q.reject(rejection);
	    },
	    'response': function(response) {
	        if(--loadingCount === 0)
	            $rootScope.$broadcast('loading:finish');
	        if(response.data.status === 500){
	        	var errorMsg = '<div class="error-page"><div class="error-content" style="margin-left: 0;">';
	        		errorMsg += '<h4><i class="fa fa-warning text-red"></i> Oops! Something went wrong.</h4>';
	        	    //errorMsg += '<p> We will work on fixing that right away. Please try after sometime.</p>';
	        	    errorMsg += '<p>Please report a ticket <a href="#/ticket">here</a>.</p>';
	        	    errorMsg += '</div></div>';
	        	perfUtils.getInstance().errorMsg(errorMsg);
	    		return $q.reject(response);
	        } else if(response.data.status === 409 || response.data.status === 412){
	    		perfUtils.getInstance().errorMsg(response.data.entity.errorMessage);
	    		return $q.reject(response);
	    	} else if(response.data.status === 403){
	    		var errorMsg = '<div class="error-page"><div class="error-content" style="margin-left: 0;">';
        		errorMsg += '<h4><i class="fa fa-warning text-red"></i>WARNING: Unauthorized access prohibited.</h4>';
        	    errorMsg += '<p>This feature is for the use of authorized users only.  Unauthorized or improper use '+ 
        	    	'of this system may result in administrative disciplinary action.</p>';
        	    errorMsg += '<p>If you believe this is an error, please report a ticket <a href="#/ticket">here</a>.</p>';
        	    errorMsg += '</div></div>';
        	    perfUtils.getInstance().errorMsg(errorMsg);
	    		return $q.reject(response);
	    	}
	        return response;
	    },
	    'responseError': function(rejection) {
	        if(--loadingCount === 0)
	           $rootScope.$broadcast('loading:finish');
	        if(rejection.status === 401) {
	        	var href = 'login';
	        	if(bowser.mobile || bowser.tablet){
	        		href = 'login.html';
	    	    }
	        	var errorMsg = '<div id="confirmError"><div class="error-content" style="margin-left: 0;">';
        		errorMsg += '<h4><i class="fa fa-warning text-red"></i>Invalid Session/ Session Timed Out.</h4>';
        	    errorMsg += '<p> Please Login <a href="'+href+'">here</a>.</p>';	                  
        	    errorMsg += '</div></div>';
        	    if($('#confirmError').length == 0)
        	    	perfUtils.getInstance().confirmMsg(errorMsg, 'login');
	        }
	        return $q.reject(rejection);
	    }
	  };
	}]);
	
	mainApp.config(['$httpProvider', function($httpProvider){
		$httpProvider.defaults.withCredentials = true;
		if (!$httpProvider.defaults.headers.get) {
		   $httpProvider.defaults.headers.common = {};
		}
		$httpProvider.defaults.headers.common["Cache-Control"] = "no-cache";
		$httpProvider.defaults.headers.common.Pragma = "no-cache";
		$httpProvider.defaults.headers.common["If-Modified-Since"] = "0";
	    $httpProvider.interceptors.push('perfInterceptor');
	}]);
	
	setBreadCrumb = function(ele){
		if(ele){
			if($('#breadcrumb li').length >= 5){
				$('#breadcrumb li:eq(1)').remove();
			}
			$('#breadcrumb').append('<li>'+ele+'</li>');
		}
	};
	
	/*Set the selected menu/sub-menu open on menu click*/
	$(document).on('click', '.sidebar-menu li a[href^="#/"]', function() {
		$('.sidebar-menu li.active').removeClass('active');
		$(this).parents().addClass('active');
	    window.location.href=$(this).attr('href');
	    var url = window.location.href;
	    url = url.split('#')[1];
	    var eleIndex = $('#breadcrumb li a[href="#'+url+'"]').length; 
	    if(eleIndex == 0){
	    	setBreadCrumb($('.sidebar li a[href="#'+url+'"]').parent().html());
	    } else {
	    	var index = $('#breadcrumb li a').index($('#breadcrumb li a[href="#'+url+'"]'));
	    	$('#breadcrumb li:gt('+index+')').remove();
	    }
	    if(bowser.mobile || bowser.tablet){
	    	$('body').removeClass('sidebar-open');
		}
	    return false;
	});
	
	/*Clear link next to the current page in breadcrumb*/
	$('#breadcrumb').on('click', 'li', function(){
		$('.sidebar-menu li.active').removeClass('active');
		$('.sidebar-menu li a[href="'+$(this).find('a').attr('href')+'"]').parents().addClass('active');
		var index = $('#breadcrumb li').index(this);
		$('#breadcrumb li:gt('+index+')').remove();
	});
	
	/*Set the selected menu/sub-menu open on refresh/reload*/
	$(document).ready(function(){
		$('.sidebar li.active').removeClass('active');
		var url = window.location.href;
		url = url.split('#')[1].split('?')[0];
		var eleIndex = $('#breadcrumb li a[href="#'+url+'"]').length;
	    if(eleIndex == 0){
			setBreadCrumb($('.sidebar li a[href="#'+url+'"]').parent().html());
	    }
	});
	
	/*
	 * Reset the form whenever its closed.
	 */
	$(document).on('hidden.bs.modal', 'div[role="dialog"]', function () {
		var formId=$(this).attr('id');
		$('#'+formId+' .help-block').empty();
		$('#'+formId+' p.text-danger').remove();
		$('#'+formId+' .has-error').removeClass('has-error');
		if(typeof scope !== 'undefined')
			scope.data = {};
	});
	
	$('body').on('click', '.filestyle', function(){
    	perfUtils.getInstance().browseFile();
    });
	
}(window));