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
import cn.mypandora.springboot.modular.system.mapper.MessageReceiverMapper;
import cn.mypandora.springboot.modular.system.mapper.MessageSenderMapper;
import cn.mypandora.springboot.modular.system.model.po.MessageContent;
import cn.mypandora.springboot.modular.system.model.po.MessageReceiver;
import cn.mypandora.springboot.modular.system.model.po.MessageSender;
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

    private final MessageSenderMapper messageSenderMapper;
    private final MessageReceiverMapper messageReceiverMapper;
    private final MessageContentMapper messageContentMapper;

    private final String INBOX = "INBOX";
    private final String SENT = "SENT";
    private final String DRAFT = "DRAFT";

    @Autowired
    public MessageServiceImpl(MessageSenderMapper messageSenderMapper, MessageReceiverMapper messageReceiverMapper,
        MessageContentMapper messageContentMapper) {
        this.messageSenderMapper = messageSenderMapper;
        this.messageReceiverMapper = messageReceiverMapper;
        this.messageContentMapper = messageContentMapper;
    }

    @Override
    public PageInfo<Msg> pageMessage(int pageNum, int pageSize, Msg msg) {
        PageHelper.startPage(pageNum, pageSize);
        List<Msg> msgList;
        if (msg.getSendId() != null) {
            msgList = messageSenderMapper.listMsg(msg);
        } else {
            msgList = messageReceiverMapper.listMsg(msg);
        }
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

        // 第二步，添加 MessageSender
        List<MessageSender> messageSenderList = new ArrayList<>();
        for (Long receiveId : msg.getReceiveIdList()) {
            MessageSender messageSender = new MessageSender();
            messageSender.setSendId(msg.getSendId());
            messageSender.setReceiveId(receiveId);
            messageSender.setContentId(messageContent.getId());
            messageSender.setStatus(msg.getStatus());
            messageSender.setIsPublish(msg.getIsPublish());
            if (msg.getIsPublish() == 1) {
                messageSender.setPublishTime(now);
            } else {
                messageSender.setPublishTime(msg.getPublishTime());
            }
            messageSender.setCreateTime(now);
            messageSenderList.add(messageSender);
        }
        messageSenderMapper.insertList(messageSenderList);

        // 第三步，添加 MessageReceiver
        List<MessageReceiver> messageReceiverList = new ArrayList<>();
        for (Long receiveId : msg.getReceiveIdList()) {
            MessageReceiver messageReceiver = new MessageReceiver();
            messageReceiver.setSendId(msg.getSendId());
            messageReceiver.setReceiveId(receiveId);
            messageReceiver.setContentId(messageContent.getId());
            messageReceiver.setStatus(msg.getStatus());
            messageReceiver.setIsRead(Boolean.FALSE);
            messageReceiver.setCreateTime(now);
            messageReceiverList.add(messageReceiver);
        }
        messageReceiverMapper.insertList(messageReceiverList);
    }

    @Override
    public Msg getMessageById(Long id, String source, Long userId) {
        Msg info;
        if (INBOX.equals(source)) {
            info = messageReceiverMapper.getMessageById(id, userId);
        } else {
            info = messageSenderMapper.getMessageById(id, userId);
        }
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
            List<MessageSender> messageSenderList = new ArrayList<>();
            for (Long receiveId : plusReceiveIds) {
                MessageSender messageSender = new MessageSender();
                messageSender.setSendId(msg.getSendId());
                messageSender.setReceiveId(receiveId);
                messageSender.setContentId(msg.getId());
                messageSender.setStatus(msg.getStatus());
                messageSender.setIsPublish(msg.getIsPublish());
                messageSender.setPublishTime(msg.getPublishTime());
                messageSender.setCreateTime(now);
                messageSenderList.add(messageSender);
            }
            messageSenderMapper.insertList(messageSenderList);
        }

        // 删除旧收信人
        if (minusReceiveIds.length > 0) {
            Example example = new Example(MessageReceiver.class);
            example.createCriteria().andIn("receiveId", Arrays.asList(minusReceiveIds)).andEqualTo("contentId",
                msg.getId());
            messageReceiverMapper.deleteByExample(example);
        }

    }

    @Override
    public void publishMessage(Long id, Long userId) {
        LocalDateTime now = LocalDateTime.now();
        Example example = new Example(MessageSender.class);
        example.createCriteria().andEqualTo("sendId", userId).andEqualTo("contentId", id);

        MessageSender messageSender = new MessageSender();
        messageSender.setIsPublish(1);
        messageSender.setUpdateTime(now);

        messageSenderMapper.updateByExampleSelective(messageSender, example);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void publishBatchMessage(Long[] ids, Long userId) {
        LocalDateTime now = LocalDateTime.now();
        Example example = new Example(MessageSender.class);
        example.createCriteria().andEqualTo("sendId", userId).andIn("contentId", Arrays.asList(ids));

        MessageSender messageSender = new MessageSender();
        messageSender.setIsPublish(1);
        messageSender.setUpdateTime(now);

        messageSenderMapper.updateByExampleSelective(messageSender, example);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteMessage(Long id, String source, Long userId) {
        if (INBOX.equals(source)) {
            Example example = new Example(MessageReceiver.class);
            example.createCriteria().andEqualTo("sendId", userId).andEqualTo("contentId", id);
            messageReceiverMapper.deleteByExample(example);
        } else if (SENT.equals(source) || DRAFT.equals(source)) {
            Example example = new Example(MessageSender.class);
            example.createCriteria().andEqualTo("sendId", userId).andEqualTo("contentId", id);
            messageSenderMapper.deleteByExample(example);
        }
        messageContentMapper.deleteByPrimaryKey(id);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteBatchMessage(Long[] ids, String source, Long userId) {
        if (INBOX.equals(source)) {
            Example example = new Example(MessageReceiver.class);
            example.createCriteria().andEqualTo("sendId", userId).andEqualTo("contentId", Arrays.asList(ids));
            messageReceiverMapper.deleteByExample(example);
        } else if (SENT.equals(source) || DRAFT.equals(source)) {
            Example example = new Example(MessageSender.class);
            example.createCriteria().andEqualTo("sendId", userId).andEqualTo("contentId", Arrays.asList(ids));
            messageSenderMapper.deleteByExample(example);
        }
        messageContentMapper.deleteByIds(StringUtils.join(ids, ","));
    }

}
