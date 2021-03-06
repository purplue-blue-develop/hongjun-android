package com.chaosApp.chaos.Activities.New_Edit_Project

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import com.chaosApp.chaos.Activities.FlawCheck.FlawCheckActivity
import com.chaosApp.chaos.Activities.MainPage.MainActivity
import com.chaosApp.chaos.Models.BuildingProject
import com.chaosApp.chaos.R
import java.io.File


class NewProjectActivity : AppCompatActivity(), View.OnClickListener {

    var buildingProject :BuildingProject? = null
    var projectNameCheckSearchSuceed : Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_project)

//        findViewById<Button>(R.id.OpenCalendarDialog).setOnClickListener(this);
//        findViewById<Button>(R.id.AddBuildingFloor).setOnClickListener(this);

        findViewById<Button>(R.id.goToHomeFromNewProjectActivity).setOnClickListener(this)
        findViewById<Button>(R.id.SearchProjectButton).setOnClickListener(this)
        findViewById<Button>(R.id.gotoFlawCheck).setOnClickListener(this)
        findViewById<EditText>(R.id.editTextProjectName).doAfterTextChanged {
            projectNameCheckSearchSuceed = false

        }
    }

    override fun onClick(v: View?) {
        when (v?.id)
        {
            R.id.goToHomeFromNewProjectActivity->{
                startActivity(Intent(this, MainActivity::class.java))
            }
            R.id.SearchProjectButton->
            {
                var projectName = findViewById<TextView>(R.id.editTextProjectName).text.toString();
                projectNameCheckSearchSuceed = true
                if ( projectName.isNullOrEmpty() ) {
                    Toast.makeText(this, "프로젝트 이름은 공백이 될 수 없습니다.",Toast.LENGTH_SHORT).show()
                    projectNameCheckSearchSuceed = false
                    return;
                }
                var dir = getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS);
                var allFiles = dir?.listFiles();

                if (allFiles == null) {
                    projectNameCheckSearchSuceed = true
                    return
                }

                for (file in allFiles)
                {
                    if (file.isDirectory)
                    {
                        if (file.nameWithoutExtension == projectName) {

                            var insideFileList = file.listFiles();
                            var xlsxfile = insideFileList.find { f ->
                                f.name == "결함정보.xlsx";
                            }

                            if (xlsxfile != null) {
                                projectNameCheckSearchSuceed = false
                            }
                        }
                    }
                }
                if ( projectNameCheckSearchSuceed)
                    Toast.makeText(this, "프로젝트를 생성할 수 있습니다.", Toast.LENGTH_SHORT).show()
                else
                    Toast.makeText(this, "프로젝트가 이미 존재합니다.", Toast.LENGTH_SHORT).show()
            }
            R.id.gotoFlawCheck->
            {
                if (!projectNameCheckSearchSuceed)
                {
                    Toast.makeText(this, "프로젝트명 중복 검사를 수행해주세요", Toast.LENGTH_SHORT).show()
                    return
                }
                var projectName = findViewById<TextView>(R.id.editTextProjectName).text.toString();
//                buildingProject?.buildingName = findViewById<TextView>(R.id.textViewProjectName).text.toString();
//                buildingProject?.investDate;
//                buildingProject?.latestEditedDte = LocalDateTime.now();

                var ff = File(
                    getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS),
                    projectName
                )
                ff.mkdir()

                ff = File(getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS),
                projectName+"/Pictures")
                ff.mkdir()

                var nextIntent = Intent(this, FlawCheckActivity::class.java)
                nextIntent.putExtra("ProjectName",projectName )
                startActivity(nextIntent)
            }
            R.id.OpenCalendarDialog->
            {

            }
            R.id.AddBuildingFloor->
            {
            }
        }
    }
}
