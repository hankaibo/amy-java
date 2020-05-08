package cn.mypandora.springboot.modular.system.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import cn.mypandora.springboot.modular.system.model.po.Department;
import cn.mypandora.springboot.modular.system.service.DepartmentService;

/**
 * <p>
 * 单元测试: DepartmentController
 * </p>
 * 本测试环境如下：SpringBoot 2.2.6 + junit5 + Mybatis 。
 * 当配置Mybatis框架的@MapperScan注解时，Spring会去尝试实例化Mapper实例，但是因为我们使用的是@WebMvcTest注解，Spring不会去实例化Mapper所依赖的sqlSessionFactory等自动配置的组件，最终导致依赖注解失败，无法构建Spring上下文环境。
 * 解决方法一是使用官方的@AutoConfigureMybatis注解；二是内部类缩小扫描包范围。
 * 
 * @author hankaibo
 * @date 2020/5/5
 * 
 * @see <a href="https://zhuanlan.zhihu.com/p/67801427">基于spring-boot的单元和集成测试方案</a>
 * @see <a href="https://github.com/mybatis/spring-boot-starter/issues/224>AutoConfigureMybatis</a>
 * @see <a href="https://github.com/mybatis/spring-boot-starter/issues/227>AutoConfigureMybatis</a>
 */
// 方法一
// @AutoConfigureMybatis
@WebMvcTest(DepartmentController.class)
public class DepartmentControllerTest {

    private static final ObjectMapper om = new ObjectMapper();
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private DepartmentService service;

    @Test
    public void listDepartmentTree() throws Exception {
        // 模拟数据
        List<Department> departmentList = new ArrayList<>();
        Department department = new Department();
        department.setName("Mock");
        departmentList.add(department);
        // 添加模拟数据
        when(service.listDepartment(1, 1L)).thenReturn(departmentList);
        // 调用返回上面定义的模拟数据，避免与 service 层深度耦合。
        this.mockMvc.perform(get("/api/v1/departments").contentType(MediaType.APPLICATION_JSON)).andDo(print())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
            .andExpect(content().string(departmentList.toString()));
    }

    @Test
    public void listDepartmentChildren() {}

    @Test
    public void addDepartment() throws Exception {}

    @Test
    public void getDepartmentById() {}

    @Test
    public void updateDepartment() {}

    @Test
    public void enableDepartment() {}

    @Test
    public void deleteDepartment() {}

    @Test
    public void moveDepartment() {}

    // 方法二
    @SpringBootApplication(scanBasePackages = {"cn.mypandora.springboot.modular.system.controller"})
    static class InnerConfig {}
}