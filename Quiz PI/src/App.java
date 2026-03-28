import javax.swing.JOptionPane;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class App {

    public static void main(String[] args) {
        // Tela Inicial, é possivel personalizar
        String[] opcoesMenu = { "Iniciar Quiz", "Sair" };
        int menuPrincipal = JOptionPane.showOptionDialog(null,
                "===================================\n" +
                        "       BEM-VINDO AO ASSASSIN'S QUIZ       \n" +
                        "===================================\n\n" +
                        "Desafio de 7 Jogos da Franquia!",
                "Menu Principal",
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, opcoesMenu, opcoesMenu[0]);

        if (menuPrincipal != 0)
            encerrar();

        // Quantidade de jogadores Minimo 1 maximo 5
        String[] botoesJogadores = { "1", "2", "3", "4", "5" };
        int selecao = JOptionPane.showOptionDialog(null,
                "Quantos assassinos vão participar?", "Configuração",
                JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, botoesJogadores, botoesJogadores[0]);

        if (selecao == JOptionPane.CLOSED_OPTION)
            encerrar();
        int qtdJogadores = selecao + 1;

        // Escolha a quantidade de perguntas
        int qtdPerguntas = escolherQuantidadeDigitada(
                "Quantas perguntas cada um deve responder?\n(Escolha de 1 a 7 jogos)", "Configuração", 1, 7);

        // Sistemas de nomes Caso nao tenha nome o sistema coloca o nome de Assassino
        // mais o numero que ele seria
        String[] nomes = new String[qtdJogadores];
        for (int i = 0; i < qtdJogadores; i++) {
            nomes[i] = JOptionPane.showInputDialog(null, "Nome do Jogador " + (i + 1) + ":", "Identificação",
                    JOptionPane.QUESTION_MESSAGE);
            if (nomes[i] == null || nomes[i].trim().isEmpty()) {
                nomes[i] = "Assassino " + (i + 1);
            }
        }

        iniciarJogo(nomes, qtdPerguntas);
    }

    private static void iniciarJogo(String[] nomes, int perguntasPorJogador) {
        List<List<Questao>> todosOsJogos = criarBancoPorJogo();
        int[] pontuacoes = new int[nomes.length];

        for (int i = 0; i < nomes.length; i++) {
            JOptionPane.showMessageDialog(null, "Vez de: " + nomes[i], "Troca de Turno", JOptionPane.WARNING_MESSAGE);

            for (int j = 0; j < perguntasPorJogador; j++) {
                List<Questao> perguntasDoJogoAtual = todosOsJogos.get(j);
                Collections.shuffle(perguntasDoJogoAtual);

                Questao q = perguntasDoJogoAtual.get(0);

                int resposta = JOptionPane.showOptionDialog(null,
                        "JOGADOR: " + nomes[i] + "\nJOGO: " + q.nomeJogo + "\n\n" + q.pergunta,
                        "Pergunta " + (j + 1),
                        JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, q.opcoes, q.opcoes[0]);

                if (resposta == -1) {
                    encerrar();
                }

                if (resposta == q.correta) {
                    pontuacoes[i]++;
                    JOptionPane.showMessageDialog(null, "✅ Resposta correta!", "Sucesso", JOptionPane.PLAIN_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "❌ Errado! Era: " + q.opcoes[q.correta], "Resposta Incorreta",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        }
        exibirResultadoFinal(nomes, pontuacoes);
    }

    // Placar no menu final
    private static void exibirResultadoFinal(String[] nomes, int[] pontuacoes) {
        StringBuilder resultado = new StringBuilder("--- PLACAR FINAL ---\n\n");

        int maiorPontuacao = -1;
        List<String> vencedores = new ArrayList<>();

        // Aqui e para descobrir a maior pontuação
        for (int i = 0; i < nomes.length; i++) {
            resultado.append(nomes[i]).append(": ").append(pontuacoes[i]).append(" pontos\n");

            if (pontuacoes[i] > maiorPontuacao) {
                maiorPontuacao = pontuacoes[i];
            }
        }

        // Aqui e se teve EMPATE
        for (int i = 0; i < nomes.length; i++) {
            if (pontuacoes[i] == maiorPontuacao) {
                vencedores.add(nomes[i]);
            }
        }

        resultado.append("\n---------------------------\n");

        // Modo e exibiçao de quem ganhou
        if (vencedores.size() > 1 && maiorPontuacao > 0) {
            resultado.append("🏆 EMPATE ENTRE: ").append(String.join(", ", vencedores));
        } else if (maiorPontuacao > 0) {
            resultado.append("🏆 VENCEDOR: ").append(vencedores.get(0).toUpperCase());
        } else {
            resultado.append("💀 Ninguém pontuou!");
        }

        JOptionPane.showMessageDialog(null, resultado.toString(), "Fim de Jogo", JOptionPane.INFORMATION_MESSAGE);
        System.exit(0);
    }

    // garante que o numero de pessoas digitadas seja entre 1 e 7 (Min e Max)
    private static int escolherQuantidadeDigitada(String msg, String titulo, int min, int max) {
        while (true) {
            String input = JOptionPane.showInputDialog(null, msg, titulo, JOptionPane.QUESTION_MESSAGE);
            if (input == null)
                encerrar();
            try {
                int valor = Integer.parseInt(input);
                if (valor >= min && valor <= max)
                    return valor;
            } catch (Exception e) {
            }
            JOptionPane.showMessageDialog(null, "Por favor, digite um número entre " + min + " e " + max);
        }
    }

    // Mini "banco de dados"
    private static List<List<Questao>> criarBancoPorJogo() {
        List<List<Questao>> listaMestra = new ArrayList<>();

        // Pimeiro jogo e assim segue
        List<Questao> j1 = new ArrayList<>();
        j1.add(new Questao("Assassin's Creed 1", "Qual o nome do mentor de Altair?",
                new String[] { "Al Mualim", "Abbas", "Mario" }, 0)); // esse (c:0) indica qual pergunta esta certa o 0 quer dizer que é a primeria alternativa
        j1.add(new Questao("Assassin's Creed 1", "O jogo se passa durante qual período?",
                new String[] { "Cruzadas", "Guerra Fria", "Renascimento" }, 0));
        listaMestra.add(j1);

        List<Questao> j2 = new ArrayList<>();
        j2.add(new Questao("", "", // Primeira "" Seria o nome do jogo, segunda "" a pergunta
                new String[] { "", "", "" }, 0)); // Dentro dessas aspas seriam as respostas (leia o que esta comentado
                                                  // na linmha 142)
        j2.add(new Questao("", "",
                new String[] { "", "", "" }, 0));
        listaMestra.add(j2);

        List<Questao> j3 = new ArrayList<>();
        j3.add(new Questao("", "",
                new String[] { "", "", "" }, 1));
        listaMestra.add(j3);

        List<Questao> j4 = new ArrayList<>();
        j4.add(new Questao("", "",
                new String[] { "", "", "" }, 0));
        listaMestra.add(j4);

        List<Questao> j5 = new ArrayList<>();
        j5.add(new Questao("", "",
                new String[] { "", "", "" }, 1));
        listaMestra.add(j5);

        List<Questao> j6 = new ArrayList<>();
        j6.add(new Questao("", "",
                new String[] { "", "", "" }, 0));
        listaMestra.add(j6);

        List<Questao> j7 = new ArrayList<>();
        j7.add(new Questao("", "",
                new String[] { "", "", "" }, 0));
        listaMestra.add(j7);

        return listaMestra;
    }

    private static void encerrar() {
        System.exit(0);
    }

    private static class Questao {
        String nomeJogo, pergunta;
        String[] opcoes;
        int correta;

        Questao(String nj, String p, String[] o, int c) {
            this.nomeJogo = nj;
            this.pergunta = p;
            this.opcoes = o;
            this.correta = c;
        }
    }
}
