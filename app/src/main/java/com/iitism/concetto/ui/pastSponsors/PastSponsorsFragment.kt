package com.iitism.concetto.ui.pastSponsors

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.iitism.concetto.R
import org.w3c.dom.Text

class PastSponsorsFragment : Fragment() {

    companion object {
        fun newInstance() = PastSponsorsFragment()
    }

    private lateinit var viewModel: PastSponsorsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_past_sponsors, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(PastSponsorsViewModel::class.java)
        // TODO: Use the ViewModel
    }

}


@Composable
fun UI(vm: viewmodel){
    vm.getUserList()

    Scaffold (
        topBar = {
            TopAppBar(
                title = { Text(text = "Users")}
            )
        },
        bottomBar = {}
    )
    { paddingValues ->
        LazyColumn(
            modifier = Modifier.padding(paddingValues)
        ) {
            items(vm.userList){ user ->
                UserWidget(user)

            }
        }

    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    API_jetpack_composeTheme {
        Greeting("Android")
    }
}