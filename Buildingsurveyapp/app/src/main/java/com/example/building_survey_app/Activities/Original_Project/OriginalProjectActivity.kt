package com.example.building_survey_app.Activities.Original_Project

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.view.View
import android.widget.Button
import android.widget.ListView
import com.example.building_survey_app.Activities.MainPage.MainActivity
import com.example.building_survey_app.ListViewFloorItem
import com.example.building_survey_app.ListViewFloorItemAdapter
import com.example.building_survey_app.R
import java.io.File

class OriginalProjectActivity : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_original_project)

        var dir = getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS);
        var allFiles = dir?.listFiles();

        if (allFiles == null)
            return;

        var allProjectListViewItem = arrayListOf<ListViewFloorItem>()
        for (file in allFiles)
        {
            if (file.isDirectory)
            {
                var insideFileList = file.listFiles();

                var xlsxfile = insideFileList.find{
                    f->f.name == "결함정보.xlsx";
                }

                if (xlsxfile!= null)
                {
                    var existProject = ListViewFloorItem();
                    existProject.projectName = file.nameWithoutExtension;
                    allProjectListViewItem.add( existProject );
                }
            }
        }

        var projectListView =findViewById<ListView>(R.id.TotalProjectList);
        projectListView.adapter = ListViewFloorItemAdapter(this, allProjectListViewItem);
        findViewById<Button>(R.id.goToHome).setOnClickListener(this);
    }

    override fun onClick(v: View?) {
        when(v?.id)
        {
            R.id.goToHome->{
                var homeIntent = Intent(this, MainActivity::class.java)
                startActivity(homeIntent)
            }
        }
    }
}