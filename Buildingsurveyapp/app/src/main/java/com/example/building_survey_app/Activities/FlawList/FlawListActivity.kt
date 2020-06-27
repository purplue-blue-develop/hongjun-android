package com.example.building_survey_app.Activities.FlawList

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ListView
import com.example.building_survey_app.ListViewFlawItem
import com.example.building_survey_app.ListViewFlawItemAdapter
import com.example.building_survey_app.R
import com.example.building_survey_app.ViewModels.BuildingProjectListViewModel
import org.apache.poi.hssf.usermodel.HSSFWorkbook
import org.apache.poi.poifs.filesystem.POIFSFileSystem
import org.apache.poi.ss.usermodel.Sheet
import org.apache.poi.ss.usermodel.Workbook
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.lang.Exception

class FlawListActivity : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_flaw_list)

        var listItems : ArrayList<ListViewFlawItem> = arrayListOf();
        for (flawItem in BuildingProjectListViewModel.BuildingProjectList[0].flawList)
        {
            var newItem = ListViewFlawItem();
            newItem.capturedPic = flawItem.capturedPic;
            newItem.ID = flawItem.id;
            newItem.Name = flawItem.Name;
            newItem.FlawCategory = flawItem.FlawCategory;
            newItem.FlawPos = flawItem.FlawPos;
            newItem.Flaw = flawItem.Flaw;
            listItems.add(newItem);
        }

        var listView = findViewById<View>(R.id.TotalFlawList) as ListView;
        listView.adapter = ListViewFlawItemAdapter(this, listItems);

        findViewById<Button>(R.id.FlawListSave).setOnClickListener(this);

    }

    override fun onClick(v: View?) {
        when(v?.id)
        {
            R.id.FlawListSave->{
                SaveExcel();
            }
        }
    }

    fun SaveExcel()
    {
        try {
            var file= File(getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), "결함정보.xls");

            var wb : Workbook?;
            var sheet : Sheet?;
            if (file.exists())
            {
                wb= HSSFWorkbook();
                sheet = wb.createSheet();
            }
            else
            {
                file.createNewFile();
                var fileSystem = POIFSFileSystem(file.inputStream())
                wb = HSSFWorkbook(fileSystem);
                wb.removeSheetAt(0);
                wb.createSheet();
                sheet = HSSFWorkbook(fileSystem).getSheetAt(0);
            }

            val row = sheet.createRow(0);
            var cell = row.createCell(0);
            cell.setCellValue("하이");

            file = File(getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), "결함정보.xls");
            try {
                var os = FileOutputStream(file);
                wb.write(os);
            }
            catch (e : IOException){
                e.printStackTrace();
            }
        }
        catch (e : Exception)
        {
            Log.e("Save Sequence Error", e.toString());
        }
    }
}
