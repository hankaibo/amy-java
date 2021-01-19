package cn.mypandora.springboot.modular.system.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

import org.hibernate.validator.constraints.Range;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import cn.mypandora.springboot.core.base.PageInfo;
import cn.mypandora.springboot.core.validate.AddGroup;
import cn.mypandora.springboot.core.validate.UpdateGroup;
import cn.mypandora.springboot.modular.system.model.vo.Msg;
import cn.mypandora.springboot.modular.system.model.vo.MsgUpdate;
import cn.mypandora.springboot.modular.system.service.MessageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import springfox.documentation.annotations.ApiIgnore;

/**
 * MessageController
 *
 * @author hankaibo
 * @date 2019/10/26
 */
@Validated
@Api(tags = "站内信管理")
@Controller
@RequestMapping("/api/v1/messages")
public class MessageController {

    private final MessageService messageService;

    @Autowired
    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    /**
     * 分页查询站内信数据。
     *
     * @param pageNum
     *            页码
     * @param pageSize
     *            每页条数
     * @param userId
     *            当前登录用户id
     * @return 分页数据
     */
    @ApiOperation(value = "分页查询站内信")
    @GetMapping
    @ResponseBody
    public PageInfo<Msg> pageMessage(
        @Positive @RequestParam(value = "current", defaultValue = "1") @ApiParam(value = "页码",
            required = true) int pageNum,
        @Positive @RequestParam(value = "pageSize", defaultValue = "10") @ApiParam(value = "每页条数",
            required = true) int pageSize,
        @RequestParam(value = "sendId", required = false) @ApiParam(value = "发信人") Long sendId,
        @RequestParam(value = "receiveId", required = false) @ApiParam(value = "收信人") Long receiveId,
        @Range(min = 0, max = 1) @RequestParam(value = "isPublish") @ApiParam(value = "是否发布") Integer isPublish,
        @Range(min = 1, max = 3) @RequestParam(value = "type",
            required = false) @ApiParam(value = "站内信类型") Integer type,
        @ApiIgnore Long userId) {
        Msg msg = new Msg();
        if (sendId != null) {
            msg.setSendId(userId);
        } else {
            List<Long> receiveIdList = new ArrayList<>();
            receiveIdList.add(userId);
            msg.setReceiveIdList(receiveIdList);
        }
        msg.setIsPublish(isPublish);
        msg.setType(type);
        return messageService.pageMessage(pageNum, pageSize, msg);
    }

    /**
     * 新建站内信。
     *
     * @param msg
     *            站内信数据
     * @param userId
     *            当前登录用户id
     */
    @ApiOperation(value = "新建站内信")
    @PostMapping
    @ResponseBody
    public void addMessage(
        @Validated({AddGroup.class}) @RequestBody @ApiParam(value = "站内信数据", required = true) Msg msg,
        @ApiIgnore Long userId) {
        msg.setSendId(userId);
        messageService.addMessage(msg);
    }

    /**
     * 查询站内信详细数据。
     *
     * @param id
     *            站内信主键id
     * @param userId
     *            当前登录用户id
     * @return 站内信
     */
    @ApiOperation(value = "获取站内信详情")
    @GetMapping("/{id}")
    @ResponseBody
    public Msg getMessageById(@Positive @PathVariable("id") @ApiParam(value = "站内信主键id", required = true) Long id,
        @NotBlank @RequestParam(value = "source") @ApiParam(value = "来源") String source, @ApiIgnore Long userId) {
        return messageService.getMessageById(id, source, userId);
    }

    /**
     * 更新站内信。
     *
     * @param msgUpdate
     *            站内信数据
     * @param userId
     *            当前登录用户id
     */
    @ApiOperation(value = "更新站内信")
    @PutMapping("/{id}")
    @ResponseBody
    public void updateMessage(
        @Validated({UpdateGroup.class}) @RequestBody @ApiParam(value = "站内信数据", required = true) MsgUpdate msgUpdate,
        @ApiIgnore Long userId) {
        Msg msg = msgUpdate.getMsg();
        msg.setSendId(userId);
        Long[] plusReceiveIds = msgUpdate.getPlusReceiveIds();
        Long[] minusReceiveIds = msgUpdate.getMinusReceiveIds();

        messageService.updateMessage(msg, plusReceiveIds, minusReceiveIds);
    }

    /**
     * 发布站内信。
     *
     * @param id
     *            站内信主键id
     * @param userId
     *            当前登录用户id
     */
    @ApiOperation(value = "发布站内信")
    @PatchMapping("/{id}/publication")
    @ResponseBody
    public void publishMessage(@Positive @PathVariable("id") @ApiParam(value = "站内信主键id", required = true) Long id,
        @ApiIgnore Long userId) {
        messageService.publishMessage(id, userId);
    }

    /**
     * 批量发布站内信。
     *
     * @param ids
     *            站内信id数组
     * @param userId
     *            当前登录用户id
     */
    @ApiOperation(value = "发布站内信(批量)")
    @PutMapping
    @ResponseBody
    public void publishBatchMessage(@RequestBody @ApiParam(value = "站内信主键数组ids", required = true) Long[] ids,
        @ApiIgnore Long userId) {
        messageService.publishBatchMessage(ids, userId);
    }

    /**
     * 删除站内信。
     *
     * @param id
     *            站内信主键id
     * @param userId
     *            当前登录用户id
     */
    @ApiOperation(value = "删除站内信")
    @DeleteMapping("/{id}")
    @ResponseBody
    public void deleteMessage(@Positive @PathVariable("id") @ApiParam(value = "站内信主键id", required = true) Long id,
        @NotBlank @RequestParam(value = "source") @ApiParam(value = "来源") String source, @ApiIgnore Long userId) {
        messageService.deleteMessage(id, source, userId);
    }

    /**
     * 批量删除站内信。
     *
     * @param ids
     *            站内信id数组
     * @param userId
     *            当前登录用户id
     */
    @ApiOperation(value = "删除站内信(批量)")
    @DeleteMapping
    @ResponseBody
    public void deleteBatchMessage(@RequestBody @ApiParam(value = "站内信主键数组ids", required = true) Long[] ids,
        @NotBlank @RequestParam(value = "source") @ApiParam(value = "来源") String source, @ApiIgnore Long userId) {
        messageService.deleteBatchMessage(ids, source, userId);
    }

    @MessageMapping("/hello")
    @SendTo("/topic/message")
    public Msg greeting() throws Exception {
        Thread.sleep(1000); // simulated delay
        Msg foo = new Msg();
        foo.setId(123L);
        return foo;
    }
}
