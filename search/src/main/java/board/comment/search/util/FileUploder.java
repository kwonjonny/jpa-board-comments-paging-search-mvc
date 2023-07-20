package board.comment.search.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.log4j.Log4j2;
import net.coobird.thumbnailator.Thumbnailator;

@Log4j2
@Component
public class FileUploder {

    // Custom Exception
    public static class UploadException extends RuntimeException {
        public UploadException(String msg) {
            super(msg);
        }
    }

    @Value("${org.zerock.upload.path}")
    private String path;

    public List<String> uploadFiles(List<MultipartFile> files, boolean makeThumbnail) {

        if (files == null || files.size() == 0) {
            throw new UploadException("No File");
        }

        List<String> uploadFileNames = new ArrayList<>();

        log.info("path: " + path);

        log.info(files);

        // loop
        for (MultipartFile mFile : files) {

            String originalFileName = mFile.getOriginalFilename();
            String uuid = UUID.randomUUID().toString();

            String saveFileName = uuid + "_" + originalFileName;

            File saveFile = new File(path, saveFileName);

            try (InputStream in = mFile.getInputStream();
                    OutputStream out = new FileOutputStream(saveFile);) {

                FileCopyUtils.copy(in, out);

                // 썸네일 필요하다면
                if (makeThumbnail) {
                    File thumbOutFile = new File(path, "s_" + saveFileName);

                    Thumbnailator.createThumbnail(saveFile, thumbOutFile, 200, 200);

                }

                uploadFileNames.add(saveFileName);

            } catch (Exception e) {
                throw new UploadException("Upload Fail:" + e.getMessage());
            }

        }

        return uploadFileNames;
    }

    public void removeFiles(List<String> fileNames) {

        if (fileNames == null || fileNames.size() == 0) {
            throw new UploadException("no file");
        }

        // lamda 예외 때문에 못씀
        for (String fname : fileNames) {
            File orginal = new File(path, fname);
            File thum = new File(path, "s_" + fname);
            // 썸네일이 있으면 삭제
            if (thum.exists()) {
                thum.delete();
            }
            // 원본 파일 삭제
            orginal.delete();

        }
    }

}