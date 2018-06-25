package yonky.kotlintest.mvp.presenter

import yonky.kotlintest.base.BasePresenter
import yonky.kotlintest.mvp.contract.RankContract
import yonky.kotlintest.mvp.model.RankModel
import yonky.kotlintest.net.exception.ExceptionHandle

/**
 * Created by Administrator on 2018/6/25.
 */
class RankPresenter : BasePresenter<RankContract.View>(), RankContract.Presenter {

    private val rankModel by lazy { RankModel() }


    /**
     *  请求排行榜数据
     */
    override fun requestRankList(apiUrl: String) {
        checkViewAttached()
        mRootView?.showLoading()
        val disposable = rankModel.requestRankList(apiUrl)
                .subscribe({ issue ->
                    mRootView?.apply {
                        dismissLoading()
                        setRankList(issue.itemList)
                    }
                }, { throwable ->
                    mRootView?.apply {
                        //处理异常
                        showError(ExceptionHandle.handleException(throwable),ExceptionHandle.errorCode)
                    }
                })
        addSubscription(disposable)
    }
}
