package Triangle.TreeXMLWriter;

/**
 * Escritor del archivo XML representativo del AST del programa
 * Código facilitado por el profesor Ignacio Trejos de autoría de otros estudiantes
 * Modificaciones hechas por: Alonso Garita
 */

import Triangle.AbstractSyntaxTrees.AnyTypeDenoter;
import Triangle.AbstractSyntaxTrees.ArrayExpression;
import Triangle.AbstractSyntaxTrees.ArrayTypeDenoter;
import Triangle.AbstractSyntaxTrees.AssignCommand;
import Triangle.AbstractSyntaxTrees.BinaryExpression;
import Triangle.AbstractSyntaxTrees.BinaryOperatorDeclaration;
import Triangle.AbstractSyntaxTrees.BoolTypeDenoter;
import Triangle.AbstractSyntaxTrees.CallCommand;
import Triangle.AbstractSyntaxTrees.CallExpression;
import Triangle.AbstractSyntaxTrees.CharTypeDenoter;
import Triangle.AbstractSyntaxTrees.CharacterCase;
import Triangle.AbstractSyntaxTrees.CharacterExpression;
import Triangle.AbstractSyntaxTrees.CharacterLiteral;
import Triangle.AbstractSyntaxTrees.CommandBody;
import Triangle.AbstractSyntaxTrees.ComplexLongIdentifier;
import Triangle.AbstractSyntaxTrees.ConstActualParameter;
import Triangle.AbstractSyntaxTrees.ConstDeclaration;
import Triangle.AbstractSyntaxTrees.ConstFormalParameter;
import Triangle.AbstractSyntaxTrees.DotVname;
import Triangle.AbstractSyntaxTrees.EmptyActualParameterSequence;
import Triangle.AbstractSyntaxTrees.EmptyCommand;
import Triangle.AbstractSyntaxTrees.EmptyExpression;
import Triangle.AbstractSyntaxTrees.EmptyFormalParameterSequence;
import Triangle.AbstractSyntaxTrees.ErrorTypeDenoter;
import Triangle.AbstractSyntaxTrees.ForDo;
import Triangle.AbstractSyntaxTrees.ForIn;
import Triangle.AbstractSyntaxTrees.ForUntil;
import Triangle.AbstractSyntaxTrees.ForWhile;
import Triangle.AbstractSyntaxTrees.FuncActualParameter;
import Triangle.AbstractSyntaxTrees.FuncDeclaration;
import Triangle.AbstractSyntaxTrees.FuncFormalParameter;
import Triangle.AbstractSyntaxTrees.Identifier;
import Triangle.AbstractSyntaxTrees.IdentifierVarname;
import Triangle.AbstractSyntaxTrees.IfCommand;
import Triangle.AbstractSyntaxTrees.IfExpression;
import Triangle.AbstractSyntaxTrees.IntTypeDenoter;
import Triangle.AbstractSyntaxTrees.IntegerCase;
import Triangle.AbstractSyntaxTrees.IntegerExpression;
import Triangle.AbstractSyntaxTrees.IntegerLiteral;
import Triangle.AbstractSyntaxTrees.LetCommand;
import Triangle.AbstractSyntaxTrees.LetExpression;
import Triangle.AbstractSyntaxTrees.LiteralCaseCommand;
import Triangle.AbstractSyntaxTrees.MultipleActualParameterSequence;
import Triangle.AbstractSyntaxTrees.MultipleArrayAggregate;
import Triangle.AbstractSyntaxTrees.MultipleFieldTypeDenoter;
import Triangle.AbstractSyntaxTrees.MultipleFormalParameterSequence;
import Triangle.AbstractSyntaxTrees.MultipleRecordAggregate;
import Triangle.AbstractSyntaxTrees.Operator;
import Triangle.AbstractSyntaxTrees.PackCommandBody;
import Triangle.AbstractSyntaxTrees.Private;
import Triangle.AbstractSyntaxTrees.ProcActualParameter;
import Triangle.AbstractSyntaxTrees.ProcDeclaration;
import Triangle.AbstractSyntaxTrees.ProcFormalParameter;
import Triangle.AbstractSyntaxTrees.Program;
import Triangle.AbstractSyntaxTrees.Rec;
import Triangle.AbstractSyntaxTrees.RecordExpression;
import Triangle.AbstractSyntaxTrees.RecordTypeDenoter;
import Triangle.AbstractSyntaxTrees.RepeatDoUntilCommand;
import Triangle.AbstractSyntaxTrees.RepeatDoWhileCommand;
import Triangle.AbstractSyntaxTrees.RepeatTimesCommand;
import Triangle.AbstractSyntaxTrees.RepeatUntilCommand;
import Triangle.AbstractSyntaxTrees.RepeatWhileCommand;
import Triangle.AbstractSyntaxTrees.Select;
import Triangle.AbstractSyntaxTrees.SequentialCase;
import Triangle.AbstractSyntaxTrees.SequentialCaseLiteral;
import Triangle.AbstractSyntaxTrees.SequentialCaseRange;
import Triangle.AbstractSyntaxTrees.SequentialCommand;
import Triangle.AbstractSyntaxTrees.SequentialDeclaration;
import Triangle.AbstractSyntaxTrees.SequentialProcFunc;
import Triangle.AbstractSyntaxTrees.SimpleLongIdentifier;
import Triangle.AbstractSyntaxTrees.SimpleTypeDenoter;
import Triangle.AbstractSyntaxTrees.SimpleVarname;
import Triangle.AbstractSyntaxTrees.SimpleVname;
import Triangle.AbstractSyntaxTrees.SingleActualParameterSequence;
import Triangle.AbstractSyntaxTrees.SingleArrayAggregate;
import Triangle.AbstractSyntaxTrees.SingleCaseLiteral;
import Triangle.AbstractSyntaxTrees.SingleFieldTypeDenoter;
import Triangle.AbstractSyntaxTrees.SingleFormalParameterSequence;
import Triangle.AbstractSyntaxTrees.SingleRecordAggregate;
import Triangle.AbstractSyntaxTrees.SkipCommand;
import Triangle.AbstractSyntaxTrees.SubscriptVname;
import Triangle.AbstractSyntaxTrees.TypeDeclaration;
import Triangle.AbstractSyntaxTrees.UnaryExpression;
import Triangle.AbstractSyntaxTrees.UnaryOperatorDeclaration;
import Triangle.AbstractSyntaxTrees.VarActualParameter;
import Triangle.AbstractSyntaxTrees.VarDeclaration;
import Triangle.AbstractSyntaxTrees.VarDeclarationExpression;
import Triangle.AbstractSyntaxTrees.VarFormalParameter;
import Triangle.AbstractSyntaxTrees.Visitor;
import Triangle.AbstractSyntaxTrees.VnameExpression;
import Triangle.AbstractSyntaxTrees.WhileCommand;
import Triangle.AbstractSyntaxTrees.PackageIdenDecla;
import Triangle.AbstractSyntaxTrees.SequentialPackage;

