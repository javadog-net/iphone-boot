package net.javadog.iphone.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.Date;

/**
 * Iphone实体
 *
 * @author hdx
 */
@Data
@TableName("`iphone`")
public class IphoneEntity {

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @JsonSerialize(using = ToStringSerializer.class)
    @ApiModelProperty(value = "id")
    private Long id;

    @Pattern(regexp = "^[A-Za-z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$", message = "邮箱格式不正确")
    private String mail;

    @NotBlank(message = "型号不能为空")
    private String model;

    @NotBlank(message = "地区不能为空")
    private String location;

    private Date createTime;
}
