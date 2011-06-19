$(function() {

	$.extend($.jgrid.defaults, {
				datatype: 'json',
				jsonReader : {
					repeatitems:false,
					total: function(result) {
						//Total number of pages
						return Math.ceil(result.total / result.max);
					},
					records: function(result) {
						//Total number of records
						return result.total;
					}
				},
				prmNames: {rows: 'max', search: null},
				height: 'auto',
				viewrecords: true,
				rowList: [10, 20, 50, 100],
				altRows: true,
				loadError: function(xhr, status, error) {
					alert(error);
				}
			});

	$.extend($.jgrid.edit, {
				closeAfterEdit: true,
				closeAfterAdd: true,
				ajaxEditOptions: { contentType: "application/json" },
				mtype: 'PUT',
				serializeEditData: function(data) {
					delete data.oper;
					return JSON.stringify(data);
				}
			});
	$.extend($.jgrid.del, {
				mtype: 'DELETE',
				serializeDelData: function() {
					return "";
				}
			});

	var editOptions = {
		onclickSubmit: function(params, postdata) {
			params.url = URL + '/' + postdata.id;
		}
	};
	var addOptions = {mtype: "POST"};
	var delOptions = {
		onclickSubmit: function(params, postdata) {
			params.url = URL + '/' + postdata;
		}
	};

	var URL = 'rest/book';
	var options = {
		url: URL,
		editurl: URL,
		colModel:[
			{
				name:'id', label: 'ID',
				formatter:'integer',
				width: 40,
				editable: true,
				editoptions: {disabled: true, size:5}
			},
			{
				name:'title',
				label: 'Title',
				width: 300,
				editable: true,
				editrules: {required: true}
			},
			{
				name:'author',
				label: 'Author',
				width: 200,
				editable: true,
				editrules: {required: true}
			},
			{
				name:'cover',
				label: 'Cover',
				hidden: true,
				editable: true,
				edittype: 'select',
				editrules: {edithidden:true},
				editoptions: {
					value: {'PAPERBACK': 'paperback', 'HARDCOVER': 'hardcover', 'DUST_JACKET': 'dust jacket'}
				}
			},
			{
				name:'publishedYear',
				label: 'Published year',
				width: 80,
				align: 'center',
				editable: true,
				editrules: {required: true, integer: true},
				editoptions: {size:5, maxlength: 4}
			},
			{
				name:'available',
				label: 'Available',
				formatter: 'checkbox',
				width: 46,
				align: 'center',
				editable: true,
				edittype: 'checkbox',
				editoptions: {value:"true:false"}
			},
			{
				name:'comments',
				label: 'Comments',
				hidden: true,
				editable: true,
				edittype: 'textarea',
				editrules: {edithidden:true}
			}
		],
		caption: "Books",
		pager : '#pager',
		height: 'auto',
		ondblClickRow: function(id) {
			jQuery(this).jqGrid('editGridRow', id, editOptions);
		}
	};

	$("#grid")
			.jqGrid(options)
			.navGrid('#pager',
			{}, //options
			editOptions,
			addOptions,
			delOptions,
			{} // search options
	);

});
