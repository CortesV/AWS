package com.devcortes.aws.sqs;

import com.amazonaws.services.sqs.AmazonSQSAsync;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import com.amazonaws.services.sqs.model.SendMessageResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class QueuePublisher {

    private final AmazonSQSAsync amazonSQS;

    public void sendMessage(String message) {
        SendMessageResult sendMessageResult = amazonSQS.sendMessage(new SendMessageRequest(amazonSQS.getQueueUrl("custom-sqs").getQueueUrl(), message));
        log.info("Publish message: {}.", sendMessageResult.toString());
    }
}
