package net.javadog.iphone.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * 有货记录
 *
 * @author: hdx
 **/
@Data
@TableName("`record`")
public class RecordEntity {

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    @ApiModelProperty(value = "型号")
    private String model;

    @ApiModelProperty(value = "地区")
    private String region;

    private Date createTime;
}
