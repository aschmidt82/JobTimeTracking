/* 
 * Copyright (C) 2019 Anika Schmidt
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package jobtimetracking.control;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author Anika.Schmidt
 */
public class Mainframe {

    @FXML
    private ScrollPane spView;
    @FXML
    private Label lblStatusTimeTracking;
    @FXML
    private AnchorPane apDetail;
    @FXML
    private Label lblLiveWorkTime;

    @FXML
    public void onEditProfile(ActionEvent event) throws IOException {
        //Open the Profil in Detail 
    }

    @FXML
    public void onManualTimeTracking(ActionEvent event) throws IOException {
        //Open the ManualTimeTracking in Main 
    }

    @FXML
    public void onEvaluation(ActionEvent event) throws IOException {
        //Open the Evaluation in Main
    }

    @FXML
    public void onBreak(ActionEvent event) throws IOException {
        //Open a popup with break information
    }
}
