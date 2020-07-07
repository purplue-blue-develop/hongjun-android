package com.example.building_survey_app

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.TextView
import com.example.building_survey_app.Activities.FlawList.FlawListActivity

class listview_floorListItem : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_listview_project_list_item)
    }
}

class ListViewFloorItem
{
    var projectName : String =""
}

class ListViewFloorItemAdapter(val ctx : Context, val data : ArrayList<ListViewFloorItem>) : BaseAdapter() {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var view = LayoutInflater.from(ctx).inflate(R.layout.activity_listview_project_list_item, null);

        val floorName = view.findViewById<TextView>(R.id.textViewListViewItemProjectName) as TextView;
        val cur = data[position];

        floorName.text =cur.projectName

        view.findViewById<Button>(R.id.buttonOpenProject).setOnClickListener(View.OnClickListener() {
            val projectName = cur.projectName;
            val intent = Intent(ctx, FlawListActivity::class.java);
            intent.putExtra("ProjectName", projectName);
            ctx.startActivity(intent);
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
