package com.miyou.miyouapp.mapper;

import com.miyou.miyouapp.entity.OperationLog;
import org.springframework.stereotype.Repository;

@Repository
public interface OperationLogMapper {
    Integer insertOperationLog(OperationLog operationLog);
}
