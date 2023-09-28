package com.example.data.model.tables

import com.example.utils.Constants

enum class RoleModel {
    ADMIN, CLIENT, GUEST
}

fun String.getRoleByString(): RoleModel {
    return when(this) {
        Constants.Role.ADMIN -> RoleModel.ADMIN
        Constants.Role.CLIENT -> RoleModel.CLIENT
        else -> RoleModel.GUEST
    }
}

fun RoleModel.getStringByRole(): String {
    return when(this) {
        RoleModel.ADMIN -> Constants.Role.ADMIN
        RoleModel.CLIENT -> Constants.Role.CLIENT
        else -> Constants.Role.GUEST
    }
}