import java.io.FileWriter;
import java.io.IOException;

public class WriterVisitor implements Visitor {

    private FileWriter fileWriter;

    WriterVisitor(FileWriter fileWriter) {
        this.fileWriter = fileWriter;
    }
    
    // Program Body
    @Override
    public Object visitPackCommandBody(PackCommandBody ast, Object o) {
        writeXMLLine("<ProgramBody>");
        ast.P.visit(this, null);
        ast.C.visit(this, null);
        writeXMLLine("</ProgramBody>");
        return null;
    }

    @Override
    public Object visitCommandBody(CommandBody ast, Object o) {
        writeXMLLine("<ProgramBody>");
        ast.C.visit(this, null);
        writeXMLLine("</ProgramBody>");
        return null;
    }

    // Commands
    public Object visitAssignCommand(AssignCommand ast, Object obj) {
        writeXMLLine("<AssignCommand>");
        ast.V.visit(this, null);
        ast.E.visit(this, null);
        writeXMLLine("</AssignCommand>");
        return null;
    }

    public Object visitCallCommand(CallCommand ast, Object obj) {
        writeXMLLine("<CallCommand>");
        ast.I.visit(this, null);
        ast.APS.visit(this, null);
        writeXMLLine("</CallCommand>");
        return null;
    }

