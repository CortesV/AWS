package com.devcortes.aws.dynamodb;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.model.AttributeDefinition;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.CreateTableResult;
import com.amazonaws.services.dynamodbv2.model.KeySchemaElement;
import com.amazonaws.services.dynamodbv2.model.KeyType;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.amazonaws.services.dynamodbv2.model.ScalarAttributeType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class DynamoDBService {

    private final AmazonDynamoDB amazonDynamoDB;

    public void createTable() {
        try {
            CreateTableRequest createTableRequest = new CreateTableRequest()
                    .withAttributeDefinitions(new AttributeDefinition("name", ScalarAttributeType.S))
                    .withKeySchema(new KeySchemaElement("name", KeyType.HASH))
                    .withProvisionedThroughput(new ProvisionedThroughput(10L, 10L))
                    .withTableName("student");
            CreateTableResult result = amazonDynamoDB.createTable(createTableRequest);
            log.info(result.getTableDescription().getTableName());
        } catch (AmazonServiceException e) {
            log.error(e.getErrorMessage());
        }
    }
}
