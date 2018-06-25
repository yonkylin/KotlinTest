package yonky.kotlintest.ui.fragment

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_rank.*
import yonky.kotlintest.R
import yonky.kotlintest.base.BaseFragment
import yonky.kotlintest.mvp.contract.RankContract
import yonky.kotlintest.mvp.model.bean.HomeBean
import yonky.kotlintest.mvp.presenter.RankPresenter
import yonky.kotlintest.net.exception.ErrorStatus
import yonky.kotlintest.showToast
import yonky.kotlintest.ui.adapter.CategoryDetailAdapter

/**
 * Created by Administrator on 2018/6/25.
 */

class RankFragment : BaseFragment(), RankContract.View {


    private val mPresenter by lazy { RankPresenter() }

    private val mAdapter by lazy { CategoryDetailAdapter(activity, itemList, R.layout.item_category_detail) }

    private var itemList = ArrayList<HomeBean.Issue.Item>()

    private var apiUrl: String? = null

    companion object {
        fun getInstance(apiUrl: String): RankFragment {
            val fragment = RankFragment()
            val bundle = Bundle()
            fragment.arguments = bundle
            fragment.apiUrl = apiUrl
            return fragment
        }
    }

    init {
        mPresenter.attachView(this)
    }


    override fun getLayoutId(): Int = R.layout.fragment_rank

    override fun initView() {
        mRecyclerView.layoutManager = LinearLayoutManager(activity)
        mRecyclerView.adapter = mAdapter

        mLayoutStatusView =multipleStatusView

    }

    override fun lazyLoad() {
        if (!apiUrl.isNullOrEmpty()) {
            mPresenter.requestRankList(apiUrl!!)
        }
    }

    override fun showLoading() {
        multipleStatusView.showLoading()
    }

    override fun dismissLoading() {

    }

    override fun setRankList(itemList: ArrayList<HomeBean.Issue.Item>) {
        multipleStatusView.showContent()

        mAdapter.addData(itemList)
    }

    override fun showError(errorMsg: String,errorCode:Int) {
        showToast(errorMsg)
        if (errorCode == ErrorStatus.NETWORK_ERROR) {
            multipleStatusView.showNoNetwork()
        } else {
            multipleStatusView.showError()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.detachView()
    }
}