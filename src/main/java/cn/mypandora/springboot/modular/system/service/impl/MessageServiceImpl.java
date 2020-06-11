package cn.mypandora.springboot.modular.system.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;

import cn.mypandora.springboot.config.exception.BusinessException;
import cn.mypandora.springboot.config.exception.EntityNotFoundException;
import cn.mypandora.springboot.core.base.PageInfo;
import cn.mypandora.springboot.modular.system.mapper.MessageContentMapper;
import cn.mypandora.springboot.modular.system.mapper.MessageMapper;
import cn.mypandora.springboot.modular.system.model.po.Message;
import cn.mypandora.springboot.modular.system.model.po.MessageContent;
import cn.mypandora.springboot.modular.system.model.vo.Msg;
import cn.mypandora.springboot.modular.system.service.MessageService;
import tk.mybatis.mapper.entity.Example;

/**
 * MessageServiceImpl
 *
 * @author hankaibo
 * @date 2019/10/29
 */
@Service
public class MessageServiceImpl implements MessageService {

    private MessageMapper messageMapper;
    private MessageContentMapper messageContentMapper;

    @Autowired
    public MessageServiceImpl(MessageMapper messageMapper, MessageContentMapper messageContentMapper) {
        this.messageMapper = messageMapper;
        this.messageContentMapper = messageContentMapper;
    }

    @Override
    public PageInfo<Msg> pageMessage(int pageNum, int pageSize, Msg msg) {
        PageHelper.startPage(pageNum, pageSize);
        List<Msg> msgList = messageMapper.listMsg(msg);
        return new PageInfo<>(msgList);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void addMessage(Msg msg) {
        LocalDateTime now = LocalDateTime.now();
        // 第一步，添加 MessageContent
        MessageContent messageContent = new MessageContent();
        messageContent.setTitle(msg.getTitle());
        messageContent.setContent(msg.getContent());
        messageContent.setType(msg.getType());
        messageContent.setCreateTime(now);
        messageContentMapper.insert(messageContent);

        // 第二步，添加 Message
        List<Message> messageList = new ArrayList<>();
        for (Long receiveId : msg.getReceiveIds()) {
            Message message = new Message();
            message.setSendId(msg.getSendId());
            message.setReceiveId(receiveId);
            message.setContentId(messageContent.getId());
            message.setStatus(msg.getStatus());
            message.setIsPublish(msg.getIsPublish());
            message.setPublishTime(msg.getPublishTime());
            message.setIsRead(Boolean.FALSE);
            message.setCreateTime(now);
            messageList.add(message);
        }
        messageMapper.insertList(messageList);
    }

    @Override
    public Msg getMessageById(Long id, Long userId) {
        Msg info = messageMapper.getMessageById(id, userId);
        if (info == null) {
            throw new EntityNotFoundException(Msg.class, "站内信不存在。");
        }
        return info;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateMessage(Msg msg, Long[] plusReceiveIds, Long[] minusReceiveIds) {
        if (msg.getIsPublish() == 1) {
            throw new BusinessException(Msg.class, "已发布站内信不允许修改。");
        }

        LocalDateTime now = LocalDateTime.now();

        // 修改站内信内容
        MessageContent messageContent = new MessageContent();
        messageContent.setId(msg.getId());
        messageContent.setTitle(msg.getTitle());
        messageContent.setType(msg.getType());
        messageContent.setContent(msg.getContent());
        messageContent.setUpdateTime(now);

        messageContentMapper.updateByPrimaryKeySelective(messageContent);

        // 添加新收信人
        if (plusReceiveIds.length > 0) {
            List<Message> messageList = new ArrayList<>();
            for (Long receiveId : plusReceiveIds) {
                Message message = new Message();
                message.setSendId(msg.getSendId());
                message.setReceiveId(receiveId);
                message.setContentId(msg.getId());
                message.setStatus(msg.getStatus());
                message.setIsPublish(msg.getIsPublish());
                message.setPublishTime(msg.getPublishTime());
                message.setIsRead(Boolean.FALSE);
                message.setCreateTime(now);
                messageList.add(message);
            }
            messageMapper.insertList(messageList);
        }

        // 删除旧收信人
        if (minusReceiveIds.length > 0) {
            Example example = new Example(Message.class);
            example.createCriteria().andIn("receiveId", Arrays.asList(minusReceiveIds)).andEqualTo("contentId",
                msg.getId());
            messageMapper.deleteByExample(example);
        }

    }

    @Override
    public void enableMessage(Long id, Integer status, Long userId) {
        LocalDateTime now = LocalDateTime.now();
        Example example = new Example(Message.class);
        example.createCriteria().andEqualTo("sendId", userId).andEqualTo("contentId", id);

        Message message = new Message();
        message.setStatus(status);
        message.setUpdateTime(now);

        messageMapper.updateByExampleSelective(message, example);
    }

    @Override
    public void publishMessage(Long id, Long userId) {
        LocalDateTime now = LocalDateTime.now();
        Example example = new Example(Message.class);
        example.createCriteria().andEqualTo("sendId", userId).andEqualTo("contentId", id);

        Message message = new Message();
        message.setIsPublish(1);
        message.setUpdateTime(now);

        messageMapper.updateByExampleSelective(message, example);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void publishBatchMessage(Long[] ids, Long userId) {
        LocalDateTime now = LocalDateTime.now();
        Example example = new Example(Message.class);
        example.createCriteria().andEqualTo("sendId", userId).andIn("contentId", Arrays.asList(ids));

        Message message = new Message();
        message.setIsPublish(1);
        message.setUpdateTime(now);

        messageMapper.updateByExampleSelective(message, example);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteMessage(Long id, Long userId) {
        Example example = new Example(Message.class);
        example.createCriteria().andEqualTo("sendId", userId).andEqualTo("contentId", id);

        messageMapper.deleteByExample(example);
        messageContentMapper.deleteByPrimaryKey(id);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteBatchMessage(Long[] ids, Long userId) {
        Example example = new Example(Message.class);
        example.createCriteria().andEqualTo("sendId", userId).andIn("contentId", Arrays.asList(ids));

        messageMapper.deleteByExample(example);
        messageContentMapper.deleteByIds(StringUtils.join(ids, ","));
    }

}
