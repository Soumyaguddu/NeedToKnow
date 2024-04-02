package com.soumya.needtoknow.ui.screen

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowForward
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
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
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.lerp
import com.alexstyl.swipeablecard.Direction
import com.alexstyl.swipeablecard.ExperimentalSwipeableCardApi
import com.alexstyl.swipeablecard.rememberSwipeableCardState
import com.alexstyl.swipeablecard.swipableCard
import com.soumya.needtoknow.R
import com.soumya.needtoknow.ui.model.PlanetInfo
import com.soumya.needtoknow.ui.model.WonderInfo
import com.soumya.needtoknow.ui.model.planets
import com.soumya.needtoknow.ui.model.wonderInfo
import com.soumya.needtoknow.ui.theme.Purple80
import com.soumya.needtoknow.ui.theme.gradientEndColor
import com.soumya.needtoknow.ui.theme.gradientStartColor
import com.soumya.needtoknow.ui.theme.primaryTextColor
import com.soumya.needtoknow.ui.theme.secondaryTextColor
import com.soumya.needtoknow.ui.theme.titleTextColor
import kotlin.math.absoluteValue

@OptIn(ExperimentalSwipeableCardApi::class)
@Composable
fun HomePage() {
    val planets = planets
    val wonders = wonderInfo
    var expanded by remember { mutableStateOf(false) }
    var selectedItem by remember { mutableStateOf("Solar System") }
    val items = listOf("Solar System", "Seven Wonder")
    Scaffold(modifier = Modifier.background(color = gradientEndColor)) { padding ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        listOf(
                            gradientStartColor,
                            gradientEndColor
                        )
                    )
                )
                .systemBarsPadding()
        ) {
            Column(
                horizontalAlignment = Alignment.Start,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(32.dp)
            ) {


                Text(
                    text = "Need To Know",
                    style = TextStyle(
                        fontSize = 40.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                )


                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier.weight(1f)
                    ) {
                        androidx.compose.material3.Text(
                            text = selectedItem,
                            style = TextStyle(
                                fontSize = 24.sp,
                                color = Color(0x7cdbf1ff),
                                fontWeight = FontWeight.W500
                            ),
                            modifier = Modifier.clickable { expanded = true }
                        )
                        DropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false },
                            modifier = Modifier.align(Alignment.TopStart)
                        ) {
                            items.forEach { item ->
                                DropdownMenuItem(text = { androidx.compose.material3.Text(text = item.toString()) }, onClick = {
                                    selectedItem = item
                                    expanded = false
                                })
                            }
                        }
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Icon(
                        painter = painterResource(id = R.drawable.drop_down_icon),
                        contentDescription = null,
                        tint = Color.Unspecified,
                        modifier = Modifier.size(12.dp).clickable { expanded = true }
                    )
                }
                when (selectedItem) {
                    "Solar System" -> {
                        val states = planets.reversed()
                            .map { it to rememberSwipeableCardState() }


                        Box(
                            Modifier
                                .padding(24.dp)
                                .fillMaxSize()

                        ) {
                            states.forEach { (matchProfile, state) ->
                                if (state.swipedDirection == null) {
                                    ProfileCard(
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .swipableCard(
                                                state = state,
                                                blockedDirections = listOf(Direction.Down),
                                                onSwiped = {
                                                    // swipes are handled by the LaunchedEffect
                                                    // so that we track button clicks & swipes
                                                    // from the same place
                                                },
                                                onSwipeCancel = {
                                                    Log.d("Swipeable-Card", "Cancelled swipe")

                                                }
                                            ),
                                        planet = matchProfile
                                    )
                                }
                                LaunchedEffect(matchProfile, state.swipedDirection) {

                                }
                            }
                        }
                    }
                    "Seven Wonder" -> {
                        val states = wonders.reversed()
                            .map { it to rememberSwipeableCardState() }


                        Box(
                            Modifier
                                .padding(24.dp)
                                .fillMaxSize()

                        ) {
                            states.forEach { (matchProfile, state) ->
                                if (state.swipedDirection == null) {
                                    WonderCard(
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .swipableCard(
                                                state = state,
                                                blockedDirections = listOf(Direction.Down),
                                                onSwiped = {
                                                    // swipes are handled by the LaunchedEffect
                                                    // so that we track button clicks & swipes
                                                    // from the same place
                                                },
                                                onSwipeCancel = {
                                                    Log.d("Swipeable-Card", "Cancelled swipe")

                                                }
                                            ),
                                        planet = matchProfile
                                    )
                                }
                                LaunchedEffect(matchProfile, state.swipedDirection) {

                                }
                            }
                        }
                    }
                }



            }
        }

    }
}

