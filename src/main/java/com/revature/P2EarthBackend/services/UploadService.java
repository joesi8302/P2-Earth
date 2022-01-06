package com.revature.P2EarthBackend.services;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.amazonaws.services.s3.transfer.TransferManagerBuilder;
import com.amazonaws.services.s3.transfer.Upload;
import com.google.common.io.Files;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
public class UploadService {
    private final String awsID = "";  //going to have to switch to environment variable when implementing
    private final String secretKey = "";
    private final String region = "us-west-1";
    private final String bucketName = "hello-s3-js";

    BasicAWSCredentials awsCredentials = new BasicAWSCredentials(awsID, secretKey);

    AmazonS3 s3Client = AmazonS3ClientBuilder
            .standard()
            .withRegion(Regions.fromName(region))
            .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
            .build();


    public String uploadFile(MultipartFile file, String name) throws IOException {
        //bucket location, URI for the file in the location, file to send

        ObjectMetadata data = new ObjectMetadata();
        data.setContentType(file.getContentType());
        data.setContentLength(file.getSize());

        s3Client.putObject(bucketName, "project/" + name, file.getInputStream(), data);
        String fileType = file.getName().substring(file.getName().lastIndexOf(".") + 1);
        String url = "https://hello-s3-js.s3.us-west-1.amazonaws.com/project/"+ name;
        return url;
    }


}
