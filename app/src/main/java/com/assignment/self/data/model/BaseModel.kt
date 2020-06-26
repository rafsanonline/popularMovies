package com.assignment.self.data.model

class BaseModel<z>() {
    var status = false
    lateinit var message: String
    var data: z? = null
}