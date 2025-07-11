import android.content.res.Resources

sealed class ProjectType(
    val iconId: Int,
    val russianName: String
) {
    data object Game : ProjectType(
        iconId = com.locaspes.t
        russianName = "Игры"
    )

    data object Web : ProjectType(
        iconId = R.drawable.ic_web,
        russianName = "Веб-разработка"
    )

    data object MobileApp : ProjectType(
        iconId = R.drawable.ic_mobile,
        russianName = "Мобильные приложения"
    )

    data object AI : ProjectType(
        iconId = R.drawable.ic_ai,
        russianName = "Искусственный интеллект"
    )

    data object Startup : ProjectType(
        iconId = R.drawable.ic_startup,
        russianName = "Стартапы"
    )

    data object Design : ProjectType(
        iconId = R.drawable.ic_design,
        russianName = "Дизайн"
    )

    data object Education : ProjectType(
        iconId = R.drawable.ic_education,
        russianName = "Образование"
    )

    data object Social : ProjectType(
        iconId = R.drawable.ic_social,
        russianName = "Социальные проекты"
    )

    data object Robotics : ProjectType(
        iconId = R.drawable.ic_robotics,
        russianName = "Робототехника"
    )

    data object IoT : ProjectType(
        iconId = R.drawable.ic_iot,
        russianName = "Интернет вещей"
    )

    data object CyberSecurity : ProjectType(
        iconId = R.drawable.ic_cyber,
        russianName = "Кибербезопасность"
    )

    data object Science : ProjectType(
        iconId = R.drawable.ic_science,
        russianName = "Наука"
    )

    data object Gamification : ProjectType(
        iconId = R.drawable.ic_gamification,
        russianName = "Геймификация"
    )

    data object BlockChain : ProjectType(
        iconId = R.drawable.ic_blockchain,
        russianName = "Блокчейн"
    )

    data object Ecology : ProjectType(
        iconId = R.drawable.ic_ecology,
        russianName = "Экология"
    )

    companion object {
        val AllTypes = listOf(
            Game, Web, MobileApp, AI, Startup,
            Design, Education, Social, Robotics,
            IoT, CyberSecurity, Science,
            Gamification, BlockChain, Ecology
        )
    }
}