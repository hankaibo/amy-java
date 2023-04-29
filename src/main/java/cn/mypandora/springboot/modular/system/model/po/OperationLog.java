package cn.mypandora.springboot.modular.system.model.po;

import cn.mypandora.springboot.core.enums.BusinessTypeEnum;
import cn.mypandora.springboot.core.enums.LogStatusEnum;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tk.mybatis.mapper.annotation.NameStyle;
import tk.mybatis.mapper.code.Style;

import javax.persistence.Table;

/**
 * @author hankaibo
 * @date 9/30/2021
 */
@ApiModel("操作日志")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "sys_operation_log")
@NameStyle(Style.camelhumpAndLowercase)
public class OperationLog extends BaseEntity {

    /**
     * 操作模块
     */
    private String title;

    /**
     * 业务类型
     */
    private BusinessTypeEnum businessType;

    /**
     * 业务类型数组
     */
    private BusinessTypeEnum[] businessTypes;

    /**
     * 请求方法
     */
    private String method;

    /**
     * 请求方式
     */
    private String requestMethod;

    /**
     * 操作人员
     */
    private String username;

    /**
     * 部门名称
     */
    private String deptName;

    /**
     * 请求url
     */
    private String url;

    /**
     * 操作ip
     */
    private String ip;

    /**
     * 操作地点
     */
    private String location;

    /**
     * 请求参数
     */
    private String params;

    /**
     * 返回参数
     */
    private String jsonResult;

    /**
     * 状态: 正常 异常
     */
    private LogStatusEnum status;

    /**
     * 错误信息
     */
    private String errorMsg;
}
