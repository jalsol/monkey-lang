package lexer

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import token.Token
import token.TokenType

internal class LexerTest {
    @Test
    fun testNextToken() {
        val input = """
           |let five = 5;
           |let ten = 10;
           |let add = fn(x, y) {
           |    x + y;
           |};
           |let result = add(five, ten);
           |!-/*5;
           |5 < 10 > 5;
           |
           |if (5 < 10) {
           |    return true;
           |} else {
           |    return false;
           |}
           |
           |10 == 10;
           |10 != 9;
        """.trimMargin()

        val tests = listOf(
            // let five = 5;
            Token(type = TokenType.LET, literal = "let"),
            Token(type = TokenType.IDENT, literal = "five"),
            Token(type = TokenType.ASSIGN, literal = "="),
            Token(type = TokenType.INT, literal = "5"),
            Token(type = TokenType.SEMICOLON, literal = ";"),

            // let ten = 10;
            Token(type = TokenType.LET, literal = "let"),
            Token(type = TokenType.IDENT, literal = "ten"),
            Token(type = TokenType.ASSIGN, literal = "="),
            Token(type = TokenType.INT, literal = "10"),
            Token(type = TokenType.SEMICOLON, literal = ";"),

            // let add = fn(x, y) {
            Token(type = TokenType.LET, literal = "let"),
            Token(type = TokenType.IDENT, literal = "add"),
            Token(type = TokenType.ASSIGN, literal = "="),
            Token(type = TokenType.FUNCTION, literal = "fn"),
            Token(type = TokenType.LPAREN, literal = "("),
            Token(type = TokenType.IDENT, literal = "x"),
            Token(type = TokenType.COMMA, literal = ","),
            Token(type = TokenType.IDENT, literal = "y"),
            Token(type = TokenType.RPAREN, literal = ")"),
            Token(type = TokenType.LBRACE, literal = "{"),

            // x + y;
            Token(type = TokenType.IDENT, literal = "x"),
            Token(type = TokenType.PLUS, literal = "+"),
            Token(type = TokenType.IDENT, literal = "y"),
            Token(type = TokenType.SEMICOLON, literal = ";"),

            // };
            Token(type = TokenType.RBRACE, literal = "}"),
            Token(type = TokenType.SEMICOLON, literal = ";"),

            // let result = add(five, ten);
            Token(type = TokenType.LET, literal = "let"),
            Token(type = TokenType.IDENT, literal = "result"),
            Token(type = TokenType.ASSIGN, literal = "="),
            Token(type = TokenType.IDENT, literal = "add"),
            Token(type = TokenType.LPAREN, literal = "("),
            Token(type = TokenType.IDENT, literal = "five"),
            Token(type = TokenType.COMMA, literal = ","),
            Token(type = TokenType.IDENT, literal = "ten"),
            Token(type = TokenType.RPAREN, literal = ")"),
            Token(type = TokenType.SEMICOLON, literal = ";"),

            // !-/*5;
            Token(type = TokenType.BANG, literal = "!"),
            Token(type = TokenType.MINUS, literal = "-"),
            Token(type = TokenType.SLASH, literal = "/"),
            Token(type = TokenType.ASTERISK, literal = "*"),
            Token(type = TokenType.INT, literal = "5"),
            Token(type = TokenType.SEMICOLON, literal = ";"),

            // 5 < 10 > 5;
            Token(type = TokenType.INT, literal = "5"),
            Token(type = TokenType.LT, literal = "<"),
            Token(type = TokenType.INT, literal = "10"),
            Token(type = TokenType.GT, literal = ">"),
            Token(type = TokenType.INT, literal = "5"),
            Token(type = TokenType.SEMICOLON, literal = ";"),

            // if (5 < 10) {
            Token(type = TokenType.IF, literal = "if"),
            Token(type = TokenType.LPAREN, literal = "("),
            Token(type = TokenType.INT, literal = "5"),
            Token(type = TokenType.LT, literal = "<"),
            Token(type = TokenType.INT, literal = "10"),
            Token(type = TokenType.RPAREN, literal = ")"),
            Token(type = TokenType.LBRACE, literal = "{"),

            // return true;
            Token(type = TokenType.RETURN, literal = "return"),
            Token(type = TokenType.TRUE, literal = "true"),
            Token(type = TokenType.SEMICOLON, literal = ";"),

            // } else {
            Token(type = TokenType.RBRACE, literal = "}"),
            Token(type = TokenType.ELSE, literal = "else"),
            Token(type = TokenType.LBRACE, literal = "{"),

            // return false;
            Token(type = TokenType.RETURN, literal = "return"),
            Token(type = TokenType.FALSE, literal = "false"),
            Token(type = TokenType.SEMICOLON, literal = ";"),

            // }
            Token(type = TokenType.RBRACE, literal = "}"),

            // 10 == 10;
            Token(type = TokenType.INT, literal = "10"),
            Token(type = TokenType.EQ, literal = "=="),
            Token(type = TokenType.INT, literal = "10"),
            Token(type = TokenType.SEMICOLON, literal = ";"),

            // 10 != 9;
            Token(type = TokenType.INT, literal = "10"),
            Token(type = TokenType.NOT_EQ, literal = "!="),
            Token(type = TokenType.INT, literal = "9"),
            Token(type = TokenType.SEMICOLON, literal = ";"),

            Token(type = TokenType.EOF, literal = ""),
        )

        val lexer = Lexer(input)

        tests.forEachIndexed { index, testToken ->
            val nextToken = lexer.nextToken()

            assertEquals(
                nextToken.type, testToken.type,
                "tests[$index] - wrong token type: expected=\"${testToken.type}\", got=\"${nextToken.type}\""
            )

            assertEquals(
                nextToken.literal, testToken.literal,
                "tests[$index] - wrong token literal: expected=\"${testToken.literal}\", got=\"${nextToken.literal}\""
            )
        }
    }
}