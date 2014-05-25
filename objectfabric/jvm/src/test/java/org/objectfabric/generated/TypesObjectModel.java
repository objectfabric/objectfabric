/**
 * This file is part of ObjectFabric (http://objectfabric.org).
 *
 * ObjectFabric is licensed under the Apache License, Version 2.0, the terms
 * of which may be found at http://www.apache.org/licenses/LICENSE-2.0.html.
 * 
 * Copyright ObjectFabric Inc.
 * 
 * This file is provided AS IS with NO WARRANTY OF ANY KIND, INCLUDING THE
 * WARRANTY OF DESIGN, MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE.
 */

package org.objectfabric.generated;

//==============================================================================
//                                                                              
//  THIS FILE HAS BEEN GENERATED BY OBJECTFABRIC                                
//                                                                              
//==============================================================================

@SuppressWarnings({ "hiding", "unchecked", "static-access", "rawtypes" })
public final class TypesObjectModel extends org.objectfabric.ObjectModel {

    private static final byte[] UID = { -84, -24, 34, 82, -19, -108, -10, 101, 12, -114, 108, 53, 27, 44, -119, 97 };

    // volatile not needed, models have no state
    private static TypesObjectModel _instance;

    private static final java.lang.Object _lock = new java.lang.Object();

    protected TypesObjectModel() {
    }

    public static TypesObjectModel instance() {
        if (_instance == null) {
            synchronized (_lock) {
                if (_instance == null)
                    _instance = new TypesObjectModel();
            }
        }

        return _instance;
    }

    public static byte[] uid() {
        byte[] copy = new byte[UID.length];
        arraycopy(UID, copy);
        return copy;
    }

    @Override
    protected byte[] uid_() {
        return UID;
    }

    /**
     * Registers this object model so that its classes can be serialized by the
     * system.
     */
    public static void register() {
        register(instance());
    }

    @Override
    protected java.lang.String objectFabricVersion() {
        return "0.9";
    }

    public static final int CLASS_COUNT = 1;

    public static final int ORG_OBJECTFABRIC_GENERATED_TYPESCLASS_CLASS_ID = 0;

    public static final int METHOD_COUNT = 0;

    @Override
    protected java.lang.Class getClass(int classId, org.objectfabric.TType[] genericParameters) {
        switch (classId) {
            case ORG_OBJECTFABRIC_GENERATED_TYPESCLASS_CLASS_ID:
                return org.objectfabric.generated.TypesClass.class;
        }

        return super.getClass(classId, genericParameters);
    }

    @Override
    protected org.objectfabric.TObject createInstance(org.objectfabric.Resource resource, int classId, org.objectfabric.TType[] genericParameters) {
        switch (classId) {
            case ORG_OBJECTFABRIC_GENERATED_TYPESCLASS_CLASS_ID:
                return new org.objectfabric.generated.TypesClass(resource);
        }

        return super.createInstance(resource, classId, genericParameters);
    }

}
