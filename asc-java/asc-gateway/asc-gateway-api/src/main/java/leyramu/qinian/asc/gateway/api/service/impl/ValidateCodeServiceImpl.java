/*
 * Copyright (c) 2024 Leyramu. All rights reserved.
 * This project (Digitalization Education), including its source code, documentation, and any associated materials, is the intellectual property of Leyramu. No part of this software may be reproduced, distributed, or transmitted in any form or by any means, including photocopying, recording, or other electronic or mechanical methods, without the prior written permission of the copyright owner, Miraitowa_zcx, except in the case of brief quotations embodied in critical reviews and certain other noncommercial uses permitted by copyright law.
 * For inquiries related to licensing or usage outside the scope of this notice, please contact the copyright holder at 2038322151@qq.com.
 * The author disclaims all warranties, express or implied, including but not limited to the warranties of merchantability and fitness for a particular purpose. Under no circumstances shall the author be liable for any special, incidental, indirect, or consequential damages arising from the use of this software.
 * By using this project, users acknowledge and agree to abide by these terms and conditions.
 */

package leyramu.qinian.asc.gateway.api.service.impl;

import com.google.code.kaptcha.Producer;
import jakarta.annotation.Resource;
import leyramu.qinian.asc.gateway.api.config.properties.CaptchaProperties;
import leyramu.qinian.asc.gateway.api.service.ValidateCodeService;
import leyramu.qinian.asc.common.core.constant.CacheConstants;
import leyramu.qinian.asc.common.core.constant.Constants;
import leyramu.qinian.asc.common.core.exception.CaptchaException;
import leyramu.qinian.asc.common.core.utils.StringUtils;
import leyramu.qinian.asc.common.core.utils.sign.Base64;
import leyramu.qinian.asc.common.core.utils.uuid.IdUtils;
import leyramu.qinian.asc.common.core.web.domain.AjaxResult;
import leyramu.qinian.asc.common.redis.service.RedisService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.FastByteArrayOutputStream;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * 验证码实现处理
 *
 * @author <a href="mailto:2038322151@qq.com">Miraitowa_zcx</a>
 * @version 1.0.0
 * @since 2024/7/23
 */
@Service
@RequiredArgsConstructor(onConstructor_ = @__(@Autowired))
public class ValidateCodeServiceImpl implements ValidateCodeService {

    /**
     * 数字验证码生成器
     */
    @Resource(name = "captchaProducer")
    private Producer captchaProducer;

    /**
     * 数字验证码生成器
     */
    @Resource(name = "captchaProducerMath")
    private Producer captchaProducerMath;

    /**
     * 缓存管理器
     */
    private final RedisService redisService;

    /**
     * 验证码配置
     */
    private final CaptchaProperties captchaProperties;

    /**
     * 生成验证码
     *
     * @return AjaxResult
     * @throws CaptchaException 验证码异常
     */
    @Override
    public AjaxResult createCaptcha() throws CaptchaException {
        AjaxResult ajax = AjaxResult.success();
        boolean captchaEnabled = captchaProperties.getEnabled();
        ajax.put("captchaEnabled", captchaEnabled);
        if (!captchaEnabled) {
            return ajax;
        }

        // 保存验证码信息
        String uuid = IdUtils.simpleUuid();
        String verifyKey = CacheConstants.CAPTCHA_CODE_KEY + uuid;

        String capStr, code = null;
        BufferedImage image = null;

        String captchaType = captchaProperties.getType();
        // 生成验证码
        if ("math".equals(captchaType)) {
            String capText = captchaProducerMath.createText();
            capStr = capText.substring(0, capText.lastIndexOf("@"));
            code = capText.substring(capText.lastIndexOf("@") + 1);
            image = captchaProducerMath.createImage(capStr);
        } else if ("char".equals(captchaType)) {
            capStr = code = captchaProducer.createText();
            image = captchaProducer.createImage(capStr);
        }

        redisService.setCacheObject(verifyKey, code, Constants.CAPTCHA_EXPIRATION, TimeUnit.MINUTES);
        // 转换流信息写出
        FastByteArrayOutputStream os = new FastByteArrayOutputStream();
        try {
            if (image != null) {
                ImageIO.write(image, "jpg", os);
            }
        } catch (IOException e) {
            return AjaxResult.error(e.getMessage());
        }

        ajax.put("uuid", uuid);
        ajax.put("img", Base64.encode(os.toByteArray()));
        return ajax;
    }

    /**
     * 校验验证码
     *
     * @param code 验证码
     * @param uuid 唯一标识
     * @throws CaptchaException 验证码异常
     */
    @Override
    public void checkCaptcha(String code, String uuid) throws CaptchaException {
        if (StringUtils.isEmpty(code)) {
            throw new CaptchaException("验证码不能为空");
        }
        String verifyKey = CacheConstants.CAPTCHA_CODE_KEY + StringUtils.nvl(uuid, "");
        String captcha = redisService.getCacheObject(verifyKey);
        if (captcha == null) {
            throw new CaptchaException("验证码已失效");
        }
        redisService.deleteObject(verifyKey);
        if (!code.equalsIgnoreCase(captcha)) {
            throw new CaptchaException("验证码错误");
        }
    }
}
