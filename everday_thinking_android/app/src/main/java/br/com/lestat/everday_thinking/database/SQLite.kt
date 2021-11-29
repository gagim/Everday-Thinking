package br.com.lestat.everday_thinking.database

import android.database.sqlite.SQLiteOpenHelper
import android.database.sqlite.SQLiteDatabase
import br.com.lestat.everday_thinking.model.Usuario
import android.content.ContentValues
import android.annotation.SuppressLint
import android.content.Context
import br.com.lestat.everday_thinking.model.Comentario
import java.util.ArrayList

class SQLite(context: Context?) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    private val CREATE_USER_TABLE_USER = ("CREATE TABLE " + TABLE_USER + "("
            + COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_USER_NAME + " TEXT,"
            + COLUMN_USER_EMAIL + " TEXT," + COLUMN_USER_PASSWORD + " TEXT" + ")")
    private val DROP_USER_TABLE_USER = "DROP TABLE IF EXISTS $TABLE_USER"
    private val columns = arrayOf(
        COLUMN_USER_ID,
        COLUMN_USER_EMAIL,
        COLUMN_USER_NAME,
        COLUMN_USER_PASSWORD
    )
    private val CREATE_TABLE_COMMENT = ("CREATE TABLE " + TABLE_COMMENT + "("
            + COLUMN_COMMENT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_USER_NAME + " TEXT,"
            + COLUMN_COMMENT_USER_ID + " TEXT,"
            + COLUMN_COMMENT + " TEXT(280),"
            + COLUMN_DATA + " TEXT" + ")")
    private val DROP_TABLE_COMMENT = "DROP TABLE IF EXISTS $TABLE_COMMENT"
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(CREATE_USER_TABLE_USER)
        db.execSQL(CREATE_TABLE_COMMENT)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL(DROP_USER_TABLE_USER)
        db.execSQL(DROP_TABLE_COMMENT)
        onCreate(db)
    }

    //Usuarios
    fun addUser(usuario: Usuario) {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(COLUMN_USER_NAME, usuario.nome)
        values.put(COLUMN_USER_EMAIL, usuario.email)
        values.put(COLUMN_USER_PASSWORD, usuario.senha)
        db.insert(TABLE_USER, null, values)
        db.close()
    }

    @SuppressLint("Range")
    fun getUser(email: String): Usuario {
        val usuario = Usuario()
        val db = this.writableDatabase
        val selection = "$COLUMN_USER_EMAIL = ?"
        val selectionArgs = arrayOf(email)
        val cursor = db.query(
            TABLE_USER,
            columns,
            selection,
            selectionArgs,
            null,
            null,
            null
        )
        if (cursor.moveToFirst()) {
            do {
                usuario.id = cursor.getString(cursor.getColumnIndex(COLUMN_USER_ID)).toInt()
                usuario.nome = cursor.getString(cursor.getColumnIndex(COLUMN_USER_NAME))
                usuario.email = cursor.getString(cursor.getColumnIndex(COLUMN_USER_EMAIL))
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return usuario
    }

    fun checkUser(email: String): Boolean {
        val columns = arrayOf(
            COLUMN_USER_ID
        )
        val db = this.readableDatabase
        val selection = "$COLUMN_USER_EMAIL = ?"
        val selectionArgs = arrayOf(email)
        val cursor = db.query(
            TABLE_USER,
            columns,
            selection,
            selectionArgs,
            null,
            null,
            null
        )
        val cursorCount = cursor.count
        cursor.close()
        db.close()
        return cursorCount > 0
    }

    fun checkUser(email: String, password: String): Boolean {
        val columns = arrayOf(
            COLUMN_USER_ID
        )
        val db = this.readableDatabase
        val selection = "$COLUMN_USER_EMAIL = ? AND $COLUMN_USER_PASSWORD = ?"
        val selectionArgs = arrayOf(email, password)
        val cursor = db.query(
            TABLE_USER,
            columns,
            selection,
            selectionArgs,
            null,
            null,
            null
        )
        val cursorCount = cursor.count
        cursor.close()
        db.close()
        return cursorCount > 0
    }

    //comentarios
    fun addComment(comentario: Comentario) {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(COLUMN_USER_NAME, comentario.nome)
        values.put(COLUMN_COMMENT_USER_ID, comentario.idUsuario)
        values.put(COLUMN_COMMENT, comentario.comentario)
        values.put(COLUMN_DATA, comentario.data)
        db.insert(TABLE_COMMENT, null, values)
        db.close()
    }

    @get:SuppressLint("Range")
    val allComments: List<Comentario>
        get() {
            val columns = arrayOf(
                COLUMN_COMMENT_ID,
                COLUMN_COMMENT_USER_ID,
                COLUMN_USER_NAME,
                COLUMN_DATA,
                COLUMN_COMMENT
            )
            val sortOrder = "$COLUMN_DATA DESC"
            val comentariosList: MutableList<Comentario> = ArrayList()
            val db = this.readableDatabase
            val cursor = db.query(
                TABLE_COMMENT,
                columns,
                null,
                null,
                null,
                null,
                sortOrder
            )
            if (cursor.moveToFirst()) {
                do {
                    val comentario = Comentario()
                    comentario.id = cursor.getInt(cursor.getColumnIndex(COLUMN_COMMENT_ID))
                    comentario.idUsuario =
                        cursor.getString(cursor.getColumnIndex(COLUMN_COMMENT_USER_ID)).toInt()
                    comentario.nome = cursor.getString(cursor.getColumnIndex(COLUMN_USER_NAME))
                    comentario.data = cursor.getString(cursor.getColumnIndex(COLUMN_DATA))
                    comentario.comentario = cursor.getString(cursor.getColumnIndex(COLUMN_COMMENT))
                    comentariosList.add(comentario)
                } while (cursor.moveToNext())
            }
            cursor.close()
            db.close()
            return comentariosList
        }

    fun updateComment(comentario: Comentario) {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(COLUMN_DATA, comentario.data)
        values.put(COLUMN_COMMENT, comentario.comentario)
        db.update(
            TABLE_COMMENT,
            values,
            "$COLUMN_COMMENT_ID = ?",
            arrayOf(comentario.id.toString())
        )
        db.close()
    }

    fun deleteComment(comentario: Comentario) {
        val db = this.writableDatabase
        db.delete(TABLE_COMMENT, "$COLUMN_COMMENT_ID = ?", arrayOf(comentario.id.toString()))
        db.close()
    }

    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "EverdayThinking.db"
        private const val COLUMN_USER_ID = "user_id"
        private const val COLUMN_USER_NAME = "user_name"

        //usuario
        private const val TABLE_USER = "user"
        private const val COLUMN_USER_EMAIL = "user_email"
        private const val COLUMN_USER_PASSWORD = "user_password"

        //cometarios
        private const val TABLE_COMMENT = "comment_table"
        private const val COLUMN_COMMENT_ID = "id_comment"
        private const val COLUMN_COMMENT_USER_ID = "user_id"
        private const val COLUMN_COMMENT = "comment"
        private const val COLUMN_DATA = "data"
    }
}