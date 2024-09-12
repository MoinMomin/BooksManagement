package com.management.bookstore.mapper;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

public class CustomResponse {
    public static <T> ResponseEntity<Map> created(T data){
        MetaResponse metaInfo = getMeta("SUCCESS", 201, false);
        //Prepare response
        Map<String, Object> map = new HashMap<>();
        map.put("meta", metaInfo);
        map.put("data", data);
        return new ResponseEntity<>(map, HttpStatus.CREATED);
    }
    public static <T>ResponseEntity<Map> ok(T data){
        //Prepare meta info
        MetaResponse metaInfo = getMeta("SUCCESS", 200, false);
        //Prepare response
        Map<String, Object> map = new HashMap<>();
        map.put("meta", metaInfo);
        map.put("data", data);
        return ResponseEntity.ok().body(map);
    }
    public static MetaResponse getMeta(String msg, int statusCode, boolean isError) {
        MetaResponse metaInfo = new MetaResponse();
        metaInfo.setMsg(msg);
        metaInfo.setStatusCode(statusCode);
        metaInfo.setError(isError);
        return metaInfo;
    }
}
