package com.example.building_survey_app.Activities.FlawList

import android.graphics.Bitmap
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.os.Environment.*
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ListView
import com.example.building_survey_app.Activities.FlawCheck.FlawCheckActivity
import com.example.building_survey_app.Helper.ExcelHelper
import com.example.building_survey_app.ListViewFlawItem
import com.example.building_survey_app.ListViewFlawItemAdapter
import com.example.building_survey_app.R
import com.example.building_survey_app.ViewModels.BuildingProjectListViewModel
import org.apache.poi.hssf.usermodel.HSSFWorkbook
import org.apache.poi.poifs.filesystem.POIFSFileSystem
import org.apache.poi.ss.usermodel.Cell
import org.apache.poi.ss.usermodel.Sheet
import org.apache.poi.ss.usermodel.Workbook
import org.apache.poi.util.IOUtils
import org.apache.poi.xssf.usermodel.XSSFSheet
import org.apache.poi.xssf.usermodel.XSSFWorkbook
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
        findViewById<Button>(R.id.FlawListAddMore).setOnClickListener(this);

    }

    override fun onClick(v: View?) {
        when(v?.id)
        {
            R.id.FlawListSave->{
                SaveExcel();
            }
            R.id.FlawListAddMore->{
                val addMoreProject = Intent(this, FlawCheckActivity:: class.java);
                startActivity(addMoreProject);
            }
        }
    }

    fun SaveExcel()
    {
        var file = File(getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), "결함정보.xlsx");

        var wb: XSSFWorkbook? = null;
        var sheet: XSSFSheet? = null;

        if (!file.exists()) {
            wb = XSSFWorkbook();
            wb.createSheet();
            sheet = wb.getSheetAt(0);
        } else {
            if ( !file.inputStream().readBytes().isEmpty()) {
                wb = XSSFWorkbook(file.inputStream());

                if (wb.numberOfSheets == 1)
                    wb.removeSheetAt(0);

                wb.createSheet();
                sheet = wb.getSheetAt(0);
            }
            else
            {
                wb = XSSFWorkbook()
                wb.createSheet();
                sheet = wb.getSheetAt(0);
            };
        }

        var flawList = BuildingProjectListViewModel.BuildingProjectList[0].flawList;

        // 개행, 테이블 타이틀 + flaw List 크기 만큼 행 만들기
        for ( x in 0..1+flawList.size)
        {
            sheet?.createRow(x);
        }


        //테이블 타이틀 만들기
        // 개행, ID, 층수, 실명, 결함유형, 결함위치, 결함종류, 결함길이, 결함너비, 결함개수, 사진, 비교사진 ( 11개)
        var titleList = arrayListOf<String>(
            " ", "ID", "층 수", "실명",
            "결함유형", "결함위치", "결함종류", "결함길이", "결함너비", "결함크기", "결함개수", "사진", "비교사진"
        );

        var i = 1;
        var j = 0;
        var row = sheet?.getRow(i);
        var cell: Cell?;

        for (title in titleList) {
            cell = row!!.createCell(j++);
            cell.setCellValue(title);
        }

        i = 2;
        j = 0;
        for (flaw in flawList) {
            row = sheet!!.getRow(i++);

            row.createCell(j);
            row.getCell(j++)?.setCellValue("");

            row.createCell(j);
            row.getCell(j++)?.setCellValue(flaw.id.toString());

            row.createCell(j);
            row.getCell(j++)?.setCellValue(flaw.id.toString());

            row.createCell(j);
            row.getCell(j++)?.setCellValue(flaw.Name);

            row.createCell(j);
            row.getCell(j++)?.setCellValue(flaw.FlawCategory);

            row.createCell(j);
            row.getCell(j++)?.setCellValue(flaw.FlawPos);

            row.createCell(j);
            row.getCell(j++)?.setCellValue(flaw.Flaw);

            row.createCell(j);
            row.getCell(j++)?.setCellValue(flaw.FlawLength);

            row.createCell(j);
            row.getCell(j++)?.setCellValue(flaw.FlawWidth);

            row.createCell(j);
            if ( flaw.FlawLength == 0.0 || flaw.FlawWidth == 0.0)
                row.getCell(j++)?.setCellValue("-");
            else
                row.getCell(j++)?.setCellValue(flaw.FlawLength.toString() + " X " + flaw.FlawWidth);

            row.createCell(j);
            row.getCell(j++)?.setCellValue(flaw.FlawCount.toString());

            var stream = ByteArrayOutputStream();
            flaw.capturedPic?.compress(Bitmap.CompressFormat.PNG, 100, stream);
            var bytes = stream.toByteArray()
            var helper = wb.addPicture(bytes, Workbook.PICTURE_TYPE_JPEG);
            var drawing = sheet.createDrawingPatriarch();

//            row.createCell(j);
//            row.getCell(j++)?.setCellValue(flaw.capturedPic);

//            row.createCell(j);
//            row.getCell(j++)?.setCellValue(flaw.compareCapturedPic);
            i++;
        }

        var os = FileOutputStream(file.absolutePath);
        wb?.write(os);

//        var excelHelper =  ExcelHelper();
//        excelHelper.Write(BuildingProjectListViewModel.BuildingProjectList[0].flawList);
//        try {

//        var state = getExternalStorageState();
//        if ( state != "mounted")
//        {
//            exitProcess(-1);
//        }
//        testFile.createNewFile();
//
//            var file= File(getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), "결함정보.xls");
//
//            var wb : Workbook? = null;
//            var sheet : Sheet? = null;
//
//            if (file.exists())
//            {
//                wb= HSSFWorkbook();
//                wb.createSheet();
//                sheet = wb.getSheetAt(0);
//            }
//            else
//            {
//                file.createNewFile();
//                var bytes = file.inputStream().readBytes()
//                if (bytes.isNotEmpty()){
//                    var fileSystem = POIFSFileSystem(file.inputStream())
//                    wb = HSSFWorkbook(fileSystem);
//                    wb.removeSheetAt(0);
//                    wb.createSheet();
//                    sheet = wb.getSheetAt(0);
//                }
//            }
//            val row = sheet?.createRow(0);
//            var cell = row?.createCell(0);
//            row?.getCell(0)?.setCellValue(11.0);
//            row?.createCell(1);
//            row?.getCell(1)?.setCellValue(12.0);
//
////            try {
//                var os = FileOutputStream(file.absolutePath);
//                wb?.write(os);
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
