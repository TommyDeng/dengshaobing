package com.tom.dengshaobing.sqlstatements;

/**
 * @author TommyDeng <250575979@qq.com>
 * @version 创建时间：2016年9月28日 下午2:21:18
 *
 */

public class SqlStatements {

	public static final String getAllVisitor = "SELECT NAME as 访问者,to_char(CREATE_TIME,'yyyy-MM-dd hh:mm:ss') as 访问时间 FROM LOG_VISIT order by id desc";

}
