package uz.umarxon.roomcrud_coroutines.events

open class Event<out T>(private val content:T){
    var hasBeenHandled = false
    private set //Allow extrenal read but not write

    /**
    * Returns the content and prevents its use again
    */
    fun getContentIfNoHandled():T?{
        return if (hasBeenHandled){
            null
        }else{
            hasBeenHandled = true
            content
        }
    }

    /**
     * Returns the content, even if it's already been handled
     */
    fun peekContent():T = content

}