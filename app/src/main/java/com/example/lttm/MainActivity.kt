package com.example.lttm

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.lttm.databinding.ActivityMainBinding
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    val sdf = SimpleDateFormat("dd.MM.yyyy")
    var BirthYear = 0
    var BirthMonth = 0
    var BirthDay = 0
    var BirthHour = 0
    var BirthMinute = 0
    var DateSet = false
    var TimeSet = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.resultTV.visibility = View.INVISIBLE
        binding.resultMinutesTV.visibility = View.INVISIBLE
        binding.equalTV.visibility = View.INVISIBLE
        binding.resultInDaysTV.visibility = View.INVISIBLE
        binding.DayTV.visibility = View.INVISIBLE



        binding.dateBT.setOnClickListener{
            datePickier()
        }
        binding.timeBT.setOnClickListener{
            timePicker()
        }
        binding.calculateBT.setOnClickListener{
            if(DateSet) {
                val minutes = calculate()
                binding.resultTV.text = minutes.toInt().toString()
                binding.resultInDaysTV.text = (minutes/(60*24)).toInt().toString()
                binding.resultTV.visibility = View.VISIBLE
                binding.resultMinutesTV.visibility = View.VISIBLE
                binding.equalTV.visibility = View.VISIBLE
                binding.resultInDaysTV.visibility = View.VISIBLE
                binding.DayTV.visibility = View.VISIBLE
            }
            else
                Toast.makeText(this,"Set Birth Day to Calculate", Toast.LENGTH_SHORT).show()

        }
    }

    private fun calculate() : Long {
        val currentTime = Calendar.getInstance()
        val birthTime = Calendar.getInstance()
        birthTime.set(BirthYear,BirthMonth,BirthDay,BirthHour,BirthMinute)
        val result = (currentTime.timeInMillis - birthTime.timeInMillis)/(1000 * 60)
        return result
    }

    private fun timePicker() {
        val myTime = Calendar.getInstance()
        val hour = myTime.get(Calendar.HOUR_OF_DAY)
        val minute = myTime.get(Calendar.MINUTE)
        TimePickerDialog(this,
        TimePickerDialog.OnTimeSetListener{view,hour,minute ->
            BirthHour = hour
            BirthMinute = minute
            if(minute < 10)
                binding.timeTV.text = "$hour:0$minute"
            else
                binding.timeTV.text = "$hour:$minute"
            TimeSet = true
        },
            hour,
            minute,
            true
            ).show()

    }

    private fun datePickier() {
        val myCalendar = Calendar.getInstance()
        val day = myCalendar.get(Calendar.DAY_OF_MONTH)
        val month = myCalendar.get(Calendar.MONTH)
        val year = myCalendar.get(Calendar.YEAR)
        val dpd = DatePickerDialog(this,
        DatePickerDialog.OnDateSetListener{view,year,month,day ->
            myCalendar.set(year, month, day)
            BirthYear = year
            BirthMonth = month
            BirthDay = day
            val date = myCalendar.timeInMillis
            binding.dateTV.text = sdf.format(Date(date))
            DateSet = true
        },
            year,
            month,
            day
        )
        dpd.datePicker.maxDate = System.currentTimeMillis()
        dpd.show()
    }


}