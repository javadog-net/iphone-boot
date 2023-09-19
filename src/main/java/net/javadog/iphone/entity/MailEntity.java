package net.javadog.iphone.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.util.Date;

/**
 * 邮箱实体
 *
 * @author hdx
 */
@Data
@TableName("`mail`")
public class MailEntity {

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    @ApiModelProperty(value = "主题")
    private String subject;

    @ApiModelProperty(value = "内容")
    private String text;

    @ApiModelProperty(value = "接收邮箱")
    @Pattern(regexp = "^[A-Za-z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$", message = "邮箱格式不正确")
    @NotEmpty(message = "邮箱不能为空")
    private String toMail;

    private String fromMail;

    private Date createTime;
}
