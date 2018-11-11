package test.edney.picpay.main.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import test.edney.picpay.R
import test.edney.picpay.databinding.ActivityMainBinding
import test.edney.picpay.main.viewmodel.MainViewModel
import test.edney.picpay.util.MyLog

class MainActivity : AppCompatActivity() {

    private val log = MyLog(MainActivity::class.java.simpleName)
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewmodel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setSupportActionBar(binding.toolbar)
        viewModel()
        getUsers()
    }

    private fun viewModel(){
        val func = "viewModel"

        viewmodel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        viewmodel.users.observe(this, Observer {
            if(it != null){
                log.showD(func, "${it.size} usuários encontrados!")
            }
        })
    }

    private fun getUsers(){
        viewmodel.getUsers()
    }
}