    //En desuso
    public Object visitEmptyCommand(EmptyCommand ast, Object obj) {
        writeXMLLine("<EmptyCommand/>");
        return null;
    }
    
    @Override
    public Object visitForDo(ForDo ast, Object o) {
        writeXMLLine("<ForDoCommand>");
        ast.IE.visit(this, null);
        ast.E.visit(this, null);
        ast.C.visit(this, null);
        writeXMLLine("</ForDoCommand>");
        return null;
    }

    @Override
    public Object visitForWhile(ForWhile ast, Object o) {
        writeXMLLine("<ForWhileCommand>");
        ast.IE.visit(this, null);
        ast.E1.visit(this, null);
        ast.E2.visit(this, null);
        ast.C.visit(this, null);
        writeXMLLine("</ForWhileCommand>");
        return null;
    }

    //--- Write Fors ---
    @Override
    public Object visitForUntil(ForUntil ast, Object o) {
        writeXMLLine("<ForUntilCommand>");
        ast.IE.visit(this, null);
        ast.E1.visit(this, null);
        ast.E2.visit(this, null);
        ast.C.visit(this, null);
        writeXMLLine("</ForUntilCommand>");
        return null;
    }

    @Override
    public Object visitForIn(ForIn ast, Object o) {
        writeXMLLine("<ForInCommand>");
        ast.IE.visit(this, null);
        ast.C.visit(this, null);
        writeXMLLine("</ForInCommand>");
        return null;
    }
    //---------------

    public Object visitIfCommand(IfCommand ast, Object obj) {
        writeXMLLine("<IfCommand>");
        ast.E.visit(this, null);
        ast.C1.visit(this, null);
        ast.C2.visit(this, null);
        writeXMLLine("</IfCommand>");
        return null;
    }

    public Object visitLetCommand(LetCommand ast, Object obj) {
        writeXMLLine("<LetCommand>");
        ast.D.visit(this, null);
        ast.C.visit(this, null);
        writeXMLLine("</LetCommand>");
        return null;
    }

    //--- Write Repeats ---
    @Override
    public Object visitRepeatDoUntilCommand(RepeatDoUntilCommand ast, Object o) {
        writeXMLLine("<RepeatDoUntilCommand>");
        ast.C.visit(this, null);
        ast.E.visit(this, null);
        writeXMLLine("</RepeatDoUntilCommand>");
        return null;
    }

    @Override
    public Object visitRepeatDoWhileCommand(RepeatDoWhileCommand ast, Object o) {
        writeXMLLine("<RepeatDoWhileCommand>");
        ast.C.visit(this, null);
        ast.E.visit(this, null);
        writeXMLLine("</RepeatDoWhileCommand>");
        return null;
    }

    @Override
    public Object visitRepeatTimesCommand(RepeatTimesCommand ast, Object o) {
        writeXMLLine("<RepeatTimesCommand>");
        ast.E.visit(this, null);
        ast.C.visit(this, null);
        writeXMLLine("</RepeatTimesCommand>");
        return null;
    }

    @Override
    public Object visitRepeatUntilCommand(RepeatUntilCommand ast, Object o) {
        writeXMLLine("<RepeatUntilCommand>");
        ast.E.visit(this, null);
        ast.C.visit(this, null);
        writeXMLLine("</RepeatUntilCommand>");
        return null;
    }

    @Override
    public Object visitRepeatWhileCommand(RepeatWhileCommand ast, Object o) {
        writeXMLLine("<RepeatWhileCommand>");
        ast.E.visit(this, null);
        ast.C.visit(this, null);
        writeXMLLine("</RepeatWhileCommand>");
        return null;
    }
    //---------------------

    public Object visitSequentialCommand(SequentialCommand ast, Object obj) {
        writeXMLLine("<SequentialCommand>");
        ast.C1.visit(this, null);
        ast.C2.visit(this, null);
        writeXMLLine("</SequentialCommand>");
        return null;
    }
    
    //Select
    @Override
    public Object visitSelect(Select ast, Object o) {
        writeXMLLine("<SelectCommand>");
        ast.E.visit(this, null);
        ast.Ca.visit(this, null);
        if (ast.Co != null)
            ast.Co.visit(this, null);
        writeXMLLine("</SelectCommand>");
        return null;
    }
    
