package com.example.building_survey_app.Activities.MainPage

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.building_survey_app.Activities.New_Edit_Project.NewProjectActivity
import com.example.building_survey_app.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 새 프로젝트 클릭시
        val buttonStartnewproject : Button = findViewById(R.id.button_StartNewProject);
        buttonStartnewproject.setOnClickListener {
            val NewProjectIntent = Intent(this,  NewProjectActivity::class.java);
            startActivity(NewProjectIntent);
        }
    }
}
