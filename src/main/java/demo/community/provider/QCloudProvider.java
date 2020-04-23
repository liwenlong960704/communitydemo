package demo.community.provider;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.model.PutObjectResult;
import com.qcloud.cos.region.Region;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.UUID;


@Service
public class QCloudProvider {
    @Value("${qcloud.secret.id}")
    private String secretId;

    @Value("${qcloud.secret.key}")
    private String secretKey;

//    public String upload(String fileName){
//        COSCredentials cred = new BasicCOSCredentials(secretId, secretKey);
//        Region region = new Region("ap-beijing");
//        ClientConfig clientConfig = new ClientConfig(region);
//        COSClient cosClient = new COSClient(cred, clientConfig);
//
//        File localFile = new File(fileName);
//        String bucketName = "community-1259454386";
//        String key = "";
//        String[] filePaths = fileName.split("\\.");
//        if(filePaths.length > 1){
//            key = UUID.randomUUID().toString() + "." + filePaths[filePaths.length - 1];
//        }
//        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, key, localFile);
//        PutObjectResult putObjectResult = cosClient.putObject(putObjectRequest);
//
//        return key;
//    }
}
