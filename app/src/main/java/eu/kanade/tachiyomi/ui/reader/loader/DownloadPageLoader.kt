package eu.kanade.tachiyomi.ui.reader.loader

import android.app.Application
import android.net.Uri
import com.hippo.unifile.UniFile
import eu.kanade.tachiyomi.data.database.models.toDomainChapter
import eu.kanade.tachiyomi.data.download.DownloadManager
import eu.kanade.tachiyomi.data.download.DownloadProvider
import eu.kanade.tachiyomi.source.Source
import eu.kanade.tachiyomi.source.model.Page
import eu.kanade.tachiyomi.ui.reader.model.ReaderChapter
import eu.kanade.tachiyomi.ui.reader.model.ReaderPage
import eu.kanade.translation.TranslationManager
import mihon.core.archive.archiveReader
import tachiyomi.domain.manga.model.Manga
import uy.kohesive.injekt.injectLazy

/**
 * Loader used to load a chapter from the downloaded chapters.
 */
internal class DownloadPageLoader(
    private val chapter: ReaderChapter,
    private val manga: Manga,
    private val source: Source,
    private val downloadManager: DownloadManager,
    private val downloadProvider: DownloadProvider,
    // KMK -->
    private val translationManager: TranslationManager,
    // KMK <--
) : PageLoader() {

    private val context: Application by injectLazy()

    private var archivePageLoader: ArchivePageLoader? = null

    override var isLocal: Boolean = true

    override suspend fun getPages(): List<ReaderPage> {
        val dbChapter = chapter.chapter
        val chapterPath = downloadProvider.findChapterDir(dbChapter.name, dbChapter.scanlator, /* SY --> */ manga.ogTitle /* SY <-- */, source)
        return if (chapterPath?.isFile == true) {
            getPagesFromArchive(chapterPath)
        } else {
            getPagesFromDirectory()
        }
    }

    override fun recycle() {
        super.recycle()
        archivePageLoader?.recycle()
    }

    private suspend fun getPagesFromArchive(file: UniFile): List<ReaderPage> {
        val loader = ArchivePageLoader(
            file.archiveReader(context),
            // KMK -->
            chapter,
            manga,
            source,
            translationManager,
            // KMK <--
        ).also { archivePageLoader = it }
        return loader.getPages()
    }

    private fun getPagesFromDirectory(): List<ReaderPage> {
        // KMK -->
        val pageTranslations = translationManager.getChapterTranslation(
            chapter.chapter.name,
            chapter.chapter.scanlator,
            manga.title,
            source,
        )
        // KMK <--
        val pages = downloadManager.buildPageList(
            source,
            manga,
            chapter.chapter.toDomainChapter()!!,
            // KMK -->
            pageTranslations,
            // KMK <--
        )
        return pages.map { (page, textTranslations) ->
            ReaderPage(
                page.index,
                page.url,
                page.imageUrl,
                // KMK -->
                translations = textTranslations,
                // KMK <--
            ) {
                context.contentResolver.openInputStream(page.uri ?: Uri.EMPTY)!!
            }.apply {
                status = Page.State.READY
            }
        }
    }

    override suspend fun loadPage(page: ReaderPage) {
        archivePageLoader?.loadPage(page)
    }
}
