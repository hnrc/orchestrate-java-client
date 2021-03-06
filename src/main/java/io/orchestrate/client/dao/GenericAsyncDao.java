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
package io.orchestrate.client.dao;

import io.orchestrate.client.*;

import java.util.concurrent.Future;

import static io.orchestrate.client.Preconditions.*;

/**
 * A generic object for CRUD data access operations.
 *
 * @param <T> The type this object will control CRUD operations for.
 *
 * @see <a href="http://java.orchestrate.io/examples/#data-access-object">http://java.orchestrate.io/examples/#data-access-object</a>
 */
public abstract class GenericAsyncDao<T> implements AsyncDao<T> {

    /** The client to query the Orchestrate.io service. */
    private final Client client;
    /** The name of the collection to store objects to. */
    private final String collection;
    /** Type information for marshalling objects at runtime. */
    private final Class<T> clazz;

    /**
     * Create a data access object to store objects of type {@code T} to the
     * specified {@code collection} using the {@code client}.
     *
     * @param client The client used to query the Orchestrate.io service.
     * @param collection The name of the collection to store objects to.
     * @param clazz Type information for deserializing to type {@code T} at
     *              runtime.
     */
    public GenericAsyncDao(final Client client, final String collection, final Class<T> clazz) {
        this.client = checkNotNull(client, "client");
        this.collection = checkNotNullOrEmpty(collection, "collection");
        this.clazz = checkNotNull(clazz, "clazz");
    }

    /** {@inheritDoc} */
    @Override
    public Future<KvObject<T>> findOne(final String key) {
        checkNotNullOrEmpty(key, "key");

        final KvFetchOperation<T> kvFetchOp = new KvFetchOperation<T>(collection, key, clazz);
        return client.execute(kvFetchOp);
    }

    /** {@inheritDoc} */
    @Override
    public void findOne(final String key, final Iterable<OrchestrateFutureListener<KvObject<T>>> listeners) {
        checkNotNullOrEmpty(key, "key");
        checkNotNull(listeners, "listeners");

        final KvFetchOperation<T> kvFetchOp = new KvFetchOperation<T>(collection, key, clazz);
        kvFetchOp.addListener(listeners);
        client.execute(kvFetchOp);
    }

    /** {@inheritDoc} */
    @Override
    public Future<KvObject<T>> findOne(final String key, final KvMetadata metadata) {
        checkNotNullOrEmpty(key, "key");
        checkNotNull(metadata, "metadata");

        final KvFetchOperation<T> kvFetchOp =
                new KvFetchOperation<T>(collection, key, metadata, clazz);
        return client.execute(kvFetchOp);
    }

    /** {@inheritDoc} */
    @Override
    public void findOne(
            final String key, final KvMetadata metadata, final Iterable<OrchestrateFutureListener<KvObject<T>>> listeners) {
        checkNotNullOrEmpty(key, "key");
        checkNotNull(metadata, "metadata");
        checkNotNull(listeners, "listeners");

        final KvFetchOperation<T> kvFetchOp = new KvFetchOperation<T>(collection, key, metadata, clazz);
        kvFetchOp.addListener(listeners);
        client.execute(kvFetchOp);
    }

    /** {@inheritDoc} */
    @Override
    public Future<KvObject<T>> findOne(final String key, final String ref) {
        checkNotNullOrEmpty(key, "key");
        checkNotNullOrEmpty(ref, "ref");

        final KvFetchOperation<T> kvFetchOp =
                new KvFetchOperation<T>(collection, key, ref, clazz);
        return client.execute(kvFetchOp);
    }

    /** {@inheritDoc} */
    @Override
    public void findOne(
            final String key, final String ref, final Iterable<OrchestrateFutureListener<KvObject<T>>> listeners) {
        checkNotNullOrEmpty(key, "key");
        checkNotNullOrEmpty(ref, "ref");
        checkNotNull(listeners, "listeners");

        final KvFetchOperation<T> kvFetchOp = new KvFetchOperation<T>(collection, key, ref, clazz);
        kvFetchOp.addListener(listeners);
        client.execute(kvFetchOp);
    }

    /** {@inheritDoc} */
    @Override
    public Future<KvMetadata> save(final String key, final T value) {
        checkNotNullOrEmpty(key, "key");
        checkNotNull(value, "value");

        final KvStoreOperation kvStoreOp = new KvStoreOperation(collection, key, value);
        return client.execute(kvStoreOp);
    }

    /** {@inheritDoc} */
    @Override
    public void save(
            final String key, final T value, final Iterable<OrchestrateFutureListener<KvMetadata>> listeners) {
        checkNotNullOrEmpty(key, "key");
        checkNotNull(value, "value");
        checkNotNull(listeners, "listeners");

        final KvStoreOperation kvStoreOp = new KvStoreOperation(collection, key, value);
        kvStoreOp.addListener(listeners);
        client.execute(kvStoreOp);
    }

    /** {@inheritDoc} */
    @Override
    public Future<KvMetadata> save(final String key, final T value, final boolean ifAbsent) {
        checkNotNullOrEmpty(key, "key");
        checkNotNull(value, "value");

        final KvStoreOperation kvStoreOp =
                new KvStoreOperation(collection, key, value, ifAbsent);
        return client.execute(kvStoreOp);
    }

