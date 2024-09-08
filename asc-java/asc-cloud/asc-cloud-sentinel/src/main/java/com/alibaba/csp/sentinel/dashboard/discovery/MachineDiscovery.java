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

package com.alibaba.csp.sentinel.dashboard.discovery;

import java.util.List;
import java.util.Set;

/**
 * @author Sentinel
 * @author <a href="mailto:2038322151@qq.com">Miraitowa_zcx</a>
 * @version 1.0.0
 * @since 2024/9/3
 */
public interface MachineDiscovery {

    /**
     * 未知的应用程序名称
     */
    String UNKNOWN_APP_NAME = "CLUSTER_NOT_STARTED";

    /**
     * 获取应用程序名称列表
     *
     * @return 应用程序名称列表
     */
    List<String> getAppNames();

    /**
     * 获取应用程序列表，其中包含应用程序名称、IP 和端口等基本信息，但不包含详细信息
     *
     * @return 应用程序列表
     */
    Set<AppInfo> getBriefApps();

    /**
     * 获取应用程序详情
     *
     * @param app 应用程序名称
     * @return 应用程序详情
     */
    AppInfo getDetailApp(String app);

    /**
     * 从应用程序注册表中删除给定的应用程序
     *
     * @param app 应用程序名称
     */
    void removeApp(String app);

    /**
     * 将给定的计算机添加到应用程序注册表
     *
     * @param machineInfo 机器信息
     * @return 计算机 ID
     */
    long addMachine(MachineInfo machineInfo);

    /**
     * 从应用程序注册表中删除给定的计算机实例
     *
     * @param app  计算机的应用程序名称
     * @param ip   机器 IP
     * @param port 机器端口
     * @return 如果删除，则为 true，否则为 false
     */
    boolean removeMachine(String app, String ip, int port);
}
