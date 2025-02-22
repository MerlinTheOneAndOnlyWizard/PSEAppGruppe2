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

package com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses

import android.graphics.Bitmap
import com.pseandroid2.dailydata.model.database.entities.GraphEntity
import com.pseandroid2.dailydata.model.database.entities.ProjectData
import com.pseandroid2.dailydata.repository.commandCenter.commands.IllegalOperationException

class Project(
    override var id: Long = 0,
    var isOnlineProject: Boolean = false,
    var isAdmin: Boolean = false,
    var title: String = "title missing",
    var description: String= "description missing",
    var wallpaper: String = "wallpaper missing",
    var table: List<Column> = ArrayList<Column>(),
    var data: List<Row> = ArrayList<Row>(),
    var buttons: List<Button> = ArrayList<Button>(),
    var notifications: List<Notification> = ArrayList<Notification>(),
    var graphs: List<Graph> = ArrayList<Graph>(),
    var members: List<Member> = ArrayList<Member>()
): Identifiable {
    fun update(graphEntity: GraphEntity){
        TODO("not yet implemented")
    }
    fun update(notification: com.pseandroid2.dailydata.model.notifications.Notification){
        TODO("not yet implemented")
    }
    fun update(projectData: ProjectData){
        TODO("not yet implemented")
    }
    //Todo nothing mit typ für settings ersetzen
    fun update(settings: Nothing){
        TODO("not yet implemented")
    }
    fun addRowIsPossible(): Boolean {
        TODO()
    }
    //@throws IllegalOperationException
    fun addRow(row: Row) {
        TODO()
    }
    fun deleteRowIsPossible(): Boolean {
        TODO()
    }
    //@throws IllegalOperationException
    fun deleteRow(row: Row) {
        if (row in data) {
            row.delete()
        }
        else {
            throw IllegalOperationException()
        }
    }
    fun addColumnIsPossible(): Boolean {
        TODO()
    }
    //@throws IllegalOperationException
    fun addColumn(column: Column) {
        TODO()
    }
    //@throws IllegalOperationException
    fun deleteColumn(column: Column) {
        if (column in table) {
            column.delete()
        }
        else{
            throw IllegalOperationException()
        }
    }

    override fun deleteIsPossible(): Boolean {
        TODO("Not yet implemented")
    }

    //@throws IllegalOperationException
    override fun delete() {
        TODO("Not yet implemented")
    }
    //@throws IllegalOperationException
    fun setCell (indexRow: Int, indexColumn: Int, content: String) {
        if (indexRow >= 0 && indexRow < data.size) {
            data[indexRow].setCell(indexColumn, content)
        }
        throw IllegalOperationException()
    }
    fun addMemberIsPossible(){
        TODO()
    }
    fun addMember (member: Member) {
        //Todo If bedingung schöner machen, keine Magic numbers und aussagekräftigere Exceptions werfen
        if (member !in members && members.size < 25 && isOnlineProject) {
            TODO()
        }
        else {
            throw IllegalOperationException()
        }
    }
    fun deleteMemberIsPossible() {
        TODO()
    }
    fun deleteMember(member: Member) {
        if (member in members && members.size > 1) {
            TODO()
        }
        else{
            throw IllegalOperationException()
        }
    }
    fun setAdminPossible() {
        TODO()
    }
    fun setAdmin(member: Member){
        TODO()
    }
    fun setWallpaper(image: Bitmap) {
        TODO()
    }
    fun setNotification(notification: Notification) {
        TODO()
    }
    fun deleteNotification(notification: Notification) {
        notification.delete()
    }
    fun setName(name: String) {
        TODO()
    }
    @JvmName("setDescription1")
    fun setDescription(description: String) {
        TODO()
    }
    fun publishIsPossible() {
        TODO()
    }
    fun publish() {
        TODO()
    }
    fun setButton(button: Button) {
        TODO()
    }
}