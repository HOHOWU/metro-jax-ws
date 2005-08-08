/*
 * $Id: XMLLogicalMessageContextImpl.java,v 1.3 2005-08-08 19:32:31 bbissett Exp $
 *
 * Copyright (c) 2005 Sun Microsystems, Inc.
 * All rights reserved.
 */
package com.sun.xml.ws.handler;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.xml.ws.LogicalMessage;
import javax.xml.ws.handler.LogicalMessageContext;
import javax.xml.ws.handler.MessageContext.Scope;
import com.sun.xml.ws.spi.runtime.MessageContext;

/**
 * Implementation of LogicalMessageContext that is used in
 * the XML/HTTP binding. It is similar to LogicalMessageContextImpl
 * except that the message impl class it uses is a
 * {@link XMLLogicalMessageImpl} rather than a
 * {@link LogicalMessageImpl}.
 *
 * <p>Class has to defer information to HandlerContext so that properties
 * are shared between this and SOAPMessageContext.
 *
 * @see LogicalMessageImpl
 * @see XMLHandlerContext
 * @see XMLLogicalMessageImpl
 *
 * @author WS Development Team
 */
public class XMLLogicalMessageContextImpl implements LogicalMessageContext {
    
    private MessageContext ctxt;
    private XMLHandlerContext handlerCtxt;

    public XMLLogicalMessageContextImpl(XMLHandlerContext handlerCtxt) {
        this.handlerCtxt = handlerCtxt;
        ctxt = handlerCtxt.getMessageContext();
    }

    public XMLHandlerContext getHandlerContext() {
        return handlerCtxt;
    }

    public LogicalMessage getMessage() {
        return new XMLLogicalMessageImpl(handlerCtxt);
    }

    public void setScope(String name, Scope scope) {
        ctxt.setScope(name, scope);
    }

    public Scope getScope(String name) {
        return ctxt.getScope(name);
    }

    /* java.util.Map methods below here */
    
    public void clear() {
        ctxt.clear();
    }

    public boolean containsKey(Object obj) {
        return ctxt.containsKey(obj);
    }

    public boolean containsValue(Object obj) {
        return ctxt.containsValue(obj);
    }

    public Set<Entry<String, Object>> entrySet() {
        return ctxt.entrySet();
    }

    public Object get(Object obj) {
        return ctxt.get(obj);
    }

    public boolean isEmpty() {
        return ctxt.isEmpty();
    }

    public Set<String> keySet() {
        return ctxt.keySet();
    }

    public Object put(String str, Object obj) {
        return ctxt.put(str, obj);
    }

    public void putAll(Map<? extends String, ? extends Object> map) {
        ctxt.putAll(map);
    }

    public Object remove(Object obj) {
        return ctxt.remove(obj);
    }

    public int size() {
        return ctxt.size();
    }

    public Collection<Object> values() {
        return ctxt.values();
    }

}
