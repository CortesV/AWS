package com.devcortes.aws;

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
}
