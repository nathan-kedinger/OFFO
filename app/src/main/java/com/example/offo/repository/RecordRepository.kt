package com.example.offo.repository

import android.content.ContentValues.TAG
import android.net.Uri
import android.util.Log
import com.example.offo.repository.RecordRepository.Singleton.databaseRef
import com.example.offo.repository.RecordRepository.Singleton.recordList
import com.example.offo.model.RecordModel
import com.example.offo.repository.RecordRepository.Singleton.firebaseStorageReferenceRecords
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storageMetadata

class RecordRepository {

    object Singleton{

    //On se connecte à au stockage de firebase
    val firebaseStorageReferenceRecords  = FirebaseStorage.getInstance("gs://offo-e8245.appspot.com/").getReference("Records")
        //val firebaseStorageReferenceRecords  = FirebaseStorage.getInstance().getReferenceFromUrl("gs://offo-e8245.appspot.com/Records")

    //se connecter à la référence record de la base de donnée
    val databaseRef = FirebaseDatabase.getInstance("https://offo-e8245-default-rtdb.europe-west1.firebasedatabase.app/").getReference("records")

    //créer une liste qui contient les records
    val recordList = arrayListOf<RecordModel>()

    }

    fun updateData(callback: ()-> Unit){//permet de faire en sorte que les données continuent de se charger après l'appel de la page
        //absorber les données depuis la databaseRef vers la liste de records
        databaseRef.addValueEventListener(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {// les data snapshot vont chercher les données. elles doivent être exactement les même dans la base que dans le fichier model
                //retire les anciens records
                recordList.clear()

                //On récolte la liste
                for (dataSnapshot in snapshot.children) {//on récupère toutes les valeurs
                    val record = dataSnapshot.getValue(RecordModel::class.java)//on récupère la structure de l'objet

                    //verifier que le record n'est pas nul
                    if (record != null) {
                        //ajouter le record à la liste
                        recordList.add(record)

                    }
                }
                 callback()//on attend le chargement avant de lancer la page
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w(TAG, "erreur de lecture des valeurs", error.toException())
            }

        })
    }
    //mettre à jour un objet record en base de donnée

    /*        val firebaseStorage : FirebaseStorage = FirebaseStorage.getInstance("gs://offo-e8245.appspot.com/")
            val storageReferenceRecords: StorageReference = firebaseStorage.getReference("Records/Users")
            val recordRef : StorageReference = storageReferenceRecords.child("/$fileName.mp4")
            val metadata = storageMetadata {*/

    fun uploadRecordFile(uri: Uri, fileName : String){
            val recordRef : StorageReference = firebaseStorageReferenceRecords.child("/$fileName.mp4")
            val metadata = storageMetadata {
                contentType = "audio/*"
            }

            val uploadTask = recordRef.putFile(uri, metadata)

            uploadTask.addOnPausedListener {
                Log.d(TAG, "Upload is paused")
            }.addOnFailureListener {
                // Handle unsuccessful uploads
            }.addOnSuccessListener{
                //if the upload is successfull hiding the progress dialog and display success toast
                //Toast.makeText(AudioRecord(), "téléchargé", Toast.LENGTH_LONG).show()
            }

    }

    fun getRecords(query: String, callback: () -> Unit){
        //absorber les données depuis la databaseRef vers la liste de records
        databaseRef.orderByChild("title").startAt(query).addValueEventListener(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {// les data snapshot vont chercher les données. elles doivent être exactement les même dans la base que dans le fichier model
                //retire les anciens records
                recordList.clear()

                //On récolte la liste
                for (dataSnapshot in snapshot.children) {//on récupère toutes les valeurs
                    val record = dataSnapshot.getValue(RecordModel::class.java)//on récupère la structure de l'objet

                    //verifier que le record n'est pas nul
                    if (record != null) {
                        //ajouter le record à la liste
                        recordList.add(record)

                    }
                }
                callback()//on attend le chargement avant de lancer la page
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w(TAG, "erreur de lecture des valeurs", error.toException())
            }

        })
    }

    fun getRecordPath(fileName: String) : StorageReference{

        var recordPath = firebaseStorageReferenceRecords.child("$fileName.mp4")
        //gs://offo-e8245.appspot.com/Records


    return recordPath
    }

    fun updateRecord(record: RecordModel){
        databaseRef.child(record.id).setValue(record)
    }

    //créer un record
    fun saveRecordDataIntDatabase(record: RecordModel){
        databaseRef.child(record.id).setValue(record)
    }

    //supprimer un élément de la base
    fun deleteRecord(record: RecordModel) = databaseRef.child(record.id).removeValue()

}