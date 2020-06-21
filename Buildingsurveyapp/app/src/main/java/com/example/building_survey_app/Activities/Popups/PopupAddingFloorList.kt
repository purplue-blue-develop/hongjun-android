package com.example.building_survey_app.Activities.Popups

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import com.example.building_survey_app.Models.BuildingProject
import com.example.building_survey_app.Models.Floor
import com.example.building_survey_app.R
import com.example.building_survey_app.ViewModels.BuildingProjectListViewModel

class PopupAddingFloorList : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_popup_adding_floor_list)
        findViewById<Button>(R.id.buttonClosePopupAddingFloorList).setOnClickListener(this);
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
            }
        }
    }
}
