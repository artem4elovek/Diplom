package com.example.fizikaforall.db_Projects

import java.lang.reflect.Field

open class AppException : RuntimeException()

class EmptyFieldException(
    val field: Field
) : AppException()

class NameProjectException : AppException()
