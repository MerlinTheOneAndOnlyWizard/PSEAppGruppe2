package com.pseandroid2.dailydata.remoteDataSource.queue.observerLogic

import com.pseandroid2.dailydata.remoteDataSource.queue.FetchRequestQueue
import org.junit.Assert
import org.junit.Test

class FetchRequestObserverTests_AddTenObserver {
    @Test
    fun addOneObserver() {
        var toUpdateObjects = mutableListOf<UpdatedByObserver_ForTesting>()

        // Erstelle Observer
        val observers: MutableList<FetchRequestQueueObserver_ForTesting> = mutableListOf<FetchRequestQueueObserver_ForTesting>()
        for (idx in 1..10) {
            val toUpdate = UpdatedByObserver_ForTesting()
            observers.add(FetchRequestQueueObserver_ForTesting(toUpdate))
            toUpdateObjects.add(toUpdate)
        }

        // Füge Observer hinzu
        var fetchRequestQueue = FetchRequestQueue()
        observers.forEach {
            fetchRequestQueue.registerObserver(it)
        }

        // Prüfe ob update() bei Observer ausgeführt wird:
        toUpdateObjects.forEach {
            Assert.assertEquals(it.isUpdated(), false)
        }

        Assert.assertEquals(fetchRequestQueue.getQueueLength(), 0)
        var fetchRequest = "Fetch Request: 1"
        fetchRequestQueue.addFetchRequest(fetchRequest)
        Assert.assertEquals(fetchRequestQueue.getQueueLength(), 1)

        // Prüfe ob alle toUpdateObjects geupdated wurden
        toUpdateObjects.forEach {
            Assert.assertEquals(it.isUpdated(), true)
        }

    }
}