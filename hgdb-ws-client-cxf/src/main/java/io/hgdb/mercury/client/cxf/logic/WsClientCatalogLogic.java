/**
 * 
 */
package io.hgdb.mercury.client.cxf.logic;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import io.hgdb.mercury.client.services.EntityPropertyMapper;
import pro.ibpm.mercury.config.MercuryConfig;
import pro.ibpm.mercury.context.Context;
import pro.ibpm.mercury.dao.services.IEntityPropertyMapper;
import pro.ibpm.mercury.dto.DtoObject;
import pro.ibpm.mercury.entities.MCatalog;
import pro.ibpm.mercury.entities.MEntity;
import pro.ibpm.mercury.entities.MIdModifier;
import pro.ibpm.mercury.entities.helpers.EntityHelper;
import pro.ibpm.mercury.exceptions.InternalErrorException;
import pro.ibpm.mercury.exceptions.MercuryException;
import pro.ibpm.mercury.logic.MCatalogLogic;
import pro.ibpm.mercury.utils.property.config.ObjectMetaData;
import pro.ibpm.mercury.utils.property.config.ObjectMetaDataHelper;
import pro.ibpm.mercury.ws.server.api.actions.IActionRoot;
import pro.ibpm.mercury.ws.server.api.returns.IWsStatus;
import pro.ibpm.mercury.ws.server.api.returns.IWsStatusWithDtos;

/**
 * 
 * WsClientCatalogLogic
 *
 * @author Sławomir Cichy &lt;slawomir.cichy@ibpm.pro&gt;
 * @version $Revision: 1.1 $
 *
 *
 * @param <Relationship>
 * @param <RelationshipId>
 * @param <Node>
 * @param <Id>
 * @param <Ws>
 */
@SuppressWarnings("serial")
public abstract class WsClientCatalogLogic<
		/* 0 */
		Relationship extends MCatalog<Relationship, RelationshipId, Node, Id> & MEntity & MIdModifier<RelationshipId>,
		/* 1 */
		RelationshipId,
		/* 2 */
		Node extends MIdModifier<Id>,
		/* 3 */
		Id,
		/* 4 */
		Ws extends IActionRoot> extends WsClientBigDataLogic<Relationship, RelationshipId, Ws>
		implements MCatalogLogic<Relationship, RelationshipId, Node, Id> {

	/** Obiekt klasy node. */
	private final Class<Node> nodeClass;
	private final Class<? extends DtoObject> nodeClassDto;
	private IEntityPropertyMapper collectionNodeInitializer = null;
	private IEntityPropertyMapper collectionNodeInitializerEager = null;

	@SuppressWarnings("unchecked")
	public WsClientCatalogLogic() {
		Class<?> tmpClass = this.getClass();
		while (!(tmpClass.getGenericSuperclass() instanceof ParameterizedType)) {
			tmpClass = tmpClass.getSuperclass();
		}
		this.nodeClass = (Class<Node>) ((ParameterizedType) tmpClass.getGenericSuperclass())
				.getActualTypeArguments()[2];

		String persistentClassDtoName = EntityHelper.DTO_PACKAGE_NAME + MercuryConfig.DOT + nodeClass.getSimpleName()
				+ EntityHelper.DTO_NAME_SUFFIX;
		try {
			nodeClassDto = (Class<? extends DtoObject>) Class.forName(persistentClassDtoName);
		} catch (ClassNotFoundException e) {
			throw new IllegalArgumentException(e);
		}
	}

	public IEntityPropertyMapper getCollectionNodeInitializer() throws MercuryException {
		if (collectionNodeInitializer == null) {
			ObjectMetaData singleGetterObjectMetaData = ObjectMetaDataHelper.loadGetters(nodeClassDto,
					/* propertyConfig */ null, /* eager */ false, /* forCollection */ EntityHelper.FOR_COLLECTION);
			ObjectMetaData singleSetterObjectMetaData = ObjectMetaDataHelper.loadSetters(nodeClass,
					/* propertyConfig */ null, /* eager */ false, /* forCollection */ EntityHelper.FOR_COLLECTION);
			collectionNodeInitializer = new EntityPropertyMapper();
			collectionNodeInitializer.init(nodeClassDto, nodeClass, singleGetterObjectMetaData,
					singleSetterObjectMetaData);
		}
		return collectionNodeInitializer;
	}

	public IEntityPropertyMapper getCollectionNodeInitializerEager() throws MercuryException {
		if (collectionNodeInitializerEager == null) {
			ObjectMetaData singleGetterObjectMetaData = ObjectMetaDataHelper.loadGetters(nodeClassDto,
					/* propertyConfig */ null, /* eager */ true, /* forCollection */ EntityHelper.FOR_COLLECTION);
			ObjectMetaData singleSetterObjectMetaData = ObjectMetaDataHelper.loadSetters(nodeClass,
					/* propertyConfig */ null, /* eager */ true, /* forCollection */ EntityHelper.FOR_COLLECTION);
			collectionNodeInitializerEager = new EntityPropertyMapper();
			collectionNodeInitializerEager.init(nodeClassDto, nodeClass, singleGetterObjectMetaData,
					singleSetterObjectMetaData);
		}
		return collectionNodeInitializerEager;
	}

	/**
	 * Inicjalizacja elementów katalogu
	 * 
	 * @param context
	 * @param dtoList
	 * @return
	 * @throws MercuryException
	 */
	@SuppressWarnings("unchecked")
	public <EDto> List<Node> initNodeCollection(Context context, Collection<EDto> dtoList) throws MercuryException {
		if (dtoList == null) {
			return Collections.emptyList();
		}
		List<Node> returnList = new ArrayList<Node>();
		boolean eager = (StringUtils.isNotBlank(context.getEager4omdBuilder())
				? Boolean.parseBoolean(context.getEager4omdBuilder())
				: true);
		if (eager) {
			for (EDto entity : dtoList) {
				returnList.add((Node) getCollectionNodeInitializerEager().mapObj(entity));
			}
		} else {
			for (EDto entity : dtoList) {
				returnList.add((Node) getCollectionNodeInitializer().mapObj(entity));
			}
		}
		return returnList;

	}

	protected <T> Collection<Node> getNodeList(Context context, final IWsStatusWithDtos<T> wsStatusWithBag)
			throws MercuryException {
		logger.debug("wsStatusWithDtos={}", new Object[] { wsStatusWithBag });
		if (checkWsStatus((IWsStatus) wsStatusWithBag)) {
			try {
				return initNodeCollection(context, wsStatusWithBag.getDtos());
			} catch (Exception e) {
				throw new InternalErrorException(e);
			}
		}
		return Collections.emptyList();
	}

	@Override
	public List<Relationship> getAllParentsRelationshipsByNode(Context context, Node t, String mountPoint)
			throws MercuryException {
		return getAllParentsRelationshipsByNodeId(context, t.getId(), mountPoint);
	}

	@Override
	public List<Relationship> getAllChildrenRelationshipsByNode(Context context, Node t, String mountPoint)
			throws MercuryException {
		return getAllChildrenRelationshipsByNodeId(context, t.getId(), mountPoint);
	}

	@Override
	public List<Node> nodesResultNewInstance(Context context) {
		return new ArrayList<Node>();
	}
}
