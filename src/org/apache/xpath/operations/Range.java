/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the  "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
/*
 * $Id$
 */
package org.apache.xpath.operations;

import java.math.BigInteger;

import org.apache.xpath.XPathContext;
import org.apache.xpath.objects.ResultSequence;
import org.apache.xpath.objects.XObject;
import org.apache.xpath.xs.types.XSInteger;

/**
 * The XPath 3.1 "to" range operation.
 * 
 * An XPath range expression can be used to construct a sequence of 
 * consecutive integers. Each of the operands of the to operator 
 * is converted as though it was an argument of a function with 
 * the expected parameter type xs:integer.
 */
public class Range extends Operation
{
    
   private static final long serialVersionUID = 7722428363208837859L;

   /**
   * Apply the operation to two operands, and return the result.
   *
   * @param left non-null reference to the evaluated left operand.
   * @param right non-null reference to the evaluated right operand.
   *
   * @return non-null reference to the XObject that represents the result of the operation.
   *
   * @throws javax.xml.transform.TransformerException
   */
    public XObject execute(XPathContext xctxt) throws javax.xml.transform.TransformerException {
        
      ResultSequence result = new ResultSequence();
  
      XObject expr1 = m_left.execute(xctxt);
      
      XObject expr2 = m_right.execute(xctxt);
      
      int fromIdx = (int)expr1.num();
      int toIdx = (int)expr2.num();
      
      for (int idx = fromIdx; idx <= toIdx; idx++) {
         result.add(new XSInteger(BigInteger.valueOf((long)idx)));    
      }
      
      return result;
    }

}
