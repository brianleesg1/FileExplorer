package sg.com.ncs.common;

import org.apache.commons.vfs2.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class ResourcesUtil {

    protected static final Logger log = LoggerFactory.getLogger(ResourcesUtil.class);

    public static List<Resource> getResource(String directory, boolean children_only) throws FileSystemException {
        log.info("getResource from -> " + directory);

        ArrayList<Resource> resources = new ArrayList<Resource>();

        FileSystemManager fsManager = VFS.getManager();
        FileObject fo = fsManager.resolveFile(directory);

        if (!children_only) {
            Resource resource = new Resource(fo.getName(), fo.getType(), 0 , fo.getContent().getLastModifiedTime());
            resources.add(resource);
        } else {

            if (fo.getType().compareTo(FileType.FOLDER) == 0) {

                FileObject[] children = fo.getChildren();

                log.error("no of children = " + children.length);
                for (int i=0; i<children.length; i++) {
                    FileObject child = children[i];
                    log.error("filename = " + child.getName() + " , type = " + child.getType().getName());

                    if (child.getType().compareTo(FileType.FILE) == 0) {
                        Resource resource = new Resource(child.getName(), child.getType(), child.getContent().getSize(), child.getContent().getLastModifiedTime());
                        resources.add(resource);
                    }
                    else {
                        Resource resource = new Resource(child.getName(), child.getType(), 0, child.getContent().getLastModifiedTime());
                        resources.add(resource);
                    }
                }
            }
        }

        return resources;
    }

    public static InputStream getResourceAsStream(String file) throws Exception  {
        log.info("getResourceAsStream from -> " + file);

        FileSystemManager fsManager = VFS.getManager();
        FileObject fo = fsManager.resolveFile(file);
        if (fo.getType().compareTo(FileType.FILE) == 0) {
            return fo.getContent().getInputStream();
        }
        else {
            throw new Exception("unable to get file inputstream from " + file);
        }


    }

    public static FileObject getResourceAsFile(String file) throws Exception  {
        log.info("getResourceAsFile from -> " + file);

        FileSystemManager fsManager = VFS.getManager();
        FileObject fo = fsManager.resolveFile(file);
        if (fo.getType().compareTo(FileType.FILE) == 0) {
            return fo;
        }
        else {
            throw new Exception("unable to get file inputstream from " + file);
        }


    }

    /*
    public static String encode(String str) {
        byte[] encoded = Base64.encodeBase64URLSafe(str.getBytes());
        return new String(encoded);
    }

    public static String decode(String str) {
        byte[] decoded = Base64.decodeBase64(str);

        return new String(decoded);
    }
    */
}
