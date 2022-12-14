package com.wdjk.webdemo624.dto;

import com.wdjk.webdemo624.constant.api.ParamConst;
import com.wdjk.webdemo624.constant.api.SetConst;


import java.util.HashMap;
import java.util.Map;

/**
 *  Api JSON 传输对象
 *      - Controller 层 -> 前端
 *      - 仅能在 Controller 层使用
 *      - 返回格式
 *          - {
 *              success: true (false)
 *              message: ""
 *              model: { }
 *             }
 *
 *  @author Suvan
 */
public class ApiJsonDTO {

    private Boolean success;
    private String message;

    private Object model;

    /**
     * Constructor
     *      - 设置默认值
     *          - success = false
     *          - message = ""
     *          - model = { }
     */
    public ApiJsonDTO() {
        this.setSuccess(ParamConst.FAIL);
        this.setMessage("");
        this.setModel(new HashMap<>(0));
    }

    public ApiJsonDTO success() {
        this.setSuccess(ParamConst.SUCCESS);
        return this;
    }
    public ApiJsonDTO fail() {
        this.setSuccess(ParamConst.FAIL);
        return this;
    }
    public ApiJsonDTO message(String message) {
        this.setMessage(message);
        return this;
    }
    public ApiJsonDTO model(Object mode) {
        this.setModel(mode);
        return this;
    }
    public ApiJsonDTO buildMap(String key, Object value) {
        Map<String, Object> tmpMap = new HashMap<>(SetConst.SIZE_ONE);
            tmpMap.put(key, value);
        this.setModel(tmpMap);
        return this;
    }


    /**
     * Getter
     */
    public Boolean getSuccess() {
        return success;
    }
    public String getMessage() {
        return message;
    }
    public Object getModel() {
        return model;
    }

    /**
     * Setter
     */
    public void setSuccess(boolean success) {
        this.success = success;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public void setModel(Object model) {
        this.model = model;
    }

    @Override
    public String toString() {
        return "PageJsonListDTO{"
                + "success=" + success
                + ", message='" + message + '\''
                + ", model=" + model
                + '}';
    }
}
