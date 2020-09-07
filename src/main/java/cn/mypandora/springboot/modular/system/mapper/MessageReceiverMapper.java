package cn.mypandora.springboot.modular.system.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.mypandora.springboot.core.base.MyBaseMapper;
import cn.mypandora.springboot.modular.system.model.po.MessageReceiver;
import cn.mypandora.springboot.modular.system.model.vo.Msg;

/**
 * MessageMapper
 *
 * @author hankaibo
 * @date 2019/10/29
 */
public interface MessageReceiverMapper extends MyBaseMapper<MessageReceiver> {

    /**
     * 根据条件查的信息列表。
     *
     * @param msg
     *            查询条件
     * @return 信息列表
     */
    List<Msg> listMsg(Msg msg);

    /**
     * 
     * @param id
     *            站内信内容主键id
     * @param userId
     *            用户主键id
     * @return 一条站内信
     */
    Msg getMessageById(@Param("id") Long id, @Param("userId") Long userId);

}
