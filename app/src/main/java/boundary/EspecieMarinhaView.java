package boundary;

import java.time.format.DateTimeFormatter;

import control.EspecieMarinhaController;
import entity.EspecieMarinha;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

public class EspecieMarinhaView {
    private EspecieMarinhaController control = new EspecieMarinhaController();
    private TableView<EspecieMarinha> listagem = new TableView<>();
    private DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private BorderPane root;

    public EspecieMarinhaView() {
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

        TextField txtNomeComum = new TextField();
        TextField txtNomeCientifico = new TextField();
        TextField txtHabitat = new TextField();
        TextField txtStatus = new TextField();

        TextField txtCadastro = new TextField();
        txtCadastro.setEditable(false);
        txtCadastro.setFocusTraversable(false);
        txtCadastro.setMouseTransparent(true);
        txtCadastro.setStyle("-fx-background-color: #e0e0e0; -fx-opacity: 1; -fx-alignment: RIGHT;");

        TextField txtAtualizacao = new TextField();
        txtAtualizacao.setEditable(false);
        txtAtualizacao.setFocusTraversable(false);
        txtAtualizacao.setMouseTransparent(true);
        txtAtualizacao.setStyle("-fx-background-color: #e0e0e0; -fx-opacity: 1; -fx-alignment: RIGHT;");

        Button btnCadastrar = new Button("Cadastrar");
        Button btnPesquisar = new Button("Pesquisar");
        Button btnLimpar = new Button("Limpar");

        String estiloDireita = "-fx-alignment: RIGHT;";
        txtNomeComum.setStyle(estiloDireita);
        txtNomeCientifico.setStyle(estiloDireita);
        txtHabitat.setStyle(estiloDireita);
        txtStatus.setStyle(estiloDireita);
        btnCadastrar.setStyle(estiloDireita);
        btnLimpar.setStyle(estiloDireita);
        btnPesquisar.setStyle(estiloDireita);

        Bindings.bindBidirectional(txtId.textProperty(), control.idProperty());
        Bindings.bindBidirectional(txtNomeComum.textProperty(), control.nomeComumProperty());
        Bindings.bindBidirectional(txtNomeCientifico.textProperty(), control.nomeCientificoProperty());
        Bindings.bindBidirectional(txtHabitat.textProperty(), control.habitatProperty());
        Bindings.bindBidirectional(txtCadastro.textProperty(), control.dataCStringProperty());
        Bindings.bindBidirectional(txtAtualizacao.textProperty(), control.dataAStringProperty());
        Bindings.bindBidirectional(txtStatus.textProperty(), control.statusProperty());

        btnCadastrar.setOnAction(e -> {
            if (control.cadastrar()) {
                new Alert(Alert.AlertType.INFORMATION, "Espécie registrada com sucesso!", ButtonType.OK).showAndWait();
            } else {
                new Alert(Alert.AlertType.ERROR, "Preencha os campos obrigatórios corretamente.", ButtonType.OK).showAndWait();
            }
        });

        btnPesquisar.setOnAction(e -> {
            if (control.pesquisar()) {
                new Alert(Alert.AlertType.INFORMATION, "Espécie(s) encontrada(s)!", ButtonType.OK).showAndWait();
            } else {
                new Alert(Alert.AlertType.ERROR, "Espécie não encontrada.", ButtonType.OK).showAndWait();
            }
        });

        btnLimpar.setOnAction(e -> {
            control.idProperty().set("");
            control.nomeComumProperty().set("");
            control.nomeCientificoProperty().set("");
            control.habitatProperty().set("");
            control.statusProperty().set("");
            control.dataCStringProperty().set("");
            control.dataAStringProperty().set("");
            control.carregarListagem();
        });

        grid.add(new Label("Id:"), 0, 0);
        grid.add(txtId, 1, 0);

        grid.add(new Label("Nome Comum:"), 0, 1);
        grid.add(txtNomeComum, 1, 1);

        grid.add(new Label("Nome Científico:"), 0, 2);
        grid.add(txtNomeCientifico, 1, 2);

        grid.add(new Label("Habitat:"), 0, 3);
        grid.add(txtHabitat, 1, 3);

        grid.add(new Label("Status de Conservação:"), 2, 1);
        grid.add(txtStatus, 3, 1);

        grid.add(new Label("Data de Cadastro:"), 2, 2);
        grid.add(txtCadastro, 3, 2);

        grid.add(new Label("Data de Última Atualização:"), 2, 3);
        grid.add(txtAtualizacao, 3, 3);

        HBox botoesForm = new HBox(8, btnCadastrar, btnPesquisar, btnLimpar);
        grid.add(botoesForm, 0, 4, 2, 1);

        TableColumn<EspecieMarinha, String> colId = new TableColumn<>("Id");
        colId.setCellValueFactory(item -> new ReadOnlyStringWrapper(String.valueOf(item.getValue().getId())));

        TableColumn<EspecieMarinha, String> colNomeComum = new TableColumn<>("Nome");
        colNomeComum.setCellValueFactory(item -> new ReadOnlyStringWrapper(item.getValue().getNomeComum()));

        TableColumn<EspecieMarinha, String> colNomeCientifico = new TableColumn<>("Nome Científico");
        colNomeCientifico.setCellValueFactory(item -> new ReadOnlyStringWrapper(item.getValue().getNomeCientifico()));

        TableColumn<EspecieMarinha, String> colHabitat = new TableColumn<>("Habitat");
        colHabitat.setCellValueFactory(item -> new ReadOnlyStringWrapper(item.getValue().getHabitat()));

        TableColumn<EspecieMarinha, String> colStatus = new TableColumn<>("Status");
        colStatus.setCellValueFactory(item -> new ReadOnlyStringWrapper(item.getValue().getStatusConservacao()));

        TableColumn<EspecieMarinha, String> colCadastro = new TableColumn<>("Cadastrado");
        colCadastro.setCellValueFactory(item -> {
            var data = item.getValue().getDataCadastro();
            return new ReadOnlyStringWrapper(data != null ? data.format(formatador) : "");
        });

        TableColumn<EspecieMarinha, String> colAtualizacao = new TableColumn<>("Atualizado");

        colAtualizacao.setCellValueFactory(item -> {
            var data = item.getValue().getDataUltimaAtualizacao();
            return new ReadOnlyStringWrapper(data != null ? data.format(formatador) : "");
        });
        TableColumn<EspecieMarinha, Void> colAcoes = new TableColumn<>("Ações");
        colAcoes.setCellFactory(col -> new TableCell<>() {
            private final Button btnAtualizar = new Button("Atualizar");
            private final Button btnExcluir = new Button("Excluir");
            private final HBox box = new HBox(6, btnAtualizar, btnExcluir);

            {
                btnPesquisar.setOnAction(e -> {
                    boolean semFiltrosPrincipais = (txtNomeComum.getText() == null || txtNomeComum.getText().isBlank()) && (txtHabitat.getText() == null || txtHabitat.getText().isBlank()) && (txtStatus.getText() == null || txtStatus.getText().isBlank());

                    if (semFiltrosPrincipais && (txtId.getText() == null || txtId.getText().isBlank())) {
                        TextInputDialog dialog = new TextInputDialog();
                        dialog.setTitle("Pesquisar por ID");
                        dialog.setHeaderText(null);
                        dialog.setContentText("Informe o ID da espécie:");
                        dialog.showAndWait().ifPresent(idDigitado -> {
                            if (!idDigitado.isBlank()) {
                                control.idProperty().set(idDigitado.trim());
                            }
                        });
                    }

                    if (control.pesquisar()) {
                        new Alert(Alert.AlertType.INFORMATION, "Espécie(s) encontrada(s)!", ButtonType.OK).showAndWait();
                    } else {
                        new Alert(Alert.AlertType.ERROR,control.mensagemErroProperty().get() != null && !control.mensagemErroProperty().get().isBlank()? control.mensagemErroProperty().get() : "Espécie não encontrada.", ButtonType.OK).showAndWait();
                    }
                });

                btnExcluir.setOnAction(e -> {
                    EspecieMarinha selecionada = getTableView().getItems().get(getIndex());
                    if (selecionada != null) {
                        control.idProperty().set(String.valueOf(selecionada.getId()));
                        if (control.deletar()) {
                            new Alert(Alert.AlertType.INFORMATION, "Espécie deletada com sucesso!", ButtonType.OK).showAndWait();
                        } else {
                            new Alert(Alert.AlertType.ERROR, "Erro ao deletar a espécie.", ButtonType.OK).showAndWait();
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
                control.nomeComumProperty().set(nova.getNomeComum() != null ? nova.getNomeComum() : "");
                control.nomeCientificoProperty().set(nova.getNomeCientifico() != null ? nova.getNomeCientifico() : "");
                control.habitatProperty().set(nova.getHabitat() != null ? nova.getHabitat() : "");
                control.statusProperty().set(nova.getStatusConservacao() != null ? nova.getStatusConservacao() : "");
                control.dataCStringProperty().set(nova.getDataCadastro() != null ? nova.getDataCadastro().format(formatador) : "");
                control.dataAStringProperty().set(nova.getDataUltimaAtualizacao() != null ? nova.getDataUltimaAtualizacao().format(formatador) : "");
            }
        });

        colId.setMaxWidth(60);
        colId.setPrefWidth(55);
        colNomeComum.setPrefWidth(130);
        colNomeComum.setMaxWidth(220);
        colAcoes.setMinWidth(150);

        listagem.getColumns().addAll(colId, colNomeComum, colNomeCientifico, colHabitat, colStatus, colCadastro, colAtualizacao, colAcoes);
        listagem.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_FLEX_LAST_COLUMN);
        listagem.setItems(control.getListagem());

        p.setTop(grid);
        p.setCenter(listagem);
    }

    public BorderPane getRoot() {
        return root;
    }
}