package pt.ipleiria.estg.dei.ei.esoft.views.paineis;

import pt.ipleiria.estg.dei.ei.esoft.DadosApp;
import pt.ipleiria.estg.dei.ei.esoft.classes.Aluguer;
import pt.ipleiria.estg.dei.ei.esoft.classes.Bilhete;
import pt.ipleiria.estg.dei.ei.esoft.classes.Venda;
import pt.ipleiria.estg.dei.ei.esoft.classes.utils.IListener;
import pt.ipleiria.estg.dei.ei.esoft.views.listas.ListaFilmes;
import pt.ipleiria.estg.dei.ei.esoft.classes.Filme;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class Estatistica {
    private String nome;
    private String unidades;
    private String valor;

    public Estatistica(String nome, String unidades, String valor) {
        this.nome = nome;
        this.unidades = unidades;
        this.valor = valor;
    }

    public String getNome() {
        return nome;
    }

    public String getUnidades() {
        return unidades;
    }

    public String getValor() {
        return valor;
    }
}

public class PainelEstatisticas extends JPanel implements IListener {
    private JComboBox<String> cbDia;
    private JComboBox<String> cbMes;
    private JComboBox<String> cbAno;
    private JButton btnFiltrar;
    private JTable tabelaEsq;
    private JTable tabelaDir;
    private DefaultTableModel tabelaModelEsq;
    private DefaultTableModel tabelaModelDir;
    private JButton btnTop5Filmes;

    private Estatistica[] estatisticas;

    public PainelEstatisticas() {
        setLayout(new BorderLayout(10, 10));
        setBackground(Color.WHITE);

        // Painel de filtros centralizado
        JPanel painelFiltrosWrapper = new JPanel(new FlowLayout(FlowLayout.CENTER));
        painelFiltrosWrapper.setBackground(Color.WHITE);

        JPanel painelFiltros = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        painelFiltros.setBackground(Color.WHITE);

        cbDia = new JComboBox<>();
        cbMes = new JComboBox<>();
        cbAno = new JComboBox<>();
        btnFiltrar = new JButton("Filtrar");

        painelFiltros.add(new JLabel("Dia:"));
        cbDia.addItem("Choose a day");
        cbDia.setSelectedIndex(0);
        for (int i = 1; i <= 31; i++) cbDia.addItem(String.valueOf(i));
        painelFiltros.add(cbDia);

        painelFiltros.add(new JLabel("Mês:"));
        cbMes.addItem("Choose a month");
        cbMes.setSelectedIndex(0);
        for (int i = 1; i <= 12; i++) cbMes.addItem(String.valueOf(i));
        painelFiltros.add(cbMes);

        painelFiltros.add(new JLabel("Ano:"));
        int anoAtual = LocalDate.now().getYear();
        cbAno.addItem("Choose a year");
        cbAno.setSelectedIndex(0);
        for (int i = anoAtual - 5; i <= anoAtual; i++) cbAno.addItem(String.valueOf(i));
        painelFiltros.add(cbAno);

        painelFiltros.add(btnFiltrar);

        painelFiltrosWrapper.add(painelFiltros);

        add(painelFiltrosWrapper, BorderLayout.NORTH);

        // Modelos das tabelas
        String[] colunasEsq = {"Estatística", "Unidade", "Valor em €"};
        String[] colunasDir = {"Estatística", "Resultado"};

        tabelaModelEsq = new DefaultTableModel(colunasEsq, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tabelaModelDir = new DefaultTableModel(colunasDir, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                // Só o botão é "editável" (para permitir clicar)
                return row == 4 && column == 1;
            }
        };

        tabelaEsq = new JTable(tabelaModelEsq);
        tabelaEsq.setRowHeight(70);
        tabelaEsq.setEnabled(false);
        tabelaEsq.getTableHeader().setReorderingAllowed(false);

        tabelaDir = new JTable(tabelaModelDir)/* {
            @Override
            public Component prepareRenderer(javax.swing.table.TableCellRenderer renderer, int row, int column) {
                Component c = super.prepareRenderer(renderer, row, column);
                if (row == 4 && column == 1) {
                    if (btnTop5Filmes == null) {
                        btnTop5Filmes = new JButton("Ver Top 5 Filmes");
                        btnTop5Filmes.addActionListener(e -> mostrarTop5Filmes());
                    }

                    return btnTop5Filmes;
                }
                return super.prepareRenderer(renderer, row, column);
            }
        }*/;
        Runnable onClick = this::mostrarTop5Filmes;
        tabelaDir.getColumn("Resultado").setCellRenderer(new ButtonInLastRowRenderer(4, 1));
        tabelaDir.getColumn("Resultado").setCellEditor(new ButtonInLastRowEditor(new JCheckBox(), 4, 1, onClick));
        tabelaDir.setRowHeight(70);
        tabelaDir.getTableHeader().setReorderingAllowed(false);


        // Painel para as duas tabelas lado a lado
        JPanel painelTabelas = new JPanel(new GridLayout(1, 2, 40, 0));
        painelTabelas.setBackground(Color.WHITE);
        painelTabelas.setBorder(BorderFactory.createEmptyBorder(20, 60, 48, 60));
        painelTabelas.add(new JScrollPane(tabelaEsq));
        painelTabelas.add(new JScrollPane(tabelaDir));

        add(painelTabelas, BorderLayout.CENTER);

        btnFiltrar.addActionListener(e -> atualizarEstatisticas());
        atualizarEstatisticas();
    }

