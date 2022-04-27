package com.arm.demo;

import com.arm.demo.entity.BaseEntity;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;
import org.springframework.util.StringUtils;

import java.io.Serializable;

public class UuidGenerator implements IdentifierGenerator {
    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object object) throws HibernateException {
        // 解决预先设置的id值还是被 重新生成的id 替换的问题。
        BaseEntity entity = (BaseEntity) object;
        if (!StringUtils.isEmpty(entity.getId())) {
            return entity.getId();
        }
        return Util.getUUID();
    }
}
