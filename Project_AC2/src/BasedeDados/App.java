package BasedeDados;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.Date;
import java.util.List;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import javax.swing.JOptionPane;

//import java.io.File;
import javax.swing.JOptionPane;

public class App {
    static ArrayList<Endereco> enderecos = new ArrayList<>();
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) throws IOException {

        // criar caminho para um arquivo txt novo sem ser no meu computador
        ArrayList<Cliente> clientes = new ArrayList<>();
        ArrayList<Item> itens = new ArrayList<>();
        ArrayList<Endereco> enderecos = new ArrayList<>();
        ArrayList<efetuarcompra> compras = new ArrayList<>();
        PessoaFis pf = new PessoaFis();
        PessoaJuri pj = new PessoaJuri();

        // Leitura do documento baseDados/clientes.txt
        try (BufferedReader br = new BufferedReader(new FileReader("clientes.txt"))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                String[] campos = linha.split(",");
                if (campos[0].equals("Pessoa Física")) {
                    pf = new PessoaFis();
                    Endereco endereco = new Endereco();
                    pf.setNome(campos[1]);
                    pf.setDataCadastro(campos[2]);
                    pf.setCPF(campos[3]);
                    pf.setQtdParcelas(Integer.parseInt(campos[4]));
                    endereco.setNumero(Integer.parseInt(campos[5]));
                    endereco.setRua(campos[6]);
                    endereco.setBairro(campos[7]);
                    endereco.setCEP(campos[8]);
                    endereco.setCidade(campos[9]);
                    endereco.setEstado(campos[10]);
                    clientes.add(pf);
                } else {
                    // Trate o caso para "Pessoa Jurídica" aqui
                    pj = new PessoaJuri();
                    Endereco endereco = new Endereco();
                    pj.setCNPJ(campos[1]);
                    pj.setRazaoSocial(campos[2]);
                    pj.setPrazoMaximo(Integer.parseInt(campos[3]));
                    endereco.setNumero(Integer.parseInt(campos[4]));
                    endereco.setRua(campos[5]);
                    endereco.setBairro(campos[6]);
                    endereco.setCEP(campos[7]);
                    endereco.setCidade(campos[8]);
                    endereco.setEstado(campos[9]);
                    clientes.add(pj);
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Leitura do documento baseDados/produtos.txt
        try (BufferedReader br = new BufferedReader(new FileReader("Itens.txt"))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                String[] campos = linha.split(",");
                Item item = new Item();
                item.setCodigo(Integer.parseInt(campos[1]));
                item.setNomeProduto(campos[2]);
                item.setDescricao(campos[3]);
                item.setValorUnitario(Double.parseDouble(campos[4]));
                itens.add(item);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Item item = new Item();

        File compraFile = new File("Compras.txt");
        if (!compraFile.exists())
            compraFile.createNewFile();
        FileWriter compraWriter = new FileWriter(compraFile, false);
        BufferedWriter compraBufferedWriter = new BufferedWriter(compraWriter);

        System.out.println("Selecione a operação desejada: ");
        System.out.println("1 - Cadastrar cliente");
        System.out.println("2 - Deletar cliente pelo CPF ou CNPJ");
        System.out.println("3 - Deletar cliente pelo nome");
        System.out.println("4 - Cadastro de Produtos");
        System.out.println("5 - Efetuação de uma compra");
        System.out.println("6 - Atualização da situação de pagamento de uma compra");
        System.out.println("7 - Relatório de clientes");
        System.out.println("8 - Sair do sistema");
        String input;
        do {
            Object[] options = { "Cadastrar cliente", "Deletar cliente pelo CPF ou CNPJ", "Deletar cliente pelo nome",
                    "Cadastro de Produtos", "Efetuação de uma compra",
                    "Atualização da situação de pagamento de uma compra",
                    "Relatório de clientes", "Sair do sistema" };
            input = (String) JOptionPane.showInputDialog(null, "Selecione a operação desejada:", "Menu",
                    JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

            // i = sc.nextInt();
            switch (input) {
                case "Cadastrar cliente":
                    cadastrarCliente();
                    break;
                case "Deletar cliente pelo CPF ou CNPJ":
                    excluirClientePorCPFCNPJ(clientes);
                    break;

                case "Deletar cliente pelo nome":
                    System.out.println("Digite o nome do cliente que deseja deletar: ");
                    String nome = sc.next();

                    for (int i = 0; i < clientes.size(); i++) {
                        Cliente cliente = clientes.get(i);
                        if (cliente.getNome().equalsIgnoreCase(nome)) {
                            clientes.remove(i);
                            System.out.println("Cliente deletado com sucesso.");
                            break;
                        }
                    }
                    break;
                case "Cadastro de Produtos":
                    cadastrarItem();
                    break;
                case "Efetuação de uma compra":
                    break;
                case "Atualização da situação de pagamento de uma compra":
                    System.out.println("Atualizar a situação de pagamento de uma compra:");
                    break;

                    case "Relatório de clientes":
                    String[] relatorioOptions = { "(a) Relação de todos os Clientes que possuem o nome iniciado por uma determinada sequência de caracteres",
                            "(b) Relação de todos os Produtos", "(c) Busca de um produto pelo nome",
                            "(d) Relação de produtos que são perecíveis e que estão com a data de validade vencida",
                            "(e) Relação de todas as compras", "(f) Busca de uma compra pelo número",
                            "(g) Relação de todas as compras que não foram pagas ainda",
                            "(h) Relação das 10 últimas compras pagas",
                            "(i) Apresentação das informações da compra mais cara",
                            "(j) Apresentação das informações da compra mais barata",
                            "(k) Relação do valor total de compras feitas em cada mês nos últimos 12 meses", "Voltar" };
                
                    String relatorioEscolha = (String) JOptionPane.showInputDialog(null, "Escolha uma opção:", "7 - Relatórios",
                            JOptionPane.PLAIN_MESSAGE, null, relatorioOptions, relatorioOptions[0]);
                
                    if (relatorioEscolha == null || relatorioEscolha.equals("Voltar")) {
                        JOptionPane.showMessageDialog(null, "Voltando...");
                        break;
                    }
                
                    switch (relatorioEscolha) {
                        case "(a) Relação de todos os Clientes que possuem o nome iniciado por uma determinada sequência de caracteres":
                            Scanner scanner = new Scanner(System.in);
                            System.out.println("Digite a sequência de caracteres para buscar clientes:");
                            String sequencia = scanner.next();
                            scanner.close();
                            buscarClientesPorNome(clientes);
                            break;
                        case "(b) Relação de todos os Produtos":
                            listarProdutos(itens);
                            break;
                        case "(c) Busca de um produto pelo nome":
                            buscarProdutoPorNome(itens);
                            break;
                        case "(d) Relação de produtos que são perecíveis e que estão com a data de validade vencida":
                            produtosVencidos(itens);
                            break;
                        case "(e) Relação de todas as compras":
                            listarCompras(compras);
                            break;
                        case "(f) Busca de uma compra pelo número":
                            buscarCompraPorNumero(compras);
                            break;
                        case "(g) Relação de todas as compras que não foram pagas ainda":
                            //   comprasNaoPagas(compras);
                            break;
                        case "(h) Relação das 10 últimas compras pagas":
                            ultimasDezComprasPagas(compras);
                            break;
                        case "(i) Apresentação das informações da compra mais cara":
                            compraMaisCara(compras);
                            break;
                        case "(j) Apresentação das informações da compra mais barata":
                            compraMaisBarata(compras);
                            break;
                        case "(k) Relação do valor total de compras feitas em cada mês nos últimos 12 meses":
                            // valorTotalComprasPorMes(compras);
                            break;
                    }
                    break;

            }

        } while (!input.equals("Sair do sistema"));
        sc.close();
        compraBufferedWriter.close();
    }

    public static void cadastrarCliente() throws IOException {
        File clienteFile = new File("Clientes.txt");
        if (!clienteFile.exists())
            clienteFile.createNewFile();

        try (BufferedWriter clienteBufferedWriter = new BufferedWriter(new FileWriter(clienteFile, true))) {
            System.out.println("Bem vindo ao cadastro de clientes!");
            System.out.println("CPF ou CNPJ do Cliente: ");
            System.out.println("1 - CPF \n2 - CNPJ:");

            switch (sc.nextInt()) {
                case 1:
                    PessoaFis pf = new PessoaFis();
                    System.out.println("Nome do Cliente:\n");
                    pf.setNome(sc.next());
                    System.out.println("\nData de Cadastro do Cliente:\n");
                    pf.setDataCadastro(sc.next());
                    System.out.println("\nCPF do Cliente:");
                    pf.setCPF(sc.next());
                    System.out.println("\nQuantidade máxima de parcelas da compra:\n ");
                    pf.setQtdParcelas(sc.nextInt());
                    pf.setEndereco(enderecoCliente());
                    System.out.println(pf.ParaString());
                    clienteBufferedWriter.write(pf.ParaString());
                    clienteBufferedWriter.newLine(); // Adiciona uma nova linha
                    break;
                case 2:
                    PessoaJuri pj = new PessoaJuri();
                    System.out.println("\nCNPJ do Cliente:\n");
                    pj.setCNPJ(sc.next());
                    System.out.println("\nRazão Social do Cliente:\n");
                    pj.setRazaoSocial(sc.next());
                    System.out.println("Prazo máximo para pagamento da compra em dias: \n");
                    pj.setPrazoMaximo(sc.nextInt());
                    pj.setEndereco(enderecoCliente());
                    System.out.println(pj.ParaString());
                    clienteBufferedWriter.write(pj.ParaString());
                    clienteBufferedWriter.newLine(); // Adiciona uma nova linha
                    break;
            }
        }
    }
    
    public static void excluirClientePorCPFCNPJ(ArrayList<Cliente> listClientes) {
        Scanner scanner = new Scanner(System.in);
    
        System.out.println("Escolha o tipo de cliente:");
        System.out.println("1 - Pessoa Física");
        System.out.println("2 - Pessoa Jurídica");
    
        int tipoEscolhido = scanner.nextInt();
    
        if (tipoEscolhido == 1) {
            System.out.println("CPF:");
            String cpf = scanner.next(); // Agora, estamos recebendo o CPF como uma String
    
            boolean encontrou = false;
            for (Cliente cliente : listClientes) {
                if (cliente instanceof PessoaFis && ((PessoaFis) cliente).getCPF().equals(cpf)) {
                    listClientes.remove(cliente);
                    encontrou = true;
                    System.out.println("Cliente removido com sucesso.");
                    break;
                }
            }
    
            if (!encontrou)
                System.out.println("Cliente não encontrado!");
    
        } else if (tipoEscolhido == 2) {
            System.out.println("CNPJ:");
            String cnpj = scanner.next(); // Agora, estamos recebendo o CNPJ como uma String
    
            boolean encontrou = false;
            for (Cliente cliente : listClientes) {
                if (cliente instanceof PessoaJuri && ((PessoaJuri) cliente).getCNPJ().equals(cnpj)) {
                    listClientes.remove(cliente);
                    encontrou = true;
                    System.out.println("Cliente removido com sucesso.");
                    break;
                }
            }
    
            if (!encontrou)
                System.out.println("Cliente não encontrado!");
        } else {
            System.out.println("Opção inválida");
        }
        scanner.close();
    }

    public static long getLong(String prompt, String title) {
        long result = 0;
        boolean validInput = false;

        while (!validInput) {
            try {
                String input = JOptionPane.showInputDialog(null, prompt, title, JOptionPane.PLAIN_MESSAGE);
                if (input == null) {
                    // User canceled input
                    System.exit(0);
                }
                result = Long.parseLong(input);
                validInput = true;
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Por favor, insira um número válido.", "Erro de Entrada",
                        JOptionPane.ERROR_MESSAGE);
            }
        }

        return result;
    }

    public static Endereco enderecoCliente() {
        Endereco endereco = new Endereco();
        System.out.println("Número do Cliente: ");
        endereco.setNumero(sc.nextInt());
        enderecos.add(endereco);
        System.out.println("Rua do Cliente: ");
        endereco.setRua(sc.next());
        enderecos.add(endereco);
        sc.nextLine(); // limpar o buffer do teclado
        System.out.println("Bairro do Cliente: ");
        endereco.setBairro(sc.nextLine());
        enderecos.add(endereco);
        System.out.println("CEP do Cliente: ");
        endereco.setCEP(sc.next());
        enderecos.add(endereco);
        System.out.println("Cidade do Cliente: ");
        endereco.setCidade(sc.next());
        enderecos.add(endereco);
        System.out.println("Estado do Cliente: ");
        endereco.setEstado(sc.next());
        enderecos.add(endereco);
        return endereco;
    }

    public static void cadastrarItem() throws IOException {
        File produtoFile = new File("Itens.txt");
        if (!produtoFile.exists())
            produtoFile.createNewFile();
        FileWriter produtoWriter = new FileWriter(produtoFile, true);
        BufferedWriter produtoBufferedWriter = new BufferedWriter(produtoWriter);
        System.out.println("Bem vindo ao cadastro de produtos!");
        System.out.println("Quantos produtos deseja cadastrar?");
        int quantidade = sc.nextInt();
        for (int i = 0; i < quantidade; i++) {
            Item itemLoop = new Item(); // Crie um novo objeto Item
            System.out.println("Nome do Produto: ");
            itemLoop.setNomeProduto(sc.next()); // Defina o nome do produto
            System.out.println("Valor Unitário do Produto: ");
            itemLoop.setValorUnitario(sc.nextDouble()); // Defina o valor unitário
            System.out.println("Codigo do Produto: ");
            itemLoop.setCodigo(sc.nextInt()); // Defina a quantidade
            System.out.println("Valor Total do Produto: ");
            itemLoop.setDescricao(sc.next()); // Defina o valor total
            System.out.println("Este produto é perecível? (S/N)");

            String perecivel = sc.next();
            if (perecivel.equalsIgnoreCase("S")) {
                System.out.println("Data de validade do produto: ");
                itemLoop.setDataValidade(sc.nextInt());
            } else if (perecivel.equalsIgnoreCase("N")) {
                System.out.println("Este produto não é perecível.");
                System.out.println(itemLoop.toString());
                produtoBufferedWriter.write(itemLoop.toString());
            }
        }
        produtoBufferedWriter.close();
    }

    public static void buscarClientesPorNome(ArrayList<Cliente> clientesList) {
        if (clientesList.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Não há clientes cadastrados.");
            return;
        }
        String sequencia = JOptionPane.showInputDialog("Digite a sequência de caracteres para buscar clientes:");
        if (sequencia == null) {
            JOptionPane.showMessageDialog(null, "Operação cancelada.");
            return;
        }
    
        ArrayList<Cliente> clientesEncontrados = new ArrayList<>();

        System.out.println(clientesEncontrados);
    
        for (Cliente cliente : clientesList) {
            if (cliente.getNome().toLowerCase().startsWith(sequencia.toLowerCase())) {
                clientesEncontrados.add(cliente);
            }
        }
    
        // Resultados da busca
        if (!clientesEncontrados.isEmpty()) {
            StringBuilder resultado = new StringBuilder("Clientes encontrados:\n");
            for (Cliente cliente : clientesEncontrados) {
                resultado.append(cliente.getNome()).append("\n");
            }
            JOptionPane.showMessageDialog(null, resultado.toString());
        } else {
            JOptionPane.showMessageDialog(null, "Nenhum cliente encontrado!");
        }
    }
    
    public static void listarProdutos(ArrayList<Item> itensList) {
        if (itensList.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Não há produtos cadastrados.");
            return;
        }
    
        StringBuilder resultado = new StringBuilder("Lista de produtos:\n");
        for (Item item : itensList) {
            resultado.append(item.toString()).append("\n");
        }
    
        JOptionPane.showMessageDialog(null, resultado.toString());
    }
    
    public static void buscarProdutoPorNome(ArrayList<Item> listItens) {
        if (listItens.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Não há itens cadastrados.");
            return;
        }

        String nomeBusca = JOptionPane.showInputDialog(null, "Digite o nome do item:", "Buscar Item",
                JOptionPane.PLAIN_MESSAGE);
            
        if (nomeBusca == null) {
            JOptionPane.showMessageDialog(null, "Operação cancelada.");
            return;
        }

        boolean itemEncontrado = false;
        StringBuilder resultado = new StringBuilder("Resultado da busca:\n");

        for (Item item : listItens) {
            if (item.getNomeProduto().toLowerCase().startsWith(nomeBusca.toLowerCase())) {
                resultado.append(item.toString()).append("\n");
                itemEncontrado = true;
            }
        }

        if (!itemEncontrado) {
            JOptionPane.showMessageDialog(null, "Nenhum item encontrado com o nome iniciado por: " + nomeBusca);
        } else {
            JOptionPane.showMessageDialog(null, resultado.toString());
        }
    }

    public static void produtosVencidos(ArrayList<Item> listItens) {
        if (listItens.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Não há itens cadastrados.");
            return;
        }

        StringBuilder resultado = new StringBuilder("Itens vencidos:\n");
        LocalDate dataAtual = LocalDate.now();

        for (Item item : listItens) {
            // Verifica se o item é perecível (tem data de validade)
            if (item.getDataValidade() > 0 && LocalDate.ofEpochDay(item.getDataValidade()).isBefore(dataAtual)) {
                resultado.append(item.toString())
                        .append(" - Vencimento: ").append(item.getDataValidade()).append("\n");
            }
        }

        if (resultado.toString().equals("Itens vencidos:\n")) {
            JOptionPane.showMessageDialog(null, "Não há itens vencidos.");
        } else {
            JOptionPane.showMessageDialog(null, resultado.toString());
        }
    }

    public static void listarCompras(ArrayList<efetuarcompra> compras) {
        if (compras.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Não há compras cadastradas.");
            return;
        }
        StringBuilder resultado = new StringBuilder("Lista de compras:\n");
        for (efetuarcompra compra : compras) {
            resultado.append(compra.toString()).append("\n");
        }

        JOptionPane.showMessageDialog(null, resultado.toString());
    }

    public static void buscarCompraPorNumero(ArrayList<efetuarcompra> compras) {
        if (compras.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Não há compras cadastradas.");
            return;
        }
    
        int numeroCompraBusca = Integer.parseInt(JOptionPane.showInputDialog(
                null, "Digite o número da compra:", "Buscar compra pelo número",
                JOptionPane.PLAIN_MESSAGE));
    
        boolean encontrouCompra = false;
        StringBuilder resultado = new StringBuilder("Resultado da busca:\n");
    
        for (efetuarcompra compra : compras) {
            // Modificação para usar o getNumeroCompra em vez de getIdentificador
            if (compra.getNumeroCompra() == numeroCompraBusca) {
                resultado.append(compra.toString()).append("\n");
                encontrouCompra = true;
                break; // Removido para continuar a busca por outras compras com o mesmo número
            }
        }
    
        if (!encontrouCompra) {
            JOptionPane.showMessageDialog(null, "Nenhuma compra com o número: " + numeroCompraBusca + " foi encontrada.");
        } else {
            JOptionPane.showMessageDialog(null, resultado.toString());
        }
    }
    
    public static void ultimasDezComprasPagas(List<efetuarcompra> compras) {
        List<efetuarcompra> comprasPagas = compras.stream()
                .filter(compra -> compra.getValorTotal() > 0)
                .collect(Collectors.toList());

        if (comprasPagas.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Não há compras pagas para exibir.");
            return;
        }

        List<efetuarcompra> ultimasDezComprasPagas = comprasPagas.stream()
                .sorted(Comparator.comparing(efetuarcompra::getDataAtual).reversed())
                .limit(10)
                .collect(Collectors.toList());

        StringBuilder mensagem = new StringBuilder("Últimas 10 compras pagas:\n");
        for (efetuarcompra compra : ultimasDezComprasPagas) {
            mensagem.append(compra.toString()).append("\n");
        }

        JOptionPane.showMessageDialog(null, mensagem.toString());
    }

    public static void compraMaisCara(ArrayList<efetuarcompra> compras) {
        if (compras.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Não há compras cadastradas.");
            return;
        }
    
        efetuarcompra compraMaisCara = compras.get(0);
    
        for (efetuarcompra compra : compras) {
            if (compra.getValorTotal() > compraMaisCara.getValorTotal()) {
                compraMaisCara = compra;
            }
        }
    
        JOptionPane.showMessageDialog(null, "Informações da compra mais cara:\n" + compraMaisCara.toString());
    }

    public static void compraMaisBarata(ArrayList<efetuarcompra> compras) {
        if (compras.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Não há compras cadastradas.");
            return;
        }
    
        efetuarcompra compraMaisBarata = compras.get(0);
    
        for (efetuarcompra compra : compras) {
            if (compra.getValorTotal() < compraMaisBarata.getValorTotal()) {
                compraMaisBarata = compra;
            }
        }
    
        JOptionPane.showMessageDialog(null, "Informações da compra mais barata:\n" + compraMaisBarata.toString());
    }
    

}

