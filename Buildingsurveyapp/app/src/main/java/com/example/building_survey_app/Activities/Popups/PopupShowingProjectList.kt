package com.example.building_survey_app.Activities.Popups

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.example.building_survey_app.R

class PopupShowingProjectList : Activity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_popup_showing_project_list)
        findViewById<Button>(R.id.ClosePopupButton).setOnClickListener(this);
    }

    override fun onClick(v: View?) {
        finish();

    }
}
