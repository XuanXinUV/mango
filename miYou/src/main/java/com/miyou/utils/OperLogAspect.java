package com.miyou.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSON;
import com.miyou.entity.OperationLog;
import com.miyou.mapper.OperationLogMapper;
import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

/**
 * 切面处理类，操作日志异常日志记录处理
 */
@Aspect
@Component
public class OperLogAspect {

    public static final String UNKNOWN_IP = "unknown";

    private Logger logger = LoggerFactory.getLogger(OperLogAspect.class);

    @Autowired
    public OperationLogMapper mapper;

    @Pointcut("@annotation(com.miyou.utils.OperLog)")
    public void operLogPoinCut() {
    }

    @Pointcut("execution(* com.miyou.controller..*.*(..))")
    public void operExceptionLogPoinCut() {
    }

    /**
     * 正常返回通知，拦截用户操作日志，连接点正常执行完成后执行， 如果连接点抛出异常，则不会执行
     *
     * @param joinPoint 切入点
     * @param keys      返回结果
     */
    @AfterReturning(value = "operLogPoinCut()", returning = "keys")
    public void saveOperLog(JoinPoint joinPoint, Object keys) {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = (HttpServletRequest) requestAttributes
                .resolveReference(RequestAttributes.REFERENCE_REQUEST);

        try {
            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            Method method = signature.getMethod();
            OperLog opLog = method.getAnnotation(OperLog.class);
            String operModel = "";
            String operType = "";
            String operDesc = "";
            if (opLog != null) {
                operModel = opLog.operModel();
                operType = opLog.operType();
                operDesc = opLog.operDesc();
            }
            String className = joinPoint.getTarget().getClass().getName();
            String methodName = method.getName();
            methodName = className + "." + methodName;
            Map<String, String> rtnMap = converMap(request.getParameterMap());
            OperationLog operlog = OperationLog.builder()
                    .id(UUID.randomUUID().toString())
                    .operModel(operModel)
                    .request(JSON.toJSONString(rtnMap))
                    .response(JSON.toJSONString(keys))
                    .userId("userId")
                    .userName("userName")
                    .url(request.getRequestURI() + methodName)
                    .ip(getIpAddress(request))
                    .createTime(new Date())
                    .type(operType)
                    .remark(operDesc)
                    .build();
            mapper.insertOperationLog(operlog);
        } catch (Exception e) {
            logger.error("saveOperLog Exception!", e);
        }
    }

    /**
     * 异常返回通知，用于拦截异常日志信息 连接点抛出异常后执行
     *
     * @param joinPoint 切入点
     * @param e         异常信息
     */
    @AfterThrowing(pointcut = "operExceptionLogPoinCut()", throwing = "e")
    public void saveExceptionLog(JoinPoint joinPoint, Throwable e) {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = (HttpServletRequest) requestAttributes
                .resolveReference(RequestAttributes.REFERENCE_REQUEST);
        try {
            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            Method method = signature.getMethod();
            OperLog opLog = method.getAnnotation(OperLog.class);
            String operModel = "";
            String operType = "";
            String operDesc = "";
            if (opLog != null) {
                operModel = opLog.operModel();
                operType = opLog.operType();
                operDesc = opLog.operDesc();
            }
            String className = joinPoint.getTarget().getClass().getName();
            String methodName = method.getName();
            methodName = className + "." + methodName;
            Map<String, String> rtnMap = converMap(request.getParameterMap());
            OperationLog operlog = OperationLog.builder()
                    .id(UUID.randomUUID().toString())
                    .operModel(operModel)
                    .request(JSON.toJSONString(rtnMap))
                    .response(e.getClass().getName())
                    .userId("userId")
                    .userName("userName")
                    .url(request.getRequestURI() + methodName)
                    .ip(getIpAddress(request))
                    .createTime(new Date())
                    .type(e.getClass().getName())
                    .remark(stackTraceToString(e.getClass().getName(), e.getMessage(), e.getStackTrace()))
                    .build();
            mapper.insertOperationLog(operlog);
        } catch (Exception e2) {
            logger.error("saveExceptionLog Exception!", e);
        }
    }

    /**
     * 转换request 请求参数
     *
     * @param paramMap request获取的参数数组
     */
    public Map<String, String> converMap(Map<String, String[]> paramMap) {
        Map<String, String> rtnMap = new HashMap<String, String>();
        for (String key : paramMap.keySet()) {
            rtnMap.put(key, paramMap.get(key)[0]);
        }
        return rtnMap;
    }

    /**
     * 转换异常信息为字符串
     *
     * @param exceptionName    异常名称
     * @param exceptionMessage 异常信息
     * @param elements         堆栈信息
     */
    public String stackTraceToString(String exceptionName, String exceptionMessage, StackTraceElement[] elements) {
        StringBuffer strbuff = new StringBuffer();
        for (StackTraceElement stet : elements) {
            strbuff.append(stet + "\n");
        }
        String message = exceptionName + ":" + exceptionMessage + "\n\t" + strbuff.toString();
        return message;
    }

    public static String getIpAddress(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || UNKNOWN_IP.equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || UNKNOWN_IP.equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || UNKNOWN_IP.equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || UNKNOWN_IP.equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || UNKNOWN_IP.equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        if (StringUtils.isNotBlank(ip)) {
            return ip.split(",")[0];
        }
        return ip;
    }


    private String getPostData(HttpServletRequest req) throws IOException {
        BufferedReader bufferReaderBody = null;
        try {
            bufferReaderBody = new BufferedReader(req.getReader());
            String postData = bufferReaderBody.readLine();
            return postData;
        } catch (IOException e) {
            throw e;
        } finally {
            if (bufferReaderBody != null) {
                bufferReaderBody.close();
            }
        }
    }

}