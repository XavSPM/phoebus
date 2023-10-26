/*
 * Copyright (C) 2020 European Spallation Source ERIC.
 *
 *  This program is free software; you can redistribute it and/or
 *  modify it under the terms of the GNU General Public License
 *  as published by the Free Software Foundation; either version 2
 *  of the License, or (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program; if not, write to the Free Software
 *  Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 *
 */

package org.phoebus.service.saveandrestore.web.controllers;

import org.phoebus.applications.saveandrestore.model.*;
import org.phoebus.service.saveandrestore.persistence.dao.NodeDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

import static org.phoebus.service.saveandrestore.web.controllers.SaveRestoreResourceDescriptors.SAR_COMPOSITE_SNAPSHOT;
import static org.phoebus.service.saveandrestore.web.controllers.SaveRestoreResourceDescriptors.SAR_COMPOSITE_SNAPSHOT_ID;
import static org.phoebus.service.saveandrestore.web.controllers.SaveRestoreResourceDescriptors.SAR_COMPOSITE_SNAPSHOT_ID_ITEMS;
import static org.phoebus.service.saveandrestore.web.controllers.SaveRestoreResourceDescriptors.SAR_COMPOSITE_SNAPSHOT_ID_NODES;
import static org.phoebus.service.saveandrestore.web.controllers.SaveRestoreResourceDescriptors.SAR_COMPOSITE_SNAPSHOT_CHECK;

@SuppressWarnings("unused")
@RestController
public class CompositeSnapshotController extends BaseController {

    @Autowired
    private NodeDAO nodeDAO;

    @PutMapping(value = SAR_COMPOSITE_SNAPSHOT, produces = JSON)
    @PreAuthorize("hasRole(this.roleUser)")
    public CompositeSnapshot createCompositeSnapshot(@RequestParam(value = "parentNodeId") String parentNodeId,
                                                     @RequestBody CompositeSnapshot compositeSnapshot,
                                                     Principal principal) {
        if(!compositeSnapshot.getCompositeSnapshotNode().getNodeType().equals(NodeType.COMPOSITE_SNAPSHOT)){
            throw new IllegalArgumentException("Composite snapshot node of wrong type");
        }
        compositeSnapshot.getCompositeSnapshotNode().setUserName(principal.getName());
        return nodeDAO.createCompositeSnapshot(parentNodeId, compositeSnapshot);
    }

    @PostMapping(value = SAR_COMPOSITE_SNAPSHOT, produces = JSON)
    @PreAuthorize("hasRole(this.roleAdmin) or (hasRole(this.roleUser) and this.mayUpdate(#compositeSnapshot, #principal))")
    public CompositeSnapshot updateCompositeSnapshot(@RequestBody CompositeSnapshot compositeSnapshot,
                                                     Principal principal) {
        if(!compositeSnapshot.getCompositeSnapshotNode().getNodeType().equals(NodeType.COMPOSITE_SNAPSHOT)){
            throw new IllegalArgumentException("Composite snapshot node of wrong type");
        }
        compositeSnapshot.getCompositeSnapshotNode().setUserName(principal.getName());
        return nodeDAO.updateCompositeSnapshot(compositeSnapshot);
    }

    /**
     * NOTE: this method MUST be public!
     *
     * <p>
     * An authenticated user may save a composite snapshot, and update if user identity is same as the target's
     * composite snapshot {@link Node}.
     * </p>
     *
     * @param compositeSnapshot {@link CompositeSnapshot} identifying the target of the user's update operation.
     * @param principal Identifies user.
     * @return <code>false</code> if user may not update the {@link CompositeSnapshot}.
     */
    public boolean mayUpdate(CompositeSnapshot compositeSnapshot, Principal principal){
        Node node = nodeDAO.getNode(compositeSnapshot.getCompositeSnapshotNode().getUniqueId());
        return node.getUserName().equals(principal.getName());
    }


    @GetMapping(value = SAR_COMPOSITE_SNAPSHOT_ID, produces = JSON)
    public CompositeSnapshotData getCompositeSnapshotData(@PathVariable String uniqueId) {
        return nodeDAO.getCompositeSnapshotData(uniqueId);
    }

    @GetMapping(value = SAR_COMPOSITE_SNAPSHOT_ID_NODES, produces = JSON)
    public List<Node> getCompositeSnapshotNodes(@PathVariable String uniqueId) {
        CompositeSnapshotData compositeSnapshotData = nodeDAO.getCompositeSnapshotData(uniqueId);
        return nodeDAO.getNodes(compositeSnapshotData.getReferencedSnapshotNodes());
    }

    @GetMapping(value = SAR_COMPOSITE_SNAPSHOT_ID_ITEMS, produces = JSON)
    public List<SnapshotItem> getCompositeSnapshotItems(@PathVariable String uniqueId) {
        return nodeDAO.getSnapshotItemsFromCompositeSnapshot(uniqueId);
    }

    /**
     * Utility end-point for the purpose of checking whether a set of snapshots contain duplicate PV names.
     * The input snapshot ids may refer to {@link Node}s of types {@link org.phoebus.applications.saveandrestore.model.NodeType#SNAPSHOT}
     * and {@link org.phoebus.applications.saveandrestore.model.NodeType#COMPOSITE_SNAPSHOT}
     *
     * @param snapshotNodeIds List of {@link Node} ids corresponding to {@link Node}s of types {@link org.phoebus.applications.saveandrestore.model.NodeType#SNAPSHOT}
     *                        and {@link org.phoebus.applications.saveandrestore.model.NodeType#COMPOSITE_SNAPSHOT}
     * @return A list of PV names that occur more than once across the list of {@link Node}s corresponding
     * to the input. Empty if no duplicates are found.
     */
    @PostMapping(value = SAR_COMPOSITE_SNAPSHOT_CHECK, produces = JSON)
    public List<String> checkSnapshotsConsistency(@RequestBody List<String> snapshotNodeIds) {
        return nodeDAO.checkForPVNameDuplicates(snapshotNodeIds);
    }
}
