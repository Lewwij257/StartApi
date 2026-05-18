package com.locaspes

import com.locaspes.model.ProjectIcon
import com.locaspes.theme.R

class ProjectIconMapper {

    companion object{

        fun getIconId(projectIcon: ProjectIcon): Int{
            return when (projectIcon){
                ProjectIcon.Default -> R.drawable.img_briefcase_selected
                ProjectIcon.Ai -> R.drawable.robotics
                ProjectIcon.Iot -> R.drawable.iot
                ProjectIcon.Study -> R.drawable.icon_study
                ProjectIcon.Unity -> R.drawable.icon_unity
                ProjectIcon.Game -> R.drawable.icon_joistick
                ProjectIcon.Java -> R.drawable.icon_java
                ProjectIcon.Robotics -> R.drawable.img_robot
                ProjectIcon.Ecology -> R.drawable.ecology
                ProjectIcon.Science -> R.drawable.science
                ProjectIcon.Blockchain -> R.drawable.blockchain
                ProjectIcon.CyberSecurity -> R.drawable.cyber_security
                ProjectIcon.Design -> R.drawable.icon_brush
                ProjectIcon.Gamification -> R.drawable.gamification
                ProjectIcon.CPlusPlus -> R.drawable.icon_c_plus_plus
                ProjectIcon.Csharp -> R.drawable.icon_c_sharp
                ProjectIcon.Kotlin -> R.drawable.icon_kotlin
                ProjectIcon.Startup -> R.drawable.icon_money
                ProjectIcon.WebDev -> R.drawable.icon_website
                ProjectIcon.MobileDev -> R.drawable.mobile_dev
                ProjectIcon.Social -> R.drawable.social
                ProjectIcon.Programming -> R.drawable.wev_dev
            }
        }

    }

}