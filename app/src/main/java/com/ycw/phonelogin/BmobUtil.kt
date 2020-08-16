package com.ycw.phonelogin

import android.widget.Toast
import cn.bmob.v3.BmobSMS
import cn.bmob.v3.exception.BmobException
import cn.bmob.v3.listener.QueryListener
import cn.bmob.v3.listener.UpdateListener

/**
 * @Description
 * @Author 闫彩威
 * @QQ
 */
//静态类
object BmobUtil {
    const val SUCCESS = 0
    const val FAILURE = 1
    //向服务器请求 -> 发送验证码  成功/失败
    //需要验证用户输入的验证码    成功/失败

    fun requestSMSCode(phone: String,callBack:(Int)->Unit){
        //使用bmob向这个用户发送数据
        BmobSMS.requestSMSCode(phone,"",object: QueryListener<Int>(){
            override fun done(p0: Int?, p1: BmobException?) {
                //回调数据
                if (p1 == null){
                    //成功
                    callBack(SUCCESS)
                }else{
                    //失败
                    callBack(FAILURE)
                }
            }
        })
    }
    //验证输入的验证码
    fun verifySMSCode(phone: String,code: String,callBack: (Int) -> Unit){
        BmobSMS.verifySmsCode(phone,code,object : UpdateListener(){
            override fun done(p0: BmobException?) {
                if (p0 == null){
                    //验证成功
                    callBack(SUCCESS)
                }else{
                    //验证失败
                    callBack(FAILURE)
                }
            }
        })
    }
}