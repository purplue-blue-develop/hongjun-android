package com.chaosApp.chaos

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.core.content.FileProvider
import com.chaosApp.chaos.Activities.FlawCheck.FlawCheckActivity
import com.chaosApp.chaos.Activities.FlawList.FlawListActivity
import com.chaosApp.chaos.Activities.Popups.FullScreenActivity
import com.chaosApp.chaos.ViewModels.BuildingProjectListViewModel
import java.io.File

class listview_flawListItem : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_listview_flaw_list_item)
    }
}

class ListViewFlawItem {
    var capturedPic : Bitmap? = null;
    var ID : Int = 0;
    var IDBasedFloor : Int  = 0
    var Name : String = "";
    var FlawCategory : String = "";
    var FlawPos : String = "";
    var Flaw : String = "";
    var Floor : String =""
}

class ListViewFlawItemAdapter(val ctx : Context, val data : ArrayList<ListViewFlawItem>) : BaseAdapter() {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var view = LayoutInflater.from(ctx).inflate(R.layout.activity_listview_flaw_list_item, null);

        val imageView = view?.findViewById<ImageView>(R.id.imageViewListViewItemCapturedPic) as ImageView;
        val flawCategoryTextView = view.findViewById<TextView>(R.id.textViewListViewItemFlawCategory) as TextView;
        val flawIDWithFloor = view.findViewById<TextView>(R.id.textViewListViewItemFLawIDwithFloor) as TextView

        val cur = data[position];

        imageView.setImageBitmap(cur.capturedPic)
        flawIDWithFloor.text =  cur.Floor + "_" + cur.IDBasedFloor
        flawCategoryTextView.text =cur.FlawCategory;

        // edit 버튼
        view.findViewById<Button>(R.id.flawListViewItemEditButton).setOnClickListener(View.OnClickListener() {
            val id = cur.ID;
            val intent = Intent(ctx, FlawCheckActivity::class.java);
            intent.putExtra("ID", id);
            ctx.startActivity(intent);
        });

        // Remove 버튼
        view.findViewById<Button>(R.id.flawListViewItemRemoveButton).setOnClickListener(View.OnClickListener() {

            val id = cur.ID;
            var delete = false;
            var flaw =
                BuildingProjectListViewModel.BuildingProjectList[0].flawList.find { f -> f.id == id };

            var alertDialog = AlertDialog.Builder(ctx).setMessage(flaw?.Floor +"_" + flaw?.idBasedFloor + "를 삭제 하시겠습니까?")
                .setCancelable(false).setPositiveButton("확인") {
                    dialog, which ->
                    delete = true
                    flaw?.Name = ""
                    flaw?.FlawCategory = ""
                    flaw?.FlawPos = ""
                    flaw?.Flaw = ""
                    flaw?.FlawWidth = 0.0
                    flaw?.FlawLength = 0
                    flaw?.FlawCount = 0
                    flaw?.capturedPic = null
                    flaw?.compareCapturedPic = null
                    flaw?.compareCapturedPicName = ""
                    flaw?.capturedPicName = ""

                    var intent = Intent(ctx, FlawListActivity::class.java)
                    intent.putExtra("ID", flaw?.id)
                    ctx.startActivity(intent)
                    Toast.makeText(ctx, "삭제되었습니다.", Toast.LENGTH_SHORT).show()

                }.setNegativeButton("취소"){
                    dialog, which ->
                }.create()

                alertDialog.show()

        })

        view.findViewById<ImageView>(R.id.imageViewListViewItemCapturedPic).setOnClickListener(View.OnClickListener {
            var file = File(ctx.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS),
                BuildingProjectListViewModel.BuildingProjectList[0].projectName + "/" +
                        "Pictures/" +
                        cur.Floor + "_" +
                        cur.IDBasedFloor + ".png"
            )
            var captureSaveUri = FileProvider.getUriForFile(ctx, "com.chaosApp.chaos.fileProvider" ,file)
            val fullScreenIntent = Intent(ctx,  FullScreenActivity::class.java)
            fullScreenIntent.putExtra("URI", captureSaveUri)
            ctx.startActivity(fullScreenIntent)
        })
        return view;
    }
    override fun getItem(position: Int): Any {
        return data[position]
    }
    override fun getItemId(position: Int): Long {
        return position.toLong()
    }
    override fun getCount(): Int {
        return data.size
    }
    fun getItems() : ArrayList<ListViewFlawItem>
    {
        return data
    }
}
