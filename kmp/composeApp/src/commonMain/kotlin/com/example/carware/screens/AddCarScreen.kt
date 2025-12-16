package com.example.carware.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import carware.composeapp.generated.resources.Res
import carware.composeapp.generated.resources.arrow_1
import carware.composeapp.generated.resources.keyboard_arrow_down
import carware.composeapp.generated.resources.keyboard_arrow_up
import carware.composeapp.generated.resources.poppins_medium
import carware.composeapp.generated.resources.poppins_semibold
import com.example.carware.m
import com.example.carware.viewModel.addcar.AddCarViewModel
import org.jetbrains.compose.resources.Font
import org.jetbrains.compose.resources.painterResource


@Composable
fun AddCarDropdown(
    label: String,
    selectedValue: String?,
    options: List<String>,
    onSelect: (String) -> Unit,

    ) {

    val textFieldColors = TextFieldDefaults.colors(

        unfocusedTextColor = Color.DarkGray,
        errorTextColor = Color(194, 0, 0, 255),

        focusedContainerColor = Color.Transparent,
        unfocusedContainerColor = Color.Transparent,
        disabledContainerColor = Color.Transparent,
        errorContainerColor = Color.Transparent,


        cursorColor = Color(194, 0, 0, 255),
        focusedIndicatorColor = Color(
            118,
            118,
            118,
            255
        ),    // underline/border when focused
        unfocusedIndicatorColor = Color(
            118,
            118,
            118,
            255
        ),  // underline/border when not focused
        errorIndicatorColor = Color(194, 0, 0, 255),
        focusedTextColor = Color(0, 0, 0, 255)


    )
    val popSemi = FontFamily(Font(Res.font.poppins_semibold))
    val popMid = FontFamily(Font(Res.font.poppins_medium))
    val scrollState = rememberScrollState()

    var expanded by remember { mutableStateOf(false) }

    Box(
        m
            .clickable { expanded = true }
    ) {
        OutlinedTextField(
            modifier = m
                .fillMaxWidth()
                .clickable { expanded = true }
                .size(290.dp, 55.dp),
            value = selectedValue ?: "",
            onValueChange = {},
            readOnly = true,
            placeholder = {
                Text(
                    label,
                    fontFamily = popMid,
                    fontSize = 16.sp,
                    color = Color(30, 30, 30, 51)
                )
            },
            trailingIcon = {
                Icon(
                    if (expanded == false) {
                        painterResource(Res.drawable.keyboard_arrow_down)
                    } else {
                        painterResource(Res.drawable.keyboard_arrow_up)
                    },
                    contentDescription = "dropdown arrow",
                    m.clickable { expanded = true }
                )
            },
            shape = RoundedCornerShape(8.dp),
            colors = textFieldColors


        )
        DropdownMenu(
            modifier = m
                .verticalScroll(scrollState)
                .size(300.dp, 110.dp)
                .background(Color(203, 202, 202, 204)),
            expanded = expanded, onDismissRequest = { expanded = false }) {
            options.forEachIndexed { index, option ->
                DropdownMenuItem(
                    modifier = m
                        .height(23.dp),
                    text = {
                        Text(
                            option,
                            fontFamily = popSemi,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.W400,
                            color = Color(102, 104, 105, 255),
                        )
                    },
                    onClick = {
                        onSelect(option)
                        expanded = false
                    }

                )
                if (index < options.size - 1) {
                    Divider(color = Color(118, 118, 118, 128), thickness = 1.dp)
                }

            }
        }
    }

}


/**
 * A reusable dropdown component for selecting an Integer value (like a year).
 * It displays the list of Ints as Strings and returns the selected Int via the callback.
 *
 * @param label The placeholder text for the dropdown.
 * @param selectedValue The currently selected Int value. Null if nothing is selected.
 * @param options The list of available Ints to choose from.
 * @param onSelect The callback function that is triggered with the selected Int.
 */
