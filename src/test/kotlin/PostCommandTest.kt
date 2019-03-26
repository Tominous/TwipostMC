import com.tklcraft.twipostmc.command.PostCommand
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class PostCommandTest {
    @Test
    internal fun connectArgsTest() {
        val postCommand = PostCommand
        val method = postCommand.javaClass.getDeclaredMethod("connectArgs", Array<out String>::class.java)
        method.isAccessible = true
        val argsMap = mapOf(
                " " to arrayOf(" "),                                    // _ -> _
                "message" to arrayOf("message"),                        // message -> message
                "sample text" to arrayOf("\"sample","text\""),          // "sample text" -> sample text
                "sample text" to arrayOf("\"sample","text\""," "),      // "sample text " -> sample text
                "sample text" to arrayOf("\"sample","text\"","dummy"),  // "sample text" dummy -> sample text
                "sample text" to arrayOf("dummy","\"sample","text\""),  // dymmy "sample text" -> sample text
                "全角テスト" to arrayOf("\"全角テスト\"")                 // "全角テスト" -> 全角テスト
                )

        argsMap.forEach{
            assertEquals(it.key, method.invoke(postCommand, it.value))
        }
    }
}