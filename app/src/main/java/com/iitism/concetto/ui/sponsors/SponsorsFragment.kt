package com.iitism.concetto.ui.sponsors

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment

class SponsorsFragment : Fragment() {

    private lateinit var vm: viewmodel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return ComposeView(requireContext()).apply {
            vm = viewmodel(requireContext())
            setContent {
                UI(vm)
            }
        }
    }


}




@OptIn(ExperimentalMaterial3Api::class)
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

        LazyVerticalGrid(
            modifier = Modifier.padding(paddingValues),
            columns = GridCells.Fixed(2)
        ) {
            items(vm.sponsorList){ sponsor ->
                UserWidget(sponsor)
                /*Card (
                    modifier = Modifier.padding(10.dp)
                ){
                    Column(modifier = Modifier.fillMaxWidth()) {

                        AsyncImage(
                            model = sponsor.img,
                            contentDescription = "Image of the Sponsor"
                        )
                        Spacer(modifier = Modifier.width(8.dp))

                        Text(text =sponsor.cat)

                    }

                }*/

            }
        }

    }
}

/*@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}*/

/*
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    API_jetpack_composeTheme {
        Greeting("Android")
    }
}*/
