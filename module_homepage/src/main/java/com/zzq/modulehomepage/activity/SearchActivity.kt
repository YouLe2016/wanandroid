package com.zzq.modulehomepage.activity

import android.arch.lifecycle.Observer
import android.content.Context
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.zzq.commonlib.bar.UltimateBar
import com.zzq.commonlib.view.RecyclerViewSpace
import com.zzq.modulehomepage.R
import com.zzq.modulehomepage.adapter.SearchKeyAdapter
import com.zzq.modulehomepage.bean.SearchKeyData
import com.zzq.modulehomepage.model.SearchModel
import com.zzq.netlib.utils.Logger

import kotlinx.android.synthetic.main.activity_search.*
import kotlinx.android.synthetic.main.fragment_home.*

class SearchActivity : AppCompatActivity() {

    private var searchKeyData: MutableList<SearchKeyData.Datas> = mutableListOf()
    private val searchModel by lazy { SearchModel(this) }
    private val searchKeyAdapter by lazy { SearchKeyAdapter(this@SearchActivity, searchKeyData) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        initStatusBar()

        val searchKey = intent.getStringExtra("searchKey")

        setSupportActionBar(toolbar)
        toolbar.title = searchKey

        initUI()

        searchModel.getSearchKeyData(0, searchKey)
        home_recyclerView.run {
            layoutManager = LinearLayoutManager(this@SearchActivity)
            addItemDecoration(RecyclerViewSpace())
            adapter = searchKeyAdapter
        }
    }

    private fun initUI() {
        searchModel.searchKeyData.observe(this@SearchActivity, Observer<SearchKeyData> {
            Logger.zzqLog().d("the data is come back????")
            searchKeyAdapter.addData(searchKeyData.size,it?.datas!!)
        })
    }

    private fun initStatusBar() {
        UltimateBar.with(this)
                .statusDrawable(ColorDrawable(resources.getColor(R.color.colorPrimary)))
                .applyNavigation(true)
                .create()
                .drawableBar()
        toolbar.setBackgroundColor(resources.getColor(R.color.colorPrimary))
    }

    companion object {
        fun open(context: Context, searchKey: String) {
            val intent = Intent(context, SearchActivity::class.java)
            intent.putExtra("searchKey", searchKey)
            context.startActivity(intent)
        }
    }
}