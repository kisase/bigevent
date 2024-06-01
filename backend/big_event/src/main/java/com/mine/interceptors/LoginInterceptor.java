package com.mine.interceptors;

import com.mine.utils.JWTUtil;
import com.mine.utils.ThreadLocalUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Map;

@Component
public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        //令牌验证
         String token = request.getHeader("Authorization");
        //验证token
        try {
            Map<String, Object> claims = JWTUtil.parseToken(token);
            //把业务数据储存到ThreadLocal
            ThreadLocalUtil.set(claims);
            //放行
            return true;
        }catch (Exception e){
            //http响应状态码为401
            response.setStatus(401);
            //不放行
            return false;
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        //清空ThreadLocal中的数据
        ThreadLocalUtil.remove();
    }
}
