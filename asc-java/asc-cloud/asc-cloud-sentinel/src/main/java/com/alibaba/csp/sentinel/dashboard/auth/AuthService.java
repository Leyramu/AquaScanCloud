/*
 * Copyright 1999-2018 Alibaba Group Holding Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.alibaba.csp.sentinel.dashboard.auth;

/**
 * 用于身份验证和授权的接口
 *
 * @author Carpenter Lee
 * @author <a href="mailto:2038322151@qq.com">Miraitowa_zcx</a>
 * @version 1.5.0
 * @since 2024/9/3
 */
public interface AuthService<R> {

    /**
     * 获取鉴权用户
     *
     * @param request 请求包含用户信息
     * @return auth 用户代表当前用户，当用户不合法时，会返回 null 值
     */
    AuthUser getAuthUser(R request);

    /**
     * 权限类型
     */
    enum PrivilegeType {

        /**
         * 读取规则
         */
        READ_RULE,

        /**
         * 创建或修改规则
         */
        WRITE_RULE,

        /**
         * 删除规则
         */
        DELETE_RULE,

        /**
         * 读取指标
         */
        READ_METRIC,

        /**
         * 添加机器
         */
        ADD_MACHINE,

        /**
         * 授予以上所有权限
         */
        ALL
    }

    /**
     * 表示当前用户
     */
    interface AuthUser {

        /**
         * 查询当前用户是否具有目标的特定权限
         *
         * @param target        要检查的目标
         * @param privilegeType 要检查的权限类型
         * @return 如果当前用户具有目标的特定权限，则返回 true，否则返回 false
         */
        boolean authTarget(String target, PrivilegeType privilegeType);

        /**
         * 检查当前用户是否为超级用户
         *
         * @return 如果当前用户是超级用户，则返回 true，否则返回 false
         */
        boolean isSuperUser();

        /**
         * 获取当前用户的昵称
         *
         * @return 当前用户的昵称
         */
        String getNickName();

        /**
         * 获取当前用户的登录名
         *
         * @return 当前用户的登录名
         */
        String getLoginName();

        /**
         * 获取当前用户的 ID
         *
         * @return 当前用户的 ID
         */
        String getId();
    }
}
