package sg.com.ncs.common;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.multipart.MultipartFile;
import sg.com.ncs.fileexplorer.UploadItem;

public class UploadValidator implements Validator {
    protected final Log log = LogFactory.getLog(UploadValidator.class);

    @Override
    public boolean supports(final Class<?> clazz) {
        return UploadItem.class.equals(clazz);
    }

    @Override
    public void validate(final Object target, final Errors errors) {
        final UploadItem uploadItem = (UploadItem) target;
        final MultipartFile multipartFile = uploadItem.getFile();

        log.info("filename = " + multipartFile.getOriginalFilename());
        log.info("file size = " + multipartFile.getSize());

        int max_file_size = Integer.parseInt(SystemProperties.getProperty("MAX_UPLOAD_FILE_SIZE"));

        if (multipartFile.getSize() > max_file_size) {
            errors.reject("error", "file size exceed " + (max_file_size/1024)/1024 + " MB");
            return;
        }
    }

}
