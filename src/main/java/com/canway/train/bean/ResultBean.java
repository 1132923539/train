package com.canway.train.bean;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.http.HttpStatus;

import java.io.Serializable;

/**
 * @author Hanson
 * @date 2018-05-11
 */
@Data
@Accessors(chain=true)
public class ResultBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private boolean success;

    private int code;

    private String msg;

    private Object data;

    private ResultBean(boolean success, HttpStatus code, String msg, Object data) {
        this.success = success;
        this.code = code.value();
        this.msg = msg;
        this.data = data;
    }

    public static ResultBean success() {
        return new ResultBean(true, HttpStatus.OK, "", null);
    }

    public static ResultBean success(Object data) {
        return new ResultBean(true, HttpStatus.OK, "", data);
    }

    public static ResultBean success(Object data, String msg) {
        return new ResultBean(true, HttpStatus.OK, msg, data);
    }

    public static ResultBean success(Object data, String msg, HttpStatus code) {
        return new ResultBean(true, code, msg, data);
    }

    public static ResultBean fail() {
        return new ResultBean(false, HttpStatus.OK, "", null);
    }

    public static ResultBean fail(Object data) {
        return new ResultBean(false, HttpStatus.OK, "", data);
    }

    public static ResultBean fail(Object data, String msg) {
        return new ResultBean(false, HttpStatus.OK, msg, data);
    }

    public static ResultBean fail(Object data, String msg, HttpStatus code) {
        return new ResultBean(false, code, msg, data);
    }

}
