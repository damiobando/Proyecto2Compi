/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Triangle.AbstractSyntaxTrees;

import Triangle.SyntacticAnalyzer.SourcePosition;

public class SingleCaseLiteral extends Case {

  public SingleCaseLiteral  (Case c1AST, SourcePosition thePosition) {
    super (thePosition);
    LC1 = c1AST;
  }

 public Object visit(Visitor v, Object o) {
    return v.visitSingleCaseLiteral(this, o);
  }

  public Case LC1;

    
}