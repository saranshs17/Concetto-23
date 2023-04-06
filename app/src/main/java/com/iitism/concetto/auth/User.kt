package com.iitism.concetto.auth

data class User(
    val Player_Email:String,
    val Team_Name:String,
    val Player_mobile: Long,
    val player_problemState:String,
    val player_organization:String,
    val team_Id:String)
