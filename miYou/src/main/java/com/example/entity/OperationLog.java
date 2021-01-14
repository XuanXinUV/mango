package com.example.entity;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class OperationLog {
    private String id;
    private String operModel;
    private String request;
    private String response;
    private String userId;
    private String userName;
    private String url;
    private String ip;
    private Date createTime;
    private String type;
    private String remark;
}
