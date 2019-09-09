package se.mobility46.cache

import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.runner.AndroidJUnit4
import junit.framework.Assert.*
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AndroidCacheTest {

    private lateinit var store: Store<TestUser>

    private val key = "my-key"
    private val testObject: TestUser = TestUser("John", "Doe")

    @Before
    fun setUp() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        val config = Config(context.cacheDir, 1024)
        store = Store(config)
    }

    @After
    fun tearDown() {
        runBlocking { store.removeAll() }
    }

    @Test
    fun add() {
        try {
            runBlocking { store.add(key, testObject) }
            assertTrue(true)
        } catch (e: Store.Exception)  {
            fail(e.message)
        }
    }

    @Test
    fun remove() {
        try {
            runBlocking { store.add(key, testObject) }
            runBlocking { store.remove(key) }
            assertTrue(true)
        } catch (e: Store.Exception) {
            fail(e.message)
        }
    }

    @Test
    fun entry() {
        try {
            runBlocking { store.add(key, testObject) }
            val result = runBlocking {
                store.entry(key)
            }
            assertNotNull("Entry could not be found", result)
        } catch (e: Store.Exception) {
            fail(e.message)
        }
    }
}