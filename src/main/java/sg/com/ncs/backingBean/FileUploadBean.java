package sg.com.ncs.backingBean;

import org.apache.commons.io.FileUtils;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import sg.com.ncs.common.MessageUtil;
import sg.com.ncs.common.SystemProperties;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;


/**
 * Created with IntelliJ IDEA.
 * User: brian
 * Date: 20/6/13
 * Time: 12:47 PM
 * To change this template use File | Settings | File Templates.
 */
@Component
@Scope("request")
public class FileUploadBean {
    Logger log = LoggerFactory.getLogger(FileUploadBean.class);



    public void handleFileUpload(FileUploadEvent event) {
        log.info("handleFileUpload");

        try {
            UploadedFile file = event.getFile();
            log.info("filename = " + file.getFileName());
            moveToDeploymentFolder(file);
           // File dest = new File()
           // File tmpFile = moveToDeploymentFolder(new File(file));


            FacesMessage msg = new FacesMessage("Succesful", event.getFile().getFileName() + " is uploaded.");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } catch (Exception e) {

            FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR, "Deployment failed", e.getMessage()));
        }
    }





    private void moveToDeploymentFolder(UploadedFile sourceFile) throws IOException {
        //This is used for new file creation.
        String destTmpFolder = SystemProperties.getProperty("DEPLOYMENT_TMP_FOLDER");
        File f = new File(destTmpFolder, sourceFile.getFileName());
        FileOutputStream out = new FileOutputStream(f);

        InputStream in = sourceFile.getInputstream();

        int read = 0;
        byte[] bytes = new byte[1024];

        while ((read = in.read(bytes)) != -1) {
            out.write(bytes, 0, read);
        }

        in.close();
        out.flush();
        out.close();
    }
}
