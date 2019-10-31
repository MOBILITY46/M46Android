package se.mobility46.jwt

import junit.framework.TestCase
import org.junit.Test
import java.lang.IllegalStateException

class JWTTests: TestCase() {

    @Test fun test_decodeJWT() {
        val token = "eyJhbGciOiJIUzI1NiJ9.eyJ1c2VyX2lkIjo2NCwiZXhwIjoxNTc4NDg5OTE2fQ.NWaNBaeSsXUZsXwPVm5GAoE6ERdkhx3uhKKIZMymuAo"

        try {
            val jwt = JWT(token).decode()
            assertEquals("{\"alg\":\"HS256\"}", jwt["header"])
            assertEquals("{\"user_id\":64,\"exp\":1578489916}", jwt["claims"])
        } catch (e: IllegalStateException) {
            print(e)
        }
    }


}