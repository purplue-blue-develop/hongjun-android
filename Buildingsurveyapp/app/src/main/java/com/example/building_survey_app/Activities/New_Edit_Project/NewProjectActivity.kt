package com.example.building_survey_app.Activities.New_Edit_Project

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.building_survey_app.Activities.Original_Project.OriginalProjectActivity
import com.example.building_survey_app.Activities.Popups.PopupShowingProjectList
import com.example.building_survey_app.Models.BuildingProject
import com.example.building_survey_app.R
import com.example.building_survey_app.ViewModels.BuildingProjectListViewModel
import java.time.LocalDateTime


class NewProjectActivity : AppCompatActivity(), View.OnClickListener {
    var buildingProject :BuildingProject? = null;
    private lateinit var linearLayoutManager: LinearLayoutManager;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_project)

        linearLayoutManager = LinearLayoutManager(this);
        findViewById<RecyclerView>(R.id.recylerView).layoutManager = linearLayoutManager;

        var SearchProjectButton : Button =  findViewById(R.id.SearchProjectButton);
        var CreateProjectBuildingModelButton : Button = findViewById(R.id.CreateProjectBuildingModelButton);

        SearchProjectButton.setOnClickListener(this)
        CreateProjectBuildingModelButton.setOnClickListener(this)

        findViewById<Button>(R.id.OpenCalendarDialog).setOnClickListener(this);
        findViewById<Button>(R.id.AddBuildingFloor).setOnClickListener(this);
    }

    override fun onClick(v: View?) {
        when (v?.id)
        {
            R.id.SearchProjectButton->
            {
                var intent = Intent(this, PopupShowingProjectList::class.java);
                startActivityForResult(intent, 1);
            }
            R.id.CreateProjectBuildingModelButton->
            {
                buildingProject = BuildingProject();
                buildingProject?.projectName = findViewById<TextView>(R.id.textViewProjectName).text.toString();
                buildingProject?.buildingName = findViewById<TextView>(R.id.textViewProjectName).text.toString();
                buildingProject?.investDate;
                buildingProject?.latestEditedDte = LocalDateTime.now();
                BuildingProjectListViewModel.BuildingProjectList.add(buildingProject!!);
                startActivity(Intent(this, OriginalProjectActivity::class.java));
            }
            R.id.OpenCalendarDialog->
            {

            }
            R.id.AddBuildingFloor->
            {
            }
        }
    }
}
