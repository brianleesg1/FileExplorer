<!DOCTYPE HTML>
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="/WEB-INF/acl-taglib.tld" prefix="acl"%>
<%@ page import="sg.com.ncs.common.JSNode.FileTypes" %>

<html>

<head>
    <script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery/js/jquery-1.8.3.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jstree-v.pre1.0/jquery.jstree.js"></script>

    <!-- Load plupload and all it's runtimes and finally the jQuery queue widget -->
    <script type="text/javascript" src="<%=request.getContextPath()%>/scripts/plupload/plupload.full.js"></script>

    <!-- need to define flash_swf_url and silverlight_xap to be used by fileupload.js-->
    <script>
        var flash_swf_url = '<%=request.getContextPath()%>/scripts/plupload/plupload.flash.swf';
        var silverlight_xap_url = '<%=request.getContextPath()%>/scripts/plupload/plupload.silverlight.xap';
    </script>

    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/scripts/fileupload/fileupload.css"/>
    <script type="text/javascript" src="<%=request.getContextPath()%>/scripts/fileupload/fileupload.js"></script>

    <script type="text/javascript">

        function log(info) {
            if (typeof console != "undefined") {
                console.log(info);
            }
        }

        function downloadFile(id) {

             var elemIF= document.getElementById("downloadIframeId");

             if ( elemIF == null || typeof elemIF == "undefined" ){
             elemIF = document.createElement("iframe");
             elemIF.id="downloadIframeId";
             }

            //commend
            var url = "<acl:junction/><%=request.getContextPath()%>/rs/downloadfile?filename=" + id;
            log("url = " + url);


             elemIF.src = url ;
             elemIF.style.display = "none";
             document.body.appendChild(elemIF);

            //window.open(url,'','width=400,height=400');
            //window.focus();
        }

        $(document).ready(function(){
            log("ready");
            $("#filebrowser").jstree({
                "core" : { "animation" : 0 },
                "json_data" : {
                    "ajax" : {
                        "url" : "fetch",
                        "data" : function (n) {
                            // the result is fed to the AJAX request `data` option
                            return {
                                "operation" : "get_children",
                                //"id" : n.attr ? n.attr("id") : "root"
                                "id" : n.attr ? n.attr("id") : "root"
                            };
                        },
                        "error": function (jqXHR, textStatus, errorThrown) {
                            alert("There was an error while loading data for this tree " + jqXHR.responseText);
                        }
                    },
                    progressive_unload : true
                    // "progressive_render" : true
                },
                "types" : {
                    "max_children"  : -2,
                    "max_depth"     : -2,
                    "valid_children" : [ "drive" ],

                    "types" : {
                        "default" : {
                            "valid_children" : "none",
                            "icon" : {
                                "image" : "<%=request.getContextPath()%>/images/jstree/file.png"
                            }
                        },
                        "<%=FileTypes.PDF%>" : {
                            "valid_children" : "none",
                            "icon" : {
                                "image" : "<%=request.getContextPath()%>/images/jstree/pdf.png"
                            }
                        },
                        "<%=FileTypes.JPG%>" : {
                            "valid_children" : "none",
                            "icon" : {
                                "image" : "<%=request.getContextPath()%>/images/jstree/jpg.png"
                            }
                        },
                        "<%=FileTypes.PNG%>" : {
                            "valid_children" : "none",
                            "icon" : {
                                "image" : "<%=request.getContextPath()%>/images/jstree/png.png"
                            }
                        },
                        "<%=FileTypes.DOC%>" : {
                            "valid_children" : "none",
                            "icon" : {
                                "image" : "<%=request.getContextPath()%>/images/jstree/doc.png"
                            }
                        },
                        "<%=FileTypes.TXT%>" : {
                            "valid_children" : "none",
                            "icon" : {
                                "image" : "<%=request.getContextPath()%>/images/jstree/txt.png"
                            }
                        },
                        "<%=FileTypes.ZIP%>" : {
                            "valid_children" : "none",
                            "icon" : {
                                "image" : "<%=request.getContextPath()%>/images/jstree/zip.png"
                            }
                        },
                        "<%=FileTypes.FOLDER%>" : {
                            "valid_children" : [ "default", "folder" ],
                            "icon" : {
                                "image" : "<%=request.getContextPath()%>/images/jstree/folder.png"
                            }
                        },
                        "<%=FileTypes.DRIVE%>" : {
                            "valid_children" : [ "default", "folder" ],
                            "icon" : {
                                "image" : "<%=request.getContextPath()%>/images/jstree/root.png"
                            },
                            "start_drag" : false,
                            "move_node" : false,
                            "delete_node" : false,
                            "remove" : false
                        }
                    }
                },
                "contextmenu": {
                    "items": function ($node) {

                        if ( typeof $node[0].attributes.rel != "undefined" && ($node[0].attributes.rel.value === "<%=FileTypes.FOLDER%>" || $node[0].attributes.rel.value === "<%=FileTypes.DRIVE%>")) {
                            return {
                                "Refresh": {
                                    "label": "Refresh",
                                    "action": function (obj) {
                                        this.refresh(obj);
                                    }
                                }
                            };
                        }
                        else {
                            return {
                                "Download": {
                                    "label": "Download",
                                    "action": function (obj) {
                                        log("download");
                                        var id = obj[0].attributes.id.value;
                                        log("id = " + id);
                                        downloadFile(id);
                                    }
                                }
                            };

                        }
                    }
                },
                "plugins" : [ "themes", "json_data", "ui", "search", "types", "contextmenu" ]

            });

            var max_file_upload_size = ${max_file_upload_size};
            var max_file_name_length = ${max_file_name_length};

            var uploader = new vitas.fileupload( {
                flash_swf_url : '<%=request.getContextPath()%>/scripts/plupload-1.5.6/plupload.flash.swf',
                silverlight_xap_url : '<%=request.getContextPath()%>/scripts/plupload-1.5.6/plupload.silverlight.xap',
                upload_url : '<%=request.getContextPath()%>/rs/deploy',
                max_file_upload_size : max_file_upload_size,
                max_file_name_length : max_file_name_length
            });

            uploader.initContainer("ApplicationDeployment", [ { extensions : "war,ear" } ]);

            var uploadtest = new vitas.fileupload( {
                flash_swf_url : '<%=request.getContextPath()%>/scripts/plupload-1.5.6/plupload.flash.swf',
                silverlight_xap_url : '<%=request.getContextPath()%>/scripts/plupload-1.5.6/plupload.silverlight.xap',
                upload_url : '<%=request.getContextPath()%>/rs/upload',
                max_file_upload_size : max_file_upload_size
            });

            uploadtest.initContainer("uploadtest");

        });

    </script>
