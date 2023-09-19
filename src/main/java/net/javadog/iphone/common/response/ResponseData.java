package net.javadog.iphone.common.response;

import cn.hutool.json.JSONObject;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.experimental.Accessors;
import net.javadog.iphone.common.enums.AbstractBaseExceptionEnum;
import net.javadog.iphone.common.enums.HttpCodeEnum;

import java.io.Serializable;

/**
 * 响应结果数据格式
 *
 * @author hdx
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "响应结果")
public class ResponseData<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 是否成功标记
     */
    private boolean success = true;

    /**
     * 响应代码
     */
    private Integer code;

    /**
     * 响应消息
     */
    private String message;

    /**
     * 响应结果
     */
    private T data;

    public ResponseData() {
    }

    public ResponseData(AbstractBaseExceptionEnum errorInfo) {
        this.code = errorInfo.getResultCode();
        this.message = errorInfo.getResultMsg();
    }

    /**
     * 成功
     *
     * @return
     */
    public static ResponseData success() {
        return success(null);
    }

    /**
     * 成功
     *
     * @param data
     * @return
     */
    public static ResponseData success(Object data) {
        ResponseData rb = new ResponseData();
        rb.setCode(HttpCodeEnum.SUCCESS.getResultCode());
        rb.setMessage(HttpCodeEnum.SUCCESS.getResultMsg());
        rb.setData(data);
        return rb;
    }

    /**
     * 成功
     *
     * @param
     * @return
     */
    public static ResponseData success(String msg) {
        ResponseData rb = new ResponseData();
        rb.setCode(HttpCodeEnum.SUCCESS.getResultCode());
        rb.setMessage(msg);
        rb.setData(null);
        return rb;
    }

    /**
     * 失败
     */
    public static ResponseData error(AbstractBaseExceptionEnum errorInfo) {
        ResponseData rb = new ResponseData();
        rb.setCode(errorInfo.getResultCode());
        rb.setMessage(errorInfo.getResultMsg());
        rb.setData(null);
        rb.setSuccess(false);
        return rb;
    }

    /**
     * 失败
     */
    public static ResponseData error(Integer code, String message) {
        ResponseData rb = new ResponseData();
        rb.setCode(code);
        rb.setMessage(message);
        rb.setSuccess(false);
        return rb;
    }

    /**
     * 失败
     */
    public static ResponseData error(String message) {
        ResponseData rb = new ResponseData();
        rb.setCode(-1);
        rb.setMessage(message);
        rb.setSuccess(false);
        return rb;
    }

    @Override
    public String toString() {
        return new JSONObject(this).toString();
    }

}


