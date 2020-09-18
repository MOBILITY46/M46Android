package se.mobility46.lib

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import org.junit.Test
import org.junit.Assert.*


class SystemTest {

    @Test fun returnsCorrectSystemDescription() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val description = System.description(context)
        assertEquals("123", description)
    }
}