</head>

<body>

    <h2>File Explorer (If you encounter error while expanding the folders, try to refresh the page again.)</h2>
    <div id="filebrowser">
    </div>

    <hr/>

    <h2>Upload Test</h2>
    <div id="uploadtest">
        <table width="100%">
            <tr>
                <td colspan="2" style="text-align: right" ><span class="currentRuntime"/></td>
            </tr>
            <tr>
                <td width="20%">Upload file: <span class='asterisk'>*</span> :</td>
                <td width="80%">
                    <fieldset>
                        <legend>File you have chosen</legend>
                        <div class="filelist"></div>
                    </fieldset>
                </td>
            </tr>
            <tr>
                <td></td>
                <td>
                    <a class="pickfiles" href="#">[Select files]</a>

                    <a class="uploadfiles" href="#">[Upload files]</a>

                    <a class="deletefiles" href="#">[Delete files]</a>
                </td>
            </tr>
        </table>
    </div>

    <hr/>

    <h2>Deploy</h2>
    <div id="ApplicationDeployment">
        <table width="100%">
            <tr>
                <td colspan="2" style="text-align: right" ><span class="currentRuntime"/></td>
            </tr>
            <tr>
                <td width="20%">WAR/EAR file: <span class='asterisk'>*</span> :</td>
                <td width="80%">
                    <fieldset>
                        <legend>File you have chosen</legend>
                            <div class="filelist"></div>
                  </fieldset>
                </td>
            </tr>
            <tr>
                <td></td>
                <td>
                    <a class="pickfiles" href="#">[Select files]</a>

                    <a class="uploadfiles" href="#">[Upload files]</a>

                    <a class="deletefiles" href="#">[Delete files]</a>
                </td>
            </tr>
        </table>
    </div>

</body>
</html>
