package cn.mypandora.springboot.modular.system.model.vo;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import cn.mypandora.springboot.core.validate.UpdateGroup;
import lombok.Data;

/**
 * 此对象作为站内信表单修改时的封装对象。
 * <p>
 * 站内信修改时，可能添加新收信人，可能删除旧收信人，为了方便计算，前台对比出之后，后台直接操作数据。
 * </p>
 *
 * @author hankaibo
 * @date 2020/6/10
 */
@Data
public class MsgUpdate {

    /**
     * 站内信实体
     */
    @Valid
    @NotNull(groups = {UpdateGroup.class}, message = "信息不可为空")
    private Msg msg;

    /**
     * 站内信要新添加的收信人
     */
    @NotNull(groups = {UpdateGroup.class}, message = "添加的新收信人不可为空")
    private Long[] plusReceiveIds;

    /**
     * 站内信要删除的旧收信人
     */
    @NotNull(groups = {UpdateGroup.class}, message = "删除的旧收信人不可为空")
    private Long[] minusReceiveIds;

}
