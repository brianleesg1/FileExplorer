package sg.com.ncs.backingBean;

import org.apache.commons.vfs2.FileObject;
import org.apache.commons.vfs2.FileType;
import org.apache.commons.vfs2.FileUtil;
import org.primefaces.event.NodeCollapseEvent;
import org.primefaces.event.NodeExpandEvent;
import org.primefaces.event.NodeSelectEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.TreeNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import sg.com.ncs.common.JSNode;
import sg.com.ncs.common.Resource;
import sg.com.ncs.common.ResourcesUtil;
import sg.com.ncs.common.SystemProperties;

import javax.faces.bean.ViewScoped;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: brian
 * Date: 19/6/13
 * Time: 3:07 PM
 * To change this template use File | Settings | File Templates.
 */

@Component
@Scope("view")
public class FileBrowserBean implements Serializable {

    private DefaultTreeNode root;
    Logger log = LoggerFactory.getLogger(FileBrowserBean.class);

    private TreeNode selectedNode;

    private StreamedContent downloadfile;

    public FileBrowserBean() {
        root = new DefaultTreeNode("Root", null);

        try {

            String upload_file_staging_root = SystemProperties.getProperty("UPLOAD_FILE_STAGING_ROOT");
            log.info("upload_file_staging_root = " + upload_file_staging_root);
            getNodes(upload_file_staging_root,false, root);

            String upload_file_tmp_root = SystemProperties.getProperty("UPLOAD_FILE_TMP_ROOT");
            log.info("upload_file_tmp_root = " + upload_file_tmp_root);
            getNodes(upload_file_tmp_root,false, root);

            String jboss_root = SystemProperties.getProperty("JBOSS_ROOT");
            log.info("LOG_FILE_ROOT = " + jboss_root);
            getNodes(jboss_root,false, root);

            String deployment_tmp_root = SystemProperties.getProperty("DEPLOYMENT_TMP_FOLDER");
            log.info("DEPLOYMENT_TMP_ROOT = " + deployment_tmp_root);
            getNodes(deployment_tmp_root,false, root);

            prepareRoot();

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public TreeNode getRoot() {
        return root;
    }

    private void getNodes(String directory, boolean children_only, DefaultTreeNode parent) throws Exception {
        List<Resource> resources = ResourcesUtil.getResource(directory, children_only);
        Iterator<Resource> it = resources.iterator();

        //ArrayList<DefaultTreeNode> results = new ArrayList<DefaultTreeNode>();
        while (it.hasNext()) {
            Resource res = it.next();
            DefaultTreeNode node = new DefaultTreeNode();
            //JSNode node = new JSNode();
            //node.setData(res.getName());
            //node.setID(res.getId());
            switch (res.getType()) {
                case FOLDER : {
                    node.setType(FileType.FOLDER.toString());
                    node.setData(res);

                    //node.setFileType(JSNode.FileTypes.FOLDER.name());
                    //node.closed();
                    break;
                }
                case FILE : {
                    node.setType(res.getType().getName());
                    node.setData(res);
                    //node.setFileType(res.getExtension());
                    //no have to set because JSNode default is FILE
                    //node.opened();
                    break;
                }
                default : {

                }

            }

            node.setParent(parent);
            //lookup.add(res.getId(), res.getFullPathName());
            //results.add(node);
        }

        //return results;
    }

    public void onNodeSelect(NodeSelectEvent event) {
        Resource node = (Resource)event.getTreeNode().getData();
        log.info("node = " + node.getName());
    }
    public void onNodeExpand(NodeExpandEvent event) {
        try {
            if (event.getTreeNode().getChildCount() > 0 ) {
                return;
            }
            Resource resource = (Resource)event.getTreeNode().getData();
            log.info("node = " + resource.getFullPathName());
            getNodes(resource.getPath(),true,(DefaultTreeNode)event.getTreeNode());
        } catch(Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
    public void onNodeCollapse(NodeCollapseEvent event) {
        Resource node = (Resource)event.getTreeNode().getData();
        log.info("node = " + node.getName());
    }

    private void prepareRoot() throws Exception {


        log.info("no of nodes = " + root.getChildCount());

        List<TreeNode> children = root.getChildren();
        for (int i=0; i<children.size(); i++) {
            DefaultTreeNode child = (DefaultTreeNode)children.get(i);
            Resource res = (Resource)child.getData();
            log.info("directory = " + res.getPath());
            getNodes(res.getPath(),true,child);
        }
    }

    public TreeNode getSelectedNode() {
        return selectedNode;
    }

    public void setSelectedNode(TreeNode selectedNode) {
        this.selectedNode = selectedNode;
    }

    public StreamedContent getDownloadfile() {
        log.info("getDownloadfile -> ");
        Resource res = (Resource)selectedNode.getData();

        log.info(res.getFullPathName());
        try {
            FileObject fo = ResourcesUtil.getResourceAsFile(res.getFullPathName());

            InputStream inputStream = fo.getContent().getInputStream();

            downloadfile = new DefaultStreamedContent(inputStream,  "attachment; filename=\"" + fo.getName().getBaseName() + "\"", res.getName(), "binary");
            return downloadfile;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public void setDownloadfile(StreamedContent downloadfile) {
        this.downloadfile = downloadfile;
    }

    public void refreshFolder() {
        log.info("refreshFolder");

        try {
            Resource res = (Resource)selectedNode.getData();
            if (res.getType().compareTo(FileType.FOLDER) == 0) {
                List<TreeNode> children = selectedNode.getChildren();
                children.clear();

                getNodes(res.getFullPathName(), true, (DefaultTreeNode) selectedNode);
                //selectedNode.getParent().getChildren().remove(selectedNode);


            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
