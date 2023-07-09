import static java.lang.System.out;
import java_cup.runtime.Symbol;

%%

%class Lexer
%unicode
%public
%final
%integer
%line
%column
%cup

%eofval{
    return createSymbol(sym.EOF);
%eofval}

%{
    private StringBuffer sb = new StringBuffer();

    private Symbol createSymbol(int type) {
        return new Symbol(type, yyline+1, yycolumn+1);
    }

    private Symbol createSymbol(int type, Object value) {
        return new Symbol(type, yyline+1, yycolumn+1, value);
    }
%}

LineTerminator = \r|\n|\r\n
WhiteSpace     = {LineTerminator} | [ \t\f] 
Comment        = "\\\$\\\$" [^$] ~"\\\$\\\$" | "\\\$\\\$" "\\\$"+ "\\\$"
SingleComment  = "\\\$" [^$] ~{WhiteSpace}

Identifier     = [:jletter:] [:jletterdigit:]*
IntegerLiteral = 0 | [1-9][0-9]*
CharLiteral    = \'(\\.|[^\n'\\])\'? 

%state STRING
%state COMMENT
%state SINGLE_COMMENT

%%

<YYINITIAL> {
    /* reserved keywords */
    "and"                 { return createSymbol(sym.AND); }
    "puts"                { return createSymbol(sym.PUTS); }
    "char"                { return createSymbol(sym.CHAR); }
    "div"                 { return createSymbol(sym.DIV); }
    "do"                  { return createSymbol(sym.DO); }
    "else"                { return createSymbol(sym.ELSE); }
    "fun"                 { return createSymbol(sym.FUN); }
    "if"                  { return createSymbol(sym.IF); }
    "int"                 { return createSymbol(sym.INT); }
    "mod"                 { return createSymbol(sym.MOD); }
    "not"                 { return createSymbol(sym.NOT); }
    "nothing"             { return createSymbol(sym.NOTHING); }
    "or"                  { return createSymbol(sym.OR); }
    "ref"                 { return createSymbol(sym.REF); }
    "return"              { return createSymbol(sym.RETURN); }
    "then"                { return createSymbol(sym.THEN); }
    "var"                 { return createSymbol(sym.VAR); }
    "while"               { return createSymbol(sym.WHILE); }

    /* identifiers */ 
    {Identifier}          { return createSymbol(sym.IDENTIFIER, yytext()); }
    {IntegerLiteral}      { return createSymbol(sym.INTEGER_LITERAL, Integer.valueOf(yytext())); }

    \"                    { sb.setLength(0); yybegin(STRING); }
    {Comment}             { /* ignore */ }
    {SingleComment}       { /* ignore */ }

    /* operators */
    "="                   { return createSymbol(sym.EQ); }
    "+"                   { return createSymbol(sym.PLUS); }
    "-"                   { return createSymbol(sym.MINUS); }
    "*"                   { return createSymbol(sym.TIMES); }
    "("                   { return createSymbol(sym.LPAREN); }
    ")"                   { return createSymbol(sym.RPAREN); }
    ";"                   { return createSymbol(sym.SEMICOLON); }
    ":"                   { return createSymbol(sym.COLON); }
    "{"                   { return createSymbol(sym.LCURLY); }
    "}"                   { return createSymbol(sym.RCURLY); }
    "["                   { return createSymbol(sym.LBRACKET); }
    "]"                   { return createSymbol(sym.RBRACKET); }
    "<-"                  { return createSymbol(sym.SPACER); }
    "<="                  { return createSymbol(sym.LEQ); }
    ">="                  { return createSymbol(sym.GEQ); }
    "#"                   { return createSymbol(sym.NEQ); }
    "<"                   { return createSymbol(sym.LTHAN); }
    ">"                   { return createSymbol(sym.GTHAN); }
    ","                   { return createSymbol(sym.COMMA); }
    {CharLiteral}         { return createSymbol(sym.CHAR_LITERAL, yytext()); }

    /* whitespace */
    {WhiteSpace}          { /* ignore */ }
}

<STRING> {
    \"                    { yybegin(YYINITIAL);
                            return createSymbol(sym.STRING_LITERAL, sb.toString());
                          }

    [^\n\r\"\\]+          { sb.append(yytext()); }
    \\t                   { sb.append('\t'); }
    \\n                   { sb.append('\n'); }

    \\r                   { sb.append('\r'); }
    \\\"                  { sb.append('\"'); }
    \\                    { sb.append('\\'); }
}

/* error fallback */
[^]                       { throw new RuntimeException((yyline+1) + ":" + (yycolumn+1) + ": illegal character <"+ yytext()+">"); }