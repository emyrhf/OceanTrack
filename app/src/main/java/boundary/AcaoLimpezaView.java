package boundary;

import java.math.RoundingMode;
import java.time.format.DateTimeFormatter;

import control.AcaoLimpezaController;
import entity.AcaoLimpeza;
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

public class AcaoLimpezaView {
    private AcaoLimpezaController control = new AcaoLimpezaController();
    private TableView<AcaoLimpeza> listagem = new TableView<>();
    private DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private BorderPane root;

    public AcaoLimpezaView() {
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
        TextField txtData = new TextField();
        txtData.setPromptText("dd/MM/aaaa");
        TextField txtLocal = new TextField();
        TextField txtParticipantes = new TextField();
        txtParticipantes.setPromptText("Ex.: 15");
        TextField txtQuantidade = new TextField();
        txtQuantidade.setPromptText("Ex.: 32,5");
        TextField txtOrganizador = new TextField();
        TextField txtResiduo = new TextField();
        txtResiduo.setPromptText("Ex.: Plástico");

        Button btnCadastrar = new Button("Cadastrar");
        Button btnPesquisar = new Button("Pesquisar");
        Button btnLimpar = new Button("Limpar");

        Bindings.bindBidirectional(txtId.textProperty(), control.idProperty());
        Bindings.bindBidirectional(txtNome.textProperty(), control.nomeProperty());
        Bindings.bindBidirectional(txtData.textProperty(), control.dataStringProperty());
        Bindings.bindBidirectional(txtLocal.textProperty(), control.localProperty());
        Bindings.bindBidirectional(txtParticipantes.textProperty(), control.numeroParticipantesStringProperty());
        Bindings.bindBidirectional(txtQuantidade.textProperty(), control.quantidadeLixoKgStringProperty());
        Bindings.bindBidirectional(txtOrganizador.textProperty(), control.organizadorProperty());
        Bindings.bindBidirectional(txtResiduo.textProperty(), control.tipoResiduoPredominanteProperty());

        btnCadastrar.setOnAction(e -> {
            if (control.cadastrar()) {
                new Alert(Alert.AlertType.INFORMATION, "Ação de limpeza registrada com sucesso!", ButtonType.OK).showAndWait();
            } else {
                mostrarErro("Não foi possível cadastrar a ação de limpeza.");
            }
        });

        btnPesquisar.setOnAction(e -> {
            boolean semFiltros = (txtNome.getText() == null || txtNome.getText().isBlank())
                    && (txtLocal.getText() == null || txtLocal.getText().isBlank())
                    && (txtOrganizador.getText() == null || txtOrganizador.getText().isBlank())
                    && (txtResiduo.getText() == null || txtResiduo.getText().isBlank())
                    && (txtData.getText() == null || txtData.getText().isBlank()
                            || txtData.getText().equals(java.time.LocalDate.now().format(formatador)));

            if (semFiltros && (txtId.getText() == null || txtId.getText().isBlank())) {
                TextInputDialog dialog = new TextInputDialog();
                dialog.setTitle("Pesquisar por ID");
                dialog.setHeaderText(null);
                dialog.setContentText("Informe o ID da Ação de Limpeza:");
                dialog.showAndWait().ifPresent(idDigitado -> {
                    if (!idDigitado.isBlank()) control.idProperty().set(idDigitado.trim());
                });
            }

            if (control.pesquisar()) {
                new Alert(Alert.AlertType.INFORMATION, "Ação(ões) de limpeza encontrada(s)!", ButtonType.OK).showAndWait();
            } else {
                mostrarErro("Ação de limpeza não encontrada.");
            }
        });

        btnLimpar.setOnAction(e -> {
            control.limparCampos();
            control.carregarListagem();
        });

        grid.add(new Label("ID:"), 0, 0);
        grid.add(txtId, 1, 0);
        grid.add(new Label("Nome *:"), 2, 0);
        grid.add(txtNome, 3, 0);
        grid.add(new Label("Data *:"), 0, 1);
        grid.add(txtData, 1, 1);
        grid.add(new Label("Local *:"), 2, 1);
        grid.add(txtLocal, 3, 1);
        grid.add(new Label("Nº de participantes *:"), 0, 2);
        grid.add(txtParticipantes, 1, 2);
        grid.add(new Label("Lixo coletado (kg) *:"), 2, 2);
        grid.add(txtQuantidade, 3, 2);
        grid.add(new Label("Organizador *:"), 0, 3);
        grid.add(txtOrganizador, 1, 3);
        grid.add(new Label("Resíduo predominante *:"), 2, 3);
        grid.add(txtResiduo, 3, 3);
        HBox botoesForm = new HBox(8, btnCadastrar, btnPesquisar, btnLimpar);
        grid.add(botoesForm, 0, 4, 4, 1);

        TableColumn<AcaoLimpeza, String> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(item -> new ReadOnlyStringWrapper(String.valueOf(item.getValue().getId())));
        TableColumn<AcaoLimpeza, String> colNome = new TableColumn<>("Nome");
        colNome.setCellValueFactory(item -> new ReadOnlyStringWrapper(item.getValue().getNome()));
        TableColumn<AcaoLimpeza, String> colData = new TableColumn<>("Data");
        colData.setCellValueFactory(item -> new ReadOnlyStringWrapper(item.getValue().getData() != null
                ? item.getValue().getData().format(formatador) : ""));
        TableColumn<AcaoLimpeza, String> colLocal = new TableColumn<>("Local");
        colLocal.setCellValueFactory(item -> new ReadOnlyStringWrapper(item.getValue().getLocal()));
        TableColumn<AcaoLimpeza, String> colParticipantes = new TableColumn<>("Participantes");
        colParticipantes.setCellValueFactory(item -> new ReadOnlyStringWrapper(String.valueOf(item.getValue().getNumeroParticipantes())));
        TableColumn<AcaoLimpeza, String> colQuantidade = new TableColumn<>("Coletado (kg)");
        colQuantidade.setCellValueFactory(item -> new ReadOnlyStringWrapper(item.getValue().getQuantidadeLixoKg() != null
                ? item.getValue().getQuantidadeLixoKg().setScale(2, RoundingMode.HALF_UP).toPlainString() : ""));
        TableColumn<AcaoLimpeza, String> colOrganizador = new TableColumn<>("Organizador");
        colOrganizador.setCellValueFactory(item -> new ReadOnlyStringWrapper(item.getValue().getOrganizador()));
        TableColumn<AcaoLimpeza, String> colResiduo = new TableColumn<>("Resíduo predominante");
        colResiduo.setCellValueFactory(item -> new ReadOnlyStringWrapper(item.getValue().getTipoResiduoPredominante()));

        TableColumn<AcaoLimpeza, Void> colAcoes = new TableColumn<>("Ações");
        colAcoes.setCellFactory(col -> new TableCell<>() {
            private final Button btnAtualizar = new Button("Atualizar");
            private final Button btnExcluir = new Button("Excluir");
            private final HBox box = new HBox(6, btnAtualizar, btnExcluir);
            {
                btnAtualizar.setOnAction(e -> {
                    AcaoLimpeza selecionada = getTableView().getItems().get(getIndex());
                    if (selecionada != null) {
                        control.idProperty().set(String.valueOf(selecionada.getId()));
                        if (control.atualizar()) {
                            new Alert(Alert.AlertType.INFORMATION, "Ação de limpeza atualizada com sucesso!", ButtonType.OK).showAndWait();
                        } else {
                            mostrarErro("Erro ao atualizar a ação de limpeza.");
                        }
                    }
                });
                btnExcluir.setOnAction(e -> {
                    AcaoLimpeza selecionada = getTableView().getItems().get(getIndex());
                    if (selecionada != null) {
                        control.idProperty().set(String.valueOf(selecionada.getId()));
                        if (control.deletar()) {
                            new Alert(Alert.AlertType.INFORMATION, "Ação de limpeza excluída com sucesso!", ButtonType.OK).showAndWait();
                        } else {
                            mostrarErro("Erro ao excluir a ação de limpeza.");
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
                control.nomeProperty().set(nova.getNome());
                control.dataStringProperty().set(nova.getData() != null ? nova.getData().format(formatador) : "");
                control.localProperty().set(nova.getLocal());
                control.numeroParticipantesStringProperty().set(String.valueOf(nova.getNumeroParticipantes()));
                control.quantidadeLixoKgStringProperty().set(nova.getQuantidadeLixoKg() != null ? nova.getQuantidadeLixoKg().toPlainString() : "");
                control.organizadorProperty().set(nova.getOrganizador());
                control.tipoResiduoPredominanteProperty().set(nova.getTipoResiduoPredominante());
            }
        });

        colId.setPrefWidth(45);
        colNome.setPrefWidth(130);
        colData.setPrefWidth(85);
        colLocal.setPrefWidth(120);
        colParticipantes.setPrefWidth(95);
        colQuantidade.setPrefWidth(90);
        colOrganizador.setPrefWidth(130);
        colResiduo.setPrefWidth(135);
        colAcoes.setMinWidth(150);

        listagem.getColumns().addAll(colId, colNome, colData, colLocal, colParticipantes,colQuantidade, colOrganizador, colResiduo, colAcoes);
        listagem.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_FLEX_LAST_COLUMN);
        listagem.setItems(control.getListagem());

        p.setTop(grid);
        p.setCenter(listagem);
    }

    private void mostrarErro(String mensagemPadrao) {
        String msg = control.mensagemErroProperty().get();
        new Alert(Alert.AlertType.ERROR, msg != null && !msg.isBlank() ? msg : mensagemPadrao, ButtonType.OK).showAndWait();
    }

    public BorderPane getRoot() {
        return root;
    }
}
