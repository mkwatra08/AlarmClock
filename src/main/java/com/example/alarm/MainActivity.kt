package com.example.alarm

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {

    private lateinit var setAlarmButton: Button
    private lateinit var alarmRecyclerView: RecyclerView
    private lateinit var alarmAdapter: AlarmListAdapter
    private lateinit var alarms: ArrayList<Alarm>
    private var alarmIdCounter = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setAlarmButton = findViewById(R.id.set_alarm_button)
        alarmRecyclerView = findViewById(R.id.alarm_recycler_view)

        // Initialize the list of alarms
        alarms = ArrayList()

        // Initialize the adapter and set it to the RecyclerView
        alarmAdapter = AlarmListAdapter(this, alarms)
        alarmRecyclerView.layoutManager = LinearLayoutManager(this)
        alarmRecyclerView.adapter = alarmAdapter

        // Set Set Alarm button click listener
        setAlarmButton.setOnClickListener {
            showTimePickerDialog()
        }

        // Set RecyclerView item click listener for deleting alarms
        alarmAdapter.setOnItemClickListener(object : AlarmListAdapter.OnItemClickListener {
            override fun onItemClick(position: Int) {
                deleteAlarm(position)
            }
        })
    }

    private fun showTimePickerDialog() {
        val calendar = Calendar.getInstance()
        val currentTime = SimpleDateFormat("HH:mm", Locale.getDefault()).format(calendar.time)

        val picker = MaterialTimePicker.Builder()
            .setTimeFormat(TimeFormat.CLOCK_24H)
            .setHour(calendar.get(Calendar.HOUR_OF_DAY))
            .setMinute(calendar.get(Calendar.MINUTE))
            .setTitleText("Set Alarm Time")
            .build()

        picker.addOnPositiveButtonClickListener {
            val selectedHour = picker.hour
            val selectedMinute = picker.minute

            val alarmTime = "$selectedHour:$selectedMinute"
            val newAlarm = Alarm(alarmIdCounter++, alarmTime)

            // Add the new alarm to the list
            alarms.add(newAlarm)
            alarmAdapter.notifyDataSetChanged()
        }

        picker.show(supportFragmentManager, "tag")
    }

    private fun deleteAlarm(position: Int) {
        alarms.removeAt(position)
        alarmAdapter.notifyDataSetChanged()
    }
}
