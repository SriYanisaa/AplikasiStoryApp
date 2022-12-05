package com.example.aplikasistoryapp

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.core.app.ActivityOptionsCompat
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.aplikasistoryapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user")

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: StoryAdapter
    private lateinit var viewModelFactory: ViewModelFactory
    private lateinit var viewModelLogin: ViewModelLogin
    private lateinit var storyViewModel: ViewModelStory
    private var lat: Float? = null
    private var lon: Float? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "List Story"

        storyViewModel.getUser().observe(this){
            if (it.isLogin){
                getStory()
            } else {
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }
        }

        viewModelFactory = ViewModelFactory.getInstance(this)
        storyViewModel = ViewModelProvider(this, viewModelFactory)[ViewModelStory::class.java]
        viewModelLogin = ViewModelProvider(this, viewModelFactory)[ViewModelLogin::class.java]

        binding.rvListStory.layoutManager = LinearLayoutManager(this@MainActivity)
        binding.rvListStory.setHasFixedSize(true)
        adapter = StoryAdapter()

        binding.floatingAddStory. setOnClickListener {
            intent = Intent(this, AddStoryActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.option_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            R.id.menu_logout -> {
                viewModelLogin.logout()
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
                return true
            }
            else -> return true
        }
    }

    private fun getStory(){
        binding.rvListStory.adapter = adapter.withLoadStateFooter(
            footer = LoadingAdapter{adapter.retry()}
        )

        storyViewModel.getStory().observe(this@MainActivity){
            adapter.submitData(lifecycle, it)
            showLoading(false)
        }
    }

    private fun showLoading(state: Boolean){
        if(state){
            binding.progressHome.visibility = View.VISIBLE
        } else {
            binding.progressHome.visibility = View.GONE
        }
    }
}