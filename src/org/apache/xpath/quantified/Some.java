/* Generated By:JJTree: Do not edit this line. Some.java */

package org.apache.xpath.quantified;

import javax.xml.transform.TransformerException;

import org.apache.xpath.Expression;
import org.apache.xpath.ExpressionOwner;
import org.apache.xpath.VariableComposeState;
import org.apache.xpath.XPathContext;
import org.apache.xpath.XPathVisitor;
import org.apache.xpath.objects.XObject;

public class Some extends Expression implements ExpressionOwner
{
	
  public Some() {
    super();
  }

  /** Accept the visitor. **/
  public Object jjtAccept(org.apache.xpath.parser.XPathVisitor visitor, Object data) {
    return visitor.visit(this, data);
  }
  
  /**
   * @see Expression#deepEquals(Expression)
   */
  public boolean deepEquals(Expression expr)
  {
    return false;
  }
  /**
   * @see Expression#fixupVariables(Vector, int)
   */
  public void fixupVariables(VariableComposeState vcs)
  {
  }

  /**
   * @see Expression#execute(XPathContext)
   */
  public XObject execute(XPathContext xctxt) throws TransformerException
  {
    return null;
  }

  /**
   * @see XPathVisitable#callVisitors(ExpressionOwner, XPathVisitor)
   */
  public void callVisitors(ExpressionOwner owner, XPathVisitor visitor)
  {
  }

  /**
   * @see ExpressionOwner#getExpression()
   */
  public Expression getExpression()
  {
    return null;
  }

  /**
   * @see ExpressionOwner#setExpression(Expression)
   */
  public void setExpression(Expression exp)
  {
  }

}
