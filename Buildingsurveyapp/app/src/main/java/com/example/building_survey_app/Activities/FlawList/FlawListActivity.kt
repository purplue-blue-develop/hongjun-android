package com.example.building_survey_app.Activities.FlawList

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ListView
import com.example.building_survey_app.ListViewFlawItem
import com.example.building_survey_app.ListViewFlawItemAdapter
import com.example.building_survey_app.Models.FlawModel
import com.example.building_survey_app.R
import com.example.building_survey_app.ViewModels.BuildingProjectListViewModel

class FlawListActivity : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_flaw_list)

        var listView = findViewById<View>(R.id.TotalFlawList) as ListView;
        listView.adapter = ListViewFlawItemAdapter();

        for (flawItem in BuildingProjectListViewModel.BuildingProjectList.get(0).flawList)
        {
            var newItem = ListViewFlawItem();
            newItem.ID = flawItem.id.toString();
            newItem.Name = flawItem.Name;
            newItem.FlawCategory = flawItem.FlawCategory.toDouble();
            newItem.FlawCount = flawItem.FlawCount.toInt();
            newItem.FlawLength = flawItem.FlawLength.toDouble();
//            (listView.adapter as ListViewFlawItemAdapter).Items.
        }
    }

    override fun onClick(v: View?) {
        when(v?.id)
        {

        }
    }
}
