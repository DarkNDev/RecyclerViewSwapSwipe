package com.darkndev.recyclerviewswapswipe

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.darkndev.recyclerviewswapswipe.databinding.ActivityMainBinding
import java.util.Collections

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val itemAdapter = ItemAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            setSupportActionBar(toolbar)

            recyclerView.adapter = itemAdapter

            ItemTouchHelper(callback).attachToRecyclerView(recyclerView)

            repopulateItems.setOnClickListener {
                itemAdapter.submitList(repopulate())
            }
        }

        itemAdapter.submitList(repopulate())
    }

    private fun repopulate() = listOf(
        Item(1, "Title 1", "Content 1"),
        Item(2, "Title 2", "Content 2"),
        Item(3, "Title 3", "Content 3"),
        Item(4, "Title 4", "Content 4"),
        Item(5, "Title 5", "Content 5"),
        Item(6, "Title 6", "Content 6"),
        Item(7, "Title 7", "Content 7")
    )

    private val callback =
        object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN or ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT,
            ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                val fromPosition = viewHolder.bindingAdapterPosition
                val toPosition = target.bindingAdapterPosition
                val items = itemAdapter.currentList.toMutableList()
                Collections.swap(items, fromPosition, toPosition)
                itemAdapter.submitList(items)
                Toast.makeText(
                    this@MainActivity,
                    "Item Swapped from ${fromPosition + 1} to ${toPosition + 1}",
                    Toast.LENGTH_SHORT
                ).show()
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val items = itemAdapter.currentList.toMutableList()
                items.removeAt(viewHolder.bindingAdapterPosition)
                itemAdapter.submitList(items)
                Toast.makeText(this@MainActivity, "Item Deleted", Toast.LENGTH_SHORT).show()
            }
        }
}