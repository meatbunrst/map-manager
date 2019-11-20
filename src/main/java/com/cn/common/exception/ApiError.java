package com.cn.common.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author zhangheng
 * @date 2018-11-23
 */
@Data
class ApiError {

    private Integer code;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime timestamp;
    private String msg;

    private ApiError() {
        timestamp = LocalDateTime.now();
    }

    public ApiError(Integer code, String message) {
        this();
        this.code = code;
        this.msg = message;
    }
}