    //--- Skip Command ---
    @Override
    public Object visitSkipCommand(SkipCommand ast, Object o) {
        writeXMLLine("<SkipCommand/>");
        return null;
    }

    public Object visitWhileCommand(WhileCommand ast, Object obj) {
        writeXMLLine("<WhileCommand>");
        ast.E.visit(this, null);
        ast.C.visit(this, null);
        writeXMLLine("</WhileCommand>");
        return null;
    }

    // Expressions
    public Object visitArrayExpression(ArrayExpression ast, Object obj) {
        writeXMLLine("<ArrayExpression>");
        ast.AA.visit(this, null);
        writeXMLLine("</ArrayExpression>");
        return null;
    }

    public Object visitBinaryExpression(BinaryExpression ast, Object obj) {
        writeXMLLine("<BinaryExpression>");
        ast.E1.visit(this, null);
        ast.O.visit(this, null);
        ast.E2.visit(this, null);
        writeXMLLine("</BinaryExpression>");
        return null;
    }

    public Object visitCallExpression(CallExpression ast, Object obj) {
        writeXMLLine("<CallExpression>");
        ast.I.visit(this, null);
        ast.APS.visit(this, null);
        writeXMLLine("</CallExpression>");
        return null;
    }

    public Object visitCharacterExpression(CharacterExpression ast, Object obj) {
        writeXMLLine("<CharacterExpression>");
        ast.CL.visit(this, null);
        writeXMLLine("</CharacterExpression>");
        return null;
    }

    public Object visitEmptyExpression(EmptyExpression ast, Object obj) {
        writeXMLLine("<EmptyExpression/>");
        return null;
    }

    public Object visitIfExpression(IfExpression ast, Object obj) {
        writeXMLLine("<IfExpression>");
        ast.E1.visit(this, null);
        ast.E2.visit(this, null);
        ast.E3.visit(this, null);
        writeXMLLine("</IfExpression>");
        return null;
    }

    public Object visitIntegerExpression(IntegerExpression ast, Object obj) {
        writeXMLLine("<IntegerExpression>");
        ast.IL.visit(this, null);
        writeXMLLine("</IntegerExpression>");
        return null;
    }

    public Object visitLetExpression(LetExpression ast, Object obj) {
        writeXMLLine("<LetExpression>");
        ast.D.visit(this, null);
        ast.E.visit(this, null);
        writeXMLLine("</LetExpression>");
        return null;
    }

    public Object visitRecordExpression(RecordExpression ast, Object obj) {
        writeXMLLine("<RecordExpression>");
        ast.RA.visit(this, null);
        writeXMLLine("</RecordExpression>");
        return null;
    }

    public Object visitUnaryExpression(UnaryExpression ast, Object obj) {
        writeXMLLine("<UnaryExpression>");
        ast.O.visit(this, null);
        ast.E.visit(this, null);
        writeXMLLine("</UnaryExpression>");
        return null;
    }

    public Object visitVnameExpression(VnameExpression ast, Object obj) {
        writeXMLLine("<VnameExpression>");
        ast.V.visit(this, null);
        writeXMLLine("</VnameExpression>");
        return null;
    }
    
    //--- Cases ---
    @Override
    public Object visitIntegerCase(IntegerCase ast, Object o) {
        writeXMLLine("<IntegerCase>");
        ast.IC.visit(this, null);
        writeXMLLine("</IntegerCase>");
        return null;
    }

    @Override
    public Object visitSequentialCase(SequentialCase ast, Object o) {
        writeXMLLine("<SequentialCase>");
        ast.C1.visit(this, null);
        ast.C1.visit(this, null);
        writeXMLLine("</SequentialCase>");
        return null;
    }

    @Override
    public Object visitCharacterCase(CharacterCase ast, Object o) {
        writeXMLLine("<CharacterCase>");
        ast.CC.visit(this, null);
        writeXMLLine("</CharacterCase>");
        return null;
    }

