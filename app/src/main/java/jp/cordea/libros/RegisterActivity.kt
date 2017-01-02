package jp.cordea.libros

import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import com.google.zxing.integration.android.IntentIntegrator
import jp.cordea.libros.databinding.ActivityRegisterBinding
import jp.cordea.libros.model.Book
import jp.cordea.libros.viewmodel.RegisterViewModel

/**
 * Created by Yoshihiro Tanaka on 2016/12/31.
 */
class RegisterActivity : AppCompatActivity() {

    private val viewModel by lazy {
        RegisterViewModel(this,
                supportFragmentManager,
                IntentIntegrator(this),
                intent.getBooleanExtra(IsEditKey, false),
                intent.getSerializableExtra(BookKey) as? Book ?: Book())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DataBindingUtil
                .setContentView<ActivityRegisterBinding>(this, R.layout.activity_register)
                .apply {
                    vm = viewModel
                }

        val toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        viewModel.onSaveCompleted = {
            finish()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (result == null) {
            super.onActivityResult(requestCode, resultCode, data)
            return
        }
        result.contents?.let {
            viewModel.readBookCode(it)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        item?.let {
            if (it.itemId == android.R.id.home) {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {
        private val IsEditKey = "IsEdit"
        private val BookKey = "Book"

        fun createIntent(context: Context, isEdit: Boolean, book: Book? = null): Intent {
            return Intent(context, RegisterActivity::class.java).apply {
                putExtra(IsEditKey, isEdit)
                putExtra(BookKey, book)
            }
        }
    }
}
