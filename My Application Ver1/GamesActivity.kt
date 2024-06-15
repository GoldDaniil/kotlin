package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2

data class Game(
    val imageResId: Int,
    val description: String,
    val activityClass: Class<*>
)

class GamesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_games)

        val games = listOf(
            Game(R.drawable.photo1, "Description of Quiz Game", QuizActivity::class.java),
            Game(R.drawable.photo2, "Description of Memory Game", QuizGameActivity::class.java),
            Game(R.drawable.photo3, "Description of Loop Game", LoopGameActivity::class.java),
            Game(R.drawable.photo4, "Description of Reaction Game", ReactionGameActivity::class.java)
        )

        val viewPager: ViewPager2 = findViewById(R.id.view_pager)
        viewPager.adapter = GamePagerAdapter(games)
    }

    inner class GamePagerAdapter(
        private val games: List<Game>
    ) : RecyclerView.Adapter<GamePagerAdapter.GameViewHolder>() {

        inner class GameViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val gameImage: ImageView = itemView.findViewById(R.id.game_image)
            val gameDescription: TextView = itemView.findViewById(R.id.game_description)
            val playButton: Button = itemView.findViewById(R.id.play_button)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.card_layout, parent, false)
            return GameViewHolder(view)
        }

        override fun onBindViewHolder(holder: GameViewHolder, position: Int) {
            val game = games[position]
            holder.gameImage.setImageResource(game.imageResId)
            holder.gameDescription.text = game.description
            holder.playButton.setOnClickListener {
                val intent = Intent(this@GamesActivity, game.activityClass)
                startActivity(intent)
            }

            val backgroundColor = when (position) {
                0 -> android.R.color.holo_green_light
                1 -> android.R.color.holo_orange_light
                2 -> android.R.color.holo_red_light
                3 -> android.R.color.holo_blue_light
                else -> android.R.color.white
            }
            holder.itemView.setBackgroundColor(resources.getColor(backgroundColor, null))
        }

        override fun getItemCount(): Int {
            return games.size
        }
    }
}
