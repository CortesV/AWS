package com.devcortes.aws.s3;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.CreateBucketRequest;
import software.amazon.awssdk.services.s3.model.CreateBucketResponse;

@Service
@RequiredArgsConstructor
public class S3Service {

    private final S3Client s3Client;

    public void createBucket() {
        CreateBucketResponse bucket = s3Client.createBucket(CreateBucketRequest.builder().bucket("test-bucket").build());
    }
}
