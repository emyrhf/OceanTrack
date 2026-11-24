package boundary;

import java.math.RoundingMode;
import java.time.format.DateTimeFormatter;

import control.OcorrenciaPoluicaoController;
import entity.OcorrenciaPoluicao;
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

public class OcorrenciaPoluicaoView {
    private OcorrenciaPoluicaoController control = new OcorrenciaPoluicaoController();
    private TableView<OcorrenciaPoluicao> listagem = new TableView<>();
    private DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private BorderPane root;

    public OcorrenciaPoluicaoView() {
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

        TextField txtLocal = new TextField();
        txtLocal.setPromptText("Ex.: Baía de Guanabara");
        TextField txtData = new TextField();
        txtData.setPromptText("dd/MM/aaaa");
        TextField txtVolume = new TextField();
        txtVolume.setPromptText("Ex.: 12,5");
        TextField txtDescricao = new TextField();
        txtDescricao.setPromptText("Descreva a ocorrência");

        ComboBox<String> cmbTipo = new ComboBox<>();
        cmbTipo.getItems().addAll("Óleo", "Plástico", "Esgoto", "Microplástico");
        cmbTipo.setPromptText("Selecione...");
        ComboBox<String> cmbGravidade = new ComboBox<>();
        cmbGravidade.getItems().addAll("Baixa", "Média", "Alta", "Crítica");
        cmbGravidade.setPromptText("Selecione...");
        ComboBox<String> cmbStatus = new ComboBox<>();
        cmbStatus.getItems().addAll("Aberta", "Resolvida");
        cmbStatus.setPromptText("Selecione...");

        Button btnCadastrar = new Button("Cadastrar");
        Button btnPesquisar = new Button("Pesquisar");
        Button btnLimpar = new Button("Limpar");

        Bindings.bindBidirectional(txtId.textProperty(), control.idProperty());
        Bindings.bindBidirectional(cmbTipo.valueProperty(), control.tipoProperty());
        Bindings.bindBidirectional(txtLocal.textProperty(), control.localProperty());
        Bindings.bindBidirectional(txtData.textProperty(), control.dataStringProperty());
        Bindings.bindBidirectional(cmbGravidade.valueProperty(), control.gravidadeProperty());
        Bindings.bindBidirectional(txtVolume.textProperty(), control.volumeEstimadoStringProperty());
        Bindings.bindBidirectional(txtDescricao.textProperty(), control.descricaoProperty());
        Bindings.bindBidirectional(cmbStatus.valueProperty(), control.statusProperty());

        btnCadastrar.setOnAction(e -> {
            if (control.cadastrar()) {
                new Alert(Alert.AlertType.INFORMATION, "Ocorrência de poluição registrada com sucesso!", ButtonType.OK).showAndWait();
            } else {
                mostrarErro("Não foi possível cadastrar a ocorrência.");
            }
        });

        btnPesquisar.setOnAction(e -> {
            boolean semFiltros = (cmbTipo.getValue() == null || cmbTipo.getValue().isBlank())
                    && (txtLocal.getText() == null || txtLocal.getText().isBlank())
                    && (cmbGravidade.getValue() == null || cmbGravidade.getValue().isBlank())
                    && (cmbStatus.getValue() == null || cmbStatus.getValue().isBlank())
                    && (txtData.getText() == null || txtData.getText().isBlank()
                            || txtData.getText().equals(java.time.LocalDate.now().format(formatador)));

            if (semFiltros && (txtId.getText() == null || txtId.getText().isBlank())) {
                TextInputDialog dialog = new TextInputDialog();
                dialog.setTitle("Pesquisar por ID");
                dialog.setHeaderText(null);
                dialog.setContentText("Informe o ID da Ocorrência de Poluição:");
                dialog.showAndWait().ifPresent(idDigitado -> {
                    if (!idDigitado.isBlank()) control.idProperty().set(idDigitado.trim());
                });
            }

            if (control.pesquisar()) {
                new Alert(Alert.AlertType.INFORMATION, "Ocorrência(s) encontrada(s)!", ButtonType.OK).showAndWait();
            } else {
                mostrarErro("Ocorrência não encontrada.");
            }
        });

        btnLimpar.setOnAction(e -> {
            control.limparCampos();
            cmbTipo.getSelectionModel().clearSelection();
            cmbGravidade.getSelectionModel().clearSelection();
            cmbStatus.getSelectionModel().clearSelection();
            control.carregarListagem();
        });

        grid.add(new Label("ID:"), 0, 0);
        grid.add(txtId, 1, 0);
        grid.add(new Label("Tipo *:"), 2, 0);
        grid.add(cmbTipo, 3, 0);
        grid.add(new Label("Local *:"), 0, 1);
        grid.add(txtLocal, 1, 1);
        grid.add(new Label("Data *:"), 2, 1);
        grid.add(txtData, 3, 1);
        grid.add(new Label("Gravidade *:"), 0, 2);
        grid.add(cmbGravidade, 1, 2);
        grid.add(new Label("Volume estimado *:"), 2, 2);
        grid.add(txtVolume, 3, 2);
        grid.add(new Label("Descrição *:"), 0, 3);
        grid.add(txtDescricao, 1, 3, 3, 1);
        grid.add(new Label("Status *:"), 0, 4);
        grid.add(cmbStatus, 1, 4);
        HBox botoesForm = new HBox(8, btnCadastrar, btnPesquisar, btnLimpar);
        grid.add(botoesForm, 2, 4, 2, 1);

        TableColumn<OcorrenciaPoluicao, String> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(item -> new ReadOnlyStringWrapper(String.valueOf(item.getValue().getId())));
        TableColumn<OcorrenciaPoluicao, String> colTipo = new TableColumn<>("Tipo");
        colTipo.setCellValueFactory(item -> new ReadOnlyStringWrapper(item.getValue().getTipo()));
        TableColumn<OcorrenciaPoluicao, String> colLocal = new TableColumn<>("Local");
        colLocal.setCellValueFactory(item -> new ReadOnlyStringWrapper(item.getValue().getLocal()));
        TableColumn<OcorrenciaPoluicao, String> colData = new TableColumn<>("Data");
        colData.setCellValueFactory(item -> new ReadOnlyStringWrapper(item.getValue().getData() != null
                ? item.getValue().getData().format(formatador) : ""));
        TableColumn<OcorrenciaPoluicao, String> colGravidade = new TableColumn<>("Gravidade");
        colGravidade.setCellValueFactory(item -> new ReadOnlyStringWrapper(item.getValue().getGravidade()));
        TableColumn<OcorrenciaPoluicao, String> colVolume = new TableColumn<>("Volume estimado");
        colVolume.setCellValueFactory(item -> new ReadOnlyStringWrapper(item.getValue().getVolumeEstimado() != null
                ? item.getValue().getVolumeEstimado().setScale(2, RoundingMode.HALF_UP).toPlainString() : ""));
        TableColumn<OcorrenciaPoluicao, String> colDescricao = new TableColumn<>("Descrição");
        colDescricao.setCellValueFactory(item -> new ReadOnlyStringWrapper(item.getValue().getDescricao()));
        TableColumn<OcorrenciaPoluicao, String> colStatus = new TableColumn<>("Status");
        colStatus.setCellValueFactory(item -> new ReadOnlyStringWrapper(item.getValue().getStatus()));

        TableColumn<OcorrenciaPoluicao, Void> colAcoes = new TableColumn<>("Ações");
        colAcoes.setCellFactory(col -> new TableCell<>() {
            private final Button btnAtualizar = new Button("Atualizar");
            private final Button btnExcluir = new Button("Excluir");
            private final HBox box = new HBox(6, btnAtualizar, btnExcluir);
            {
                btnAtualizar.setOnAction(e -> {
                    OcorrenciaPoluicao selecionada = getTableView().getItems().get(getIndex());
                    if (selecionada != null) {
                        control.idProperty().set(String.valueOf(selecionada.getId()));
                        if (control.atualizar()) {
                            new Alert(Alert.AlertType.INFORMATION, "Ocorrência atualizada com sucesso!", ButtonType.OK).showAndWait();
                        } else {
                            mostrarErro("Erro ao atualizar a ocorrência.");
                        }
                    }
                });
                btnExcluir.setOnAction(e -> {
                    OcorrenciaPoluicao selecionada = getTableView().getItems().get(getIndex());
                    if (selecionada != null) {
                        control.idProperty().set(String.valueOf(selecionada.getId()));
                        if (control.deletar()) {
                            new Alert(Alert.AlertType.INFORMATION, "Ocorrência excluída com sucesso!", ButtonType.OK).showAndWait();
                        } else {
                            mostrarErro("Erro ao excluir a ocorrência.");
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
                control.tipoProperty().set(nova.getTipo());
                control.localProperty().set(nova.getLocal());
                control.dataStringProperty().set(nova.getData() != null ? nova.getData().format(formatador) : "");
                control.gravidadeProperty().set(nova.getGravidade());
                control.volumeEstimadoStringProperty().set(nova.getVolumeEstimado() != null ? nova.getVolumeEstimado().toPlainString() : "");
                control.descricaoProperty().set(nova.getDescricao());
                control.statusProperty().set(nova.getStatus());
            }
        });

        colId.setPrefWidth(45);
        colTipo.setPrefWidth(95);
        colLocal.setPrefWidth(130);
        colData.setPrefWidth(85);
        colGravidade.setPrefWidth(85);
        colVolume.setPrefWidth(100);
        colDescricao.setPrefWidth(190);
        colStatus.setPrefWidth(80);
        colAcoes.setMinWidth(150);

        listagem.getColumns().addAll(colId, colTipo, colLocal, colData, colGravidade, colVolume, colDescricao, colStatus, colAcoes);
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
