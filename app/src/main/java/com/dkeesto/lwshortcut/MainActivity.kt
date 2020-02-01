/*
 * Copyright (C) 2020 Cypher Cove, LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.dkeesto.lwshortcut

import android.app.Activity
import android.app.WallpaperInfo
import android.app.WallpaperManager
import android.content.ComponentName
import android.content.Intent
import android.os.Bundle
import android.widget.Toast

class MainActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val wallpaperManager = WallpaperManager.getInstance(this)
        val info: WallpaperInfo? = wallpaperManager.wallpaperInfo
        val settings: String? = info?.settingsActivity
        when {
            info == null -> openWallpaperChooser()
            settings == null -> Toast.makeText(this, R.string.wallpaper_no_settings, Toast.LENGTH_LONG).show()
            else -> openWallpaperSettings(info.packageName, settings)
        }
        finish()
    }

    private fun openWallpaperChooser() {
        startActivity(Intent(WallpaperManager.ACTION_LIVE_WALLPAPER_CHOOSER))
    }

    private fun openWallpaperSettings(wallpaperPackage: String, settingsActivity: String){
        try {
            val intent = Intent(Intent.ACTION_MAIN).apply {
                component = ComponentName(wallpaperPackage, settingsActivity)
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
            }
            startActivity(intent)
        } catch (e: SecurityException){
            Toast.makeText(this, R.string.wallpaper_restricted_settings, Toast.LENGTH_LONG).show()
        }
    }

}
