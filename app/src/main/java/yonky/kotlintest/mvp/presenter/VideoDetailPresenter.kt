package yonky.kotlintest.mvp.presenter

import android.app.Activity
import yonky.kotlintest.MyApplication
import yonky.kotlintest.base.BasePresenter
import yonky.kotlintest.dataFormat
import yonky.kotlintest.mvp.contract.VideoDetailContract
import yonky.kotlintest.mvp.model.VideoDetailModel
import yonky.kotlintest.mvp.model.bean.HomeBean
import yonky.kotlintest.net.NetworkUtil
import yonky.kotlintest.net.exception.ExceptionHandle
import yonky.kotlintest.showToast
import yonky.kotlintest.utils.DisplayManager

/**
 * Created by Administrator on 2018/6/23.
 */
class VideoDetailPresenter :BasePresenter<VideoDetailContract.View>(),VideoDetailContract.Presenter{
    private val videoDetailModel:VideoDetailModel by lazy{
        VideoDetailModel()
    }
/**
 * 加载视频相关的数据
 */
    override fun loadVideoInfo(itemInfo: HomeBean.Issue.Item) {
    val playInfo = itemInfo.data?.playInfo

    val netType = NetworkUtil.isWifi(MyApplication.context)
    // 检测是否绑定 View
    checkViewAttached()
    if (playInfo!!.size > 1) {
        // 当前网络是 Wifi环境下选择高清的视频
        if (netType) {
            for (i in playInfo) {
                if (i.type == "high") {
                    val playUrl = i.url
                    mRootView?.setVideo(playUrl)
                    break
                }
            }
        } else {
            //否则就选标清的视频
            for (i in playInfo) {
                if (i.type == "normal") {
                    val playUrl = i.url
                    mRootView?.setVideo(playUrl)
                    //Todo 待完善
                    (mRootView as Activity).showToast("本次消耗${(mRootView as Activity)
                            .dataFormat(i.urlList[0].size)}流量")
                    break
                }
            }
        }
    } else {
        mRootView?.setVideo(itemInfo.data.playUrl)
    }

    //设置背景
    val backgroundUrl = itemInfo.data.cover.blurred + "/thumbnail/${DisplayManager.getScreenHeight()!! - DisplayManager.dip2px(250f)!!}x${DisplayManager.getScreenWidth()}"
    backgroundUrl.let { mRootView?.setBackground(it) }

    mRootView?.setVideoInfo(itemInfo)

}

    /**
     * 请求相关的视频数据
     */
    override fun requestRelatedVideo(id: Long) {
        mRootView?.showLoading()
        val disposable = videoDetailModel.requestRelatedData(id)
                .subscribe({ issue ->
                    mRootView?.apply {
                        dismissLoading()
                        setRecentRelatedVideo(issue.itemList)
                    }
                }, { t ->
                    mRootView?.apply {
                        dismissLoading()
                        setErrorMsg(ExceptionHandle.handleException(t))
                    }
                })

        addSubscription(disposable)

    }

}