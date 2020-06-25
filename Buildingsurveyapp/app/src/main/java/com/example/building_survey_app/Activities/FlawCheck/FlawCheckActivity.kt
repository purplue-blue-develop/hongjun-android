package com.example.building_survey_app.Activities.FlawCheck

import android.app.Activity
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.*
import android.widget.AdapterView.OnItemSelectedListener
import com.example.building_survey_app.Activities.Popups.PopupAddingFloorList
import com.example.building_survey_app.R
import com.example.building_survey_app.ViewModels.BuildingProjectListViewModel

const val CAMERA_REQUET = 1
const val CAMERA_CHKREQUEST = 2

class FlawCheckActivity : AppCompatActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_flaw_check);
        findViewById<Button>(R.id.buttonOpenAddFloorPopup).setOnClickListener(this);
        findViewById<Button>(R.id.buttonTakeAPhoto).setOnClickListener(this);
        findViewById<Button>(R.id.buttonTakeACheckPhoto).setOnClickListener(this);
        //findViewById<Spinner>(R.id.spinnerFloor).setAutofillHints()
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
            R.id.buttonTakeAPhoto ->
            {
                val takepictureintent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                if(takepictureintent.resolveActivity(this.packageManager) != null){
                    startActivityForResult(takepictureintent, CAMERA_REQUET)
                } else{
                    Toast.makeText(this, "Unable to load Camera", Toast.LENGTH_SHORT).show()
                }
            }

            R.id.buttonTakeACheckPhoto ->
            {
                val chkPhotoIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                if(chkPhotoIntent.resolveActivity(this.packageManager) != null){
                    startActivityForResult(chkPhotoIntent, CAMERA_CHKREQUEST)
                } else{
                    Toast.makeText(this, "Unable to load Camera", Toast.LENGTH_SHORT).show()
                }


            }

        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when(requestCode){
            CAMERA_REQUET ->
            {
                val imageFloor = data?.extras?.get("data") as Bitmap
                super.onActivityResult(requestCode, resultCode, data)
            }
            CAMERA_CHKREQUEST ->
            {
                val imageCompFloor = data?.extras?.get("data") as Bitmap
                super.onActivityResult(requestCode, resultCode, data)
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

