/*
 * @(#)WhileCommand.java                        2.1 2003/10/07
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

/* Autor: Alonso Garita Granados
 * Compiladores e Intérpretes IS 2023
 * Se crea un AST por cada tipo de Repeat para facilitar más adelante su diferenciación a la hora de evaluar la forma en que el
 * bucle itera. El RepeatWhile primero evalua la condición, luego ejecuta y sigue ejecutando mientras sea verdadera.
 */

package Triangle.AbstractSyntaxTrees;

import Triangle.SyntacticAnalyzer.SourcePosition;

public class RepeatDoWhileCommand extends Command {

  public RepeatDoWhileCommand (Command cAST, Expression eAST, SourcePosition thePosition) {
    super (thePosition);
    C = cAST;
    E = eAST;
  }

  public Object visit(Visitor v, Object o) {
    return v.visitRepeatDoWhileCommand(this, o);
  }

  public Command C;
  public Expression E;
}
