package fr97.umlfx.views;

import javafx.fxml.Initializable;

/**
 * Describes behaviour of FXMLController
 * @param <M> model type
 */
public interface FXMLController<M> extends Initializable {

    /**
     * Sets given model to this controller
     * @param model given model
     */
    void setModel(M model);

    /**
     * Gets model that is associated with this controller
     * @return model associated with controller
     */
    M getModel();

}
