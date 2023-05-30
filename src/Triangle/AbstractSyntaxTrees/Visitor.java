/*
 * @(#)Visitor.java                        2.1 2003/10/07
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

package Triangle.AbstractSyntaxTrees;

public interface Visitor {

  // Commands
  public abstract Object visitAssignCommand(AssignCommand ast, Object o);
  public abstract Object visitCallCommand(CallCommand ast, Object o);
  public abstract Object visitEmptyCommand(EmptyCommand ast, Object o);
  public abstract Object visitIfCommand(IfCommand ast, Object o);
  public abstract Object visitLetCommand(LetCommand ast, Object o);
  public abstract Object visitRepeatDoUntilCommand(RepeatDoUntilCommand ast, Object o); //RepeatDoUntil - Alonso
  public abstract Object visitRepeatDoWhileCommand(RepeatDoWhileCommand ast, Object o); //RepeatDoWhile - Alonso
  public abstract Object visitRepeatTimesCommand(RepeatTimesCommand ast, Object o); //RepeatTimes - Alonso
  public abstract Object visitRepeatUntilCommand(RepeatUntilCommand ast, Object o); //RepeatUntil - Alonso
  public abstract Object visitRepeatWhileCommand(RepeatWhileCommand ast, Object o); //RepeatWhile - Alonso
  public abstract Object visitSequentialCommand(SequentialCommand ast, Object o);
  public abstract Object visitSkipCommand(SkipCommand ast, Object o); //Skip visitor - Alonso
  public abstract Object visitWhileCommand(WhileCommand ast, Object o);
  public abstract Object visitForDo(ForDo ast, Object o);
  public abstract Object visitForWhile(ForWhile ast, Object o);
  public abstract Object visitForUntil(ForUntil ast, Object o);
  public abstract Object visitForIn(ForIn ast, Object o);
  public abstract Object visitSelect(Select ast, Object o);
  
          

  
  
  //nuevo
  // Case
  
  //public abstract Object visitSequentialCase(SequentialCaseLiteral ast, Object o);

  // Expressions
  public abstract Object visitArrayExpression(ArrayExpression ast, Object o);
  public abstract Object visitBinaryExpression(BinaryExpression ast, Object o);
  public abstract Object visitCallExpression(CallExpression ast, Object o);
  public abstract Object visitCharacterExpression(CharacterExpression ast, Object o);
  public abstract Object visitEmptyExpression(EmptyExpression ast, Object o);
  public abstract Object visitIfExpression(IfExpression ast, Object o);
  public abstract Object visitIntegerExpression(IntegerExpression ast, Object o);
  public abstract Object visitLetExpression(LetExpression ast, Object o);
  public abstract Object visitRecordExpression(RecordExpression ast, Object o);
  public abstract Object visitUnaryExpression(UnaryExpression ast, Object o);
  public abstract Object visitVnameExpression(VnameExpression ast, Object o);

  // Declarations
  public abstract Object visitBinaryOperatorDeclaration(BinaryOperatorDeclaration ast, Object o);
  public abstract Object visitConstDeclaration(ConstDeclaration ast, Object o);
  public abstract Object visitFuncDeclaration(FuncDeclaration ast, Object o);
  public abstract Object visitRec(Rec ast, Object o);
  public abstract Object visitPrivate(Private ast, Object o);
  public abstract Object visitProcDeclaration(ProcDeclaration ast, Object o);
  public abstract Object visitSequentialDeclaration(SequentialDeclaration ast, Object o);
  public abstract Object visitSequentialProcFunc(SequentialProcFunc ast, Object o);
  public abstract Object visitTypeDeclaration(TypeDeclaration ast, Object o);
  public abstract Object visitUnaryOperatorDeclaration(UnaryOperatorDeclaration ast, Object o);
  public abstract Object visitVarDeclaration(VarDeclaration ast, Object o);
  public Object visitVarDeclarationExpression(VarDeclarationExpression ast, Object o);

  // Array Aggregates
  public abstract Object visitMultipleArrayAggregate(MultipleArrayAggregate ast, Object o);
  public abstract Object visitSingleArrayAggregate(SingleArrayAggregate ast, Object o);

  // Record Aggregates
  public abstract Object visitMultipleRecordAggregate(MultipleRecordAggregate ast, Object o);
  public abstract Object visitSingleRecordAggregate(SingleRecordAggregate ast, Object o);

  // Formal Parameters
  public abstract Object visitConstFormalParameter(ConstFormalParameter ast, Object o);
  public abstract Object visitFuncFormalParameter(FuncFormalParameter ast, Object o);
  public abstract Object visitProcFormalParameter(ProcFormalParameter ast, Object o);
  public abstract Object visitVarFormalParameter(VarFormalParameter ast, Object o);

  public abstract Object visitEmptyFormalParameterSequence(EmptyFormalParameterSequence ast, Object o);
  public abstract Object visitMultipleFormalParameterSequence(MultipleFormalParameterSequence ast, Object o);
  public abstract Object visitSingleFormalParameterSequence(SingleFormalParameterSequence ast, Object o);

  // Actual Parameters
  public abstract Object visitConstActualParameter(ConstActualParameter ast, Object o);
  public abstract Object visitFuncActualParameter(FuncActualParameter ast, Object o);
  public abstract Object visitProcActualParameter(ProcActualParameter ast, Object o);
  public abstract Object visitVarActualParameter(VarActualParameter ast, Object o);

  public abstract Object visitEmptyActualParameterSequence(EmptyActualParameterSequence ast, Object o);
  public abstract Object visitMultipleActualParameterSequence(MultipleActualParameterSequence ast, Object o);
  public abstract Object visitSingleActualParameterSequence(SingleActualParameterSequence ast, Object o);

  // Type Denoters
  public abstract Object visitAnyTypeDenoter(AnyTypeDenoter ast, Object o);
  public abstract Object visitArrayTypeDenoter(ArrayTypeDenoter ast, Object o);
  public abstract Object visitBoolTypeDenoter(BoolTypeDenoter ast, Object o);
  public abstract Object visitCharTypeDenoter(CharTypeDenoter ast, Object o);
  public abstract Object visitErrorTypeDenoter(ErrorTypeDenoter ast, Object o);
  public abstract Object visitSimpleTypeDenoter(SimpleTypeDenoter ast, Object o);
  public abstract Object visitIntTypeDenoter(IntTypeDenoter ast, Object o);
  public abstract Object visitRecordTypeDenoter(RecordTypeDenoter ast, Object o);

  public abstract Object visitMultipleFieldTypeDenoter(MultipleFieldTypeDenoter ast, Object o);
  public abstract Object visitSingleFieldTypeDenoter(SingleFieldTypeDenoter ast, Object o);

  // Literals, Identifiers and Operators
  public abstract Object visitCharacterLiteral(CharacterLiteral ast, Object o);
  public abstract Object visitIdentifier(Identifier ast, Object o);
  public abstract Object visitIntegerLiteral(IntegerLiteral ast, Object o);
  public abstract Object visitOperator(Operator ast, Object o);

  // Value-or-variable names
  public abstract Object visitDotVname(DotVname ast, Object o);
  public abstract Object visitSimpleVname(SimpleVname ast, Object o);
  public abstract Object visitSubscriptVname(SubscriptVname ast, Object o);
  public Object visitIdentifierVarname(IdentifierVarname ast, Object o);
  public Object visitSimpleVarname(SimpleVarname ast, Object o);

  // Programs
  public abstract Object visitProgram(Program cAST, Object o);
  
  // Case
  public Object visitIntegerCase(IntegerCase ast, Object o);
  public Object visitSequentialCase(SequentialCase ast, Object o);
  public Object visitCharacterCase(CharacterCase ast, Object o);
  public Object visitSequentialCaseLiteral(SequentialCaseLiteral ast, Object o);
  public Object visitSingleCaseLiteral(SingleCaseLiteral ast, Object o);
  public Object visitLiteralCaseCommand(LiteralCaseCommand ast, Object o);
  public Object visitSequentialCaseRange(SequentialCaseRange ast, Object o);

  // Packages
 public Object visitpackageIdenDecla(PackageIdenDecla aThis, Object o);
 public Object visitSequentialPackage(SequentialPackage aThis, Object o);
 
 // Long-Identifier
 public Object visitsimpleLongIdentifier(SimpleLongIdentifier aThis, Object o);
 public Object visitcomplexLongIdentifier(ComplexLongIdentifier aThis, Object o);

   
// Body
public Object visitPackCommandBody(PackCommandBody aThis, Object o);
public Object visitCommandBody(CommandBody aThis, Object o);

    public Object IdForExpression(IdForExpression aThis, Object o);

    



  
}
