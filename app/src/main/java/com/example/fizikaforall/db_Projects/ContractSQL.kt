package com.example.fizikaforall.db_Projects

object ContractSQL {
    object ProjectsTable{
        const val NAME_TABLE = "Projects"
        const val ID_COLUMN = "idProject"
        const val NAME_PROJECT = "nameProject"
    }
    object Details{
        const val NAME_TABLE = "details"
        const val ID_COLUMN = "id"
        const val SIZE_COLUMN = "size"
        const val POSITION_X = "x_pos"
        const val POSITION_Y = "y_pos"
        const val ROTATION = "rotation"
        const val TYPE_COMPONENT = "type"
        const val ID_PROJECT = "id_project"
    }
    object Voltmeter{
        const val NAME_TABLE = "voltmeter"
        const val ID_COLUMN = "id"
        const val VOLTAGE =  "voltage"
        const val TYPE_COMPONENT = "typeComponent"
        const val ID_DETAIL = "id_detail"
    }
    object Resistors{
        const val NAME_TABLE = "resistor"
        const val ID_COLUMN = "id"
        const val RESISTANCE="resistance"
        const val ID_DETAIL = "id_detail"
    }
    object Powers{
        const val NAME_TABLE = "power"
        const val ID_COLUMN = "id"
        const val VOLTAGE = "powers"
        const val ID_DETAIL = "id_detail"
    }
    object DotsContract{
        const val NAME_TABLE = "dots_contact"
        const val ID_COLUMN = "id"
        const val POSITION_X = "x_pos"
        const val POSITION_Y = "y_pos"
        const val ID_DETAIL = "id_detail"
    }
    object ContactDots{
        const val NAME_TABLE = "contact_dots"
        const val ID_COLUMN = "id"
        const val ID_START = "id_start"
        const val ID_FINISH = "id_finish"
    }
}