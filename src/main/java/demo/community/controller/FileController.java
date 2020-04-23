package demo.community.controller;

import demo.community.dto.FileDTO;
import demo.community.provider.QCloudProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;

@Controller
public class FileController {

    @Autowired
    private QCloudProvider qCloudProvider;

//    @PostMapping("/file/upload")
//    @ResponseBody
//    public FileDTO upload(HttpServletRequest request){
//        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
//        MultipartFile file = multipartRequest.getFile("editormd-image-file");
//        String fileName = file.getOriginalFilename();
//        String res =  qCloudProvider.upload(fileName);
//        FileDTO fileDTO = new FileDTO();
//        fileDTO.setSuccess(1);
//        fileDTO.setMessage("upload success!");
//        fileDTO.setUrl(res);
//        return fileDTO;
//    }
}
