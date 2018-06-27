package com.cbt.server;

// 业务异常
public class CbtException extends RuntimeException {
    private int code;
    private String notice;

    public CbtException(int code, String message) {
        super(message);
        this.code = code;
    }

    public CbtException(int code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }

    public String getNotice() {
        return notice;
    }

    public void setNotice(String notice) {
        this.notice = notice;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

}
