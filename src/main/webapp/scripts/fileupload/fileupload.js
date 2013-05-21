
vitas = {};

vitas.fileupload = function(options) {
    this.flash_swf_url = options.flash_swf_url;
    this.silverlight_xap_url = options.silverlight_xap_url;
    this.upload_url = options.upload_url;
    if (typeof options.callback != "undefined") {
        this.callback = options.callback;
    }
}

vitas.fileupload.prototype.callback = function(msg) {
    //internal callback which do nothing, can be overwritten using fileupload construtor method options.callback
    common.log("internal callback -> msg");
}

vitas.fileupload.prototype.initContainer = function (containername, filters) {
    common.log("initContainer -> " + containername);

    var pickfiles_id = containername + '_' + 'pickfiles';

    if (typeof filters == "undefined") {
        filters = [];
    }

    uploadcontainer = this;

    $('#'+containername + ' .pickfiles').attr('id',pickfiles_id);

    var uploader = new plupload.Uploader({
        runtimes : 'gears,html5,flash,silverlight,browserplus',
        browse_button : pickfiles_id,
        container : containername,
        max_file_size : '10mb',
        url : this.upload_url,
        flash_swf_url : this.flash_swf_url,
        silverlight_xap_url : this.silverlight_xap_url,
        filters : filters,
        resize : {width : 320, height : 240, quality : 90}
    });

    uploader.bind('Init', function(up, params) {
        $(' .currentRuntime').html("<div>Current runtime: " + params.runtime + "</div>");



    });

    $('#'+containername + ' .uploadfiles').click(function(e) {
        uploader.start();
        e.preventDefault();
    });

    uploader.init();

    uploader.bind('FilesAdded', function(up, files) {
        $.each(files, function(i, file) {
            $('#'+containername + ' .filelist').append('<div id="' + file.id + '">' + '<span class="fileselect"><input type="checkbox" name="first" value="cg"/></span> <label class="filename">' + file.name + ' (' + plupload.formatSize(file.size) + ')</label>' + '<label class="filestatus">Selected</label></div>');

            $('#'+containername + ' .deletefiles').click(function(e) {

                $('#'+file.id+ ' .fileselect :checkbox:checked').each(function() {
                    e.preventDefault();
                    common.log("delete " + file.name);
                    uploader.removeFile(file);
                    $('#' + file.id).remove();
                    up.refresh(); // Reposition Flash/Silverlight
                });


            });

        });
        up.refresh(); // Reposition Flash/Silverlight
    });

    uploader.bind('UploadProgress', function(up, file) {
        $('#' + file.id + " .filestatus").html(file.percent + "% completed");
    });

    uploader.bind('Error', function(up, err) {

        if (err.status == 500) {
            err.message = 'Internal Server Error';
        }

        $('#'+err.file.id + ' .filestatus').addClass('fileerror').html("Error: " + err.status + " - " + err.message);
        $('#' + file.id + " .fileselect :checkbox").attr('disabled', 'disabled');

        up.refresh(); // Reposition Flash/Silverlight
        uploadcontainer.callback(err.message);


    });

    uploader.bind('FileUploaded', function(up, file,info) {

        if (typeof info.status != 'undefined') {
            if (info.status != 200) {
                //if status is 0, could be server not available or network error.
                //$('#'+containername + ' > .filelist').append("<div>Error: " + info.status + ", Message: " + info.response + ", File: " + file.name + "</div>");
                $('#' + file.id + " .filestatus").addClass('fileerror').html("0% - Failed, Error : IO Error");
                $('#' + file.id + " .fileselect :checkbox").attr('disabled', 'disabled');

                uploadcontainer.callback("IO Error");
            }
        }

        //for some runtimes like html4, flash, the info.status may be undefined.
        var result = jQuery.parseJSON(info.response);

        if (result) {
            if (result.status == "successful") {
                $('#' + file.id + " .filestatus").html("100% completed.");
                uploadcontainer.callback("successful");

            }
            else {
                $('#' + file.id + " .filestatus").addClass('fileerror').html("100% - Failed, " + result.status + ".");
                uploadcontainer.callback(result.status);

            }
        } else {
            $('#' + file.id + " .filestatus").addClass('fileerror').html("100% - Failed, unknown reason.");
            uploadcontainer.callback("unknown reason");

        }
        $('#' + file.id + " .fileselect :checkbox").attr('disabled', 'disabled');

    });
};

var common = {};

common.log = function log(msg) {
    if (typeof console != "undefined") {
        console.log(msg);
    }
}
