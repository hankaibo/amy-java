package cn.mypandora.springboot.modular.system.service;

import cn.mypandora.springboot.core.base.PageInfo;
import cn.mypandora.springboot.modular.system.model.vo.Msg;

/**
 * MessageService
 *
 * @author hankaibo
 * @date 2019/10/29
 */
public interface MessageService {

    /**
     * 根据分页参数查询 站内信。
     *
     * @param pageNum
     *            当前页码
     * @param pageSize
     *            当前页数
     * @param msg
     *            站内信
     * @return 站内信列表
     */
    PageInfo<Msg> pageMessage(int pageNum, int pageSize, Msg msg);

    /**
     * 新建站内信。
     *
     * @param msg
     *            站内信
     */
    void addMessage(Msg msg);

    /**
     * 根据站内信 id 查询。
     *
     * @param id
     *            站内信id
     * @param source
     *            站内信来源：收件箱(INBOX)、发件箱(SENT)、草稿箱(DRAFT)
     * @param userId
     *            用户id
     * @return 站内信
     */
    Msg getMessageById(Long id, String source, Long userId);

    /**
     * 更新站内信。
     *
     * @param msg
     *            站内信
     * @param plusReceiveIds
     *            新添加收信人id数组
     * @param minusReceiveIds
     *            删除旧收信人id数组
     */
    void updateMessage(Msg msg, Long[] plusReceiveIds, Long[] minusReceiveIds);

    /**
     * 发布站内信。
     *
     * @param id
     *            站内信id
     * @param userId
     *            用户id
     */
    void publishMessage(Long id, Long userId);

    /**
     * 批量发布站内信。
     *
     * @param ids
     *            '1,2,3,4'
     * @param userId
     *            用户id
     */
    void publishBatchMessage(Long[] ids, Long userId);

    /**
     * 删除站内信。
     *
     * @param id
     *            站内信id
     * @param source
     *            站内信来源：收件箱(INBOX)、发件箱(SENT)、草稿箱(DRAFT)
     * @param userId
     *            用户id
     */
    void deleteMessage(Long id, String source, Long userId);

    /**
     * 批量删除站内信。
     *
     * @param ids
     *            '1,2,3,4'
     * @param source
     *            站内信来源：收件箱(INBOX)、发件箱(SENT)、草稿箱(DRAFT)
     * @param userId
     *            用户id
     */
    void deleteBatchMessage(Long[] ids, String source, Long userId);

}
