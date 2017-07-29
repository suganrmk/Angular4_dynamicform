function perfUtils(){};
var PerfWidgetCache = [];
perfUtils.getInstance = function(){
    var obj = PerfWidgetCache['perfIns'];
    if(!obj)
        obj = PerfWidgetCache['perfIns'] = new perfUtils();
    return obj;
};

perfUtils.prototype = {
    init: function(){
        
    },
    compareDate: function(){
    	if(new Date(moment.utc($('#startDt').val(), "DD-MMM-YYYY")).getTime() > new Date(moment.utc($('#endDt').val(), "DD-MMM-YYYY")).getTime()){
    		var errorMsg = '<p class="text-danger"></p>';
    		$('#startDt').parent().addClass('has-error');
        	$(errorMsg).html($('#startDt').attr('name')+' must be lesser than '+$('#endDt').attr('name')+'.').insertAfter($('#startDt'));
    	}
    },
    validateFutureDate: function(ids){
    	$.each(ids, function(i, id){
    		if(new Date(moment.utc($('#'+id).val(), "DD-MMM-YYYY")).getTime() > new Date().getTime()){
    			var errorMsg = '<p class="text-danger"></p>';
    			$('#'+id).parent().addClass('has-error');
    			$(errorMsg).html($('#'+id).attr('name')+' must be lesser than present date.').insertAfter($('#'+id));
    		}
    	});
    },
    resetForm: function(){
    	$('form .help-block').html('');
    	$('form').find(':input[name]').val('');
    	$('form').find('.inputfile').val('');
    },
    successMsg: function(msg){
    	$.alert({
		    title: 'Message:',
		    type: 'green',
		    columnClass: 'col-md-6 col-md-offset-3',
		    content: msg,
		    buttons: {
		    	Okay: {
    				btnClass: 'btn-success'
    	        }
		    }
		});
    },
    warningMsg: function(msg){
    	$.alert({
		    title: 'Message:',
		    type: 'Orange',
		    columnClass: 'col-md-6 col-md-offset-3',
		    content: msg,
		    buttons: {
		    	Okay: {
    				btnClass: 'btn-warning'
    	        }
		    }
		});
    },
    errorMsg: function(msg){
    	$.alert({
		    title: 'Error',
		    theme: 'black',
		    type: 'red',
		    columnClass: 'col-md-6 col-md-offset-3',
		    content: msg,
		    buttons: {
		    	Okay: {
    				btnClass: 'btn-danger'
    	        }
		    }
		});
    },
    confirmMsg: function(msg, url){
    	$.confirm({
    		title: 'Error',
    		content: msg,
    		theme: 'black',
    		type: 'red',
		    columnClass: 'col-md-6 col-md-offset-3',
    		buttons: {
    			Okay: {
    				btnClass: 'btn-danger',
    				action: function(){
    					$('.jconfirm.jconfirm-black').remove();
    					window.location.href = url;
    	            }
    	        }
    	    }
    	});
    },
    confirmPopMsg: function(msg){
    	var defer = $.Deferred();
    	$.confirm({
    		title: '',
    		content: msg,
    		theme: 'black',
    		type: 'red',
		    columnClass: 'col-md-6 col-md-offset-3',
    		buttons: {
    			Okay: {
    				btnClass: 'btn-success',
    				action: function(){
    					defer.resolve(true);	
    				}
    	        },
    	        Cancel: {
    				btnClass: 'btn-danger',
    				action: function(){
    					defer.resolve(false);	
    				}
    	        }
    	    }
    	});
    	return defer.promise();
    },
    redirectToStore: function(msg, url){
    	var defer = $.Deferred();
    	$.confirm({
    		title: '',
    		content: msg,
    		theme: 'black',
    		type: 'red',
		    columnClass: 'col-md-6 col-md-offset-3',
    		buttons: {
    			Update : {
    				btnClass: 'btn-success',
    				action: function(){
    					defer.resolve(true);
    					location.href = url;
    				}
    	        },
    	        Cancel: {
    				btnClass: 'btn-danger',
    				action: function(){
    					defer.resolve(false);	
    				}
    	        }
    	    }
    	});
    	return defer.promise();
    },
    getUrlVars: function(){
        var vars = [], hash;
        var hashes = window.location.href.slice(window.location.href.indexOf('?') + 1).split('&');
        for(var i = 0; i < hashes.length; i++) {
            hash = hashes[i].split('=');
            vars.push(hash[0]);
            vars[hash[0]] = hash[1];
        }
        return vars;
    },
    validatePageAccess: function(roles, pageKey){
    	if(!roles[pageKey]){
    		window.location.href = '#page_403'
    	}
    },
    browseFile: function(){
    	//file handler
    	$(document.body).on('change', ':file', function() {
    	    var input = $(this),
    	        numFiles = input.get(0).files ? input.get(0).files.length : 1,
    	        label = input.val().replace(/\\/g, '/').replace(/.*\//, '');
    	    input.trigger('fileselect', [numFiles, label]);
    	});
    	
    	$(':file').on('fileselect', function(event, numFiles, label) {
            var input = $(this).parents('.input-group').find(':text'),
                log = numFiles > 1 ? numFiles + ' files selected' : label;
            if( input.length ) {
                input.val(log);
            } else {
                if( log ) console.log(log);
            }
        });
    },
    getIndex: function(arrList, pk){
    	if(bowser.name === 'Chrome'){
    		var index = arrList.findIndex(function(item, i){
  	      	  return item.pk === pk;
  	      	});
    		return index;
    	} else {// if(bowser.name === 'Internet Explorer'){
    		var index = -1;
    		arrList.some(function(el, i) {
	    	    if (el.pk === pk) {
	    	        index = i;
	    	        return true;
	    	    }
	    	});
    		return index;
    	}
    },
    checkInactiveData: function($scope, arrList, serviceApi, dataId, $timeout){
    	var index = perfUtils.getInstance().getIndex(arrList, $scope.data[dataId]);
    	if(index == -1){
    		serviceApi.loadUserNameViewById($scope.data[dataId]).success(function(response) {
    			$scope.data[dataId] = '';
				$scope.isInactiveUser = response.entity.pk;
				arrList.push(response.entity);
    	        $timeout(function(){
    	        	$scope.data[dataId] = response.entity.pk;
    	        }, 0);
    	    });
    	}
    	$(document).on('hidden.bs.modal', 'div[role="dialog"]', function () {
	    	if($scope.isInactiveUser){
	    		var idx;
	            $.grep(arrList, function (element, index) {
	                if($scope.isInactiveUser === element.pk){
	                	idx = index;
	                }
	                return element.pk === $scope.isInactiveUser;
	            });
	            arrList.splice(idx, 1);
	            $scope.isInactiveUser = null;
	    	}
	    });
    }
};
