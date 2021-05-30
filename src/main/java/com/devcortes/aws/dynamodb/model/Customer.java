package com.devcortes.aws.dynamodb.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Setter;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSecondaryPartitionKey;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSecondarySortKey;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSortKey;

import java.time.Instant;

@Setter
@Builder(toBuilder = true)
@DynamoDbBean
@NoArgsConstructor
@AllArgsConstructor
public class Customer {
    private String accountId;
    private int subId;            // primitive types are supported
    private String name;
    private Instant createdDate;

    @DynamoDbPartitionKey
    public String getAccountId() {
        return this.accountId;
    }

    @DynamoDbSortKey
    public int getSubId() {
        return this.subId;
    }

    // Defines a GSI (customers_by_name) with a partition key of 'name'
    @DynamoDbSecondaryPartitionKey(indexNames = "customers_by_name")
    public String getName() {
        return this.name;
    }

    // Defines an LSI (customers_by_date) with a sort key of 'createdDate' and also declares the
    // same attribute as a sort key for the GSI named 'customers_by_name'
    @DynamoDbSecondarySortKey(indexNames = {"customers_by_date", "customers_by_name"})
    public Instant getCreatedDate() {
        return this.createdDate;
    }
}
