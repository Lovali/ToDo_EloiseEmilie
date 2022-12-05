package com.eloemi.todo.tasklist

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.eloemi.todo.R
import com.eloemi.todo.data.Api
import com.eloemi.todo.databinding.FragmentTaskListBinding
import com.eloemi.todo.detail.DetailActivity
import kotlinx.coroutines.launch

class TaskListFragment : Fragment() {
    /*private var taskList = listOf(
        Task(id = "id_1", title = "Task 1", description = "description 1"),
        Task(id = "id_2", title = "Task 2"),
        Task(id = "id_3", title = "Task 3")
    )*/
    val adapterListener : TaskListListener = object : TaskListListener {
        override fun onClickDelete(task: Task) {
            viewModel.remove(task)
            viewModel.refresh()
            //taskList = taskList - task;
            //refreshAdapter()
        }

        override fun onClickEdit(task: Task) {
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra("task", task)
            editTask.launch(intent)
        }
    }
    private val adapter = TaskListAdapter(adapterListener)
    private lateinit var binding : FragmentTaskListBinding
    val createTask = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        val task = result.data?.getSerializableExtra("task") as Task
        viewModel.add(task)
        //taskList = (taskList + task!!)
        viewModel.refresh()
    }
    val editTask = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        val task = result.data?.getSerializableExtra("task") as Task
        viewModel.edit(task)
        //taskList = taskList.map { if (it.id == task.id) task else it }
        viewModel.refresh()
    }
    private val viewModel: TasksListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTaskListBinding.inflate(layoutInflater)
        val rootView = binding.root
        //adapter.submitList(taskList)
        /*adapter.onClickDelete = { task ->
            taskList = taskList - task;
            refreshAdapter()
        }
        adapter.onClickEdit = { task ->
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra("task", task)
            editTask.launch(intent)
        }*/
        return rootView
    }

    /*fun refreshAdapter() {
        adapter.submitList(taskList)
    }*/

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.recyclerView.adapter = adapter
        binding.floatingActionButton.setOnClickListener {
            // Instanciation d'un objet task avec des données préremplies:
            //val newTask = Task(id = UUID.randomUUID().toString(), title = "Task ${taskList.size + 1}")
            //taskList = taskList + newTask
            //refreshAdapter()
            val intent = Intent(context, DetailActivity::class.java)
            createTask.launch(intent)
        }
        lifecycleScope.launch { // on lance une coroutine car `collect` est `suspend`
            viewModel.tasksStateFlow.collect { newList ->
                // cette lambda est executée à chaque fois que la liste est mise à jour dans le VM
                adapter.submitList(newList)
            }
        }


    }

    override fun onResume() {
        super.onResume()
        lifecycleScope.launch {
            mySuspendMethod()
        }
        viewModel.refresh()
    }

    private suspend fun mySuspendMethod() {
        val user = Api.userWebService.fetchUser().body()!!
        view?.findViewById<TextView>(R.id.userTextView)?.text = user.name
    }
}