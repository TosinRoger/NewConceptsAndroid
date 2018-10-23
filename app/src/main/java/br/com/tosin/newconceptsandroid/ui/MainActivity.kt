package br.com.tosin.newconceptsandroid.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import br.com.tosin.newconceptsandroid.R
import br.com.tosin.newconceptsandroid.ui.main.MainFragment
import kotlinx.android.synthetic.main.main_activity.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        toolbar?.title = "Lista Fake"

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
        supportActionBar?.setDisplayShowTitleEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MainFragment.newInstance())
                .commitNow()
        }
    }

}
