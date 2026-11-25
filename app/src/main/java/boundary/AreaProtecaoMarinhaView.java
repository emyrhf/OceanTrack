package boundary;

import java.time.format.DateTimeFormatter;

import control.AreaProtecaoMarinhaController;
import entity.AreaProtecaoMarinha;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

public class AreaProtecaoMarinhaView {
    private AreaProtecaoMarinhaController control = new AreaProtecaoMarinhaController();
    private TableView<AreaProtecaoMarinha> listagem = new TableView<>();
    private DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private BorderPane root;

    public AreaProtecaoMarinhaView() {
        BorderPane p = new BorderPane();
        this.root = p;
        GridPane grid = new GridPane();

        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(12));

        TextField txtId = new TextField();
        txtId.setEditable(false);
        txtId.setFocusTraversable(false);
        txtId.setMouseTransparent(true);
        txtId.setStyle("-fx-background-color: #e0e0e0; -fx-opacity: 1; -fx-alignment: RIGHT;");

        TextField txtNome = new TextField();
        txtNome.setPromptText("Ex: Parque Nacional Marinho...");
        TextField txtEstado = new TextField();
        txtEstado.setPromptText("Ex: São Paulo");
        
        TextField txtAreaKm2 = new TextField();
        txtAreaKm2.setPromptText("Ex: 1200");
        
        TextField txtOrgaoResponsavel = new TextField();
        txtOrgaoResponsavel.setPromptText("Ex: Icmbio");

        TextField txtCadastro = new TextField();
        txtCadastro.setEditable(false);
        txtCadastro.setFocusTraversable(false);
        txtCadastro.setMouseTransparent(true);
        txtCadastro.setStyle("-fx-background-color: #e0e0e0; -fx-opacity: 1; -fx-alignment: RIGHT;");

        Button btnCadastrar = new Button("Cadastrar");
        Button btnPesquisar = new Button("Pesquisar");
        Button btnLimpar = new Button("Limpar");

        ComboBox<String> cmbAtiva = new ComboBox<>();
        cmbAtiva.getItems().addAll(
            "Ativa",
            "Inativa"
        );
        cmbAtiva.setPromptText("Selecione...");

        Bindings.bindBidirectional(txtId.textProperty(), control.idProperty());
        Bindings.bindBidirectional(txtNome.textProperty(), control.nomeProperty());
        Bindings.bindBidirectional(txtEstado.textProperty(), control.estadoProperty());
        Bindings.bindBidirectional(txtAreaKm2.textProperty(), control.areaKm2StringProperty());
        Bindings.bindBidirectional(txtOrgaoResponsavel.textProperty(), control.orgaoResponsavelProperty());
        Bindings.bindBidirectional(txtCadastro.textProperty(), control.dataCStringProperty());
        Bindings.bindBidirectional(cmbAtiva.valueProperty(), control.ativaStringProperty());

        btnCadastrar.setOnAction(e -> {
            if (control.cadastrar()) {
                cmbAtiva.getSelectionModel().clearSelection();
                new Alert(Alert.AlertType.INFORMATION, "Área de proteção registrada com sucesso!", ButtonType.OK).showAndWait();
            } else {
                String msg = control.mensagemErroProperty().get();
                new Alert(
                    Alert.AlertType.ERROR,
                    (msg != null && !msg.isBlank()) ? msg : "Preencha os campos obrigatórios corretamente.",
                    ButtonType.OK
                ).showAndWait();
            }
        });

