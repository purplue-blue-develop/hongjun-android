package com.example.building_survey_app.Activities.Popups

import android.app.Application
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.example.building_survey_app.Models.BuildingProject
import com.example.building_survey_app.Models.Floor
import com.example.building_survey_app.R
import com.example.building_survey_app.ViewModels.BuildingProjectListViewModel

class PopupAddingFloorList : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_popup_adding_floor_list)
        findViewById<Button>(R.id.buttonAddFloor).setOnClickListener(this);
        findViewById<Button>(R.id.buttonClosePopupAddingFloorList).setOnClickListener(this);
        BuildingProjectListViewModel.BuildingProjectList.add(BuildingProject());
    }

    override fun onClick(v: View?) {
        when(v?.id)
        {
            R.id.buttonClosePopupAddingFloorList ->
            {
                finish();
            }
            R.id.buttonAddFloor->
            {
                var project : BuildingProject = BuildingProjectListViewModel.BuildingProjectList?.get(0);
                val floorName  = findViewById<EditText>(R.id.editTextFloorName).text.toString();
                project.floorList.add(Floor(floorName));

                var floorNames = mutableListOf<String>();
                for (floor in project.floorList)
                {
                    if(floorNames.contains(floor.Name)){
                        Toast.makeText(this, "floor already exist", Toast.LENGTH_SHORT).show()
                    } else{
                        floorNames.add(floor.Name);
                    }

                }

                val floorListView = findViewById<ListView>(R.id.ListViewShowFloorList)
                val adapter = ArrayAdapter(applicationContext, android.R.layout.simple_list_item_1,floorNames)
                //adapter.add(Floor(floorName))
                floorListView.adapter = adapter

            }
        }
    }
}
