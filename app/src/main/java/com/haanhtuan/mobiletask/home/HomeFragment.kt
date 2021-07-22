package com.haanhtuan.mobiletask.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Switch
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.haanhtuan.mobiletask.MyApplication
import com.haanhtuan.mobiletask.R
import com.haanhtuan.mobiletask.Repository
import com.haanhtuan.mobiletask.base.BaseFragment
import com.haanhtuan.mobiletask.data.database.Item
import com.haanhtuan.mobiletask.databinding.FragmentHomeBinding
import com.haanhtuan.mobiletask.databinding.FragmentSigninBinding
import com.haanhtuan.mobiletask.login.LoginViewModel
import com.haanhtuan.mobiletask.utils.Utils
import androidx.recyclerview.widget.ItemTouchHelper as ItemTouchHelper

class HomeFragment : BaseFragment(), View.OnClickListener {
    private var homeViewModel: HomeViewModel? = null
    private lateinit var binding: FragmentHomeBinding
    private var adapter: ItemAdapter? = null

    override fun onClick(v: View?) {
        when (v) {
            binding.fab -> {
                context?.let { it ->
                    Utils.newItemDialog(it) { item ->
                        add(item)
                    }
                }
            }

            binding.backTv -> {
                activity?.finish()
            }

            binding.profileTv -> {
                goToScreen(ProfileFragment())
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentHomeBinding.inflate(layoutInflater)
        binding.fab.setOnClickListener(this)
        binding.backTv.setOnClickListener(this)
        binding.profileTv.setOnClickListener(this)
        context?.let { homeViewModel?.initRepository(Repository.LOCAL, it) }
        initRecyclerView()
        getDataForRecyclerView()
        createSwipeAction()
        initSwitch()

        return binding.root
    }

    private fun initSwitch() {
        binding.switch1.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked)
                context?.let { homeViewModel?.initRepository(Repository.FIREBASE, it) }
            else
                context?.let { homeViewModel?.initRepository(Repository.LOCAL, it) }

            getDataForRecyclerView()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        homeViewModel = activity?.let { ViewModelProvider(it).get(HomeViewModel::class.java) }
    }

    private fun getDataForRecyclerView() {
        showLoading()
        homeViewModel?.getListItem()?.observe(viewLifecycleOwner, {
            it?.let {
                hideLoading()
                adapter?.update(it)
            }
        })
    }

    private fun delete(item: Item) {
        showLoading()
        homeViewModel?.delete(item)?.observe(viewLifecycleOwner, {
            getDataForRecyclerView()
        })
    }

    private fun add(item: Item) {
        showLoading()
        homeViewModel?.addItem(item)?.observe(viewLifecycleOwner, {
            getDataForRecyclerView()
        })
    }

    private fun initRecyclerView() {
        adapter = ItemAdapter(ArrayList())
        val llManager = LinearLayoutManager(context)
        binding.recyclerView.setHasFixedSize(false)
        binding.recyclerView.layoutManager = llManager
        binding.recyclerView.adapter = adapter
        val dividerItemDecoration =
            DividerItemDecoration(binding.recyclerView.context, llManager.orientation)
        binding.recyclerView.addItemDecoration(dividerItemDecoration)
    }

    private fun createSwipeAction() {
        val itemTouchHelperCallback =
            object :
                ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
                ): Boolean {
                    return true
                }

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    adapter?.getItemPosition(viewHolder.layoutPosition)?.let {
                        delete(it)
                    }
                }
            }

        val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
        itemTouchHelper.attachToRecyclerView(binding.recyclerView)
    }
}