package com.iitism.concetto.ui.sponsors

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.letmeknow.Adapters.SponsorsAdapter
import com.iitism.concetto.R


class SponsorsFragment : Fragment(R.layout.fragment_sponsors) {

    private lateinit var vm: viewmodel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//        return ComposeView(requireContext()).apply {
//            vm = viewmodel(requireContext())
//            setContent {
//                UI(vm)
//            }
//        }
        return inflater.inflate(R.layout.fragment_sponsors, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val SponsorsRecyclerView: RecyclerView = view.findViewById(R.id.recycler_view_sponsors)
        SponsorsRecyclerView.layoutManager = LinearLayoutManager(context)
        SponsorsRecyclerView.setHasFixedSize(true)

        vm = viewmodel(requireContext())
        vm.getSponsorList()

        val itemAdapter = SponsorsAdapter(vm.sponsorList)
        SponsorsRecyclerView.adapter = itemAdapter

        itemAdapter.setOnItemClickListener(object: SponsorsAdapter.onItemClickListener{
            override fun onItemClick(position: Int) {
                try {
                    val url = vm.sponsorList[position].link
                    val i = Intent()
                    i.setPackage("com.android.chrome")
                    i.action = Intent.ACTION_VIEW
                    i.data = Uri.parse(url)
                    startActivity(i)
                } catch (e: ActivityNotFoundException){
                    Toast.makeText(context,"Chrome not Installed",Toast.LENGTH_SHORT).show()
                }

            }

        })


    }


}




/*@OptIn(ExperimentalMaterial3Api::class)
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
                *//*Card (
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

                }*//*

            }
        }

    }
}*/

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
