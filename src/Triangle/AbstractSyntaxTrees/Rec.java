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
public class Rec extends Declaration {
  
  public Rec (Declaration pfAST, SourcePosition thePosition) {
    super (thePosition);
    PF = pfAST;
  }

  public Declaration PF;
  
  @Override
  public Object visit(Visitor v, Object o) {
    return v.visitRec(this, o);
  }

}
