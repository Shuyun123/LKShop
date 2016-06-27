package net.anumbrella.lkshop.api;

/**
 * author：Anumbrella
 * Date：16/5/30 下午2:48
 */
public class ServiceApi {

    /**
     * 注册接口:
     * <p>
     * result:0101  //注册手机号类型不对
     * result:0102  //注册手机号已经注册
     * result:0103  //注册用户名已经存在
     * result:0104  //两次密码不一致
     * result:0200  //注册成功
     * result:0404  //注册失败
     */

    public final static String[][] RegisterApi = {
            {"0101", "手机号类型不对"},
            {"0102", "手机号已经注册"},
            {"0103", "用户名已经存在"},
            {"0104", "两次密码不一致"},
            {"0200", "注册成功"},
            {"0400", "注册失败"},
    };


    /**
     * 用户登录接口
     * <p>
     * result:0102   //手机号不存在
     * result:0100   //密码不正确
     * result:用户注册id,用户名称   //登录成功
     */
    public final static String[][] LoginApi = {
            {"0100", "密码不正确"},
            {"0102", "手机号不存在"},

    };


}