@Composable
fun AddCarIntDropdown(
    label: String,
    selectedValue: Int?, // Accepts Int?
    options: List<Int>, // Accepts List<Int>
    onSelect: (Int) -> Unit, // Returns Int
    m: Modifier = Modifier // Added a default modifier parameter for convenience
) {
    // 1. Convert Int options to String options for display
    val displayOptions = remember(options) { options.map { it.toString() } }

    // 2. Convert selected Int value to String for display
    val selectedValueString = remember(selectedValue) { selectedValue?.toString() ?: "" }


    val textFieldColors = TextFieldDefaults.colors(
        unfocusedTextColor = Color.DarkGray,
        errorTextColor = Color(194, 0, 0, 255),

        focusedContainerColor = Color.Transparent,
        unfocusedContainerColor = Color.Transparent,
        disabledContainerColor = Color.Transparent,
        errorContainerColor = Color.Transparent,

        cursorColor = Color(194, 0, 0, 255),
        focusedIndicatorColor = Color(118, 118, 118, 255),
        unfocusedIndicatorColor = Color(118, 118, 118, 255),
        errorIndicatorColor = Color(194, 0, 0, 255),
        focusedTextColor = Color(0, 0, 0, 255)
    )
    // NOTE: You must define your Font resources (Res.font.poppins_...)
    // in your project structure for this code to compile.
    val popSemi = FontFamily(Font(Res.font.poppins_semibold))
    val popMid = FontFamily(Font(Res.font.poppins_medium))
    val scrollState = rememberScrollState()

    var expanded by remember { mutableStateOf(false) }

    Box(
        m
            .clickable { expanded = true }
    ) {
        OutlinedTextField(
            modifier = m
                .fillMaxWidth()
                .clickable { expanded = true }
                .size(290.dp, 55.dp),
            value = selectedValueString, // Use the String representation
            onValueChange = {},
            readOnly = true,
            placeholder = {
                Text(
                    label,
                    fontFamily = popMid,
                    fontSize = 16.sp,
                    color = Color(30, 30, 30, 51)
                )
            },
            trailingIcon = {
                Icon(
                    if (!expanded) {
                        painterResource(Res.drawable.keyboard_arrow_down)
                    } else {
                        painterResource(Res.drawable.keyboard_arrow_up)
                    },
                    contentDescription = "dropdown arrow",
                    m.clickable { expanded = true }
                )
            },
            shape = RoundedCornerShape(8.dp),
            colors = textFieldColors
        )

        DropdownMenu(
            modifier = m
                .verticalScroll(scrollState)
                .size(300.dp, 110.dp)
                .background(Color(203, 202, 202, 204)),
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            displayOptions.forEachIndexed { index, optionString ->
                DropdownMenuItem(
                    modifier = m.height(23.dp),
                    text = {
                        Text(
                            optionString, // Display the String
                            fontFamily = popSemi,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.W400,
                            color = Color(102, 104, 105, 255),
                        )
                    },
                    onClick = {
                        // 3. Convert the selected String back to Int before calling onSelect
                        val selectedInt = optionString.toIntOrNull()
                        if (selectedInt != null) {
                            onSelect(selectedInt) // Call the callback with the Int
                        }
                        expanded = false
                    }
                )
                if (index < displayOptions.size - 1) {
                    Divider(color = Color(118, 118, 118, 128), thickness = 1.dp)
                }
            }
        }
    }
}

