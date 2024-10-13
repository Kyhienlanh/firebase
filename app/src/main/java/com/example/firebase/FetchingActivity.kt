package com.example.firebase

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.util.Pools
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class FetchingActivity : AppCompatActivity() {
    private lateinit var ds:ArrayList<Product>
    private lateinit var dbRef:DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_fetching)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        var recyclerView=findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager=LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)
        ds= arrayListOf<Product>()
        GetThongTin()
    }

    private fun GetThongTin() {
        var recyclerView=findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.visibility= View.GONE
        var textView=findViewById<TextView>(R.id.textView)
        textView.visibility=View.VISIBLE
        dbRef=FirebaseDatabase.getInstance().getReference("Products")
        dbRef.addValueEventListener(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
               ds.clear()
                if(snapshot.exists()){
                    for(product in snapshot.children){
                        val data=product.getValue(Product::class.java)
                        ds.add(data!!)
                    }
                    val mAdapter=EmpAdapter(ds)
                    recyclerView.adapter=mAdapter
                    //code lang nghe su kien
                    mAdapter.setOnItemClickListener(object :EmpAdapter.onItemClickListener{
                        override fun onItemClick(position: Int) {
                            val intent= Intent(this@FetchingActivity,DetailMainActivity3::class.java)
                            intent.putExtra("id", ds[position].id)
                            intent.putExtra("name", ds[position].name)
                            intent.putExtra("price", ds[position].price)
                            intent.putExtra("description", ds[position].description)
                            startActivity(intent)

                        }
                    })
                    recyclerView.visibility= View.VISIBLE
                    textView.visibility=View.GONE
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })

    }
}