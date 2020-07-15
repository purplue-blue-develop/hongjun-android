package com.chaosApp.chaos.Models

import android.graphics.Bitmap
import java.io.Serializable

class Floor : Serializable {
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