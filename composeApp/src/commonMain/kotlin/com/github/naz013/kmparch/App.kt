package com.github.naz013.kmparch

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import com.github.naz013.datastore.example.ExampleSettings
import com.github.naz013.ktor.weather.CurrentWeatherApi
import com.github.naz013.logging.Logger
import com.github.naz013.roomdatabase.example.Example
import com.github.naz013.roomdatabase.example.ExampleRepository
import com.github.naz013.sqldelightdatabase.DelightModel
import com.github.naz013.sqldelightdatabase.DelightRepository
import kotlinx.coroutines.runBlocking
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.getKoin
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
@Composable
@Preview
fun App(
    exampleRepository: ExampleRepository = getKoin().get(),
    exampleSettings: ExampleSettings = getKoin().get(),
    delightRepository: DelightRepository = getKoin().get(),
    currentWeatherApi: CurrentWeatherApi = getKoin().get()
) {
    MaterialTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .safeContentPadding()
                .padding(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState()),
            ) {
                Text(
                    text = "Compose Multiplatform Skeleton",
                    modifier = Modifier.fillMaxWidth(),
                    fontSize = 24.sp,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Modules testing",
                    modifier = Modifier.fillMaxWidth(),
                    fontSize = 22.sp,
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
                        fontSize = 18.sp,
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
                    fontSize = 18.sp,
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

                runBlocking {
                    firstEntity = exampleRepository.getFirst()?.name ?: "null"
                    lastEntity = exampleRepository.getLast()?.name ?: "null"
                }

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

                var increment by remember { mutableStateOf("null") }
                runBlocking {
                    increment = exampleSettings.getIncrement().toString()
                }

                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Datastore module, increment value = $increment",
                        modifier = Modifier.weight(1f),
                        fontSize = 18.sp,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    Button(
                        onClick = {
                            runBlocking {
                                val value = exampleSettings.getIncrement() + 1
                                exampleSettings.setIncrement(value)
                                increment = exampleSettings.getIncrement().toString()
                            }
                        }
                    ) {
                        Text(
                            text = "Increment"
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "SqlDelight testing",
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
                        text = "Add new entity",
                        modifier = Modifier.weight(1f),
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    Button(
                        onClick = {
                            runBlocking {
                                val model = DelightModel(name = Uuid.random().toHexString())
                                delightRepository.insert(model)
                                Logger.i("App", "SqlDelight inserted: ${model.name}")
                            }
                        }
                    ) {
                        Text(
                            text = "Write to DB"
                        )
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
                var firstDelightEntity by remember { mutableStateOf("null") }
                var lastDelightEntity by remember { mutableStateOf("null") }

                runBlocking {
                    firstDelightEntity = delightRepository.getFirst()?.name ?: "null"
                    lastDelightEntity = delightRepository.getLast()?.name ?: "null"
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "First entity name: $firstDelightEntity",
                        modifier = Modifier.weight(1f),
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    Button(
                        onClick = {
                            runBlocking {
                                firstDelightEntity = delightRepository.getFirst()?.name ?: "null"
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
                        text = "Last entity name: $lastDelightEntity",
                        modifier = Modifier.weight(1f),
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    Button(
                        onClick = {
                            runBlocking {
                                lastDelightEntity = delightRepository.getLast()?.name ?: "null"
                            }
                        }
                    ) {
                        Text(
                            text = "Read last"
                        )
                    }
                }

                var currentTemperature by remember { mutableStateOf("null") }

                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Current temperature in Kyiv is $currentTemperature",
                        modifier = Modifier.weight(1f),
                        fontSize = 18.sp,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    Button(
                        onClick = {
                            runBlocking {
                                currentTemperature = currentWeatherApi.getTemperature() ?: "null"
                            }
                        }
                    ) {
                        Text(
                            text = "Get temperature"
                        )
                    }
                }
            }
        }
    }
}
