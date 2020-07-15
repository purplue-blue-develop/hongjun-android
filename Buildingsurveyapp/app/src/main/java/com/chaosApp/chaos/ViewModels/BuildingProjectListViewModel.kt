package com.chaosApp.chaos.ViewModels

import com.chaosApp.chaos.Models.BuildingProject
import com.chaosApp.chaos.Models.FlawModel

class BuildingProjectListViewModel
{
    companion object{
        var BuildingProjectList = mutableListOf<BuildingProject>();
        var rencentFlawModel : FlawModel?  = null
    }
}
