/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.xpath.xs.types;

import org.apache.xpath.objects.ResultSequence;

/**
 * This class serves as base type, of all the XML Schema built-in 
 * numeric types.
 * 
 * @author Mukul Gandhi <mukulg@apache.org>
 * 
 * @xsl.usage advanced
 */
public class XSNumericType extends XSCtrType {

    private static final long serialVersionUID = 6842313858622701811L;

    @Override
    public ResultSequence constructor(ResultSequence arg) {
        return null;
    }

    @Override
    public String typeName() {
        return null;
    }

    @Override
    public String stringType() {
        return null;
    }

    @Override
    public String stringValue() {
        return null;
    }

}
