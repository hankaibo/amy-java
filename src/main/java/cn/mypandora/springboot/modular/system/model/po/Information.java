package cn.mypandora.springboot.modular.system.model.po;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Table;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import tk.mybatis.mapper.annotation.NameStyle;
import tk.mybatis.mapper.code.Style;

/**
 * Information
 *
 * @author hankaibo
 * @date 2019/10/26
 */
@ApiModel(value = "信息对象")
@Data
@Table(name = "sys_information")
@NameStyle(Style.camelhumpAndLowercase)
public class Information extends BaseEntity {

    /**
     * 信息头像
     */
    private String avatar;

    /**
     * 信息标题
     */
    private String title;

    /**
     * 信息内容
     */
    private String content;

    /**
     * 信息类型
     */
    private Integer type;

    /**
     * 信息状态
     */
    private Integer status;

    /**
     * 是否发布
     */
    @Column(name = "is_publish")
    private Integer publish;

    /**
     * 是否已读
     */
    @Column(name = "is_read")
    private Integer read;

    /**
     * 发布时间
     */
    private LocalDateTime publishTime;

}
