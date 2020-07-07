package com.example.building_survey_app.Activities.FlawList

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.building_survey_app.Activities.FlawCheck.FlawCheckActivity
import com.example.building_survey_app.Activities.MainPage.MainActivity
import com.example.building_survey_app.ListViewFlawItem
import com.example.building_survey_app.ListViewFlawItemAdapter
import com.example.building_survey_app.Models.BuildingProject
import com.example.building_survey_app.Models.FlawModel
import com.example.building_survey_app.Models.Floor
import com.example.building_survey_app.R
import com.example.building_survey_app.ViewModels.BuildingProjectListViewModel
import org.apache.poi.ss.usermodel.Cell
import org.apache.poi.ss.usermodel.Workbook
import org.apache.poi.xssf.usermodel.*
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.lang.Exception

class FlawListActivity : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_flaw_list)

        if (savedInstanceState == null) {
            var navigateData = intent.extras;
            // 프로젝트 로드로 들어온경우
            if (navigateData != null){
                var loadProjectName = navigateData.getString("ProjectName");
                if (!loadProjectName.isNullOrEmpty()) {
                    if ( BuildingProjectListViewModel.BuildingProjectList.size > 0 )
                        BuildingProjectListViewModel.BuildingProjectList.clear()

                    var bp = BuildingProject()
                    bp.projectName = loadProjectName
                    BuildingProjectListViewModel.BuildingProjectList.add(bp)
                    loadExcel(loadProjectName)
                }
            }
        }

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

        findViewById<Button>(R.id.goToHomeFromFlawList).setOnClickListener(this)
        findViewById<Button>(R.id.FlawListSave).setOnClickListener(this);
        findViewById<Button>(R.id.FlawListAddMore).setOnClickListener(this);
    }

    override fun onBackPressed() {
        Toast.makeText(this, "지원하지 않습니다.", Toast.LENGTH_SHORT).show()
        return;
    }

    override fun onClick(v: View?) {
        when(v?.id)
        {
            R.id.goToHomeFromFlawList->{
                BuildingProjectListViewModel.BuildingProjectList.clear()
                var homeIntent = Intent(this, MainActivity::class.java)
                startActivity(homeIntent)
            }
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

        try {
            var wb: XSSFWorkbook? =null
            var sheet: XSSFSheet? =null

                wb = XSSFWorkbook()
                wb.createSheet();

            sheet = wb?.getSheetAt(0);

            var drawing = sheet.createDrawingPatriarch() as XSSFDrawing;
            var flawList = BuildingProjectListViewModel.BuildingProjectList[0].flawList;

            // 개행, 테이블 타이틀 + flaw List 크기 만큼 행 만들기
            for (x in 0..1 + flawList.size) {
                sheet?.createRow(x);
            }

            //테이블 타이틀 만들기
            // 개행, ID, 층수, 실명, 결함유형, 결함위치, 결함종류, 결함길이, 결함너비, 결함개수, 사진, 비교사진 ( 11개)
            var titleList = arrayListOf<String>(
                " ", "ID", "STORY", "실명",
                "결함유형", "결함위치", "결함종류", "결함길이", "결함너비", "결함크기", "결함개수", "사진", "비교사진"
            );

            var i = 1;
            var j = 0;
            var rowFirst = sheet.getRow(0);
            var row = sheet.getRow(i);
            var cell: Cell?;

            for (title in titleList) {
                rowFirst.createCell(j).setCellValue(" ");
                cell = row!!.createCell(j++);
                cell.setCellValue(title);
            }

            i = 2;
            for (flaw in flawList) {
                j = 0;
                row = sheet!!.getRow(i);

                row.createCell(j);
                row.getCell(j++)?.setCellValue(" ");
    //            sheet.autoSizeColumn(j++);

                row.createCell(j);
                row.getCell(j++)?.setCellValue(flaw.id.toString());
    //            sheet.autoSizeColumn(j++);

                row.createCell(j);
                row.getCell(j++)?.setCellValue(flaw.Floor);
    //            sheet.autoSizeColumn(j++);

                row.createCell(j);
                row.getCell(j++)?.setCellValue(flaw.Name);
    //            sheet.autoSizeColumn(j++);

                row.createCell(j);
                row.getCell(j++)?.setCellValue(flaw.FlawCategory);
    //            sheet.autoSizeColumn(j++);

                row.createCell(j);
                row.getCell(j++)?.setCellValue(flaw.FlawPos);
    //            sheet.autoSizeColumn(j++);

                row.createCell(j);
                row.getCell(j++)?.setCellValue(flaw.Flaw);
    //            sheet.autoSizeColumn(j++);

                row.createCell(j);
                row.getCell(j++)?.setCellValue(flaw.FlawLength);
    //            sheet.autoSizeColumn(j++);

                row.createCell(j);
                row.getCell(j++)?.setCellValue(flaw.FlawWidth);
    //            sheet.autoSizeColumn(j++);

                row.createCell(j);
                if (flaw.FlawLength == 0.0 || flaw.FlawWidth == 0.0)
                    row.getCell(j++)?.setCellValue("-");
                else
                    row.getCell(j++)
                        ?.setCellValue(flaw.FlawLength.toString() + " X " + flaw.FlawWidth);
    //            sheet.autoSizeColumn(j++);

                row.createCell(j);
                row.getCell(j++)?.setCellValue(flaw.FlawCount.toString());
    //            sheet.autoSizeColumn(j++);

                var stream = ByteArrayOutputStream();
                flaw.capturedPic?.compress(Bitmap.CompressFormat.PNG, 100, stream);
                var bytes = stream.toByteArray()
                var pictureId = wb.addPicture(bytes, Workbook.PICTURE_TYPE_JPEG)
                stream.close();

                var anchor = XSSFClientAnchor();

                anchor.setCol1(j);
                anchor.setCol2(++j);
                anchor.row1 = i
                anchor.row2 = i+1;
                var pic = drawing.createPicture(anchor, pictureId);


                stream = ByteArrayOutputStream();
                flaw.compareCapturedPic?.compress(Bitmap.CompressFormat.PNG, 100, stream);
                bytes = stream.toByteArray()
                pictureId = wb.addPicture(bytes, Workbook.PICTURE_TYPE_JPEG)
                stream.close()

                anchor = XSSFClientAnchor()

                anchor.setCol1(j)
                anchor.setCol2(++j)
                anchor.row1 = i;
                anchor.row2 = i+1;
                pic = drawing.createPicture(anchor,pictureId);

                i++;
            }

            var document = getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS)
            var allFiles = document!!.listFiles()
            var needToCreateFolder = true

            for (file in allFiles)
            {
                if (file.isDirectory)
                {
                    if (
                        file.nameWithoutExtension == BuildingProjectListViewModel.BuildingProjectList[0].projectName
                    )
                        needToCreateFolder = false
                }
            }
            if ( needToCreateFolder ) {
                var ff = File(
                    getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS),
                    BuildingProjectListViewModel.BuildingProjectList[0].projectName
                )
                ff.mkdir()
            }
            var file = File(getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS),
                BuildingProjectListViewModel.BuildingProjectList[0].projectName + "/결함정보.xlsx")

            var os = FileOutputStream(file.absolutePath);
            wb?.write(os)
            Toast.makeText(this, "저장이 성공적으로 완료되었습니다.", Toast.LENGTH_SHORT).show()
        }
        catch (e : Exception)
        {
            Log.e("ERROR", e.printStackTrace().toString())
        };
    }

    private fun loadExcel(projectName : String)
    {
        var file = File(getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), "/" + projectName + "/"+ "결함정보.xlsx");

        var wb: XSSFWorkbook? =null
        var sheet: XSSFSheet? = null
        if (!file.exists())
        {
            return;
        } else if (!file.inputStream().readBytes().isEmpty()) {
            wb = XSSFWorkbook(file.inputStream());
            sheet = wb.getSheetAt(0);
        } else {
            wb = XSSFWorkbook()
            wb.createSheet();
            sheet = wb.getSheetAt(0);
        };

        BuildingProjectListViewModel.BuildingProjectList.add(BuildingProject())
        var flawList = BuildingProjectListViewModel.BuildingProjectList[0].flawList;

        //테이블 타이틀 만들기
        // 개행, ID, 층수, 실명, 결함유형, 결함위치, 결함종류, 결함길이, 결함너비, 결함개수, 사진, 비교사진 ( 11개)
        var titleList = arrayListOf<String>(
            " ", "ID", "층 수", "실명",
            "결함종류", "결함위치", "결함유형", "결함길이", "결함너비", "결함크기", "결함개수", "사진", "비교사진"
        );


        // 모든 행에 대해 데이터 주입
        for (i in 2 .. sheet.lastRowNum) {
            var readedFlaw = FlawModel()
            var row = sheet.getRow(i)

            for (j in 1 .. row.lastCellNum )
            {
                when(j) {
                    1-> readedFlaw.id = row.getCell(j).stringCellValue.toInt()
                    2-> readedFlaw.Floor = row.getCell(j).stringCellValue
                    3-> readedFlaw.Name = row.getCell(j).stringCellValue
                    4-> readedFlaw.FlawCategory = row.getCell(j).stringCellValue
                    5-> readedFlaw.FlawPos = row.getCell(j).stringCellValue
                    6-> readedFlaw.Flaw = row.getCell(j).stringCellValue
                    7-> readedFlaw.FlawLength =  row.getCell(j).numericCellValue
                    8-> readedFlaw.FlawWidth = row.getCell(j).numericCellValue
                    10-> readedFlaw.FlawCount = row.getCell(j).stringCellValue.toInt()
                    else-> {

                    }
                }
            }
            flawList.add(readedFlaw)
        }
        var floorList = BuildingProjectListViewModel.BuildingProjectList[0].floorList
        for (flaw in flawList)
        {
            var exist= floorList.find{
                f->f.Name == flaw.Floor
            }

            if ( exist == null)
                floorList.add(Floor(flaw.Floor))
        }

        var dp = sheet.createDrawingPatriarch();
        if ( dp==null)
            return;

        var helper = wb.creationHelper;
        var i =0;
        for (pic in dp.shapes)
        {
            var bytes =  (pic as XSSFPicture).pictureData.data;
            if ( i%2 == 0)
                flawList[i/2].capturedPic = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
            else
                flawList[i/2].compareCapturedPic = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
            i++;
        }

    }
}
