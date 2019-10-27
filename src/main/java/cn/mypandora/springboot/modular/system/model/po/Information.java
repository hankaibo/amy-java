package cn.mypandora.springboot.modular.system.model.po;

import cn.mypandora.springboot.modular.system.model.BaseEntity;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import tk.mybatis.mapper.annotation.NameStyle;
import tk.mybatis.mapper.code.Style;

import javax.persistence.Table;
import java.time.LocalDateTime;

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
     * 信息描述
     */
    private String description;

    /**
     * 信息类型
     */
    private Integer type;

    /**
     * 是否已读
     */
    private Integer read;

    /**
     * 信息时间
     */
    private LocalDateTime datetime;

    /**
     * 信息状态，不同与其它实体的开启禁用。
     * 1, 未开始
     * 2, 进行中
     * 3, 马上到期
     * 4, 已耗时
     */
    private Integer status;
}
