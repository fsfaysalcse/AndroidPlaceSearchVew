package android.library.network


import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RetrofitUtil {
    fun<T> Call<T>.enqueue(callback: CallBackKt<T>.() -> Unit) {
        val callBackKt = CallBackKt<T>()
        callback.invoke(callBackKt)
        this.enqueue(callBackKt)
    }

    class CallBackKt<T>: Callback<T> {

        var onResponse: ((Response<T>) -> Unit)? = null
        var onFailure: ((t: Throwable?) -> Unit)? = null

        override fun onFailure(call: Call<T>, t: Throwable) {
            onFailure?.invoke(t)
        }

        override fun onResponse(call: Call<T>, response: Response<T>) {
            onResponse?.invoke(response)
        }
    }

    fun<T> Call<T>.onEnqueue(actOnSuccess: (Response<T>) -> Unit, actOnFailure: (t: Throwable?) -> Unit)   {
        this.enqueue(object: Callback<T>    {
            override fun onFailure(call: Call<T>?, t: Throwable?) {
                actOnFailure(t)
            }

            override fun onResponse(call: Call<T>?, response: Response<T>) {
                actOnSuccess(response)
            }
        })
    }

    inline fun <T> Call<T>.addEnqueue(
        crossinline onSuccess: (response: Response<T>) -> Unit = { response: Response<T> -> },
        crossinline onFail: (t: Throwable) -> Unit = { throwable: Throwable ->}
    ):Callback<T> {
        val callback = object : Callback<T> {
            override fun onFailure(call: Call<T>, t: Throwable) {
                onFail(t)
            }

            override fun onResponse(call: Call<T>, response: Response<T>) {
                onSuccess(response)
            }
        }
        enqueue(callback)
        return callback
    }


}