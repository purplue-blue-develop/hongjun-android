package com.chaosApp.chaos.Models

import java.io.Serializable
import java.time.LocalDateTime

class BuildingProject : Serializable {
    var projectName : String = "";
    var buildingName : String = "";
    var floorList = mutableListOf<Floor>();
    var flawList = mutableListOf<FlawModel>();
    var flawNameList = mutableListOf<String>("선택 없음");
    var investDate : LocalDateTime = LocalDateTime.now();

    // 생성일시
    val createdDate : LocalDateTime = LocalDateTime.now();
    // 최근수정일시
    var latestEditedDte : LocalDateTime = LocalDateTime.now();

    constructor()
    {
    }
}