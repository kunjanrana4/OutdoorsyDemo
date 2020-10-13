package com.kunjan.outdoorsydemo

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kunjan.outdoorsydemo.pojo.Data
import com.kunjan.outdoorsydemo.pojo.Rentals
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers


class MainActivity : AppCompatActivity() {
    lateinit var rentalList: MutableList<Data>
    private lateinit var recyclerView:RecyclerView
    private lateinit var rentalAdapter:RentalAdapter
    private val getApiService by lazy {
        ApiService.create()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recyclerView = findViewById(R.id.recycler_rental)
        val compositeDisposable = CompositeDisposable()
        val userObservable = getApiService.getAllRentals()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ result -> setRecyclerView(result!!) },
                { error -> showError(error) }
            )
        compositeDisposable.add(userObservable)

    }

    private fun setRecyclerView(result: Rentals) {
        rentalList = result.data.toMutableList()
        rentalAdapter = RentalAdapter(rentalList)
        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = rentalAdapter
    }

    private fun showError(message: Any) {
        Toast.makeText(this, message.toString(), Toast.LENGTH_LONG).show()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)
        val searchItem: MenuItem = menu.findItem(R.id.action_search)
        val searchView: SearchView = searchItem.actionView as SearchView
        searchView.setOnQueryTextListener(object:SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                rentalAdapter.filter.filter(newText)
                return false
            }
        })
        return true
    }
}