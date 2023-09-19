package net.javadog.iphone.common.enums;

/**
 * 自定义基础接口类
 *
 * @author hdx
 */
public interface AbstractBaseExceptionEnum {
    /**
     * 获取结果错误码
     *
     * @param
     * @return java.lang.Integer
     */
    Integer getResultCode();

    /**
     * 获取错误描述
     *
     * @param
     * @return java.lang.String
     */
    String getResultMsg();
}
