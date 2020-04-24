package demo.community.provider;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.model.ObjectMetadata;
import com.qcloud.cos.model.PutObjectResult;
import com.qcloud.cos.region.Region;
import demo.community.exception.CustomizeErrorCode;
import demo.community.exception.CustomizeException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Date;
import java.util.UUID;


@Service
@Slf4j
public class QCloudProvider {
    @Value("${qcloud.secret.id}")
    private String secretId;

    @Value("${qcloud.secret.key}")
    private String secretKey;

    public String upload(MultipartFile file){
        COSCredentials cred = new BasicCOSCredentials(secretId, secretKey);
        Region region = new Region("ap-beijing");
        String bucketName = "community-1259454386";
        ClientConfig clientConfig = new ClientConfig(region);
        COSClient cosClient = new COSClient(cred, clientConfig);
        InputStream inputStream = null;
        try {
            inputStream = file.getInputStream();
        } catch (IOException e) {
            log.error("getInputStream error",e);
            throw new  CustomizeException(CustomizeErrorCode.FILE_UPLOAD_FAIL);
        }

        String fileName = file.getOriginalFilename();
        String key = "";
        String[] filePaths = fileName.split("\\.");
        if(filePaths.length > 1){
            key = UUID.randomUUID().toString() + "." + filePaths[filePaths.length - 1];
        }else{
            log.error("get filePath error,{}",filePaths);
            throw new  CustomizeException(CustomizeErrorCode.FILE_UPLOAD_FAIL);
        }
        try {
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentType(file.getContentType());
            objectMetadata.setContentLength(file.getSize());
            PutObjectResult putObjectResult = cosClient.putObject(bucketName, key, inputStream, objectMetadata);
            Date expiration = new Date(System.currentTimeMillis() + 3600L * 1000 * 24 * 365*10 );
            URL url = cosClient.generatePresignedUrl(bucketName, key, expiration);
            if(url != null){
                return url.toString();
            }else{
                log.error("get url error");
                throw new  CustomizeException(CustomizeErrorCode.FILE_UPLOAD_FAIL);
            }
        }catch (Exception e){
            log.error("upload error",e);
            throw new  CustomizeException(CustomizeErrorCode.FILE_UPLOAD_FAIL);
        }finally {
            cosClient.shutdown();
        }
    }
}
