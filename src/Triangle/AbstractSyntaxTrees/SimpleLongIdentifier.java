/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Triangle.AbstractSyntaxTrees;

import Triangle.SyntacticAnalyzer.SourcePosition;

/**
 *
 * @author Alonso Garita
 */
public class SimpleLongIdentifier extends LongIdentifier {
    
    public SimpleLongIdentifier (Identifier iAST, SourcePosition thePosition) {
        super (thePosition);
        I = iAST;
    }
  
    public Identifier I;

    @Override
    public Object visit(Visitor v, Object o) {
        return v.visitsimpleLongIdentifier(this, o);
    }
    
}
