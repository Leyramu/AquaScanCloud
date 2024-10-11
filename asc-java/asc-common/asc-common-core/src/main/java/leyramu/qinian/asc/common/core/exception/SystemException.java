/*
 * Copyright (c) 2024 Leyramu. All rights reserved.
 * This project (Digitalization Education), including its source code, documentation, and any associated materials, is the intellectual property of Leyramu. No part of this software may be reproduced, distributed, or transmitted in any form or by any means, including photocopying, recording, or other electronic or mechanical methods, without the prior written permission of the copyright owner, Miraitowa_zcx, except in the case of brief quotations embodied in critical reviews and certain other noncommercial uses permitted by copyright law.
 * For inquiries related to licensing or usage outside the scope of this notice, please contact the copyright holder at 2038322151@qq.com.
 * The author disclaims all warranties, express or implied, including but not limited to the warranties of merchantability and fitness for a particular purpose. Under no circumstances shall the author be liable for any special, incidental, indirect, or consequential damages arising from the use of this software.
 * By using this project, users acknowledge and agree to abide by these terms and conditions.
 */

package leyramu.qinian.asc.common.core.exception;

/**
 * 系统异常
 *
 * @author <a href="mailto:2038322151@qq.com">Miraitowa_zcx</a>
 * @version 1.0.0
 * @since 2024/9/14
 */
public final class SystemException extends GlobalException {

    /**
     * IP已列入黑名单
     */
    public static final String IP_BLACKED = "S_Ip_Blacked";

    /**
     * IP被限制
     */
    public static final String IP_RESTRICTED = "S_Ip_Restricted";

    /**
     * 路由不存在
     */
    public static final String ROUTER_NOT_EXIST = "S_Gateway_RouterNotExist";

    /**
     * 授权规则错误
     */
    public static final String AUTHORITY = "S_Sentinel_Authority";

    /**
     * 系统规则错误
     */
    public static final String SYSTEM_BLOCKED = "S_Sentinel_SystemBlocked";

    /**
     * 热点参数已限流
     */
    public static final String PARAM_FLOWED = "S_Sentinel_ParamFlowed";

    /**
     * 已降级
     */
    public static final String DEGRADED = "S_Sentinel_Degraded";

    /**
     * 已限流
     */
    public static final String FLOWED = "S_Sentinel_Flowed";

    /**
     * 分布式事务已宕机
     */
    public static final String DISTRIBUTED_TRANSACTION_DOWNTIME = "S_Seata_TransactionDowntime";

    /**
     * 分布式事务已超时
     */
    public static final String DISTRIBUTED_TRANSACTION_TIMEOUT = "S_Seata_TransactionTimeout";

    /**
     * 表不存在
     */
    public static final String TABLE_NOT_EXIST = "S_DS_TableNotExist";

    /**
     * 用户名解密失败
     */
    public static final String AES_DECRYPT_USERNAME_FAIL = "S_User_AESDecryptUsernameFail";

    /**
     * 手机号解密失败
     */
    public static final String AES_DECRYPT_MOBILE_FAIL = "S_User_AESDecryptMobileFail";

    /**
     * 邮箱解密失败
     */
    public static final String AES_DECRYPT_MAIL_FAIL = "S_User_AESDecryptMailFail";

    /**
     * 构造方法
     *
     * @param code 异常码
     */
    public SystemException(String code) {
        super(code);
    }

    /**
     * 构造方法
     *
     * @param code 异常码
     * @param msg  异常信息
     */
    public SystemException(String code, String msg) {
        super(code, msg);
    }
}
