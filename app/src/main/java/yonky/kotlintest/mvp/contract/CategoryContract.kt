package yonky.kotlintest.mvp.contract

import yonky.kotlintest.base.IBaseView
import yonky.kotlintest.base.IPresenter
import yonky.kotlintest.mvp.model.bean.CategoryBean

/**
 * Created by Administrator on 2018/6/25.
 */
interface CategoryContract {

    interface View : IBaseView {
        /**
         * 显示分类的信息
         */
        fun showCategory(categoryList: ArrayList<CategoryBean>)

        /**
         * 显示错误信息
         */
        fun showError(errorMsg:String,errorCode:Int)
    }

    interface Presenter: IPresenter<View> {
        /**
         * 获取分类的信息
         */
        fun getCategoryData()
    }
}