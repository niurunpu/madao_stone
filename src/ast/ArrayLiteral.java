package ast;
import java.util.List;

public class ArrayLiteral extends ASTList
{
    public ArrayLiteral(List<ASTree> c) { super(c); }
    public int size() { return numChildren(); }
    public Object eval(Environment env)
    {
    	int s = numChildren();
    	Object[] res = new Object [s];
    	int i = 0;
    	for (ASTree t : this)
    		res[i++] = t.eval(env);
    	return res;
    }
}
