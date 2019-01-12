package cn.mypandora.springbootdemo.common.utils;

import cn.mypandora.springbootdemo.common.entity.User;
import cn.mypandora.springbootdemo.common.enums.SessionEnum;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;

/**
 * SessionUtil
 *
 * @author hankaibo
 * @date 2019/1/12
 */
public class SessionUtil {
    public static Session getCurrentSession() {
        Subject subject = SecurityUtils.getSubject();
        return subject == null ? null : subject.getSession();
    }

    public static String getCurrentSessionId() {
        return getCurrentSession() == null ? null : getCurrentSession().getId().toString();
    }

    public static User getCurrentUser() {
        return getCurrentSession() == null ? null : (User) getCurrentSession().getAttribute(SessionEnum.CURRENT_USER.getValue());
    }

    public static Long getCurrentUserId() {
        return getCurrentUser() == null ? null : getCurrentUser().getId();
    }

    public static void setAttribute(String key, Object value) {
        Session session = getCurrentSession();
        if (session != null) {
            session.setAttribute(key, value);
        }
    }

    public static void setAttribute(SessionEnum sessionEnum, Object value) {
        setAttribute(sessionEnum.getValue(), value);
    }
}
