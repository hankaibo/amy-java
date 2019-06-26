package cn.mypandora.springboot.modular.zhizhu.controller;

import cn.mypandora.springboot.core.base.Result;
import cn.mypandora.springboot.core.base.ResultGenerator;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * HomeController
 *
 * @author hankaibo
 * @date 2019/6/23
 */
@Api(tags = "首页管理")
@RestController
@RequestMapping("/api/v1/home")
public class HomeController {

    /**
     * @return ok
     */
    @GetMapping
    public Result hello() {
        return ResultGenerator.success();
    }
}