    /** {@inheritDoc} */
    @Override
    public void save(
            final String key, final T value, final boolean ifAbsent, final Iterable<OrchestrateFutureListener<KvMetadata>> listeners) {
        checkNotNullOrEmpty(key, "key");
        checkNotNull(value, "value");
        checkNotNull(listeners, "listeners");

        final KvStoreOperation kvStoreOp =
                new KvStoreOperation(collection, key, value, ifAbsent);
        kvStoreOp.addListener(listeners);
        client.execute(kvStoreOp);
    }

    /** {@inheritDoc} */
    @Override
    public Future<KvMetadata> save(final String key, final T value, final KvMetadata metadata) {
        checkNotNullOrEmpty(key, "key");
        checkNotNull(value, "value");
        checkNotNull(metadata, "metadata");

        final KvStoreOperation kvStoreOp =
                new KvStoreOperation(collection, key, value, metadata);
        return client.execute(kvStoreOp);
    }

    /** {@inheritDoc} */
    @Override
    public void save(
            final String key, final T value, final KvMetadata metadata, final Iterable<OrchestrateFutureListener<KvMetadata>> listeners) {
        checkNotNullOrEmpty(key, "key");
        checkNotNull(metadata, "metadata");
        checkNotNull(listeners, "listeners");

        final KvStoreOperation kvStoreOp =
                new KvStoreOperation(collection, key, value, metadata);
        kvStoreOp.addListener(listeners);
        client.execute(kvStoreOp);
    }

    /** {@inheritDoc} */
    @Override
    public Future<KvMetadata> save(final String key, final T value, final String currentRef) {
        checkNotNullOrEmpty(key, "key");
        checkNotNull(value, "value");
        checkNotNullOrEmpty(currentRef, "currentRef");

        final KvStoreOperation kvStoreOp =
                new KvStoreOperation(collection, key, value, currentRef);
        return client.execute(kvStoreOp);
    }

    /** {@inheritDoc} */
    @Override
    public void save(
            final String key, final T value, final String currentRef, final Iterable<OrchestrateFutureListener<KvMetadata>> listeners) {
        checkNotNullOrEmpty(key, "key");
        checkNotNull(value, "value");
        checkNotNullOrEmpty(currentRef, "currentRef");
        checkNotNull(listeners, "listeners");

        final KvStoreOperation kvStoreOp =
                new KvStoreOperation(collection, key, value, currentRef);
        kvStoreOp.addListener(listeners);
        client.execute(kvStoreOp);
    }

    /** {@inheritDoc} */
    @Override
    public Future<Boolean> delete(final String key) {
        checkNotNullOrEmpty(key, "key");

        final KvDeleteOperation deleteOp = new KvDeleteOperation(collection, key);
        return client.execute(deleteOp);
    }

    /** {@inheritDoc} */
    @Override
    public void delete(final String key, final Iterable<OrchestrateFutureListener<Boolean>> listeners) {
        checkNotNullOrEmpty(key, "key");
        checkNotNull(listeners, "listeners");

        final KvDeleteOperation deleteOp = new KvDeleteOperation(collection, key);
        deleteOp.addListener(listeners);
        client.execute(deleteOp);
    }

    /** {@inheritDoc} */
    @Override
    public Future<Boolean> delete(final String key, final KvMetadata metadata) {
        checkNotNullOrEmpty(key, "key");
        checkNotNull(metadata, "metadata");

        final KvDeleteOperation deleteOp = new KvDeleteOperation(collection, key, metadata);
        return client.execute(deleteOp);
    }

    /** {@inheritDoc} */
    @Override
    public void delete(
            final String key, final KvMetadata metadata, final Iterable<OrchestrateFutureListener<Boolean>> listeners) {
        checkNotNullOrEmpty(key, "key");
        checkNotNull(metadata, "metadata");
        checkNotNull(listeners, "listeners");

        final KvDeleteOperation deleteOp = new KvDeleteOperation(collection, key, metadata);
        deleteOp.addListener(listeners);
        client.execute(deleteOp);
    }

    /** {@inheritDoc} */
    @Override
    public Future<Boolean> delete(final String key, final String currentRef) {
        checkNotNullOrEmpty(key, "key");
        checkNotNullOrEmpty(currentRef, "currentRef");

        final KvDeleteOperation deleteOp = new KvDeleteOperation(collection, key, currentRef);
        return client.execute(deleteOp);
    }

    /** {@inheritDoc} */
    @Override
    public void delete(
            final String key, final String currentRef, final Iterable<OrchestrateFutureListener<Boolean>> listeners) {
        checkNotNullOrEmpty(key, "key");
        checkNotNullOrEmpty(currentRef, "currentRef");
        checkNotNull(listeners, "listeners");

        final KvDeleteOperation deleteOp = new KvDeleteOperation(collection, key, currentRef);
        deleteOp.addListener(listeners);
        client.execute(deleteOp);
    }

    /** {@inheritDoc} */
    @Override
    public Future<Boolean> deleteAll() {
        final DeleteOperation deleteOp = new DeleteOperation(collection);
        return client.execute(deleteOp);
    }

    /** {@inheritDoc} */
    @Override
    public void deleteAll(final Iterable<OrchestrateFutureListener<Boolean>> listeners) {
        checkNotNull(listeners, "listeners");

        final DeleteOperation deleteOp = new DeleteOperation(collection);
        deleteOp.addListener(listeners);
        client.execute(deleteOp);
    }

}
