/*
 * Copyright (c) 2024 Leyramu. All rights reserved.
 * This project (Digitalization Education), including its source code, documentation, and any associated materials, is the intellectual property of Leyramu. No part of this software may be reproduced, distributed, or transmitted in any form or by any means, including photocopying, recording, or other electronic or mechanical methods, without the prior written permission of the copyright owner, Miraitowa_zcx, except in the case of brief quotations embodied in critical reviews and certain other noncommercial uses permitted by copyright law.
 * For inquiries related to licensing or usage outside the scope of this notice, please contact the copyright holder at 2038322151@qq.com.
 * The author disclaims all warranties, express or implied, including but not limited to the warranties of merchantability and fitness for a particular purpose. Under no circumstances shall the author be liable for any special, incidental, indirect, or consequential damages arising from the use of this software.
 * By using this project, users acknowledge and agree to abide by these terms and conditions.
 */

package leyramu.softwarecup.digEdu.common.security.interceptor;

import leyramu.softwarecup.digEdu.common.security.auth.AuthUtil;
import leyramu.softwarecup.digEdu.common.security.utils.SecurityUtils;
import jakarta.annotation.Nonnull;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import leyramu.softwarecup.digEdu.common.core.constant.SecurityConstants;
import leyramu.softwarecup.digEdu.common.core.context.SecurityContextHolder;
import leyramu.softwarecup.digEdu.common.core.utils.ServletUtils;
import leyramu.softwarecup.digEdu.common.core.utils.StringUtils;
import leyramu.softwarecup.digEdu.system.api.model.LoginUser;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.AsyncHandlerInterceptor;

/**
 * 自定义请求头拦截器，将Header数据封装到线程变量中方便获取
 *
 * @author <a href="mailto:2038322151@qq.com">Miraitowa_zcx</a>
 * @version 1.0.0
 * @since 2024/7/24
 */
public class HeaderInterceptor implements AsyncHandlerInterceptor {

    /**
     * 拦截请求
     * @param request 请求
     * @param response 响应
     * @param handler 处理器
     * @return 是否继续执行
     */
    @Override
    public boolean preHandle(
            @Nonnull HttpServletRequest request,
            @Nonnull HttpServletResponse response,
            @Nonnull Object handler) {
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        SecurityContextHolder.setUserId(ServletUtils.getHeader(request, SecurityConstants.DETAILS_USER_ID));
        SecurityContextHolder.setUserName(ServletUtils.getHeader(request, SecurityConstants.DETAILS_USERNAME));
        SecurityContextHolder.setUserKey(ServletUtils.getHeader(request, SecurityConstants.USER_KEY));

        String token = SecurityUtils.getToken();
        if (StringUtils.isNotEmpty(token)) {
            LoginUser loginUser = AuthUtil.getLoginUser(token);
            if (StringUtils.isNotNull(loginUser)) {
                AuthUtil.verifyLoginUserExpire(loginUser);
                SecurityContextHolder.set(SecurityConstants.LOGIN_USER, loginUser);
            }
        }
        return true;
    }

    /**
     * 请求处理之后进行调用，但是在视图被渲染之前（Controller方法调用之后）
     * @param request 请求
     * @param response 响应
     * @param handler 处理器
     * @param ex 异常
     */
    @Override
    public void afterCompletion(
            @Nonnull HttpServletRequest request,
            @Nonnull HttpServletResponse response,
            @Nonnull Object handler,
            Exception ex) {
        SecurityContextHolder.remove();
    }
}
