package boundary;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.function.Function;

import control.ExpedicaoController;
import entity.Expedicao;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class ExpedicaoView {

    private final ExpedicaoController control = new ExpedicaoController();
    private final DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private final BorderPane root = new BorderPane();
    private final TextField txtTitulo = new TextField();
    private final DatePicker dpInicio = new DatePicker();
    private final DatePicker dpFim = new DatePicker();
    private final TextField txtLocalizacao = new TextField();
    private final TextField txtEmbarcacao = new TextField();
    private final TextField txtOrcamento = new TextField();
    private final TextField txtObjetivo = new TextField();
    private final ComboBox<String> cmbStatus = new ComboBox<>();
    private final TableView<Expedicao> listagem = new TableView<>();

    public ExpedicaoView() {
        cmbStatus.getItems().addAll("PLANEJADA", "EM_ANDAMENTO", "CONCLUIDA", "CANCELADA");
        cmbStatus.setValue("PLANEJADA");
        txtOrcamento.setPromptText("Ex.: 15000,50");

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(8);
        grid.setPadding(new Insets(15));
        grid.addRow(0, new Label("Título:"), txtTitulo, new Label("Local:"), txtLocalizacao);
        grid.addRow(1, new Label("Início:"), dpInicio, new Label("Fim:"), dpFim);
        grid.addRow(2, new Label("Embarcação:"), txtEmbarcacao, new Label("Orçamento (R$):"), txtOrcamento);
        grid.addRow(3, new Label("Objetivo:"), txtObjetivo, new Label("Status:"), cmbStatus);

        Button btnSalvar = new Button("Salvar");
        Button btnAtualizar = new Button("Atualizar");
        Button btnRemover = new Button("Remover");
        Button btnLimpar = new Button("Limpar");
        HBox botoes = new HBox(10, btnSalvar, btnAtualizar, btnRemover, btnLimpar);
        botoes.setPadding(new Insets(0, 15, 10, 15));

        configurarTabela();

        btnSalvar.setOnAction(e -> executar(() -> {
            control.salvar(txtTitulo.getText(), dpInicio.getValue(), dpFim.getValue(),
                    txtLocalizacao.getText(), txtEmbarcacao.getText(), txtOrcamento.getText(),
                    txtObjetivo.getText(), cmbStatus.getValue());
            limpar();
        }));

        btnAtualizar.setOnAction(e -> executar(() -> {
            control.atualizar(listagem.getSelectionModel().getSelectedItem(),
                    txtTitulo.getText(), dpInicio.getValue(), dpFim.getValue(),
                    txtLocalizacao.getText(), txtEmbarcacao.getText(), txtOrcamento.getText(),
                    txtObjetivo.getText(), cmbStatus.getValue());
            limpar();
        }));

        btnRemover.setOnAction(e -> executar(() -> {
            control.remover(listagem.getSelectionModel().getSelectedItem());
            limpar();
        }));

        btnLimpar.setOnAction(e -> limpar());

        listagem.getSelectionModel().selectedItemProperty().addListener((obs, antigo, sel) -> {
            if (sel != null) {
                preencher(sel);
            }
        });

        root.setTop(new VBox(grid, botoes));
        root.setCenter(listagem);
    }

    public BorderPane getRoot() {
        return root;
    }

    private void configurarTabela() {
        listagem.getColumns().add(coluna("ID", e -> String.valueOf(e.getId())));
        listagem.getColumns().add(coluna("Título", Expedicao::getTitulo));
        listagem.getColumns().add(coluna("Início", e -> data(e.getDataInicio())));
        listagem.getColumns().add(coluna("Fim", e -> data(e.getDataFim())));
        listagem.getColumns().add(coluna("Local", Expedicao::getLocalizacao));
        listagem.getColumns().add(coluna("Embarcação", Expedicao::getEmbarcacao));
        listagem.getColumns().add(coluna("Orçamento", e -> String.format("R$ %.2f", e.getOrcamento())));
        listagem.getColumns().add(coluna("Status", Expedicao::getStatus));
        listagem.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_FLEX_LAST_COLUMN);
        listagem.setItems(control.getListagem());
    }

    // Cria uma coluna de texto a partir de uma função que extrai o valor da expedição
    private TableColumn<Expedicao, String> coluna(String titulo, Function<Expedicao, String> valor) {
        TableColumn<Expedicao, String> col = new TableColumn<>(titulo);
        col.setCellValueFactory(c -> new SimpleStringProperty(valor.apply(c.getValue())));
        return col;
    }

    private String data(LocalDate d) {
        return d != null ? d.format(formatador) : "";
    }

    private void preencher(Expedicao e) {
        txtTitulo.setText(e.getTitulo());
        dpInicio.setValue(e.getDataInicio());
        dpFim.setValue(e.getDataFim());
        txtLocalizacao.setText(e.getLocalizacao());
        txtEmbarcacao.setText(e.getEmbarcacao());
        txtOrcamento.setText(e.getOrcamento().toPlainString());
        txtObjetivo.setText(e.getObjetivo());
        cmbStatus.setValue(e.getStatus());
    }

    private void limpar() {
        txtTitulo.clear();
        dpInicio.setValue(null);
        dpFim.setValue(null);
        txtLocalizacao.clear();
        txtEmbarcacao.clear();
        txtOrcamento.clear();
        txtObjetivo.clear();
        cmbStatus.setValue("PLANEJADA");
        listagem.getSelectionModel().clearSelection();
    }

    private void executar(Runnable acao) {
        try {
            acao.run();
        } catch (RuntimeException ex) {
            new Alert(Alert.AlertType.ERROR, ex.getMessage()).showAndWait();
        }
    }
}