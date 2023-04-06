package com.iitism.concetto.ui.problemstatement

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.iitism.concetto.ui.aboutus.AboutUsRepository

class ProblemStatementViewModelFactory constructor(private val repository: AboutUsRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when{
            modelClass.isAssignableFrom(ProblemStatementViewModel::class.java) -> ProblemStatementViewModel(this.repository) as T
            else->throw IllegalArgumentException("ViewModel not Found")
        }
    }
}