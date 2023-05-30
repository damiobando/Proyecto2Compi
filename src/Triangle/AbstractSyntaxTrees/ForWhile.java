/* Raquel Arguedas Sánchez          13/04/2023
 * Copyright (C) 1999, 2003 D.A. Watt and D.F. Brown
 * Dept. of Computing Science, University of Glasgow, Glasgow G12 8QQ Scotland
 * and School of Computer and Math Sciences, The Robert Gordon University,
 * St. Andrew Street, Aberdeen AB25 1HG, Scotland.
 * All rights reserved.
 */
package Triangle.AbstractSyntaxTrees;

import Triangle.SyntacticAnalyzer.SourcePosition;

/**
 *
 * @author raque
 */
public class ForWhile extends Command {

  public Declaration IE;
  public Expression E1, E2;
  public Command C;
  
  public ForWhile (Declaration ieAST, Expression e1AST, Expression e2AST,Command cAST,
                    SourcePosition thePosition) {
    super (thePosition);
    IE = ieAST;
    E1 = e1AST;
    E2 = e2AST;
    C = cAST; 
  }

  public Object visit(Visitor v, Object o) {
    return v.visitForWhile(this, o);
  }

}
