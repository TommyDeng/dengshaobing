package com.tom.dengshaobing.common;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.transaction.annotation.Transactional;

/**
 * @author TommyDeng <250575979@qq.com>
 * @version 创建时间：2016年9月16日 下午3:20:25
 *
 */
@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Transactional("DefaultTx")
public @interface DefaultTx {

}
