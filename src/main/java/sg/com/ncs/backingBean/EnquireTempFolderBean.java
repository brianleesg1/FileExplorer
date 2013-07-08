package sg.com.ncs.backingBean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import sg.com.ncs.common.Resource;
import sg.com.ncs.common.ResourcesUtil;
import sg.com.ncs.common.SystemProperties;

import java.util.ArrayList;
import java.util.List;


/**
 * Created with IntelliJ IDEA.
 * User: brian
 * Date: 5/7/13
 * Time: 4:21 PM
 * To change this template use File | Settings | File Templates.
 */
@Component
@Scope("request")
public class EnquireTempFolderBean {

    private String upload_file_tmp_root = SystemProperties.getProperty("UPLOAD_FILE_TMP_ROOT");

    Logger log = LoggerFactory.getLogger(EnquireTempFolderBean.class);
    private String submissionNo;

    public String getSubmissionNo() {
        return submissionNo;
    }

    public void setSubmissionNo(String submissionNo) {
        this.submissionNo = submissionNo;
    }

    public List<String> complete(String query) throws Exception {
        log.info("query = " + query);

        ArrayList<String> results = new ArrayList<String>();

        List<Resource> resources = ResourcesUtil.getResource(upload_file_tmp_root, true);

        for (Resource res : resources) {
            log.info(res.getName());
            if (res.getName().startsWith(query)) {
                results.add(res.getName());
            }
        }
        return results;
    }

}