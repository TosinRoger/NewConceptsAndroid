package br.com.tosin.newconceptsandroid.ui.detail

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import br.com.tosin.newconceptsandroid.R
import br.com.tosin.newconceptsandroid.entity.FakeData
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.activity_fake_detail.*
import org.jetbrains.anko.alert

class FakeDetailActivity : AppCompatActivity() {

    private lateinit var viewModel: FakeDetailModelView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fake_detail)

        val extras = intent.extras

        if (extras == null) {
            finish()
        }

        viewModel = ViewModelProviders.of(this).get(FakeDetailModelView::class.java)

        setSupportActionBar(toolbar_detail)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)


        val fakeId = extras.getString("FAKE_ID", "")

        addListener()

        viewModel.fetchFakeDataById(fakeId)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when ( item?.itemId) {
            android.R.id.home -> {
                finish()
                true
             }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun addListener() {
        viewModel.observeListChange { aux ->
            aux.observe(this, Observer { fake ->
                if (fake != null)
                    showFakeDat(fake)
            })
        }
        viewModel.observeErrorChange { aux ->
            aux.observe(this, Observer { error ->
                error?.let {
                    showError(getString(error.title), getString(error.msg))
                }
            })
        }
    }

    private fun showError(_title: String, _message: String) {
        alert {
            title = _title
            message = _message
            positiveButton("Ok") {

            }
        }.show()
    }

    private fun showFakeDat(fake: FakeData) {
        val options = RequestOptions()
                .circleCrop()
                .placeholder(R.drawable.ic_person_gray_24dp)
                .error(R.drawable.ic_error_outline_red_24dp)
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)

        Glide.with(this)
                .load(fake.picture)
                .apply(options)
                .into(imageView_detail)

        val fullName = "${fake.name.first} ${fake.name.last}"
        textView_detail_name.text = fullName
        textView_detail_greeting.text = fake.greeting
        textView_details_email.text = fake.email
        textView_detail_phone.text = fake.phone
        textView_detail_address.text = fake.address
        textView_detail_age.text = fake.age.toString()
        textView_detail_eyesColor.text = fake.eyeColor
        textView_detail_favoriteFruit.text = fake.favoriteFruit
        textView_detail_registerSince.text = fake.registered
    }
}
