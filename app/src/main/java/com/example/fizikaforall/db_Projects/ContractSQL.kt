package com.example.fizikaforall.db_Projects

object ContractSQL {
    object ProjectsTable{
        const val NAME_TABLE = "Projects"
        const val ID_COLUMN = "idProject"
        const val NAME_PROJECT = "nameProject"
    }
    object ComponentsProjectsTable{
        const val NAME_TABLE = "ComponentsInProject"
        const val ID_COLUMN = "IdComponents"
        const val POSITION_X = "posX"
        const val POSITION_Y = "posY"
        const val ROTATION = "rotation"
        const val TYPE_COMPONENT = "typeComponent"
        const val ID_PROJECT = "projectId"
    }
}