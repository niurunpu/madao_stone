package ast;
import stone.Token;
public class StringLiteral extends ASTLeaf
{
    public StringLiteral(Token t) { super(t); }
    public String value() { return token().getText(); }
    public Object eval(Environment env) { return value(); }
}
