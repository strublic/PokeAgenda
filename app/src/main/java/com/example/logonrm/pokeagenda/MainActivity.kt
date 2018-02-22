package com.example.logonrm.pokeagenda

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.logonrm.pokeagenda.api.PokemonAPI
import com.example.logonrm.pokeagenda.model.Pokemon
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btPesquisar.setOnClickListener{
            pesquisarPokemon()
        }
    }

    fun pesquisarPokemon(){
        val retrofit = Retrofit.Builder()
                .baseUrl("https://pokeapi.co")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        val api = retrofit.create(PokemonAPI::class.java)

        api.buscarPokemon(etNomePokemon.text.toString().toInt())
                .enqueue(object : Callback<Pokemon> {
                    override fun onResponse(call: Call<Pokemon>?, response: Response<Pokemon>?) {
                        tvNomePokemon.setText(response?.body()?.name)
                        Picasso.with(applicationContext)
                                .load(response?.body()?.sprites?.frontDefault)
                                .into(ivPokemon);
                    }

                    override fun onFailure(call: Call<Pokemon>?, t: Throwable?) {
                        Toast.makeText(applicationContext, t?.message, Toast.LENGTH_SHORT).show()
                    }
                })
    }
}
