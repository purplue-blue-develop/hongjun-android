package com.example.building_survey_app.Activities.FlawCheck

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.example.building_survey_app.Activities.Popups.PopupAddingFloorList
import com.example.building_survey_app.R

class FlawCheckActivity : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_flaw_check);
        findViewById<Button>(R.id.buttonOpenAddFloorPopup).setOnClickListener(this);
    }

    override fun onClick(v: View?) {

        when(v?.id)
        {
            R.id.buttonOpenAddFloorPopup->
            {
                var intent = Intent(this, PopupAddingFloorList::class.java)
                startActivity(intent);
            }
        }
    }
}
