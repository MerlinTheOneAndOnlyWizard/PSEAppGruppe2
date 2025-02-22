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

package com.pseandroid2.dailydata.model.database.daos


import androidx.room.withTransaction
import com.pseandroid2.dailydata.model.Graph
import com.pseandroid2.dailydata.model.Project
import com.pseandroid2.dailydata.model.ProjectSkeleton
import com.pseandroid2.dailydata.model.ProjectTemplate
import com.pseandroid2.dailydata.model.User
import com.pseandroid2.dailydata.model.database.AppDataBase
import com.pseandroid2.dailydata.model.database.entities.ProjectEntity
import com.pseandroid2.dailydata.model.database.entities.ProjectSkeletonEntity
import com.pseandroid2.dailydata.model.notifications.Notification
import com.pseandroid2.dailydata.model.table.TableLayout
import com.pseandroid2.dailydata.model.uielements.UIElement
import com.pseandroid2.dailydata.util.SortedIntListUtil
import java.util.SortedSet
import java.util.TreeSet

class ProjectCDManager private constructor(
    private val projectDAO: ProjectDataDAO,
    private val uiDAO: UIElementDAO,
    private val notifDAO: NotificationsDAO,
    private val graphManager: GraphCDManager,
    private val db: AppDataBase
) {

    private val existingIds: SortedSet<Int> = TreeSet()

    /**
     * Inserts a Project into the Database. This method does so as a transaction, i.e. it will roll
     * back any changes if an exception is thrown at any point.
     *
     * @param project The project that is to be inserted into the database
     * @return The same project that was given as a parameter, however if there were id changes
     * necessary to insert it into the database, these are reflected here.
     */
    suspend fun insertProject(project: Project): Project = db.withTransaction() {
        val newID: Int = insertProjectEntity(project)
        project.getProjectSkeleton().setID(newID)

        for (graph: Graph in project.getProjectSkeleton().getGraphs()) {
            val newGraphId: Int = graphManager.insertGraph(newID, graph)
            graph.id = newGraphId
        }

        for (notif: Notification in project.getProjectSkeleton().getNotifications()) {
            val newNotifId: Int = notifDAO.insertNotification(newID, notif)
            notif.id = newNotifId
        }

        val layout: TableLayout = project.getTable().getLayout()
        for (col: Int in 0 until layout.getSize()) {
            for (uiElement: UIElement in layout.getUIElements(col)) {
                val newUiId: Int = uiDAO.insertUIElement(newID, col, uiElement)
                uiElement.id = newUiId
            }
        }

        for (user: User in project.getUsers()) {
            projectDAO.addUser(newID, user)
        }

        return@withTransaction project
    }

    fun deleteProject(project: Project) {
        //TODO
    }

    fun insertProjectTemplate(template: ProjectTemplate): Int {
        //TODO
        return 0
    }

    fun deleteProjectTemplate(template: ProjectTemplate) {
        //TODO
    }

    private fun isTemplate(id: Int): Boolean {
        //TODO
        return false
    }

    private fun getNextId(): Int {
        return SortedIntListUtil.getFirstMissingInt(ArrayList(existingIds))
    }

    private fun createSkeleton(project: Project): ProjectSkeletonEntity {
        val skeleton: ProjectSkeleton = project.getProjectSkeleton()
        val name: String = skeleton.getName()
        val desc: String = skeleton.getDescription()
        val wallpaper: String = skeleton.getWallpaperPath()
        val layout: String =
            project.getTable().getLayout().toJSON()
        return ProjectSkeletonEntity(name, desc, wallpaper, layout)
    }

    private fun insertProjectEntity(project: Project): Int {
        val skeleton: ProjectSkeletonEntity = createSkeleton(project)
        val admin: User = project.getAdmin()
        val onlineId: Long = project.getOnlineId()
        val id = getNextId()
        val entity = ProjectEntity(id, skeleton, admin, onlineId)

        //projectDAO.insertProjectEntity(entity)

        return id
    }

}