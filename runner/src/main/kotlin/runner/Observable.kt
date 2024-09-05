package runner

interface Observable {
    fun addObserver(observer: Observer): Observable

    fun removeObserver(observer: Observer): Observable

    fun notifyObservers(event: Event)
}