        btnPesquisar.setOnAction(e -> {
            boolean semFiltrosPrincipais =
                    (txtNome.getText() == null || txtNome.getText().isBlank())
                    && (txtEstado.getText() == null || txtEstado.getText().isBlank())
                    && (txtOrgaoResponsavel.getText() == null || txtOrgaoResponsavel.getText().isBlank())
                    && (cmbAtiva.getValue() == null || cmbAtiva.getValue().isBlank());

            if (semFiltrosPrincipais && (txtId.getText() == null || txtId.getText().isBlank())) {
                TextInputDialog dialog = new TextInputDialog();
                dialog.setTitle("Pesquisar por ID");
                dialog.setHeaderText(null);
                dialog.setContentText("Informe o ID da área de proteção:");
                dialog.showAndWait().ifPresent(idDigitado -> {
                    if (!idDigitado.isBlank()) {
                        control.idProperty().set(idDigitado.trim());
                    }
                });
            }

            if (control.pesquisar()) {
                new Alert(Alert.AlertType.INFORMATION, "Área(s) de proteção encontrada(s)!", ButtonType.OK).showAndWait();
            } else {
                String msg = control.mensagemErroProperty().get();
                new Alert(
                    Alert.AlertType.ERROR,
                    (msg != null && !msg.isBlank()) ? msg : "Área de proteção não encontrada.",
                    ButtonType.OK
                ).showAndWait();
            }
        });

        btnLimpar.setOnAction(e -> {
            control.limparCampos();
            cmbAtiva.getSelectionModel().clearSelection();
            control.carregarListagem();
        });

        grid.add(new Label("Id:"), 0, 0);
        grid.add(txtId, 1, 0);

        grid.add(new Label("Nome *:"), 0, 1);
        grid.add(txtNome, 1, 1);

        grid.add(new Label("Estado *:"), 0, 2);
        grid.add(txtEstado, 1, 2);

        grid.add(new Label("Área (km²) *:"), 0, 3);
        grid.add(txtAreaKm2, 1, 3);

        grid.add(new Label("Órgão Responsável *:"), 2, 1);
        grid.add(txtOrgaoResponsavel, 3, 1);

        grid.add(new Label("Situação *:"), 2, 2);
        grid.add(cmbAtiva, 3, 2);

        grid.add(new Label("Data de Cadastro:"), 2, 3);
        grid.add(txtCadastro, 3, 3);

        HBox botoesForm = new HBox(8, btnCadastrar, btnPesquisar, btnLimpar);
        grid.add(botoesForm, 0, 4, 2, 1);

        TableColumn<AreaProtecaoMarinha, String> colId = new TableColumn<>("Id");
        colId.setCellValueFactory(item -> new ReadOnlyStringWrapper(String.valueOf(item.getValue().getId())));

        TableColumn<AreaProtecaoMarinha, String> colNome = new TableColumn<>("Nome");
        colNome.setCellValueFactory(item -> new ReadOnlyStringWrapper(item.getValue().getNome()));

        TableColumn<AreaProtecaoMarinha, String> colEstado = new TableColumn<>("Estado");
        colEstado.setCellValueFactory(item -> new ReadOnlyStringWrapper(item.getValue().getEstado()));

        TableColumn<AreaProtecaoMarinha, String> colArea = new TableColumn<>("Área (km²)");
        colArea.setCellValueFactory(item -> new ReadOnlyStringWrapper(String.valueOf(item.getValue().getArea())));

        TableColumn<AreaProtecaoMarinha, String> colOrgao = new TableColumn<>("Órgão Responsável");
        colOrgao.setCellValueFactory(item -> new ReadOnlyStringWrapper(item.getValue().getOrgaoResponavel()));

        TableColumn<AreaProtecaoMarinha, String> colAtiva = new TableColumn<>("Situação");
        colAtiva.setCellValueFactory(item -> new ReadOnlyStringWrapper(item.getValue().isAtiva() ? "Ativa" : "Inativa"));

        TableColumn<AreaProtecaoMarinha, String> colCadastro = new TableColumn<>("Cadastrado");
        colCadastro.setCellValueFactory(item -> {
            var data = item.getValue().getDataCadastro();
            return new ReadOnlyStringWrapper(data != null ? data.format(formatador) : "");
        });

