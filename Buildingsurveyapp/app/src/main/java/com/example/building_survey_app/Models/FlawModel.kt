package com.example.building_survey_app.Models

import android.graphics.Bitmap
import java.io.Serializable

class FlawModel : Serializable {
    var id : Int = 0
    var idBasedFloor : Int = 0
    var Name : String =""
    var Floor : String =""
    var FlawCategory : String =""
    var FlawPos : String =""
    var Flaw : String=""
    var FlawWidth : Double = 0.0
    var FlawLength : Double = 0.0
    var FlawCount : Int = 0
    var capturedPic : Bitmap? = null
    var compareCapturedPic : Bitmap? = null

    var capturedPicName : String =""
    var compareCapturedPicName :String =""
}