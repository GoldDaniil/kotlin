package com.example.myapplication

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView

class ViewPager2 : RecyclerView.Adapter<ViewPager2.ViewHolder>() {

    private val layouts = listOf(
        R.layout.block_layout_1,
        R.layout.block_layout_2,
        R.layout.block_layout_3
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(viewType, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // Обработка первой страницы
        if (position == 0) {
            val context = holder.itemView.context
            val buttonQuiz = holder.itemView.findViewById<Button>(R.id.button_quiz)
            val buttonQuizPhoto = holder.itemView.findViewById<Button>(R.id.button_quiz_photo)

            buttonQuiz.setOnClickListener {
                context.startActivity(Intent(context, QuizActivity::class.java))
            }

            buttonQuizPhoto.setOnClickListener {
                context.startActivity(Intent(context, QuizGameActivity::class.java))
            }
        }
    }

    override fun getItemCount(): Int = layouts.size

    override fun getItemViewType(position: Int): Int = layouts[position]

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}
