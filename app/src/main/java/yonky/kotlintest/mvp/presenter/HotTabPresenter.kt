package yonky.kotlintest.mvp.presenter

import yonky.kotlintest.base.BasePresenter
import yonky.kotlintest.mvp.contract.HotTabContract
import yonky.kotlintest.mvp.model.HotTabModel
import yonky.kotlintest.net.exception.ExceptionHandle

/**
 * Created by Administrator on 2018/6/25.
 */
class HotTabPresenter: BasePresenter<HotTabContract.View>(), HotTabContract.Presenter {

    private val hotTabModel by lazy { HotTabModel() }


    override fun getTabInfo() {
        checkViewAttached()
        mRootView?.showLoading()
        val disposable = hotTabModel.getTabInfo()
                .subscribe({
                    tabInfo->
                    mRootView?.setTabInfo(tabInfo)
                },{
                    throwable->
                    //处理异常
                    mRootView?.showError(ExceptionHandle.handleException(throwable), ExceptionHandle.errorCode)
                })
        addSubscription(disposable)
    }
}