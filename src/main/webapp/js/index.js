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

	var options = {
		url:'rest/book',
		colModel:[
			{name:'id', label: 'ID', formatter:'integer', width: 40},
			{name:'title', label: 'Title', width: 300},
			{name:'author', label: 'Author', width: 200},
			{name:'publishedYear', label: 'Published year', width: 80, align: 'center'},
			{name:'available', label: 'Available', formatter: 'checkbox', width: 46, align: 'center'}
		],
		caption: "Books",
		pager : '#pager',
		height: 'auto'
	};
	$("#grid")
			.jqGrid(options)
			.navGrid('#pager', {edit:false,add:false,del:false, search: false});

});
