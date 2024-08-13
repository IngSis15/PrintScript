package interpreter

class Scope {
    private val variables = mutableMapOf<String, Any>()

    fun setVariable(name: String, value: Any) {
        variables[name] = value
    }

    fun getVariable(name: String): Any? {
        return variables[name]
    }
}