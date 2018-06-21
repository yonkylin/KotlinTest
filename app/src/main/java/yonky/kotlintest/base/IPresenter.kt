package yonky.kotlintest.base

/**
 * Created by Administrator on 2018/6/21.
 */

interface IPresenter<in V: IBaseView> {

    fun attachView(mRootView: V)

    fun detachView()

}
