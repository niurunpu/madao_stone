package ast;
import java.util.List;
import java.util.Iterator;
import stone.StoneException;

public class ASTList extends ASTree
{
    protected List<ASTree> children;
    public ASTList(List<ASTree> list) { children = list; }
    public ASTree child(int i) { return children.get(i); }
    public int numChildren() { return children.size(); }
    public Iterator<ASTree> children() { return children.iterator(); }
    public String toString()
    {
    	StringBuilder sb = new StringBuilder();
    	sb.append('(');
    	String sep = "";
    	for (ASTree t : children)
    	{
    		sb.append(sep);
    		sep = " ";
    		sb.append(t.toString());
    	}
    	return sb.append(')').toString();
    }
    
    public String location()
    {
    	for (ASTree t : children)
    	{
    		String pos = t.location();
    		if (pos != null)
    			return pos;
    	}
    	return null;
    }
    public Object eval(Environment env)
    {
    	throw new StoneException("cannot eval: " + toString(), this);
   }    
}
