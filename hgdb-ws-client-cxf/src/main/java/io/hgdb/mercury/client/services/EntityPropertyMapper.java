package io.hgdb.mercury.client.services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import pl.slawas.twl4j.Logger;
import pl.slawas.twl4j.LoggerFactory;
import pro.ibpm.mercury.dao.services.IEntityPropertyMapper;
import pro.ibpm.mercury.entities.helpers.SimpleJavaType;
import pro.ibpm.mercury.exceptions.InternalErrorException;
import pro.ibpm.mercury.exceptions.MercuryException;
import pro.ibpm.mercury.utils.property.config.ObjectMetaData;

/**
 * 
 * EntityPropertyMapper
 * 
 * <pre>
 * 
 * ObjectMetaData entityMetaDataForGetter = (new ObjectMetaDataBuilderImpl(getEntityClass(),
 * 		ObjectMetaData.GETTER_PREFIX, Boolean.TRUE)).loadMetaData(getEntityClass(), EntityHelper.ROOT_FIELD_NAME, 0,
 * 				false, EntityHelper.FOR_SINGLETON);
 * ObjectMetaData entityMetaDataForSetter = (new ObjectMetaDataBuilderImpl(getEntityClass(),
 * 		ObjectMetaData.SETTER_PREFIX, Boolean.TRUE)).loadMetaData(getEntityClass(), EntityHelper.ROOT_FIELD_NAME, 0,
 * 				false, EntityHelper.FOR_SINGLETON);
 * PropertyMapperEntity2Dto maper = new PropertyMapperEntity2Dto(getEntityClass(), entityMetaDataForGetter,
 * 		entityMetaDataForSetter);
 * 
 * </pre>
 *
 * @author Sławomir Cichy &lt;slawomir.cichy@ibpm.pro&gt;
 * @version $Revision: 1.1 $
 *
 */
public class EntityPropertyMapper implements IEntityPropertyMapper {

	protected final Logger logger = LoggerFactory.getLogger(getClass());

	private ObjectMetaData entityMetaDataForGetter;
	private ObjectMetaData entityMetaDataForSetter;
	/** podstawowy typ obiektu, do którego mapujemy */
	private Class<?> targetClazz;
	/** podstawowy typ obiektu, z którego mapujemy */
	private Class<?> sourceClazz;

	public EntityPropertyMapper() {
	}

	/**
	 * 
	 * @param source
	 *            podstawowy typ obiektu, z którego mapujemy
	 * @param target
	 *            podstawowy typ obiektu, do którego mapujemy
	 * @param entityMetaDataForGetter
	 * @param entityMetaDataForSetter
	 */
	public EntityPropertyMapper(Class<?> source, Class<?> target, ObjectMetaData entityMetaDataForGetter,
			ObjectMetaData entityMetaDataForSetter) {
		super();
		this.entityMetaDataForGetter = entityMetaDataForGetter;
		this.entityMetaDataForSetter = entityMetaDataForSetter;
		this.sourceClazz = source;
		this.targetClazz = target;
	}

	/* Overridden (non-Javadoc) */
	@Override
	public void init(Class<?> source, Class<?> target, ObjectMetaData entityMetaDataForGetter,
			ObjectMetaData entityMetaDataForSetter) throws MercuryException {
		this.entityMetaDataForGetter = entityMetaDataForGetter;
		this.entityMetaDataForSetter = entityMetaDataForSetter;
		this.sourceClazz = source;
		this.targetClazz = target;
	}

	/* Overridden (non-Javadoc) */
	@Override
	public Object mapObj(Object fromObject) throws MercuryException {
		if (fromObject == null) {
			return null;
		}
		return mapObj(this.sourceClazz, fromObject, 0);
	}

	/* Overridden (non-Javadoc) */
	@Override
	@SuppressWarnings("unchecked")
	public <S, T> Collection<T> mapObjs(Class<S> elementType, Collection<S> source, Collection<T> target)
			throws MercuryException {
		if (source == null) {
			return null;
		}
		if (source.isEmpty()) {
			target.clear();
			return target;
		}
		for (S entity : source) {
			target.add((T) mapObj(elementType, entity, 0));
		}
		return target;
	}

