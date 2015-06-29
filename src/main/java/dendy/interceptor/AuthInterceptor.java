package dendy.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>Created by Dendy on 2015/6/25.
 *
 * @author Dendy
 * @version 0.1
 * @since 0.1
 */
public class AuthInterceptor extends HandlerInterceptorAdapter {
    //~ Static fields/initializers =====================================================================================
    private static final Logger LOG = LoggerFactory.getLogger(AuthInterceptor.class);

    //~ Instance fields ================================================================================================

    //~ Methods ========================================================================================================

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //TODO 权限验证
        return super.preHandle(request, response, handler);
    }
}
