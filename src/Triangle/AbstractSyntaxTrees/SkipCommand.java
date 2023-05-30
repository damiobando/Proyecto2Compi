/*
 * @(#)EmptyCommand.java                        2.1 2003/10/07
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
 * El comando Skip no hace nada más que indicar la ausencia de un commando. Se crea su AST para que aparezca como parte del
 * árbol sintáctico y se pueda mostrar en el IDE
 */

package Triangle.AbstractSyntaxTrees;

import Triangle.SyntacticAnalyzer.SourcePosition;

public class SkipCommand extends Command {

  public SkipCommand (SourcePosition thePosition) {
    super (thePosition);
  }

  public Object visit(Visitor v, Object o) {
    return v.visitSkipCommand(this, o);
  }
}
