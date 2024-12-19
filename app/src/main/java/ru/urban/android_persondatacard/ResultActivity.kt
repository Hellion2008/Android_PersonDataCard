package ru.urban.android_persondatacard

import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.time.LocalDate
import java.time.ZoneId
import kotlin.system.exitProcess

class ResultActivity : AppCompatActivity() {

    private lateinit var toolbarMain: Toolbar

    private lateinit var photoResultIV: ImageView
    private lateinit var nameResultTV: TextView
    private lateinit var surnameResultTV: TextView
    private lateinit var dateBirhdayResultTV: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_result)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        toolbarMain = findViewById(R.id.toolbarMain)
        setSupportActionBar(toolbarMain)

        photoResultIV = findViewById(R.id.photoResultIV)
        nameResultTV = findViewById(R.id.nameResultTV)
        surnameResultTV = findViewById(R.id.surnameResultTV)
        dateBirhdayResultTV = findViewById(R.id.dateBirhdayResultTV)

        val person = intent.getParcelableExtra<Person>("person")
        nameResultTV.text = person?.name
        surnameResultTV.text = person?.surname
        photoResultIV.setImageURI(Uri.parse(person?.photoUri))

        val today = LocalDate.now(ZoneId.of("GMT+3"))
        val one = person?.dateBirthday?.dayOfYear!!
        val two = today.dayOfYear
        val year = today.year
        val nextBirthday = LocalDate.of(
            if (one < two) year else year + 1,
            person?.dateBirthday?.monthValue!!,
            person?.dateBirthday?.dayOfMonth!!
            )
        val dif = nextBirthday.dayOfYear - today.dayOfYear
        dateBirhdayResultTV.text = "До Дня рождения осталось: $dif дней"

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            R.id.exitMenuMain -> {
                Toast.makeText(
                    applicationContext,
                    "Работа завершена",
                    Toast.LENGTH_LONG
                ).show()
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}