package com.devcortes.aws.sqs;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import com.amazonaws.services.sqs.model.SendMessageResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class SpringQueuePublisher {

    @Value("${localstack.sqs.url}")
    private String serviceEndpoint;

    @Qualifier("springAmazonSQS")
    private final AmazonSQS springAmazonSQS;

    public void sendMessage(String message) {
        SendMessageResult sendMessageResult = springAmazonSQS.sendMessage(new SendMessageRequest(springAmazonSQS.getQueueUrl("spring-sqs").getQueueUrl(), message));
        log.info("Publish message: {}.", sendMessageResult.toString());
    }
}
