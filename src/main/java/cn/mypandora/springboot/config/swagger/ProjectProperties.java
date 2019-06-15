package cn.mypandora.springboot.config.swagger;

import cn.mypandora.springboot.core.enums.EnvironmentEnum;
import cn.mypandora.springboot.core.enums.EnvironmentGroupEnum;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Data
@Repository
@ConfigurationProperties(prefix = "project")
@EnableConfigurationProperties(ProjectProperties.class)
public class ProjectProperties {

    private String name;
    private String version;
    private String description;
    private String groupId;
    private String artifactId;
    private String[] env;
    private ProjectAuthorProperties author;
    private final Environment environment;

    @Autowired
    public ProjectProperties(Environment environment) {
        this.environment = environment;
        this.env = environment.getActiveProfiles();
    }

    /**
     * 判断是否是生产环境。
     *
     * @return  boolean 判断结果
     */
    public boolean isProduct() {
        List<String> runtimeEnvs = new ArrayList<>();
        for (String s : this.env) {
            if (EnvironmentGroupEnum.isRuntime(s)) {
                runtimeEnvs.add(s);
            }
        }
        if (runtimeEnvs.size() == 0) {
            return false;
        }
        //最后一个运行环境, 如果spring.profiles.active=dev, prod, mysql  则运行环境为dev, prod, 最后一个运行环境为prod，是生产环境
        String env = runtimeEnvs.get(runtimeEnvs.size() - 1);
        return EnvironmentEnum.PROD.getName().equals(env);
    }
}
