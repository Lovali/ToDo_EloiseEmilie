package com.eloemi.todo.tasklist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.eloemi.todo.R
import com.eloemi.todo.databinding.FragmentTaskListBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.util.*

class TaskListFragment : Fragment() {
    private var taskList = listOf(
        Task(id = "id_1", title = "Task 1", description = "description 1"),
        Task(id = "id_2", title = "Task 2"),
        Task(id = "id_3", title = "Task 3")
    )
    private val adapter = TaskListAdapter()
    private lateinit var binding : FragmentTaskListBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTaskListBinding.inflate(layoutInflater)
        val rootView = binding.root
        adapter.submitList(taskList)
        adapter.onClickDelete = { task ->
            taskList = taskList - task;
            refreshAdapter()
        }
        return rootView
    }

    fun refreshAdapter() {
        adapter.submitList(taskList)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.recyclerView.adapter = adapter
        binding.floatingActionButton.setOnClickListener {
            // Instanciation d'un objet task avec des données préremplies:
            val newTask = Task(id = UUID.randomUUID().toString(), title = "Task ${taskList.size + 1}")
            taskList = taskList + newTask
            refreshAdapter()
        }


    }
}