/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 1997-2012 Oracle and/or its affiliates. All rights reserved.
 *
 * The contents of this file are subject to the terms of either the GNU
 * General Public License Version 2 only ("GPL") or the Common Development
 * and Distribution License("CDDL") (collectively, the "License").  You
 * may not use this file except in compliance with the License.  You can
 * obtain a copy of the License at
 * http://glassfish.java.net/public/CDDL+GPL_1_1.html
 * or packager/legal/LICENSE.txt.  See the License for the specific
 * language governing permissions and limitations under the License.
 *
 * When distributing the software, include this License Header Notice in each
 * file and include the License file at packager/legal/LICENSE.txt.
 *
 * GPL Classpath Exception:
 * Oracle designates this particular file as subject to the "Classpath"
 * exception as provided by Oracle in the GPL Version 2 section of the License
 * file that accompanied this code.
 *
 * Modifications:
 * If applicable, add the following below the License Header, with the fields
 * enclosed by brackets [] replaced by your own identifying information:
 * "Portions Copyright [year] [name of copyright owner]"
 *
 * Contributor(s):
 * If you wish your version of this file to be governed by only the CDDL or
 * only the GPL Version 2, indicate your decision by adding "[Contributor]
 * elects to include this software in this distribution under the [CDDL or GPL
 * Version 2] license."  If you don't indicate a single choice of license, a
 * recipient has the option to distribute your version of this file under
 * either the CDDL, the GPL Version 2 or to extend the choice of license to
 * its licensees as provided above.  However, if you add GPL Version 2 code
 * and therefore, elected the GPL Version 2 license, then the option applies
 * only if the new code is made subject to such option by the copyright
 * holder.
 */

package server.endpoint_6790700.client;

import javax.xml.ws.Provider;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.ws.WebServiceProvider;
import javax.xml.ws.ServiceMode;
import javax.xml.ws.Service;
import javax.xml.ws.Service.Mode;
import javax.xml.ws.*;
import java.io.StringReader;
import java.io.*;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.*;
import org.w3c.dom.*;
import javax.xml.transform.stream.StreamResult;


/**
 * @author Jitendra Kotamraju
 */

@WebServiceProvider
@ServiceMode(value=Service.Mode.PAYLOAD)
public class MyProvider implements Provider<Source> {
    public Source invoke(Source source) {
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            StreamResult sr = new StreamResult(bos );
            Transformer trans = TransformerFactory.newInstance().newTransformer();
            trans.transform(source, sr);

            source = new StreamSource(new ByteArrayInputStream(bos.toByteArray()));
            // Typically received source is StAXSource. When it is converted
            // to DOMSource, it seems to pullin inscope namespaces. Hence
            // it is converted to StreamSource above
            DOMResult dr = new DOMResult();
            trans = TransformerFactory.newInstance().newTransformer();
            trans.transform(source, dr);
            Node node = dr.getNode();
            node = node.getFirstChild().getFirstChild();
            node = node.getAttributes().getNamedItemNS("http://www.w3.org/2001/XMLSchema-instance" , "nil");
            if (node == null) {
                throw new WebServiceException("xsi:nil attribute is not found");
            }
        } catch(Exception e) {
            throw new WebServiceException(e);
        }

        String replyElement = new String("<p>hello world</p>");
        StreamSource reply = new StreamSource(new StringReader (replyElement));
        return reply;
    }
}