    @Override
    public Object visitSequentialCaseLiteral(SequentialCaseLiteral ast, Object o) {
        writeXMLLine("<SequentialCaseLiteral>");
        ast.CL1.visit(this, null);
        ast.CL2.visit(this, null);
        writeXMLLine("</SequentialCaseLiteral>");
        return null;
    }

    @Override
    public Object visitSingleCaseLiteral(SingleCaseLiteral ast, Object o) {
        writeXMLLine("<SingleCaseLiteral>");
        ast.LC1.visit(this, null);
        writeXMLLine("</SingleCaseLiteral>");
        return null;
    }

    @Override
    public Object visitLiteralCaseCommand(LiteralCaseCommand ast, Object o) {
        writeXMLLine("<LiteralCaseCommand>");
        ast.LC.visit(this, null);
        ast.C.visit(this, null);
        writeXMLLine("</LiteralCaseCommand>");
        return null;
    }

    @Override
    public Object visitSequentialCaseRange(SequentialCaseRange ast, Object o) {
        writeXMLLine("<SequentialCaseRange>");
        ast.CR1.visit(this, null);
        ast.CR2.visit(this, null);
        writeXMLLine("</SequentialCaseRange>");
        return null;
    }

    // Declarations
    public Object visitBinaryOperatorDeclaration(BinaryOperatorDeclaration ast, Object obj) {
        writeXMLLine("<BinaryOperatorDeclaration>");
        ast.O.visit(this, null);
        ast.ARG1.visit(this, null);
        ast.ARG2.visit(this, null);
        ast.RES.visit(this, null);
        writeXMLLine("</BinaryOperatorDeclaration>");
        return null;
    }

    public Object visitConstDeclaration(ConstDeclaration ast, Object obj) {
        writeXMLLine("<ConstDeclaration>");
        ast.I.visit(this, null);
        ast.E.visit(this, null);
        writeXMLLine("</ConstDeclaration>");
        return null;
    }

    public Object visitFuncDeclaration(FuncDeclaration ast, Object obj) {
        writeXMLLine("<FuncDeclaration>");
        ast.I.visit(this, null);
        ast.FPS.visit(this, null);
        ast.T.visit(this, null);
        ast.E.visit(this, null);
        writeXMLLine("</FuncDeclaration>");
        return null;
    }
    
    @Override
    public Object visitPrivate(Private ast, Object o) {
        writeXMLLine("<PrivateDeclaration>");
        ast.D1.visit(this, null);
        ast.D2.visit(this, null);
        writeXMLLine("</PrivateDeclaration>");
        return null;
    }

    public Object visitProcDeclaration(ProcDeclaration ast, Object obj) {
        writeXMLLine("<ProcDeclaration>");
        ast.I.visit(this, null);
        ast.FPS.visit(this, null);
        ast.C.visit(this, null);
        writeXMLLine("</ProcDeclaration>");
        return null;
    }
    
    @Override
    public Object visitRec(Rec ast, Object o) {
        writeXMLLine("<RecursiveDeclaration>");
        ast.PF.visit(this, null);
        writeXMLLine("</RecursiveDeclaration>");
        return null;
    }

    public Object visitSequentialDeclaration(SequentialDeclaration ast, Object obj) {
        writeXMLLine("<SequentialDeclaration>");
        ast.D1.visit(this, null);
        ast.D2.visit(this, null);
        writeXMLLine("</SequentialDeclaration>");
        return null;
    }
    
    @Override
    public Object visitSequentialProcFunc(SequentialProcFunc ast, Object o) {
        writeXMLLine("<SequentialProcFunDeclaration>");
        ast.D1.visit(this, null);
        ast.D2.visit(this, null);
        writeXMLLine("</SequentialProcFunDeclaration>");
        return null;
    }

    public Object visitTypeDeclaration(TypeDeclaration ast, Object obj) {
        writeXMLLine("<TypeDeclaration>");
        ast.I.visit(this, null);
        ast.T.visit(this, null);
        writeXMLLine("</TypeDeclaration>");
        return null;
    }

    public Object visitUnaryOperatorDeclaration(UnaryOperatorDeclaration ast, Object obj) {
        writeXMLLine("<UnaryOperatorDeclaration>");
        ast.O.visit(this, null);
        ast.ARG.visit(this, null);
        ast.RES.visit(this, null);
        writeXMLLine("</UnaryOperatorDeclaration>");
        return null;
    }

