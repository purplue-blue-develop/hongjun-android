package com.example.building_survey_app.Models

import android.graphics.Bitmap

class Floor {
    var Name : String ="";
    var FloorLayer : Bitmap? = null;

    constructor(Name:String) {
        this.Name = Name;
    }

    constructor(Name:String, FloorLayer : Bitmap)
    {
        this.Name = Name
        this.FloorLayer = FloorLayer
    }
}