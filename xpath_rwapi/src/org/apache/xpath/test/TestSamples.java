/*
 * The Apache Software License, Version 1.1
 * 
 * Copyright (c) 2002-2003 The Apache Software Foundation. All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met: 1.
 * Redistributions of source code must retain the above copyright notice, this
 * list of conditions and the following disclaimer. 2. Redistributions in
 * binary form must reproduce the above copyright notice, this list of
 * conditions and the following disclaimer in the documentation and/or other
 * materials provided with the distribution. 3. The end-user documentation
 * included with the redistribution, if any, must include the following
 * acknowledgment: "This product includes software developed by the Apache
 * Software Foundation (http://www.apache.org/)." Alternately, this
 * acknowledgment may appear in the software itself, if and wherever such
 * third-party acknowledgments normally appear. 4. The names "Xalan" and
 * "Apache Software Foundation" must not be used to endorse or promote products
 * derived from this software without prior written permission. For written
 * permission, please contact apache@apache.org. 5. Products derived from this
 * software may not be called "Apache", nor may "Apache" appear in their name,
 * without prior written permission of the Apache Software Foundation.
 * 
 * THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED WARRANTIES,
 * INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 * FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE
 * APACHE SOFTWARE FOUNDATION OR ITS CONTRIBUTORS BE LIABLE FOR ANY DIRECT,
 * INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
 * THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 * ====================================================================
 * 
 * This software consists of voluntary contributions made by many individuals
 * on behalf of the Apache Software Foundation and was originally based on
 * software copyright (c) 2002, International Business Machines Corporation.,
 * http://www.ibm.com. For more information on the Apache Software Foundation,
 * please see <http://www.apache.org/> .
 */
package org.apache.xpath.test;

import java.io.StringReader;
import java.math.BigInteger;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.xml.QName;
import org.apache.xpath.XPath20Factory;
import org.apache.xpath.expression.Expr;
import org.apache.xpath.expression.ExpressionFactory;
import org.apache.xpath.expression.NodeTest;
import org.apache.xpath.expression.PathExpr;
import org.apache.xpath.expression.StepExpr;
import org.apache.xpath.impl.ExprImpl;
import org.apache.xpath.impl.parser.ParseException;
import org.apache.xpath.impl.parser.SimpleNode;
import org.apache.xpath.impl.parser.XPath;
import org.apache.xpath.impl.parser.XPathTreeConstants;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

/**
 * Simple unit test for various samples; adhoc.
 */
public class TestSamples
{

	public static String TEST_SAMPLES_XML;
	
	private static boolean m_dumpTree;

	public TestSamples()
	{
		TEST_SAMPLES_XML = "src/org/apache/xpath/test/TestSamples.xml";
	}

