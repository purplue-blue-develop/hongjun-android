package com.example.building_survey_app.ViewModels

import com.example.building_survey_app.Models.BuildingProject
import com.example.building_survey_app.Models.FlawModel
import com.example.building_survey_app.Models.Floor

class BuildingProjectListViewModel
{
    companion object{
        var BuildingProjectList = mutableListOf<BuildingProject>();
        var latestCreatedFlawData : FlawModel?  = null
    }
}
