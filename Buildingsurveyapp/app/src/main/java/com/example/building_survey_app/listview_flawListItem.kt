package com.example.building_survey_app

import android.content.Context
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView

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
//    var FlawCategory : Double = 0.0;
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
//        var flawCategoryView = view.findViewById<>()

        val flawLengthTextView = view.findViewById<TextView>(R.id.textViewListViewItemFlawCategory) as TextView;
        val flawWidthTextView = view.findViewById<TextView>(R.id.textViewListViewItemFlawPos) as TextView;
        val flawCountTextView = view.findViewById<TextView>(R.id.textViewListViewItemFlaw) as TextView;

        val cur = data[position];

        imageView.setImageBitmap(cur.capturedPic)
        idTextView.text = cur.ID.toString();
        nameTextView.text =cur.Name;
        flawWidthTextView.text =cur.FlawCategory;
        flawCountTextView.text = cur.FlawPos;
        flawLengthTextView.text = cur.Flaw;
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
