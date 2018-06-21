package yonky.kotlintest.mvp.presenter

import yonky.kotlintest.base.BasePresenter
import yonky.kotlintest.mvp.contract.HomeContract
import yonky.kotlintest.mvp.model.HomeModel
import yonky.kotlintest.mvp.model.bean.HomeBean
import yonky.kotlintest.net.exception.ExceptionHandle

/**
 * Created by Administrator on 2018/6/21.
 */
class HomePresenter : BasePresenter<HomeContract.View>(), HomeContract.Presenter {
    private var bannerHomeBean: HomeBean? = null

    private var nextPageUrl:String?=null     //加载首页的Banner 数据+一页数据合并后，nextPageUrl没 add

    private val homeModel: HomeModel by lazy {

        HomeModel()
    }

    /**
     * 获取首页精选数据 banner 加 一页数据
     */
    override fun requestHomeData(num: Int) {
        // 检测是否绑定 View
        checkViewAttached()
        mRootView?.showLoading()
        val disposable = homeModel.requestHomeData(num)
                .flatMap({ homeBean ->

                    //过滤掉 Banner2(包含广告,等不需要的 Type), 具体查看接口分析
                    val bannerItemList = homeBean.issueList[0].itemList

                    bannerItemList.filter { item ->
                        item.type=="banner2"|| item.type=="horizontalScrollCard"
                    }.forEach{ item ->
                        //移除 item
                        bannerItemList.remove(item)
                    }

                    bannerHomeBean = homeBean //记录第一页是当做 banner 数据


                    //根据 nextPageUrl 请求下一页数据
                    homeModel.loadMoreData(homeBean.nextPageUrl)
                })

                .subscribe({ homeBean->
                    mRootView?.apply {
                        dismissLoading()

                        nextPageUrl = homeBean.nextPageUrl
                        //过滤掉 Banner2(包含广告,等不需要的 Type), 具体查看接口分析
                        val newBannerItemList = homeBean.issueList[0].itemList

                        newBannerItemList.filter { item ->
                            item.type=="banner2"||item.type=="horizontalScrollCard"
                        }.forEach{ item ->
                            //移除 item
                            newBannerItemList.remove(item)
                        }
                        // 重新赋值 Banner 长度
                        bannerHomeBean!!.issueList[0].count = bannerHomeBean!!.issueList[0].itemList.size

                        //赋值过滤后的数据 + banner 数据
                        bannerHomeBean?.issueList!![0].itemList.addAll(newBannerItemList)

                        setHomeData(bannerHomeBean!!)

                    }

                }, { t ->
                    mRootView?.apply {
                        dismissLoading()
                        showError(ExceptionHandle.handleException(t), ExceptionHandle.errorCode)
                    }
                })

        addSubscription(disposable)

    }

    /**
     * 加载更多
     */

    override fun loadMoreData() {
        val disposable = nextPageUrl?.let {
            homeModel.loadMoreData(it)
                    .subscribe({ homeBean->
                        mRootView?.apply {
                            //过滤掉 Banner2(包含广告,等不需要的 Type), 具体查看接口分析
                            val newItemList = homeBean.issueList[0].itemList

                            newItemList.filter { item ->
                                item.type=="banner2"||item.type=="horizontalScrollCard"
                            }.forEach{ item ->
                                //移除 item
                                newItemList.remove(item)
                            }

                            nextPageUrl = homeBean.nextPageUrl
                            setMoreData(newItemList)
                        }

                    },{ t ->
                        mRootView?.apply {
                            showError(ExceptionHandle.handleException(t),ExceptionHandle.errorCode)
                        }
                    })


        }
        if (disposable != null) {
            addSubscription(disposable)
        }
    }



}