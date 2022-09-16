package com.arm.mongo;

import com.arm.mongo.entity.User;
import com.arm.mongo.entity.UserCopy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author z-ewa
 */
@Component
public class MongoService {
    @Autowired
    private MongoTemplate template;

    public void insert(User user) {
        template.insert(user);
    }

    public UserCopy findOne() {
        return template.findOne(Query.query(Criteria.where("age").is(18)), UserCopy.class);
    }


    public List<UserCopy> findAll() {
        return template.find(new Query(), UserCopy.class);
    }
}
