package com.skybase.domain.repository;


import io.katharsis.queryParams.QueryParams;
import io.katharsis.repository.ResourceRepository;

import javax.inject.Inject;

import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Key;

import com.skybase.domain.model.Project;
import com.skybase.mongo.managed.MongoManaged;

public class ProjectRepository implements ResourceRepository<Project, ObjectId> {
    private Datastore datastore;

    @Inject
    public ProjectRepository(MongoManaged mongoManaged) {
        datastore = mongoManaged.getDatastore();
    }

    @SuppressWarnings("unchecked")
	public <S extends Project> S save(S entity) {
        Key<Project> saveKey = datastore.save(entity);
        return (S) datastore.getByKey(Project.class, saveKey);
    }

    public Project findOne(ObjectId id, QueryParams requestParams) {
        return datastore.getByKey(Project.class, new Key<>(Project.class, id));
    }

    @Override
    public Iterable<Project> findAll(QueryParams requestParams) {
        return datastore.find(Project.class);
    }

    @Override
    public Iterable<Project> findAll(Iterable<ObjectId> iterable, QueryParams requestParams) {
        return datastore.get(Project.class, iterable);
    }

    public void delete(ObjectId id) {
        datastore.delete(datastore.createQuery(Project.class).filter("_id", id));
    }
}
