package com.timeforpublic.navigation

object Routes {
    const val SPLASH = "splash"
    const val LOGIN = "login"
    const val CITIZEN_HOME = "citizen_home"
    const val SCHEMES = "schemes"
    const val SCHEME_DETAIL = "scheme_detail/{schemeId}"
    const val DOCUMENTS = "documents"
    const val OFFICES = "offices"
    const val OFFICE_DETAIL = "office_detail/{officeId}"
    const val AI_ASSISTANT = "ai_assistant"
    const val OFFICER_DASHBOARD = "officer_dashboard"

    fun schemeDetail(schemeId: String): String = "scheme_detail/$schemeId"
    fun officeDetail(officeId: String): String = "office_detail/$officeId"
}
