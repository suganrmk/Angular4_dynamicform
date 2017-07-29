(function(root){
    var perfDatatable = root.perfDatatable || {};
    this.params = null;
    perfDatatable.loadTable = {
        init: function(params){
            this.params = params;
            var editRow = true, deleteRow = true, actions = true;
            params.vm.message = '';
            params.vm.dtInstance = {};
            params.vm.datalist = {};
            params.url = (params.loadListUrl)?params.loadListUrl:'';
            params.editRow = (params.editRow === undefined)?editRow:params.editRow;
            params.deleteRow = (params.deleteRow === undefined)?deleteRow:params.deleteRow;
            params.actions = (params.actions === undefined)?actions:params.actions;
            params.responsive = params.responsive?params.responsive: false;
            perfDatatable.loadTable.fnSortDate();
            perfDatatable.loadTable.loadDataTable(params);
        },
        fnSortDate: function(){
        	jQuery.extend( jQuery.fn.dataTableExt.oSort, {
                "date-pre": function ( date ) {
                    return moment(date, 'DD-MMM-YYYY');
                },
                "date-asc": function ( a, b ) {
                    return (a.isBefore(b) ? -1 : (a.isAfter(b) ? 1 : 0));
                },
                "date-desc": function ( a, b ) {
                    return (a.isBefore(b) ? 1 : (a.isAfter(b) ? -1 : 0));
                }
            });
        },
        loadRecords: function(){
    		var deferred = this.params.$q.defer();
        	this.params.service.loadRecords(this.params.url).success(function (response) {
				if(response.entity){
					deferred.resolve(response.entity);
				}
	        });
			return deferred.promise;	
        },
        loadDataTable: function(params){
        	params.vm.dtOptions = params.DtOptionsBuilder;
        	if(params.url != ''){
        		params.vm.dtOptions = params.DtOptionsBuilder.fromFnPromise(function() {
                    return perfDatatable.loadTable.loadRecords();
                });	
        	} else {
        		params.vm.dtOptions =  params.DtOptionsBuilder.fromSource(params.url);
        	}            
            //.withDataProp('entity')
        	var displayLength = 7;
        	var displayMenu = [];
        	if(bowser.mobile || bowser.tablet){
        		displayLength = 10;
        		displayMenu = [10, 25, 50, 100];
    	    } else {
    	    	displayMenu = [7, 10, 25, 50, 100];
    	    }
        	
        	params.vm.dtOptions.withDisplayLength(displayLength)
            .withOption('lengthMenu', displayMenu)
//            .withDOM('pitrfl')
            .withBootstrap()
            .withOption('createdRow', createdRow)
	        .withOption('paging', true)
            .withOption('aaSorting', [params.sortCol === undefined? 0: params.sortCol, 'asc'])
            .withPaginationType('full_numbers')
            .withOption("oLanguage", {"sEmptyTable": params.vm.sEmptyTable === undefined?"No Records Found.": params.vm.sEmptyTable})
            .withColumnFilter()
            .withOption('fnDrawCallback', function(){
            	$('.DTFC_LeftWrapper').hide();
            	$('.dataTables_wrapper').trigger('resize');
            	$('.DTFC_RightBodyWrapper').trigger('resize');
	        })
            .withButtons([
                {
                    extend : 'excel',
                    title: 'Perficient Chennai - '+params.vm.title,
                    exportOptions: {
                        columns: params.actions === false? ':visible':':not(:last-child)'
                    }
                },
                {
                    extend : 'print',
                    title: 'Perficient Chennai - '+params.vm.title,
                    exportOptions: {
                        columns: params.actions === false? ':visible':':not(:last-child)'
                    }
                }
            ]);
        	
        	//check the device
    	    if(bowser.mobile || bowser.tablet){
    	    	params.vm.dtOptions.withFixedColumns({
    	               rightColumns: 1
    	        })
    	        .withOption('scrollX', '100%')
    	        .withOption('scrollCollapse', true);
    	    }
        	
            if(params.actions)
                params.vm.dtColumns.push(params.DTColumnBuilder.newColumn(null).withTitle('Actions').withClass('actionsCol').notSortable().renderWith(actionsHtml));

            function createdRow(row) {
                // Recompiling so we can bind Angular directive to the DT
                if(params.scope)
	                params.compile(angular.element(row).contents())(params.scope);
	            else
		            params.compile(angular.element(row).contents())(params.vm);
            }
            function actionsHtml(data) {
                params.vm.datalist[data.pk] = data;
                var editRecord='', deleteRecord ='';
                if(params.editRow){
                    editRecord = '<button class="btn btn-edit" id="'+data.pk+'" data-toggle="modal" onclick="perfDatatable.loadTable.popRecord(this, '+data.pk+', '+params.editFormId+')">' +
                    '   <i class="fa fa-info-circle"></i>' +
                    '</button>&nbsp;';
                }
                if(params.deleteRow){
                    if(data.isDel === undefined || data.isDel === true){
                		deleteRecord = '<button class="btn btn-danger" id="'+data.pk+'" data-toggle="modal" onclick="perfDatatable.loadTable.popRecord(this, '+data.pk+', '+params.deleteFormId+')">' +
                        '   <i class="fa fa-trash-o"></i>' +
                        '</button>';	
                	}
                }
                return  editRecord+deleteRecord;
            };
        },
        popRecord: function(ele, id, formId){
            this.params.vm.dtInstance.DataTable.$('tr.selected').removeClass('selected');
            if($('.DTFC_ScrollWrapper').length != 0)
            	$(ele).closest('.DTFC_ScrollWrapper').find('.dataTables_scroll tr button#'+id).parents('tr').addClass('selected');
            else
                $(ele).parents('tr').addClass('selected');
        	this.params.vm.data = angular.copy(this.params.vm.datalist[id]);        
            this.params.vm.$apply();
            if(this.params.vm.valUpdateDelete){
            	this.params.vm.valUpdateDelete();
            }
            $(formId).modal('show');
        }
    };
    root.perfDatatable = perfDatatable;
})(this);