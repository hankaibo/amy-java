package cn.mypandora.springboot.modular.system.controller;

import cn.mypandora.springboot.modular.system.model.po.Information;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.*;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.util.Map;

/**
 * InformationController
 *
 * @author hankaibo
 * @date 2019/10/26
 */
@Slf4j
@Controller
public class InformationController {

    private SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    public InformationController(SimpMessagingTemplate simpMessagingTemplate) {
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    public void test(Message message, MessageHeaders messageHeaders, @Header("destination") String destination, @Headers Map<String, Object> headers, @DestinationVariable long id, @Payload String body) {
        log.info("[test] Message: {}", message);
        log.info("[test] MessageHeaders: {}", messageHeaders);
        log.info("[test] Header: {}", destination);
        log.info("[test] Headers: {}", headers);
        log.info("[test] DestinationVariable: {}", id);
        log.info("[test] Payload: {}", body);
    }

    @MessageMapping("/hello")
    public void hello(@Payload String body) {
        simpMessagingTemplate.convertAndSend("/sub/public", "reply hello");
    }

    public String hello1(@Payload)
}
