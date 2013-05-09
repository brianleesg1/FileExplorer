package sg.com.ncs.fileexplorer;


import org.apache.commons.vfs2.FileObject;
import org.apache.commons.vfs2.FileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import sg.com.ncs.common.JSNode;
import sg.com.ncs.common.Resource;
import sg.com.ncs.common.ResourcesUtil;
import sg.com.ncs.common.SystemProperties;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


@Controller
public class MainController {
    Logger log = LoggerFactory.getLogger(MainController.class);

    @RequestMapping(value = "/init", method = RequestMethod.GET)
    public String init() {
        log.info("MainController home..");


        return "index";
    }

    @RequestMapping(value = "/fetch", method = RequestMethod.GET)
    public @ResponseBody List<JSNode> fetch(String operation, String id) throws Exception {
        log.info("operation = " + operation);
        log.info("id = " + id);

        ArrayList<JSNode> list = new ArrayList<JSNode>();

        if ("root".equalsIgnoreCase(id)) {


            //JSNode root = new JSNode();
            //root.setData("root");
            //root.setID("root");
            //root.opened();
            //root.setAsDrive();

            String upload_file_root = SystemProperties.getProperty("UPLOAD_FILE_ROOT");
            log.info("upload_file_root = " + upload_file_root);
            List<JSNode> nodes = getNodes(upload_file_root);
            log.info("no of nodes = " + nodes.size());
            list.addAll(nodes);
            //root.addChildren(nodes);

            //list.add(root);

        }
        else {
            log.info("id = " + id);
            id = ResourcesUtil.decode(id);
            log.info("decoded id = " + id);

            List<JSNode> nodes = getNodes(id);
            log.info("no of nodes = " + nodes.size());

            list.addAll(nodes);
        }

        return list;

    }

    @RequestMapping(value = "/downloadfile", method = RequestMethod.GET)
    public @ResponseBody byte[] downloadfile(String filename, HttpServletResponse response) throws Exception {
        log.info("downloadfile -> " + filename);

        String id = ResourcesUtil.decode(filename);
        log.info("decoded file = " + id);
        FileObject fo = ResourcesUtil.getResourceAsFile(id);

        //HttpHeaders responseHeaders = new HttpHeaders();
        //responseHeaders.setContentType(MediaType.parseMediaType("application/octet-stream"));
        //responseHeaders.setContentLength(fo.getContent().getSize());
        //responseHeaders.set("Content-Disposition", "attachment");
        //responseHeaders.add("Content-Disposition", "filename=\"" + fo.getName().getBaseName() + '\"');

        byte[] data = FileUtil.getContent(fo);

        response.setHeader("Content-Disposition", "attachment; filename=\"" + fo.getName().getBaseName() + "\"");
        response.setContentType("application/octet-stream");
        response.setContentLength(data.length);
        return data;
    }

    private List<JSNode> getNodes(String directory) throws Exception {
        List<Resource> resources = ResourcesUtil.getResource(directory);
        Iterator<Resource> it = resources.iterator();

        ArrayList<JSNode> results = new ArrayList<JSNode>();
        while (it.hasNext()) {
            Resource res = it.next();
            JSNode node = new JSNode();
            node.setData(res.getName());
            node.setID(res.getId());
            switch (res.getType()) {
                case FOLDER : {
                    node.setAsFolder();
                    node.closed();
                    break;
                }
                case FILE : {
                    if (res.getExtension().equalsIgnoreCase("pdf")) {
                        node.setAsPDF();
                    }
                    if (res.getExtension().equalsIgnoreCase("jpg")) {
                        node.setAsJPG();
                    }
                    if (res.getExtension().equalsIgnoreCase("png")) {
                        node.setAsPNG();
                    }
                    if (res.getExtension().equalsIgnoreCase("doc")) {
                        node.setAsDOC();
                    }
                    //no have to set because JSNode default is FILE
                    node.opened();
                    break;
                }
                default : {

                }

            }

            results.add(node);
        }

        return results;
    }

}