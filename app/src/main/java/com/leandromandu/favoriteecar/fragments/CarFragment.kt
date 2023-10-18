package com.leandromandu.favoriteecar.fragments

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.AsyncTask
import android.os.Build
import android.os.Bundle
import android.util.Base64InputStream
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.leandromandu.favoriteecar.R
import com.leandromandu.favoriteecar.adapter.CarAdapter
import com.leandromandu.favoriteecar.data.CarroFactory
import com.leandromandu.favoriteecar.data.CarsApi
import com.leandromandu.favoriteecar.data.local.CarRepository
import com.leandromandu.favoriteecar.dominio.Carro
import org.json.JSONArray
import org.json.JSONObject
import org.json.JSONTokener
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.lang.Exception
import java.net.HttpURLConnection
import java.net.URL

class CarFragment : Fragment(){
    lateinit var car_list : RecyclerView
    lateinit var pb_progress : ProgressBar
    lateinit var iv_no_connection:ImageView
    lateinit var carsApi : CarsApi
    lateinit var bt_tentar_novamente : Button
    var listaCarros : ArrayList<Carro> = ArrayList()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.car_fragment, container, false)
//        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        setupRetrofit()
        setupView(view)
        setupListeners()


    }

    override fun onResume() {
        super.onResume()
        if( checkInternet(context)){
            //metodo asynctask http
            //GetCarInformations().execute("https://igorbag.github.io/cars-api/cars.json")

            //metodo retrofit

            getAllCars()
        }
        else{
            stateNoConnection()
        }
    }

    fun setupListeners(){
        bt_tentar_novamente.setOnClickListener{

            if( checkInternet(context)){
                getAllCars()
            }
            else{
                stateNoConnection()
            }
        }
    }

    fun stateNoConnection(){
        iv_no_connection.visibility=View.VISIBLE
        bt_tentar_novamente.visibility=View.VISIBLE
        car_list.visibility=View.GONE
        pb_progress.visibility=View.GONE
    }

    fun stateLoading(){
        pb_progress.visibility=View.VISIBLE
        iv_no_connection.visibility=View.GONE
        bt_tentar_novamente.visibility=View.GONE
        car_list.visibility=View.GONE
    }



    fun stateSuccess(){
        car_list.visibility=View.VISIBLE
        pb_progress.visibility=View.GONE
        iv_no_connection.visibility=View.GONE
        bt_tentar_novamente.visibility=View.GONE
    }

    fun stateError(){
        Toast.makeText(context, R.string.response_error, Toast.LENGTH_LONG).show()
        stateNoConnection()
    }


    fun setupRetrofit(){
        val retrofit = Retrofit.Builder()
            .baseUrl("https://igorbag.github.io/cars-api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        carsApi=retrofit.create(CarsApi::class.java)

    }

    fun getAllCars(){
        stateLoading()
        carsApi.getAllCars().enqueue(object : Callback<List<Carro>>{
            override fun onResponse(call: Call<List<Carro>>, response: Response<List<Carro>>) {
                Log.e("retrofit","onResponse")
                if(response.isSuccessful){
                    response.body()?.let {
                        setupList(it)
                    }
                    stateSuccess()
                }else{
                    stateError()
                }
            }

            override fun onFailure(call: Call<List<Carro>>, t: Throwable) {
                Log.e("retrofit","onFailure")
                stateError()
            }

        })
    }

    fun setupView(view:View){
        bt_tentar_novamente = view.findViewById(R.id.bt_tentar_novamente)
        pb_progress = view.findViewById(R.id.pb_progressBar)
        iv_no_connection=view.findViewById(R.id.iv_no_connection)
        //val dados = CarroFactory.lista
//        val adapter = CarAdapter(dados)
  //      val adapter = CarAdapter( listaCarros)
        car_list=view.findViewById(R.id.rv_car_list)
    //    car_list.adapter=adapter
    }

    fun setupList(listaApi: List<Carro>){
//        var listaFavoritos = CarRepository(requireContext()).getAll()
        for (i in 0 until listaApi.size){
            if (CarRepository(requireContext()).findById(listaApi[i].id) != null)
                listaApi[i].isFavorite=true
        }
        val adapter = CarAdapter( listaApi)
        car_list.adapter=adapter
        adapter.carItemListener={

            if (CarRepository(requireContext()).findById(it.id) == null) {
                CarRepository(requireContext()).save(it)
                Toast.makeText(context, "Carro ${it.id} adicionado aos favoritos! :)", Toast.LENGTH_LONG).show()
            }
            else {
                CarRepository(requireContext()).deleteById(it.id)
                Toast.makeText(context, "Carro ${it.id} removido dos favoritos! :(", Toast.LENGTH_LONG).show()
            }
        }
    }

    fun checkInternet(context:Context?):Boolean{
        val connectivityManager = context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            val network = connectivityManager.activeNetwork?:return false
            val activeNetwork=connectivityManager.getNetworkCapabilities(network)?:return false
            return when{
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)->true
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)->true
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_BLUETOOTH)->true
else -> false
            }

        }
        else{
            val networkInfo = connectivityManager.activeNetworkInfo?:return false
            return networkInfo.isConnected;
        }
    }


    //deprecated - only for example, use retrofit
    inner class GetCarInformations : AsyncTask<String, String, String>(){
        override fun onPreExecute() {
            super.onPreExecute()
        }

        override fun doInBackground(vararg url: String?): String {
            stateLoading()
            var urlConnection: HttpURLConnection?=null
            try {
                val urlBase =   URL(url[0])

                urlConnection=urlBase.openConnection() as HttpURLConnection
                if(urlConnection.responseCode == HttpURLConnection.HTTP_OK) {


                    //var inputString = streamToString(urlConnection.inputStream)
//                publishProgress(inputString)


                    //alternativa parta não precisar da função streamToString
                    var response = urlConnection.inputStream.bufferedReader().use { it.readText() }

                    publishProgress(response)
                }
                else{Log.e("Erro","Serviço indisponível")}
            }catch (e:Exception){
                Log.e("Erro", "Erro na requisição")
            }
            finally {
                urlConnection?.disconnect()
            }
            return ""
        }
//        fun streamToString(inputStream: InputStream) : String{
//            val bufferReader = BufferedReader(InputStreamReader(inputStream))
//            var line : String
//            var result = ""
//
//
//            try {
//                do {
//                    line=bufferReader.readLine()
//                    line?.let { result+=line
//                    }
//                }while (line != null)
//            }catch (e:Exception){
//                Log.e("erro","Erro no bufferReader")
//            }
//
//            return result
//        }

        override fun onProgressUpdate(vararg values: String?) {
            //var json: JSONObject para um unico objeto no json
            //val jsonArray : JSONTokener //para um jsom com lista
            try {

//                values[0]?.let { json=JSONObject(it) }

                values[0]?.let {
                   val jsonArray=JSONTokener(it).nextValue() as JSONArray
                    for (i in 0 until jsonArray.length()){
                        val carro =         Carro(
                            id =  jsonArray.getJSONObject(i).getString("id").toInt(),
                            preco = jsonArray.getJSONObject(i).getString("preco"),
                            bateria = jsonArray.getJSONObject(i).getString("bateria"),
                            potencia = jsonArray.getJSONObject(i).getString("potencia"),
                            recarga = jsonArray.getJSONObject(i).getString("recarga"),
                            urlPhoto = jsonArray.getJSONObject(i).getString("urlPhoto"),
                            isFavorite = false
                        )

                        listaCarros.add(carro)
                        Log.d("carro ${carro.id} = ",carro.toString())
                    }
                    //setupList()
                    car_list.visibility=View.VISIBLE
                    pb_progress.visibility=View.GONE
                }
            }catch (e:Exception){
                Log.e("Erro","no progressUpdate")
            }
        }
    }
}