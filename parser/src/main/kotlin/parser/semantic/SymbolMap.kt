package parser.semantic

class SymbolMap {
    private val symbols = mutableMapOf<String, Object>()

    fun addSymbol(
        name: String,
        type: VariableType,
        mutable: Boolean,
    ) {
        symbols[name] = Object(name, type, mutable)
    }

    fun getSymbol(name: String): Object? {
        return symbols[name]
    }
}
