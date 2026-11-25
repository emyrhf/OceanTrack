package boundary;

import control.PesquisadorController;
import entity.Pesquisador;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.time.format.DateTimeFormatter;

public class PesquisadorView {

    private final PesquisadorController control = new PesquisadorController();
    private final DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private final BorderPane root = new BorderPane();
    private final TextField txtNome = new TextField();
    private final TextField txtCpf = new TextField();
    private final TextField txtTelefone = new TextField();
    private final TextField txtEmail = new TextField();
    private final TextField txtInstituicao = new TextField();
    private final ComboBox<String> cmbTipo = new ComboBox<>();
    private final TableView<Pesquisador> listagem = new TableView<>();

    public PesquisadorView() {
        cmbTipo.getItems().addAll("PESQUISADOR", "VOLUNTARIO");
        cmbTipo.setValue("PESQUISADOR");

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(8);
        grid.setPadding(new Insets(15));
        grid.addRow(0, new Label("Nome: *"), txtNome, new Label("CPF:"), txtCpf);
        grid.addRow(1, new Label("Telefone:"), txtTelefone, new Label("E-mail:"), txtEmail);
        grid.addRow(2, new Label("Instituição:"), txtInstituicao, new Label("Tipo:"), cmbTipo);

        Button btnSalvar = new Button("Salvar");
        Button btnAtualizar = new Button("Atualizar");
        Button btnRemover = new Button("Remover");
        Button btnLimpar = new Button("Limpar");
        HBox botoes = new HBox(10, btnSalvar, btnAtualizar, btnRemover, btnLimpar);
        botoes.setPadding(new Insets(0, 15, 10, 15));

        VBox topo = new VBox(grid, botoes);

        configurarTabela();

        btnSalvar.setOnAction(e -> executar(() -> {
            control.salvar(txtNome.getText(), txtCpf.getText(), txtTelefone.getText(),
                    txtEmail.getText(), txtInstituicao.getText(), cmbTipo.getValue());
            limpar();
        }));

        btnAtualizar.setOnAction(e -> executar(() -> {
            control.atualizar(listagem.getSelectionModel().getSelectedItem(),
                    txtNome.getText(), txtCpf.getText(), txtTelefone.getText(),
                    txtEmail.getText(), txtInstituicao.getText(), cmbTipo.getValue());
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

        root.setTop(topo);
        root.setCenter(listagem);
    }

    public BorderPane getRoot() {
        return root;
    }

    private void configurarTabela() {
        TableColumn<Pesquisador, String> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(c -> new SimpleStringProperty(String.valueOf(c.getValue().getId())));
        colId.setMaxWidth(60);

        TableColumn<Pesquisador, String> colNome = new TableColumn<>("Nome");
        colNome.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getNome()));

        TableColumn<Pesquisador, String> colCpf = new TableColumn<>("CPF");
        colCpf.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getCpf()));

        TableColumn<Pesquisador, String> colTipo = new TableColumn<>("Tipo");
        colTipo.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getTipo()));

        TableColumn<Pesquisador, String> colInst = new TableColumn<>("Instituição");
        colInst.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getInstituicao()));

        TableColumn<Pesquisador, String> colData = new TableColumn<>("Ingresso");
        colData.setCellValueFactory(c -> new SimpleStringProperty(
                c.getValue().getDataIngresso() != null
                        ? c.getValue().getDataIngresso().format(formatador) : ""));

        listagem.getColumns().add(colId);
        listagem.getColumns().add(colNome);
        listagem.getColumns().add(colCpf);
        listagem.getColumns().add(colTipo);
        listagem.getColumns().add(colInst);
        listagem.getColumns().add(colData);
        listagem.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_FLEX_LAST_COLUMN);
        listagem.setItems(control.getListagem());
    }

    private void preencher(Pesquisador p) {
        txtNome.setText(p.getNome());
        txtCpf.setText(p.getCpf());
        txtTelefone.setText(p.getTelefone());
        txtEmail.setText(p.getEmail());
        txtInstituicao.setText(p.getInstituicao());
        cmbTipo.setValue(p.getTipo());
    }

    private void limpar() {
        txtNome.clear();
        txtCpf.clear();
        txtTelefone.clear();
        txtEmail.clear();
        txtInstituicao.clear();
        cmbTipo.setValue("PESQUISADOR");
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

