package cn.mypandora.springboot.config.shiro;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.subject.SubjectContext;
import org.apache.shiro.web.mgt.DefaultWebSubjectFactory;

import lombok.NoArgsConstructor;

/**
 * StatelessWebSubjectFactory
 *
 * @author hankaibo
 * @date 2019/6/18
 * @see <a href="https://jinnianshilongnian.iteye.com/blog/2041909">more</a>
 */
@NoArgsConstructor
public class StatelessWebSubjectFactory extends DefaultWebSubjectFactory {

    @Override
    public Subject createSubject(SubjectContext context) {
        // 不创建session
        context.setSessionCreationEnabled(Boolean.FALSE);
        return super.createSubject(context);
    }

}
