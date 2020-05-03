package me.zhoudongyu.core.Session;

import javax.servlet.http.HttpSession;
import java.util.HashMap;

/**
 * Servlet2.1之后不支持SessionContext里面getSession(String id)方法
 * 通过HttpSessionListener监听器和全局静态map自己实现一个SessionContext
 */
public class MySessionContext {
    private static MySessionContext instance;
    private HashMap mymap;

    private MySessionContext() {
        mymap = new HashMap();
    }

    public static MySessionContext getInstance() {
        if (instance == null) {
            instance = new MySessionContext();
        }
        return instance;
    }

    public synchronized void AddSession(HttpSession session) {
        if (session != null) {
            mymap.put(session.getId(), session);
        }
    }

    public synchronized void DelSession(HttpSession session) {
        if (session != null) {
            mymap.remove(session.getId());
        }
    }

    public synchronized HttpSession getSession(String session_id) {
        if (session_id == null) return null;
        return (HttpSession) mymap.get(session_id);
    }

}
