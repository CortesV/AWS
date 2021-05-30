package com.devcortes.aws.dynamodb;

import com.devcortes.aws.dynamodb.model.Customer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.enhanced.dynamodb.model.CreateTableEnhancedRequest;
import software.amazon.awssdk.enhanced.dynamodb.model.EnhancedGlobalSecondaryIndex;
import software.amazon.awssdk.enhanced.dynamodb.model.EnhancedLocalSecondaryIndex;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.AttributeDefinition;
import software.amazon.awssdk.services.dynamodb.model.BillingMode;
import software.amazon.awssdk.services.dynamodb.model.CreateTableRequest;
import software.amazon.awssdk.services.dynamodb.model.CreateTableResponse;
import software.amazon.awssdk.services.dynamodb.model.GlobalSecondaryIndex;
import software.amazon.awssdk.services.dynamodb.model.KeySchemaElement;
import software.amazon.awssdk.services.dynamodb.model.KeyType;
import software.amazon.awssdk.services.dynamodb.model.Projection;
import software.amazon.awssdk.services.dynamodb.model.ProjectionType;
import software.amazon.awssdk.services.dynamodb.model.ProvisionedThroughput;
import software.amazon.awssdk.services.dynamodb.model.ScalarAttributeType;

@Slf4j
@Service
@RequiredArgsConstructor
public class DynamoDBService {

    private final DynamoDbEnhancedClient dynamoDbEnhancedClient;
    private final DynamoDbClient dynamoDbClient;

    public void createTableWithBean() {
        CreateTableEnhancedRequest request = CreateTableEnhancedRequest.builder()
                .provisionedThroughput(ProvisionedThroughput.builder().readCapacityUnits(10L).writeCapacityUnits(10L).build())
                .globalSecondaryIndices(
                        EnhancedGlobalSecondaryIndex.builder()
                                .indexName("customers_by_name")
                                .provisionedThroughput(ProvisionedThroughput.builder().readCapacityUnits(5L).writeCapacityUnits(5L).build())
                                .projection(Projection.builder().projectionType(ProjectionType.ALL).build())
                                .build())
                .localSecondaryIndices(
                        EnhancedLocalSecondaryIndex.builder()
                                .indexName("customers_by_date")
                                .projection(Projection.builder().projectionType(ProjectionType.ALL).build())
                                .build())
                .build();
        dynamoDbEnhancedClient.table("customer", TableSchema.fromClass(Customer.class)).createTable(request);

        boolean customer = dynamoDbClient.listTables().tableNames().stream().anyMatch(name -> name.equals("customer"));

        log.info("Customer table exists - {}.", customer);
    }

    public void createTableWithoutBean() {
        CreateTableRequest req = CreateTableRequest.builder()
                .tableName("user")
                .attributeDefinitions(
                        AttributeDefinition.builder()
                                .attributeName("id")
                                .attributeType(ScalarAttributeType.S)
                                .build(),
                        AttributeDefinition.builder()
                                .attributeName("email")
                                .attributeType(ScalarAttributeType.S)
                                .build()
                )
                .keySchema(
                        KeySchemaElement.builder()
                                .attributeName("id")
                                .keyType(KeyType.HASH)
                                .build()
                )
                .globalSecondaryIndexes(
                        GlobalSecondaryIndex.builder()
                                .indexName("GSI_EMAIL")
                                .keySchema(
                                        KeySchemaElement.builder()
                                                .attributeName("email")
                                                .keyType(KeyType.HASH)
                                                .build())
                                .provisionedThroughput(ProvisionedThroughput.builder().readCapacityUnits(5L).writeCapacityUnits(5L).build())
                                .projection(Projection.builder().projectionType(ProjectionType.ALL).build())
                                .build())
                .provisionedThroughput(ProvisionedThroughput.builder().readCapacityUnits(5L).writeCapacityUnits(5L).build())
                .billingMode(BillingMode.PAY_PER_REQUEST)
                .build();

        CreateTableResponse table = dynamoDbClient.createTable(req);

        boolean user = dynamoDbClient.listTables().tableNames().stream().anyMatch(name -> name.equals("customer"));

        log.info("User table exists - {}.", user);
    }
}
