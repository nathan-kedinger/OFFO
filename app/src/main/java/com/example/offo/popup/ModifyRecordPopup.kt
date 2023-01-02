package com.example.offo.popup

import android.app.Dialog
import android.os.Bundle
import android.view.Window
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import com.example.offo.R
import com.example.offo.adapter.RecordAdapter
import com.example.offo.model.RecordModel
import com.example.offo.repository.RecordRepository

class ModifyRecordPopup(
    private val adapter: RecordAdapter,
    private val currentRecord: RecordModel
    ) : Dialog(adapter.context){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE) // on évite de générer un titre puisqu'on en a déjà un
        setContentView(R.layout.popup_modify_my_record) // On injecte le layout
        setupComponents()
        setupCloseButton()
        setupDeleteButton()
    }

    private fun setupDeleteButton() {
        findViewById<Button>(R.id.modify_my_record_delete).setOnClickListener{
            //supprimer le record de la base
            val repo = RecordRepository()
            repo.deleteRecord(currentRecord)
            dismiss()
        }
    }

    private fun setupCloseButton() {
        findViewById<ImageButton>(R.id.modify_my_record_close).setOnClickListener{
            //fermer la fenêtre
            dismiss()
        }
    }

    private fun setupComponents() {
        //on modifie l'item
        findViewById<TextView>(R.id.modify_my_record_title).text = currentRecord.title
    }

}