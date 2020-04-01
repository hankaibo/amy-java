package cn.mypandora.springboot.modular.system.controller;

import java.security.Principal;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.*;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import cn.mypandora.springboot.config.exception.CustomException;
import cn.mypandora.springboot.config.websocket.WebSocketConfig;
import cn.mypandora.springboot.core.base.PageInfo;
import cn.mypandora.springboot.modular.system.model.po.Information;
import cn.mypandora.springboot.modular.system.service.InformationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;

/**
 * InformationController
 *
 * @author hankaibo
 * @date 2019/10/26
 */
@Slf4j
@Api(tags = "信息管理")
@Controller
@RequestMapping("/api/v1/information")
public class InformationController {

    private InformationService informationService;
    private SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    public InformationController(InformationService informationService, SimpMessagingTemplate simpMessagingTemplate) {
        this.informationService = informationService;
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    /**
     * 分页查询信息数据。
     *
     * @param pageNum
     *            页码
     * @param pageSize
     *            每页条数
     * @return 分页数据
     */
    @ApiOperation(value = "分页查询信息", notes = "根据分页数据进行查询。")
    @GetMapping
    @ResponseBody
    public PageInfo<Information> pageInformation(
        @RequestParam(value = "current", defaultValue = "1") @ApiParam(value = "页码", required = true) int pageNum,
        @RequestParam(value = "pageSize", defaultValue = "10") @ApiParam(value = "每页条数", required = true) int pageSize,
        @RequestParam(value = "type", required = false) @ApiParam(value = "信息类型") Integer type) {
        Information information = new Information();
        if (type != null) {
            information.setType(type);
        }
        return informationService.pageInformation(pageNum, pageSize, information);
    }

    /**
     * 新建信息。
     *
     * @param information
     *            信息数据
     */
    @ApiOperation(value = "信息新建", notes = "根据数据新建信息。")
    @PostMapping
    @ResponseBody
    public void addInformation(@RequestBody @ApiParam(value = "信息数据", required = true) Information information) {
        informationService.addInformation(information);
    }

    /**
     * 查询信息详细数据。
     *
     * @param id
     *            信息主键id
     * @return 信息信息
     */
    @ApiOperation(value = "信息详情", notes = "根据信息id查询信息详情。")
    @GetMapping("/{id}")
    @ResponseBody
    public Information getInformationById(@PathVariable("id") @ApiParam(value = "信息主键id", required = true) Long id) {
        Information information = informationService.getInformationById(id);
        if (information == null) {
            throw new CustomException(HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND.getReasonPhrase());
        }
        return information;
    }

    /**
     * 更新信息。
     *
     * @param information
     *            信息数据
     */
    @ApiOperation(value = "信息更新", notes = "根据信息数据更新信息。")
    @PutMapping("/{id}")
    @ResponseBody
    public void updateInformation(@RequestBody @ApiParam(value = "信息数据", required = true) Information information) {
        informationService.updateInformation(information);
    }

    /**
     * 启用|禁用信息。
     *
     * @param id
     *            信息主键id
     * @param map
     *            状态(1:启用，0:禁用)
     */
    @ApiOperation(value = "信息状态启用禁用", notes = "根据信息id启用或禁用其状态。")
    @PatchMapping("/{id}/status")
    @ResponseBody
    public void enableInformation(@PathVariable("id") @ApiParam(value = "信息主键id", required = true) Long id,
        @RequestBody @ApiParam(value = "启用(1)，禁用(0)", required = true) Map<String, Integer> map) {
        Integer status = map.get("status");
        informationService.enableInformation(id, status);
    }

    /**
     * 发布信息。
     *
     * @param id
     *            信息主键id
     */
    @ApiOperation(value = "信息发布", notes = "根据信息Id发布信息。")
    @PatchMapping("/{id}/publication")
    @ResponseBody
    public void publishInformation(@PathVariable("id") @ApiParam(value = "信息主键id", required = true) Long id) {
        informationService.publishInformation(id);
    }

    /**
     * 批量发布信息。
     *
     * @param ids
     *            信息id数组
     */
    @ApiOperation(value = "信息发布(批量)", notes = "根据信息Id批量发布信息。")
    @PutMapping
    @ResponseBody
    public void
        publishBatchInformation(@RequestBody @ApiParam(value = "信息主键数组ids", required = true) Map<String, Long[]> ids) {
        informationService.publishBatchInformation(StringUtils.join(ids.get("ids"), ','));
    }

    /**
     * 删除信息。
     *
     * @param id
     *            信息主键id
     */
    @ApiOperation(value = "信息删除", notes = "根据信息Id删除信息。")
    @DeleteMapping("/{id}")
    @ResponseBody
    public void deleteInformation(@PathVariable("id") @ApiParam(value = "信息主键id", required = true) Long id) {
        informationService.deleteInformation(id);
    }

    /**
     * 批量删除信息。
     *
     * @param ids
     *            信息id数组
     */
    @ApiOperation(value = "信息删除(批量)", notes = "根据信息Id批量删除信息。")
    @DeleteMapping
    @ResponseBody
    public void
        deleteBatchInformation(@RequestBody @ApiParam(value = "信息主键数组ids", required = true) Map<String, Long[]> ids) {
        informationService.deleteBatchInformation(StringUtils.join(ids.get("ids"), ','));
    }

    @MessageMapping("/test/{id}")
    public void test(Message message, MessageHeaders messageHeaders, @Header("destination") String destination,
        @Headers Map<String, Object> headers, @DestinationVariable long id, @Payload String body) {
        log.info("[test] Message: {}", message);
        log.info("[test] MessageHeaders: {}", messageHeaders);
        log.info("[test] Header: {}", destination);
        log.info("[test] Headers: {}", headers);
        log.info("[test] DestinationVariable: {}", id);
        log.info("[test] Payload: {}", body);
    }

    /**
     * 广播方式之一，使用 SimpMessagingTemplate 当前端浏览器访问"/api/v1/hello"时，该方法进行响应。 "/api/v1" 即
     * {@link WebSocketConfig#configureMessageBroker } 配置中定义值。 "/topic/**" 也要与
     * {@link WebSocketConfig#configureMessageBroker}中相对应。
     *
     * @param body
     *            信息体
     */
    @MessageMapping("/hello")
    public void hello(@Payload String body) {
        simpMessagingTemplate.convertAndSend("/topic/sub/public", "reply hello");
    }

    /**
     * 广播方式之二，使用 SendTo 注解
     *
     * @param body
     *            信息体
     * @return 广播内容
     */
    @MessageMapping("/hello1")
    @SendTo("/topic/sub/public")
    public String hello1(@Payload String body) {
        return "reply hello1";
    }

    /**
     * 点播方式之一，使用 SimpMessagingTemplate。
     *
     * @param body
     *            信息体
     * @param principal
     *            主体
     */
    public void hello3(@Payload String body, Principal principal) {
        simpMessagingTemplate.convertAndSendToUser(principal.getName(), "/queue/sub/msg", "reply hello3");
    }

    /**
     * 点播方式之二，使用 SendToIinformation 注解。
     *
     * @param body
     *            信息体
     * @param principal
     *            主体
     * @return 返回内容
     */
    @MessageMapping("/hello4")
    @SendToUser("sub/msg")
    public String hello4(@Payload String body, Principal principal) {
        return "reply hello4";
    }

}
