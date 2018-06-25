package yonky.kotlintest.mvp.presenter

import yonky.kotlintest.base.BasePresenter
import yonky.kotlintest.mvp.contract.CategoryContract
import yonky.kotlintest.mvp.model.CategoryModel
import yonky.kotlintest.net.exception.ExceptionHandle

/**
 * Created by Administrator on 2018/6/25.
 */

class CategoryPresenter : BasePresenter<CategoryContract.View>(), CategoryContract.Presenter {

    private val categoryModel: CategoryModel by lazy {
        CategoryModel()
    }

    /**
     * 获取分类
     */
    override fun getCategoryData() {
        checkViewAttached()
        mRootView?.showLoading()
        val disposable = categoryModel.getCategoryData()
                .subscribe({ categoryList ->
                    mRootView?.apply {
                        dismissLoading()
                        showCategory(categoryList)
                    }
                }, { t ->
                    mRootView?.apply {
                        //处理异常
                        showError(ExceptionHandle.handleException(t), ExceptionHandle.errorCode)
                    }

                })

        addSubscription(disposable)
    }
}