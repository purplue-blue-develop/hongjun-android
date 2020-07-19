package com.chaosApp.chaos.Activities.Popups

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.chaosApp.chaos.Models.BuildingProject
import com.chaosApp.chaos.R
import com.chaosApp.chaos.ViewModels.BuildingProjectListViewModel

class PopupAddingFlawNameList : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_popup_adding_flaw_name_list)
        findViewById<Button>(R.id.buttonAddFlawName).setOnClickListener(this)
        findViewById<Button>(R.id.buttonClosePopupAddingFlawNameList).setOnClickListener(this)

        var project : BuildingProject = BuildingProjectListViewModel.BuildingProjectList[0];
        var flawNames = mutableListOf<String>();

        for (flawName in project.flawNameList)
        {
            flawNames.add(flawName);
        }

        val floorListView = findViewById<ListView>(R.id.ListViewShowFlawNameList)
        val adapter = ArrayAdapter(applicationContext, android.R.layout.simple_list_item_1,flawNames)
        floorListView.adapter = adapter
    }

    override fun onClick(v: View?) {
        when(v?.id)
        {
            R.id.buttonAddFlawName->{
                var project : BuildingProject = BuildingProjectListViewModel.BuildingProjectList?.get(0);
                val flawName  = findViewById<EditText>(R.id.editTextFlawName).text.toString();

                var flawNames = mutableListOf<String>();
                for (flawName in project.flawNameList)
                {
                    flawNames.add(flawName);
                }

                if(flawNames.contains(flawName))
                {
                    Toast.makeText(this, "실명이 이미 존재합니다.", Toast.LENGTH_SHORT).show()
                }
                else if ( flawName.isNullOrEmpty())
                {
                    Toast.makeText(this, "실명은 공백이 될 수 없습니다.", Toast.LENGTH_SHORT).show()
                }
                else
                {
                    flawNames.add(flawName);
                    project.flawNameList.add(flawName)
                };

                val floorListView = findViewById<ListView>(R.id.ListViewShowFlawNameList)
                val adapter = ArrayAdapter(applicationContext, android.R.layout.simple_list_item_1,flawNames)
                floorListView.adapter = adapter
            }
            R.id.buttonClosePopupAddingFlawNameList->{
                setResult(Activity.RESULT_OK,intent);
                finish()
            }
        }
    }
}
