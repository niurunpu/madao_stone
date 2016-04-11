package ast;
import stone.Token;

public class NumberLiteral extends ASTLeaf
{
    public NumberLiteral(Token t) { super(t); }
    public int value() { return token.getNumber(); }
    public Object eval(Environment env) { return value(); }
}
