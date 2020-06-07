package com.devcortes.aws.sqs;

import com.amazonaws.services.sqs.AmazonSQSAsync;
import com.amazonaws.services.sqs.model.Message;
import com.amazonaws.services.sqs.model.ReceiveMessageRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class QueueListener {

    private final AmazonSQSAsync amazonSQS;

    @EventListener(ContextRefreshedEvent.class)
    public void receiveMessage() {
        while (true) {
            final ReceiveMessageRequest receiveMessageRequest =
                    new ReceiveMessageRequest(amazonSQS.getQueueUrl("custom-sqs").getQueueUrl())
                            .withMaxNumberOfMessages(1)
                            .withWaitTimeSeconds(3);
            List<Message> messages = amazonSQS.receiveMessage(receiveMessageRequest).getMessages();
            for (Message message : messages) {
                log.info("Receive message: {}.", message.getBody());
                amazonSQS.deleteMessage(amazonSQS.getQueueUrl("custom-sqs").getQueueUrl(), message.getReceiptHandle());
                log.info("Delete message: {}.", message.toString());
            }
        }
    }
}
