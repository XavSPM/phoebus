/*******************************************************************************
 * Copyright (c) 2018 Oak Ridge National Laboratory.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 ******************************************************************************/
package org.csstudio.scan.ui.datatable;

import java.net.URI;

import org.csstudio.scan.client.Preferences;
import org.csstudio.scan.client.ScanClient;
import org.csstudio.scan.ui.ScanUI;
import org.phoebus.framework.spi.AppDescriptor;
import org.phoebus.framework.spi.AppInstance;
import org.phoebus.ui.docking.DockItemWithInput;
import org.phoebus.ui.docking.DockPane;

/** Application instance for Scan Data Table
 *  @author Kay Kasemir
 */
@SuppressWarnings("nls")
public class ScanDataTableInstance implements AppInstance
{
    private final ScanDataTableApplication app;
    private final DockItemWithInput tab;

    public ScanDataTableInstance(final ScanDataTableApplication app, final long scan_id)
    {
        this.app = app;

        final DataTable data_table = create(scan_id);
        final URI input = ScanUI.createURI(scan_id);
        tab = new DockItemWithInput(this, data_table, input, null, null);
        tab.setLabel("Data for Scan #" + scan_id);
        tab.addCloseCheck(() ->
        {
            data_table.dispose();
            return true;
        });
        DockPane.getActiveDockPane().addTab(tab);
    }

    private DataTable create(final long scan_id)
    {
        final ScanClient client = new ScanClient(Preferences.host, Preferences.port);
        return new DataTable(client, scan_id);
    }

    @Override
    public AppDescriptor getAppDescriptor()
    {
        return app;
    }
}
