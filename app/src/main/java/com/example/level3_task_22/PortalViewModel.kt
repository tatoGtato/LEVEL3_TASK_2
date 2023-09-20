package com.example.level3_task_22

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel

class PortalViewModel : ViewModel() {

    data class Portal(
        val name: String,
        val url: String,
    )

    val portals: SnapshotStateList<Portal> = mutableStateListOf<Portal>()

    fun addPortal(newPortal: Portal) {
        portals.add(newPortal)
    }

}