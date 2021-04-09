package com.csci5308.g17.utils;

public class JsonResponseDTO<O> {

    private Boolean success;
    private String message;
    private String error;
    private O data;

    public JsonResponseDTO(Boolean success, String message, String error, O data) {
        this.success = success;
        this.message = message;
        this.error = error;
        this.data = data;
    }

    public JsonResponseDTO() {
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public O getData() {
        return data;
    }

    public void setData(O data) {
        this.data = data;
    }
}
