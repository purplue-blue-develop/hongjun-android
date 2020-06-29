package com.example.building_survey_app.Activities.MainPage

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.example.building_survey_app.Activities.FlawCheck.FlawCheckActivity
import com.example.building_survey_app.Activities.FlawList.FlawListActivity
import com.example.building_survey_app.Activities.New_Edit_Project.NewProjectActivity
import com.example.building_survey_app.Activities.Original_Project.OriginalProjectActivity
import com.example.building_survey_app.R

class MainActivity : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 새 프로젝트 클릭시
        val buttonStartnewproject : Button = findViewById(R.id.button_StartNewProject);
        buttonStartnewproject.setOnClickListener(this);
        
        //프로젝트 불러오기
        val buttonBringOriginalProject : Button = findViewById(R.id.button_LoadExistProject);
        buttonBringOriginalProject.setOnClickListener(this);
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.button_StartNewProject -> {
                val NewProjectIntent = Intent(this,  FlawCheckActivity::class.java);
                startActivity(NewProjectIntent);
            }
            R.id.button_LoadExistProject -> {
                val originalProjectIntent = Intent(this, FlawListActivity:: class.java);
                originalProjectIntent.putExtra("LOAD", "LOAD");
                startActivity(originalProjectIntent);
            }
        }
    }
}