@Composable
private fun ProfileCard(
    modifier: Modifier,
    planet: PlanetInfo,
) {
    Box( modifier
        .fillMaxWidth()
        .height(800.dp))
    {

        Box(modifier)
        {
            Column {
                Spacer(modifier = Modifier.height(100.dp))
                androidx.compose.material3.Card(
                    modifier = Modifier
                        .fillMaxWidth().height(320.dp),

                    shape = RoundedCornerShape(32.dp),
                    colors = CardDefaults.cardColors(planet.background),
                    elevation = CardDefaults.cardElevation(8.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(32.dp),
                        horizontalAlignment = Alignment.Start
                    ) {
                        Spacer(modifier = Modifier.height(100.dp))
                        Text(
                            text = planet.name,
                            style = TextStyle(
                                fontSize = 30.sp,
                                fontWeight = FontWeight.W900,
                                textAlign = TextAlign.Left,
                                color = Color(0xffFFFFFF),
                            )
                        )
                       Text(
                            text = planet.desc,
                            style = TextStyle(
                                fontSize = 23.sp,
                                color = primaryTextColor,
                                fontWeight = FontWeight.W400,
                                textAlign = TextAlign.Left
                            )
                        )
                        Spacer(modifier = Modifier.height(32.dp))
                        Row {
                            androidx.compose.material3.Text(
                                text = "Know more",
                                style = TextStyle(
                                    fontSize = 16.sp,
                                    color = titleTextColor,
                                    fontWeight = FontWeight.W400,
                                    textAlign = TextAlign.Left
                                )
                            )
                            Icon(
                                Icons.Rounded.ArrowForward,
                                contentDescription = null,
                                tint = titleTextColor,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                    }
                }
            }
            Image(
                painter = painterResource(id = planet.iconImage),
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .size(300.dp)
                    .clickable { },
                contentDescription = null,
            )
        }

    }
}

@Composable
private fun WonderCard(
    modifier: Modifier,
    planet: WonderInfo,
) {
    Box( modifier
        .fillMaxWidth()
        .height(800.dp))
    {

        Box(modifier)
        {
            Column {
                Spacer(modifier = Modifier.height(80.dp))
                androidx.compose.material3.Card(
                    modifier = Modifier
                        .fillMaxWidth().height(320.dp),

                    shape = RoundedCornerShape(32.dp),
                    colors = CardDefaults.cardColors(planet.background),
                    elevation = CardDefaults.cardElevation(8.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(32.dp),
                        horizontalAlignment = Alignment.Start
                    ) {
                        Spacer(modifier = Modifier.height(100.dp))
                        Text(
                            text = planet.name,
                            style = TextStyle(
                                fontSize = 30.sp,
                                fontWeight = FontWeight.W900,
                                textAlign = TextAlign.Left,
                                color = Color(0xffFFFFFF),
                            )
                        )
                       Text(
                            text = planet.desc,
                            style = TextStyle(
                                fontSize = 23.sp,
                                color = primaryTextColor,
                                fontWeight = FontWeight.W400,
                                textAlign = TextAlign.Left
                            )
                        )
                        Spacer(modifier = Modifier.height(32.dp))
                        Row {
                            androidx.compose.material3.Text(
                                text = "Know more",
                                style = TextStyle(
                                    fontSize = 16.sp,
                                    color = titleTextColor,
                                    fontWeight = FontWeight.W400,
                                    textAlign = TextAlign.Left
                                )
                            )
                            Icon(
                                Icons.Rounded.ArrowForward,
                                contentDescription = null,
                                tint = titleTextColor,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                    }
                }
            }
            Image(
                painter = painterResource(id = planet.iconImage),
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .size(200.dp)
                    .clickable { },
                contentDescription = null,
            )
        }

    }
}
@Composable
fun Scrim(modifier: Modifier = Modifier) {
    Box(
        modifier
            .background(Brush.verticalGradient(listOf(Color.Transparent, Color.Black)))
            .height(180.dp)
            .fillMaxWidth()
    )
}