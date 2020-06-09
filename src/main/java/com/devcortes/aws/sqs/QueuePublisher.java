package com.devcortes.aws.sqs;

import com.amazonaws.services.sqs.AmazonSQSAsync;
import com.amazonaws.services.sqs.model.MessageAttributeValue;
import com.amazonaws.services.sqs.model.SendMessageBatchRequest;
import com.amazonaws.services.sqs.model.SendMessageBatchRequestEntry;
import com.amazonaws.services.sqs.model.SendMessageBatchResult;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import com.amazonaws.services.sqs.model.SendMessageResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class QueuePublisher {

    private final AmazonSQSAsync amazonSQS;

    public void sendMessage(String message) {
        SendMessageResult sendMessageResult = amazonSQS.sendMessage(new SendMessageRequest(amazonSQS.getQueueUrl("custom-sqs").getQueueUrl(), message));
        log.info("Publish message: {}.", sendMessageResult.toString());
    }

    public void sendMessageToStandardQueue(String message) {
        SendMessageRequest sendMessageStandardQueue = new SendMessageRequest()
                .withQueueUrl(amazonSQS.getQueueUrl("custom-sqs").getQueueUrl())
                .withMessageBody(message);
        SendMessageResult sendMessageResult = amazonSQS.sendMessage(sendMessageStandardQueue);
        log.info("Publish message: {}.", sendMessageResult.toString());
    }

    public void sendMessageToFIFOQueue(String message) {
        SendMessageRequest sendMessageFifoQueue = new SendMessageRequest()
                .withQueueUrl(amazonSQS.getQueueUrl("custom-sqs").getQueueUrl())
                .withMessageBody(message)
                .withMessageGroupId("devcortes-group-1");
        SendMessageResult sendMessageResult = amazonSQS.sendMessage(sendMessageFifoQueue);
        log.info("Publish message: {}.", sendMessageResult.toString());
    }

    public void sendMessageWithDelay(String message) {
        Map<String, MessageAttributeValue> messageAttributes = new HashMap<>();
        messageAttributes.put("AttributeOne", new MessageAttributeValue()
                .withStringValue("This is an attribute")
                .withDataType("String"));
        SendMessageRequest sendMessageRequest = new SendMessageRequest()
                .withQueueUrl(amazonSQS.getQueueUrl("custom-sqs").getQueueUrl())
                .withDelaySeconds(15)
                .withMessageAttributes(messageAttributes)
                .withMessageBody(message);
        SendMessageResult sendMessageResult = amazonSQS.sendMessage(sendMessageRequest);
        log.info("Publish message: {}.", sendMessageResult.toString());
    }

    public void sendMultipleMessageToQueue(String ... message) {
        List<SendMessageBatchRequestEntry> messageEntries = new ArrayList<>();
        messageEntries.add(new SendMessageBatchRequestEntry()
                .withId("id-1")
                .withMessageBody("batch-1")
                .withMessageGroupId("devcortes-group-1"));
        messageEntries.add(new SendMessageBatchRequestEntry()
                .withId("id-2")
                .withMessageBody("batch-2")
                .withMessageGroupId("devcortes-group-1"));

        SendMessageBatchRequest sendMessageBatchRequest
                = new SendMessageBatchRequest(amazonSQS.getQueueUrl("custom-sqs").getQueueUrl(), messageEntries);
        SendMessageBatchResult sendMessageBatchResult = amazonSQS.sendMessageBatch(sendMessageBatchRequest);
        log.info("Publish batch: {}.", sendMessageBatchResult.toString());
    }
}
