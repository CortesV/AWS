package com.devcortes.aws;

import com.devcortes.aws.dynamodb.DynamoDBService;
import com.devcortes.aws.sqs.QueuePublisher;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AwsApplicationTests {

    @Autowired
    private QueuePublisher queuePublisher;
    @Autowired
    private DynamoDBService dynamoDBService;

    @Test
    void testQueuePublisher() {
        queuePublisher.sendMessage("test queue message");
    }

    @Test
    void testDynamoDBTableCreation() {
        dynamoDBService.createTable();
    }

}
