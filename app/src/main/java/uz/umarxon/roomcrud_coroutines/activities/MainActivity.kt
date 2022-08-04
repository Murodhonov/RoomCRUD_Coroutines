package uz.umarxon.roomcrud_coroutines.activities

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import uz.umarxon.roomcrud_coroutines.R
import uz.umarxon.roomcrud_coroutines.ViewModelFactories.UserViewModelFactory
import uz.umarxon.roomcrud_coroutines.adapter.RvAdapter
import uz.umarxon.roomcrud_coroutines.databinding.ActivityMainBinding
import uz.umarxon.roomcrud_coroutines.db.appdatabase.AppDatabase
import uz.umarxon.roomcrud_coroutines.db.entity.User
import uz.umarxon.roomcrud_coroutines.repositories.UserRepository
import uz.umarxon.roomcrud_coroutines.viewmodels.UserViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var userViewModel: UserViewModel
    private lateinit var adapter: RvAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        val dao = AppDatabase.getInstance(application).userDao()
        val repository = UserRepository(dao)
        val factory = UserViewModelFactory(repository)
        userViewModel = ViewModelProvider(this, factory)[UserViewModel::class.java]
        binding.viewModel = userViewModel
        binding.lifecycleOwner = this
        showUsers()
        initRecyclerView()

        userViewModel.message.observe(this) {
            it.getContentIfNoHandled()?.let { message->
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun initRecyclerView() {
        binding.rv.layoutManager = LinearLayoutManager(this)
        adapter = RvAdapter { selectedItem: User ->
            listItemClicked(selectedItem)
        }
        binding.rv.adapter = adapter
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun showUsers() {
        userViewModel.users.observe(this) {
            Log.d("userList", it.toString())
            adapter.setList(it)
            adapter.notifyDataSetChanged()
        }
    }

    private fun listItemClicked(user: User) {
        Toast.makeText(this, "Clicked ${user.name}", Toast.LENGTH_SHORT).show()
        userViewModel.initUpdateAndDelete(user)
    }
}