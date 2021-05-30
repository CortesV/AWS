package com.devcortes.aws.kinesis;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.client.config.ClientOverrideConfiguration;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.kinesis.KinesisAsyncClient;
import software.amazon.awssdk.services.kinesis.KinesisClient;

import java.net.URI;

@Configuration
public class KinesisConfiguration {

    @Value("${cloud.aws.credentials.accessKey}")
    private String accessKey;
    @Value("${cloud.aws.credentials.secretKey}")
    private String secretKey;

    @Value("${kinesis.endpoint}")
    private String endpoint;

    @Bean
    public KinesisClient buildAmazonKinesis() {
        return KinesisClient.builder()
                .overrideConfiguration(ClientOverrideConfiguration.builder().build())
                .endpointOverride(URI.create(endpoint))
                .region(Region.US_WEST_2)
                .credentialsProvider(StaticCredentialsProvider.create(AwsBasicCredentials.create(accessKey, secretKey)))
                .build();
    }

    @Bean
    public KinesisAsyncClient buiKinesisAsyncClient() {
        return KinesisAsyncClient.builder()
                .overrideConfiguration(ClientOverrideConfiguration.builder().build())
                .endpointOverride(URI.create(endpoint))
                .region(Region.US_WEST_2)
                .credentialsProvider(StaticCredentialsProvider.create(AwsBasicCredentials.create(accessKey, secretKey)))
                .build();
    }
}
