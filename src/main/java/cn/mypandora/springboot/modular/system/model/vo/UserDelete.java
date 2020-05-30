package cn.mypandora.springboot.modular.system.model.vo;

import java.util.List;

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
     * 用户id
     */
    @Positive(groups = {SingleGroup.class}, message = "主键不能为空")
    private Long userId;

    /**
     * 用户id数组
     */
    @NotEmpty(groups = {BatchGroup.class}, message = "主键不能为空")
    private List<Long> userIdList;

    /**
     * 部门id
     */
    @Positive
    private Long departmentId;
}
