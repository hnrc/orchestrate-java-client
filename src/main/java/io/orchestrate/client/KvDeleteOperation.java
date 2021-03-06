/*
 * Copyright 2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.orchestrate.client;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.glassfish.grizzly.http.HttpHeader;

import javax.annotation.Nullable;
import java.io.IOException;

import static io.orchestrate.client.Preconditions.*;

/**
 * Delete a KV object from a collection in the Orchestrate.io service.
 *
 * <p>Usage:
 * <pre>
 * {@code
 * KvDeleteOperation kvDeleteOp = new KvDeleteOperation("myCollection", "someKey");
 * Future<Boolean> futureResult = client.execute(deleteOp);
 * Boolean result = futureResult.get();
 * if (result)
 *     System.out.println("Successfully deleted 'someKey'.");
 * }
 * </pre>
 *
 * @see <a href="http://java.orchestrate.io/querying/#delete-data">http://java.orchestrate.io/querying/#delete-data</a>
 */
@ToString(callSuper=false)
@EqualsAndHashCode(callSuper=false)
public final class KvDeleteOperation extends AbstractOperation<Boolean> {

    /** The collection to delete. */
    private final String collection;
    /** The key in the collection to delete. */
    private final String key;
    /** The current version of the object stored with the key. */
    private final String currentRef;

    /**
     * Create a new {@code KvDeleteOperation} to remove the object with the
     * specified {@code key} from the {@code collection}.
     *
     * @param collection The name of the collection containing the key to delete.
     * @param key The name of the key to delete.
     */
    public KvDeleteOperation(final String collection, final String key) {
        this.collection = checkNotNullOrEmpty(collection, "collection");
        this.key = checkNotNullOrEmpty(key, "key");
        this.currentRef = null;
    }

    /**
     * Create a new {@code KvDeleteOperation} to remove the object with the
     * information specified in the {@code metadata}.
     *
     * @param metadata The metadata containing the collection, key and ref to
     *                 build this operation from.
     */
    public KvDeleteOperation(final KvMetadata metadata) {
        checkNotNull(metadata, "metadata");

        this.collection = metadata.getCollection();
        this.key = metadata.getKey();
        this.currentRef = metadata.getRef();
    }

    /**
     * Create a new {@code KvDeleteOperation} to remove the object with the
     * specified {@code key} from the {@code collection} if and only if the
     * ref from the {@code metadata} matches the current stored ref.
     *
     * @param collection The name of the collection containing the key to delete.
     * @param key The name of the key to delete.
     * @param metadata The metadata about the current object stored to this key.
     */
    public KvDeleteOperation(
            final String collection, final String key, final KvMetadata metadata) {
        this(collection, key, metadata.getRef());
    }

    /**
     * Create a new {@code KvDeleteOperation} to remove the object with the
     * specified {@code key} from the {@code collection} if and only if the
     * {@code currentRef} matches the current stored ref.
     *
     * @param collection The name of the collection containing the key to delete.
     * @param key The name of the key to delete.
     * @param currentRef The ref of the last known object stored to this key.
     */
    public KvDeleteOperation(
            final String collection, final String key, final String currentRef) {
        this.collection = checkNotNullOrEmpty(collection, "collection");
        this.key = checkNotNullOrEmpty(key, "key");
        this.currentRef = checkNotNullOrEmpty(currentRef, "currentRef");
    }

    /** {@inheritDoc} */
    @Override
    Boolean fromResponse(final int status, final HttpHeader httpHeader, final String json, final JacksonMapper mapper)
            throws IOException {
        return (status == 204);
    }

    /**
     * Returns the collection from this operation.
     *
     * @return The collection from this operation.
     */
    public String getCollection() {
        return collection;
    }

    /**
     * Returns the key from this operation.
     *
     * @return The key from this operation.
     */
    public String getKey() {
        return key;
    }

    /**
     * Returns the current "ref" from this operation.
     *
     * @return The current "ref" from this operation, may be {@code null}.
     * @see #hasCurrentRef()
     */
    @Nullable
    public String getCurrentRef() {
        return currentRef;
    }

    /**
     * Returns whether a "ref" was supplied to this operation.
     *
     * @return {@code true} if a "ref" was supplied to the operation.
     */
    public boolean hasCurrentRef() {
        return (currentRef != null);
    }

}