	public Object mapObj(Class<?> type, Object fromObject, final int iteration) throws MercuryException {

		if (fromObject == null) {
			return null;
		}

		Object toObject = null;
		String propertyName = null;
		final int nextiteration = iteration + 1;
		Class<?> setterArg = null;
		try {

			if (entityMetaDataForGetter.isSimple()) {
				throw new InternalErrorException(
						"PropertyMapper : entityMetaData.isSimple() = true. PropertyMapper mapuje tylko obiekty złożone a mam "
								+ entityMetaDataForGetter.getTypeName());

			}

			logger.debug("{} --> mapObj : from {} to {}",
					new Object[] { iteration, fromObject.getClass().getSimpleName(), targetClazz.getSimpleName() });

			toObject = targetClazz.newInstance();

			for (Map.Entry<String, ObjectMetaData> entry : entityMetaDataForGetter.getPropertiesMap().entrySet()) {

				propertyName = entry.getKey();
				ObjectMetaData getterObjectMetaData = entry.getValue();
				ObjectMetaData setterObjectMetaData = entityMetaDataForSetter.getPropertiesMap().get(propertyName);
				if (setterObjectMetaData == null) {
					if (logger.isTraceEnabled()) {
						logger.warn("Iteracja: {}. Obiekt docelowy {} nie posiada setter'a dla parametru {}",
								new Object[] { iteration, toObject.getClass().getSimpleName(), propertyName });
					}
					continue;
				}
				/* potrzebujemy informację do ewentualnego błędu */
				setterArg = setterObjectMetaData.getField().getType();

				Object propertyValue = null;
				SimpleJavaType entityPropJavaType = SimpleJavaType.get(getterObjectMetaData.getField().getType());
				if (entry.getValue().getMethod() != null) {
					if (logger.isTraceEnabled()) {
						logger.trace(
								"{} --> Pobieram właściwość dla {}.{} \n -> metoda: {}\n -> metoda.declaringClass: {}",
								new Object[] { iteration, propertyName, fromObject.getClass().getSimpleName(),
										(getterObjectMetaData.getMethod() != null
												? getterObjectMetaData.getMethod().getName()
												: "n/a"),
										(getterObjectMetaData.getMethod() != null
												? getterObjectMetaData.getMethod().getDeclaringClass()
												: "n/a") });
					}
					propertyValue = getterObjectMetaData.getMethod().invoke(fromObject);
				}

				if (logger.isTraceEnabled()) {
					logger.trace(
							"{} --> Ustawiam właściwość dla {} \n -> metoda: {}\n -> metoda.declaringClass: {}\n  -> propertyValue: {} \n  -> typ: {}",
							new Object[] { iteration, propertyName,
									(setterObjectMetaData.getMethod() != null
											? setterObjectMetaData.getMethod().getName()
											: "n/a"),
									(setterObjectMetaData.getMethod() != null
											? setterObjectMetaData.getMethod().getDeclaringClass()
											: "n/a"),
									(propertyValue != null ? propertyValue : "null"),
									(entityPropJavaType != null ? "typ prosty" : getterObjectMetaData.getTypeName()) });
				}

				if (propertyValue != null && entityPropJavaType == null) {
					/* Typ złożony */
					if (getterObjectMetaData.isEntityType()) {
						propertyValue = convert(getterObjectMetaData.getEntityType().getEntityClass(), propertyValue,
								getterObjectMetaData, setterObjectMetaData, nextiteration);
					} else if (getterObjectMetaData.isCollection()) {
						/* Kolekcja */
						propertyValue = convertList(getterObjectMetaData.getCollectionElementType(), propertyValue,
								getterObjectMetaData.getCollectionGenericConfig(),
								setterObjectMetaData.getCollectionGenericConfig(), nextiteration);

					} else {
						/* Obiekt złożony/ nie encja np. klucz główny */
						propertyValue = convert(getterObjectMetaData.getParamType(), propertyValue,
								getterObjectMetaData, setterObjectMetaData, nextiteration);

					}
				}
				if (setterObjectMetaData.getMethod() != null) {
					/* aktualizujemy informację do ewentualnego błędu */
					setterArg = (propertyValue != null ? propertyValue.getClass()
							: setterObjectMetaData.getField().getType());
					setterObjectMetaData.getMethod().invoke(toObject, propertyValue);
					logger.trace("{} --> mam object dla {} w {}",
							new Object[] { iteration, propertyName, toObject.getClass().getSimpleName() });
				} else if (logger.isTraceEnabled()) {
					logger.warn(
							"Iteracja: {}. Obiekt setter'a nie posiada zmapowanej metody by móc ustawić {} ({}) w {}",
							new Object[] { iteration, propertyName,
									(propertyValue != null ? propertyValue.getClass().getSimpleName() : "n/a"),
									toObject.getClass().getSimpleName() });
				}

			}
		} catch (MercuryException e) {
			throw e;
		} catch (Exception e) {
			throw new MercuryException(InternalErrorException.ERROR_CODE,
					"[" + e.getClass().getSimpleName() + "] Błąd w mapowaniu obiektu encji: fromType="
							+ type.getSimpleName() + "; toType=" + targetClazz + "; propertyName=" + propertyName
							+ "; setterArg=" + setterArg + "; iteration=" + nextiteration + "\n " + e.getMessage(),
					e);
		}

		return toObject;
	}

