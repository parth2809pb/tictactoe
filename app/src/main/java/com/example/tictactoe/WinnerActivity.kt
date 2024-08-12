package com.example.tictactoe

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class WinnerActivity : AppCompatActivity() {

    private lateinit var player1Name: String
    private lateinit var player2Name: String
    private var player1Score: Int = 0
    private var player2Score: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_winner)

        val winner = intent.getStringExtra("WINNER").orEmpty()
        player1Name = intent.getStringExtra("PLAYER1_NAME").orEmpty()
        player2Name = intent.getStringExtra("PLAYER2_NAME").orEmpty()
        player1Score = intent.getIntExtra("PLAYER1_SCORE", 0)
        player2Score = intent.getIntExtra("PLAYER2_SCORE", 0)

        val winnerText = findViewById<TextView>(R.id.winnerText)
        winnerText.text = "$winner wins!"

        val player1ScoreText = findViewById<TextView>(R.id.player1Score)
        val player2ScoreText = findViewById<TextView>(R.id.player2Score)
        player1ScoreText.text = "$player1Name: $player1Score"
        player2ScoreText.text = "$player2Name: $player2Score"

        val playAgainButton = findViewById<Button>(R.id.playAgainButton)
        val resetGameButton = findViewById<Button>(R.id.resetGameButton)

        playAgainButton.setOnClickListener {
            val intent = Intent(this, GameActivity::class.java).apply {
                putExtra("PLAYER1_NAME", player1Name)
                putExtra("PLAYER2_NAME", player2Name)
                putExtra("PLAYER1_SCORE", player1Score)
                putExtra("PLAYER2_SCORE", player2Score)
            }
            startActivity(intent)
        }

        resetGameButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}
