package com.github.naz013.kmparch

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.github.naz013.logging.Logger
import com.github.naz013.roomdatabase.example.Example
import com.github.naz013.roomdatabase.example.ExampleRepository
import kotlinx.coroutines.runBlocking
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.getKoin
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
@Composable
@Preview
fun App(
    exampleRepository: ExampleRepository = getKoin().get()
) {
    MaterialTheme {
        var showContent by remember { mutableStateOf(false) }
        Column(
            modifier = Modifier
                .safeContentPadding()
                .fillMaxSize()
                .padding(16.dp),
        ) {
            Text(
                text = "Compose Multiplatform Skeleton",
                modifier = Modifier.fillMaxWidth(),
                fontSize = 20.sp,
                color = MaterialTheme.colorScheme.onBackground
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Modules testing",
                modifier = Modifier.fillMaxWidth(),
                fontSize = 18.sp,
                color = MaterialTheme.colorScheme.onBackground
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Logging module",
                    modifier = Modifier.weight(1f),
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Button(
                    onClick = {
                        Logger.i("App", "Logging module is working!")
                    }
                ) {
                    Text(
                        text = "Print log"
                    )
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Room database testing",
                modifier = Modifier.fillMaxWidth(),
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.onBackground
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Add new entity",
                    modifier = Modifier.weight(1f),
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Button(
                    onClick = {
                        runBlocking {
                            val example = Example(name = Uuid.random().toHexString())
                            exampleRepository.insert(example)
                            Logger.i("App", "Entity added: ${example.name}")
                        }
                    }
                ) {
                    Text(
                        text = "Write to DB"
                    )
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            var firstEntity by remember { mutableStateOf("null") }
            var lastEntity by remember { mutableStateOf("null") }
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "First entity name: $firstEntity",
                    modifier = Modifier.weight(1f),
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Button(
                    onClick = {
                        runBlocking {
                            firstEntity = exampleRepository.getFirst()?.name ?: "null"
                        }
                    }
                ) {
                    Text(
                        text = "Read first"
                    )
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Last entity name: $lastEntity",
                    modifier = Modifier.weight(1f),
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Button(
                    onClick = {
                        runBlocking {
                            lastEntity = exampleRepository.getLast()?.name ?: "null"
                        }
                    }
                ) {
                    Text(
                        text = "Read last"
                    )
                }
            }
        }
    }
}
