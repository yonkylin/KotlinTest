package yonky.kotlintest.base

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import yonky.kotlintest.MultipleStatusView
import yonky.kotlintest.MyApplication

/**
 * Created by Administrator on 2018/6/21.
 */
abstract class BaseActivity:AppCompatActivity(){
    /**
     * 多种状态的 View 的切换
     */
    protected var mLayoutStatusView:MultipleStatusView?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutId())
        initData()
        initView()
        start()
        initListener()
    }

    /**
     *  加载布局
     */
    abstract fun layoutId(): Int

    /**
     * 初始化数据
     */
    abstract fun initData()

    /**
     * 初始化 View
     */
    abstract fun initView()

    /**
     * 开始请求
     */
    abstract fun start()
    private fun initListener(){
        mLayoutStatusView?.setOnClickListener(mRetryClickListener)
    }
    open val mRetryClickListener:View.OnClickListener = View.OnClickListener {
        start()
    }

    /**
     * 打卡软键盘
     */
    fun openKeyboard(mEditText: EditText,mContext: Context){
        val imm=mContext.getSystemService(Context.INPUT_METHOD_SERVICE)as InputMethodManager
        imm.showSoftInput(mEditText,InputMethodManager.RESULT_SHOWN)
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,InputMethodManager.HIDE_IMPLICIT_ONLY)
    }

    /**
     * 关闭软键盘
     */
    fun closeKeyBord(mEditText: EditText, mContext: Context) {
        val imm = mContext.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(mEditText.windowToken, 0)
    }

    override fun onDestroy() {
        super.onDestroy()
        MyApplication.getRefWatcher(this)?.watch(this)
    }
}