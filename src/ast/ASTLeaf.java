package ast;
import java.util.Iterator;
import java.util.ArrayList;
import stone.Token;
import stone.StoneException;

public class ASTLeaf extends ASTree
{
    private static ArrayList<ASTree> empty = new ArrayList<ASTree>();
    protected Token token;
    public ASTLeaf(Token t) { token = t; }
    //
    public ASTree child(int i) { throw new IndexOutOfBoundsException(); }
    public int numChildren() { return 0;}
    public Iterator<ASTree> children() { return empty.iterator(); }
    public String location() { return "at line " + token.getLineNumber(); }
    
    public String toString() { return token.getText(); }
    public Token token() { return token; }
    public Object eval(Environment env)
    {
    	throw new StoneException("cannot eval " + toString(), this);
    }
}
