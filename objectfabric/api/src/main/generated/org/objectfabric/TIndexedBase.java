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

//==============================================================================
//                                                                              
//  THIS FILE HAS BEEN GENERATED BY OBJECTFABRIC                                
//                                                                              
//==============================================================================

abstract class TIndexedBase extends TObject {

    protected TIndexedBase(Resource resource, TObject.Version shared) {
        super(resource, shared);
    }

    /*
     * 32.
     */

    protected final TIndexed.Version32 getVersion32_(Transaction current, int index) {
        TIndexed.Version32 version = (TIndexed.Version32) current.getVersion(this);

        /*
         * If we have already written to this field, use this.
         */
        if (version != null && version.getBit(index))
            return version;

        version = findPrivateVersion32(current.getPrivateSnapshotVersions(), index);

        /*
         * Same if it was in a private snapshot.
         */
        if (version != null)
            return version;

        /*
         * Otherwise keep track of read and find previous value.
         */
        if (!current.ignoreReads()) {
            TIndexed32Read read = (TIndexed32Read) current.getRead(this);

            if (read == null) {
                read = (TIndexed32Read) createRead();
                current.putRead(read);
            }

            read.setBit(index);
        }

        return findPublicVersion32(current, index);
    }

    private final TIndexed.Version32 findPrivateVersion32(Version[][] versions, int index) {
        if (versions != null) {
            for (int i = versions.length - 1; i >= 0; i--) {
                TIndexed.Version32 version = (TIndexed.Version32) TransactionBase.getVersion(versions[i], this);

                if (version != null && version.getBit(index))
                    return version;
            }
        }

        return null;
    }

    final TIndexed.Version32 findPublicVersion32(Transaction transaction, int index) {
        for (int i = transaction.getPublicSnapshotVersions().length - 1; i > TransactionManager.OBJECTS_VERSIONS_INDEX; i--) {
            TObject.Version[] delta = transaction.getPublicSnapshotVersions()[i];
            TIndexed.Version32 version = (TIndexed.Version32) TransactionBase.getVersion(delta, this);

            if (version != null && version.getBit(index))
                return version;
        }

        return (TIndexed.Version32) shared_();
    }

    /*
     * N.
     */

    protected final TIndexed.VersionN getVersionN_(Transaction current, int index) {
        TIndexed.VersionN version = (TIndexed.VersionN) current.getVersion(this);

        /*
         * If we have already written to this field, use this.
         */
        if (version != null && version.getBit(index))
            return version;

        version = findPrivateVersionN(current.getPrivateSnapshotVersions(), index);

        /*
         * Same if it was in a private snapshot.
         */
        if (version != null)
            return version;

        /*
         * Otherwise keep track of read and find previous value.
         */
        if (!current.ignoreReads()) {
            TIndexedNRead read = (TIndexedNRead) current.getRead(this);

            if (read == null) {
                read = (TIndexedNRead) createRead();
                current.putRead(read);
            }

            read.setBit(index);
        }

        return findPublicVersionN(current, index);
    }

    private final TIndexed.VersionN findPrivateVersionN(Version[][] versions, int index) {
        if (versions != null) {
            for (int i = versions.length - 1; i >= 0; i--) {
                TIndexed.VersionN version = (TIndexed.VersionN) TransactionBase.getVersion(versions[i], this);

                if (version != null && version.getBit(index))
                    return version;
            }
        }

        return null;
    }

    final TIndexed.VersionN findPublicVersionN(Transaction transaction, int index) {
        for (int i = transaction.getPublicSnapshotVersions().length - 1; i > TransactionManager.OBJECTS_VERSIONS_INDEX; i--) {
            TObject.Version[] delta = transaction.getPublicSnapshotVersions()[i];
            TIndexed.VersionN version = (TIndexed.VersionN) TransactionBase.getVersion(delta, this);

            if (version != null && version.getBit(index))
                return version;
        }

        return (TIndexed.VersionN) shared_();
    }
}
