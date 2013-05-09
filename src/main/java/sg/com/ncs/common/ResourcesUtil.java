package sg.com.ncs.common;

import org.apache.commons.vfs2.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class ResourcesUtil {

    protected static final Logger log = LoggerFactory.getLogger(ResourcesUtil.class);

    public static List<Resource> getResource(String directory) throws FileSystemException {
        log.info("getResource from -> " + directory);

        ArrayList<Resource> resources = new ArrayList<Resource>();

        FileSystemManager fsManager = VFS.getManager();
        FileObject fo = fsManager.resolveFile(directory);
        if (fo.getType().compareTo(FileType.FOLDER) == 0) {

            FileObject[] children = fo.getChildren();

            log.info("no of children = " + children.length);
            for (int i=0; i<children.length; i++) {
                FileObject child = children[i];
                log.info("filename = " + child.getName() + " , type = " + child.getType().getName());

                Resource resource = new Resource(child.getName(), child.getType());
                resources.add(resource);
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
