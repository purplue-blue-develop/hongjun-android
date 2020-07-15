package com.chaosApp.chaos.Helper

import android.os.Bundle
import android.os.Environment
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.chaosApp.chaos.Models.FlawModel
import org.apache.poi.ss.usermodel.Cell
import org.apache.poi.xssf.usermodel.XSSFSheet
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import java.io.File
import java.io.FileOutputStream
import java.lang.Exception

class ExcelHelper : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState);
    }
    fun ExcelHelper()
    {

    }

    public fun Write(flawList: MutableList<FlawModel>)
    {
        try {
            var file = File(getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), "결함정보.xlsx");

            var wb: XSSFWorkbook? = null;
            var sheet: XSSFSheet? = null;

            if (file.exists()) {
                wb = XSSFWorkbook();
                wb.createSheet();
                sheet = wb.getSheetAt(0);
            } else {
                file.createNewFile();
                wb = XSSFWorkbook(file.inputStream());

                if (wb.numberOfSheets == 1)
                    wb.removeSheetAt(0);

                wb.createSheet();
                sheet = wb.getSheetAt(0);
            }

            // 개행, 테이블 타이틀 + flaw List 크기 만큼 행 만들기
            sheet?.createRow(2 + flawList.size);

            //테이블 타이틀 만들기
            // 개행, ID, 층수, 실명, 결함유형, 결함위치, 결함종류, 결함길이, 결함너비, 결함개수, 사진, 비교사진 ( 11개)
            var titleList = arrayListOf<String>(
                " ", "ID", "층 수", "실명",
                "결함유형", "결함위치", "결함길이", "결함너비", "결함개수", "사진", "비교사진"
            );

            var i = 1;
            var j = 1;
            var row = sheet?.getRow(i);
            row!!.createCell(titleList.size);

            var cell: Cell?;

            for (title in titleList) {
                cell = row.createCell(j++);
                cell.setCellValue(title);
            }

            i = 2;
            j = 1;
            for (flaw in flawList) {
                row = sheet!!.getRow(i++);
                row.createCell(titleList.size);

                row?.getCell(j++)?.setCellValue(flaw.id.toString());
//            row?.getCell(j++)?.setCellValue(flaw.);
                row?.getCell(j++)?.setCellValue(flaw.Name);
                row?.getCell(j++)?.setCellValue(flaw.FlawCategory);
                row?.getCell(j++)?.setCellValue(flaw.FlawPos);
                row?.getCell(j++)?.setCellValue(flaw.FlawLength.toString());
                row?.getCell(j++)?.setCellValue(flaw.FlawWidth);
                row?.getCell(j++)?.setCellValue(flaw.FlawCount.toString());
//            row?.getCell(j++)?.setCellValue(flaw.capturedPic);
//            row?.getCell(j++)?.setCellValue(flaw.compareCapturedPic);
            }

            var os = FileOutputStream(file.absolutePath);
            wb?.write(os);
        }
        catch (e : Exception)
        {
            Log.e("ERROR", e.printStackTrace().toString());

        }
    }

}