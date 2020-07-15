package com.chaosApp.chaos.Activities.MainPage

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.chaosApp.chaos.Activities.New_Edit_Project.NewProjectActivity
import com.chaosApp.chaos.Activities.Original_Project.OriginalProjectActivity
import com.chaosApp.chaos.R

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
                val NewProjectIntent = Intent(this,  NewProjectActivity::class.java);
                startActivity(NewProjectIntent);
            }
            R.id.button_LoadExistProject -> {
//                val originalProjectIntent = Intent(this, FlawListActivity:: class.java);
//                originalProjectIntent.putExtra("LOAD", "LOAD");
//                startActivity(originalProjectIntent);

                val originalProjectIntent = Intent(this, OriginalProjectActivity:: class.java);
//                originalProjectIntent.putExtra("LOAD", "LOAD");
//                startActivity(originalProjectIntent);
                startActivity(originalProjectIntent);
            }
        }
    }
}
