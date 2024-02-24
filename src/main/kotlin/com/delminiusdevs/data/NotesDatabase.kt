package com.delminiusdevs.data

import com.delminiusdevs.data.model.Note
import com.delminiusdevs.data.model.User
import com.delminiusdevs.util.checkHashForPassword
import com.mongodb.client.model.Filters
import com.mongodb.client.model.Filters.eq
import com.mongodb.client.model.Filters.not
import com.mongodb.client.model.Updates
import com.mongodb.kotlin.client.coroutine.MongoClient
import com.mongodb.kotlin.client.coroutine.MongoDatabase
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.toList


class NotesDatabase : NoteDAO {

    private val connectString = if (System.getenv("MONGODB_URI") != null) {
        System.getenv("MONGODB_URI")
    } else {
        "mongodb+srv://<usename>:<password>@cluster0.sq3aiau.mongodb.net/?retryWrites=true&w=majority"
    }
    private val databaseName = "note_database"

    private val client = MongoClient.create(connectionString = connectString)
    private val database = client.getDatabase(databaseName = databaseName)

    private val users = database.getCollection<User>("users")
    private val notes = database.getCollection<Note>("notes")


    override suspend fun checkPasswordForEmail(email: String, password: String): Boolean {
        val actualPassword =
            users.find(Filters.eq(User::email.name, email)).firstOrNull()?.password ?: return false

        return checkHashForPassword(password, actualPassword)
        client.close()
    }

    override suspend fun checkIfEmailExist(email: String): Boolean {
        return users.find(Filters.eq(User::email.name, email)).firstOrNull() != null
        client.close()
    }

    override suspend fun registerUser(user: User): Boolean {
        return users.insertOne(user).wasAcknowledged()
        client.close()
    }

    override suspend fun getUserIdWithEmail(email: String): String {
        return users.find(Filters.eq(User::email.name, email)).firstOrNull()?.id ?: ""
        client.close()
    }

    override suspend fun getAllNotesOfUser(userId: String): List<Note> {
        return notes.find(Filters.eq(Note::owner.name, userId)).toList()
        client.close()
    }

    override suspend fun insertNote(note: Note): Boolean {
        return notes.insertOne(note).wasAcknowledged()
        client.close()
    }

    override suspend fun updateNote(note: Note): Boolean {
        notes.updateOne(Filters.eq(note.id), Updates.set(Note::title.name, note.title))
        notes.updateOne(Filters.eq(note.id), Updates.set(Note::text.name, note.text))
        return notes.updateOne(Filters.eq(note.id), Updates.set(Note::timestamp.name, System.currentTimeMillis()))
            .wasAcknowledged()
        client.close()
    }

    override suspend fun checkIfNoteExist(noteId: String): Boolean {
        return notes.find(Filters.eq(noteId)) != null
        client.close()
    }

    override suspend fun isNoteOwnedBy(noteId: String, userId: String): Boolean {
        return notes.find(Filters.eq(noteId))?.firstOrNull()?.owner == userId
        client.close()
    }

    override suspend fun deleteNote(noteId: String): Boolean {
        return notes.deleteOne(Filters.eq(noteId)).wasAcknowledged()
        client.close()
    }


//    suspend fun setupConnection(
//        databaseName: String = "note_database",
//        connectionEnvVariable: String = "MONGODB_URI"
//    ): MongoDatabase? {
//        val connectString = if (System.getenv(connectionEnvVariable) != null) {
//            System.getenv(connectionEnvVariable)
//        } else {
//            "mongodb+srv://<usename>:<password>@cluster0.sq3aiau.mongodb.net/?retryWrites=true&w=majority"
//        }
//
//        val client = MongoClient.create(connectionString = connectString)
//        val database = client.getDatabase(databaseName = databaseName)
//
//        return try {
//            // Send a ping to confirm a successful connection
//            val command = Document("ping", BsonInt64(1))
//            database.runCommand(command)
//            println("Pinged your deployment. You successfully connected to MongoDB!")
//            database
//        } catch (me: MongoException) {
//            System.err.println(me)
//            null
//        }
//    }
}