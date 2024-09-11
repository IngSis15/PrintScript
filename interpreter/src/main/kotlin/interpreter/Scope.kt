package interpreter

class Scope(private val parent: Scope?) {
    private val variables = mutableMapOf<String, Variable>()

    fun setVariable(
        name: String,
        type: String,
        mutable: Boolean,
        value: Any?,
    ) {
        variables[name] = Variable(type, mutable, value)
    }

    fun getVariable(name: String): Variable? {
        return variables[name] ?: parent?.getVariable(name)
    }
}