    public Object visitVarDeclaration(VarDeclaration ast, Object obj) {
        writeXMLLine("<VarDeclaration>");
        ast.I.visit(this, null);
        ast.T.visit(this, null);
        writeXMLLine("</VarDeclaration>");
        return null;
    }

    @Override
    public Object visitVarDeclarationExpression(VarDeclarationExpression ast, Object o) {
        writeXMLLine("<VarDeclarationExpression>");
        ast.I.visit(this, null);
        ast.E.visit(this, null);
        writeXMLLine("</VarDeclarationExpression>");
        return null;
    }

    // Array Aggregates
    public Object visitMultipleArrayAggregate(MultipleArrayAggregate ast, Object obj) {
        writeXMLLine("<MultipleArrayAggregate>");
        ast.E.visit(this, null);
        ast.AA.visit(this, null);
        writeXMLLine("</MultipleArrayAggregate>");
        return null;
    }

    public Object visitSingleArrayAggregate(SingleArrayAggregate ast, Object obj) {
        writeXMLLine("<SingleArrayAggregate>");
        ast.E.visit(this, null);
        writeXMLLine("</SingleArrayAggregate>");
        return null;
    }

    // Record Aggregates
    public Object visitMultipleRecordAggregate(MultipleRecordAggregate ast, Object obj) {
        writeXMLLine("<MultipleRecordAggregate>");
        ast.I.visit(this, null);
        ast.E.visit(this, null);
        ast.RA.visit(this, null);
        writeXMLLine("</MultipleRecordAggregate>");
        return null;
    }

    public Object visitSingleRecordAggregate(SingleRecordAggregate ast, Object obj) {
        writeXMLLine("<SingleRecordAggregate>");
        ast.I.visit(this, null);
        ast.E.visit(this, null);
        writeXMLLine("</SingleRecordAggregate>");
        return null;
    }

    // Formal Parameters
    public Object visitConstFormalParameter(ConstFormalParameter ast, Object obj) {
        writeXMLLine("<ConstFormalParameter>");
        ast.I.visit(this, null);
        ast.T.visit(this, null);
        writeXMLLine("</ConstFormalParameter>");
        return null;
    }

    public Object visitFuncFormalParameter(FuncFormalParameter ast, Object obj) {
        writeXMLLine("<FuncFormalParameter>");
        ast.I.visit(this, null);
        ast.FPS.visit(this, null);
        ast.T.visit(this, null);
        writeXMLLine("</FuncFormalParameter>");
        return null;
    }

    public Object visitProcFormalParameter(ProcFormalParameter ast, Object obj) {
        writeXMLLine("<ProcFormalParameter>");
        ast.I.visit(this, null);
        ast.FPS.visit(this, null);
        writeXMLLine("</ProcFormalParameter>");
        return null;
    }

    public Object visitVarFormalParameter(VarFormalParameter ast, Object obj) {
        writeXMLLine("<VarFormalParameter>");
        ast.I.visit(this, null);
        ast.T.visit(this, null);
        writeXMLLine("</VarFormalParameter>");
        return null;
    }

    public Object visitEmptyFormalParameterSequence(EmptyFormalParameterSequence ast, Object obj) {
        writeXMLLine("<EmptyFormalParameterSequence/>");
        return null;
    }

    public Object visitMultipleFormalParameterSequence(MultipleFormalParameterSequence ast, Object obj) {
        writeXMLLine("<MultipleFormalParameterSequence>");
        ast.FP.visit(this, null);
        ast.FPS.visit(this, null);
        writeXMLLine("</MultipleFormalParameterSequence>");
        return null;
    }

    public Object visitSingleFormalParameterSequence(SingleFormalParameterSequence ast, Object obj) {
        writeXMLLine("<SingleFormalParameterSequence>");
        ast.FP.visit(this, null);
        writeXMLLine("</SingleFormalParameterSequence>");
        return null;
    }

