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

package org.objectfabric;

/**
 * Transactional object with fields generated by ObjectFabric generator.
 */
public abstract class TGenerated extends TIndexed {

    private final int _length;

    protected TGenerated(Resource resource, TObject.Version shared, int length) {
        super(resource, shared);

        _length = length;
    }

    public final int getFieldCount() {
        return _length;
    }

    @Override
    final int length() {
        return _length;
    }

    public final String getFieldName(int index) {
        return ((TIndexed.Read) shared_()).getFieldName(index);
    }

    public final TType getFieldType(int index) {
        return ((TIndexed.Read) shared_()).getFieldType(index);
    }

    /**
     * Generated fields are assigned an index, so their value can also be retrieved by
     * index.
     */
    public final Object getField(int index) {
        if (index < 0 || index >= getFieldCount())
            throw new IndexOutOfBoundsException();

        Transaction outer = current_();
        Transaction inner = startRead_(outer);
        Object value;

        if (shared_() instanceof Version32) {
            Version32 version = getVersion32_(inner, index);
            value = version != null ? version.getAsObject(index) : null;
        } else {
            VersionN version = getVersionN_(inner, index);
            value = version != null ? version.getAsObject(index) : null;
        }

        endRead_(outer, inner);
        return value;
    }

    /**
     * Return the field as it was when the current transaction started.
     */
    final Object getOldField(int index, Transaction transaction) {
        if (index < 0 || index >= getFieldCount())
            throw new IndexOutOfBoundsException();

        if (transaction == null)
            throw new RuntimeException(Strings.CURRENT_NULL);

        Object value;

        if (shared_() instanceof Version32) {
            Version32 version = findPublicVersion32(transaction, index);
            value = version != null ? version.getAsObject(index) : null;
        } else {
            VersionN version = findPublicVersionN(transaction, index);
            value = version != null ? version.getAsObject(index) : null;
        }

        return value;
    }

    public final void setField(int index, Object value) {
        if (index < 0 || index >= getFieldCount())
            throw new IndexOutOfBoundsException();

        if (Debug.ENABLED)
            Debug.assertion(!(value instanceof TObject.Version));

        Transaction outer = current_();
        Transaction inner = startWrite_(outer);
        TObject.Version version = getOrCreateVersion_(inner);

        if (shared_() instanceof Version32) {
            Version32 version32 = (Version32) version;
            version32.setAsObject(index, value);
            version32.setBit(index);
        } else {
            VersionN versionN = (VersionN) version;
            versionN.setAsObject(index, value);
            versionN.setBit(index);
        }

        endWrite_(outer, inner);
    }

    //

    @Override
    final TObject.Version createRead() {
        TObject.Version version;

        if (_length > 32)
            version = new TIndexedNRead();
        else
            version = new TIndexed32Read();

        version.setObject(this);
        return version;
    }
}