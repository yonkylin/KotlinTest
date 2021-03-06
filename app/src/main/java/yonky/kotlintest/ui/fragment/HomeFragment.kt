package yonky.kotlintest.ui.fragment

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.v4.app.ActivityOptionsCompat

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.orhanobut.logger.Logger
import com.scwang.smartrefresh.header.MaterialHeader
import kotlinx.android.synthetic.main.fragment_home.*
import yonky.kotlintest.R
import yonky.kotlintest.base.BaseFragment
import yonky.kotlintest.mvp.contract.HomeContract
import yonky.kotlintest.mvp.model.bean.HomeBean
import yonky.kotlintest.mvp.presenter.HomePresenter
import yonky.kotlintest.net.exception.ErrorStatus
import yonky.kotlintest.showToast
import yonky.kotlintest.ui.adapter.HomeAdapter
import yonky.kotlintest.utils.StatusBarUtil
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

/**
 * Created by Administrator on 2018/6/21.
 */
class HomeFragment : BaseFragment(), HomeContract.View{

    private val mPresenter by lazy{ HomePresenter() }

    private var mTitle:String?=null

    private var num:Int =1

    private var mHomeAdapter: HomeAdapter? = null

    private var loadingMore = false

    private var isRefresh =false
    private var mMaterialHeader:MaterialHeader?=null

    companion object {
        fun getInstance(title:String):HomeFragment{
            val fragment =HomeFragment()
            val bundle = Bundle()
            fragment.arguments = bundle
            fragment.mTitle = title
            return fragment
        }
    }

    private val linearLayoutManager by lazy{
        LinearLayoutManager(activity,LinearLayoutManager.VERTICAL,false)
    }

    private val simpleDateFormat by lazy{
        SimpleDateFormat("-MMM. dd,'Brunch' -", Locale.ENGLISH)
    }

    override fun getLayoutId(): Int = R.layout.fragment_home

    /**
     * 初始化 ViewI
     */
    override fun initView() {
        mPresenter.attachView(this)
//        内容跟随偏移
        mRefreshLayout.setEnableHeaderTranslationContent(true)
        mRefreshLayout.setOnRefreshListener{
            isRefresh=true
            mPresenter.requestHomeData(num)
        }
        mMaterialHeader =mRefreshLayout.refreshHeader as MaterialHeader?
        //打开下拉刷新区域块背景:
        mMaterialHeader?.setShowBezierWave(true)
        //设置下拉刷新主题颜色
        mRefreshLayout.setPrimaryColorsId(R.color.color_light_black,R.color.color_title_bg)

        mRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if(newState==RecyclerView.SCROLL_STATE_IDLE){
                    val childCount = mRecyclerView.childCount
                    val itemCount = mRecyclerView.layoutManager.itemCount
                    val firstVisibleItem =(mRecyclerView.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
                    if(firstVisibleItem+childCount==itemCount){
                        if(!loadingMore){
                            loadingMore=true
                            mPresenter.loadMoreData()
                        }
                    }
                }
            }

            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val currentVisibleItemPosition=linearLayoutManager.findFirstVisibleItemPosition()
                if(currentVisibleItemPosition==0){
//                  背景设置为透明
                    toolbar.setBackgroundColor(getColor(R.color.color_translucent))
                    iv_search.setImageResource(R.mipmap.ic_action_search_white)
                    tv_header_title.text=""
                }else{
                    if(mHomeAdapter?.mData!!.size>1){
                        toolbar.setBackgroundColor(getColor(R.color.color_title_bg))
                        iv_search.setImageResource(R.mipmap.ic_action_search_black)
                        val itemList=mHomeAdapter!!.mData
                        val item =itemList[currentVisibleItemPosition + mHomeAdapter!!.bannerItemSize-1]
                        if(item.type=="textHeader"){
                            tv_header_title.text =item.data?.text
                        }else{
                            tv_header_title.text=simpleDateFormat.format(item.data?.date)

                        }
                    }
                }
            }
        })

//        iv_search.setOnClickListener{openSearchActivity()}

        mLayoutStatusView=multipleStatusView
        //状态栏透明和间距处理
        StatusBarUtil.darkMode(activity)
        StatusBarUtil.setPaddingSmart(activity,toolbar)

    }

//    private fun openSearchActivity() {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            val options = ActivityOptionsCompat.makeSceneTransitionAnimation(activity, iv_search, iv_search.transitionName)
//            startActivity(Intent(activity, SearchActivity::class.java), options.toBundle())
//        } else {
//            startActivity(Intent(activity, SearchActivity::class.java))
//        }
//    }

    override fun lazyLoad() {
        mPresenter.requestHomeData(num)
    }

    /**
     * 设置首页数据
     */
    override fun setHomeData(homeBean: HomeBean) {
        mLayoutStatusView?.showContent()
        Logger.d(homeBean)

        // Adapter
        mHomeAdapter = HomeAdapter(activity, homeBean.issueList[0].itemList)
        //设置 banner 大小
        mHomeAdapter?.setBannerSize(homeBean.issueList[0].count)

        mRecyclerView.adapter = mHomeAdapter
        mRecyclerView.layoutManager = linearLayoutManager
//        mRecyclerView.itemAnimator = DefaultItemAnimator()
    }

    override fun setMoreData(itemList: ArrayList<HomeBean.Issue.Item>) {
        loadingMore = false
        mHomeAdapter?.addItemData(itemList)
    }

    /**
     * 显示错误信息
     */
    override fun showError(msg: String, errorCode: Int) {
        showToast(msg)
        if (errorCode == ErrorStatus.NETWORK_ERROR) {
            mLayoutStatusView?.showNoNetwork()
        } else {
            mLayoutStatusView?.showError()
        }
    }

    /**
     * 显示 Loading （下拉刷新的时候不需要显示 Loading）
     */
    override fun showLoading() {
        if(!isRefresh){
            isRefresh = false
            mLayoutStatusView?.showLoading()
        }
    }

    /**
     * 隐藏 Loading
     */
    override fun dismissLoading() {
        mRefreshLayout.finishRefresh()
    }

    fun getColor(colorId:Int):Int{
        return resources.getColor(colorId)
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.detachView()

    }
}