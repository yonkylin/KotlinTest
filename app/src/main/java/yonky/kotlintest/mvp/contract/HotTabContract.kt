package yonky.kotlintest.mvp.contract

import yonky.kotlintest.base.IBaseView
import yonky.kotlintest.base.IPresenter
import yonky.kotlintest.mvp.model.bean.TabInfoBean

/**
 * Created by Administrator on 2018/6/25.
 */

interface HotTabContract {

    interface View: IBaseView {
        /**
         * 设置 TabInfo
         */
        fun setTabInfo(tabInfoBean: TabInfoBean)

        fun showError(errorMsg:String,errorCode:Int)
    }


    interface Presenter: IPresenter<View> {
        /**
         * 获取 TabInfo
         */
        fun getTabInfo()
    }
}