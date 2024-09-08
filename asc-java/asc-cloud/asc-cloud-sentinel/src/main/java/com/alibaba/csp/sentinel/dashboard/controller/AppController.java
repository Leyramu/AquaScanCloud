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

package com.alibaba.csp.sentinel.dashboard.controller;

import com.alibaba.csp.sentinel.dashboard.discovery.AppInfo;
import com.alibaba.csp.sentinel.dashboard.discovery.AppManagement;
import com.alibaba.csp.sentinel.dashboard.discovery.MachineInfo;
import com.alibaba.csp.sentinel.dashboard.domain.Result;
import com.alibaba.csp.sentinel.dashboard.domain.vo.MachineInfoVo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * 应用控制器
 *
 * @author Carpenter Lee
 * @author <a href="mailto:2038322151@qq.com">Miraitowa_zcx</a>
 * @version 1.0.0
 * @since 2024/9/3
 */
@RestController
@RequestMapping(value = "/app")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AppController {

    /**
     * 应用管理
     */
    private final AppManagement appManagement;

    /**
     * 获取应用名称列表
     *
     * @param ignoredRequest 请求
     * @return 应用名称列表
     */
    @GetMapping("/names.json")
    public Result<List<String>> queryApps(HttpServletRequest ignoredRequest) {
        return Result.ofSuccess(appManagement.getAppNames());
    }

    /**
     * 获取应用信息列表
     *
     * @param ignoredRequest 请求
     * @return 应用信息列表
     */
    @GetMapping("/briefinfos.json")
    public Result<List<AppInfo>> queryAppInfos(HttpServletRequest ignoredRequest) {
        List<AppInfo> list = new ArrayList<>(appManagement.getBriefApps());
        list.sort(Comparator.comparing(AppInfo::getApp));
        return Result.ofSuccess(list);
    }

    /**
     * 获取应用机器信息列表
     *
     * @param app 应用名称
     * @return 应用机器信息列表
     */
    @GetMapping(value = "/{app}/machines.json")
    public Result<List<MachineInfoVo>> getMachinesByApp(@PathVariable("app") String app) {
        AppInfo appInfo = appManagement.getDetailApp(app);
        if (appInfo == null) {
            return Result.ofSuccess(null);
        }
        List<MachineInfo> list = new ArrayList<>(appInfo.getMachines());
        list.sort(Comparator.comparing(MachineInfo::getApp).thenComparing(MachineInfo::getIp).thenComparingInt(MachineInfo::getPort));
        return Result.ofSuccess(MachineInfoVo.fromMachineInfoList(list));
    }

    /**
     * 移除应用机器信息
     *
     * @param app 应用名称
     * @return 移除结果
     */
    @RequestMapping(value = "/{app}/machine/remove.json")
    public Result<String> removeMachineById(
            @PathVariable("app") String app,
            @RequestParam(name = "ip") String ip,
            @RequestParam(name = "port") int port) {
        AppInfo appInfo = appManagement.getDetailApp(app);
        if (appInfo == null) {
            return Result.ofSuccess(null);
        }
        if (appManagement.removeMachine(app, ip, port)) {
            return Result.ofSuccessMsg("success");
        } else {
            return Result.ofFail(1, "remove failed");
        }
    }
}