        TableColumn<AreaProtecaoMarinha, Void> colAcoes = new TableColumn<>("Ações");
        colAcoes.setCellFactory(col -> new TableCell<>() {
            private final Button btnAtualizar = new Button("Atualizar");
            private final Button btnExcluir = new Button("Excluir");
            private final HBox box = new HBox(6, btnAtualizar, btnExcluir);

            {
                btnAtualizar.setOnAction(e -> {
                    AreaProtecaoMarinha selecionada = getTableView().getItems().get(getIndex());
                    if (selecionada != null) {
                        control.idProperty().set(String.valueOf(selecionada.getId()));
                        control.nomeProperty().set(txtNome.getText() != null ? txtNome.getText() : "");
                        control.estadoProperty().set(txtEstado.getText() != null ? txtEstado.getText() : "");
                        control.areaKm2StringProperty().set(txtAreaKm2.getText() != null ? txtAreaKm2.getText() : "");
                        control.orgaoResponsavelProperty().set(txtOrgaoResponsavel.getText() != null ? txtOrgaoResponsavel.getText() : "");
                        control.ativaStringProperty().set(cmbAtiva.getValue() != null ? cmbAtiva.getValue() : "");

                        if (control.atualizar()) {
                            cmbAtiva.getSelectionModel().clearSelection();
                            new Alert(Alert.AlertType.INFORMATION, "Área de proteção atualizada com sucesso!", ButtonType.OK).showAndWait();
                        } else {
                            String msg = control.mensagemErroProperty().get();
                            new Alert(
                                Alert.AlertType.ERROR,
                                (msg != null && !msg.isBlank()) ? msg : "Erro ao atualizar a área de proteção.",
                                ButtonType.OK
                            ).showAndWait();
                        }
                    }
                });

                btnExcluir.setOnAction(e -> {
                    AreaProtecaoMarinha selecionada = getTableView().getItems().get(getIndex());
                    if (selecionada != null) {
                        control.idProperty().set(String.valueOf(selecionada.getId()));
                        if (control.deletar()) {
                            cmbAtiva.getSelectionModel().clearSelection();
                            new Alert(Alert.AlertType.INFORMATION, "Área de proteção deletada com sucesso!", ButtonType.OK).showAndWait();
                        } else {
                            new Alert(Alert.AlertType.ERROR, "Erro ao deletar a área de proteção.", ButtonType.OK).showAndWait();
                        }
                    }
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : box);
            }
        });

        listagem.getSelectionModel().selectedItemProperty().addListener((obs, antiga, nova) -> {
            if (nova != null) {
                control.idProperty().set(String.valueOf(nova.getId()));
                control.nomeProperty().set(nova.getNome() != null ? nova.getNome() : "");
                control.estadoProperty().set(nova.getEstado() != null ? nova.getEstado() : "");
                control.areaKm2StringProperty().set(String.valueOf(nova.getArea()));
                control.orgaoResponsavelProperty().set(nova.getOrgaoResponavel() != null ? nova.getOrgaoResponavel() : "");
                control.ativaStringProperty().set(nova.isAtiva() ? "Ativa" : "Inativa");
                control.dataCStringProperty().set(nova.getDataCadastro() != null ? nova.getDataCadastro().format(formatador) : "");
            }
        });

        colId.setMinWidth(45);
        colId.setPrefWidth(50);
        colId.setMaxWidth(60);

        colNome.setMinWidth(220);
        colNome.setPrefWidth(360);
        colNome.setMaxWidth(Double.MAX_VALUE);

        colEstado.setMinWidth(110);
        colEstado.setPrefWidth(130);

        colArea.setMinWidth(85);
        colArea.setPrefWidth(95);
        colArea.setMaxWidth(115);

        colOrgao.setMinWidth(150);
        colOrgao.setPrefWidth(190);

        colAtiva.setMinWidth(75);
        colAtiva.setPrefWidth(85);
        colAtiva.setMaxWidth(100);

        colCadastro.setMinWidth(95);
        colCadastro.setPrefWidth(105);
        colCadastro.setMaxWidth(120);

        colAcoes.setMinWidth(155);
        colAcoes.setPrefWidth(160);
        colAcoes.setMaxWidth(175);

        listagem.getColumns().addAll(colId, colNome, colEstado, colArea, colOrgao, colAtiva, colCadastro, colAcoes);
        listagem.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_FLEX_LAST_COLUMN);
        listagem.setItems(control.getListagem());

        p.setTop(grid);
        p.setCenter(listagem);
    }

    public BorderPane getRoot() {
        return root;
    }
}