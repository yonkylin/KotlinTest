package yonky.kotlintest.mvp.contract

import yonky.kotlintest.base.IBaseView
import yonky.kotlintest.base.IPresenter
import yonky.kotlintest.mvp.model.bean.HomeBean

/**
 * Created by Administrator on 2018/6/25.
 */
interface CategoryDetailContract {

    interface View: IBaseView {
        /**
         *  设置列表数据
         */
        fun setCateDetailList(itemList:ArrayList<HomeBean.Issue.Item>)

        fun showError(errorMsg:String)




    }

    interface Presenter: IPresenter<View> {

        fun getCategoryDetailList(id:Long)

        fun loadMoreData()
    }
}