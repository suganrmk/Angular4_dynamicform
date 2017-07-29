(function(angular) {
	var AbstractController = function($controller, vm, commonAPIservice, $compile, DTOptionsBuilder, DTColumnBuilder, $q){
		var _this = this; _this.vm = vm;  _this.service = commonAPIservice;
		vm.scope.add = function(){
			_this.add();
	    };
	    vm.scope.save = function(){
	        _this.save();
	    };
	    vm.scope.update = function(){
	        _this.update();
	    };
	    vm.scope.del = function(){
	        _this.del(vm.deleteUrl);
	    };
	    
	    vm.scope.title = vm.title;
	    vm.scope.isAdd = vm.isAdd;
	   
	    var paramObj = {
            "vm" : vm.scope,
            "compile" : $compile,
            '$q': $q,
            "DtOptionsBuilder" : DTOptionsBuilder,
            "DTColumnBuilder" : DTColumnBuilder,
            "service" : commonAPIservice,
            'loadListUrl' : vm.loadListUrl,
            'editFormId' : vm.formId,
            'deleteFormId' : 'deleteRecord'
        };
	    if(vm.deleteRow === undefined)
	    	paramObj['deleteRow'] = false;
        perfDatatable.loadTable.init(paramObj);
	};
	
	AbstractController.prototype.add = function(){
		this.vm.scope.data = {};
		perfUtils.getInstance().resetForm();
		$('#'+this.vm.formId).modal();
	};
	
	AbstractController.prototype.save = function(){
		var _this = this;
		this.service.add(_this.vm.addUrl, _this.vm.scope.data).success(function (response) {
    		perfUtils.getInstance().successMsg(_this.vm.title+' Saved Successfully!');
    		_this.vm.scope.dtInstance.reloadData();
    		$('#'+_this.vm.formId).modal('hide');
        });
	};
	
	AbstractController.prototype.update = function(){
		var _this = this;
		var data = _this.vm.scope.data;
		this.service.updateDel(_this.vm.updateUrl, _this.vm.scope.data).success(function (response) {
			$('#'+_this.vm.formId).modal('hide');
			_this.vm.scope.dtInstance.dataTable.fnUpdate(data, _this.vm.scope.dtInstance.DataTable.$('tr.selected'), undefined, false);
			perfUtils.getInstance().successMsg(_this.vm.title+' updated Successfully!');
        });
	};
	
	AbstractController.prototype.del = function(){
		var _this = this;
		this.service.updateDel(_this.vm.deleteUrl, _this.vm.scope.data).success(function (response) {
			_this.vm.scope.dtInstance.DataTable.row('.selected').remove().draw(false);
			$('#deleteRecord').modal('hide');
    		perfUtils.getInstance().successMsg(_this.vm.title+' deleted Successfully!');
        });
	};

	AbstractController.$inject = ['_this', 'vm', 'commonAPIservice','$compile', 'DTOptionsBuilder', 'DTColumnBuilder', '$q'];
	mainApp.controller('AbstractController', AbstractController);
})(angular);