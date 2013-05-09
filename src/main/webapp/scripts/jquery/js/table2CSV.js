jQuery.fn.exportAllData2CSV = function(options) {

	var el = this;

	function exportExcel() {
		var mya1 = new Array();
		mya1 = $(el).getDataIDs();
		var colNames = new Array();
		var myaValue = $(el).getRowData(mya1[0]);
		var ii = 0;
		for ( var i in myaValue) {
			colNames[ii++] = i;
		}// capture col names
		var mya = new Array();
		mya = $(el).jqGrid('getGridParam', 'data');
		var html = "";
		for (k = 0; k < colNames.length; k++) {
			if(k != (colNames.length-1)){
				html = html + '"' + colNames[k] + '"' + ","; // output each Column as tab delimited
			}
			else{
				html = html + '"' + colNames[k] + '"'; // output each Column as tab delimited
			}
		}
		html = html + "\n";
		for (i = 0; i < mya.length; i++) {
			myaValue = mya[i];
			for (j = 0; j < colNames.length; j++) {
				if(j != (colNames.length-1)){
					html = html + '"' + trim(myaValue[colNames[j]]) + '"' + ","; // output each Row as tab delimited
				}
				else{
					html = html + '"' + trim(myaValue[colNames[j]]) + '"'; // output each Row as tab delimited
				}
			}
			html = html + "\n"; // output each row with end of line
		}
		html = html + "\n"; // end of line at the end
		return html;
	}

	function popup(data) {
		var generator = window.open('', 'csv', 'height=600,width=800');
		generator.document.write('<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">');
		generator.document.write('<html xmlns="http://www.w3.org/1999/xhtml">');
		generator.document.write('<head><meta http-equiv="content-type" content="text/html; charset=utf-8" />');
		generator.document.write('</head><body >');
		//generator.document.write('<textArea cols=95 rows=35 wrap="off" >');
		generator.document.write('<pre>');
		generator.document.write(data);
		//generator.document.write('</textArea>');
		generator.document.write('</pre>');
		generator.document.write('</body></html>');
		generator.document.close();
		generator.document.execCommand('SaveAs', false, 'GET_DATA.txt');
		/*
		 * var fsObj = new ActiveXObject("Scripting.FileSystemObject"); var
		 * newFile = fsObj.OpenAsTextStream("GET_DATA.csv", 1, true,
		 * 0); newFile.WriteLine(data); newFile.close();
		 */
		return true;
	}

	return popup(exportExcel());
}

jQuery.fn.table2CSV = function(options) {
	var options = jQuery.extend({
		separator : ',',
		header : [],
		delivery : 'popup'// popup, value
	}, options);

	var csvData = [];
	var headerArr = [];
	var el = this;

	// header
	var numCols = options.header.length;
	var tmpRow = []; // construct header avalible array

	if (numCols > 0) {
		for ( var i = 0; i < numCols; i++) {
			tmpRow[tmpRow.length] = formatData(options.header[i]);
		}
	} else {
		$(el).filter(':visible').find('th').each(function() {
			if ($(this).css('display') != 'none')
				tmpRow[tmpRow.length] = formatData($(this).html());
		});
	}

	row2CSV(tmpRow);

	// actual data
	$(el).find('tr').each(function() {
		var tmpRow = [];
		$(this).find('td').each(function() {
			if ($(this).css('display') != 'none')
				tmpRow[tmpRow.length] = formatData($(this).html());
		});
		row2CSV(tmpRow);
	});
	if (options.delivery == 'popup') {
		var mydata = csvData.join('\n');
		return popup(mydata);
	} else {
		var mydata = csvData.join('\n');
		return mydata;
	}

	function row2CSV(tmpRow) {
		var tmp = tmpRow.join('') // to remove any blank rows
		// alert(tmp);
		if (tmpRow.length > 0 && tmp != '') {
			var mystr = tmpRow.join(options.separator);
			csvData[csvData.length] = mystr;
		}
	}
	function formatData(input) {
		// replace " with “
		var regexp = new RegExp(/["]/g);
		var output = input.replace(regexp, "“");
		// HTML
		var regexp = new RegExp(/\<[^\<]+\>/g);
		var output = output.replace(regexp, "");
		if (output == "")
			return '';
		return output;
	}

	function popup(data) {
		var generator = window.open('', 'csv', 'height=400,width=600');
		generator.document.write('<html><head>');
		generator.document.write('</head><body >');
		generator.document.write('<textArea cols=70 rows=15 wrap="off" >');
		generator.document.write(data);
		generator.document.write('</textArea>');
		generator.document.write('</body></html>');
		generator.document.close();
		generator.document.execCommand('SaveAs', false, '.txt');
		/*
		 * var fsObj = new ActiveXObject("Scripting.FileSystemObject"); var
		 * newFile = fsObj.OpenAsTextStream("EXPORTED_DATA_BASE.csv", 1, true,
		 * 0); newFile.WriteLine(data); newFile.close();
		 */
		return true;
	}
};