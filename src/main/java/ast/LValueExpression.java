// package ast;

// public class LValueExpression extends Expression {

//     private Expression expression1;
//     private Expression expression2;
//     private String identifier;

//     public LValueExpression(Expression expression1, Expression expression2) {
//         this.expression1 = expression1;
//         this.expression2 = expression2;
//     }

//     public LValueExpression(String identifier, Expression expression2) {
//         this.identifier = identifier;
//         this.expression2 = expression2;
//     }

//     public Expression getExpression1() {
//         return expression1;
//     }

//     public void setExpression1(Expression expression1) {
//         this.expression1 = expression1;
//     }

//     public Expression getExpression2() {
//         return expression2;
//     }

//     public void setExpression2(Expression expression2) {
//         this.expression2 = expression2;
//     }

//     public String getIdentifier() {
//         return identifier;
//     }

//     public void setIdentifier(String identifier) {
//         this.identifier = identifier;
//     }

//     @Override
//     public void accept(ASTVisitor visitor) throws ASTVisitorException {
//         visitor.visit(this);
//     }
// }
