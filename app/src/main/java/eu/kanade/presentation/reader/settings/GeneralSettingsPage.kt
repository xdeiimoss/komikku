package eu.kanade.presentation.reader.settings

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import eu.kanade.presentation.util.collectAsState
import eu.kanade.tachiyomi.R
import eu.kanade.tachiyomi.ui.reader.setting.ReaderPreferences
import eu.kanade.tachiyomi.ui.reader.setting.ReaderSettingsScreenModel
import tachiyomi.presentation.core.components.CheckboxItem
import tachiyomi.presentation.core.components.SettingsFlowRow

private val themes = listOf(
    R.string.black_background to 1,
    R.string.gray_background to 2,
    R.string.white_background to 0,
    R.string.automatic_background to 3,
)

@Composable
internal fun ColumnScope.GeneralPage(screenModel: ReaderSettingsScreenModel) {
    val readerTheme by screenModel.preferences.readerTheme().collectAsState()
    SettingsFlowRow(R.string.pref_reader_theme) {
        themes.map { (labelRes, value) ->
            FilterChip(
                selected = readerTheme == value,
                onClick = { screenModel.preferences.readerTheme().set(value) },
                label = { Text(stringResource(labelRes)) },
            )
        }
    }

    val showPageNumber by screenModel.preferences.showPageNumber().collectAsState()
    CheckboxItem(
        label = stringResource(R.string.pref_show_page_number),
        checked = showPageNumber,
        onClick = {
            screenModel.togglePreference(ReaderPreferences::showPageNumber)
        },
    )

    // SY -->
    val forceHorizontalSeekbar by screenModel.preferences.forceHorizontalSeekbar().collectAsState()
    CheckboxItem(
        label = stringResource(R.string.pref_force_horz_seekbar),
        checked = forceHorizontalSeekbar,
        onClick = {
            screenModel.togglePreference(ReaderPreferences::forceHorizontalSeekbar)
        },
    )

    if (!forceHorizontalSeekbar) {
        val landscapeVerticalSeekbar by screenModel.preferences.landscapeVerticalSeekbar().collectAsState()
        CheckboxItem(
            label = stringResource(R.string.pref_show_vert_seekbar_landscape),
            checked = landscapeVerticalSeekbar,
            onClick = {
                screenModel.togglePreference(ReaderPreferences::landscapeVerticalSeekbar)
            },
        )

        val leftVerticalSeekbar by screenModel.preferences.leftVerticalSeekbar().collectAsState()
        CheckboxItem(
            label = stringResource(R.string.pref_left_handed_vertical_seekbar),
            checked = leftVerticalSeekbar,
            onClick = {
                screenModel.togglePreference(ReaderPreferences::leftVerticalSeekbar)
            },
        )
    }
    // SY <--

    val fullscreen by screenModel.preferences.fullscreen().collectAsState()
    CheckboxItem(
        label = stringResource(R.string.pref_fullscreen),
        checked = fullscreen,
        onClick = {
            screenModel.togglePreference(ReaderPreferences::fullscreen)
        },
    )

    // TODO: hide if there's no cutout
    val cutoutShort by screenModel.preferences.cutoutShort().collectAsState()
    CheckboxItem(
        label = stringResource(R.string.pref_cutout_short),
        checked = cutoutShort,
        onClick = {
            screenModel.togglePreference(ReaderPreferences::cutoutShort)
        },
    )

    val keepScreenOn by screenModel.preferences.keepScreenOn().collectAsState()
    CheckboxItem(
        label = stringResource(R.string.pref_keep_screen_on),
        checked = keepScreenOn,
        onClick = {
            screenModel.togglePreference(ReaderPreferences::keepScreenOn)
        },
    )

    val readWithLongTap by screenModel.preferences.readWithLongTap().collectAsState()
    CheckboxItem(
        label = stringResource(R.string.pref_read_with_long_tap),
        checked = readWithLongTap,
        onClick = {
            screenModel.togglePreference(ReaderPreferences::readWithLongTap)
        },
    )

    val alwaysShowChapterTransition by screenModel.preferences.alwaysShowChapterTransition().collectAsState()
    CheckboxItem(
        label = stringResource(R.string.pref_always_show_chapter_transition),
        checked = alwaysShowChapterTransition,
        onClick = {
            screenModel.togglePreference(ReaderPreferences::alwaysShowChapterTransition)
        },
    )

    // SY -->
    /*val pageTransitions by screenModel.preferences.pageTransitions().collectAsState()
    CheckboxItem(
        label = stringResource(R.string.pref_page_transitions),
        checked = pageTransitions,
        onClick = {
            screenModel.togglePreference(ReaderPreferences::pageTransitions)
        },
    )*/
    val useAutoWebtoon by screenModel.preferences.useAutoWebtoon().collectAsState()
    CheckboxItem(
        label = stringResource(R.string.auto_webtoon_mode),
        checked = useAutoWebtoon,
        onClick = {
            screenModel.togglePreference(ReaderPreferences::useAutoWebtoon)
        },
    )
    // SY <--
}
