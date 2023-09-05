package com.iitism.concetto.ui.pastSponsors

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.iitism.concetto.R
import org.w3c.dom.Text

class PastSponsorsFragment : Fragment() {

    companion object {
        fun newInstance() = PastSponsorsFragment()
    }

    private lateinit var vm: viewmodel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_past_sponsors, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        vm = viewmodel(requireContext())

    }

}



@Composable
fun UI(vm: viewmodel){
    vm.getSponsorList()

    Scaffold (
        topBar = {
            TopAppBar(
                title = { Text("Sponsors") }
            )
        },
        bottomBar = {}
    )
    { paddingValues ->
        Row {
            LazyColumn(
                modifier = Modifier.padding(paddingValues)
            ) {
                items(vm.sponsorList){ sponsor ->
                    UserWidget(sponsor)

                }
            }

            LazyColumn(
                modifier = Modifier.padding(paddingValues)
            ) {
                items(vm.sponsorList){ user ->
                    UserWidget(sponsor)

                }
            }
        }


    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

/*
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    API_jetpack_composeTheme {
        Greeting("Android")
    }
}*/
