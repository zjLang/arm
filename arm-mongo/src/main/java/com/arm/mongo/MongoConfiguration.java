package com.arm.mongo;

import org.springframework.boot.autoconfigure.mongo.MongoProperties;

/**
 * @author z-ewa
 */
public class MongoConfiguration {

    private MongoProperties mongoProperties;

    public MongoConfiguration(MongoProperties mongoProperties) {
        this.mongoProperties = mongoProperties;
    }


}