    private Stream<Venda> getVendasPorData() {
        int dia = cbDia.getSelectedItem().equals("Choose a day") ? 0 : Integer.parseInt(cbDia.getSelectedItem().toString());
        int mes = cbMes.getSelectedItem().equals("Choose a month") ? 0 : Integer.parseInt(cbMes.getSelectedItem().toString());
        int ano = cbAno.getSelectedItem().equals("Choose a year") ? 0 : Integer.parseInt(cbAno.getSelectedItem().toString());
        return DadosApp.getInstance().getVendas().stream()
                .filter(v -> (dia == 0 || v.getData().getDayOfMonth() == dia) && (mes == 0 || v.getData().getMonthValue() == mes) && (ano == 0 || v.getData().getYear() == ano));
    }

    private Estatistica getBilhetesVendidos() {
        var bilhetes = getVendasPorData()
                .filter(p -> Objects.equals(p.getProduto().getCategoria(), "Bilhete")).toList();
        return new Estatistica("Bilhetes vendidos", bilhetes.size() + " unid.",
                bilhetes.stream().mapToDouble(p -> p.getProduto().getPreco()).sum() + " €");
    }

    private Estatistica getRefrigerantesVendidos() {
        var refrigerantes = getVendasPorData()
                .filter(p -> Objects.equals(p.getProduto().getCategoria(), "Bebida")).filter(p -> !p.getProduto().getNome().toLowerCase().contains("água")).toList();
        return new Estatistica("Refrigerantes vendidos", refrigerantes.size() + " unid.",
                refrigerantes.stream().mapToDouble(p -> p.getProduto().getPreco()).sum() + " €");
    }

    private Estatistica getAguaVendida() {
        var aguas = getVendasPorData()
                .filter(p -> Objects.equals(p.getProduto().getCategoria(), "Bebida")).filter(p -> p.getProduto().getNome().toLowerCase().contains("água")).toList();
        return new Estatistica("Águas vendidas", aguas.size() + " unid.",
                aguas.stream().mapToDouble(p -> p.getProduto().getPreco()).sum() + " €");
    }

    private Estatistica getPipocasVendidas() {
        var pipocas = getVendasPorData()
                .filter(p -> Objects.equals(p.getProduto().getCategoria(), "Comida")).filter(p -> p.getProduto().getNome().toLowerCase().contains("pipocas")).toList();
        return new Estatistica("Pipocas vendidas", pipocas.size() + " unid.",
                pipocas.stream().mapToDouble(p -> p.getProduto().getPreco()).sum() + " €");
    }

    private Estatistica getMenusVendidos() {
        var menus = getVendasPorData()
                .filter(p -> p.getProduto().getNome().toLowerCase().contains("menu")).toList();
        return new Estatistica("Menus vendidos", menus.size() + " unid.",
                menus.stream().mapToDouble(p -> p.getProduto().getPreco()).sum() + " €");
    }

    private Estatistica getLucro() {
        var vendas = getVendasPorData();
        return new Estatistica("Lucro", "", vendas.mapToDouble(p -> p.getProduto().getPreco()).sum() + " €");
    }
    private Estatistica getCategoriaFavorita() {
        var bilhetes = getVendasPorData()
                .filter(p -> Objects.equals(p.getProduto().getCategoria(), "Bilhete")).map(v -> (Bilhete) v.getProduto());
        String categoria = bilhetes.map(b -> b.getSessao().getFilme().getCategoria()).collect(Collectors.groupingBy(Function.identity(), Collectors.counting())) // Map<String, Long>
                .entrySet().stream()
                .max(Map.Entry.comparingByValue()) // find max by frequency
                .map(Map.Entry::getKey) // get the element, not the count
                .orElse("Sem dados"); // in case the list is empty;
        return new Estatistica("Categoria de filme favorita", "N/A", categoria); // Placeholder
    }
    private Estatistica getHorarioFavorito() {
        var bilhetes = getVendasPorData()
                .filter(p -> Objects.equals(p.getProduto().getCategoria(), "Bilhete"))
                .map(v -> (Bilhete) v.getProduto());

        // Convert to 3-hour time block label
        var blocos = bilhetes
                    .map(b -> b.getSessao().getHorario().getBlocoHorario())
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        // Get most common block
        String horarioMaisPopular = blocos.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse("Sem dados");

        return new Estatistica("Horário favorito", "N/A", horarioMaisPopular);
    }
    private Estatistica getInvestimentoAluguer() {
        int dia = cbDia.getSelectedItem().equals("Choose a day") ? 0 : Integer.parseInt(cbDia.getSelectedItem().toString());
        int mes = cbMes.getSelectedItem().equals("Choose a month") ? 0 : Integer.parseInt(cbMes.getSelectedItem().toString());
        int ano = cbAno.getSelectedItem().equals("Choose a year") ? 0 : Integer.parseInt(cbAno.getSelectedItem().toString());
        Stream<Aluguer> alugueres = DadosApp.getInstance().getAlugueres().stream()
                .filter(v -> (dia == 0 || v.getData().getDayOfMonth() == dia) && (mes == 0 || v.getData().getMonthValue() == mes) && (ano == 0 || v.getData().getYear() == ano));

        double investimento = alugueres.mapToDouble(Aluguer::getPreco).sum();

        return new Estatistica("Investimento em aluguer", "N/A", String.format("%.2f €", investimento));
    }


