package com.tom.dengshaobing.common.bo.wmp.json;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author TommyDeng <250575979@qq.com>
 * @version 创建时间：2016年9月27日 下午12:21:33
 *
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Oauth2UserInfo extends Errorable {
	private static final long serialVersionUID = 8439265802152712533L;
	public String openid;// 用户的标识，对当前公众号唯一
	public String nickname;// 用户的昵称
	public String sex;// 用户的性别，值为1时是男性，值为2时是女性，值为0时是未知
	public String city;// 用户所在城市
	public String country;// 用户所在国家
	public String province;// 用户所在省份
	public String language;// 用户的语言，简体中文为zh_CN
	public String headimgurl;// 用户头像，最后一个数值代表正方形头像大小（有0、46、64、96、132数值可选，0代表640*640正方形头像），用户没有头像时该项为空。若用户更换头像，原有头像URL将失效。

	public String subscribe;// 用户是否订阅该公众号标识，值为0时，代表此用户没有关注该公众号，拉取不到其余信息。
	public String subscribe_time;// 用户关注时间，为时间戳。如果用户曾多次关注，则取最后关注时间
	public String unionid;// 只有在用户将公众号绑定到微信开放平台帐号后，才会出现该字段。详见：获取用户个人信息（UnionID机制）
	public String remark;// 公众号运营者对粉丝的备注，公众号运营者可在微信公众平台用户管理界面对粉丝添加备注
	public String groupid;// 用户所在的分组ID
}
