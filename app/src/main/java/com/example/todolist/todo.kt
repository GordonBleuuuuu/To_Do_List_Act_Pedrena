import org.json.JSONObject

data class Todo(val title: String, var checked: Boolean = false) {
    fun toJson(): String {
        val json = JSONObject()
        json.put("title", title)
        json.put("checked", checked)
        return json.toString()
    }

    companion object {
        fun fromJson(jsonString: String): Todo {
            val json = JSONObject(jsonString)
            return Todo(json.getString("title"), json.getBoolean("checked"))
        }
    }
}