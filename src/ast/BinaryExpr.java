package ast;
import java.util.List;
import stone.StoneException;
import ast.StoneObject.AccessException;

public class BinaryExpr extends ASTList
{
    public BinaryExpr(List<ASTree> list) { super(list); }
    public ASTree left() { return child(0); }
    public ASTree right() { return child(2); }
    public static final int TRUE = 1;
    public static final int FALSE = 0;
    
    public String operator()
    {
    	return ((ASTLeaf)child(1)).token().getText();
    }
    public Object eval(Environment env)
    {
    	String op = operator();
    	if ("=".equals(op))
    	{
    		Object right = right().eval(env);
    		return computeAssign(env, right);
    	}
    	else
    	{
    		Object left = left().eval(env);
    		Object right = right().eval(env);
    		return computeOp(left, op, right);
    	}
    }
    protected Object computeAssign_normal(Environment env, Object right)
    {
    	ASTree l = left();
    	if (l instanceof Name)
    	{
    		env.put(((Name)l).name(), right);
    		return right;
    	}
    		else throw new StoneException("bad assignment", this);
    }
    //
    protected Object computeAssign(Environment env, Object rvalue)
    {
    	ASTree le = left();
    	if (le instanceof PrimaryExpr)
    	{
    		PrimaryExpr p = (PrimaryExpr)le;
    		if (p.hasPostfix(0) && p.postfix(0) instanceof Dot) // class
    		{
    			Object t = ((PrimaryExpr)le).evalSubExpr(env, 1);
    			if (t instanceof StoneObject)
    				return setField((StoneObject)t, (Dot)p.postfix(0), rvalue);
    		}
    		else if (p.hasPostfix(0) && p.postfix(0) instanceof ArrayRef) // array
    		{
    			Object a = ((PrimaryExpr) le).evalSubExpr(env, 1);
    			if (a instanceof Object[])
    			{
    				ArrayRef aref = (ArrayRef)p.postfix(0);
    				Object index = aref.index().eval(env);
    				if (index instanceof Integer)
    				{
    					((Object[])a)[(Integer)index] = rvalue;
    					return rvalue;
    				}
    			}
    			throw new StoneException("bad array access", this);
    		}
    	}
    	return computeAssign_normal(env, rvalue);

    }
    //
    protected Object setField(StoneObject obj, Dot expr, Object rvalue)
    {
    	String name = expr.name();
    	try
    	{
    		obj.write(name, rvalue);
    		return rvalue;
    	}
    	catch (AccessException e)
    	{
    		throw new StoneException("bad member access " + location() + " : " + name);
    	}
    }
    //
    protected Object computeOp(Object left, String op, Object right)
    {
    	if (left instanceof Integer && right instanceof Integer)
    		return computeNumber((Integer)left, op, (Integer)right);
    	else if (op.equals("+"))
    		return String.valueOf(left) + String.valueOf(right);
    	else if (op.equals("=="))
    	{
    		if (null == left)
    			return right == null ? TRUE : FALSE;
    		else
    			return left.equals(right) ? TRUE : FALSE;
    	}
    	else
    		throw new StoneException("bad type", this);
    }
    protected Object computeNumber(Integer left, String op, Integer right)
    {
    	int a = left.intValue();
    	int b = right.intValue();
    	if (op.equals("+"))
    		return a + b;
    	else if (op.equals("-"))
    		return a - b;
    	else if (op.equals("*"))
    		return a*b;
    	else if (op.equals("/"))
    		return a / b;
    	else if (op.equals("%"))
    		return a % b;
    	else if (op.equals("=="))
    		return a == b ? TRUE : FALSE;
    	else if (op.equals(">"))
    		return a > b ? TRUE : FALSE;
    	else if (op.equals("<"))
    		return a < b ? TRUE : FALSE;
    	else 
    		throw new StoneException("bad operator", this);
    }
}
