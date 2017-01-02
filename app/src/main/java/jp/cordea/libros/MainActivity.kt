package jp.cordea.libros

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import jp.cordea.libros.databinding.ActivityMainBinding
import jp.cordea.libros.viewmodel.MainViewModel

/**
 * Created by Yoshihiro Tanaka on 2016/12/31.
 */
class MainActivity : AppCompatActivity() {

    private val viewModel = MainViewModel(this, supportFragmentManager)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main).apply {
            vm = viewModel
        }

        val toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
    }

    override fun onResume() {
        super.onResume()
        viewModel.refreshItems()
    }
}