    // Actual Parameters
    public Object visitConstActualParameter(ConstActualParameter ast, Object obj) {
        writeXMLLine("<ConstActualParameter>");
        ast.E.visit(this, null);
        writeXMLLine("</ConstActualParameter>");
        return null;
    }

    public Object visitFuncActualParameter(FuncActualParameter ast, Object obj) {
        writeXMLLine("<FuncActualParameter>");
        ast.I.visit(this, null);
        writeXMLLine("</FuncActualParameter>");
        return null;
    }

    public Object visitProcActualParameter(ProcActualParameter ast, Object obj) {
        writeXMLLine("<ProcActualParameter>");
        ast.I.visit(this, null);
        writeXMLLine("</ProcActualParameter>");
        return null;
    }

    public Object visitVarActualParameter(VarActualParameter ast, Object obj) {
        writeXMLLine("<VarActualParameter>");
        ast.V.visit(this, null);
        writeXMLLine("</VarActualParameter>");
        return null;
    }

    public Object visitEmptyActualParameterSequence(EmptyActualParameterSequence ast, Object obj) {
        writeXMLLine("<EmptyActualParameterSequence/>");
        return null;
    }

    public Object visitMultipleActualParameterSequence(MultipleActualParameterSequence ast, Object obj) {
        writeXMLLine("<MultipleActualParameterSequence>");
        ast.AP.visit(this, null);
        ast.APS.visit(this, null);
        writeXMLLine("</MultipleActualParameterSequence>");
        return null;
    }

    public Object visitSingleActualParameterSequence(SingleActualParameterSequence ast, Object obj) {
        writeXMLLine("<SingleActualParameterSequence>");
        ast.AP.visit(this, null);
        writeXMLLine("</SingleActualParameterSequence>");
        return null;
    }

    // Type Denoters
    public Object visitAnyTypeDenoter(AnyTypeDenoter ast, Object obj) {
        writeXMLLine("<AnyTypeDenoter/>");
        return null;
    }

    public Object visitArrayTypeDenoter(ArrayTypeDenoter ast, Object obj) {
        writeXMLLine("<ArrayTypeDenoter>");
        ast.IL.visit(this, null);
        ast.T.visit(this, null);
        writeXMLLine("</ArrayTypeDenoter>");
        return null;
    }

    public Object visitBoolTypeDenoter(BoolTypeDenoter ast, Object obj) {
        writeXMLLine("<BoolTypeDenoter/>");
        return null;
    }

    public Object visitCharTypeDenoter(CharTypeDenoter ast, Object obj) {
        writeXMLLine("<CharTypeDenoter/>");
        return null;
    }

    public Object visitErrorTypeDenoter(ErrorTypeDenoter ast, Object obj) {
        writeXMLLine("<ErrorTypeDenoter/>");
        return null;
    }

    public Object visitSimpleTypeDenoter(SimpleTypeDenoter ast, Object obj) {
        writeXMLLine("<SimpleTypeDenoter>");
        ast.I.visit(this, null);
        writeXMLLine("</SimpleTypeDenoter>");
        return null;
    }

    public Object visitIntTypeDenoter(IntTypeDenoter ast, Object obj) {
        writeXMLLine("<IntTypeDenoter/>");
        return null;
    }

    public Object visitRecordTypeDenoter(RecordTypeDenoter ast, Object obj) {
        writeXMLLine("<RecordTypeDenoter>");
        ast.FT.visit(this, null);
        writeXMLLine("</RecordTypeDenoter>");
        return null;
    }

    public Object visitMultipleFieldTypeDenoter(MultipleFieldTypeDenoter ast, Object obj) {
        writeXMLLine("<MultipleFieldTypeDenoter>");
        ast.I.visit(this, null);
        ast.T.visit(this, null);
        ast.FT.visit(this, null);
        writeXMLLine("</MultipleFieldTypeDenoter>");
        return null;
    }

    public Object visitSingleFieldTypeDenoter(SingleFieldTypeDenoter ast, Object obj) {
        writeXMLLine("<SingleFieldTypeDenoter>");
        ast.I.visit(this, null);
        ast.T.visit(this, null);
        writeXMLLine("</SingleFieldTypeDenoter>");
        return null;
    }

