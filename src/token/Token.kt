package token

data class Token(
    val type: TokenType,
    val literal: String,
)

enum class TokenType {
    ILLEGAL,
    EOF,

    IDENT,
    INT,

    COMMA,
    SEMICOLON,

    ASSIGN,
    PLUS,
    MINUS,
    SLASH,
    ASTERISK,
    LT,
    GT,
    BANG,

    EQ,
    NOT_EQ,

    LPAREN,
    RPAREN,
    LBRACE,
    RBRACE,

    FUNCTION,
    LET,
    TRUE,
    FALSE,
    IF,
    ELSE,
    RETURN,
}

private val keywords = mapOf(
    "fn" to TokenType.FUNCTION,
    "let" to TokenType.LET,
    "true" to TokenType.TRUE,
    "false" to TokenType.FALSE,
    "if" to TokenType.IF,
    "else" to TokenType.ELSE,
    "return" to TokenType.RETURN,
)

private val oneCharTokens = mapOf(
    ',' to TokenType.COMMA,
    ';' to TokenType.SEMICOLON,
    '(' to TokenType.LPAREN,
    ')' to TokenType.RPAREN,
    '{' to TokenType.LBRACE,
    '}' to TokenType.RBRACE,
    '=' to TokenType.ASSIGN,
    '+' to TokenType.PLUS,
    '-' to TokenType.MINUS,
    '/' to TokenType.SLASH,
    '*' to TokenType.ASTERISK,
    '<' to TokenType.LT,
    '>' to TokenType.GT,
    '!' to TokenType.BANG,
    '\u0000' to TokenType.EOF,
)

private val twoCharTokens = mapOf(
    "==" to TokenType.EQ,
    "!=" to TokenType.NOT_EQ,
)

fun lookupOneCharTokens(ch: Char) =
    oneCharTokens.getOrDefault(ch, TokenType.ILLEGAL)

fun lookupTwoCharTokens(str: String) =
    twoCharTokens.getOrDefault(str, TokenType.ILLEGAL)

fun lookupIdentifier(identifier: String) =
    keywords.getOrDefault(identifier, TokenType.IDENT)