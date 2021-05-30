package com.devcortes.aws.s3;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3AsyncClient;
import software.amazon.awssdk.services.s3.S3Client;

import java.net.URI;

@Configuration
public class S3Configuration {

    @Value("${cloud.aws.credentials.accessKey}")
    private String accessKey;
    @Value("${cloud.aws.credentials.secretKey}")
    private String secretKey;

    @Value("${s3.endpoint}")
    private String endpoint;

    @Bean
    public S3Client s3Client() {
        return S3Client.builder()
                .region(Region.US_WEST_2)
                .credentialsProvider(StaticCredentialsProvider.create(AwsBasicCredentials.create(accessKey, secretKey)))
                .endpointOverride(URI.create(endpoint))
                .build();
    }

    @Bean
    public S3AsyncClient s3AsyncClient() {
        return S3AsyncClient.builder().region(Region.US_WEST_2).build();
    }
}
