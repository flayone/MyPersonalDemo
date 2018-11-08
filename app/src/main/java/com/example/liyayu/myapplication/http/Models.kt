package com.example.liyayu.myapplication.http

import java.io.Serializable

/**
 * Created by liyayu on 2018/10/19.
 */

class HomesLoginModel : Serializable{
    var passwd = ""
    var userAccount = ""
}

class HomesLoginResponseModel: Serializable{
    var empId= ""
    var userAccount= ""
    var accessToken= ""
    var storeId= ""
    var storeName= ""
    var empName= ""
    var regionAbbr= ""
    var storeTel= ""
    var storeOwnerType= ""//门店归属类型02直营店04加盟店05合资店
}