package com.release_next_project_safa_ptintln_out_systems_release_project.presentation.activity.screen

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import com.release_next_project_safa_ptintln_out_systems_release_project.data.data_serach.local.storage.SqlManager2
import com.release_next_project_safa_ptintln_out_systems_release_project.databinding.ActivityMainBinding
import com.release_next_project_safa_ptintln_out_systems_release_project.domain.models.UserTaskInfo2
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        val db = SqlManager2.getSqlDatabase(this)

        Thread {
            db.getUserTaskInfoDao().insertUserTaskInfo(
                UserTaskInfo2(null, true, "Yeeah, it works!")
            )
        }.start()

        lifecycleScope.launchWhenStarted {

            db.getUserTaskInfoDao().getUserTasks().onEach {
                Log.d("MyLog", "List: $it")
            }.collect()

        }

    }

}