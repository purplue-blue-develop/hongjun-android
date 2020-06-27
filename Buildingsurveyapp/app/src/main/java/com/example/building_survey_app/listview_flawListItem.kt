package com.example.building_survey_app

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import com.example.building_survey_app.Activities.FlawCheck.FlawCheckActivity
import com.example.building_survey_app.Activities.FlawList.FlawListActivity
import com.example.building_survey_app.ViewModels.BuildingProjectListViewModel

class listview_flawListItem : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_listview_flaw_list_item)
    }
}

class ListViewFlawItem {
    var capturedPic : Bitmap? = null;
    var ID : Int = 0;
    var Name : String = "";
    var FlawCategory : String = "";
    var FlawPos : String = "";
    var Flaw : String = "";
}

class ListViewFlawItemAdapter(val ctx : Context, val data : ArrayList<ListViewFlawItem>) : BaseAdapter() {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var view = LayoutInflater.from(ctx).inflate(R.layout.activity_listview_flaw_list_item, null);

        val imageView = view?.findViewById<ImageView>(R.id.imageViewListViewItemCapturedPic) as ImageView;
        val idTextView = view.findViewById<TextView>(R.id.textViewListViewItemID) as TextView;
        val nameTextView = view.findViewById<TextView>(R.id.textViewListViewItemName) as TextView;

        val flawCategoryTextView = view.findViewById<TextView>(R.id.textViewListViewItemFlawCategory) as TextView;
        val flawPosTextView = view.findViewById<TextView>(R.id.textViewListViewItemFlawPos) as TextView;
        val flawTextView = view.findViewById<TextView>(R.id.textViewListViewItemFlaw) as TextView;

        val cur = data[position];

        imageView.setImageBitmap(cur.capturedPic)
        idTextView.text = cur.ID.toString();
        nameTextView.text =cur.Name;
        flawCategoryTextView.text =cur.FlawCategory;
        flawPosTextView.text = cur.FlawPos;
        flawTextView.text = cur.Flaw;

        view.findViewById<Button>(R.id.flawListViewItemEditButton).setOnClickListener(View.OnClickListener() {
            val id = cur.ID;
            val intent = Intent(ctx, FlawCheckActivity::class.java);
            intent.putExtra("ID", id.toString());
            ctx.startActivity(intent);
        });

        view.findViewById<Button>(R.id.flawListViewItemRemoveButton).setOnClickListener(View.OnClickListener() {
            val id = cur.ID;
            val flaw =
                BuildingProjectListViewModel.BuildingProjectList[0].flawList.find { f -> f.id == id };
            BuildingProjectListViewModel.BuildingProjectList[0].flawList.remove(flaw);
            ctx.startActivity(Intent(ctx, FlawListActivity::class.java));
        });

        return view;
    }
    override fun getItem(position: Int): Any {
        return data[position]
    }
    override fun getItemId(position: Int): Long {
        return position.toLong();
    }
    override fun getCount(): Int {
        return data.size;
    }
}
