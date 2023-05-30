/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Triangle.AbstractSyntaxTrees;

import Triangle.SyntacticAnalyzer.SourcePosition;

/**
 *
 * @author antho
 */
public class ComplexLongIdentifier extends LongIdentifier {

    public ComplexLongIdentifier (Identifier pIdentifier, Identifier iAST, SourcePosition thePosition) {
        super (thePosition);
        I = iAST;
        P = pIdentifier;
    }
    
    @Override
    public Object visit(Visitor v, Object o) {
        return v.visitcomplexLongIdentifier(this, o);
    }
    
    public Identifier I;
    public Identifier P;
    
    
}
