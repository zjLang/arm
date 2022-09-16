package com.arm.mongo.entity;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

/**
 * @author z-ewa
 */
@Data
@Document(collection = "user")
public class UserCopy {
    private String id;

    private String name;

    private int age;

    private List<Curriculum> curriculums;
}
