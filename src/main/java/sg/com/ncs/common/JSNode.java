package sg.com.ncs.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class JSNode {

    Logger log = LoggerFactory.getLogger(JSNode.class);

    private static final String OPENED = "opened";
    private static final String CLOSED = "closed";

    public static enum FileTypes {DRIVE,FOLDER,PDF,JPG,PNG,DOC,TXT,ZIP};

    private String data;

    private HashMap<String,Object> attr = new HashMap<String,Object>();

    private String state = CLOSED; //default

    private ArrayList<JSNode> children = new ArrayList<JSNode>();

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void addAttribute(String attribute, String value) {

        attr.put(attribute, value);
    }

    public HashMap<String,Object> getAttr() {
        return attr;
    }

    public ArrayList<JSNode> getChildren() {
        return children;
    }

    public void addChild(JSNode child) {
        children.add(child);
    }

    public void addChildren(List<JSNode> children) {
        this.children.addAll(children);
    }

    public void opened() {
        this.state = OPENED;
    }

    public void closed() {
        this.state = CLOSED;
    }

    public void setFileType(String filetype) {

        if (!StringUtil.isNullOrEmpty(filetype)) {

            try {
                switch (FileTypes.valueOf(filetype.toUpperCase())) {
                    case DRIVE : {
                        this.addAttribute("rel",FileTypes.DRIVE.name());
                        break;
                    }
                    case FOLDER : {
                        this.addAttribute("rel",FileTypes.FOLDER.name());
                        break;
                    }
                    case PDF : {
                        this.addAttribute("rel",FileTypes.PDF.name());
                        break;
                    }
                    case JPG : {
                        this.addAttribute("rel",FileTypes.JPG.name());
                        break;
                    }
                    case PNG : {
                        this.addAttribute("rel",FileTypes.PNG.name());
                        break;
                    }
                    case DOC : {
                        this.addAttribute("rel",FileTypes.DOC.name());
                        break;
                    }
                    case TXT : {
                        this.addAttribute("rel",FileTypes.TXT.name());
                        break;
                    }
                    case ZIP : {
                        this.addAttribute("rel",FileTypes.ZIP.name());
                        break;
                    }
                }
            } catch (IllegalArgumentException iae) {
                //log.info("no FileType constant for " + filetype);
            }
        }

    }




    public void setID(String id) {
        this.addAttribute("id", id);
    }


}
