import javax.swing.JOptionPane;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class QuizJO {

    public static void main(String[] args) {
        // --- MENU INICIAL ---
        String[] opcoesMenu = { "Iniciar Quiz", "Sair" };
        int menuPrincipal = JOptionPane.showOptionDialog(null,
                "===================================\n" +
                        "       BEM-VINDO AO ASSASSIN'S QUIZ       \n" +
                        "===================================\n\n" +
                        "Nada é verdadeiro, tudo é permitido.",
                "Menu Principal",
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, opcoesMenu, opcoesMenu[0]);

        if (menuPrincipal != 0)
            encerrar();

        // --- ESCOLHA DE JOGADORES (BOTÕES) ---
        String[] botoesJogadores = { "1", "2", "3", "4" };
        int selecaoJogadores = JOptionPane.showOptionDialog(null,
                "Quantos assassinos vão participar?", "Configuração de Jogadores",
                JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, botoesJogadores, botoesJogadores[0]);

        if (selecaoJogadores == -1)
            encerrar();
        int qtdJogadores = selecaoJogadores + 1;

        // --- ESCOLHA DE PERGUNTAS (BOTÕES) ---
        String[] botoesPerguntas = { "1", "2", "3", "4", "5" };
        int selecaoPerguntas = JOptionPane.showOptionDialog(null,
                "Quantas perguntas para cada jogador?", "Configuração de Perguntas",
                JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, botoesPerguntas, botoesPerguntas[0]);

        if (selecaoPerguntas == -1)
            encerrar();
        int qtdPerguntas = selecaoPerguntas + 1;

        // --- NOMES DOS JOGADORES ---
        String[] nomes = new String[qtdJogadores];
        for (int i = 0; i < qtdJogadores; i++) {
            nomes[i] = JOptionPane.showInputDialog(null, "Nome do Jogador " + (i + 1) + ":", "Identificação",
                    JOptionPane.QUESTION_MESSAGE);
            // Caso o usuário cancele ou deixe vazio
            if (nomes[i] == null || nomes[i].trim().isEmpty()) {
                nomes[i] = "Assassino " + (i + 1);
            }
        }

        iniciarJogo(nomes, qtdPerguntas);
    }

    private static void iniciarJogo(String[] nomes, int perguntasPorJogador) {
        List<Questao> banco = criarBancoPerguntas();
        int[] pontuacoes = new int[nomes.length];

        for (int i = 0; i < nomes.length; i++) {
            JOptionPane.showMessageDialog(null, "Vez de: " + nomes[i], "Troca de Turno", JOptionPane.WARNING_MESSAGE);

            // Embaralha as perguntas para cada jogador não pegar a mesma ordem
            Collections.shuffle(banco);

            for (int j = 0; j < perguntasPorJogador; j++) {
                Questao q = banco.get(j);

                int resposta = JOptionPane.showOptionDialog(null,
                        "Jogador: " + nomes[i] + "\n\n" + q.pergunta,
                        "Pergunta " + (j + 1),
                        JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, q.opcoes, q.opcoes[0]);

                // Se clicar em Encerrar ou no X da janela
                if (resposta == -1 || resposta == q.opcoes.length - 1) {
                    encerrar();
                }

                if (resposta == q.correta) {
                    pontuacoes[i]++;
                    JOptionPane.showMessageDialog(null, "✅ Resposta correta!", "Resultado", JOptionPane.PLAIN_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "❌ Errado! A resposta era: " + q.opcoes[q.correta], "Resultado",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        }

        exibirResultadoFinal(nomes, pontuacoes);
    }

    private static void exibirResultadoFinal(String[] nomes, int[] pontuacoes) {
        StringBuilder resultado = new StringBuilder("--- PLACAR FINAL ---\n\n");
        int maiorPontuacao = -1;
        String vencedor = "";
        boolean empate = false;

        for (int i = 0; i < nomes.length; i++) {
            resultado.append(nomes[i]).append(": ").append(pontuacoes[i]).append(" pontos\n");

            if (pontuacoes[i] > maiorPontuacao) {
                maiorPontuacao = pontuacoes[i];
                vencedor = nomes[i];
                empate = false;
            } else if (pontuacoes[i] == maiorPontuacao && maiorPontuacao != -1) {
                empate = true;
            }
        }

        resultado.append("\n---------------------------\n");
        if (empate && maiorPontuacao > 0) {
            resultado.append("RESULTADO: Empate entre os melhores!");
        } else if (maiorPontuacao <= 0) {
            resultado.append("Ninguém pontuou! Melhorem, iniciantes.");
        } else {
            resultado.append("🏆 VENCEDOR: ").append(vencedor.toUpperCase());
        }

        JOptionPane.showMessageDialog(null, resultado.toString(), "Fim de Jogo", JOptionPane.INFORMATION_MESSAGE);
        System.exit(0);
    }

    private static void encerrar() {
        JOptionPane.showMessageDialog(null, "Saindo do Animus... Até a próxima!", "Encerrado",
                JOptionPane.WARNING_MESSAGE);
        System.exit(0);
    }

    private static List<Questao> criarBancoPerguntas() {
        List<Questao> banco = new ArrayList<>();
        // O último item de cada array de opções DEVE ser "Encerrar"
        banco.add(new Questao("Em qual cidade se passa Assassin's Creed Unity?",
                new String[] { "Londres", "Paris", "Roma", "Encerrar" }, 1));

        banco.add(new Questao("Quem é o protagonista de Assassin's Creed II?",
                new String[] { "Ezio Auditore", "Altair", "Connor", "Encerrar" }, 0));

        banco.add(new Questao("Qual a ordem inimiga histórica dos Assassinos?",
                new String[] { "Illuminati", "Templários", "Maçons", "Encerrar" }, 1));

        banco.add(new Questao("Qual o nome do navio de Edward Kenway em Black Flag?",
                new String[] { "Jackdaw", "Aquila", "Vingança", "Encerrar" }, 0));

        banco.add(new Questao("Quem é o protagonista do primeiro Assassin's Creed (2007)?",
                new String[] { "Desmond", "Altair Ibn-La'Ahad", "Bayek", "Encerrar" }, 1));

        return banco;
    }

    // Classe para estruturar as perguntas
    private static class Questao {
        String pergunta;
        String[] opcoes;
        int correta;

        Questao(String pergunta, String[] opcoes, int correta) {
            this.pergunta = pergunta;
            this.opcoes = opcoes;
            this.correta = correta;
        }
    }
}
