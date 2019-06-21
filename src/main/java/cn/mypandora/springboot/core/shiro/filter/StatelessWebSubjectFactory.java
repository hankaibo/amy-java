package cn.mypandora.springboot.core.shiro.filter;

import lombok.NoArgsConstructor;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.subject.SubjectContext;
import org.apache.shiro.web.mgt.DefaultWebSubjectFactory;

/**
 * StatelessWebSubjectFactory
 *
 * @author hankaibo
 * @date 2019/6/18
 */
public class StatelessWebSubjectFactory extends DefaultWebSubjectFactory {
    @Override
    public Subject createSubject(SubjectContext context) {
        // 这里都不创建session
        context.setSessionCreationEnabled(Boolean.FALSE);
        return super.createSubject(context);
    }

    public StatelessWebSubjectFactory() {}

}
