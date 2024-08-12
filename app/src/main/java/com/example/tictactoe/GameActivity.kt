package com.example.tictactoe

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Button
import android.widget.GridLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

class GameActivity : AppCompatActivity() {

    private lateinit var player1Name: String
    private lateinit var player2Name: String
    private var player1Turn: Boolean = true
    private var board: Array<Array<String?>> = Array(3) { arrayOfNulls<String>(3) }
    private var player1Score: Int = 0
    private var player2Score: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        player1Name = intent.getStringExtra("PLAYER1_NAME").orEmpty()
        player2Name = intent.getStringExtra("PLAYER2_NAME").orEmpty()
        player1Score = intent.getIntExtra("PLAYER1_SCORE", 0)
        player2Score = intent.getIntExtra("PLAYER2_SCORE", 0)

        val playerTurnText = findViewById<TextView>(R.id.playerTurn)
        val player1ScoreText = findViewById<TextView>(R.id.player1Score)
        val player2ScoreText = findViewById<TextView>(R.id.player2Score)
        val gameGrid = findViewById<GridLayout>(R.id.gameGrid)

        updateTurnText(playerTurnText)
        updateScoreText(player1ScoreText, player2ScoreText)

        for (i in 0 until gameGrid.childCount) {
            val button = gameGrid.getChildAt(i) as Button
            button.setOnClickListener {
                onGridButtonClick(button, i / 3, i % 3, playerTurnText, player1ScoreText, player2ScoreText)
            }
        }
    }

    private fun onGridButtonClick(
        button: Button, row: Int, col: Int,
        playerTurnText: TextView,
        player1ScoreText: TextView,
        player2ScoreText: TextView
    ) {
        if (board[row][col] == null) {
            board[row][col] = if (player1Turn) "X" else "O"
            button.text = board[row][col]
            if (checkWinner()) {
                val winner = if (player1Turn) player1Name else player2Name
                if (player1Turn) player1Score++ else player2Score++
                updateScoreText(player1ScoreText, player2ScoreText)
                showNotification("Winner", "$winner wins!")
                navigateToWinnerActivity(winner)
            } else {
                player1Turn = !player1Turn
                updateTurnText(playerTurnText)
            }
        }
    }

    private fun updateTurnText(playerTurnText: TextView) {
        playerTurnText.text = if (player1Turn) "$player1Name's Turn (X)" else "$player2Name's Turn (O)"
    }

    private fun updateScoreText(player1ScoreText: TextView, player2ScoreText: TextView) {
        player1ScoreText.text = "$player1Name: $player1Score"
        player2ScoreText.text = "$player2Name: $player2Score"
    }

    private fun checkWinner(): Boolean {
        for (i in 0..2) {
            if ((board[i][0] != null && board[i][0] == board[i][1] && board[i][1] == board[i][2]) ||
                (board[0][i] != null && board[0][i] == board[1][i] && board[1][i] == board[2][i])
            ) {
                return true
            }
        }
        return (board[0][0] != null && board[0][0] == board[1][1] && board[1][1] == board[2][2]) ||
                (board[0][2] != null && board[0][2] == board[1][1] && board[1][1] == board[2][0])
    }

    private fun navigateToWinnerActivity(winner: String) {
        val intent = Intent(this, WinnerActivity::class.java).apply {
            putExtra("WINNER", winner)
            putExtra("PLAYER1_NAME", player1Name)
            putExtra("PLAYER2_NAME", player2Name)
            putExtra("PLAYER1_SCORE", player1Score)
            putExtra("PLAYER2_SCORE", player2Score)
        }
        startActivity(intent)
    }

    private fun showNotification(title: String, text: String) {
        // Create the notification builder
        val builder = NotificationCompat.Builder(this, "default")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle(title)
            .setContentText(text)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        // Get NotificationManagerCompat instance
        val notificationManager = NotificationManagerCompat.from(this)

        // Check if notification permissions are granted
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // Handle the case where notification permissions are not granted
            // You can request the permission if needed
            return
        }

        // Show the notification
        notificationManager.notify(1, builder.build())
    }

}
