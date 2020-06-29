package com.example.building_survey_app.Activities.Popups

import android.app.Activity
import android.app.Application
import android.content.Intent
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
        var project : BuildingProject = BuildingProjectListViewModel.BuildingProjectList[0];
        var floorNames = mutableListOf<String>();

        for (floor in project.floorList)
        {
            floorNames.add(floor.Name);
        }

        val floorListView = findViewById<ListView>(R.id.ListViewShowFloorList)
        val adapter = ArrayAdapter(applicationContext, android.R.layout.simple_list_item_1,floorNames)
        floorListView.adapter = adapter

    }

    override fun onClick(v: View?) {
        when(v?.id)
        {
            R.id.buttonClosePopupAddingFloorList ->
            {
                intent = Intent();
                intent.putExtra("result", "UpdateFloorList");
                setResult(Activity.RESULT_OK,intent);
                finish();
            }
            R.id.buttonAddFloor->
            {
                var project : BuildingProject = BuildingProjectListViewModel.BuildingProjectList?.get(0);
                val floorName  = findViewById<EditText>(R.id.editTextFloorName).text.toString();

                var floorNames = mutableListOf<String>();
                for (floor in project.floorList)
                {
                    floorNames.add(floor.Name);
                }

                if(floorNames.contains(floorName))
                {
                    Toast.makeText(this, "층이 이미 존재합니다.", Toast.LENGTH_SHORT).show()
                }
                else if ( floorName.isNullOrEmpty())
                {
                    Toast.makeText(this, "층은 공백이 될 수 없습니다.", Toast.LENGTH_SHORT).show()
                }
                else
                {
                    floorNames.add(floorName);
                    project.floorList.add(Floor(floorName))
                };


                val floorListView = findViewById<ListView>(R.id.ListViewShowFloorList)
                val adapter = ArrayAdapter(applicationContext, android.R.layout.simple_list_item_1,floorNames)
                floorListView.adapter = adapter

            }
        }
    }
}
