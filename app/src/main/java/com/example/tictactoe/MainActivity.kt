package com.example.tictactoe

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val player1Name = findViewById<EditText>(R.id.player1Name)
        val player2Name = findViewById<EditText>(R.id.player2Name)
        val startGameButton = findViewById<Button>(R.id.startGameButton)

        startGameButton.setOnClickListener {
            val intent = Intent(this, GameActivity::class.java).apply {
                putExtra("PLAYER1_NAME", player1Name.text.toString())
                putExtra("PLAYER2_NAME", player2Name.text.toString())
                putExtra("PLAYER1_SCORE", 0)
                putExtra("PLAYER2_SCORE", 0)
            }
            startActivity(intent)
        }
    }

}