	/* Overridden (non-Javadoc) */
	@Override
	public Object convert(Class<?> type, Object entity, ObjectMetaData entityMetaDataForGetter,
			ObjectMetaData entityMetaDataForSetter, int iteration) throws MercuryException {
		Object result = null;
		if (entity != null) {
			if (logger.isTraceEnabled()) {
				logger.trace("{} --> convert form {} ({}): start\n\tsetter={}\n\tgetter={}", new Object[] { iteration,
						entity.getClass(), iteration, entityMetaDataForSetter, entityMetaDataForGetter });
			}
			IEntityPropertyMapper propertyMapper = new EntityPropertyMapper();
			propertyMapper.init(entityMetaDataForSetter.getParamType(), entityMetaDataForSetter.getParamType(),
					entityMetaDataForGetter, entityMetaDataForSetter);
			result = propertyMapper.mapObj(type, entity, iteration);
		}
		return result;
	}

	/* Overridden (non-Javadoc) */
	@Override
	@SuppressWarnings("unchecked")
	public Object convertList(Class<?> elementType, Object collectionOb, ObjectMetaData entityMetaDataForGetter,
			ObjectMetaData entityMetaDataForSetter, int iteration) throws MercuryException {
		if (logger.isTraceEnabled()) {
			logger.trace("{} --> convertList form {} ({}): start\n\tsetter={}\n\tgetter={}", new Object[] { iteration,
					collectionOb.getClass(), iteration, entityMetaDataForSetter, entityMetaDataForGetter });
		}

		if (collectionOb != null) {
			if (collectionOb instanceof List) {
				List<Object> list = new ArrayList<Object>();
				for (Object entity : (List<Object>) collectionOb) {
					Object element = convert(elementType, entity, entityMetaDataForGetter, entityMetaDataForSetter,
							iteration);
					list.add(element);
				}
				return list;
			} else if (collectionOb instanceof Set) {
				Set<Object> set = new HashSet<Object>();
				for (Object entity : (Set<Object>) collectionOb) {
					Object element = convert(elementType, entity, entityMetaDataForGetter, entityMetaDataForSetter,
							iteration);
					set.add(element);
				}

				return set;
			} else {
				logger.warn("Obiekt listy {} jest nieobsługiwany podczas inicjalizacji!", collectionOb.getClass());
			}
		}
		return null;
	}

}
