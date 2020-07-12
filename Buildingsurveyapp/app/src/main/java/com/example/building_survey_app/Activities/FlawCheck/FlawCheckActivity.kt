package com.example.building_survey_app.Activities.FlawCheck

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.graphics.ImageDecoder.createSource
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.text.Layout
import android.util.AttributeSet
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.*
import androidx.core.content.FileProvider
import com.example.building_survey_app.Activities.FlawList.FlawListActivity
import com.example.building_survey_app.Activities.Popups.FullScreenActivity
import com.example.building_survey_app.Activities.Popups.PopupAddingFloorList
import com.example.building_survey_app.Activities.Popups.PopupFlawSpinnerCustomUserInput
import com.example.building_survey_app.Models.BuildingProject
import com.example.building_survey_app.Models.FlawModel
import com.example.building_survey_app.R
import com.example.building_survey_app.ViewModels.BuildingProjectListViewModel
import kotlinx.android.synthetic.main.activity_flaw_check.*
import kotlinx.android.synthetic.main.activity_new_project.view.*
import org.w3c.dom.Text
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.lang.Exception
import kotlin.math.max
import kotlin.math.round

const val CAMERA_REQUET = 1
const val CAMERA_CHKREQUEST = 2
const val POPUP_FLOORADD = 3

class FlawCheckActivity : AppCompatActivity(), View.OnClickListener {
    var imageFloor : Bitmap? = null;
    var imageCompFloor : Bitmap? = null;
    var isEditMode : Boolean = false;
    var EditModeID : Int = 0
    var spinnerCounter : Int = 1;
    var capturePicSaveUri : Uri? = null
    var compCapturePicSaveUri : Uri? =null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_flaw_check);

        findViewById<Button>(R.id.buttonOpenAddFloorPopup).setOnClickListener(this);
        findViewById<Button>(R.id.buttonSaveFlawModel).setOnClickListener(this);

        findViewById<Button>(R.id.buttonTakeAPhoto).setOnClickListener(this);
        findViewById<Button>(R.id.buttonTakeACheckPhoto).setOnClickListener(this);
        findViewById<ImageView>(R.id.pictureimageView).setOnClickListener(this)
        findViewById<ImageButton>(R.id.flawWidthUpButton).setOnClickListener(this)
        findViewById<ImageButton>(R.id.flawWidthDownButton).setOnClickListener(this)
        findViewById<ImageButton>(R.id.flawLengthUpButton).setOnClickListener(this)
        findViewById<ImageButton>(R.id.flawLengthDownButton).setOnClickListener(this)
        findViewById<ImageButton>(R.id.flawCountUpButton).setOnClickListener(this)
        findViewById<ImageButton>(R.id.flawCountDownButton).setOnClickListener(this)

        BuildingProjectListViewModel.BuildingProjectList.add(BuildingProject());

        // <editor-fold desc="스피너변환">
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
                                    android.R.layout.simple_spinner_item
                                );
                            findViewById<Spinner>(R.id.spinnerFlaw).adapter =
                                ArrayAdapter.createFromResource(
                                    applicationContext,
                                    R.array.flawArrayA,
                                    android.R.layout.simple_spinner_item
                                );
                        }
                        "열화" -> {
                            findViewById<Spinner>(R.id.spinnerFlawPos).adapter =
                                ArrayAdapter.createFromResource(
                                    applicationContext,
                                    R.array.flawPosItemArrayB,
                                    android.R.layout.simple_spinner_item
                                );
                            findViewById<Spinner>(R.id.spinnerFlaw).adapter =
                                ArrayAdapter.createFromResource(
                                    applicationContext,
                                    R.array.flawArrayB,
                                    android.R.layout.simple_spinner_item
                                );
                        }
                        "직접입력"->{

//                            var popup = layoutInflater.inflate(R.layout.activity_popup_flaw_spinner_custom_user_input, null)
//
//                            if ( popup == null )
//                                return;
//                            var popupWindow = PopupWindow(popup, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, true)
//                            popupWindow.showAtLocation(view, Gravity.CENTER, 0 , 0)
//
//                            var rl = findViewById<ScrollView>(R.id.activityFlawCheck)
//                            rl.alpha = 0.4f
                        }
                    }
                }
            }
        }
        findViewById<Spinner>(R.id.spinnerFlawPos).setSelection(Adapter.NO_SELECTION, false)
        findViewById<Spinner>(R.id.spinnerFlawPos).onItemSelectedListener =  object: AdapterView.OnItemSelectedListener{

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                when (parent?.getItemAtPosition(position)) {
                    "직접입력"->{
//                        var popup = layoutInflater.inflate(R.layout.activity_popup_flaw_spinner_custom_user_input, null)
//
//                        if ( popup == null )
//                            return;
//                        var popupWindow = PopupWindow(popup, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, true)
//                        popupWindow.showAtLocation(view, Gravity.CENTER, 0 , 0)
//
//                        var rl = findViewById<ScrollView>(R.id.activityFlawCheck)
//                        rl.alpha = 0.4f
                    }
                }
            }
        }
        findViewById<Spinner>(R.id.spinnerFlaw).setSelection(Adapter.NO_SELECTION, false)
        findViewById<Spinner>(R.id.spinnerFlaw).onItemSelectedListener =  object: AdapterView.OnItemSelectedListener{

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                when (parent?.getItemAtPosition(position)) {
                    "직접입력"->{
//                        var popup = layoutInflater.inflate(R.layout.activity_popup_flaw_spinner_custom_user_input, null)
//
//                        if ( popup == null )
//                            return;
//                        var popupWindow = PopupWindow(popup, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, true)
//                        popupWindow.showAtLocation(view, Gravity.CENTER, 0 , 0)
//
//                        var rl = findViewById<ScrollView>(R.id.activityFlawCheck)
//                        rl.alpha = 0.4f
                    }
                }
            }
        }
        // </editor-fold>

        var floorList = BuildingProjectListViewModel.BuildingProjectList[0].floorList;
        var floorListArray = mutableListOf<String>();
        for( floor in floorList)
        {
            if (!floor.Name.isNullOrEmpty())
                floorListArray.add(floor.Name);
        }

        val adapter = ArrayAdapter(applicationContext, android.R.layout.simple_spinner_item, floorListArray);

        val floorView = findViewById<Spinner>(R.id.spinnerFloor)
        floorView.adapter = adapter

        if (savedInstanceState == null) {
            var navigateData = intent.extras;
            // 수정 으로 들어온 경우
            if (navigateData != null) {
//                var id = navigateData.getInt("ID");
                var idobj = navigateData.get("ID")
                if (idobj != null) {
                    var id = idobj.toString().toInt()
                    var flaw =
                        BuildingProjectListViewModel.BuildingProjectList[0].flawList.find { flawModel ->
                            flawModel.id == id
                        }
                    if (flaw == null) {

                    } else {
                        loadExistData(flaw)

                        if ( flaw.FlawCategory == "열화")
                            spinnerCounter = 0
                        isEditMode = true;
                        EditModeID = id
                        findViewById<Button>(R.id.buttonSaveFlawModel).text = "수정";
                    };
                } else if (navigateData?.get("ProjectName") != null) {
                    var projectName = navigateData.getString("ProjectName");
                    if (projectName != null) {
                        if (BuildingProjectListViewModel.BuildingProjectList != null
                            || BuildingProjectListViewModel.BuildingProjectList.size > 0
                        ) {
                            BuildingProjectListViewModel.BuildingProjectList.clear()
                        }


                        var newBP = BuildingProject();
                        newBP.projectName = projectName;
                        BuildingProjectListViewModel.BuildingProjectList?.add(newBP);

//                        findViewById<TextView>(R.id.textViewProjectName_FlawCheck).setText( BuildingProjectListViewModel.BuildingProjectList[0].projectName )
//                        findViewById<TextView>(R.id.textViewDisplayNo).text =
//                            (BuildingProjectListViewModel.BuildingProjectList[0].flawList.size + 1).toString();
                    }
                } else {
//                    findViewById<TextView>(R.id.textViewProjectName_FlawCheck).setText( BuildingProjectListViewModel.BuildingProjectList[0].projectName )
//                    findViewById<TextView>(R.id.textViewDisplayNo).text =
//                        (BuildingProjectListViewModel.BuildingProjectList[0].flawList.size + 1).toString();
                }
            }
            else
            {
//                findViewById<TextView>(R.id.textViewProjectName_FlawCheck).setText( BuildingProjectListViewModel.BuildingProjectList[0].projectName )
//                findViewById<TextView>(R.id.textViewDisplayNo).text =
//                    (BuildingProjectListViewModel.BuildingProjectList[0].flawList.size + 1).toString();
            }
        }

        // 층 스피너 바뀔때마다 floor ID  바뀌는 로직
        findViewById<Spinner>(R.id.spinnerFloor).onItemSelectedListener =
            object: AdapterView.OnItemSelectedListener{

                override fun onNothingSelected(parent: AdapterView<*>?) {
                }

                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    if (isEditMode) {
                        var flawByIdBasedFloor = BuildingProjectListViewModel.BuildingProjectList[0].flawList.find {
                            f->f.id == EditModeID
                        }
                        var floorName = parent?.selectedItem?.toString()?:""

                        if (floorName == flawByIdBasedFloor!!.Floor)
                            findViewById<TextView>(R.id.textViewDisplayNo).setText(flawByIdBasedFloor!!.idBasedFloor.toString())
                        else{
                            var floorName = parent?.selectedItem?.toString()?:""
                            if (floorName.isNullOrEmpty()) {
                                findViewById<TextView>(R.id.textViewDisplayNo).setText("1")
                                return
                            }

                            var sameFloorFlawsize =BuildingProjectListViewModel.BuildingProjectList[0].flawList.count {
                                    f->f.Floor == floorName
                            }
                            findViewById<TextView>(R.id.textViewDisplayNo).setText((sameFloorFlawsize + 1).toString())
                        }
                    }
                    else {
                       var floorName = parent?.selectedItem?.toString()?:""
                        if (floorName.isNullOrEmpty()) {
                            findViewById<TextView>(R.id.textViewDisplayNo).setText("1")
                            return
                        }

                        var sameFloorFlawsize =BuildingProjectListViewModel.BuildingProjectList[0].flawList.count {
                                f->f.Floor == floorName
                        }
                        findViewById<TextView>(R.id.textViewDisplayNo).setText((sameFloorFlawsize + 1).toString())
                    }
                }
            }
        supportActionBar?.setTitle( "현장조사 RPA (" + BuildingProjectListViewModel.BuildingProjectList[0].projectName + ")")
    }

    override fun onCreateView(name: String, context: Context, attrs: AttributeSet): View? {
        return super.onCreateView(name, context, attrs)
    }

    fun loadExistData(flaw : FlawModel)
    {
        findViewById<TextView>(R.id.textViewDisplayNo).text = flaw.idBasedFloor.toString();

        var adapter = ArrayAdapter.createFromResource(applicationContext, R.array.flawCategoryItemArray, android.R.layout.simple_spinner_item );
        findViewById<Spinner>(R.id.spinnerFlawCategory).setSelection(adapter.getPosition(flaw.FlawCategory), false);
        if ( flaw?.FlawCategory == "균열")
        {
            adapter = ArrayAdapter.createFromResource(applicationContext, R.array.flawPosItemArrayA, android.R.layout.simple_spinner_item);
            findViewById<Spinner>(R.id.spinnerFlawPos).adapter = adapter;
            findViewById<Spinner>(R.id.spinnerFlawPos).setSelection(adapter.getPosition(flaw.FlawPos));
            adapter = ArrayAdapter.createFromResource(applicationContext, R.array.flawArrayA, android.R.layout.simple_spinner_item );
            findViewById<Spinner>(R.id.spinnerFlaw).adapter = adapter;
            findViewById<Spinner>(R.id.spinnerFlaw).setSelection(adapter.getPosition(flaw.Flaw));
        }
        else
        {
            adapter = ArrayAdapter.createFromResource(applicationContext, R.array.flawPosItemArrayB, android.R.layout.simple_spinner_item);
            findViewById<Spinner>(R.id.spinnerFlawPos).adapter = adapter;
            findViewById<Spinner>(R.id.spinnerFlawPos).setSelection(adapter.getPosition(flaw.FlawPos));
            adapter = ArrayAdapter.createFromResource(applicationContext, R.array.flawArrayB, android.R.layout.simple_spinner_item );
            findViewById<Spinner>(R.id.spinnerFlaw).adapter = adapter;
            findViewById<Spinner>(R.id.spinnerFlaw).setSelection(adapter.getPosition(flaw.Flaw));
        }
        var floorList = BuildingProjectListViewModel.BuildingProjectList[0].floorList;
        var floorListArray = mutableListOf<String>();
        for( floor in floorList)
        {
            if ( floor.Name.isNullOrEmpty())
                continue
            floorListArray.add(floor.Name);
        }

        val flooradapter = ArrayAdapter(applicationContext, android.R.layout.simple_spinner_item, floorListArray);

        val floorView = findViewById<Spinner>(R.id.spinnerFloor)
        floorView.adapter = flooradapter
        floorView.setSelection(flooradapter.getPosition(flaw.Floor))
        findViewById<EditText>(R.id.editTextFlawName).setText( flaw.Name, TextView.BufferType.EDITABLE )
        findViewById<TextView>(R.id.textViewProjectName_FlawCheck).setText( BuildingProjectListViewModel.BuildingProjectList[0].projectName )
        findViewById<EditText>(R.id.editTextFlawWidth).setText( flaw.FlawWidth.toString(), TextView.BufferType.EDITABLE);
        findViewById<EditText>(R.id.editTextFlawCount).setText( flaw.FlawCount.toString(), TextView.BufferType.EDITABLE);
        findViewById<EditText>(R.id.editTextFlawLength).setText( flaw.FlawLength.toString(), TextView.BufferType.EDITABLE);

        if (!flaw.capturedPicName.isNullOrEmpty()) {
            findViewById<ImageView>(R.id.pictureimageView).setImageBitmap(flaw.capturedPic);
            imageFloor = flaw.capturedPic;
        }
        if (!flaw.capturedPicName.isNullOrEmpty()) {
            findViewById<ImageView>(R.id.picturecompimageView).setImageBitmap(flaw.compareCapturedPic);
            imageCompFloor = flaw.compareCapturedPic;
        }
        findViewById<TextView>(R.id.projectInfoNameTextView).setText( BuildingProjectListViewModel.BuildingProjectList[0].projectName );
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

                var project = BuildingProjectListViewModel.BuildingProjectList?.get(0);
                var newFlaw = FlawModel();

                if (isEditMode)
                    newFlaw.id = EditModeID
                else
                    newFlaw.id = BuildingProjectListViewModel.BuildingProjectList[0].flawList.size + 1

                newFlaw.idBasedFloor = findViewById<TextView>(R.id.textViewDisplayNo).text.toString().toInt();
                newFlaw.Name = findViewById<EditText>( R.id.editTextFlawName ).text.toString();
                newFlaw.FlawCategory = findViewById<Spinner>(R.id.spinnerFlawCategory).selectedItem.toString();
                newFlaw.FlawPos = findViewById<Spinner>(R.id.spinnerFlawPos).selectedItem.toString();
                newFlaw.Flaw = findViewById<Spinner>(R.id.spinnerFlaw).selectedItem.toString();
                if ( findViewById<Spinner>(R.id.spinnerFloor).selectedItemPosition == -1) {
                    Toast.makeText(this, "층을 선택해주세요.", Toast.LENGTH_SHORT).show()
                    return;
                }
                else
                    newFlaw.Floor = findViewById<Spinner>(R.id.spinnerFloor).selectedItem.toString();

                newFlaw.FlawWidth = findViewById<EditText>(R.id.editTextFlawWidth).text.toString().toDoubleOrNull()?:0.0
                newFlaw.FlawLength = findViewById<EditText>(R.id.editTextFlawLength).text.toString().toIntOrNull()?:0
                newFlaw.FlawCount = findViewById<EditText>(R.id.editTextFlawCount).text.toString().toIntOrNull()?:0

                newFlaw.capturedPic = imageFloor;
                newFlaw.compareCapturedPic = imageCompFloor;
                newFlaw.capturedPicName = newFlaw.Floor + "_" + newFlaw.idBasedFloor  +".png"
                newFlaw.compareCapturedPicName = newFlaw.Floor + "_" + newFlaw.idBasedFloor + "_비교.png"

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

                /* 로컬 폴더에 사진을 저장한다 */
                if ( capturePicSaveUri != null)
                {
                    var file = File(capturePicSaveUri!!.path)

                    var destFile = File(getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS),
                            BuildingProjectListViewModel.BuildingProjectList[0].projectName + "/" +
                                    "Pictures/" +
                                    newFlaw.Floor + "_"
                                    +newFlaw.idBasedFloor + ".png")

                    if ( file.name != destFile.name) {
                        if (!destFile.exists())
                            destFile.delete()
                        file.renameTo(destFile)
                    }
                }

                if (compCapturePicSaveUri != null)
                {
                    var file = File(compCapturePicSaveUri!!.path)

                    var destFile = File(getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS),
                        BuildingProjectListViewModel.BuildingProjectList[0].projectName + "/" +
                                "Pictures/" +
                                newFlaw.Floor + "_"
                                +newFlaw.idBasedFloor + "_비교.png")

                    if (file.absolutePath != destFile.absolutePath) {
                        if (!destFile.exists())
                            destFile.delete()
                        file.renameTo(destFile)
                    }
                }


                var intent = Intent(this, FlawListActivity::class.java);
                intent.putExtra("ID", newFlaw.id)
                startActivity(intent);
            }
            R.id.buttonTakeAPhoto ->
            {

                var selectedSpinner = (findViewById<Spinner>(R.id.spinnerFloor).selectedItem?:"").toString()

                var file = File(getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS),
                    BuildingProjectListViewModel.BuildingProjectList[0].projectName + "/" +
                            "Pictures/" +
                            selectedSpinner + "_"
                            +findViewById<TextView>(R.id.textViewDisplayNo).text.toString() + ".png"
                )
                try {
                    capturePicSaveUri  = FileProvider.getUriForFile(this, "com.example.building_survey_app.fileProvider" ,file)

                    val takepictureintent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                    takepictureintent.putExtra(MediaStore.EXTRA_OUTPUT, capturePicSaveUri)
                    if (takepictureintent.resolveActivity(this.packageManager) != null) {
                        startActivityForResult(takepictureintent, CAMERA_REQUET)
                    } else {
                        Toast.makeText(this, "Unable to load Camera", Toast.LENGTH_SHORT).show()
                    }
                }
                catch ( e: Exception)
                {
                    Log.e("ERROR", e.stackTrace.toString())
                }

            }
            R.id.buttonTakeACheckPhoto ->
            {
                var selectedSpinner = (findViewById<Spinner>(R.id.spinnerFloor).selectedItem?:"").toString()
                var file = File(getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS),
                    BuildingProjectListViewModel.BuildingProjectList[0].projectName + "/" +
                            "Pictures/" +
                            selectedSpinner + "_"
                            +findViewById<TextView>(R.id.textViewDisplayNo).text.toString() + "_비교.png"
                )

                compCapturePicSaveUri = FileProvider.getUriForFile(this, "com.example.building_survey_app.fileProvider" ,file)
                val chkPhotoIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                chkPhotoIntent.putExtra(MediaStore.EXTRA_OUTPUT, compCapturePicSaveUri)
                if(chkPhotoIntent.resolveActivity(this.packageManager) != null){
                    startActivityForResult(chkPhotoIntent, CAMERA_CHKREQUEST)
                } else{
                    Toast.makeText(this, "Unable to load Camera", Toast.LENGTH_SHORT).show()
                }
            }
            R.id.flawWidthUpButton->
            {
                var width = findViewById<EditText>(R.id.editTextFlawWidth).text.toString().toDoubleOrNull()?:0.0
                width += 0.1
                width = round(width*10)/10
                findViewById<EditText>(R.id.editTextFlawWidth).setText(width.toString())
            }
            R.id.flawWidthDownButton->
            {
                var width = findViewById<EditText>(R.id.editTextFlawWidth).text.toString().toDoubleOrNull()?:0.0
                width -=0.1
                width = round(width*10)/10
                width = max(width, 0.0)
                findViewById<EditText>(R.id.editTextFlawWidth).setText(width.toString())
            }
            R.id.flawLengthUpButton->
            {
                var length = findViewById<EditText>(R.id.editTextFlawLength).text.toString().toIntOrNull()?:0
                length += 100
                findViewById<EditText>(R.id.editTextFlawLength).setText(length.toString())
            }
            R.id.flawLengthDownButton->
            {
                var length = findViewById<EditText>(R.id.editTextFlawLength).text.toString().toIntOrNull()?:0
                length -= 100
                length = max(length, 0)
                findViewById<EditText>(R.id.editTextFlawLength).setText(length.toString())
            }
            R.id.flawCountUpButton->
            {
                var count = findViewById<EditText>(R.id.editTextFlawCount).text.toString().toIntOrNull()?:0
                count += 1
                findViewById<EditText>(R.id.editTextFlawCount).setText(count.toString())
            }
            R.id.flawCountDownButton->
            {
                var count = findViewById<EditText>(R.id.editTextFlawCount).text.toString().toIntOrNull()?:0
                count -= 1
                count = max(0, count)
                findViewById<EditText>(R.id.editTextFlawCount).setText(count.toString())

            }
            R.id.pictureimageView->
            {
                val fullScreenIntent = Intent(this,  FullScreenActivity::class.java);
                fullScreenIntent.putExtra("URI", capturePicSaveUri)
                startActivity(fullScreenIntent);
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when(requestCode){
            CAMERA_REQUET ->
            {
                if(resultCode == Activity.RESULT_OK){
                    try {
                        imageFloor =
                            ImageDecoder.decodeBitmap(createSource(this.contentResolver, capturePicSaveUri!!))
                        pictureimageView.setImageBitmap(imageFloor)
                    }
                    catch (e : Exception)
                    {
                        Log.e("EWF", e.stackTrace.toString())
                    }
                } else{
                    super.onActivityResult(requestCode, resultCode, data)
                }

            }
            CAMERA_CHKREQUEST ->
            {
                if(resultCode == Activity.RESULT_OK){
                    imageCompFloor =
                        ImageDecoder.decodeBitmap(createSource(this.contentResolver, compCapturePicSaveUri!!))
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

                    val adapter = ArrayAdapter(applicationContext, android.R.layout.simple_spinner_item, floorListArray);

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

