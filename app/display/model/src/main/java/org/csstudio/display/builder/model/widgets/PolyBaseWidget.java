/*******************************************************************************
 * Copyright (c) 2015-2020 Oak Ridge National Laboratory.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.csstudio.display.builder.model.widgets;

import static org.csstudio.display.builder.model.properties.CommonWidgetProperties.propPoints;

import java.util.List;

import org.csstudio.display.builder.model.WidgetProperty;
import org.csstudio.display.builder.model.properties.Points;

/** Base for widgets that display points
 *  Please not that this class does not override defineProperties() to add the points property;
 *   You have to manually add it by calling definePoints() in your defineProperties() function
 *  @author Kay Kasemir
 */
@SuppressWarnings("nls")
public abstract class PolyBaseWidget extends MacroWidget
{
    private volatile WidgetProperty<Points> points;

    public PolyBaseWidget(final String type)
    {
        super(type);
    }

    protected void definePoints(final List<WidgetProperty<?>> properties)
    {
        properties.add(points = propPoints.createProperty(this, new Points()));
    }

    /** @return 'points' property */
    public WidgetProperty<Points> propPoints()
    {
        return points;
    }
}
