package interpreter

class Scope {
    private val variables = mutableMapOf<String, Variable>()

    fun setVariable(
        name: String,
        type: String,
        value: Any?,
    ) {
        variables[name] = Variable(type, value)
    }

    fun getVariable(name: String): Variable? {
        return variables[name]
    }
}
