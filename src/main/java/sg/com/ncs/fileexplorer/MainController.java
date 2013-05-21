package sg.com.ncs.fileexplorer;


import org.apache.commons.io.FileUtils;
import org.apache.commons.vfs2.FileObject;
import org.apache.commons.vfs2.FileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import sg.com.ncs.common.*;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


@Controller
public class MainController {
    Logger log = LoggerFactory.getLogger(MainController.class);

    @Autowired
    LookUpTable<String,String> lookup = new LookUpTable<String,String>();

    @Autowired
    ServletContext context;

    @RequestMapping(value = "/init", method = RequestMethod.GET)
    public String init() {
        log.info("MainController home..");


        return "index";
    }

    @RequestMapping(value = "/fetch", method = RequestMethod.GET)
    public @ResponseBody List<JSNode> fetch(@RequestParam("operation") String operation, @RequestParam("id") String id) throws Exception {
        log.info("operation = " + operation);
        log.info("id = " + id);

        ArrayList<JSNode> list = new ArrayList<JSNode>();

        if ("root".equalsIgnoreCase(id)) {

            lookup.clear();

            String upload_file_staging_root = SystemProperties.getProperty("UPLOAD_FILE_STAGING_ROOT");
            log.info("upload_file_staging_root = " + upload_file_staging_root);
            List<JSNode> staging_nodes = getNodes(upload_file_staging_root,false);
            log.info("no of nodes = " + staging_nodes.size());
            list.addAll(staging_nodes);

            String upload_file_tmp_root = SystemProperties.getProperty("UPLOAD_FILE_TMP_ROOT");
            log.info("upload_file_tmp_root = " + upload_file_tmp_root);
            List<JSNode> tmp_nodes = getNodes(upload_file_tmp_root,false);
            log.info("no of nodes = " + tmp_nodes.size());
            list.addAll(tmp_nodes);

            String jboss_root = SystemProperties.getProperty("JBOSS_ROOT");
            log.info("LOG_FILE_ROOT = " + jboss_root);
            List<JSNode> log_nodes = getNodes(jboss_root,false);
            log.info("no of nodes = " + log_nodes.size());
            list.addAll(log_nodes);
        }
        else {
            id = lookup.get(id);
            log.info("lookup id = " + id);

            if (StringUtil.isNullOrEmpty(id)) {
                String errmsg = "if id is null, this means that the application may have been restarted, need to close/reopen browser again.";
                log.error(errmsg);
                throw new Exception(errmsg);
            }

            List<JSNode> nodes = getNodes(id,true);
            log.info("no of nodes = " + nodes.size());

            list.addAll(nodes);
        }

        return list;

    }

    @RequestMapping(value = "/downloadfile", method = RequestMethod.GET, produces = "application/octet-stream")
    public @ResponseBody byte[] downloadfile(String filename, HttpServletResponse response) throws Exception {
        log.info("downloadfile -> " + filename);

        //String id = ResourcesUtil.decode(filename);
        //log.info("decoded file = " + id);
        String id = lookup.get(filename);
        log.info("lookup id = " + id);

        FileObject fo = ResourcesUtil.getResourceAsFile(id);
        byte[] data = FileUtil.getContent(fo);

        response.setHeader("Content-Disposition", "attachment; filename=\"" + fo.getName().getBaseName() + "\"");
        response.setHeader("Content-Transfer-Encoding", "binary");
        response.setContentLength(data.length);
        return data;
    }

    private List<JSNode> getNodes(String directory, boolean children_only) throws Exception {
        List<Resource> resources = ResourcesUtil.getResource(directory,children_only);
        Iterator<Resource> it = resources.iterator();

        ArrayList<JSNode> results = new ArrayList<JSNode>();
        while (it.hasNext()) {
            Resource res = it.next();
            JSNode node = new JSNode();
            node.setData(res.getName());
            node.setID(res.getId());
            switch (res.getType()) {
                case FOLDER : {
                    node.setFileType(JSNode.FileTypes.FOLDER.name());
                    node.closed();
                    break;
                }
                case FILE : {
                    node.setFileType(res.getExtension());
                    //no have to set because JSNode default is FILE
                    node.opened();
                    break;
                }
                default : {

                }

            }

            lookup.add(res.getId(), res.getFullPathName());
            results.add(node);
        }

        return results;
    }

    @RequestMapping(value = "/upload", method = RequestMethod.POST, produces = "application/json")
    public @ResponseBody UploadStatus uploadFile(UploadItem uploadItem, BindingResult result) {

        log.info("uploadFile");


        if (result.hasErrors()) {
            StringBuilder errors = new StringBuilder();
            for (ObjectError error : result.getAllErrors()) {
                errors.append("Error: ").append(error.getCode()).append(" - ").append(error.getDefaultMessage()).append("\n");

            }
            log.error(errors.toString());
            //throw new RuntimeException("Error in uploadFile");
            return new UploadStatus("failure", errors.toString());
        }

        uploadItem.printFileInfo();
        MultipartFile file = uploadItem.getFile();
        return new UploadStatus("successful","");

    }

    @RequestMapping(value = "/deploy", method = RequestMethod.POST, produces = "application/json")
    public @ResponseBody UploadStatus deploy(UploadItem uploadItem, BindingResult result) {

        try {
            uploadItem.printFileInfo();
            File tmpFile = copyToTmpFolder(uploadItem.getFile());
            //moveToDeploymentFolder(tmpFile);

            return new UploadStatus("successful","");
        } catch (Exception e) {
            e.printStackTrace();;
            return new UploadStatus("failure","fail to deploy");
        }
    }

    private File copyToTmpFolder(MultipartFile file) throws IOException {
        log.info("copyToTmpFolder");


        String destFile = SystemProperties.getProperty("DEPLOYMENT_TMP_FOLDER" + file.getOriginalFilename());
        File dest = new File(destFile);
        file.transferTo(dest);

        return dest;

        /*
        String destTmpFolder = SystemProperties.getProperty("DEPLOYMENT_TMP_FOLDER") + File.separator + SystemUtil.getSystemId();
        File tmpFolder = new File(destTmpFolder);
        tmpFolder.mkdir();

        String dest = destTmpFolder + File.separator + file.getOriginalFilename();
        log.info("dest -> " + dest);

        File f = new File(dest);
        file.transferTo(f);
        return f;
        */


    }

    private void moveToDeploymentFolder(File sourceFile) throws IOException {
        log.info("moveToDeploymentFolder");

        String destTmpFolder = SystemProperties.getProperty("DEPLOYMENT_FOLDER");
        //String destTmpFilename = SystemUtil.getSystemId() + "." + sourceFile.getName();
        String dest = destTmpFolder + File.separator + sourceFile.getName();;
        log.info("dest -> " + dest);

        File destFile = new File(dest);

        FileUtils.copyFile(sourceFile, destFile,true);

    }
}