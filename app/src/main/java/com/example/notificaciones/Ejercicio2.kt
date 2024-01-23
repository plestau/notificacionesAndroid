package com.example.notificaciones

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.core.app.NotificationCompat
import androidx.core.graphics.drawable.toBitmap

class Ejercicio2 : AppCompatActivity(R.layout.activity_ejercicio2) {

    private var notificationIdCounter = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ejercicio2)

        val bigPictureBtn = findViewById<Button>(R.id.btnBigPicture)
        val bigTextBtn = findViewById<Button>(R.id.btnBigText)

        bigPictureBtn.setOnClickListener {
            mostrarNotificacionBigPicture()
        }

        bigTextBtn.setOnClickListener {
            mostrarNotificacionBigText()
        }
    }

    private fun mostrarNotificacionBigPicture() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelId = "mi_canal_id"
            val channelName = "Mi canal"
            val notificationChannel = NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_DEFAULT)
            this.getSystemService(NotificationManager::class.java)?.createNotificationChannel(notificationChannel)
        }

        val intent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE)

        val otraAccionIntent = Intent(this, MainActivity::class.java)
        val otraAccionPendingIntent = PendingIntent.getActivity(this, 1, otraAccionIntent, PendingIntent.FLAG_IMMUTABLE)

        val builder = NotificationCompat.Builder(this, "mi_canal_id")
            .setSmallIcon(R.drawable.baseline_image_24)
            .setContentTitle("Notificación media")
            .setContentText("Hola, soy una notificación con imagen")
            .setColor(resources.getColor(R.color.black))
            .setContentIntent(pendingIntent)
            .setStyle(NotificationCompat.BigPictureStyle().bigPicture(resources.getDrawable(R.drawable.descarga).toBitmap()))
            .addAction(R.drawable.baseline_delete_24, "Borrar", otraAccionPendingIntent)
            .addAction(R.drawable.baseline_share_24, "Compartir", otraAccionPendingIntent)

        with(this.getSystemService(NotificationManager::class.java)) {
            this?.notify(notificationIdCounter, builder.build())
            notificationIdCounter++
        }
    }

    private fun mostrarNotificacionBigText() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelId = "mi_canal_id"
            val channelName = "Mi canal"
            val notificationChannel = NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_DEFAULT)
            this.getSystemService(NotificationManager::class.java)?.createNotificationChannel(notificationChannel)
        }

        val intent = Intent(this, Ejercicio2::class.java)
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE)

        val otraAccionIntent = Intent(this, MainActivity::class.java)
        val otraAccionPendingIntent = PendingIntent.getActivity(this, 1, otraAccionIntent, PendingIntent.FLAG_IMMUTABLE)

        val builder = NotificationCompat.Builder(this, "mi_canal_id")
            .setSmallIcon(R.drawable.baseline_format_color_text_24)
            .setContentTitle("Notificación texto")
            .setContentText("Hola, soy una notificación de texto largo")
            .setColor(resources.getColor(R.color.black))
            .setContentIntent(pendingIntent)
            .setStyle(NotificationCompat.BigTextStyle().bigText(getString(R.string.texto_largo)))
            .addAction(R.drawable.baseline_delete_24, "Borrar", otraAccionPendingIntent)
            .addAction(R.drawable.baseline_share_24, "Compartir", otraAccionPendingIntent)

        with(this.getSystemService(NotificationManager::class.java)) {
            this?.notify(notificationIdCounter, builder.build())
            notificationIdCounter++
        }
    }
}