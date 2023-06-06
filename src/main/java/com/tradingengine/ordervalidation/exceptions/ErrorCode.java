package com.tradingengine.ordervalidation.exceptions;


public enum ErrorCode {
    // Internal Errors: 1 to 0999
    GENERIC_ERROR("500", "The system is unable to complete the request. Contact system support."),
    NOT_FOUND("400", "Your request is Invalid");


    private String errCode;
    private String errMsgKey;

    ErrorCode(final String errCode, final String errMsgKey) {
        this.errCode = errCode;
        this.errMsgKey = errMsgKey;
    }


    public String getErrCode() {
        return errCode;
    }


    public String getErrMsgKey() {
        return errMsgKey;
    }
}