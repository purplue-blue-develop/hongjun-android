package com.example.building_survey_app.Activities.New_Edit_Project

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import com.example.building_survey_app.R
import com.example.building_survey_app.ViewModels.BuildingProjectListViewModel

class NewProjectActivity : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_project)

        var SearchProjectButton : Button =  findViewById(R.id.SearchProjectButton);
        var CreateProjectBuildingModelButton : Button = findViewById(R.id.CreateProjectBuildingModelButton);

        SearchProjectButton.setOnClickListener(this)
        CreateProjectBuildingModelButton.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id)
        {
            R.id.SearchProjectButton->
            {
                Log.d("asd", "Fff");
            }
            R.id.CreateProjectBuildingModelButton->
            {
                Log.d("fff", "kkkk");
            }
        }
    }
}
