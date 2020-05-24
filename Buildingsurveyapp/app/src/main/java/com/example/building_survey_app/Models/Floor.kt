package com.example.building_survey_app.Models

import android.graphics.Bitmap

class Floor {
    var FloorLayer : Bitmap? = null;

    constructor() {

    }

    constructor(_floorLayer : Bitmap)
    {
        FloorLayer = _floorLayer;
    }
}