package com.ycw.phonelogin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import cn.bmob.v3.Bmob
import cn.bmob.v3.BmobSMS
import cn.bmob.v3.exception.BmobException
import cn.bmob.v3.listener.QueryListener
import kotlinx.android.synthetic.main.activity_verify.*

class VerifyActivity : AppCompatActivity() {
    private val numViews: Array<TextView> by lazy {
        arrayOf(num1,num2,num3,num4,num5,num6)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_verify)

        //获取数据
        intent.getStringExtra("phone").also {
            //显示号码
            mPhone.text = it
        }

        //监听输入框的内容改变事件
        numView.addTextChangedListener(object :TextWatcher{
            override fun afterTextChanged(s: Editable?) {}

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                //将输入的内容拆分到每一个textView中
                //刷新一下文本框
                for (item in numViews){
                    item.text = ""
                }
                //给文本框输入内容
                for ((i,item) in s!!.withIndex()){
                    //获取i对应的textView
                    numViews[i].text = item.toString()
                }

                //判断验证码是不是6个
                if (s.length == 6){
                    //发起验证的请求
                    BmobUtil.verifySMSCode(mPhone.text.toString(),s.toString()){
                        if (it == BmobUtil.SUCCESS){
                            Intent().apply {
                                setClass(this@VerifyActivity,HomeActivity::class.java)
                                startActivity(this)
                            }
                        }else{
                            Toast.makeText(this@VerifyActivity,"验证码错误",Toast.LENGTH_SHORT).show()
                            numView.text.clear()
                        }
                    }
                }
            }

        })
    }

    override fun onResume() {
        super.onResume()
        BmobUtil.requestSMSCode(mPhone.text.toString()){
            if (it == BmobUtil.SUCCESS){
                Toast.makeText(this,"发送验证码成功",Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(this,"发送验证码失败",Toast.LENGTH_SHORT).show()
            }
        }
    }
}
