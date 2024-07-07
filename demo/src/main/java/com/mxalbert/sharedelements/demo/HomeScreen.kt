@file:OptIn(ExperimentalMaterialApi::class)

package com.mxalbert.sharedelements.demo

import androidx.annotation.DrawableRes
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.magnifier
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ListItem
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mxalbert.sharedelements.FadeMode
import com.mxalbert.sharedelements.LocalSharedElementsRootScope
import com.mxalbert.sharedelements.MaterialArcMotionFactory
import com.mxalbert.sharedelements.MaterialContainerTransformSpec
import com.mxalbert.sharedelements.ProgressThresholds
import com.mxalbert.sharedelements.SharedElement
import com.mxalbert.sharedelements.SharedElementsTransitionSpec
import com.mxalbert.sharedelements.SharedMaterialContainer

private var selectedUser: Int by mutableStateOf(-1)
private var previousSelectedUser: Int = -1


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun UserListScreen(
    navigateToDetails: (Int) -> Unit
) {
    val listState = rememberLazyListState()

    LaunchedEffect(listState) {
        val previousIndex = previousSelectedUser.coerceAtLeast(0)
        if (!listState.layoutInfo.visibleItemsInfo.any { it.index == previousIndex }) {
            listState.scrollToItem(previousIndex)
        }
    }

    val scope = LocalSharedElementsRootScope.current!!
    val pagerState = rememberPagerState(pageCount = { users.size })

    Column(Modifier.fillMaxSize()) {
        toptitleAndProgressbar(pagerState)
        Box(modifier = Modifier.fillMaxWidth().fillMaxHeight()) {
            Column {
                VerticalPager(state = pagerState) { page ->
                    // Our page content
                    Column {
                        SharedElement(
                            key = users.get(page),
                            screenKey = ListScreen,
                            transitionSpec = CrossFadeTransitionSpec
                        ) {
                            SharedMaterialContainer(  key = users.get(page),
                                screenKey = ListScreen,
                                color = Color.Unspecified,
                                transitionSpec = FadeOutTransitionSpec) {
                                Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
                                    Image(
                                        painterResource(id = R.drawable.food_background),
                                        contentDescription = users.get(page).name,
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .padding(16.dp).clickable(enabled = !scope.isRunningTransition) {
                                                navigateToDetails(page)
                                                selectedUser = page
                                                previousSelectedUser = page
                                            },
                                        contentScale = ContentScale.Fit
                                    )
                                }

                            }


                        }
                    }
                }
            }

        }
    }



}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun toptitleAndProgressbar(pagerState: PagerState) {
    val text = "Get started with Android (Kotlin, Jet Compose) & IOS (Swift UI), MVVM clean architecture, and Beautiful UI UX design patterns"
    val lines = text.split(" ")

    Column(
        modifier = Modifier
            .padding(20.dp)
    ) {
        Image(painter = painterResource(R.drawable.avatar_5), contentDescription = "dadsdd", modifier = Modifier.size(30.dp))
        val line1 = lines.take(10).joinToString(" ")
        val line2 = lines.drop(10).joinToString(" ")

        Spacer(modifier = Modifier.size(10.dp))
        Text(
            text = line1.uppercase(),
            fontStyle = FontStyle.Normal,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            modifier = Modifier
                .fillMaxWidth(),
            textAlign = TextAlign.Start,
            color = Color.Black,
            maxLines = 1
        )

        Text(
            text = line2.uppercase(),
            fontStyle = FontStyle.Normal,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            modifier = Modifier
                .wrapContentWidth()
                .background(Brush.verticalGradient(colors = listOf(Color.White, AppColor().Orange))),
            textAlign = TextAlign.Start,
            color = Color.Black,
            maxLines = 1
        )
        PagerIndicator(
            pagerState = pagerState,
            borderWidth = 2.dp,
            borderColor = AppColor().Orange
        )
    }


}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PagerIndicator(
    pagerState: PagerState,
    modifier: Modifier = Modifier,
    activeColor: Color = AppColor().Orange,
    inactiveColor: Color = Color.White,
    indicatorWidth: Dp = 15.dp,
    indicatorHeight: Dp = 15.dp,
    spacing: Dp = 8.dp,
    borderWidth: Dp = 1.dp,
    borderColor: Color = Color.Black
) {
    Row(
        modifier = modifier.padding(top = 20.dp, start = 0.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        val indicators = remember { List(pagerState.pageCount) { index -> index } }
        indicators.forEach { index ->
            Box(
                modifier = Modifier
                    .size(indicatorWidth, indicatorHeight)
                    .border(
                        width = borderWidth,
                        color = borderColor,
                        shape = CircleShape
                    )
                    .background(
                        if (pagerState.currentPage == index) activeColor else inactiveColor,
                        CircleShape
                    )
            )
            Spacer(modifier = Modifier.width(spacing))
        }
    }
}
@Composable
fun UserDetailsScreen(
    index: Int,
    navigateBack: () -> Unit
) {
    val user = users[index]
    Column(
        Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SharedMaterialContainer(
            key = user.avatar,
            screenKey = DetailsScreen,
            shape = MaterialTheme.shapes.medium,
            color = Color.Transparent,
            elevation = 10.dp,
            transitionSpec = FadeOutTransitionSpec
        ) {
            Image(
                painterResource(id = user.avatar),
                contentDescription = user.name,
                modifier = Modifier
                    .size(200.dp)
                    .clickable { navigateBack() },
                contentScale = ContentScale.Crop
            )
        }
        SharedElement(
            key = user.name,
            screenKey = DetailsScreen,
            transitionSpec = CrossFadeTransitionSpec
        ) {
            Text(text = user.name, style = MaterialTheme.typography.h1)
        }
    }
}


data class User(@DrawableRes val avatar: Int, val name: String)

val users = listOf(
    User(R.drawable.avatar_1, "Adam"),
    User(R.drawable.avatar_2, "Andrew"),
    User(R.drawable.avatar_3, "Anna"),
    User(R.drawable.avatar_4, "Boris"),
)

private const val ListScreen = "list"
private const val DetailsScreen = "details"

private const val TransitionDurationMillis = 1000

private val FadeOutTransitionSpec = MaterialContainerTransformSpec(
    durationMillis = TransitionDurationMillis,
    fadeMode = FadeMode.Out
)
private val CrossFadeTransitionSpec = SharedElementsTransitionSpec(
    durationMillis = TransitionDurationMillis,
    fadeMode = FadeMode.Cross,
    fadeProgressThresholds = ProgressThresholds(0.10f, 0.40f)
)
private val MaterialFadeInTransitionSpec = MaterialContainerTransformSpec(
    pathMotionFactory = MaterialArcMotionFactory,
    durationMillis = TransitionDurationMillis,
    fadeMode = FadeMode.In
)
private val MaterialFadeOutTransitionSpec = MaterialContainerTransformSpec(
    pathMotionFactory = MaterialArcMotionFactory,
    durationMillis = TransitionDurationMillis,
    fadeMode = FadeMode.Out
)