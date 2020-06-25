package com.example.building_survey_app.Activities.FlawCheck

import android.app.Activity
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.*
import android.widget.AdapterView.OnItemSelectedListener
import androidx.core.view.drawToBitmap
import com.example.building_survey_app.Activities.FlawList.FlawListActivity
import com.example.building_survey_app.Activities.Popups.PopupAddingFloorList
import com.example.building_survey_app.Models.BuildingProject
import com.example.building_survey_app.Models.FlawModel
import com.example.building_survey_app.R
import com.example.building_survey_app.ViewModels.BuildingProjectListViewModel
import kotlinx.android.synthetic.main.activity_flaw_check.*


import java.io.FileOutputStream
import java.io.ObjectOutputStream
import java.lang.Exception
import java.util.jar.Attributes

const val CAMERA_REQUET = 1
const val CAMERA_CHKREQUEST = 2

class FlawCheckActivity : AppCompatActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_flaw_check);
        findViewById<Button>(R.id.buttonOpenAddFloorPopup).setOnClickListener(this);
        findViewById<Button>(R.id.buttonSaveFlawModel).setOnClickListener(this);
        findViewById<Button>(R.id.buttonTakeAPhoto).setOnClickListener(this);
        findViewById<Button>(R.id.buttonTakeACheckPhoto).setOnClickListener(this);
        //findViewById<Spinner>(R.id.spinnerFloor).setAutofillHints()
        BuildingProjectListViewModel.BuildingProjectList?.add(BuildingProject());
        findViewById<TextView>(R.id.textViewDisplayNo).text = BuildingProjectListViewModel.BuildingProjectList[0].flawList.size.toString();
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
            R.id.buttonSaveFlawModel-> {
//                try {
//                    var flawModelData =
//                        ObjectOutputStream(FileOutputStream("buildingSurveyApp.data"))
//                    flawModelData.writeObject(
//                        BuildingProjectListViewModel.BuildingProjectList?.get(
//                            0
//                        )
//                    );
//                    flawModelData.close();
//                } catch (e: Exception) {
//                    e.printStackTrace();
//                }
                var project = BuildingProjectListViewModel.BuildingProjectList?.get(0);
                var newFlaw = FlawModel();
                newFlaw.Name = findViewById<EditText>( R.id.editTextName ).text.toString();
                newFlaw.FlawCategory = findViewById<Spinner>(R.id.spinnerFlawCategory).selectedItem.toString();
                newFlaw.FlawPos = findViewById<Spinner>(R.id.spinnerFlawPos).selectedItem.toString();
                newFlaw.Flaw = findViewById<Spinner>(R.id.spinnerFlaw).selectedItem.toString();

                if( findViewById<EditText>(R.id.editTextFlawWidth).text.toString().isNullOrEmpty())
                    newFlaw.FlawWidth = 0.0;
                 else newFlaw.FlawWidth = findViewById<EditText>(R.id.editTextFlawWidth).text.toString().toDouble()

                if ( findViewById<EditText>(R.id.editTextFlawCount).text.toString().isNullOrEmpty())
                    newFlaw.FlawCount = 0;
                else newFlaw.FlawCount = findViewById<EditText>(R.id.editTextFlawCount).text.toString().toInt();

                if ( findViewById<EditText>(R.id.editTextFlawLength).text.toString().isNullOrEmpty())
                    newFlaw.FlawLength =0.0;
                else newFlaw.FlawLength = findViewById<EditText>(R.id.editTextFlawLength).text.toString().toDouble();
//
                newFlaw.capturedPic = (findViewById<ImageView>(R.id.pictureimageView).drawable as BitmapDrawable).bitmap
                newFlaw.compareCapturedPic = (findViewById<ImageView>(R.id.picturecompimageView).drawable as BitmapDrawable).bitmap
                project.flawList.add(newFlaw);
                var intent = Intent(this, FlawListActivity::class.java);
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
                if(resultCode == Activity.RESULT_OK){
                    val imageFloor = data?.extras?.get("data") as Bitmap
                    pictureimageView.setImageBitmap(imageFloor)
                } else{
                    super.onActivityResult(requestCode, resultCode, data)
                }

            }
            CAMERA_CHKREQUEST ->
            {
                if(resultCode == Activity.RESULT_OK){
                    val imageCompFloor = data?.extras?.get("data") as Bitmap
                    picturecompimageView.setImageBitmap(imageCompFloor)
                } else{
                    super.onActivityResult(requestCode, resultCode, data)
                }

            }
        }

    }
}

