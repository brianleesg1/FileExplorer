package sg.com.ncs.fileexplorer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;


public class UploadItem implements Serializable {

    Logger log = LoggerFactory.getLogger(UploadItem.class);
    private MultipartFile file;
    private int chunk;
    private int chunks;

    public int getChunks() {
		return chunks;
	}

	public void setChunks(int chunks) {
		this.chunks = chunks;
	}

	public int getChunk() {
		return chunk;
	}

	public void setChunk(int chunk) {
		this.chunk = chunk;
	}

	

     public MultipartFile getFile() {
             return file;
     }

     public void setFile(MultipartFile file) {
             this.file = file;
     }

    public void printFileInfo() {
        log.info("file name = " + file.getName());
        log.info("file (original name) = " + file.getOriginalFilename());
        log.info("file size = " + file.getSize());
        log.info("file content type = " + file.getContentType());
        log.info("chunk = " + getChunk());
        log.info("chunks = " + getChunks());
    }
}
