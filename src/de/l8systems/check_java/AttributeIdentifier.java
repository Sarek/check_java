/*
 * Copyright (c) 2015, Sebastian Schäfer <me@sebastianschaefer.net>
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *  * Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 *  * Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in the
 *    documentation and/or other materials provided with the distribution.
 *  * Neither the name of check_java nor the
 *    names of its contributors may be used to endorse or promote products
 *    derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL SEBASTIAN SCHÄFER BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package de.l8systems.check_java;

import javax.management.ObjectName;

/**
 * Class to save the identifying attributes of a JMX attribute
 */
public class AttributeIdentifier {
    /**
     * The name of the object this attribute resides in
     */
    private ObjectName objectName;

    /**
     * The name of the attribute
     */
    private String attribute;

    /**
     * The name of the sub-attribute, in case the attribute is of a complex type.
     */
    private String subAttribute;

    /**
     * Create a new identifier for a complex typed JMX attribute
     * @param obj the name of the object that contains this attribute
     * @param attribute the name of the attribute
     * @param subAttribute the name of the sub-attribute
     */
    public AttributeIdentifier(ObjectName obj, String attribute, String subAttribute) {
        this.objectName = obj;
        this.attribute = attribute;
        this.subAttribute = subAttribute;
    }

    /**
     * Create a new identifier for a simply typed JMX attribute
     * @param obj the name of the object that contains this attribute
     * @param attribute the name of the attribute
     */
    public AttributeIdentifier(ObjectName obj, String attribute) {
        this(obj, attribute, null);
    }

    /**
     * Return the name of the object that contains this attribute
     *
     * @return the object name
     */
    public ObjectName getObjectName() {
        return objectName;
    }

    /**
     * Set the name of the object that contains this attribute
     * @param objectName the object name
     */
    public void setObjectName(ObjectName objectName) {
        this.objectName = objectName;
    }

    /**
     * Return the name of the attribute
     * @return the name of the attribtue
     */
    public String getAttribute() {
        return attribute;
    }

    /**
     * Set the name of the attribute
     * @param attribute the name of the attribute
     */
    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }

    /**
     * Return the name of the sub-attribute in case the attribute is of a complex type
     * @return the name of the sub-attribute
     */
    public String getSubAttribute() {
        return subAttribute;
    }

    /**
     * Set the name of the sub-attribute in case the attribute is of a complex type
     * @param subAttribute the name of the sub-attribute
     */
    public void setSubAttribute(String subAttribute) {
        this.subAttribute = subAttribute;
    }
}
