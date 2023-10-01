package com.iitism.concetto.ui.singleevent

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.iitism.concetto.ui.sponsors.viewmodel

class FragmentPageAdapter(
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle,
    val viewModel: MyViewModel
) : FragmentStateAdapter(fragmentManager,lifecycle){
    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> AboutFragment(viewModel.EventsList.value)
            1-> RulesFragment((viewModel.EventsList.value?.flatMap { it.rules }) ?: emptyList(),viewModel.EventsList.value?.get(0)?.pdfLink)
            else -> DetailsFragment(viewModel.EventsList.value)
        }
    }
}