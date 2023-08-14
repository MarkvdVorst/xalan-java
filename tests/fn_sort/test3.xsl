<?xml version="1.0"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                version="3.0">
                
   <!-- Author: mukulg@apache.org -->
   
   <!-- use with test1_b.xml -->
   
   <!-- An XSLT stylesheet test case, to test XPath 3.1 fn:sort function,
        by reading input data from an XML external source document. 
        
        This stylesheet, sorts a sequence of XML person elements by last name 
        as the major sort key and first name as the minor sort key, using the 
        default collation.
   -->                

   <xsl:output method="xml" indent="yes"/>
   
   <xsl:template match="/document">
      <document>       
         <xsl:copy-of select="sort(person, (), function($person) { $person/lName || ':' || $person/fName })"/>
      </document>
   </xsl:template>
   
   <!--
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
   -->

</xsl:stylesheet>