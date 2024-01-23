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

class Ejercicio1 : AppCompatActivity(R.layout.activity_ejercicio1) {

    private var notificationIdCounter = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ejercicio1)

        val botonNotificaciones = findViewById<Button>(R.id.btnNotificaciones)
        botonNotificaciones.setOnClickListener {
            mostrarNotificacion()
        }
    }

    private fun mostrarNotificacion() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelId = "mi_canal_id"
            val channelName = "Mi canal"
            val notificationChannel = NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_DEFAULT)
            this.getSystemService(NotificationManager::class.java)?.createNotificationChannel(notificationChannel)
        }

        val intent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE)

        val otraAccionIntent = Intent(this, Ejercicio1::class.java)
        val otraAccionPendingIntent = PendingIntent.getActivity(this, 1, otraAccionIntent, PendingIntent.FLAG_IMMUTABLE)

        val builder = NotificationCompat.Builder(this, "mi_canal_id")
            .setSmallIcon(R.drawable.baseline_email_24)
            .setContentTitle("Notificación")
            .setContentText("Tienes un correo, Mi id es $notificationIdCounter")
            .setColor(resources.getColor(R.color.black))
            .setContentIntent(pendingIntent)
            .addAction(R.drawable.ic_launcher_foreground, "Ir al ejercicio 1", otraAccionPendingIntent)

        with(this.getSystemService(NotificationManager::class.java)) {
            this?.notify(notificationIdCounter, builder.build())
            notificationIdCounter++
        }
    }
}