package com.example.notificaciones

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import androidx.core.app.NotificationCompat

class Ejercicio3 : AppCompatActivity() {
    private companion object {
        const val CHANNEL_ID = "my_channel"
        const val NOTIFICATION_ID = 1
    }

    private lateinit var editTitle: EditText
    private lateinit var editText: EditText
    private lateinit var spinnerIcon: Spinner
    private lateinit var spinnerImage: Spinner
    private lateinit var btnSubtract: Button
    private lateinit var tvButtonCount: TextView
    private lateinit var btnAdd: Button
    private lateinit var editButtonNames: EditText
    private lateinit var btnEnviarNotificacion: Button

    private var buttonCount = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ejercicio3)

        createNotificationChannel()

        editTitle = findViewById(R.id.editTitle)
        editText = findViewById(R.id.editText)
        spinnerIcon = findViewById(R.id.spinnerIcon)
        spinnerImage = findViewById(R.id.spinnerImage)
        btnSubtract = findViewById(R.id.btnRestar)
        tvButtonCount = findViewById(R.id.contador)
        btnAdd = findViewById(R.id.btnSumar)
        editButtonNames = findViewById(R.id.editButtonNames)
        btnEnviarNotificacion = findViewById(R.id.btnEnviarNotificacion)

        val iconOptions = arrayOf("Icono 1", "Icono 2", "Icono 3")
        val imageOptions = arrayOf("Imagen 1", "Imagen 2", "Imagen 3")

        val iconAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, iconOptions)
        iconAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerIcon.adapter = iconAdapter
        val imageAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, imageOptions)
        imageAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerImage.adapter = imageAdapter

        btnSubtract.setOnClickListener {
            if (buttonCount > 1) {
                buttonCount--
                updateButtonCount()
            }
        }

        btnAdd.setOnClickListener {
            buttonCount++
            updateButtonCount()
        }

        btnEnviarNotificacion.setOnClickListener {
            sendNotification()
        }
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "My Channel"
            val description = "Channel description"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance)
            channel.description = description

            val notificationManager = getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun sendNotification() {
        val title = editTitle.text.toString()
        val text = editText.text.toString()
        val selectedIcon = when (spinnerIcon.selectedItemPosition) {
            0 -> R.drawable.baseline_format_color_text_24
            1 -> R.drawable.baseline_email_24
            else -> R.drawable.baseline_delete_24
        }
        val selectedImage = when (spinnerImage.selectedItemPosition) {
            0 -> R.drawable.descarga2
            1 -> R.drawable.descarga
            else -> R.drawable.ic_launcher_foreground
        }
        val buttonNames = editButtonNames.text.toString().split(",")

        val bigPicture = BitmapFactory.decodeResource(resources, selectedImage)

        val bigPictureStyle = NotificationCompat.BigPictureStyle()
            .bigPicture(bigPicture)
            .setBigContentTitle(title)
            .setSummaryText(text)

        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(selectedIcon)
            .setContentTitle(title)
            .setContentText(text)
            .setStyle(bigPictureStyle)
            .setLargeIcon(bigPicture)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        for (i in 0 until buttonCount) {
            val buttonTitle = if (i < buttonNames.size) buttonNames[i] else "Botón $i"
            val intent = Intent(this, Ejercicio3::class.java)
            val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT)

            notification.addAction(0, buttonTitle, pendingIntent)
        }

        val notificationManager = getSystemService(NotificationManager::class.java)
        notificationManager.notify(NOTIFICATION_ID, notification.build())
    }

    private fun updateButtonCount() {
        tvButtonCount.text = buttonCount.toString()
    }
}