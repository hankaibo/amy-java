package cn.mypandora.springboot.modular.system.model.vo;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;

import cn.mypandora.springboot.core.validate.BatchGroup;
import cn.mypandora.springboot.core.validate.SingleGroup;
import lombok.Data;

/**
 * @author hankaibo
 * @date 2020/5/28
 */
@Data
public class UserDelete {

    /**
     * 用户id数组
     */
    @NotEmpty(groups = {BatchGroup.class}, message = "用户主键列表不能为空")
    private Long[] userIds;

    /**
     * 部门id
     */
    @Positive(groups = {SingleGroup.class, BatchGroup.class}, message = "部门不能为空")
    private Long departmentId;
}
