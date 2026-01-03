package com.together.study.main

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navOptions
import com.together.study.calendar.maincalendar.navigation.calendarGraph
import com.together.study.calendar.maincalendar.navigation.navigateToCategoryDetail
import com.together.study.common.type.study.StudyUpdateType
import com.together.study.main.component.MainBottomBar
import com.together.study.planner.navigation.plannerGraph
import com.together.study.search.navigation.navigateToUnivSearch
import com.together.study.search.navigation.univSearchGraph
import com.together.study.study.navigation.navigateToStudy
import com.together.study.study.navigation.navigateToStudySearch
import com.together.study.study.navigation.studyGraph
import com.together.study.study.type.StudyRole
import com.together.study.studydetail.navigation.navigateToStudyDetail
import com.together.study.studydetail.navigation.studyDetailGraph
import com.together.study.studymember.navigation.navigateToMemberListScreen
import com.together.study.studymember.navigation.studyMemberGraph
import com.together.study.studysettings.navigation.MemberSettings
import com.together.study.studysettings.navigation.navigateToLeaderSettingsScreen
import com.together.study.studysettings.navigation.navigateToMemberSettingsScreen
import com.together.study.studysettings.navigation.studySettingsGraph
import com.together.study.studyupdate.navigation.navigateToStudyUpdate
import com.together.study.studyupdate.navigation.navigateToStudyUpdateDone
import com.together.study.studyupdate.navigation.studyUpdateGraph
import kotlinx.collections.immutable.toImmutableList

@Composable
fun MainScreen(
    navigator: MainNavigator = rememberMainNavigator(),
) {
    Scaffold(
        bottomBar = {
            MainBottomBar(
                isVisible = navigator.showBottomBar(),
                tabs = MainTab.entries.toImmutableList(),
                currentTab = navigator.currentTab,
                onTabSelected = navigator::navigate
            )
        },
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            MainNavHost(
                navigator = navigator,
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Composable
private fun MainNavHost(
    navigator: MainNavigator,
    modifier: Modifier = Modifier,
) {
    NavHost(
        enterTransition = {
            EnterTransition.None
        },
        exitTransition = {
            ExitTransition.None
        },
        popEnterTransition = {
            EnterTransition.None
        },
        popExitTransition = {
            ExitTransition.None
        },
        navController = navigator.navController,
        startDestination = navigator.startDestination
    ) {
        calendarGraph(
            navigateToUp = navigator.navController::popBackStack,
            navigateToUnivSearch = navigator.navController::navigateToUnivSearch,
            navigateToCategoryDetail = navigator.navController::navigateToCategoryDetail,
            modifier = modifier,
        )

        univSearchGraph(
            navigateUp = { navigator.navigateUp() },
            modifier = modifier
        )

        studyGraph(
            navigateToUp = navigator::navigateUp,
            navigateToStudyUpdate = { isChallenge ->
                navigator.navController.navigateToStudyUpdate(isChallenge = isChallenge)
            },
            navigateToStudySearch = navigator.navController::navigateToStudySearch,
            navigateToStudyDetail = navigator.navController::navigateToStudyDetail,
            modifier = modifier,
        )

        studyDetailGraph(
            navigateToUp = navigator::navigateUp,
            navigateToStudySettings = { id, role ->
                when (role) {
                    StudyRole.LEADER -> navigator.navController.navigateToLeaderSettingsScreen(id)
                    StudyRole.MEMBER -> navigator.navController.navigateToMemberSettingsScreen(id)
                }
            },
            modifier = modifier,
        )

        studyUpdateGraph(
            navigateToUp = navigator::navigateUp,
            navigateToStudyUpdateDone = { studyId, studyName, studyIntroduce, studyCategory, studyImageUri, studyPassword, memberCount, isChallenge, selectedStudyTime, updateType ->
                navigator.navController.navigateToStudyUpdateDone(
                    studyId = studyId,
                    studyName = studyName,
                    studyIntroduce = studyIntroduce,
                    studyCategory = studyCategory,
                    studyImageUri = studyImageUri,
                    studyPassword = studyPassword,
                    memberCount = memberCount,
                    isChallenge = isChallenge,
                    selectedStudyTime = selectedStudyTime,
                    updateType = updateType
                )
            },
            navController = navigator.navController,
            modifier = modifier,
        )

        studyMemberGraph(
            navigateToUp = navigator::navigateUp,
            navigateToMemberSettings = { id ->
                navigator.navController.navigateToMemberSettingsScreen(
                    studyId = id,
                    navOptions = navOptions {
                        popUpTo(MemberSettings) { inclusive = true }
                        launchSingleTop = true
                    }
                )
            },
            navController = navigator.navController,
            modifier = modifier,
        )

        studySettingsGraph(
            navigateToUp = navigator::navigateUp,
            navigateToStudyMain = navigator.navController::navigateToStudy,
            navigateToStudyUpdate = { studyId, updateType: StudyUpdateType ->
                navigator.navController.navigateToStudyUpdate(
                    studyId = studyId,
                    updateType = updateType,
                    isChallenge = false
                )
            },
            navigateToStudyMemberEdit = navigator.navController::navigateToMemberListScreen,
            navController = navigator.navController,
            modifier = modifier,
        )

        plannerGraph(
            navigateToUp = navigator.navController::popBackStack,
            modifier = modifier,
        )

        composable<Dummy> {
            // TODO: Dummy 추가
        }
    }
}
