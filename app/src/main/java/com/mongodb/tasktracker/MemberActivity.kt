package com.mongodb.tasktracker

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.mongodb.tasktracker.model.*
import io.realm.mongodb.functions.Functions
import org.bson.Document
import java.util.*

/*
* MemberActivity: allows a user to view, add, and remove the members of their project.
*/
class MemberActivity : AppCompatActivity() {
    private var user: io.realm.mongodb.User? = null
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: MemberAdapter
    private lateinit var fab: FloatingActionButton
    private lateinit var members: ArrayList<Member>

    override fun onStart() {
        super.onStart()
        user = taskApp.currentUser()
        if (user == null) {
            // if no user is currently logged in, start the login activity so the user can authenticate
            startActivity(Intent(this, LoginActivity::class.java))
        } else {
            // display a descriptive title for the activity in the action bar via the title member variable of the Activity
            val projectName = intent.extras?.getString(PROJECT_NAME_EXTRA_KEY)
            title = "Manage Team"
            setUpRecyclerView()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_member)

        recyclerView = findViewById(R.id.project_users_list)
        fab = findViewById(R.id.floating_action_button)
        // create a dialog to add a user by email when an item is clicked
        fab.setOnClickListener {
            val input = EditText(this)
            val dialogBuilder = AlertDialog.Builder(this)
            dialogBuilder.setMessage("Add user to project by email:")
                .setCancelable(true)
                .setPositiveButton("Add User") { dialog, _ ->
                    dialog.dismiss()
                    // TODO: Add the new team member to the project by calling the `addTeamMember` Realm Function through `taskApp`.
                }
                .setNegativeButton("Cancel") { dialog, _ ->
                    dialog.cancel()
                }

            val dialog = dialogBuilder.create()
            dialog.setView(input)
            dialog.setTitle("Add Team Member")
            dialog.show()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        recyclerView.adapter = null
    }

    private fun setUpRecyclerView() {
        // TODO: Call the `getMyTeamMembers` function to get a list of team members, then display them in a RecyclerView with the following code:
        // The `getMyTeamMembers` function returns team members as Document objects. Convert them into Member objects so the MemberAdapter can display them.
        // this.members = ArrayList(result.get().map { item -> Member(item as Document) })
        // adapter = MemberAdapter(members, user!!)
        // recyclerView.layoutManager = LinearLayoutManager(this)
        // recyclerView.adapter = adapter
        // recyclerView.setHasFixedSize(true)
        // recyclerView.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
    }
}
