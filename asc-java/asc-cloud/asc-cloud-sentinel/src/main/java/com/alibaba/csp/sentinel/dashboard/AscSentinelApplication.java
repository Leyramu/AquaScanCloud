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

package com.alibaba.csp.sentinel.dashboard;

import com.alibaba.csp.sentinel.init.InitExecutor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Sentinel 仪表板应用程序
 *
 * @author Carpenter Lee
 * @author <a href="mailto:2038322151@qq.com">Miraitowa_zcx</a>
 * @version 1.8.8
 * @since 2024/9/3
 */
@Slf4j
@SpringBootApplication
public class AscSentinelApplication {

    /**
     * 启动 Sentinel 模块
     *
     * @param args 命令行参数
     */
    public static void main(String[] args) {
        triggerSentinelInit();
        SpringApplication.run(AscSentinelApplication.class, args);
        log.info("""
                Sentinel 模块 服务启动成功
                       __   _         ________        __         \s
                      |  ] (_)       |_   __  |      |  ]        \s
                  .--.| |  __   .--./) | |_ \\_|  .--.| | __   _  \s
                / /'`\\' | [  | / /'`\\; |  _| _ / /'`\\' |[  | | | \s
                | \\__/  |  | | \\ \\._//_| |__/ || \\__/  | | \\_/ |,\s
                 '.__.;__][___].',__`|________| '.__.;__]'.__.'_/\s
                              ( ( __))     \s
                """);
    }

    /**
     * 触发 Sentinel 初始化
     */
    private static void triggerSentinelInit() {
        new Thread(InitExecutor::doInit).start();
    }
}
