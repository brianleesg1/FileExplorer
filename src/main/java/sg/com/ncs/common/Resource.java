package sg.com.ncs.common;

import org.apache.commons.vfs2.FileName;
import org.apache.commons.vfs2.FileType;
import sg.com.ncs.common.ResourcesUtil;


public class Resource {

    private FileName filename;
    private FileType type;
    private String id;


    public Resource(FileName filename, FileType type) {
        this.filename = filename;
        this.type = type;

        this.id = ResourcesUtil.encode(filename.toString());

    }

    public String getName() {
        return filename.getBaseName();
    }

    public FileType getType() {
        return type;
    }

    public String getId() {
        return id;
    }

    public String getExtension() {
        return this.filename.getExtension();
    }


}