    // Literals, Identifiers and Operators
    public Object visitCharacterLiteral(CharacterLiteral ast, Object obj) {
        writeXMLLine("<CharacterLiteral value=\"" + ast.spelling + "\"/>");
        return null;
    }

    public Object visitIdentifier(Identifier ast, Object obj) {
        writeXMLLine("<Identifier value=\"" + ast.spelling + "\"/>");
        return null;
    }

    public Object visitIntegerLiteral(IntegerLiteral ast, Object obj) {
        writeXMLLine("<IntegerLiteral value=\"" + ast.spelling + "\"/>");
        return null;
    }

    public Object visitOperator(Operator ast, Object obj) {
        writeXMLLine("<Operator value=\"" + transformOperator(ast.spelling) + "\"/>");
        return null;
    }
    
    // Long-Identifiers
    
    @Override
    public Object visitsimpleLongIdentifier(SimpleLongIdentifier ast, Object o) {
        writeXMLLine("<SimpleIdentifier>");
        ast.I.visit(this, null);
        writeXMLLine("</SimpleIdentifier>");
        return null;
    }

    @Override
    public Object visitcomplexLongIdentifier(ComplexLongIdentifier ast, Object o) {
        writeXMLLine("<LongIdentifier>");
        ast.P.visit(this, null);
        ast.I.visit(this, null);
        writeXMLLine("</LongIdentifier>");
        return null;
    }

    // Value-or-variable names
    public Object visitDotVname(DotVname ast, Object obj) {
        writeXMLLine("<DotVname>");
        ast.V.visit(this, null);
        ast.I.visit(this, null);
        writeXMLLine("</DotVname>");
        return null;
    }
    
    @Override
    public Object visitIdentifierVarname(IdentifierVarname ast, Object o) {
        writeXMLLine("<IdentifierVarname>");
        ast.V.visit(this, null);
        ast.I.visit(this, null);
        writeXMLLine("</IdentifierVarname>");
        return null;
    }
    
    @Override
    public Object visitSimpleVarname(SimpleVarname ast, Object o) {
        writeXMLLine("<SimpleVarname>");
        ast.V.visit(this, null);
        writeXMLLine("</SimpleVarname>");
        return null;
    }

    public Object visitSimpleVname(SimpleVname ast, Object obj) {
        writeXMLLine("<SimpleVname>");
        ast.I.visit(this, null);
        writeXMLLine("</SimpleVname>");
        return null;
    }

    public Object visitSubscriptVname(SubscriptVname ast, Object obj) {
        writeXMLLine("<SubscriptVname>");
        ast.V.visit(this, null);
        ast.E.visit(this, null);
        writeXMLLine("</SubscriptVname>");
        return null;
    }
    
    //--- Packages ---
    @Override
    public Object visitpackageIdenDecla(PackageIdenDecla ast, Object o) {
        writeXMLLine("<PackageDeclaration>");
        ast.I.visit(this, null);
        ast.D.visit(this, null);
        writeXMLLine("</PackageDeclaration>");
        return null;
    }

    @Override
    public Object visitSequentialPackage(SequentialPackage ast, Object o) {
        writeXMLLine("<SequentialPackages>");
        ast.P1.visit(this, null);
        ast.P2.visit(this, null);
        writeXMLLine("</SequentialPackages>");
        return null;
    }

    // Programs
    public Object visitProgram(Program ast, Object obj) {
        writeXMLLine("<Program>");
        ast.B.visit(this, null);
        writeXMLLine("</Program>");
        return null;
    }

    private void writeXMLLine(String line) {
        try {
            fileWriter.write(line);
            fileWriter.write('\n');
        } catch (IOException e) {
            System.err.println("Error while writing file for print the AST");
            e.printStackTrace();
        }
    }

    // Convert the characters "<" & "<=" to their equivalents in html
    private String transformOperator(String operator) {
        if (operator.compareTo("<") == 0) {
            return "&lt;";
        } else if (operator.compareTo("<=") == 0) {
            return "&lt;=";
        } else {
            return operator;
        }
    }

    //Nuevos visits *AGREGAR*

    @Override
    public Object IdForExpression(Triangle.AbstractSyntaxTrees.IdForExpression aThis, Object o) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
