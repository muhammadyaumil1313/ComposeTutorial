package com.haristudio.composetutorial

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.compose.tutorial.SampleData
import com.haristudio.composetutorial.ui.theme.ComposeTutorialTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeTutorialTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Conversations(messages = SampleData.conversationSample)
                }
            }
        }
    }
}

data class Message(val author: String, val message : String)

@Composable
fun MessageCard(msg: Message){
       Row(modifier=Modifier.padding(all=8.dp)) {
           Image(
               modifier= Modifier
                   .size(40.dp)
                   .clip(CircleShape)
                   .border(1.5.dp, MaterialTheme.colors.secondary),
               painter = painterResource(id = R.drawable.profile_picture),
               contentDescription = "Profile Image"
           )
           var isExpanded by remember { mutableStateOf(false) }
           val surfaceColor by animateColorAsState(
               if (isExpanded) MaterialTheme.colors.primary else MaterialTheme.colors.surface,
           )
           Column(modifier=Modifier.clickable { isExpanded = !isExpanded }){
               Text(text = msg.author,
                   color=MaterialTheme.colors.secondaryVariant,
                   style = MaterialTheme.typography.subtitle2
               )
               Spacer(modifier = Modifier.height(4.dp))
               Surface(
                   shape = MaterialTheme.shapes.medium,
                   elevation = 0.1.dp,
                   color = surfaceColor,
                   modifier = Modifier.animateContentSize().padding(1.dp)
               ) {
                   Text(
                       text = msg.message,
                       modifier = Modifier.padding(all = 4.dp),
                       // If the message is expanded, we display all its content
                       // otherwise we only display the first line
                       maxLines = if (isExpanded) Int.MAX_VALUE else 1,
                       style = MaterialTheme.typography.body2
                   )
               }
           }
       }
}

@Composable
fun Conversations(messages : List<Message>) {
    LazyColumn{
        items(messages){message ->
            MessageCard(msg = message)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ComposeTutorialTheme {
        Conversations(messages = SampleData.conversationSample)
    }
}