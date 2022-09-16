package com.arm.mongo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author z-ewa
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Curriculum implements Serializable {
    private String id;

    private String name;

    private double price;

    private int page;

}
