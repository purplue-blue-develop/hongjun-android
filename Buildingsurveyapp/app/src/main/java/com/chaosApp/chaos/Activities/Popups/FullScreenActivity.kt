package com.chaosApp.chaos.Activities.Popups

import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.chaosApp.chaos.R

class FullScreenActivity : AppCompatActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fullscreen_image);
        val fullScreenMode : ImageView = findViewById(R.id.fullScreenImage);

        val uriData = intent.extras?.get("URI")

        if (uriData.toString().isNullOrEmpty())
            return

        val uri = Uri.parse(uriData.toString())
        fullScreenMode.setImageURI(uri)
        fullScreenMode.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.fullScreenImage ->
                finish();

        }

    }

}