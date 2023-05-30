/*
 * @(#)Token.java                        2.1 2003/10/07
 *
 * Copyright (C) 1999, 2003 D.A. Watt and D.F. Brown
 * Dept. of Computing Science, University of Glasgow, Glasgow G12 8QQ Scotland
 * and School of Computer and Math Sciences, The Robert Gordon University,
 * St. Andrew Street, Aberdeen AB25 1HG, Scotland.
 * All rights reserved.
 *
 * This software is provided free for educational use only. It may
 * not be used for commercial purposes without the prior written permission
 * of the authors.
 */

package Triangle.SyntacticAnalyzer;


final class Token extends Object {

  protected int kind;
  protected String spelling;
  protected SourcePosition position;

  public Token(int kind, String spelling, SourcePosition position) {

    if (kind == Token.IDENTIFIER) {
      int currentKind = firstReservedWord;
      boolean searching = true;
      while (searching) {
        int comparison = tokenTable[currentKind].compareTo(spelling);
         //System.out.println(comparison + " token:" +tokenTable[currentKind] + " spelling:" +spelling);
        if (comparison == 0) {
          this.kind = currentKind;
          searching = false;
        } else if (comparison > 0 || currentKind == lastReservedWord) {
          this.kind = Token.IDENTIFIER;
          searching = false;
        } else {
          currentKind ++;
        }
      }
    } else
      this.kind = kind;

    this.spelling = spelling;
    this.position = position;

  }

  public static String spell (int kind) {
    return tokenTable[kind];
  }
  
  public static int getKind (String search) {
      for (int i = 0; i<tokenTable.length; i++){
          if (tokenTable[i].equals(search)) return i;
      }
      return -1;
  }

  public String toString() {
    return "Kind=" + kind + ", spelling=" + spelling +
      ", position=" + position;
  }

  // Token classes...

  public static final int

    // literals, identifiers, operators...
    INTLITERAL	= 0,
    CHARLITERAL	= 1,
    IDENTIFIER	= 2,
    OPERATOR	= 3,

    // reserved words - must be in alphabetical order...
    ARRAY		= 4,
    //BEGIN		= #, //Eliminar la palabra reservada begin
    CONST		= 5,
    DO			= 6,
    ELSE		= 7,
    END			= 8,
    FOR                 = 9, //For - Raque
    FROM                = 10, //Raque
    FUNC		= 11,
    IF			= 12,
    IN			= 13,
    LET			= 14,
    OF			= 15,
    PACKAGE             = 16,   
    PRIVATE             = 17,
    PROC		= 18,
    REC                 = 19,
    RECORD		= 20,
    REPEAT              = 21, //Repeat... - Alonso
    SELECT              = 22, //Raque
    SKIP                = 23, //Skip - Alonso
    THEN		= 24,
    TIMES               = 25, //Repeat times - Alonso
    TYPE		= 26,
    UNTIL               = 27, //Repeat y For Until - Alonso
    VAR			= 28,
    WHEN                = 29, //When - Mari y Toño
    WHILE		= 30,

    // punctuation...
    DOT			= 31,
    TWODOTS             = 32, //Raque
    COLON		= 33,
    SEMICOLON           = 34,
    COMMA		= 35,
    BECOMES		= 36,
    IS			= 37,
    PIPE                = 38,
    DOLAR               = 39,

    // brackets...
    LPAREN		= 40,
    RPAREN		= 41,
    LBRACKET            = 42,
    RBRACKET            = 43,
    LCURLY		= 44,
    RCURLY		= 45,

    // special tokens...
    EOT			= 46,
    ERROR		= 47;

  private static String[] tokenTable = new String[] {
    "<int>",
    "<char>",
    "<identifier>",
    "<operator>",
    "array",
    "const",
    "do",
    "else",
    "end",
    "for",
    "from",
    "func",
    "if",
    "in",
    "let",
    "of",
    "package",
    "private",
    "proc",
    "rec",
    "record",
    "repeat", //Repeat - Alonso
    "select", //Raque
    "skip", //Skip - Alonso
    "then",
    "times", //Times - Alonso
    "type",
    "until", //Until - Alonso
    "var",
    "when", //Mari y Toño
    "while",
    ".",
    "..", //Raque
    ":",
    ";",
    ",",
    ":=",
    "~",
    "|",
    "$",
    "(",
    ")",
    "[",
    "]",
    "{",
    "}",
    "",
    "<error>"
  };

  private final static int	firstReservedWord = Token.ARRAY,
  				lastReservedWord  = Token.WHILE;

}
