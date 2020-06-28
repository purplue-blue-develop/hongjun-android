package com.example.building_survey_app.Activities.FlawList

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.os.Environment.*
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ListView
import com.example.building_survey_app.Helper.ExcelHelper
import com.example.building_survey_app.ListViewFlawItem
import com.example.building_survey_app.ListViewFlawItemAdapter
import com.example.building_survey_app.R
import com.example.building_survey_app.ViewModels.BuildingProjectListViewModel
import org.apache.poi.hssf.usermodel.HSSFWorkbook
import org.apache.poi.poifs.filesystem.POIFSFileSystem
import org.apache.poi.ss.usermodel.Sheet
import org.apache.poi.ss.usermodel.Workbook
import java.io.*
import java.lang.Exception
import kotlin.system.exitProcess

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
        var excelHelper =  ExcelHelper();
        excelHelper.Write(BuildingProjectListViewModel.BuildingProjectList[0].flawList);
//        try {
//            var testFile = File(getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), "씨브럴.txt");
//            testFile.createNewFile();
//
//        var writer =BufferedWriter(FileWriter(testFile, true));
//        writer.append("SIBAL");
//        writer.newLine();
//        writer.append("SIBAL");
//        writer.close();
//
//        var state = getExternalStorageState();
//        if ( state != "mounted")
//        {
//            exitProcess(-1);
//        }
//        testFile.createNewFile();

            var file= File(getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), "결함정보.xls");

            var wb : Workbook? = null;
            var sheet : Sheet? = null;

            if (file.exists())
            {
                wb= HSSFWorkbook();
                wb.createSheet();
                sheet = wb.getSheetAt(0);
            }
            else
            {
                file.createNewFile();
                var bytes = file.inputStream().readBytes()
                if (bytes.isNotEmpty()){
                    var fileSystem = POIFSFileSystem(file.inputStream())
                    wb = HSSFWorkbook(fileSystem);
                    wb.removeSheetAt(0);
                    wb.createSheet();
                    sheet = wb.getSheetAt(0);
                }
            }
            val row = sheet?.createRow(0);
            var cell = row?.createCell(0);
            row?.getCell(0)?.setCellValue(11.0);
            row?.createCell(1);
            row?.getCell(1)?.setCellValue(12.0);

//            try {
                var os = FileOutputStream(file.absolutePath);
                wb?.write(os);
//            }
//            catch (e : IOException){
//                e.printStackTrace();
//            }
//        }
//        catch (e : Exception)
//        {
//            Log.e("Save Sequence Error", e.toString());
//        }
    }
}
