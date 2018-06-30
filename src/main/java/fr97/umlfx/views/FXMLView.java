package fr97.umlfx.views;

import fr97.umlfx.utils.ArgumentChecker;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;

import java.io.IOException;

/**
 * <p>
 * FXMLView is abstract class that encapsulates common instructions used for instantiating an FXML view file
 * By default it will try to load view with same name as class extending this view, but extending class can call
 * constructor which takes name of file to be used instead of default.
 *
 * So if extending class is called ExampleView then it will look for a file in same directory called ExampleView.fxml
 *
 * This class enforces MVC structure, model must be given to constructor
 * and controller that implements {@link FXMLController} must be set in fxml file
 * </p>
 * @param <N> type of root node for view
 * @param <M> type of model associated with this view
 */
public abstract class FXMLView<N extends Node, M> implements View<N> {

    private static final String FXML_SUFFIX = ".fxml";
    private final String DEFAULT_VIEW_NAME = getClass().getSimpleName();;

    private final FXMLLoader loader = new FXMLLoader();

    private String viewName;
    private N root;

    protected FXMLController<M> controller;

    /**
     *
     * @param model model to be associated with the view
     * @throws IllegalStateException if controller isn't set in fxml file
     * @throws IllegalArgumentException if given model is null
     */
    protected FXMLView(M model) throws IllegalStateException, IllegalArgumentException {
        ArgumentChecker.notNull(model, "model can't be null");
        viewName = DEFAULT_VIEW_NAME;
        loader.setLocation(getClass().getResource(viewName+FXML_SUFFIX));
        System.out.println("View name: " + DEFAULT_VIEW_NAME);
        load(model, viewName);
    }

    /**
     *
     * @param model model to be associated with the view
     * @param viewName alternative name of fxml file, by default it uses same name as class representing that view
     * @throws IllegalStateException if controller isn't set in fxml file
     * @throws IllegalArgumentException if given model is null
     */
    protected FXMLView(M model, String viewName) throws IllegalStateException, IllegalArgumentException {
        ArgumentChecker.notNull(model, "model can't be null");
        this.viewName = viewName;
        System.out.println("View name: " + viewName);
        System.out.println(getClass().getResource(viewName+FXML_SUFFIX));
        loader.setLocation(getClass().getResource(viewName+FXML_SUFFIX));
        load(model, this.viewName);
    }

    private void load(M model, String viewPath) throws IllegalStateException{
        try{
            root = loader.load();
            controller = loader.getController();
            if(controller == null)
                throw new  IllegalStateException("Controller isn't set for " + viewPath);
            controller.setModel(model);
        } catch (IOException ex){
            System.out.println("Error loading fxml: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    /**
     *
     * @return controller associated to this view
     */
    public FXMLController<M> getController(){
        return this.controller;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public N getRoot() {
        return root;
    }
}
