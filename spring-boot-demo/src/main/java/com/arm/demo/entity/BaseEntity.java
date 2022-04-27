package com.arm.demo.entity;

import lombok.Getter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;

@MappedSuperclass // 指定该类的字段应用于继承子类，本身不生成单独的表
@Getter

public class BaseEntity implements Serializable {
    @Id
    @Column(name = "id", nullable = false, length = 64)
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "com.arm.demo.UuidGenerator")
    protected String id;

    public void setId(String id) {
        this.id = id;
        /*if (this.id == null) {
            this.id = id;
        }*/
    }
}
