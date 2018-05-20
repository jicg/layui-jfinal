package com.jicg.admin;

import com.jfinal.upload.UploadFile;
import com.jicg.common.vo.Result;
import org.apache.commons.io.FileUtils;

import java.io.File;

/**
 * Created by jicg on 2018/4/10.
 */
public class UploadController extends BaseController {

    private String pathimg = "upload/image/";
    private String pathvideo = "upload/video/";
    private String pathaudio = "upload/audio/";

    public void img() {
        upload(pathimg);
    }


    private void upload(String path) {
        UploadFile file = getFile();
        String filename = file.getFileName();
        String filename2 = System.currentTimeMillis() + "-" + filename;
        String dest = getSession().getServletContext().getRealPath(path);
        try {
            File destFile = new File(dest);
            if (!destFile.exists()) {
                destFile.mkdirs();
            }
            FileUtils.copyFile(file.getFile(), new File(destFile.getAbsolutePath() + "/" + filename2));
            FileUtils.forceDelete(file.getFile());
            renderJson(new Result(Result.OK_CODE, "上传成功", new UpdateRetData("/" + path + filename2)));
        } catch (Exception e) {
            e.printStackTrace();
            renderJson(new Result(Result.ERROR_CODE, "上传失败：" + e.getMessage()));

        }
    }

    public void video() {
        upload(pathvideo);
    }

    public void audio() {
        upload(pathaudio);
    }

    public class UpdateRetData {
        private String src;

        public UpdateRetData(String src) {
            this.src = src;
        }

        public String getSrc() {
            return src;
        }

        public void setSrc(String src) {
            this.src = src;
        }
    }
}
