package com.example.building_survey_app.Activities.FlawCheck

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import com.example.building_survey_app.Activities.Popups.PopupAddingFloorList
import com.example.building_survey_app.R

class FlawCheckActivity : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_flaw_check);
        findViewById<Button>(R.id.buttonOpenAddFloorPopup).setOnClickListener(this);
        findViewById<Spinner>(R.id.spinnerFlawCategory).onItemSelectedListener =  object: AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                when(parent?.getItemAtPosition(position))
                {
                    "균열"->{
                        findViewById<Spinner>(R.id.spinnerFlawPos).adapter =
                            ArrayAdapter.createFromResource(applicationContext, R.array.flawPosItemArrayA, R.layout.support_simple_spinner_dropdown_item );
                        findViewById<Spinner>(R.id.spinnerFlaw).adapter =
                            ArrayAdapter.createFromResource(applicationContext, R.array.flawArrayA, R.layout.support_simple_spinner_dropdown_item );
                    }
                    "열화"->{
                        findViewById<Spinner>(R.id.spinnerFlawPos).adapter =
                            ArrayAdapter.createFromResource(applicationContext, R.array.flawPosItemArrayB, R.layout.support_simple_spinner_dropdown_item );
                        findViewById<Spinner>(R.id.spinnerFlaw).adapter =
                            ArrayAdapter.createFromResource(applicationContext, R.array.flawArrayB, R.layout.support_simple_spinner_dropdown_item );
                    }
                }
            }
        }
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

//     fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
//        when(parent?.getItemAtPosition(position))
//        {
//            "균열"->{
//                findViewById<Spinner>(R.id.spinnerFlawPos).adapter =
//                ArrayAdapter.createFromResource(this, R.array.flawPosItemArrayA, R.layout.activity_flaw_check );
//            }
//            "열화"->{
//                findViewById<Spinner>(R.id.spinnerFlawPos).adapter =
//                    ArrayAdapter.createFromResource(this, R.array.flawPosItemArrayB, R.layout.activity_flaw_check );
//            }
//        }
//    }
}

