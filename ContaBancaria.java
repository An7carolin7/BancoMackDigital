import java.util.ArrayList;
import java.util.Scanner;
import java.util.Random;

class ContaBancaria {
    private String titular;
    private String agencia;
    private String numeroConta;
    private String senha;
    private double saldo;
    
    public ContaBancaria(String titular, String agencia, String numeroConta, String senha) {
        this.titular = titular;
        this.agencia = agencia;
        this.numeroConta = numeroConta;
        this.senha = senha;
        this.saldo = 0.0;
    }

    // Getters e Setters
    public String getTitular() {
        return titular;
    }

    public void setTitular(String titular) {
        this.titular = titular;
    }

    public String getAgencia() {
        return agencia;
    }

    public String getNumeroConta() {
        return numeroConta;
    }

    public boolean verificarSenha(String senha) {
        return this.senha.equals(senha);
    }

    public void alterarSenha(String novaSenha) {
        this.senha = novaSenha;
    }

    public double getSaldo() {
        return saldo;
    }

    public void depositar(double valor) {
        saldo += valor;
    }

    public boolean sacar(double valor) {
        if (valor <= saldo) {
            saldo -= valor;
            return true;
        }
        return false;
    }
}

public class BancoMack {
    private static ArrayList<ContaBancaria> contas = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);
    private static Random random = new Random();

    public static void main(String[] args) {
        System.out.println("=== BANCO MACK ===");
        
        while (true) {
            System.out.println("\nMENU PRINCIPAL:");
            System.out.println("1 - Criar nova conta");
            System.out.println("2 - Fazer login");
            System.out.println("0 - Sair");
            System.out.print("Escolha uma opção: ");
            
            int opcao = scanner.nextInt();
            scanner.nextLine(); // Limpar buffer
            
            switch(opcao) {
                case 1:
                    criarConta();
                    break;
                case 2:
                    fazerLogin();
                    break;
                case 0:
                    System.out.println("Obrigado por utilizar o Banco Mack!");
                    System.exit(0);
                default:
                    System.out.println("Opção inválida!");
            }
        }
    }

    private static void criarConta() {
        System.out.println("\nCRIAÇÃO DE CONTA");
        
        System.out.print("Digite seu nome completo: ");
        String titular = scanner.nextLine();
        
        System.out.print("Crie uma agência (4 dígitos): ");
        String agencia = scanner.nextLine();
        
        // Gerar número de conta aleatório com 6 dígitos
        String numeroConta = String.format("%06d", random.nextInt(1000000));
        System.out.println("Seu número da conta será: " + numeroConta);
        
        System.out.print("Crie uma senha numérica (4 dígitos): ");
        String senha = scanner.nextLine();
        
        ContaBancaria novaConta = new ContaBancaria(titular, agencia, numeroConta, senha);
        contas.add(novaConta);
        
        System.out.println("\nConta criada com sucesso!");
        System.out.println("Agência: " + agencia);
        System.out.println("Conta: " + numeroConta);
        System.out.println("Titular: " + titular);
    }

    private static void fazerLogin() {
        System.out.println("\nLOGIN");
        
        System.out.print("Digite o número da agência: ");
        String agencia = scanner.nextLine();
        
        System.out.print("Digite o número da conta: ");
        String numeroConta = scanner.nextLine();
        
        System.out.print("Digite sua senha: ");
        String senha = scanner.nextLine();
        
        ContaBancaria conta = encontrarConta(agencia, numeroConta);
        
        if (conta != null && conta.verificarSenha(senha)) {
            System.out.println("\nLogin realizado com sucesso!");
            menuConta(conta);
        } else {
            System.out.println("Agência, conta ou senha incorretos!");
        }
    }

    private static ContaBancaria encontrarConta(String agencia, String numeroConta) {
        for (ContaBancaria conta : contas) {
            if (conta.getAgencia().equals(agencia) && conta.getNumeroConta().equals(numeroConta)) {
                return conta;
            }
        }
        return null;
    }

    private static void menuConta(ContaBancaria conta) {
        while (true) {
            System.out.println("\nMENU DA CONTA:");
            System.out.println("1 - Depositar");
            System.out.println("2 - Sacar");
            System.out.println("3 - Consultar saldo");
            System.out.println("4 - Extrato");
            System.out.println("5 - Alterar titular");
            System.out.println("6 - Alterar senha");
            System.out.println("0 - Sair");
            System.out.print("Escolha uma opção: ");
            
            int opcao = scanner.nextInt();
            scanner.nextLine(); // Limpar buffer
            
            switch(opcao) {
                case 1:
                    System.out.print("Digite o valor para depósito: ");
                    double valorDeposito = scanner.nextDouble();
                    conta.depositar(valorDeposito);
                    System.out.printf("Depósito de R$%.2f realizado com sucesso!\n", valorDeposito);
                    break;
                    
                case 2:
                    System.out.print("Digite o valor para saque: ");
                    double valorSaque = scanner.nextDouble();
                    if (conta.sacar(valorSaque)) {
                        System.out.printf("Saque de R$%.2f realizado com sucesso!\n", valorSaque);
                    } else {
                        System.out.println("Saldo insuficiente!");
                    }
                    break;
                    
                case 3:
                    System.out.printf("Saldo atual: R$%.2f\n", conta.getSaldo());
                    break;
                    
                case 4:
                    System.out.println("\n=== EXTRATO BANCÁRIO ===");
                    System.out.println("Banco: Mack");
                    System.out.println("Agência: " + conta.getAgencia());
                    System.out.println("Conta: " + conta.getNumeroConta());
                    System.out.println("Titular: " + conta.getTitular());
                    System.out.printf("Saldo: R$%.2f\n", conta.getSaldo());
                    break;
                    
                case 5:
                    System.out.print("Digite o novo nome do titular: ");
                    String novoTitular = scanner.nextLine();
                    conta.setTitular(novoTitular);
                    System.out.println("Titular alterado com sucesso!");
                    break;
                    
                case 6:
                    System.out.print("Digite a senha atual: ");
                    String senhaAtual = scanner.nextLine();
                    
                    if (conta.verificarSenha(senhaAtual)) {
                        System.out.print("Digite a nova senha (4 dígitos): ");
                        String novaSenha = scanner.nextLine();
                        conta.alterarSenha(novaSenha);
                        System.out.println("Senha alterada com sucesso!");
                    } else {
                        System.out.println("Senha incorreta!");
                    }
                    break;
                    
                case 0:
                    return;
                    
                default:
                    System.out.println("Opção inválida!");
            }
        }
    }
}
