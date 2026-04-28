package dojo;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

public class DojoMarcial extends JFrame {
	private CardLayout cardLayout = new CardLayout();
	private JPanel painelPrincipal = new JPanel(cardLayout);
	private final String caminhoDiscipulo = "src\\pergaminho_discipulo.csv";
	private final String caminhoAnciao = "src\\pergaminho_anciao.csv";
	private final String caminhoServo = "src\\pergaminho_servo.csv";

	// Paletas
	Color corFundo = new Color(252, 248, 232);
	Color corPapelAntigo = new Color(230, 210, 180);
	Color corOuroVelho = new Color(184, 134, 11);
	Color corJade = new Color(0, 168, 107);
	Color corSangue = new Color(120, 0, 0);
	Color corTextoAntigo = new Color(44, 26, 11);

	public DojoMarcial() {
		setTitle("✨ Dojo Marcial dos Mestres Marciais");
		setMinimumSize(new Dimension(600, 600));
		setSize(800, 700);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);

		criarTelaBoasVindas();
		criarTelaCandidatura();
		criarTelaCandidaturaFuncionario();
		criarTelaAdmin();

		add(painelPrincipal);
		setVisible(true);
	}

	// Estilo místico para botões
	private void estilizarBotao(JButton btn, Color corPrincipal) {
		btn.setBackground(corFundo);
		btn.setForeground(corPrincipal);
		btn.setFont(new Font("Serif", Font.BOLD, 16));
		btn.setFocusPainted(false);
		btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
		// Borda que parece moldura de quadro antigo
		btn.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(corOuroVelho, 1),
				BorderFactory.createEmptyBorder(12, 25, 12, 25)));
	}

	// Tela de inicio
	private void criarTelaBoasVindas() {
		JPanel tela = new JPanel(new GridBagLayout());
		tela.setBackground(corFundo);

		// Borda de moldura dourada
		tela.setBorder(BorderFactory.createLineBorder(corOuroVelho, 5));

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.insets = new Insets(20, 20, 20, 20);
		gbc.fill = GridBagConstraints.HORIZONTAL;

		JLabel titulo = new JLabel("🏯 Dojo Marcial", SwingConstants.CENTER);
		titulo.setFont(new Font("Serif", Font.ITALIC + Font.BOLD, 42));
		titulo.setForeground(corOuroVelho);

		JLabel sub = new JLabel("۞ O Caminho para a Maestria começa aqui ۞", SwingConstants.CENTER);
		sub.setFont(new Font("Serif", Font.PLAIN, 18));
		sub.setForeground(corPapelAntigo);

		JButton btnCandidato = new JButton("QUERO ME CANDIDATAR");
		JButton btnFuncionario = new JButton("QUERO SER UM FUNCIONÁRIO");
		JButton btnAdmin = new JButton("CÂMARA DOS MESTRES");

		estilizarBotao(btnCandidato, corJade);
		estilizarBotao(btnFuncionario, corJade);
		estilizarBotao(btnAdmin, corOuroVelho);

		btnCandidato.addActionListener(e -> cardLayout.show(painelPrincipal, "candidatura"));
		btnFuncionario.addActionListener(e -> cardLayout.show(painelPrincipal, "funcionario"));
		btnAdmin.addActionListener(e -> {
			atualizarTabelaAdmin();
			cardLayout.show(painelPrincipal, "admin");
		});

		tela.add(titulo, gbc);
		tela.add(sub, gbc);
		tela.add(btnCandidato, gbc);
		tela.add(btnFuncionario, gbc);
		tela.add(btnAdmin, gbc);

		painelPrincipal.add(tela, "inicio");
	}

	// Tela pra criar objetos Discipulo e Anciao
	private void criarTelaCandidatura() {
		JPanel tela = new JPanel(new GridBagLayout());
		tela.setBackground(corPapelAntigo);
		tela.setBorder(BorderFactory.createMatteBorder(10, 10, 10, 10, corFundo));

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(10, 10, 10, 10);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 1.0;

		TitledBorder bordaTitulo = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(corSangue, 2),
				" INSCREVER NOME NO PERGAMINHO ", TitledBorder.CENTER, TitledBorder.TOP,
				new Font("Serif", Font.BOLD, 18), corSangue);
		tela.setBorder(BorderFactory.createCompoundBorder(tela.getBorder(), bordaTitulo));

		JTextField txtNome = new JTextField();
		txtNome.setFont(new Font("Serif", Font.ITALIC, 16));
		JTextField txtIdade = new JTextField();

		JTextField txtTecnica = new JTextField();
		String[] posicao = { "Discípulo", "Mestre" };
		JComboBox<String> comboPosi = new JComboBox<>(posicao);
		String[] niveis = {};
		JComboBox<String> comboNivel = new JComboBox<>(niveis);

		comboPosi.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (comboPosi.getSelectedItem().equals("Discípulo")) {
					String[] niveis = { "Nenhum", "Faixa Branca", "Faixa Cinza", "Faixa Amarela", "Faixa Laranja",
							"Faixa Verde", "Faixa Roxa", "Faixa Azul", "Faixa Vermelha" };
					comboNivel.setModel(new DefaultComboBoxModel<>(niveis));

				}
				if (comboPosi.getSelectedItem().equals("Mestre")) {
					String[] niveis = { "Faixa Preta(1 dan)", "Faixa Preta(2 dan)", "Faixa Preta(3 dan)",
							"Faixa Preta(4 dan)", "Faixa Preta(5 dan)" };
					comboNivel.setModel(new DefaultComboBoxModel<>(niveis));
				}

			}
		});
		;

		JButton btnEnviar = new JButton("ENVIAR CANDIDATURA");
		JButton btnVoltar = new JButton("RETORNAR");

		estilizarBotao(btnEnviar, corSangue);
		estilizarBotao(btnVoltar, Color.GRAY);

		gbc.gridy = 0;
		gbc.gridx = 0;
		tela.add(new JLabel("Nome do Candidato:"), gbc);
		gbc.gridx = 1;
		tela.add(txtNome, gbc);

		gbc.gridy = 1;
		gbc.gridx = 0;
		tela.add(new JLabel("Idade:"), gbc);
		gbc.gridx = 1;
		tela.add(txtIdade, gbc);

		gbc.gridy = 2;
		gbc.gridx = 0;
		tela.add(new JLabel("Posição Desejada:"), gbc);
		gbc.gridx = 1;
		tela.add(comboPosi, gbc);

		gbc.gridy = 3;
		gbc.gridx = 0;
		tela.add(new JLabel("Nível de Maestria:"), gbc);
		gbc.gridx = 1;
		tela.add(comboNivel, gbc);

		gbc.gridy = 4;
		gbc.gridx = 0;
		tela.add(new JLabel("Arte Principal:"), gbc);
		gbc.gridx = 1;
		tela.add(txtTecnica, gbc);

		gbc.gridy = 5;
		gbc.gridx = 0;
		gbc.gridwidth = 2;
		tela.add(btnEnviar, gbc);
		gbc.gridy = 6;
		tela.add(btnVoltar, gbc);

		btnEnviar.addActionListener(e -> {
			try {
				// Validação de Vazio
				if (txtNome.getText().trim().isEmpty() || txtIdade.getText().trim().isEmpty()) {
					throw new Exception("O pergaminho possui campos vazios!");
				}

				// Validação Numérica
				int idade;
				try {
					idade = Integer.parseInt(txtIdade.getText().trim());
				} catch (NumberFormatException ex) {
					throw new Exception("A idade deve ser um número válido!");
				}

				// Validação de Regra
				if (idade < 10) {
					throw new Exception("Idade fora dos limites permitidos.");
				}
				if (comboPosi.getSelectedItem().equals("Discípulo")) {
					String mestreAtribuido = sortearMestre();
					Discipulo d = new Discipulo(txtNome.getText(), idade, comboNivel.getSelectedItem().toString(),
							txtTecnica.getText(), mestreAtribuido);
					DiscipuloCSV.AdicionaDiscipulo(d);
					MestreCSV.vincularDiscipulo(txtNome.getText(), mestreAtribuido);
					JOptionPane.showMessageDialog(this, "Sucesso! Você é um " + d.getStatus());
					txtNome.setText("");
					txtIdade.setText("");
					txtTecnica.setText("");

					cardLayout.show(painelPrincipal, "inicio");

				} else if (comboPosi.getSelectedItem().equals("Mestre")) {
					Mestre a = new Mestre(txtNome.getText(), (int) idade, (String) comboNivel.getSelectedItem(),
							txtTecnica.getText(), "");
					MestreCSV.adicionaMestre(a);
					JOptionPane.showMessageDialog(this, "Sucesso! Você é um " + a.getStatus());
					txtNome.setText("");
					txtIdade.setText("");
					txtTecnica.setText("");
					cardLayout.show(painelPrincipal, "inicio");
				}

			} catch (Exception ex) {
				JOptionPane.showMessageDialog(this, "⚠️ Erro: " + ex.getMessage(), "Alerta da Seita",
						JOptionPane.WARNING_MESSAGE);
			}
		});

		btnVoltar.addActionListener(e -> cardLayout.show(painelPrincipal, "inicio"));
		painelPrincipal.add(tela, "candidatura");
	}

	// Tela para criar objeto Servo
	private void criarTelaCandidaturaFuncionario() {
		JPanel tela = new JPanel(new GridBagLayout());
		tela.setBackground(corPapelAntigo);
		tela.setBorder(BorderFactory.createMatteBorder(10, 10, 10, 10, corFundo));

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(10, 10, 10, 10);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 1.0;

		TitledBorder bordaTitulo = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(corSangue, 2),
				" INSCREVER NOME NO PERGAMINHO ", TitledBorder.CENTER, TitledBorder.TOP,
				new Font("Serif", Font.BOLD, 18), corSangue);
		tela.setBorder(BorderFactory.createCompoundBorder(tela.getBorder(), bordaTitulo));

		JTextField txtNome = new JTextField();
		txtNome.setFont(new Font("Serif", Font.ITALIC, 16));
		JTextField txtIdade = new JTextField();

		JTextField txtTecnica = new JTextField();

		String[] niveis = { "Mortal" };
		JComboBox<String> comboNivel = new JComboBox<>(niveis);

		JButton btnEnviar = new JButton("ENVIAR CANDIDATURA");
		JButton btnVoltar = new JButton("RETORNAR");

		estilizarBotao(btnEnviar, corSangue);
		estilizarBotao(btnVoltar, Color.GRAY);

		gbc.gridy = 0;
		gbc.gridx = 0;
		tela.add(new JLabel("Nome do Cultivador:"), gbc);
		gbc.gridx = 1;
		tela.add(txtNome, gbc);

		gbc.gridy = 1;
		gbc.gridx = 0;
		tela.add(new JLabel("Idade:"), gbc);
		gbc.gridx = 1;
		tela.add(txtIdade, gbc);

		gbc.gridy = 2;
		gbc.gridx = 0;
		tela.add(new JLabel("Nível de Cultivo:"), gbc);
		gbc.gridx = 1;
		tela.add(comboNivel, gbc);

		gbc.gridy = 4;
		gbc.gridx = 0;
		tela.add(new JLabel("Técnica Primordial:"), gbc);
		gbc.gridx = 1;
		tela.add(txtTecnica, gbc);

		gbc.gridy = 5;
		gbc.gridx = 0;
		gbc.gridwidth = 2;
		tela.add(btnEnviar, gbc);
		gbc.gridy = 6;
		tela.add(btnVoltar, gbc);

		btnEnviar.addActionListener(e -> {
			try {
				// Validação de Vazio
				if (txtNome.getText().trim().isEmpty() || txtIdade.getText().trim().isEmpty()) {
					throw new Exception("O pergaminho possui campos vazios!");
				}

				// Validação Numérica
				int idade;
				try {
					idade = Integer.parseInt(txtIdade.getText().trim());
				} catch (NumberFormatException ex) {
					throw new Exception("A idade deve ser um número válido!");
				}

				// Validação de Regra
				if (idade < 10) {
					throw new Exception("Idade fora dos limites permitidos.");
				}
				Funcionario s = new Funcionario(txtNome.getText(), (int) idade,
						(String) comboNivel.getSelectedItem().toString(), txtTecnica.getText(), "");
				FuncionarioCSV.adicionaFuncionario(s);
				JOptionPane.showMessageDialog(this, "Sucesso! Você é um " + s.getStatus());
				txtNome.setText("");
				txtIdade.setText("");
				txtTecnica.setText("");
				cardLayout.show(painelPrincipal, "inicio");

			} catch (Exception ex) {
				JOptionPane.showMessageDialog(this, "⚠️ Erro: " + ex.getMessage(), "Alerta da Seita",
						JOptionPane.WARNING_MESSAGE);
			}
		});

		btnVoltar.addActionListener(e -> cardLayout.show(painelPrincipal, "inicio"));
		painelPrincipal.add(tela, "funcionario");
	}

	// Tela pra listagem e pesquisa dos objetos
	private void criarTelaAdmin() {
		JPanel tela = new JPanel(new BorderLayout());
		tela.setBackground(corFundo);

		JPanel painelBusca = new JPanel(new FlowLayout(FlowLayout.LEFT));
		painelBusca.setBackground(corPapelAntigo);
		painelBusca.setPreferredSize(new Dimension(800, 100));

		painelBusca.setBorder(
				BorderFactory.createTitledBorder(BorderFactory.createLineBorder(corJade), "Busca Espiritual"));

		JTextField txtBusca = new JTextField(20);
		JTextField txtNivel = new JTextField(20);
		JTextField txtID = new JTextField(10);
		JButton btnBuscar = new JButton("🔍 Buscar");
		JButton btnBuscarID = new JButton("🔍 Buscar ID");
		JButton btnLimpar = new JButton("🔄 Ver Todos");
		String[] posicao = { "Discípulo", "Mestre", "Funcionário", "Todos" };
		JComboBox<String> comboPosi = new JComboBox<>(posicao);
		comboPosi.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (comboPosi.getSelectedItem().equals("Discípulo")) {
					atualizarTabelaD();
				} else if (comboPosi.getSelectedItem().equals("Mestre")) {
					atualizarTabelaM();
				} else if (comboPosi.getSelectedItem().equals("Funcionário")) {
					atualizarTabelaF();
				} else {
					atualizarTabelaAdmin();
				}

			}

		});
		comboPosi.setSelectedItem("Todos");

		estilizarBotao(btnBuscar, corOuroVelho);
		estilizarBotao(btnLimpar, corOuroVelho);

		painelBusca.add(new JLabel("Nome:"));
		painelBusca.add(txtBusca);
		painelBusca.add(new JLabel("Maestria:"));
		painelBusca.add(txtNivel);
		painelBusca.add(new JLabel("ID"));
		painelBusca.add(txtID);
		painelBusca.add(btnBuscar);
		painelBusca.add(btnBuscarID);
		painelBusca.add(btnLimpar);
		painelBusca.add(comboPosi);

		JTable tabela = new JTable(modelTabela);
		tabela.setModel(modelTabela);
		tabela.setRowHeight(30);
		JTableHeader header = tabela.getTableHeader();
		header.setBackground(corOuroVelho);
		header.setForeground(Color.WHITE);

		JButton btnVoltar = new JButton("FECHAR REGISTROS");
		estilizarBotao(btnVoltar, corOuroVelho);
		estilizarBotao(btnBuscarID, corOuroVelho);

		btnBuscar.addActionListener(e -> filtro(txtNivel.getText(), txtBusca.getText(), comboPosi));
		btnBuscarID.addActionListener(e -> buscaID(txtID.getText()));

		btnLimpar.addActionListener(e -> {
			txtNivel.setText("");
			txtBusca.setText("");
			txtID.setText("");
			atualizarTabelaAdmin();
			comboPosi.setSelectedItem("Todos");
		});

		btnVoltar.addActionListener(e -> cardLayout.show(painelPrincipal, "inicio"));

		tela.add(painelBusca, BorderLayout.NORTH);
		tela.add(new JScrollPane(tabela), BorderLayout.CENTER);
		tela.add(btnVoltar, BorderLayout.SOUTH);

		painelPrincipal.add(tela, "admin");
	}

	// Pesquisa em todas as tabelas por nome e nível de poder
	public void filtro(String nivel, String termo, JComboBox<String> posicao) {
		modelTabela.setRowCount(0);

		String selecao = (String) posicao.getSelectedItem();

		switch (selecao) {

		case "Discípulo":
			try (Scanner scanner = new Scanner(new FileReader(caminhoDiscipulo, StandardCharsets.ISO_8859_1))) {
				while (scanner.hasNextLine()) {
					String linha = scanner.nextLine();
					String[] dados = linha.split(";");
					if ((dados[1].toLowerCase().equalsIgnoreCase(termo.toLowerCase().trim()))
							|| dados[3].toLowerCase().contentEquals(nivel)) {

						modelTabela.addRow(dados);
					}
				}
				if (modelTabela.getRowCount() == 0) {
					JOptionPane.showMessageDialog(this, "Nenhum discípulo encontrado com este nome ou nível!");
					atualizarTabelaAdmin();
				}

			} catch (IOException ex) {
				JOptionPane.showMessageDialog(this, "O pergaminho ainda não foi criado!");
			}
			break;
		case "Mestre":
			try (Scanner scanner = new Scanner(new FileReader(caminhoAnciao, StandardCharsets.ISO_8859_1))) {
				while (scanner.hasNextLine()) {
					String linha = scanner.nextLine();
					String[] dados = linha.split(";");
					if ((dados[1].toLowerCase().equalsIgnoreCase(termo.toLowerCase().trim()))
							|| dados[3].toLowerCase().contentEquals(nivel)) {

						modelTabela.addRow(dados);
					}
				}
				if (modelTabela.getRowCount() == 0) {
					JOptionPane.showMessageDialog(this, "Nenhum mestre encontrado com este nome ou nível!");
					atualizarTabelaAdmin();
				}

			} catch (IOException ex) {
				JOptionPane.showMessageDialog(this, "O pergaminho ainda não foi criado!");
			}
			break;
		case "Funcionário":
			try (Scanner scanner = new Scanner(new FileReader(caminhoServo, StandardCharsets.ISO_8859_1))) {
				while (scanner.hasNextLine()) {
					String linha = scanner.nextLine();
					String[] dados = linha.split(";");

					if ((dados[1].toLowerCase().equalsIgnoreCase(termo.toLowerCase().trim()))
							|| dados[3].toLowerCase().contentEquals(nivel)) {

						modelTabela.addRow(dados);
					}
				}
				if (modelTabela.getRowCount() == 0) {
					JOptionPane.showMessageDialog(this, "Nenhum funcionário encontrado com este nome!");
					atualizarTabelaAdmin();
				}

			} catch (IOException ex) {
				JOptionPane.showMessageDialog(this, "O pergaminho ainda não foi criado!");
			}
			break;
		case "Todos":

			String[] caminhoCelestial = { caminhoDiscipulo, caminhoAnciao, caminhoServo };

			for (String caminhoAtual : caminhoCelestial) {
				try (Scanner scanner = new Scanner(new FileReader(caminhoAtual, StandardCharsets.ISO_8859_1))) {
					while (scanner.hasNextLine()) {
						String linha = scanner.nextLine();
						String[] dados = linha.split(";");

						boolean bateNome = termo.trim().isEmpty()
								|| dados[1].toLowerCase().contains(termo.toLowerCase().trim());

						boolean bateNivel = nivel.equals("Todos") || nivel.equals("")
								|| dados[3].equalsIgnoreCase(nivel);

						if (bateNome && bateNivel) {
							modelTabela.addRow(dados);
						}
					}
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(this, "O pergaminho ainda não foi criado!");
				}
			}

			if (modelTabela.getRowCount() == 0) {
				JOptionPane.showMessageDialog(this, "Ninguém encontrado com estes critérios!");
				atualizarTabelaAdmin();
			}
			break;

		default:
			JOptionPane.showMessageDialog(this, "Nenhum cultivador encontrado com estes critérios!");
			atualizarTabelaAdmin();

			break;
		}
	}

	private void buscaID(String id) {
		modelTabela.setRowCount(0);
		String[] caminhoCelestial = { caminhoDiscipulo, caminhoAnciao, caminhoServo };

		for (String caminhoAtual : caminhoCelestial) {
			try (Scanner scanner = new Scanner(new FileReader(caminhoAtual, StandardCharsets.ISO_8859_1))) {
				while (scanner.hasNextLine()) {
					String linha = scanner.nextLine();
					String[] dados = linha.split(";");

					boolean bateId = id.trim().isEmpty() || dados[0].toLowerCase().equals(id.toLowerCase().trim());

					if (bateId) {
						modelTabela.addRow(dados);
					}
				}
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(this, "O pergaminho ainda não foi criado!");
			}
		}
	}

	private DefaultTableModel modelTabela = new DefaultTableModel(
			new String[] { "Id", "Nome", "Idade", "Maestria", "Arte Marcial", "Posição", "Professor/Aluno" }, 0);

	// Atualiza a tabela mostrada ao usuário com as informações dos arquivos CSV
	private void atualizarTabelaAdmin() {
		modelTabela.setRowCount(0);

		try (Scanner scanner = new Scanner(new FileReader(caminhoDiscipulo, StandardCharsets.ISO_8859_1))) {
			while (scanner.hasNextLine()) {
				modelTabela.addRow(scanner.nextLine().split(";"));
			}
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(this, "Erro" + ex.getMessage());
		}
		try (Scanner scanner = new Scanner(new FileReader(caminhoAnciao, StandardCharsets.ISO_8859_1))) {
			while (scanner.hasNextLine()) {
				modelTabela.addRow(scanner.nextLine().split(";"));
			}
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(this, "Erro" + ex.getMessage());
		}
		try (Scanner scanner = new Scanner(new FileReader(caminhoServo, StandardCharsets.ISO_8859_1))) {
			while (scanner.hasNextLine()) {
				modelTabela.addRow(scanner.nextLine().split(";"));
			}
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(this, "Erro" + ex.getMessage());
		}
	}

	// Mostra somente os discipulos
	private void atualizarTabelaD() {
		modelTabela.setColumnIdentifiers(
				new String[] { "Id", "Nome", "Idade", "Maestria", "Arte Marcial", "Posição", "Professor" });
		modelTabela.setRowCount(0);
		try (Scanner scanner = new Scanner(new FileReader(caminhoDiscipulo, StandardCharsets.ISO_8859_1))) {
			while (scanner.hasNextLine()) {
				modelTabela.addRow(scanner.nextLine().split(";"));
			}
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(this, "Erro" + ex.getMessage());
		}
	}

	// Mostra somente os mestres
	private void atualizarTabelaM() {
		modelTabela.setColumnIdentifiers(
				new String[] { "Id", "Nome", "Idade", "Maestria", "Arte Marcial", "Posição", "Aluno" });
		modelTabela.setRowCount(0);
		try (Scanner scanner = new Scanner(new FileReader(caminhoAnciao, StandardCharsets.ISO_8859_1))) {
			while (scanner.hasNextLine()) {
				modelTabela.addRow(scanner.nextLine().split(";"));
			}
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(this, "Erro" + ex.getMessage());
		}

	}

	// Mostra somente os funcionários
	private void atualizarTabelaF() {
		modelTabela.setColumnIdentifiers(new String[] { "Id", "Nome", "Idade", "Maestria", "Arte Marcial", "Posição" });
		modelTabela.setRowCount(0);
		try (Scanner scanner = new Scanner(new FileReader(caminhoServo, StandardCharsets.ISO_8859_1))) {
			while (scanner.hasNextLine()) {
				modelTabela.addRow(scanner.nextLine().split(";"));
			}
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(this, "Erro" + ex.getMessage());
		}

	}

	private String sortearMestre() {
		ArrayList<String> mestresDisponiveis = new ArrayList<>();
		File arquivo = new File(caminhoAnciao);

		if (arquivo.exists()) {
			try (Scanner scanner = new Scanner(new FileReader(caminhoAnciao, StandardCharsets.ISO_8859_1))) {
				while (scanner.hasNextLine()) {
					String linha = scanner.nextLine();
					String[] dados = linha.split(";");
					mestresDisponiveis.add(dados[1]);
				}

			} catch (Exception e) {
				e.printStackTrace();
				// TODO: handle exception
			}
		}
		if (mestresDisponiveis.isEmpty()) {
			return "Mestre Fundador (Sistema)";
		}
		Random random = new Random();
		int sorteado = random.nextInt(mestresDisponiveis.size());
		return mestresDisponiveis.get(sorteado);
	};

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> new DojoMarcial());
	}
}