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
public class IdentifierVarname extends Vname {

  public IdentifierVarname (Identifier iAST, Vname vAST, SourcePosition thePosition) {
    super (thePosition);
    I = iAST;
    V = vAST;
  }

  public Object visit (Visitor v, Object o) {
    return v.visitIdentifierVarname(this, o);
  }

  public Identifier I;
  public Vname V;
}