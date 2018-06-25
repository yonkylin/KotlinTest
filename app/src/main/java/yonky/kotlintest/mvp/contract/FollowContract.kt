package yonky.kotlintest.mvp.contract

import yonky.kotlintest.base.IBaseView
import yonky.kotlintest.base.IPresenter
import yonky.kotlintest.mvp.model.bean.HomeBean

/**
 * Created by Administrator on 2018/6/25.
 */
interface FollowContract {

    interface View : IBaseView {
        /**
         * 设置关注信息数据
         */
        fun setFollowInfo(issue: HomeBean.Issue)

        fun showError(errorMsg: String, errorCode: Int)
    }


    interface Presenter : IPresenter<View> {
        /**
         * 获取List
         */
        fun requestFollowList()

        /**
         * 加载更多
         */
        fun loadMoreData()
    }
}