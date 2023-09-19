package net.javadog.iphone.common.enums;

import lombok.Getter;

/**
 * HttpCode枚举类
 *
 * @author hdx
 */
@Getter
public enum HttpCodeEnum implements AbstractBaseExceptionEnum {
    // 数据操作错误定义
    SUCCESS(200, "成功!"),
    BODY_NOT_MATCH(400, "请求的数据格式不符!"),
    SIGNATURE_NOT_MATCH(401, "身份认证异常!"),
    NOT_FOUND(404, "未找到该资源!"),
    METHOD_NOT_ALLOWED(405, "方法不被允许!"),
    INTERNAL_SERVER_ERROR(500, "服务器内部错误!"),
    SERVER_BUSY(503, "服务器正忙，请稍后再试!");

    /**
     * 错误码
     */
    private final Integer resultCode;

    /**
     * 错误描述
     */
    private final String resultMsg;

    HttpCodeEnum(Integer resultCode, String resultMsg) {
        this.resultCode = resultCode;
        this.resultMsg = resultMsg;
    }
}