package com.iitism.concetto.ui.allevents

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.iitism.concetto.R
import com.iitism.concetto.databinding.ActivityViewerBinding

class ViewerActivity : AppCompatActivity() {

    private lateinit var binding: ActivityViewerBinding
    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager: ViewPager2
    private lateinit var adapter: FragmentPageAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.binding= ActivityViewerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter= FragmentPageAdapter(supportFragmentManager,lifecycle)
        tabLayout= binding.tabLayout
        viewPager= binding.viewPager2
        tabLayout.addTab(tabLayout.newTab().setText("ABOUT"))
        tabLayout.addTab(tabLayout.newTab().setText("RULES"))
        tabLayout.addTab(tabLayout.newTab().setText("DETAILS"))

        binding.viewPager2.adapter = adapter

        tabLayout.addOnTabSelectedListener(object: TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                if (tab!= null){
                    viewPager.currentItem= tab.position
                }


            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

        })

        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                tabLayout.selectTab(tabLayout.getTabAt(position))
            }
        })



    }
}