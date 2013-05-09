<!DOCTYPE HTML>
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>

<head>
    <script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery/js/jquery-1.8.3.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jstree-v.pre1.0/jquery.jstree.js"></script>

    <script type="text/javascript">

        function downloadFile(id) {

             var elemIF= document.getElementById("downloadIframeId");

             if ( elemIF == null || elemIF == "undefinde" ){
             elemIF = document.createElement("iframe");
             elemIF.id="downloadIframeId";
             }

            var url = '<%=request.getContextPath()%>/downloadfile?filename=' + id;
            console.log("url = " + url);


             elemIF.src = url ;
             elemIF.style.display = "none";
             document.body.appendChild(elemIF);

            //window.open(url,'','width=400,height=400');
            //window.focus();
        }

        $(document).ready(function(){
            console.log("ready");
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
                        "pdf" : {
                            "valid_children" : "none",
                            "icon" : {
                                "image" : "<%=request.getContextPath()%>/images/jstree/pdf.png"
                            }
                        },
                        "jpg" : {
                            "valid_children" : "none",
                            "icon" : {
                                "image" : "<%=request.getContextPath()%>/images/jstree/jpg.png"
                            }
                        },
                        "png" : {
                            "valid_children" : "none",
                            "icon" : {
                                "image" : "<%=request.getContextPath()%>/images/jstree/png.png"
                            }
                        },
                        "doc" : {
                            "valid_children" : "none",
                            "icon" : {
                                "image" : "<%=request.getContextPath()%>/images/jstree/doc.png"
                            }
                        },

                        "folder" : {
                            "valid_children" : [ "default", "folder" ],
                            "icon" : {
                                "image" : "<%=request.getContextPath()%>/images/jstree/folder.png"
                            }
                        },
                        "drive" : {
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

                        if ($node[0].attributes.rel.value === "folder" || $node[0].attributes.rel.value === "drive") {
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
                                        console.log("download");
                                        var id = obj[0].attributes.id.value;
                                        console.log("id = " + id);
                                        downloadFile(id);
                                    }
                                }
                            };

                        }
                    }
                },
                "plugins" : [ "themes", "json_data", "ui", "search", "types", "contextmenu" ]

            });

        });

    </script>
</head>

<body>
    <h2>File Explorer</h2>
    <div id="filebrowser">
    </div>

</body>
</html>
