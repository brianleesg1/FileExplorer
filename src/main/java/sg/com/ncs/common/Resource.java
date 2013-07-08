package sg.com.ncs.common;

import org.apache.commons.vfs2.FileName;
import org.apache.commons.vfs2.FileType;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;


public class Resource implements Serializable{

    //private FileName filename;
    private String fullPathName;
    private String name;
    private long size;
    private Date lastModifiedTime;

    private FileType type;
    private String id;
    private String extension;
    private String path;


    public Resource(FileName filename, FileType type, long size, long lastModifiedTime) {
        //this.filename = filename;
        this.type = type;
        this.fullPathName = filename.toString();
        this.name = filename.getBaseName();
        //this.id = ResourcesUtil.encode(filename.toString());
        this.id = SystemUtil.getSystemId();
        this.extension = filename.getExtension();
        this.path = filename.getPath();

        this.size = size;
        this.lastModifiedTime = new Date(lastModifiedTime);

    }

    public String getName() {
        return this.name;
    }

    public String getFullPathName() {
        return this.fullPathName;
    }

    public FileType getType() {
        return type;
    }

    public String getId() {
        return id;
    }

    public String getExtension() {
        return this.extension;
    }

    public String getPath() {
        return this.path;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public Date getLastModifiedTime() {
        return lastModifiedTime;
    }

    public void setLastModifiedTime(Date lastModifiedTime) {
        this.lastModifiedTime = lastModifiedTime;
    }
}
