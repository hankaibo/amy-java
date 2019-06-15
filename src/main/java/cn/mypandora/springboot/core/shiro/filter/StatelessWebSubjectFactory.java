package cn.mypandora.springboot.core.shiro.filter;

import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.subject.SubjectContext;
import org.apache.shiro.web.mgt.DefaultWebSubjectFactory;

/* *
 * @Author tomsun28
 * @Description 对于无状态的TOKEN不创建session 这里都不使用session
 * @Date 21:52 2018/3/3
 */
public class StatelessWebSubjectFactory extends DefaultWebSubjectFactory {

    @Override
    public Subject createSubject(SubjectContext context) {
        // 这里都不创建session
        AuthenticationToken token = context.getAuthenticationToken();
        context.setSessionCreationEnabled(Boolean.FALSE);
        return super.createSubject(context);
    }

    public StatelessWebSubjectFactory() {}

}
