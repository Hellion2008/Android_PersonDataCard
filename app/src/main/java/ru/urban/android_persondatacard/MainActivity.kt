package ru.urban.android_persondatacard

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.text.isDigitsOnly
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.time.LocalDate
import java.util.Calendar

class MainActivity : AppCompatActivity() {
    private val GALLERY_REQUEST = 302

    var photoUri: Uri? = null

    private lateinit var toolbarMain: Toolbar

    private lateinit var photoMainIV: ImageView
    private lateinit var nameMainET: EditText
    private lateinit var surnameMainET: EditText

    private lateinit var dayBirthdayMainET: EditText
    private lateinit var monthBirthdayMainET: EditText
    private lateinit var yearBirthdayMainET: EditText

    private lateinit var saveBTN: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        toolbarMain = findViewById(R.id.toolbarMain)
        setSupportActionBar(toolbarMain)

        photoMainIV = findViewById(R.id.photoMainIV)
        nameMainET = findViewById(R.id.nameMainET)
        surnameMainET = findViewById(R.id.surnameMainET)

        dayBirthdayMainET = findViewById(R.id.dayBirthdayMainET)
        monthBirthdayMainET = findViewById(R.id.monthBirthdayMainET)
        yearBirthdayMainET = findViewById(R.id.yearBirthdayMainET)

        saveBTN = findViewById(R.id.saveBTN)

        photoMainIV.setOnClickListener {
            val photoPickerIntent = Intent(Intent.ACTION_PICK)
            photoPickerIntent.type = "image/*"
            startActivityForResult(photoPickerIntent, GALLERY_REQUEST)
        }

        saveBTN.setOnClickListener{
            if (checkRightFields()) return@setOnClickListener

            if (checkRightDate()) return@setOnClickListener

            val person = Person(
                nameMainET.text.toString(),
                surnameMainET.text.toString(),
                LocalDate.of(
                    yearBirthdayMainET.text.toString().toInt(),
                    monthBirthdayMainET.text.toString().toInt(),
                    dayBirthdayMainET.text.toString().toInt()
                ),
                photoUri.toString()
            )

            val intent: Intent = Intent(this, ResultActivity::class.java)
            intent.putExtra("person", person)
            startActivity(intent)
            finish()
        }

    }

    private fun checkRightDate(): Boolean {
        if (dayBirthdayMainET.text.toString().toInt() > 32 || dayBirthdayMainET.text.toString()
                .toInt() < 0
        ) {
            Toast.makeText(this, "Не бывает такого дня в месяце", Toast.LENGTH_SHORT).show()
            return true
        }

        if (monthBirthdayMainET.text.toString().toInt() > 13 || monthBirthdayMainET.text.toString()
                .toInt() < 0
        ) {
            Toast.makeText(this, "Не бывает такого месяца в году", Toast.LENGTH_SHORT).show()
            return true
        }

        if (monthBirthdayMainET.text.toString()
                .toInt() == Calendar.FEBRUARY + 1 && dayBirthdayMainET.text.toString().toInt() > 29
        ) {
            Toast.makeText(this, "В феврале не бывает столько дней", Toast.LENGTH_SHORT).show()
            return true
        }
        return false
    }

    private fun checkRightFields(): Boolean {
        if (nameMainET.text.isEmpty() || surnameMainET.text.isEmpty() ||
            dayBirthdayMainET.text.isEmpty() || monthBirthdayMainET.text.isEmpty() || yearBirthdayMainET.text.isEmpty()
        ) {
            Toast.makeText(this, "Пустые значения", Toast.LENGTH_SHORT).show()
            return true
        }

        if (!dayBirthdayMainET.text.isDigitsOnly() ||
            !monthBirthdayMainET.text.isDigitsOnly() ||
            !yearBirthdayMainET.text.isDigitsOnly()
        ) {
            Toast.makeText(this, "Дата рождения должна быть в цифрах", Toast.LENGTH_SHORT).show()
            return true
        }
        return false
    }

    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        super.onActivityResult(requestCode, resultCode, data)
        photoMainIV = findViewById(R.id.photoMainIV)
        photoMainIV.setImageResource(R.drawable.ic_some_person)
        when (requestCode) {
            GALLERY_REQUEST -> if (resultCode === RESULT_OK) {
                photoUri = data?.data

                photoMainIV.setImageURI(photoUri)
            }

            else -> photoMainIV.setImageResource(R.drawable.ic_some_person)
        }
    }

}