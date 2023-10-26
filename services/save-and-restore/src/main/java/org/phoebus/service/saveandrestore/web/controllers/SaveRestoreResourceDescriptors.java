package org.phoebus.service.saveandrestore.web.controllers;

public class SaveRestoreResourceDescriptors {

    public static final String SAR_AUTH_LOGIN = "/login";

    public static final String SAR_CONFIG = "/config";
    public static final String SAR_CONFIG_ID = "/config/{uniqueId}";

    public static final String SAR_NODE = "/node";
    public static final String SAR_NODES = "/nodes";
    public static final String SAR_NODE_ID = "/node/{uniqueNodeId}";
    public static final String SAR_NODE_ID_PARENT = "/node/{uniqueNodeId}/parent";
    public static final String SAR_NODE_ID_CHILDREN = "/node/{uniqueNodeId}/children";

    public static final String SAR_SNAPSHOT = "/snapshot";
    public static final String SAR_SNAPSHOTS = "/snapshots";
    public static final String SAR_SNAPSHOT_ID = "/snapshot/{uniqueId}";

    public static final String SAR_COMPOSITE_SNAPSHOT = "/composite-snapshot";
    public static final String SAR_COMPOSITE_SNAPSHOT_ID = "/composite-snapshot/{uniqueId}";
    public static final String SAR_COMPOSITE_SNAPSHOT_ID_NODES = "/composite-snapshot/{uniqueId}/nodes";
    public static final String SAR_COMPOSITE_SNAPSHOT_ID_ITEMS = "/composite-snapshot/{uniqueId}/items";
    public static final String SAR_COMPOSITE_SNAPSHOT_CHECK = "/composite-snapshot-consistency-check";

    public static final String SAR_TAGS = "/tags";

    public static final String SAR_SEARCH = "/search";

    public static final String SAR_FILTER = "/filter";
    public static final String SAR_FILTERS = "/filters";
    public static final String SAR_FILTER_NAME = "/filter/{name}";

    public static final String SAR_MOVE = "/move";
    public static final String SAR_COPY = "/copy";
    public static final String SAR_PATH = "/path";
    public static final String SAR_PATH_ID = "/path/{uniqueNodeId}";
}
