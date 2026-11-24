package boundary;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;
public class Principal extends Application {
    @Override
    public void start(Stage stage) {
        TabPane tabPane = new TabPane();
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

        // CRUD ESPECIEMARINHA
        EspecieMarinhaView especieView = new EspecieMarinhaView();
        Tab tabEspecies = new Tab("Espécies Marinhas", especieView.getRoot());
        
        // CRUD AREAPROTECAOMARINHA
        AreaProtecaoMarinhaView areaProtecaoView = new AreaProtecaoMarinhaView();
        Tab tabAreaProtecao = new Tab("Área de Proteção Marinha", areaProtecaoView.getRoot());

        tabPane.getTabs().addAll(tabEspecies, tabAreaProtecao);

        Scene scene = new Scene(tabPane, 950, 650);
        stage.setTitle("OceanTrack");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}