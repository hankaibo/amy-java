package cn.mypandora.springboot.modular.your;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author hankaibo
 * @date 2020/5/13
 */

@RestController
@RequestMapping("/api/v1/hello")
public class HelloController {

    @GetMapping()
    public String helloWorld() {
        return "Hello World";
    }
}
