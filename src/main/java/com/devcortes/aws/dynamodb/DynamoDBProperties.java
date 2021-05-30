package com.devcortes.aws.dynamodb;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Data
@ConfigurationProperties("dynamodb")
@Configuration
public class DynamoDBProperties {
    private String endpoint;
    private String region;
    private String table;
    private Duration itemExpirationTime;
    private int maxBatchSize;
}
