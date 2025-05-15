package com.hd.misaleawianegager.utils.compose

import androidx.compose.foundation.BorderStroke
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.ChipColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SelectableChipColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * Object containing default values and functions for creating Chip colors and properties
 * in a Material 3 context, mirroring the structure of the M2 ChipDefaults.
 */
@OptIn(ExperimentalMaterial3Api::class) // Opt-in for Experimental Material 3 APIs
object ChipDefaultsM3 {

    /**
     * The default height applied to chips.
     * Note that you can override it by applying Modifier.height directly on a chip.
     * In M3, this is typically 32.dp for most chips.
     */
    val Height: Dp =  32.dp

    /**
     * Creates a [ChipColors] that represents the default background and content colors used in
     * a filled Chip (akin to M3 AssistChip).
     *
     * @param containerColor The color used for the background of this chip.
     * @param labelColor The color used for the text label of this chip.
     * @param leadingIconContentColor The color used for the leading icon of this chip.
     * @param trailingIconContentColor The color used for the trailing icon of this chip.
     * @param disabledContainerColor The color used for the background of this chip when disabled.
     * @param disabledLabelColor The color used for the text label of this chip when disabled.
     * @param disabledLeadingIconContentColor The color used for the leading icon of this chip when disabled.
     * @param disabledTrailingIconContentColor The color used for the trailing icon of this chip when disabled.
     * @return The [ChipColors] that will be used for an M3-style filled chip.
     */
    @Composable
    fun chipColors(
        containerColor: Color = MaterialTheme.colorScheme.surfaceContainerLow, // A common M3 default for non-emphasized surfaces
        labelColor: Color = MaterialTheme.colorScheme.onSurfaceVariant,
        leadingIconContentColor: Color = MaterialTheme.colorScheme.onSurfaceVariant,
        trailingIconContentColor: Color = MaterialTheme.colorScheme.onSurfaceVariant,
        disabledContainerColor: Color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f)
            .compositeOver(MaterialTheme.colorScheme.surface), // Mimicking M3 disabled state
        disabledLabelColor: Color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f),
        disabledLeadingIconContentColor: Color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f),
        disabledTrailingIconContentColor: Color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f)
    ): ChipColors = AssistChipDefaults.assistChipColors( // Using AssistChipDefaults as a base for a generic chip
        containerColor = containerColor,
        labelColor = labelColor,
        leadingIconContentColor = leadingIconContentColor,
        trailingIconContentColor = trailingIconContentColor,
        disabledContainerColor = disabledContainerColor,
        disabledLabelColor = disabledLabelColor,
        disabledLeadingIconContentColor = disabledLeadingIconContentColor,
        disabledTrailingIconContentColor = disabledTrailingIconContentColor
    )

    /**
     * Creates a [ChipColors] that represents the default background and content colors used in
     * an outlined Chip (akin to M3 outlined AssistChip).
     *
     * @param labelColor The color used for the text label of this chip.
     * @param leadingIconContentColor The color used for the leading icon of this chip.
     * @param trailingIconContentColor The color used for the trailing icon of this chip.
     * @param disabledLabelColor The color used for the text label of this chip when disabled.
     * @param disabledLeadingIconContentColor The color used for the leading icon of this chip when disabled.
     * @param disabledTrailingIconContentColor The color used for the trailing icon of this chip when disabled.
     * @param containerColor The container color, typically transparent for outlined chips.
     * @return The [ChipColors] that will be used for an M3-style outlined chip.
     */
    @Composable
    fun outlinedChipColors(
        labelColor: Color = MaterialTheme.colorScheme.onSurface,
        leadingIconContentColor: Color = MaterialTheme.colorScheme.onSurfaceVariant,
        trailingIconContentColor: Color = MaterialTheme.colorScheme.onSurfaceVariant,
        disabledLabelColor: Color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f),
        disabledLeadingIconContentColor: Color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f),
        disabledTrailingIconContentColor: Color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f),
        containerColor: Color = Color.Transparent // Outlined chips typically have a transparent background
    ): ChipColors = AssistChipDefaults.assistChipColors(
        containerColor = containerColor, // Transparent for outlined
        labelColor = labelColor,
        leadingIconContentColor = leadingIconContentColor,
        trailingIconContentColor = trailingIconContentColor,
        disabledContainerColor = containerColor, // Keep transparent when disabled
        disabledLabelColor = disabledLabelColor,
        disabledLeadingIconContentColor = disabledLeadingIconContentColor,
        disabledTrailingIconContentColor = disabledTrailingIconContentColor
    )

    /**
     * Creates a [SelectableChipColors] that represents the default background and content colors
     * used in a filled, selectable Chip (akin to M3 FilterChip).
     *
     * Parameters map to M3 FilterChipDefaults.filterChipColors(), using M3 naming conventions.
     * @return The [SelectableChipColors] for a filled, selectable chip.
     */
    @Composable
    fun filterChipColors(
        containerColor: Color = MaterialTheme.colorScheme.surfaceContainerLow,
        labelColor: Color = MaterialTheme.colorScheme.onSurfaceVariant,
        iconColor: Color = MaterialTheme.colorScheme.onSurfaceVariant, // M3 uses 'iconColor' for leading/trailing in FilterChip
        disabledContainerColor: Color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f)
            .compositeOver(MaterialTheme.colorScheme.surface),
        disabledLabelColor: Color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f),
        disabledLeadingIconColor: Color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f),
        disabledTrailingIconColor: Color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f),
        selectedContainerColor: Color = MaterialTheme.colorScheme.secondaryContainer,
        selectedLabelColor: Color = MaterialTheme.colorScheme.onSecondaryContainer,
        selectedLeadingIconColor: Color = MaterialTheme.colorScheme.onSecondaryContainer,
        selectedTrailingIconColor: Color = MaterialTheme.colorScheme.onSecondaryContainer
    ): SelectableChipColors = FilterChipDefaults.filterChipColors(
        containerColor = containerColor,
        labelColor = labelColor,
        iconColor = iconColor, // M3 FilterChip uses a single 'iconColor' for both leading and selected checkmark
        disabledContainerColor = disabledContainerColor,
        disabledLabelColor = disabledLabelColor,
        disabledLeadingIconColor = disabledLeadingIconColor,
        disabledTrailingIconColor = disabledTrailingIconColor, // M3 FilterChip doesn't distinguish trailing icon in disabled state separately, uses disabledLabelColor
        selectedContainerColor = selectedContainerColor,
        selectedLabelColor = selectedLabelColor,
        selectedLeadingIconColor = selectedLeadingIconColor, // This applies to the checkmark icon when selected
        selectedTrailingIconColor = selectedTrailingIconColor
    )

    /**
     * Creates a [SelectableChipColors] that represents the default background and content colors
     * used in an outlined, selectable Chip (akin to M3 outlined FilterChip).
     *
     * Parameters map to M3 FilterChipDefaults.filterChipColors() with adjustments for an outlined style.
     * @return The [SelectableChipColors] for an outlined, selectable chip.
     */
    @Composable
    fun outlinedFilterChipColors(
        // For outlined, the unselected container is transparent
        containerColor: Color = Color.Transparent,
        labelColor: Color = MaterialTheme.colorScheme.onSurface,
        iconColor: Color = MaterialTheme.colorScheme.primary, // Often primary color for icon in outlined selected chip
        disabledContainerColor: Color = Color.Transparent,
        disabledLabelColor: Color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f),
        disabledLeadingIconColor: Color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f),
        disabledTrailingIconColor: Color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f),
        // Selected state for outlined often fills the container
        selectedContainerColor: Color = MaterialTheme.colorScheme.secondaryContainer,
        selectedLabelColor: Color = MaterialTheme.colorScheme.onSecondaryContainer,
        selectedLeadingIconColor: Color = MaterialTheme.colorScheme.onSecondaryContainer, // Checkmark color
        selectedTrailingIconColor: Color = MaterialTheme.colorScheme.onSecondaryContainer
    ): SelectableChipColors = FilterChipDefaults.filterChipColors(
        containerColor = containerColor,
        labelColor = labelColor,
        iconColor = iconColor,
        disabledContainerColor = disabledContainerColor,
        disabledLabelColor = disabledLabelColor,
        disabledLeadingIconColor = disabledLeadingIconColor,
        disabledTrailingIconColor = disabledTrailingIconColor,
        selectedContainerColor = selectedContainerColor,
        selectedLabelColor = selectedLabelColor,
        selectedLeadingIconColor = selectedLeadingIconColor,
        selectedTrailingIconColor = selectedTrailingIconColor
    )

    /**
     * The border used by outlined chips in M3.
     * For a basic outlined chip (like AssistChip), you'd pass this to its `border` parameter.
     * For FilterChip, the border is handled slightly differently by its own defaults when `containerColor` is transparent.
     */
    val outlinedChipBorder: BorderStroke
        @Composable
        get() = BorderStroke(
            1.dp,
            MaterialTheme.colorScheme.outline
        )

    /**
     * The default opacity for the leading icon in M3 chips.
     * Note: M3 often bakes opacity into the color roles (e.g., onSurfaceVariant).
     * This value is from M3 ChipDefaults for reference.
     */
    const val LeadingIconOpacity =  1.0f

    /**
     * The default opacity for the label in M3 chips.
     * Note: M3 often bakes opacity into the color roles.
     * This value is from M3 ChipDefaults for reference.
     */
    const val LabelOpacity =  1.0f

    /**
     * The border size for an outlined chip.
     */
    val OutlinedBorderSize: Dp = 1.dp

    /**
     * The default size of a chip's leading icon.
     */
    val LeadingIconSize: Dp =  18.dp

    /**
     * The default size of a chip's avatar (if used, e.g., with InputChip).
     * For a selected icon (checkmark in FilterChip), see `SelectedIconSize`.
     */
    val AvatarSize: Dp =  24.dp

    /**
     * The default size for the selected icon (e.g., checkmark in FilterChip).
     */
    val SelectedIconSize: Dp =  18.dp
}

// Helper to mimic M2's Color.compositeOver for disabled states if needed,
// though M3's theming often handles this.
internal fun Color.compositeOver(background: Color): Color {
    val alpha = this.alpha
    if (alpha == 0.0f) {
        // Fully transparent, return background
        return background
    }

    val r1 = this.red
    val g1 = this.green
    val b1 = this.blue
    val a1 = alpha

    val r2 = background.red
    val g2 = background.green
    val b2 = background.blue
    val a2 = background.alpha

    // StandardPorterDuff.SRC_OVER
    val a = a1 + a2 * (1f - a1)
    val r = (r1 * a1 + r2 * a2 * (1f - a1)) / a
    val g = (g1 * a1 + g2 * a2 * (1f - a1)) / a
    val b = (b1 * a1 + b2 * a2 * (1f - a1)) / a

    return Color(red = r.coerceIn(0f, 1f), green = g.coerceIn(0f, 1f), blue = b.coerceIn(0f, 1f), alpha = a.coerceIn(0f, 1f))
}