    private void atualizarEstatisticas() {

        estatisticas = new Estatistica[]{

                getBilhetesVendidos(),
                getRefrigerantesVendidos(),
                getPipocasVendidas(),
                getAguaVendida(),
                getMenusVendidos(),
                getLucro(),
                getCategoriaFavorita(),
                getHorarioFavorito(),
                getInvestimentoAluguer()
        };

        tabelaModelEsq.setRowCount(0);
        tabelaModelDir.setRowCount(0);

        // Preencher as tabelas
        for (int i = 0; i < 5; i++) {
            tabelaModelEsq.addRow(new Object[]{estatisticas[i].getNome(), estatisticas[i].getUnidades(), estatisticas[i].getValor()});
        }
        for (int i = 5; i < 9; i++) {
            tabelaModelDir.addRow(new Object[]{estatisticas[i].getNome(), estatisticas[i].getValor()});
        }
        // Última linha da direita: botão
        tabelaModelDir.addRow(new Object[]{"Top 5 Filmes", ""});
    }

    private void mostrarTop5Filmes() {
        var bilhetes = getVendasPorData()
                .filter(p -> Objects.equals(p.getProduto().getCategoria(), "Bilhete"))
                .map(v -> (Bilhete) v.getProduto());

        // Agrupa por título e conta ocorrências
        var filmesFrequencia = bilhetes
                .map(b -> b.getSessao().getFilme().getTitulo())
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        // Ordena por frequência (descendente) e pega os top 5
        var top5 = filmesFrequencia.entrySet().stream()
                .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                .limit(5)
                .toList(); // Java 16+. Se estiveres numa versão mais antiga: usar .collect(Collectors.toList())

        StringBuilder sb = new StringBuilder("Top 5 Filmes:\n");

        if (top5.isEmpty()) {
            sb.append("Sem dados.");
        } else {
            int i = 1;
            for (var entry : top5) {
                sb.append(i++)
                        .append(". ")
                        .append(entry.getKey()) // título do filme
                        .append(" — ")
                        .append(entry.getValue()) // número de bilhetes
                        .append(" bilhete(s)\n");
            }
        }

        JOptionPane.showMessageDialog(this, sb.toString(), "Top 5 Filmes", JOptionPane.INFORMATION_MESSAGE);
    }

    @Override
    public void update() {
        atualizarEstatisticas();
    }


    // Renderer that shows a button only on the last row, third column
    static class ButtonInLastRowRenderer extends JButton implements TableCellRenderer {
        private final int targetRow;
        private final int targetCol;

        public ButtonInLastRowRenderer(int row, int col) {
            this.targetRow = row;
            this.targetCol = col;
            setOpaque(true);
        }

        public Component getTableCellRendererComponent(JTable table, Object value,
                                                       boolean isSelected, boolean hasFocus,
                                                       int row, int column) {
            if (row == targetRow && column == targetCol) {
                setText("Mostrar Top 5 Filmes");
                return this;
            } else {
                // Default cell renderer for other cells
                return new DefaultTableCellRenderer().getTableCellRendererComponent(
                        table, value, isSelected, hasFocus, row, column);
            }
        }
    }

    // Editor that activates the button
    static class ButtonInLastRowEditor extends DefaultCellEditor {
        private JButton button;
        private boolean clicked;
        private final int targetRow, targetCol;
        private final Runnable onClick;

        public ButtonInLastRowEditor(JCheckBox checkBox, int row, int col, Runnable onClick) {
            super(checkBox);
            this.targetRow = row;
            this.targetCol = col;
            this.onClick = onClick;

            button = new JButton("Mostrar Top 5 Filmes");
            button.setOpaque(true);
            button.addActionListener(e -> fireEditingStopped());
        }

        public Component getTableCellEditorComponent(JTable table, Object value,
                                                     boolean isSelected, int row, int column) {
            if (row == targetRow && column == targetCol) {
                clicked = true;
                return button;
            }
            return new JLabel(value != null ? value.toString() : "");
        }

        public Object getCellEditorValue() {
            if (clicked && onClick != null) {
                onClick.run();
            }
            clicked = false;
            return "Mostrar Top 5 Filmes";
        }

        public boolean stopCellEditing() {
            clicked = false;
            return super.stopCellEditing();
        }

        protected void fireEditingStopped() {
            super.fireEditingStopped();
            if (clicked && onClick != null) {
                onClick.run();
            }
        }
    }
}
