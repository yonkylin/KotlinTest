package yonky.kotlintest.mvp.contract

import yonky.kotlintest.base.IBaseView
import yonky.kotlintest.base.IPresenter
import yonky.kotlintest.mvp.model.bean.HomeBean

/**
 * Created by Administrator on 2018/6/25.
 */
interface RankContract {

    interface View: IBaseView {
        /**
         * 设置排行榜的数据
         */
        fun setRankList(itemList: ArrayList<HomeBean.Issue.Item>)

        fun showError(errorMsg:String,errorCode:Int)
    }


    interface Presenter: IPresenter<View> {
        /**
         * 获取 TabInfo
         */
        fun requestRankList(apiUrl:String)
    }
}