package repl

import lexer.Lexer
import token.TokenType

const val PROMPT = ">>> "

fun start() {
    while (true) {
        print(PROMPT)
        val scanned = readlnOrNull() ?: return

        val lexer = Lexer(scanned)
        while (true) {
            val token = lexer.nextToken()
            if (token.type == TokenType.EOF) {
                break
            }
            println(token)
        }
    }
}