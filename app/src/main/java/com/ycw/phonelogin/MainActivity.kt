package com.ycw.phonelogin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.SpannableStringBuilder
import android.text.TextWatcher
import android.view.MotionEvent
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var shouldAutoSplit = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mPhoneText.addTextChangedListener(object :LoginTextWatch(){
            override fun afterTextChanged(s: Editable?) {
                //设置按钮是否可以点击
                mLogin.isEnabled = (s.toString().length == 13)

                if (shouldAutoSplit){
                    s.toString().length.also {
                        if (it == 3 || it == 8){
                            //自动添加空格
                            s?.append(" ")
                        }
                    }
                }else return
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                //count为0就是删除，删除的时候就不用添加空格了
                shouldAutoSplit = count != 0
            }
        })

        //按钮点击事件
        mLogin.setOnClickListener{
            if (!mLogin.isEnabled){
                Toast.makeText(this,"222",Toast.LENGTH_SHORT).show()
            }
            Intent().apply {
                setClass(this@MainActivity,VerifyActivity::class.java)
                //配置数据
                putExtra("phone",getPhoneNumber(mPhoneText.text))
                startActivity(this)
            }
        }
    }

    //将格式化的内容转化为正常数据
    private fun getPhoneNumber(edit: Editable):String{
        //创建一个新的对象 内容和edit一样，这样就不会修改本来的内容
        //SpannableStringBuilder类实现了Editable接口
        var string = SpannableStringBuilder(edit)
        string.delete(3,4)
        string.delete(7,8)
        return string.toString()
    }
}

open class LoginTextWatch: TextWatcher{
    override fun afterTextChanged(s: Editable?) {

    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

    }

}