package se.mobility46.cache

import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.runner.AndroidJUnit4
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AndroidCacheTest {

    lateinit var store: Store<TestUser>

    private val key = "my-key"
    private val testObject = TestUser("John", "Doe")

    @Before
    fun setUp() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        val config = Config(context.cacheDir, 20)
        store = Store(config)
    }

    @After
    fun tearDown() {
        runBlocking { store.removeAll() }
    }

    @Test
    fun add() {
        runBlocking { store.add(key, testObject) }
        assert(true)
    }

    @Test
    fun remove() {
        runBlocking { store.add(key, testObject) }
        runBlocking { store.remove(key) }
        assert(true)
    }

    @Test
    fun entry() {
        val result = runBlocking {
            store.add(key, testObject)
            store.entry("fdsfds")
        }

        assert(result != null) { "Object should exist" }
    }
}