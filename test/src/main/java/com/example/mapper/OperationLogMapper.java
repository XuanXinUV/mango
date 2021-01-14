package com.example.mapper;

import com.example.entity.OperationLog;
import org.springframework.stereotype.Repository;

@Repository
public interface OperationLogMapper {
    Integer insertOperationLog(OperationLog operationLog);
}
