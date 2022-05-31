package com.example.fizikaforall.db_Projects

import com.example.fizikaforall.manual.ManualProject
import kotlinx.coroutines.flow.Flow

interface ProjectInterface {

        /**
         * Get the list of boxes.
         * @param onlyActive if set to `true` then only active boxes are emitted.
         */
        suspend fun getProjects(onlyActive: Boolean = false): Flow<List<ManualProject>>

        /**
         * Mark the specified box as active. Only active boxes are displayed in dashboard screen.
         * @throws StorageException
         */
        ///suspend fun activateBox(box: Box)

        /**
         * Mark the specified box as inactive. Inactive boxes are not displayed in dashboard screen.
         * @throws StorageException
         */
        //suspend fun deactivateBox(box: Box)

    }