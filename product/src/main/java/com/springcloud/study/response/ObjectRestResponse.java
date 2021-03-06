package com.springcloud.study.response;

public class ObjectRestResponse<T> extends BaseResponse {

    private T data;

    public ObjectRestResponse data(T data) {
        this.setData(data);
        return this;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
