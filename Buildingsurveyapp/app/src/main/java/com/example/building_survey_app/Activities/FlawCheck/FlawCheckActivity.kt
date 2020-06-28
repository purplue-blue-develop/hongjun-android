package com.example.building_survey_app.Activities.FlawCheck

import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.AttributeSet
import android.view.View
import android.widget.*
import android.widget.AdapterView.OnItemSelectedListener
import androidx.core.view.drawToBitmap
import com.example.building_survey_app.Activities.FlawList.FlawListActivity
import com.example.building_survey_app.Activities.Popups.PopupAddingFloorList
import com.example.building_survey_app.Models.BuildingProject
import com.example.building_survey_app.Models.FlawModel
import com.example.building_survey_app.Models.Floor
import com.example.building_survey_app.R
import com.example.building_survey_app.ViewModels.BuildingProjectListViewModel
import kotlinx.android.synthetic.main.activity_flaw_check.*


import java.io.FileOutputStream
import java.io.ObjectOutputStream
import java.lang.Exception
import java.util.jar.Attributes

const val CAMERA_REQUET = 1
const val CAMERA_CHKREQUEST = 2
const val POPUP_FLOORADD = 3

class FlawCheckActivity : AppCompatActivity(), View.OnClickListener {
    var imageFloor : Bitmap? = null;
    var imageCompFloor : Bitmap? = null;
    var isEditMode : Boolean = false;
    var spinnerCounter : Int = 1;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_flaw_check);
        findViewById<Button>(R.id.buttonOpenAddFloorPopup).setOnClickListener(this);
        findViewById<Button>(R.id.buttonSaveFlawModel).setOnClickListener(this);
        findViewById<Button>(R.id.buttonTakeAPhoto).setOnClickListener(this);
        findViewById<Button>(R.id.buttonTakeACheckPhoto).setOnClickListener(this);
        BuildingProjectListViewModel.BuildingProjectList.add(BuildingProject());
        findViewById<Spinner>(R.id.spinnerFlawCategory).setSelection(Adapter.NO_SELECTION, false)
        findViewById<Spinner>(R.id.spinnerFlawCategory).onItemSelectedListener =  object: AdapterView.OnItemSelectedListener{

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if (++spinnerCounter > 1) {
                    when (parent?.getItemAtPosition(position)) {
                        "균열" -> {
                            findViewById<Spinner>(R.id.spinnerFlawPos).adapter =
                                ArrayAdapter.createFromResource(
                                    applicationContext,
                                    R.array.flawPosItemArrayA,
                                    R.layout.support_simple_spinner_dropdown_item
                                );
                            findViewById<Spinner>(R.id.spinnerFlaw).adapter =
                                ArrayAdapter.createFromResource(
                                    applicationContext,
                                    R.array.flawArrayA,
                                    R.layout.support_simple_spinner_dropdown_item
                                );
                        }
                        "열화" -> {
                            findViewById<Spinner>(R.id.spinnerFlawPos).adapter =
                                ArrayAdapter.createFromResource(
                                    applicationContext,
                                    R.array.flawPosItemArrayB,
                                    R.layout.support_simple_spinner_dropdown_item
                                );
                            findViewById<Spinner>(R.id.spinnerFlaw).adapter =
                                ArrayAdapter.createFromResource(
                                    applicationContext,
                                    R.array.flawArrayB,
                                    R.layout.support_simple_spinner_dropdown_item
                                );
                        }
                    }
                }
            }
        }

        var floorList = BuildingProjectListViewModel.BuildingProjectList[0].floorList;
        var floorListArray = mutableListOf<String>();
        for( floor in floorList)
        {
            floorListArray.add(floor.Name);
        }

        val adapter = ArrayAdapter(applicationContext, R.layout.support_simple_spinner_dropdown_item, floorListArray);

        val floorView = findViewById<Spinner>(R.id.spinnerFloor)
        floorView.adapter = adapter

        if (savedInstanceState == null) {
            var navigateData = intent.extras;
            // 수정 으로 들어온 경우
            if (navigateData != null){
                var id = navigateData.getInt("ID");
                var flaw = BuildingProjectListViewModel.BuildingProjectList[0].flawList.find{
                        flawModel->flawModel.id==id
                }
                if (flaw == null)
                {

                }
                else {
                    loadExistData(flaw)
                    spinnerCounter = 0
                    isEditMode = true;
                    findViewById<Button>(R.id.buttonSaveFlawModel).text = "수정";
                };
            }
            else {
                BuildingProjectListViewModel.BuildingProjectList?.add(BuildingProject());
                findViewById<TextView>(R.id.textViewDisplayNo).text =
                    (BuildingProjectListViewModel.BuildingProjectList[0].flawList.size + 1).toString();
            }
        }
    }

    override fun onCreateView(name: String, context: Context, attrs: AttributeSet): View? {
        return super.onCreateView(name, context, attrs)
    }

    fun loadExistData(flaw : FlawModel)
    {
        findViewById<TextView>(R.id.textViewDisplayNo).text = flaw.id.toString();

        var adapter = ArrayAdapter.createFromResource(applicationContext, R.array.flawCategoryItemArray, R.layout.support_simple_spinner_dropdown_item );
        findViewById<Spinner>(R.id.spinnerFlawCategory).setSelection(adapter.getPosition(flaw.FlawCategory), false);
        if ( flaw?.FlawCategory == "균열")
        {
            adapter = ArrayAdapter.createFromResource(applicationContext, R.array.flawPosItemArrayA, R.layout.support_simple_spinner_dropdown_item);
            findViewById<Spinner>(R.id.spinnerFlawPos).adapter = adapter;
            findViewById<Spinner>(R.id.spinnerFlawPos).setSelection(adapter.getPosition(flaw.FlawPos));
            adapter = ArrayAdapter.createFromResource(applicationContext, R.array.flawArrayA, R.layout.support_simple_spinner_dropdown_item );
            findViewById<Spinner>(R.id.spinnerFlaw).adapter = adapter;
            findViewById<Spinner>(R.id.spinnerFlaw).setSelection(adapter.getPosition(flaw.Flaw));
        }
        else
        {
            adapter = ArrayAdapter.createFromResource(applicationContext, R.array.flawPosItemArrayB, R.layout.support_simple_spinner_dropdown_item);
            findViewById<Spinner>(R.id.spinnerFlawPos).adapter = adapter;
            findViewById<Spinner>(R.id.spinnerFlawPos).setSelection(adapter.getPosition(flaw.FlawPos));
            adapter = ArrayAdapter.createFromResource(applicationContext, R.array.flawArrayB, R.layout.support_simple_spinner_dropdown_item );
            findViewById<Spinner>(R.id.spinnerFlaw).adapter = adapter;
            findViewById<Spinner>(R.id.spinnerFlaw).setSelection(adapter.getPosition(flaw.Flaw));
        }
        var floorList = BuildingProjectListViewModel.BuildingProjectList[0].floorList;
        var floorListArray = mutableListOf<String>();
        for( floor in floorList)
        {
            floorListArray.add(floor.Name);
        }

        val flooradapter = ArrayAdapter(applicationContext, R.layout.support_simple_spinner_dropdown_item, floorListArray);

        val floorView = findViewById<Spinner>(R.id.spinnerFloor)
        floorView.adapter = flooradapter
        floorView.setSelection(flooradapter.getPosition(flaw.Floor))
        findViewById<EditText>(R.id.editTextName).setText( flaw.Name.toString(), TextView.BufferType.EDITABLE);
        findViewById<EditText>(R.id.editTextFlawWidth).setText( flaw.FlawWidth.toString(), TextView.BufferType.EDITABLE);
        findViewById<EditText>(R.id.editTextFlawCount).setText( flaw.FlawCount.toString(), TextView.BufferType.EDITABLE);
        findViewById<EditText>(R.id.editTextFlawLength).setText( flaw.FlawLength.toString(), TextView.BufferType.EDITABLE);

        findViewById<ImageView>(R.id.pictureimageView). setImageBitmap(flaw.capturedPic);
        findViewById<ImageView>(R.id.picturecompimageView). setImageBitmap(flaw.compareCapturedPic);
        imageFloor = flaw.capturedPic;
        imageCompFloor = flaw.compareCapturedPic;
    }

    override fun onClick(v: View?) {

        when(v?.id)
        {
            R.id.buttonOpenAddFloorPopup->
            {
                var intent = Intent(this, PopupAddingFloorList::class.java)
                startActivityForResult(intent, POPUP_FLOORADD);
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
                newFlaw.Floor = findViewById<Spinner>(R.id.spinnerFloor).selectedItem.toString();

                if( findViewById<EditText>(R.id.editTextFlawWidth).text.toString().isNullOrEmpty())
                    newFlaw.FlawWidth = 0.0;
                 else newFlaw.FlawWidth = findViewById<EditText>(R.id.editTextFlawWidth).text.toString().toDouble()

                if ( findViewById<EditText>(R.id.editTextFlawCount).text.toString().isNullOrEmpty())
                    newFlaw.FlawCount = 0;
                else newFlaw.FlawCount = findViewById<EditText>(R.id.editTextFlawCount).text.toString().toInt();

                if ( findViewById<EditText>(R.id.editTextFlawLength).text.toString().isNullOrEmpty())
                    newFlaw.FlawLength =0.0;
                else newFlaw.FlawLength = findViewById<EditText>(R.id.editTextFlawLength).text.toString().toDouble();

                newFlaw.capturedPic = imageFloor;
                newFlaw.compareCapturedPic = imageCompFloor;

                if ( isEditMode  == false) {
                    project.flawList.add(newFlaw);
                }
                else
                {
                    var flaw = project.flawList.find{
                        f->f.id == newFlaw.id
                    };
                    var index = project.flawList.indexOf(flaw!!);
                    project.flawList[index] = newFlaw;
                }
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
                    imageFloor = data?.extras?.get("data") as Bitmap
                    pictureimageView.setImageBitmap(imageFloor)
                } else{
                    super.onActivityResult(requestCode, resultCode, data)
                }

            }
            CAMERA_CHKREQUEST ->
            {
                if(resultCode == Activity.RESULT_OK){
                    imageCompFloor = data?.extras?.get("data") as Bitmap
                    picturecompimageView.setImageBitmap(imageCompFloor)
                } else{
                    super.onActivityResult(requestCode, resultCode, data)
                }
            }

            POPUP_FLOORADD ->
            {
                if(resultCode == Activity.RESULT_OK){
                    var floorList = BuildingProjectListViewModel.BuildingProjectList[0].floorList;
                    var floorListArray = mutableListOf<String>();
                    for( floor in floorList)
                    {
                        floorListArray.add(floor.Name);
                    }

                    val adapter = ArrayAdapter(applicationContext, R.layout.support_simple_spinner_dropdown_item, floorListArray);

                    val floorView = findViewById<Spinner>(R.id.spinnerFloor)
                    floorView.adapter = adapter
                }
            }
        }
    }

    fun IsEditMode(savedInstanceState: Bundle?) : Boolean
    {
        var result = false;
        if ( savedInstanceState == null) {
            var navigateData = intent.extras;
            // 수정 으로 들어온 경우
            if (navigateData != null){
                var id = navigateData.getInt("ID");
                var flaw = BuildingProjectListViewModel.BuildingProjectList[0].flawList.find{
                        flawModel->flawModel.id==id
                }
                if (flaw != null)
                    result = true;
            }
        }
        return true;
    }
}

