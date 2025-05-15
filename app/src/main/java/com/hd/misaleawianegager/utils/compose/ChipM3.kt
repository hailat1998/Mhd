package com.hd.misaleawianegager.utils.compose


import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.ChipColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp

// Define constants for padding, similar to M2 ChipDefaults or internal M3 values
// These maintain the padding structure of your original component.
private val ChipHorizontalPadding = 12.dp
private val LeadingIconStartSpacing = 8.dp // Common spacing around icons
private val LeadingIconEndSpacing = 8.dp   // Common spacing between icon and content

/**
 * A custom Chip implementation adapted for Material 3.
 *
 * This chip is a clickable surface that can contain a leading icon and custom content.
 * It uses Material 3 components and theming conventions.
 *
 * @param onClick Callback to be invoked when the chip is clicked.
 * @param modifier Modifier to be applied to the chip.
 * @param enabled Controls the enabled state of the chip. When `false`, this chip will not be clickable.
 * @param interactionSource The [MutableInteractionSource] representing the stream of
 * interactions for this chip. You can create and pass in your own remembered
 * [MutableInteractionSource] if you want to observe interactions and customize the
 * appearance / behavior of this chip in different states.
 * @param shape Defines the chip's shape. Defaults to a pill shape ([CircleShape]).
 * @param border Optional border to draw around the chip.
 * @param colors [ChipColors] that will be used to resolve the colors used for this chip in
 * different states. See [AssistChipDefaults.assistChipColors].
 * @param leadingIcon Optional composable to be displayed at the start of the chip.
 * @param content The main content of the chip, laid out in a [RowScope].
 */
@OptIn(ExperimentalMaterial3Api::class) // Opt-in for Experimental Material 3 APIs
@Composable
fun Chip(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    shape: Shape = CircleShape, // M3 default for AssistChip is CircleShape (pill)
    border: BorderStroke? = null,
    colors: ChipColors = ChipDefaultsM3.chipColors(), // Using M3 AssistChipColors as a default
    leadingIcon: @Composable (() -> Unit)? = null,
    content: @Composable RowScope.() -> Unit
) {
    // Retrieve M3 specific colors using the ChipColors interface
    val containerColor = colors.containerColor
    val contentColor = colors.labelColor // M3 uses 'labelColor' for the main text/content
    val leadingIconColor = colors.leadingIconContentColor

    Surface(
        onClick = onClick,
        modifier = modifier.semantics { role = Role.Button },
        enabled = enabled,
        shape = shape,
        color = containerColor, // M3 Surface uses 'color' for background
        contentColor = contentColor, // M3 Surface uses 'contentColor' for preferred content color
        border = border,
        interactionSource = interactionSource,
    ) {
        // ProvideTextStyle to set the default text style for the chip's content
        // Using MaterialTheme.typography.labelLarge, common for M3 chips
        ProvideTextStyle(
            value = MaterialTheme.typography.labelLarge
        ) {
            Row(
                Modifier
                    .defaultMinSize(
                        minHeight = ChipDefaultsM3.Height // Using M3 ChipDefaults.Height (32.dp)
                    )
                    .padding( // Preserving original padding logic
                        start = if (leadingIcon == null) ChipHorizontalPadding else 0.dp,
                        end = ChipHorizontalPadding,
                    ),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (leadingIcon != null) {
                    Spacer(Modifier.width(LeadingIconStartSpacing))
                    // Provide the specific color for the leading icon
                    CompositionLocalProvider(
                        LocalContentColor provides leadingIconColor,
                        content = leadingIcon
                    )
                    Spacer(Modifier.width(LeadingIconEndSpacing))
                }
                // The content lambda is called within the RowScope
                content()
            }
        }
    }
}