	public void testXPath()
	{
		try
		{
			completude();

			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.parse(new InputSource(TEST_SAMPLES_XML));
			doc.normalize();

			Element tests = doc.getDocumentElement();
			NodeList testElems = tests.getChildNodes();
			int nChildren = testElems.getLength();
			int testid = 0;
			boolean testSuccess = true;

			for (int i = 0; i < nChildren; i++)
			{
				org.w3c.dom.Node node = testElems.item(i);

				if (org.w3c.dom.Node.ELEMENT_NODE == node.getNodeType())
				{
					testid++;
					testSuccess &= testNode(node, testid);
				}
			}

			if (testSuccess)
			{
				System.out.println("Parsing Test successful.");
			}
			else
			{
				System.out.println("Parsing Test fails!");
			}

			System.out.println(
				"arraycopy calls number: " + SimpleNode.m_arrayCopyCount);

			// Test adhoc manual creation
			ExpressionFactory exprFct =
				XPath20Factory.newInstance().newExpressionFactory();

			Expr expr = exprFct.createExpr("child::tutu[10]");
			System.out.println("tutu[10] =? " + expr.getString(true));

			PathExpr pathExpr = exprFct.createPathExpr(true);
			System.out.println("/ =? " + pathExpr.getString(true));

			NodeTest nt = exprFct.createNameTest(new QName("toto"));
			pathExpr.addOperand(
				exprFct.createStepExpr(StepExpr.AXIS_CHILD, nt));
			System.out.println("/toto =? " + pathExpr.getString(true));
			System.out.println("/child::toto =? " + pathExpr.getString(false));

			nt = exprFct.createNameTest(new QName("titi"));
			pathExpr.addOperand(
				exprFct.createStepExpr(StepExpr.AXIS_DESCENDANT, nt));

			System.out.println(
				"/toto/descendant::titi =? " + pathExpr.getString(true));
			System.out.println(
				"/child::toto/descendant::titi =? "
					+ pathExpr.getString(false));

			StepExpr se = (StepExpr) pathExpr.getOperand(0); // first step
			se.appendPredicate(
				exprFct.createIntegerLiteralExpr(BigInteger.valueOf(50)));
			System.out.println(
				"/toto[50]/descendant::titi =? " + pathExpr.getString(true));
			System.out.println(
				"/child::toto[50]/descendant::titi =? "
					+ pathExpr.getString(false));

			pathExpr.removeOperand(se);
			System.out.println(
				"/descendant::titi =? " + pathExpr.getString(true));
			System.out.println(
				"/descendant::titi =? " + pathExpr.getString(false));

			System.out.println(
				"clone=" + pathExpr.cloneExpression().getString(false));

		}
		catch (Exception e)
		{
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}

	public void completude()
	{
		//		first: check if the implementation is complete
		XPath p = new XPath(new StringReader("null"));
		int nbOfToken = XPathTreeConstants.jjtNodeName.length;
		for (int i = 0; i < nbOfToken; i++)
		{
			org.apache.xpath.impl.parser.Node n = SimpleNode.jjtCreate(p, i);
		}

	}

	public SimpleNode parse(String xpathString, boolean isPattern)
		throws ParseException
	{
		SimpleNode tree;
		XPath parser = new XPath(new StringReader(xpathString));

		if (isPattern)
		{
			tree = parser.MatchPattern();
		}
		else
		{
			tree = parser.XPath2();
		}
		return tree;
	}

	public boolean testNode(Node node, int testid) throws Exception
	{
		boolean testOK = true;
		String xpathString = ((Element) node).getAttribute("value");
		System.out.println("TestSamples[" + testid + "]: " + xpathString);

		SimpleNode tree;

		boolean isPattern =
			"true".equals(((Element) node).getAttribute("pattern"));
		try
		{
			tree = parse(xpathString, isPattern);

			if ("false".equals(((Element) node).getAttribute("valid")))
			{
				// the parser should have raise an exception
				System.err.println(
					"The expression has not been detected as invalid!");
				System.err.flush();
				testOK = false;
			}
			else
			{
				if (SimpleNode.PRODUCE_RAW_TREE)
				{
					//  if (dumpTree)
					//{
					tree.dump("|");

					//   }
				}
				else
				{
					Expr expr = (Expr) tree.jjtGetChild(0);
					((ExprImpl) expr).jjtSetParent(null);

					// Gets the reference AST to compare with
					NodeList astNodes =
						((Element) node).getElementsByTagName("ast");

					if ((astNodes != null) && (astNodes.getLength() >= 1))
					{
						Node astNode = astNodes.item(0);

						if (!checkAST((SimpleNode) expr,
							(Element) ((Element) astNode).getElementsByTagName(
								"node").item(
								0)))
						{
							System.err.println(
								"Generated AST doesn't match the reference one");
							System.err.flush();
							tree.dump("|");

							// Produce the raw tree
							System.err.println("Raw tree is");
							System.err.flush();
							SimpleNode.PRODUCE_RAW_TREE = true;

							tree = parse(xpathString, isPattern);

							tree.dump("|");
							System.out.flush();
							SimpleNode.PRODUCE_RAW_TREE = false;
						}
					}
					else
					{
						System.err.println("No reference AST provided");
						System.err.flush();
					}

					// Test round-trip (abbreviated form)
					
					String ab = expr.getString(true);
					String norm =
						((Element) node).getAttribute("normalized-round-trip");

					if ("".equals(norm))
					{
						norm = xpathString;
					}
					
					//	Test round-trip (non-abbreviated form)
					
					String rtna = expr.getString(false);
					String normna =((Element) node).getAttribute("non-abbreviated-round-trip");
					if ("".equals(normna))
					{	
						normna = rtna;
					}
					
					if (!ab.equals(norm) || !rtna.equals(normna))
					{
						System.err.print(
							"Bad external or internal representation: ");
						if (!ab.equals(norm))
						{
							System.err.println(ab + " !=  " + norm + " ~ Abbreviated REF~");
						}
						else
						{
							System.err.println(rtna + "  !=  " + normna + " ~ Non-Abbreviated REF~");
						}
						System.err.flush();
						tree.dump("|");
						System.out.flush();

						// Produce the raw tree
						System.err.println("Raw tree is");
						System.err.flush();
						SimpleNode.PRODUCE_RAW_TREE = true;

						tree = parse(xpathString, isPattern);

						tree.dump("|");
						System.out.flush();
						SimpleNode.PRODUCE_RAW_TREE = false;
						testOK = false;
					}
				}
			}
		}
		catch (ParseException e)
		{
			System.err.println("Parsing error occurs: " + e.getMessage());
		}
		catch (RuntimeException e)
		{
			e.printStackTrace(System.err);

			System.err.println("Raw tree is");
			SimpleNode.PRODUCE_RAW_TREE = true;

			tree = parse(xpathString, isPattern);

			tree.dump("|");

			SimpleNode.PRODUCE_RAW_TREE = false;
			testOK = false;
		}

		return testOK;
	}

	/**
	 * Check that the given internal representation of expr match the reference
	 * AST
	 * 
	 * @param expr
	 * @param refAST
	 * 
	 * @return boolean
	 */
	public boolean checkAST(SimpleNode expr, Element refAST)
	{
		String nodeName =
			refAST.getAttributes().getNamedItem("name").getNodeValue();

		if (XPathTreeConstants.jjtNodeName[expr.getId()].equals(nodeName))
		{
			int i = 0;

			for (Node rac = refAST.getFirstChild();
				rac != null;
				rac = rac.getNextSibling())
			{

				if ("node".equals(rac.getNodeName()))
				{
					ExprImpl child = (ExprImpl) expr.jjtGetChild(i);

					// sanity check
					if (child.getParentExpr() != expr)
					{
						System.err.println(
							"Wrong parent <-> child link. parent:"
								+ child.getParentExpr().hashCode()
								+ " expected "
								+ expr.hashCode()
								+ " child:"
								+ child);
						System.err.flush();
					}

					if (!checkAST(child, (Element) rac))
					{
						return false;
					}

					i++;
				}
			}

			if (i < expr.jjtGetNumChildren())
			{
				return false;
			}

			return true;
		}

		return false;
	}

	public static void main(String[] args)
	{
		m_dumpTree =
			((args.length == 1) && args[0].equals("-dump")) ? true : false;
		TestSamples test = new TestSamples();
		test.testXPath();
	}

}