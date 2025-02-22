/*

    DailyData is an android app to easily create diagrams from data one has collected
    Copyright (C) 2022  Antonia Heiming, Anton Kadelbach, Arne Kuchenbecker, Merlin Opp, Robin Amman

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <https://www.gnu.org/licenses/>.

*/

package com.pseandroid2.dailydata.model.table

import com.google.gson.Gson
import com.pseandroid2.dailydata.model.User
import com.pseandroid2.dailydata.model.database.entities.RowEntity
import com.pseandroid2.dailydata.model.uielements.UIElement
import java.time.LocalDateTime

interface Table {

    fun getCell(row: Int, col: Int): Any

    fun getLayout(): TableLayout

    fun getSize(): Int

    fun getRow(row: Int): Row

    fun addRow(row: Row)

    fun deleteRow(row: Int)

    fun getColumn(col: Int): List<Any>

    fun addColumn(type: Class<Any>)

    fun deleteColumn(col: Int)

}

interface TableLayout {

    fun getSize(): Int

    fun getColumnType(col: Int): Class<Any>

    fun getUIElements(col: Int): List<UIElement>

    fun toJSON(): String

    fun fromJSON(json: String): TableLayout

}

interface Row {

    fun getAll(): List<Any>

    fun getCell(col: Int): Any

    fun getMetaData(): RowMetaData

    fun getSize(): Int

}

fun Row.toRowEntity(projectId: Int): RowEntity {
    val meta = this.getMetaData()
    val values = Gson().toJson(this.getAll())
    return RowEntity(
        projectId,
        meta.createdOn,
        meta.createdBy,
        values,
        meta.publishedOn
    )
}

data class RowMetaData(
    val createdOn: LocalDateTime,
    var publishedOn: LocalDateTime,
    val createdBy: User
)
