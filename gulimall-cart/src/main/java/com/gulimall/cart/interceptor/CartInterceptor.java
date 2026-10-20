package com.gulimall.cart.interceptor;

import com.gulimall.cart.vo.UserInfoTo;
import com.gulimall.common.constant.AuthConstants;
import com.gulimall.common.constant.CartConstant;
import com.gulimall.common.utils.StringUtils;
import com.gulimall.common.utils.uuid.UUID;
import com.gulimall.common.vo.MemberVo;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.jspecify.annotations.Nullable;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class CartInterceptor implements HandlerInterceptor {
    //重写preHandle,处理执行目标方法前判断登录状态

    public static ThreadLocal<UserInfoTo> userInfoToThreadLocal = new ThreadLocal<>();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        MemberVo member = (MemberVo) session.getAttribute(AuthConstants.LOGIN_USER);
        UserInfoTo userInfoTo = new UserInfoTo();
        if(member == null){
            userInfoTo.setTempUser(true);
            //没登录，临时用户，看cookie有无user-key？无放session一个新user-key，有则得到
            Cookie[] cookies = request.getCookies();
            if (cookies != null&&cookies.length>0) {
                for(Cookie cookie : cookies){
                    if(StringUtils.equals(cookie.getName(), CartConstant.TEMP_USER_COOKIE_NAME)){
                        //已有user-key
                        userInfoTo.setUserKey(cookie.getValue());
//                        userInfoTo.setTempUser(true);
                    }
                }
            }
        }else{
            //已登录
            userInfoTo.setUserId(member.getId().toString());
        }

        if(StringUtils.isEmpty(userInfoTo.getUserKey())){
            String string = UUID.randomUUID().toString();
            userInfoTo.setUserKey(string);
//            userInfoTo.setTempUser(true);
        }
        //放入ThreadLocal便于快速得到值
        userInfoToThreadLocal.set(userInfoTo);
        return true;
    }

    //方法执行结束后，让浏览器把user-key放到cookie（第一次登录的人）
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable ModelAndView modelAndView) throws Exception {
        UserInfoTo userInfoTo = userInfoToThreadLocal.get();
        if (userInfoTo.getTempUser()){
            Cookie cookie = new Cookie(CartConstant.TEMP_USER_COOKIE_NAME, userInfoTo.getUserKey());
            cookie.setDomain(AuthConstants.GULIMALL_SESSION_DOMAIN_NAME);
            cookie.setMaxAge(CartConstant.TEMP_USER_COOKIE_TIMEOUT);
            response.addCookie(cookie);
        }
//        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }
}
