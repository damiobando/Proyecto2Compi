/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Triangle.AbstractSyntaxTrees;

import Triangle.SyntacticAnalyzer.SourcePosition;

/**
 *
 * @author maria
 */
public class SimpleVarname extends Vname {

  public SimpleVarname (Vname vAST, SourcePosition thePosition) {
    super (thePosition);
    V = vAST;
  }

  public Object visit (Visitor v, Object o) {
    return v.visitSimpleVarname(this, o);
  }

  public Vname V;
}