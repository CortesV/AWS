package com.devcortes.aws;

import com.devcortes.aws.dynamodb.DynamoDBService;
import com.devcortes.aws.s3.S3Service;
import com.devcortes.aws.sqs.QueuePublisher;
import com.devcortes.aws.sqs.SpringQueuePublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class AWSRestController {
    private final QueuePublisher queuePublisher;
    private final SpringQueuePublisher springQueuePublisher;
    private final DynamoDBService dynamoDBService;
    private final S3Service s3Service;

    @GetMapping("/sqs/{message}")
    public ResponseEntity<?> pushEvent(@PathVariable("message") String message) {
        queuePublisher.sendMessage(message);
        queuePublisher.sendMessageToStandardQueue(message);
        queuePublisher.sendMessageToFIFOQueue(message);
        queuePublisher.sendMessageWithDelay("test queue message with delay");
        queuePublisher.sendMultipleMessageToQueue(message);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/sqs/v1/{message}")
    public ResponseEntity<?> springPushEvent(@PathVariable("message") String message) {
        springQueuePublisher.sendMessage(message);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/dynamodb")
    public ResponseEntity<?> dynamoDB() {
        dynamoDBService.createTableWithBean();
        dynamoDBService.createTableWithoutBean();
        return ResponseEntity.ok().build();
    }

    @GetMapping("/s3")
    public ResponseEntity<?> s3() {
        s3Service.createBucket();
        return ResponseEntity.ok().build();
    }
}