@Composable
fun AddCarScreen(navController: NavController, viewModel: AddCarViewModel) {

    val state by viewModel.state.collectAsState()
    val popSemi = FontFamily(Font(Res.font.poppins_semibold))
    val popMid = FontFamily(Font(Res.font.poppins_medium))

    Column(
        m
            .background(Color(230, 230, 230, 255))
            .fillMaxSize()

    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.25f)
                .clip(RoundedCornerShape(bottomStart = 70.dp, bottomEnd = 70.dp))
                .appGradBack(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp)
            ) {
                // Back icon at the start
                Icon(
                    painter = painterResource(Res.drawable.arrow_1),
                    contentDescription = null,
                    tint = Color(245, 245, 245),
                    modifier = Modifier
                        .size(26.dp)
                        .rotate(180f)
                        .align(Alignment.CenterStart)
                )

                // Centered text
                Text(
                    "Add Your Car",
                    fontFamily = popMid,
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(235, 235, 235),
                    modifier = Modifier.align(Alignment.Center)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Subtitle
            Text(
                "Set up your car profile to get personalized \nservice reminders and tips.",
                fontFamily = popMid,
                fontSize = 14.sp,
                color = Color(235, 235, 235),
                textAlign = TextAlign.Center
            )
        }//top bar

        Spacer(m.padding(vertical = 30.dp)) //will be edited at line addition
        Column(
            m.padding(horizontal = 30.dp)
                .fillMaxSize(),

            ) {
            Text(
                "Brand ",
                fontFamily = popMid,
                fontSize = 20.sp,
                fontWeight = Bold,
                color = Color(30, 30, 30, 153),
            )
            AddCarDropdown(
                "Brand",
                state.selectedBrand,
                state.availableBrands,
                { name ->
                    val brand = viewModel.brands.firstOrNull { it.name == name }
                    brand?.let { viewModel.selectBrand(it) } // stores ID and name
                }
            )

            Spacer(m.padding(vertical = 8.dp))
            Text(
                "Model ",
                fontFamily = popMid,
                fontSize = 20.sp,
                fontWeight = Bold,
                color = Color(30, 30, 30, 153),
            )
            AddCarDropdown(
                "Model",
                state.selectedModel,
                state.availableModels,
                { name ->
                    val model = viewModel.models.firstOrNull { it.name == name }
                    model?.let { viewModel.selectModel(it) }
                }

            )

            Spacer(m.padding(vertical = 8.dp))
            Text(
                "Year ",
                fontFamily = popMid,
                fontSize = 20.sp,
                fontWeight = Bold,
                color = Color(30, 30, 30, 153),
            )
            AddCarIntDropdown(
                label = "Year",
                selectedValue = state.selectedYear,
                options = state.availableYears,
                onSelect = viewModel::selectYear
            )
            Spacer(m.padding(vertical = 8.dp))
            // Color Dropdown
            Text(
                "Color ",
                fontFamily = popMid,
                fontSize = 20.sp,
                fontWeight = Bold,
                color = Color(30, 30, 30, 153),
            )
            AddCarDropdown(
                label = "Color",
                selectedValue = state.selectedColor,
                options = state.availableColors,
                onSelect = viewModel::selectColor
            )
            Spacer(m.padding(vertical = 16.dp))

            Card(
                onClick = {
                    viewModel.addVehicle(navController)

                },
                modifier = m
                    .size(width = 240.dp, height = 45.dp)
                    .border(
                        width = 1.dp,
                        color = Color(30, 30, 30, 110),
                        shape = RoundedCornerShape(8.dp)
                    )
                    .align(Alignment.CenterHorizontally)
                    .clip(shape = RoundedCornerShape(8.dp))
                    .appButtonBack(),
                colors = CardDefaults.cardColors(
                    containerColor = Color.Transparent,
                    disabledContainerColor = Color.Gray
                ),
                enabled = state.isSaveButtonEnabled,

                ) {

                Row(
                    modifier = m.fillMaxSize(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        "Add Car",
                        fontFamily = popSemi,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(217, 217, 217, 255)
                    )
                }
            } // login button

        }


    }

}


//// Brand Dropdown
//AddCarDropdown(
//label = "Brand",
//selectedValue = state.selectedBrand,
//options = state.availableBrands,
//onSelect = viewModel::selectBrand // Calls the ViewModel function
//)
//
//// Model Dropdown (Options change based on Brand selection)
//AddCarDropdown(
//label = "Model",
//selectedValue = state.selectedModel,
//options = state.availableModels,
//onSelect = viewModel::selectModel // Calls the ViewModel function
//)
//
//// Color Dropdown
//AddCarDropdown(
//label = "Color",
//selectedValue = state.selectedColor,
//options = state.availableColors,
//onSelect = viewModel::selectColor
//)
//
//// Year Dropdown
//AddCarDropdown(
//label = "Year",
//selectedValue = state.selectedYear,
//options = state.availableYears,
//onSelect = viewModel::selectYear
//)
//
//Spacer(Modifier.height(8.dp))
//
//// Save Button (Enabled based on state validation)
//Button(
//onClick = { /* TODO: Implement save logic */ },
//enabled = state.isSaveButtonEnabled,
//modifier = Modifier.fillMaxWidth()
//) {
//    Text("Add Car")
//}
//}


