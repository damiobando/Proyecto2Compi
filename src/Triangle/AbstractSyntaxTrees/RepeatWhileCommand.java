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
 * Compiladores e Int�rpretes IS 2023
 * Se crea un AST por cada tipo de Repeat para facilitar m�s adelante su diferenciaci�n a la hora de evaluar la forma en que el
 * bucle itera. El RepeatWhile primero evalua la condici�n, luego ejecuta y sigue ejecutando mientras sea verdadera.
 */

package Triangle.AbstractSyntaxTrees;

import Triangle.SyntacticAnalyzer.SourcePosition;

public class RepeatWhileCommand extends Command {

  public RepeatWhileCommand (Expression eAST, Command cAST, SourcePosition thePosition) {
    super (thePosition);
    E = eAST;
    C = cAST;
  }

  public Object visit(Visitor v, Object o) {
    return v.visitRepeatWhileCommand(this, o);
  }

  public Expression E;
  public Command C;
}
