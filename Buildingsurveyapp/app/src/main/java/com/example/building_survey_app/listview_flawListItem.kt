package com.example.building_survey_app

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ListView
import android.widget.TextView

class listview_flawListItem : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_listview_flaw_list_item)
    }
}

class ListViewFlawItem {
    var ID : String = "";
    var Name : String = "";
    var FlawCategory : Double = 0.0;
    var FlawCount : Int = 0;
    var FlawLength : Double = 0.0;
}

class ListViewFlawItemAdapter : BaseAdapter() {
    var Items = ArrayList<ListViewFlawItem>();
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var view = convertView;
        var context = parent?.context;

        if (view == null) {
            val inflater = context?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater;
            view = inflater.inflate(R.layout.activity_listview_flaw_list_item, parent, false);
        }
        val idTextView = view?.findViewById<TextView>(R.id.textViewID) as TextView;
        val nameTextView = view?.findViewById<TextView>(R.id.textViewName) as TextView;
        val flawCategoryTextView = view?.findViewById<TextView>(R.id.textViewFlawCategory) as TextView;
        val flawCountTextView = view?.findViewById<TextView>(R.id.textViewFlawCount) as TextView;
        val flawLengthTextView = view?.findViewById<TextView>(R.id.textViewFlawLength) as TextView;

        val cur = Items[position];

        idTextView.setText(cur.ID);
        nameTextView.setText(cur.Name);
        flawCategoryTextView.setText(cur.FlawCategory.toString());
        flawCountTextView.setText(cur.FlawCount.toString());
        flawLengthTextView.setText(cur.FlawLength.toString());
        return view;
    }
    override fun getItem(position: Int): Any {
        return Items[position]
    }
    override fun getItemId(position: Int): Long {
        return position.toLong();
    }
    override fun getCount(): Int {
        return Items.size;
    }
}
