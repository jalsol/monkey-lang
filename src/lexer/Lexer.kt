package lexer

import token.*

class Lexer(private val input: String) {
    private var position = 0
    private var readPosition = 0
    private var ch = '\u0000'

    init {
        readChar()
    }

    fun nextToken(): Token {
        skipWhitespace()
        // Two-char Tokens
        val str = "$ch${peekChar()}"
        val twoCharTokenType = lookupTwoCharTokens(str)
        if (twoCharTokenType != TokenType.ILLEGAL) {
            repeat(2) { readChar() }
            return Token(twoCharTokenType, str)
        }

        // One-char Tokens
        val tokenType = lookupOneCharTokens(ch)
        if (tokenType != TokenType.ILLEGAL) {
            val tokenStr = if (tokenType != TokenType.EOF) {
                ch.toString()
            } else {
                ""
            }
            readChar()
            return Token(tokenType, tokenStr)
        }

        // Others
        return if (isLetter(ch)) {
            val literal = readIdentifier()
            val type = lookupIdentifier(literal)
            Token(type, literal)
        } else if (ch.isDigit()) {
            Token(TokenType.INT, readNumber())
        } else {
            Token(TokenType.ILLEGAL, ch.toString())
        }
    }

    private fun readChar() {
        ch = peekChar()
        position = readPosition
        readPosition++
    }

    private fun peekChar() =
        if (readPosition >= input.length) {
            '\u0000'
        } else {
            input[readPosition]
        }

    private fun readIdentifier(): String {
        val prevPosition = position
        while (isLetter(ch)) {
            readChar()
        }
        return input.substring(prevPosition, position)
    }

    private fun readNumber(): String {
        val prevPosition = position
        while (ch.isDigit()) {
            readChar()
        }
        return input.substring(prevPosition, position)
    }

    private fun isLetter(ch: Char) = ch.isLetter() || (ch == '_')

    private fun skipWhitespace() {
        while (ch.isWhitespace()) {
            readChar()
        }
    }
}