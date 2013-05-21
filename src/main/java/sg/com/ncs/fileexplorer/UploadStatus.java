package sg.com.ncs.fileexplorer;

/**
 * Created with IntelliJ IDEA.
 * User: brian
 * Date: 10/5/13
 * Time: 8:39 PM
 * To change this template use File | Settings | File Templates.
 */
public class UploadStatus {

    private String status;
    private String reason;

    public UploadStatus(String status, String reason) {
        this.status = status;
        this.reason = reason;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
