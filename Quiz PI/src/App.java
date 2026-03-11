import java.util.Scanner;

public class App {
    public static final String RESET = "\u001B[0m";
    public static final String VERMELHO = "\u001B[31m";
    public static final String VERDE = "\u001B[32m";
    public static final String AMARELO = "\u001B[33m";
    public static final String AZUL = "\u001B[34m";
    public static final String CIANO = "\u001B[36m";

    public static void main(String[] args) {

        Scanner ler = new Scanner(System.in);
        int pontuacao = 0;

        System.out.println(AZUL + "================================" + RESET);
        System.out.println(CIANO + "=== Quiz de perguntas gerais ===" + RESET);
        System.out.println(AZUL + "================================" + RESET);
        System.out.println();// LInha em branco

        String inicio = "";

        // Esquema para verificar o inicio
        while (!inicio.equalsIgnoreCase("ok")) {
            System.out.print(AMARELO + "Digite 'ok' para iniciar o quiz: " + RESET);
            inicio = ler.nextLine();

            if (!inicio.equalsIgnoreCase("ok")) {
                System.out.println(VERMELHO + "Entrada inválida. Digite apenas 'ok'.\n" + RESET);
            }
        }

        System.out.println("\nQuiz iniciado!\n");

        // Pergunta 1
        System.out.println(CIANO + "-----------------------------------" + RESET);
        System.out.println("Em que cidade se passa a história de Assassin's Creed Unity?");
        System.out.println("a) Londres");
        System.out.println("b) Paris");
        System.out.println("c) Roma");
        System.out.println();

        String resposta1 = lerAlternativa(ler);

        if (resposta1.equalsIgnoreCase("b")) {
            System.out.println(VERDE + "[+] Correto \n" + RESET);
            pontuacao++;
        } else {
            System.out.println(VERMELHO + "[-] Errado! A resposta correta é Paris ? \n" + RESET);
        }

        // Pergunta 2
        System.out.println(CIANO + "-----------------------------------" + RESET);
        System.out.println("Qual é o nome do protagonista de Assassin's Creed Unity?");
        System.out.println("a) Arno Dorian");
        System.out.println("b) Ezio Auditore");
        System.out.println("c) Connor Kenway");
        System.out.println();

        String resposta2 = lerAlternativa(ler);

        if (resposta2.equalsIgnoreCase("a")) {
            System.out.println(VERDE + "✅ Correto \n" + RESET);
            pontuacao++;
        } else {
            System.out.println(VERMELHO + "❌ Errado! A resposta correta é Arno Dorian \n" + RESET);
        }

        // Resultado final
        System.out.println("\nSua pontuação final foi: " + pontuacao + "/2");

        if (pontuacao == 2) {
            System.out.println("Parabéns você acertou tudo!");
        } else if (pontuacao == 1) {
            System.out.println("Parabéns, porém dá pra melhorar.");
        } else {
            System.out.println("Tente outra vez.");
        }

        ler.close();
    }

    private static String lerAlternativa(Scanner ler) {
        while (true) {
            System.out.print("Digite apenas A, B ou C: ");
            String resposta = ler.nextLine().trim().toLowerCase();

            if (resposta.equals("a") || resposta.equals("b") || resposta.equals("c")) {
                return resposta;
            }

            System.out.println("\nEntrada inválida. Digite somente A, B ou C.\n");
        }
    }
}