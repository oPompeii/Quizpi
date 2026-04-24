import javax.swing.JOptionPane;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class App {

    public static void main(String[] args) {
        // Tela Inicial, é possivel personalizar
        while (true) {
            // Tela Inicial estilizada
            String[] opcoesMenu = { "🚀 Iniciar Quiz", "📖 Como Jogar", "❌ Sair" };
            String mensagem = "<html><div style='text-align: center; background-color: #2c3e50; color: white; padding: 10px; border-radius: 5px;'>"
                    + "   <h2 style='margin: 0; letter-spacing: 2px;'>🎮 DESAFIO DOS 7 JOGOS</h2>"
                    + "   <hr color='#ecf0f1'>"
                    + "   <p style='font-size: 12px;'>Teste seus conhecimentos sobre o mundo dos games!</p>"
                    + "</div></html>";

            int menuPrincipal = JOptionPane.showOptionDialog(null, mensagem, "Menu Principal",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, opcoesMenu, opcoesMenu[0]);

            if (menuPrincipal == 1) { // Clicou em "Como Jogar"
                exibirInstrucoes();
                continue; // Volta para o início do loop (Menu Principal)
            } else if (menuPrincipal == 2 || menuPrincipal == JOptionPane.CLOSED_OPTION) {
                encerrar();
            }
            // Configuração de Jogadores
            String[] botoesJogadores = { "1", "2", "3", "4", "5" };
            String msgJogadores = "<html><div style='text-align:center;'><h3>👥 Configuração</h3><hr><p>Quantos jogadores vão participar?</p></div></html>";

            int selecao = JOptionPane.showOptionDialog(null, msgJogadores, "Configuração",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, botoesJogadores,
                    botoesJogadores[0]);

            if (selecao == JOptionPane.CLOSED_OPTION)
                encerrar();

            int qtdJogadores = selecao + 1;
            int qtdPerguntas = escolherQuantidadeDigitada(
                    "Quantas perguntas cada um deve responder?\n(Escolha de 1 a 7)", "Configuração", 1, 7);

            // Cadastro de Nomes
            String[] nomes = new String[qtdJogadores];
            for (int i = 0; i < qtdJogadores; i++) {
                nomes[i] = JOptionPane.showInputDialog(null, "Nome do Jogador " + (i + 1) + ":", "Identificação",
                        JOptionPane.QUESTION_MESSAGE);
                if (nomes[i] == null || nomes[i].trim().isEmpty()) {
                    nomes[i] = "Jogador " + (i + 1);
                }
            }

            // Inicia o fluxo do jogo
            boolean reiniciar = iniciarJogo(nomes, qtdPerguntas);

            // Se o retorno de exibirResultadoFinal for false, quebra o loop e encerra
            if (!reiniciar) {
                encerrar();
                break;
            }
        }
    }

    private static void exibirInstrucoes() {
        String texto = "<html><body style='width: 300px; font-family: sans-serif;'>"
                + "<h2 style='color: #2980b9; border-bottom: 2px solid #2980b9;'>📖 Como Jogar</h2>"
                + "<p><b>1. Jogadores:</b> Suporta de 1 a 5 jogadores locais.</p>"
                + "<p><b>2. Rodadas:</b> Cada jogador escolhe o número de perguntas que deseja responder (1 a 7).</p>"
                + "<p><b>3. Pontuação:</b> Cada pergunta tem um <b>valor</b> baseado na dificuldade. Acertar soma pontos, errar não desconta.</p>"
                + "<p><b>4. Dinâmica:</b> O jogo embaralha os temas (Assassin's Creed, RDR2, etc.) para que cada rodada seja única.</p>"
                + "<hr>"
                + "<p style='text-align: center; color: #7f8c8d;'><i>Boa sorte, Player 1!</i></p>"
                + "</body></html>";

        JOptionPane.showMessageDialog(null, texto, "Instruções", JOptionPane.PLAIN_MESSAGE);
    }

    private static boolean iniciarJogo(String[] nomes, int perguntasPorJogador) {
        List<List<Questao>> todosOsJogos = criarBancoPorJogo();
        int[] pontuacoes = new int[nomes.length];

        for (int i = 0; i < nomes.length; i++) {
            JOptionPane.showMessageDialog(null, "Vez de: " + nomes[i], "Troca de Turno", JOptionPane.WARNING_MESSAGE);

            Collections.shuffle(todosOsJogos);

            for (int j = 0; j < perguntasPorJogador; j++) {
                List<Questao> perguntasDoJogoAtual = todosOsJogos.get(j);
                Collections.shuffle(perguntasDoJogoAtual);

                Questao q = perguntasDoJogoAtual.get(0);

                int resposta = JOptionPane.showOptionDialog(null,
                        "JOGADOR: " + nomes[i] + "\nJOGO: " + q.nomeJogo +
                                "\nVALOR: " + q.valor + " pontos\n\n" + q.pergunta,
                        "Pergunta " + (j + 1),
                        JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, q.opcoes, q.opcoes[0]);

                if (resposta == -1) {
                    encerrar();
                }

                if (resposta == q.correta) {
                    // --- SOLUÇÃO 2: PONTUAÇÃO POR PESO ---
                    pontuacoes[i] += q.valor;
                    JOptionPane.showMessageDialog(null, "✅ Correto! Você ganhou " + q.valor + " pontos.", "Sucesso",
                            JOptionPane.PLAIN_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "❌ Errado! Era: " + q.opcoes[q.correta], "Resposta Incorreta",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        }
        return exibirResultadoFinal(nomes, pontuacoes);
    }

    // Placar no menu final
    private static boolean exibirResultadoFinal(String[] nomes, int[] pontuacoes) {
        String bgHeader = "#1a1a2e";
        String accentColor = "#e94560";
        StringBuilder resultado = new StringBuilder(
                "<html><body style='width: 250px; font-family: sans-serif; background-color: #f4f7f6; margin: 0;'>");

        resultado.append("<div style='background-color: ").append(bgHeader)
                .append("; color: white; padding: 15px; text-align: center; border-radius: 10px 10px 0 0;'>")
                .append("<h2 style='margin: 0; letter-spacing: 2px;'>📊 PLACAR FINAL</h2></div>");

        resultado.append(
                "<div style='padding: 15px; background-color: white;'> <table style='width: 100%; border-collapse: collapse;'>");

        int maiorPontuacao = -1;
        List<String> vencedores = new ArrayList<>();

        for (int i = 0; i < nomes.length; i++) {
            String rowBg = (i % 2 == 0) ? "#ffffff" : "#f9f9f9";
            resultado.append("<tr style='background-color: ").append(rowBg).append(";'>")
                    .append("<td style='padding: 8px;'><b>").append(nomes[i]).append("</b></td>")
                    .append("<td style='padding: 8px; text-align: right; color: #2980b9;'><b>").append(pontuacoes[i])
                    .append(" pts</b></td></tr>");

            if (pontuacoes[i] > maiorPontuacao) {
                maiorPontuacao = pontuacoes[i];
                vencedores.clear();
                vencedores.add(nomes[i]);
            } else if (pontuacoes[i] == maiorPontuacao && maiorPontuacao > 0) {
                vencedores.add(nomes[i]);
            }
        }
        resultado.append("</table></div>");

        resultado.append(
                "<div style='background-color: #eeeeee; padding: 15px; text-align: center; border-radius: 0 0 10px 10px; border-top: 2px solid ")
                .append(accentColor).append(";'>");

        if (maiorPontuacao <= 0) {
            resultado.append("<b style='color: #c0392b;'>💀 NINGUÉM PONTUOU</b>");
        } else {
            String label = vencedores.size() > 1 ? "EMPATE!" : "GRANDE CAMPEÃO:";
            resultado.append("<span style='color: #666; font-size: 10px;'>").append(label).append("</span><br>")
                    .append("<b style='color: ").append(accentColor).append("; font-size: 18px;'>👑 ")
                    .append(String.join(" & ", vencedores).toUpperCase()).append("</b>");
        }
        resultado.append("</div></body></html>");

        Object[] opcoes = { "Reiniciar Jogo", "Finalizar" };
        int escolha = JOptionPane.showOptionDialog(null, resultado.toString(), "Resultados do Desafio",
                JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, opcoes, opcoes[0]);

        return escolha == 0;
    }

    // Garante que o numero de pessoas digitadas seja entre 1 e 7 (Min e Max)
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
        // * Assassin's Creed
        List<Questao> j1 = new ArrayList<>();
        j1.add(new Questao("Assassin's Creed 1", "Qual o nome do mentor de Altair?",
                new String[] { "Al Mualim", "Abbas", "Mario" }, 0, 1));
        j1.add(new Questao("Assassin's Creed 1", "O jogo se passa durante qual período?",
                new String[] { "Cruzadas", "Guerra Fria", "Renascimento" }, 0, 3));
        j1.add(new Questao("Assassin's Creed 1", "Em qual cidade começa a jornada de Altaïr?",
                new String[] { "Masyaf", "Damasco", "Jerusalém" }, 0, 1));
        j1.add(new Questao("Assassin's Creed 1", "Qual o nome da organização inimiga dos Assassinos?",
                new String[] { "Sarracenos", "Templários", "Hospitalários" }, 1, 2));
        j1.add(new Questao("Assassin's Creed 1", "Qual o nome do protagonista no presente (2012)?",
                new String[] { "Clay Kaczmarek", "William Miles", "Desmond Miles" }, 2, 2));
        listaMestra.add(j1);

        // ! Red dead Redemption 2
        List<Questao> j2 = new ArrayList<>();
        j2.add(new Questao("Red Dead Redemption 2",
                "Qual é o nome completo do protagonista?",
                new String[] { "John Marston", "Arthur Morgan", "Dutch van der Linde" }, 1, 2));
        j2.add(new Questao("Red Dead Redemption 2",
                "Em que ano se passa a maior parte da história?",
                new String[] { "1889", "1899", "1909", "1919" }, 1, 3));
        j2.add(new Questao("Red Dead Redemption 2",
                "Qual gangue Arthur Morgan e John Marston fazem parte?",
                new String[] { "Gangue O'Driscoll", "Gangue Van der Linde", "Gangue Lemoyne Raiders",
                        "Gangue Murfree Brood" },
                0, 1));
        j2.add(new Questao("Red Dead Redemption 2",
                "Qual é o nome da cidade fictícia inspirada em Nova Orleans que aparece no jogo?",
                new String[] { "Valentine", "Saint Denis", "Rhodes", "Blackwater" },
                0, 3));
        listaMestra.add(j2);

        // * MINECRAFT
        List<Questao> j3 = new ArrayList<>();
        j3.add(new Questao("", "",
                new String[] { "", "", "" }, 1, 1));
        listaMestra.add(j3);

        // !GOD.OF.WAR
        List<Questao> j4 = new ArrayList<>();
        j4.add(new Questao("", "",
                new String[] { "", "", "" }, 0, 1));

        listaMestra.add(j4);

        // !GTA 5
        List<Questao> j5 = new ArrayList<>();
        j5.add(new Questao("", "",
                new String[] { "", "", "" }, 1, 3));

        listaMestra.add(j5);

        // !MARIO BROS
        List<Questao> j6 = new ArrayList<>();
        j6.add(new Questao("", "",
                new String[] { "", "", "" }, 0, 1));

        listaMestra.add(j6);

        // !BATMAN
        List<Questao> j7 = new ArrayList<>();
        j7.add(new Questao("", "",
                new String[] { "", "", "" }, 0, 2));

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
        int valor;

        Questao(String nj, String p, String[] o, int c, int v) {
            this.nomeJogo = nj;
            this.pergunta = p;
            this.opcoes = o;
            this.correta = c;
            this.valor = v;
        }
    }
}
