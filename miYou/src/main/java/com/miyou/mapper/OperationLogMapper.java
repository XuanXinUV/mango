package com.miyou.mapper;

import com.miyou.entity.OperationLog;
import org.springframework.stereotype.Repository;

@Repository
public interface OperationLogMapper {
    Integer insertOperationLog(OperationLog operationLog);
}
