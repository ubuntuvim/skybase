package com.skybase.domain.repository;

import io.katharsis.queryParams.QueryParams;
import io.katharsis.repository.RelationshipRepository;

import java.lang.reflect.InvocationTargetException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;
import org.bson.types.ObjectId;

import com.skybase.domain.model.Project;
import com.skybase.domain.model.Task;

public class TaskToProjectRepository implements RelationshipRepository<Task, ObjectId, Project, ObjectId> {

	private TaskRepository taskRepository;
	private ProjectRepository projectRepository;
	
	/**
	 * A simple implementation of the addRelations method which presents the general concept of the method.
	 * It SHOULD NOT be used in production because of possible race condition - production ready code should perform an atomci operation
	 * 
	 * 设置task关联到project下。
	 * 首先根据关联的id查询出project对象，然后把
	 * //TODO 有待理解
	 * @param task 关联的类
	 * @param iterator 关联的project对象
	 * @param fieldName 关联的属性
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void addRelations(Task task, Iterable<ObjectId> iterator, String fieldName) {
		List<Project> newProjList = new LinkedList<>();
		Iterable<Project> proj2Add = this.projectRepository.findAll(iterator, null);
		proj2Add.forEach(newProjList::add);  //直接遍历数据病执行add方法设置对象到newProjList中
		
		try {
			// 获取对象的值
			if (PropertyUtils.getProperty(task, fieldName) != null) {
				Iterable<Project> projs = (Iterable<Project>) PropertyUtils.getProperty(task, fieldName);
				projs.forEach(newProjList::add);
			}
			// 设置关联的project主键到关联的属性上
			PropertyUtils.setProperty(task, fieldName, newProjList);
			
			this.taskRepository.save(task);
		} catch (IllegalAccessException | InvocationTargetException
				| NoSuchMethodException e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public Iterable<Project> findManyTargets(ObjectId id, String fieldName,
			QueryParams requestParams) {
		// 根据id查询task
		Task task = this.taskRepository.findOne(id, requestParams);
		try {
			//获取task关联的project属性值
			return (Iterable<Project>) PropertyUtils.getProperty(task, fieldName);
		} catch (IllegalAccessException | InvocationTargetException
				| NoSuchMethodException e) {
			e.printStackTrace();
		}
		return null;
	}
	

	@Override
	public Project findOneTarget(ObjectId id, String fieldName, QueryParams requestParams) {
		Task task = this.taskRepository.findOne(id, requestParams);
		try {
			return (Project) PropertyUtils.getProperty(task, fieldName);
		} catch (IllegalAccessException | InvocationTargetException
				| NoSuchMethodException e) {
			e.printStackTrace();
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void removeRelations(Task task, Iterable<ObjectId> projectIds, String fieldName) {
		try {
			if (PropertyUtils.getProperty(task, fieldName) != null) {
				Iterable<Project> projs = (Iterable<Project>) PropertyUtils.getProperty(task, fieldName);
				Iterator<Project> iterator = projs.iterator();
				while (iterator.hasNext()) {
					for (ObjectId projIdToRemove : projectIds) {
						if (iterator.next().getId().equals(projIdToRemove)) {
							iterator.remove();
							break;
						}
					}
				}
				List<Project> newProjList = new LinkedList<Project>();
				projs.forEach(newProjList::add);
				PropertyUtils.setProperty(task, fieldName, newProjList);
				this.taskRepository.save(task);
			}
		} catch (IllegalAccessException | InvocationTargetException
				| NoSuchMethodException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 单个关联关系设置
	 * 
	 * 一个task对一个project
	 * @param task
	 * @param projectId
	 * @param fieldName
	 */
	@Override
	public void setRelation(Task task, ObjectId projectId, String fieldName) {
		Project proj = this.projectRepository.findOne(projectId, null);
		try {
			PropertyUtils.setProperty(task, fieldName, proj);
			this.taskRepository.save(task);
		} catch (IllegalAccessException | InvocationTargetException
				| NoSuchMethodException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 设置一对多关联关系
	 * 
	 * 一个task对多个project
	 * @param task
	 * @param iterator
	 * @param fieldName
	 */
	@Override
	public void setRelations(Task task, Iterable<ObjectId> projectIds, String fieldName) {
		Iterable<Project> projs = this.projectRepository.findAll(projectIds, null);
		try {
			PropertyUtils.setProperty(task, fieldName, projs);
			this.taskRepository.save(task);
		} catch (IllegalAccessException | InvocationTargetException
				| NoSuchMethodException e) {
			e.printStackTrace();
		}
	}
	
}
