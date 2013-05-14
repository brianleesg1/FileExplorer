package sg.com.ncs.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class JSNode {

    private static final String OPENED = "opened";
    private static final String CLOSED = "closed";

    private static final String DRIVE = "drive";
    private static final String FOLDER = "folder";
    private static final String PDF = "pdf";
    private static final String JPG = "jpg";
    private static final String PNG = "png";
    private static final String DOC = "doc";
    private static final String TXT = "txt";
    private static final String ZIP = "zip";

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

    public void setAsDrive() {
        this.addAttribute("rel", DRIVE);
    }

    public void setAsFolder() {
        this.addAttribute("rel", FOLDER);
    }

    public void setAsPDF() {
        this.addAttribute("rel", PDF);
    }
    public void setAsJPG() {
        this.addAttribute("rel", JPG);
    }
    public void setAsPNG() {
        this.addAttribute("rel", PNG);
    }
    public void setAsDOC() {
        this.addAttribute("rel", DOC);
    }
    public void setAsTXT() {
        this.addAttribute("rel", TXT);
    }

    public void setID(String id) {
        this.addAttribute("id", id);
    }

    public void setAsZip() {
        this.addAttribute("rel",ZIP);
    }
}
