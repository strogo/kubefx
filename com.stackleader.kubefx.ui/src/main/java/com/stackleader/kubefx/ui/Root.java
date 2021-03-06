package com.stackleader.kubefx.ui;

import com.stackleader.kubefx.ui.toolbar.LogoBanner;
import aQute.bnd.annotation.component.Activate;
import aQute.bnd.annotation.component.Component;
import aQute.bnd.annotation.component.Reference;
import com.stackleader.kubefx.ui.footer.Footer;
import java.awt.DisplayMode;
import java.awt.GraphicsEnvironment;
import javafx.application.Platform;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

/**
 *
 * @author dcnorris
 */
@Component(immediate = true, provide = Root.class)
public class Root extends VBox {

    private MainSplitPane mainSplitPane;
    private AnchorPane anchorPane;
    private Footer footer;
    private LogoBanner logoBanner;

    public Root() {
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        final DisplayMode displayMode = ge.getDefaultScreenDevice().getDisplayMode();
        double width = displayMode.getWidth() * .8;
        double height = displayMode.getHeight() * .8;
        setPrefSize(width, height);
        anchorPane = new AnchorPane();
        anchorPane.getStyleClass().add("theme-presets");
        VBox.setVgrow(anchorPane, Priority.ALWAYS);
    }

    @Activate
    public void activate() {
        Platform.runLater(() -> {
            final SplitPane splitPane = mainSplitPane.getRoot();
            AnchorPane.setBottomAnchor(splitPane, 0d);
            AnchorPane.setLeftAnchor(splitPane, 0d);
            AnchorPane.setRightAnchor(splitPane, 0d);
            AnchorPane.setTopAnchor(splitPane, 0d);
            anchorPane.getChildren().add(splitPane);
//            getChildren().add(menuBarManager.getMenuBar());
//            getChildren().add(toolbarProvider.getTopToolbar());
            getChildren().add(logoBanner);
            getChildren().add(anchorPane);
            getChildren().add(footer);
        });
    }

    @Reference(unbind = "removeMainSplitPane")
    public void setMainSplitPane(MainSplitPane mainSplitPane) {
        this.mainSplitPane = mainSplitPane;
    }

    public void removeMainSplitPane(MainSplitPane mainSplitPane) {
        Platform.runLater(() -> {
            getChildren().clear();
        });
    }

    @Reference(unbind = "removeFooter")
    public void setFooter(Footer footer) {
        this.footer = footer;
    }

    @Reference(unbind = "removeLogoBanner")
    public void setLogoBanner(LogoBanner logoBanner) {
        this.logoBanner = logoBanner;
    }

    public void removeLogoBanner(LogoBanner logoBanner) {
        Platform.runLater(() -> {
            getChildren().remove(logoBanner);
        });
    }

    public void removeFooter(Footer footer) {
        Platform.runLater(() -> {
            getChildren().remove(footer);
        });
    }
}
