package com.together.study.main

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navOptions
import com.together.study.calendar.maincalendar.navigation.calendarGraph
import com.together.study.calendar.maincalendar.navigation.navigateToCalendar
import com.together.study.calendar.maincalendar.navigation.navigateToCategoryDetail
import com.together.study.chatbot.navigation.chatBotGraph
import com.together.study.common.event.TogedyUiEvent
import com.together.study.common.event.TogedyUiEventBus
import com.together.study.common.type.study.StudyUpdateType
import com.together.study.designsystem.component.dialog.TogedyBasicDialog
import com.together.study.designsystem.component.toast.LocalTogedyToast
import com.together.study.designsystem.component.toast.ToastType
import com.together.study.designsystem.component.toast.TogedyToast
import com.together.study.designsystem.theme.TogedyTheme
import com.together.study.gallery.navigation.TogedyGallery
import com.together.study.gallery.navigation.galleryGraph
import com.together.study.gallery.navigation.navigateToGallery
import com.together.study.login.navigation.Login
import com.together.study.login.navigation.loginGraph
import com.together.study.login.navigation.navigateToLogin
import com.together.study.login.splash.Splash
import com.together.study.login.splash.splashGraph
import com.together.study.main.component.MainBottomBar
import com.together.study.mypage.navigation.myPageGraph
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
import com.together.study.timer.navigation.navigateToTimer
import com.together.study.timer.navigation.timerGraph
import kotlinx.collections.immutable.toImmutableList

@Composable
fun MainScreen(
    navigator: MainNavigator = rememberMainNavigator(),
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val togedyToast = remember { TogedyToast(context, lifecycleOwner) }
    val snackbarHostState = remember { SnackbarHostState() }
    val isChatModeState = remember { mutableStateOf(false) }
    val showChatBotExitDialogState = remember { mutableStateOf(false) }
    val pendingTabState = remember { mutableStateOf<MainTab?>(null) }
    val currentTab = navigator.currentTab


    LaunchedEffect(Unit) {
        TogedyUiEventBus.event.collect { event ->
            when (event) {
                is TogedyUiEvent.ShowToast -> {
                    togedyToast.makeText(
                        toastType = ToastType.COMMON,
                        message = event.message,
                        icon = event.icon,
                        yOffset = togedyToast.toastOffsetWithBottomBar(),
                    )
                }
            }
        }
    }

    if (showChatBotExitDialogState.value) {
        TogedyBasicDialog(
            title = "대화 종료",
            subTitle = {
                Text(
                    text = "지금 나가면 대화 내용이 삭제됩니다.\n나가시겠습니까?",
                    style = TogedyTheme.typography.body14m,
                    color = TogedyTheme.colors.gray700,
                    textAlign = TextAlign.Center
                )
            },
            buttonText = "예",
            onDismissRequest = {
                showChatBotExitDialogState.value = false
            },
            onButtonClick = {
                showChatBotExitDialogState.value = false
                isChatModeState.value = false
                val targetTab = pendingTabState.value
                if (targetTab != null) {
                    navigator.navigate(targetTab)
                } else {
                    navigator.navigateUp()
                }
            },
        )
    }
    Scaffold(
        snackbarHost = {
            SnackbarHost(snackbarHostState)
        },
        bottomBar = {
            MainBottomBar(
                isVisible = navigator.showBottomBar(),
                tabs = MainTab.entries.toImmutableList(),
                currentTab = currentTab,
                onTabSelected = { tab ->
                    if (currentTab == MainTab.CHATBOT && isChatModeState.value && tab != MainTab.CHATBOT) {
                        pendingTabState.value = tab
                        showChatBotExitDialogState.value = true
                    } else {
                        navigator.navigate(tab)
                    }
                }
            )
        },
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        CompositionLocalProvider(
            LocalTogedyToast provides togedyToast
        ) {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                MainNavHost(
                    navigator = navigator,
                    onChatBotChatModeChanged = { isChatModeState.value = it },
                    onChatBotRequestExit = {
                        pendingTabState.value = null
                        showChatBotExitDialogState.value = true
                    },
                    modifier = Modifier
                        .padding(innerPadding)
                        .consumeWindowInsets(innerPadding)
                )
            }
        }
    }
}

@Composable
private fun MainNavHost(
    navigator: MainNavigator,
    onChatBotChatModeChanged: (Boolean) -> Unit,
    onChatBotRequestExit: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val togedyToast = LocalTogedyToast.current
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
        splashGraph(
            onNavigateToLogin = {
                navigator.navController.navigateToLogin(
                    navOptions {
                        popUpTo(Splash) { inclusive = true }
                    }
                )
            },
            onNavigateToCalendar = {
                navigator.navController.navigateToCalendar(
                    navOptions {
                        popUpTo(Splash) { inclusive = true }
                    }
                )
            },
        )

        loginGraph(
            modifier = modifier,
            navigateToCalendar = {
                navigator.navController.navigateToCalendar(
                    navOptions {
                        popUpTo(Login) { inclusive = true }
                    }
                )
            },
        )

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
            navigateToTimer = navigator.navController::navigateToTimer,
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
            navigateToTimer = navigator.navController::navigateToTimer,
            navigateToGallery = { date -> navigator.navController.navigateToGallery(date) },
            navController = navigator.navController,
            modifier = modifier,
        )

        chatBotGraph(
            navigateToUp = navigator.navController::popBackStack,
            onChatModeChanged = onChatBotChatModeChanged,
            onRequestExit = onChatBotRequestExit,
            modifier = modifier,
        )

        timerGraph(
            navigateToUp = navigator.navController::popBackStack,
            modifier = modifier,
        )

        myPageGraph(
            navigateToUp = navigator.navController::popBackStack,
            navigateToCreateStudy = navigator.navController::navigateToStudyUpdate,
            navigateToStudyDetail = navigator.navController::navigateToStudyDetail,
            navigateToStudy = navigator.navController::navigateToStudy,
            navController = navigator.navController,
            modifier = modifier,
        )

        galleryGraph(
            navigateToUp = navigator.navController::popBackStack,
            onUploadSuccess = {
                navigator.navController.popBackStack<TogedyGallery>(inclusive = true)
                togedyToast.makeText(
                    toastType = ToastType.COMMON,
                    message = "이미지가 업데이트 되었어요",
                    icon = com.together.study.designsystem.R.drawable.ic_check_green,
                    yOffset = togedyToast.toastBasicOffset(),
                )
            },
            navController = navigator.navController,
            modifier = modifier,
        )

        composable<Dummy> {
            // TODO: Dummy 추가
        }
    }
}
