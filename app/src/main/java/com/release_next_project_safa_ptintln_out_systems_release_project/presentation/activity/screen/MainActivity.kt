package com.release_next_project_safa_ptintln_out_systems_release_project.presentation.activity.screen

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.release_next_project_safa_ptintln_out_systems_release_project.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }

}