package com.example.firebase

import android.content.ClipDescription
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.database.FirebaseDatabase

class DetailMainActivity3 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_detail_main3)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
         setValue()
        var ButtonDelete=findViewById<Button>(R.id.button_delete)
        ButtonDelete.setOnClickListener(){
            deleteReCord(intent.getStringExtra("id").toString())
        }
        var ButtonUpdate=findViewById<Button>(R.id.button_update)
        ButtonUpdate.setOnClickListener(){
            openUpdateDialog(
                intent.getStringExtra("id").toString() ,
                intent.getStringExtra("name").toString(),
                intent.getStringExtra("price").toString(),
                intent.getStringExtra("description").toString()
            )
        }



    }

    private fun openUpdateDialog(id: String, name: String,price:String,description:String) {
        // Tạo AlertDialog Builder
        val mDialog = AlertDialog.Builder(this)


        val inflater = layoutInflater
        val mDialogView = inflater.inflate(R.layout.update, null)

        // Thiết lập các giá trị cho dialog view (nếu cần)
        val itemIdTextView = mDialogView.findViewById<TextView>(R.id.item_id)
        val itemNameEditText = mDialogView.findViewById<EditText>(R.id.item_name)
        val itemPriceEditText = mDialogView.findViewById<EditText>(R.id.item_price)
        val itemDescriptionEditText = mDialogView.findViewById<EditText>(R.id.item_description)

        itemIdTextView.text = id
        itemNameEditText.setText(name)
        itemPriceEditText.setText(price)
        itemDescriptionEditText.setText(description)


        mDialog.setView(mDialogView)


        mDialog.setTitle("Update Item")
        mDialog.setPositiveButton("Update") { dialog, which ->
            val newName = itemNameEditText.text.toString()
            val newPrice = itemPriceEditText.text.toString()
            val newDescription = itemDescriptionEditText.text.toString()

            
            updateItem(id, newName, newPrice, newDescription)
        }
        mDialog.setNegativeButton("Cancel") { dialog, which ->
            dialog.dismiss()
        }

        mDialog.create().show()
    }
    private fun updateItem(id: String, name: String, price: String, description: String) {
        val dbRef = FirebaseDatabase.getInstance().getReference("Products").child(id)
        val product = Product(id, name, price, description)

        dbRef.setValue(product).addOnSuccessListener {
            Toast.makeText(this, "Cập nhật thành công!", Toast.LENGTH_SHORT).show()
        }.addOnFailureListener { err ->
            Toast.makeText(this, "Lỗi: ${err.message}", Toast.LENGTH_SHORT).show()
        }
    }

    private fun deleteReCord(id:String) {
        val dbRef=FirebaseDatabase.getInstance().getReference("Products").child((id))
        var mtask=dbRef.removeValue()
        mtask.addOnSuccessListener {
            Toast.makeText(this,"DATA da duoc xoa",Toast.LENGTH_SHORT).show()
            val intent=Intent(this,FetchingActivity::class.java)
            finish()
            startActivity(intent)
        }.addOnFailureListener { err->
            Toast.makeText(this,"Lỗi",Toast.LENGTH_SHORT).show()
        }
    }

    private fun setValue() {
        var item_id=findViewById<TextView>(R.id.item_id)
        var name=findViewById<TextView>(R.id.item_name)
        var price=findViewById<TextView>(R.id.item_price)
        var description=findViewById<TextView>(R.id.item_description)
        item_id.text=intent.getStringExtra("id")
        name.text=intent.getStringExtra("name")
        price.text=intent.getStringExtra("price")
        description.text=intent.getStringExtra("description")

    }
}