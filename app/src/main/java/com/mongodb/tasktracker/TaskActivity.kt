package com.mongodb.tasktracker

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import io.realm.Realm
import io.realm.mongodb.User
import io.realm.kotlin.where
import io.realm.mongodb.sync.SyncConfiguration
import com.mongodb.tasktracker.model.TaskAdapter
import com.mongodb.tasktracker.model.Task

/*
* TaskActivity: allows a user to view a collection of Tasks, edit the status of those tasks,
* create new tasks, and delete existing tasks from the collection. All tasks are stored in a realm
* and synced across devices using the partition "project=<user id>".
*/
class TaskActivity : AppCompatActivity() {
    private lateinit var projectRealm: Realm
    private var user: User? = null
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: TaskAdapter
    private lateinit var fab: FloatingActionButton
    private lateinit var partition: String

    override fun onStart() {
        super.onStart()
        user = taskApp.currentUser()
        if (user == null) {
            // if no user is currently logged in, start the login activity so the user can authenticate
            startActivity(Intent(this, LoginActivity::class.java))
        }
        else {
            // get the partition value and name of the project we are currently viewing
            partition = intent.extras?.getString(PARTITION_EXTRA_KEY)!!
            val projectName = intent.extras?.getString(PROJECT_NAME_EXTRA_KEY)

            // display the name of the project in the action bar via the title member variable of the Activity
            title = projectName

            // TODO: initialize a connection to a realm containing all of the Tasks in this project
        }
    }

    // TODO: always ensure that the project realm closes when the activity ends via the onStop lifecycle method


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task)
        recyclerView = findViewById(R.id.task_list)
        fab = findViewById(R.id.floating_action_button)

        // create a dialog to enter a task name when the floating action button is clicked
        fab.setOnClickListener {
            val input = EditText(this)
            val dialogBuilder = AlertDialog.Builder(this)
            dialogBuilder.setMessage("Enter task name:")
                .setCancelable(true)
                .setPositiveButton("Create") { dialog, _ -> run {
                    dialog.dismiss()
                    // TODO: Add a new task to the project by inserting into the realm when the user clicks "create" for a new task.
                }
                }
                .setNegativeButton("Cancel") { dialog, _ -> dialog.cancel()
                }

            val dialog = dialogBuilder.create()
            dialog.setView(input)
            dialog.setTitle("Create New Task")
            dialog.show()
        }
    }

    // TODO: always ensure that the project realm closes when the activity ends via the onDestroy lifecycle method


    private fun setUpRecyclerView(realm: Realm, user: User?, partition: String) {
        // a recyclerview requires an adapter, which feeds it items to display.
        // Realm provides RealmRecyclerViewAdapter, which you can extend to customize for your application
        // pass the adapter a collection of Tasks from the realm
        // sort this collection so that the displayed order of Tasks remains stable across updates
        // TODO: Query the realm for Task objects, sorted by a stable order that remains consistent between runs.
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
        recyclerView.setHasFixedSize(true)
        recyclerView.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
    }
}
