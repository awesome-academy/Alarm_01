package com.asterisk.tuandao.alarmstudy.data.dao

<<<<<<< 4b837c4ea078e652f19d77bdf0c6a3e86833b1ca
import com.asterisk.tuandao.alarmstudy.data.model.Alarm

interface AlarmDao {
    fun getAlarms(): List<Alarm>
=======

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.asterisk.tuandao.alarmstudy.data.AppDatabase
import com.asterisk.tuandao.alarmstudy.data.entry.AlarmEntry
import com.asterisk.tuandao.alarmstudy.data.model.Alarm
import com.asterisk.tuandao.alarmstudy.util.AlarmDatabaseUtils

class AlarmDao(
    val context: Context,
    val factory: SQLiteDatabase.CursorFactory?,
    val DATABASE_NAME: String,
    val DATABASE_VERSION: Int
) : AppDatabase(context, factory, DATABASE_NAME, DATABASE_VERSION) {

    override fun getAlarms(): List<Alarm> {
        val db = this.readableDatabase
        val cursor = db.rawQuery(SELETECT_ALL_AlARMS_QUERY, null)
        cursor.close()
        return AlarmDatabaseUtils.toList(cursor)
    }

    companion object {
        private const val SELETECT_ALL_AlARMS_QUERY = "SELECT * FROM " + AlarmEntry.TABLE_NAME
    }
>>>>>>> build database alarm
}
