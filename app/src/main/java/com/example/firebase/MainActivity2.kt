package com.example.firebase

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class MainActivity2 : AppCompatActivity() {
    private lateinit var dbRef :DatabaseReference
    private lateinit var id: TextView
    private lateinit var name: TextView
    private lateinit var price: TextView
    private lateinit var description: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main2)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        id = findViewById(R.id.id)
        name = findViewById(R.id.name)
        price = findViewById(R.id.price)
        description = findViewById(R.id.description)
        dbRef =FirebaseDatabase.getInstance().getReference("Products")
        var buttonInsert=findViewById<Button>(R.id.buttonInsert)
        buttonInsert.setOnClickListener(){
            SaveProduct()
        }

    }

    private fun SaveProduct() {

        val name1 = name.text.toString()
        val price1 = price.text.toString()
        val description1 = description.text.toString()
        val ID1 = dbRef.push().key ?: ""
        if(name1.isEmpty()){
            name.error="Please enter name"
            return
        }
        if(price1.isEmpty()){
            price.error="Please enter price"
            return
        }
        if(description1.isEmpty()){
            description.error="Please enter description"
            return
        }
        if (ID1.isNotEmpty()) {
            val product = Product(ID1, name1, price1, description1)
            dbRef.child(ID1).setValue(product).addOnCompleteListener {
                Toast.makeText(this, "Upload thành công", Toast.LENGTH_SHORT).show()
                name.setText("")
                price.setText("")
                description.setText("")

            }.addOnFailureListener {
                Toast.makeText(this, "Upload thất bại", Toast.LENGTH_SHORT).show()
            }
        }
    }
}