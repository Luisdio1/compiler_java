// package ast;

// public class ArrayExpression extends Expression {

//     private String identifier;
//     private String literal;
//     private Expression expression;

//     public ArrayExpression(String identifier, Expression expression) {
//         this.identifier = identifier;
//         this.expression = expression;
//     }

//     public ArrayExpression(Expression expression, String literal) {
//         this.literal = literal;
//         this.expression = expression;
//     }

//     public String getIdentifier() {
//         return identifier;
//     }

//     public Expression getExpression() {
//         return expression;
//     }

//     public String getLiteral() {
//         return literal;
//     }

//     @Override
//     public void accept(ASTVisitor visitor) throws ASTVisitorException {
//         visitor.visit(this);
//     }
    
// }
