package cn.mypandora.springboot.modular.system.model.vo;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import cn.mypandora.springboot.core.validate.UpdateGroup;
import cn.mypandora.springboot.modular.system.model.po.User;
import lombok.Data;

/**
 * 此对象作为用户表单修改时封装对象。
 * <p>
 * 新建用户与修改用户使用不同的表单对象实体。 因为用户多部门时，为了计算方便，前端提交的数据会分为用户实体、用户新增加的
 * </p>
 * 
 * @author hankaibo
 * @date 2020/5/28
 */
@Data
public class UserUpdate {

    /**
     * 用户实体
     */
    @Valid
    private User user;

    /**
     * 用户要新添加的部门
     */
    @NotNull(groups = {UpdateGroup.class}, message = "添加的新部门不可为null")
    private Long[] plusDepartmentIds;

    /**
     * 用户要删除的旧部门
     */
    @NotNull(groups = {UpdateGroup.class}, message = "删除的旧部门不可为null")
    private Long[] minusDepartmentIds;

}
