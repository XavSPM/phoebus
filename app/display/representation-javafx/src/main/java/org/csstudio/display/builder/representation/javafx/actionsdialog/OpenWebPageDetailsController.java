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
 */

package org.csstudio.display.builder.representation.javafx.actionsdialog;

import org.csstudio.display.builder.model.properties.ActionInfo;
import org.csstudio.display.builder.model.properties.OpenWebpageActionInfo;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

/** FXML Controller */
public class OpenWebPageDetailsController implements ActionDetailsController{

    private OpenWebpageActionInfo openWebpageActionInfo;

    @FXML
    private TextField description;
    @FXML
    private TextField url;

    private StringProperty descriptionProperty = new SimpleStringProperty();
    private StringProperty urlProperty = new SimpleStringProperty();

    /** @param actionInfo ActionInfo */
    public OpenWebPageDetailsController(ActionInfo actionInfo){
        this.openWebpageActionInfo = (OpenWebpageActionInfo)actionInfo;
    }

    /** Init */
    @FXML
    public void initialize(){
        descriptionProperty.setValue(openWebpageActionInfo.getDescription());
        urlProperty.setValue(openWebpageActionInfo.getURL());

        description.textProperty().bindBidirectional(descriptionProperty);
        url.textProperty().bindBidirectional(urlProperty);
    }

    /** @return ActionInfo */
    @Override
    public ActionInfo getActionInfo(){
        return new OpenWebpageActionInfo(descriptionProperty.get(), urlProperty.get());
    }
}
