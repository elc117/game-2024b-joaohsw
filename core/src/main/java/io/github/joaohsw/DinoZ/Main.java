package io.github.joaohsw.DinoZ;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.audio.Music;

import java.util.Random;

public class Main extends ApplicationAdapter {
    private enum State {
        MENU, CHARACTER_SELECTION, COMBAT, VICTORY, DEFEAT
    }

    private Stage palco;
    private Skin skin;
    private SpriteBatch lote;
    private ShapeRenderer shapeRenderer;
    private Texture textura;
    private State estado_atual;
    private int etapa_atual;
    private String[] dinossauros_selecionados;
    private String[] dinossauros_adversarios;
    private int vida_jogador;
    private int vida_adversario;
    private int pergunta_atual;
    private int adversario_atual;
    private int dinossauro_atual;
    private Pergunta[] perguntas;
    private String[] especies_dinossauros;
    private Texture dino1;
    private Texture dino2;
    private Texture dino3;
    private Texture dino4;
    private Texture dino5;
    private Texture dino6;
    private Music music_background;

    @Override
    public void create() {
        palco = new Stage(new FitViewport(1280, 720));
        Gdx.input.setInputProcessor(palco);

        skin = new Skin(Gdx.files.internal("ui/uiskin.json"));

        lote = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();
        textura = new Texture("background.png");
        dino1 = new Texture("dino.png");
        dino2 = new Texture("inimigo.png");
        dino3 = new Texture("dino2.png");
        dino4 = new Texture("inimigo2.png");
        dino5 = new Texture("dino3.png");
        dino6 = new Texture("inimigo3.png");


        music_background = Gdx.audio.newMusic(Gdx.files.internal("battlemusic.mp3"));
        music_background.setLooping(true);
        music_background.setVolume(0.1f);
        music_background.play();


        estado_atual = State.MENU;
        etapa_atual = 1;
        dinossauros_selecionados = new String[3];
        dinossauros_adversarios = new String[3];
        vida_jogador = 100;
        vida_adversario = 100;
        pergunta_atual = 0;
        adversario_atual = 0;
        dinossauro_atual = 0;

        perguntas = new Pergunta[] {
            new Pergunta("Em qual estado brasileiro está localizado o Geoparque Quarta Colônia?", new String[] {"Rio Grande do Sul", "Santa Catarina", "Paraná", "Minas Gerais"}, 0),
            new Pergunta("Qual município é considerado a sede do Geoparque Quarta Colônia?", new String[] {"Santa Maria", "São João do Polêsine", "Restinga Sêca", "Faxinal do Soturno"}, 1),
            new Pergunta("Qual é um dos principais fósseis encontrados no Geoparque Quarta Colônia?", new String[] {"Dinossauros", "Tubarões pré-históricos", "Fósseis de dicinodontes", "Fósseis de plantas"}, 2),
            new Pergunta("O Geoparque Caçapava do Sul é famoso por qual tipo de formação geológica?", new String[] {"Montanhas de granito", "Cânions", "Cavernas de calcário", "Dunas de areia"}, 0),
            new Pergunta("Qual é o significado de um território ser reconhecido como Geoparque?", new String[] {"Área para mineração intensiva", "Preservação geológica e desenvolvimento sustentável", "Uso exclusivo para turismo", "Exploração de combustíveis fósseis"}, 1),
            new Pergunta("Quantos municípios fazem parte do Geoparque Quarta Colônia?", new String[] {"5", "7", "9", "11"}, 2),
            new Pergunta("Qual é um dos objetivos principais dos geoparques?", new String[] {"Expandir áreas urbanas", "Educação ambiental e valorização cultural", "Explorar recursos naturais", "Preservar espécies ameaçadas"}, 3),
            new Pergunta("O Geoparque Quarta Colônia possui destaque por qual característica natural?", new String[] {"Cavernas subterrâneas", "Dunas costeiras", "Corais fossilizados", "Cachoeiras e matas nativas"}, 3),
            new Pergunta("O Geoparque Caçapava do Sul é conhecido pela formação rochosa chamada:", new String[] {"Cânion do Itaibezinho", "Morro do Diabo", "Pedra do Segredo", "Gruta Azul"}, 2),
            new Pergunta("Qual é o órgão responsável por reconhecer e apoiar os geoparques no mundo?", new String[] {"UNESCO", "WWF", "Greenpeace", "IBAMA"}, 0),
            new Pergunta("Qual é uma das principais atividades turísticas promovidas no Geoparque Quarta Colônia?", new String[] {"Pesca esportiva", "Caminhadas ecológicas", "Esportes radicais", "Observação de aves"}, 1),
            new Pergunta("Qual é o principal objetivo da rede de geoparques criada pela UNESCO?", new String[] {"Construir infraestruturas para mineração", "Expandir áreas urbanas próximas a geoparques", "Promover agricultura intensiva", "Proteger patrimônios culturais e geológicos"}, 3),
            new Pergunta("O Geoparque Caçapava do Sul é parte de qual importante cadeia de montanhas brasileira?", new String[] {"Serra do Mar", "Serra Geral", "Planalto Meridional", "Serra Gaúcha"}, 1),
            new Pergunta("A Pedra do Segredo, formação famosa no Geoparque Caçapava do Sul, é composta principalmente de qual rocha?", new String[] {"Arenito", "Basalto", "Granito", "Calcário"}, 2),
            new Pergunta("Qual é a importância da paleontologia no contexto do Geoparque Quarta Colônia?", new String[] {"Identificar novas espécies de plantas", "Estudar fósseis de animais pré-históricos", "Explorar recursos minerais para indústria", "Preservar formações rochosas raras"}, 1),
            new Pergunta("O Geoparque Quarta Colônia está localizado em uma área com grande diversidade de qual recurso natural?", new String[] {"Água doce", "Minerais preciosos", "Flora tropical", "Corais"}, 0),
            new Pergunta("Quais são as formações geológicas mais comuns no Geoparque Caçapava do Sul?", new String[] {"Granitos e quartzitos", "Calcários e dolomitos", "Basaltos e diabásios", "Arenitos e conglomerados"}, 0),
            new Pergunta("O Geoparque Quarta Colônia está integrado a um projeto de pesquisa de qual universidade brasileira?", new String[] {"PUCRS", "UFRGS", "UFSM", "UNISINOS"}, 2),
            new Pergunta("Qual rio importante corta a região do Geoparque Quarta Colônia?", new String[] {"Rio Taquari", "Rio Uruguai", "Rio Íjui", "Rio Jacuí"}, 3),
            new Pergunta("O Geoparque Caçapava do Sul está inserido em qual bioma brasileiro?", new String[] {"Amazônia", "Mata Atlântica", "Cerrado", "Pampa"}, 3)
        };
        

        especies_dinossauros = new String[] {
            "Staurikosaurus pricei", "Prestosuchus chiniquensis", "Gnathovorax cabreirai",
            "Unaysaurus tolentinoi", "Saturnalia tupiniquim", "Sacisaurus agudoensis",
            "Pampadromaeus barberenai", "Bagualosaurus agudoensis", "Stahleckeria potens"
        };

        criarMenu();
    }

    private void criarMenu() {
        palco.clear();

        Table tabela = new Table();
        tabela.setFillParent(true);
        palco.addActor(tabela);

        Label titulo = new Label("DinoZ", skin);
        titulo.setFontScale(6);

        TextButton botao_jogar = new TextButton("Jogar", skin);
        botao_jogar.getLabel().setFontScale(4f);

        TextButton botao_sair = new TextButton("Sair", skin);
        botao_sair.getLabel().setFontScale(4f);

        botao_jogar.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                estado_atual = State.CHARACTER_SELECTION;
                criarSelecaoPersonagem();
            }
        });

        botao_sair.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });

        tabela.add(titulo).padBottom(40).row();
        tabela.add(botao_jogar).fillX().uniformX().padBottom(20).row();
        tabela.add(botao_sair).fillX().uniformX();
    }

    private void criarSelecaoPersonagem() {
        palco.clear();

        Table tabela = new Table();
        tabela.setFillParent(true);
        palco.addActor(tabela);

        Label titulo = new Label("Selecione seu dinossauro", skin);
        titulo.setFontScale(4);

        int index1 = (etapa_atual - 1) * 3;
        int index2 = index1 + 1;
        int index3 = index1 + 2;

        TextButton personagem1 = new TextButton(especies_dinossauros[index1], skin);
        TextButton personagem2 = new TextButton(especies_dinossauros[index2], skin);
        TextButton personagem3 = new TextButton(especies_dinossauros[index3], skin);

        personagem1.getLabel().setFontScale(3f);
        personagem2.getLabel().setFontScale(3f);
        personagem3.getLabel().setFontScale(3f);

        ClickListener listener = new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                TextButton botao = (TextButton) event.getListenerActor();
                dinossauros_selecionados[etapa_atual - 1] = botao.getText().toString();
                if (etapa_atual < 3) {
                    etapa_atual++;
                    criarSelecaoPersonagem();
                } else {
                    estado_atual = State.COMBAT;
                    selecionarAdversarios();
                    criarTelaCombate();
                }
            }
        };

        personagem1.addListener(listener);
        personagem2.addListener(listener);
        personagem3.addListener(listener);

        tabela.add(titulo).padBottom(40).row();
        tabela.add(personagem1).fillX().uniformX().padBottom(20).row();
        tabela.add(personagem2).fillX().uniformX().padBottom(20).row();
        tabela.add(personagem3).fillX().uniformX();
    }

    private void selecionarAdversarios() {
        Random random = new Random();
        for (int i = 0; i < 3; i++) {
            dinossauros_adversarios[i] = especies_dinossauros[random.nextInt(especies_dinossauros.length)];
        }
        adversario_atual = 0;
        vida_adversario = 100;
    }

    private void criarTelaCombate() {
        palco.clear();

        Table tabela = new Table();
        tabela.setFillParent(true);
        tabela.bottom();
        palco.addActor(tabela);

        Pergunta pergunta = perguntas[pergunta_atual];
        Label perguntaLabel = new Label("Pergunta: " + pergunta.texto, skin);
        perguntaLabel.setFontScale(2);

        TextButton resposta1 = new TextButton(pergunta.opcoes[0], skin);
        TextButton resposta2 = new TextButton(pergunta.opcoes[1], skin);
        TextButton resposta3 = new TextButton(pergunta.opcoes[2], skin);
        TextButton resposta4 = new TextButton(pergunta.opcoes[3], skin);

        resposta1.getLabel().setFontScale(1.5f);
        resposta2.getLabel().setFontScale(1.5f);
        resposta3.getLabel().setFontScale(1.5f);
        resposta4.getLabel().setFontScale(1.5f);

        resposta1.getStyle().font.getData().setScale(1.5f);
        resposta2.getStyle().font.getData().setScale(1.5f);
        resposta3.getStyle().font.getData().setScale(1.5f);
        resposta4.getStyle().font.getData().setScale(1.5f);

        resposta1.setSize(500, 50);
        resposta2.setSize(500, 50);
        resposta3.setSize(500, 50);
        resposta4.setSize(500, 50);

        ClickListener listener = new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                TextButton botao = (TextButton) event.getListenerActor();
                if (botao.getText().toString().equals(pergunta.opcoes[pergunta.resposta_correta])) {
                    vida_adversario -= 20;
                } else {
                    vida_jogador -= 20;
                }

                if (vida_jogador <= 0) {
                    dinossauro_atual++;
                    if (dinossauro_atual < 3) {
                        vida_jogador = 100;
                        criarTelaCombate();
                    } else {
                        estado_atual = State.DEFEAT;
                        criarTelaDerrota();
                    }
                } else if (vida_adversario <= 0) {
                    adversario_atual++;
                    if (adversario_atual < 3) {
                        vida_adversario = 100;
                        criarTelaCombate();
                    } else {
                        estado_atual = State.VICTORY;
                        criarTelaVitoria();
                    }
                } else {
                    pergunta_atual = (pergunta_atual + 1) % perguntas.length;
                    criarTelaCombate();
                }
            }
        };

        resposta1.addListener(listener);
        resposta2.addListener(listener);
        resposta3.addListener(listener);
        resposta4.addListener(listener);

        tabela.add(perguntaLabel).padBottom(20).row();
        Table botoesTabela = new Table();
        botoesTabela.add(resposta1).size(500, 50).padBottom(10).padRight(10);
        botoesTabela.add(resposta2).size(500, 50).padBottom(10).row();
        botoesTabela.add(resposta3).size(500, 50).padBottom(10).padRight(10);
        botoesTabela.add(resposta4).size(500, 50).padBottom(10);
        tabela.add(botoesTabela).padBottom(20).row();



        
    }

    @Override
    public void render() {

        ScreenUtils.clear(0.9f, 0.9f, 0.9f, 1f); 

        if (estado_atual == State.COMBAT) {
            lote.begin();
            lote.draw(textura, 0, 0);

            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

            shapeRenderer.setColor(0, 1, 0, 1);
            shapeRenderer.rect(275, 470, vida_jogador * 2, 20);

            shapeRenderer.setColor(0, 0, 1, 1);
            Texture textura_jogador[] = {dino1, dino3, dino5};
            if(dinossauro_atual == 0) lote.draw(textura_jogador[0], 230, 230);
            if(dinossauro_atual == 1) lote.draw(textura_jogador[1], 230, 230);
            if(dinossauro_atual == 2) lote.draw(textura_jogador[2], 230, 230);

            shapeRenderer.setColor(1, 0, 0, 1);
            shapeRenderer.rect(760, 590, vida_adversario * 2, 20);

            shapeRenderer.setColor(1, 0, 0, 1);
            Texture textura_adversario[] = {dino2, dino4, dino6};
            if(adversario_atual == 0) lote.draw(textura_adversario[0], 770, 420, 180, 180);
            if(adversario_atual == 1) lote.draw(textura_adversario[1], 770, 420, 180, 180);
            if(adversario_atual == 2) lote.draw(textura_adversario[2], 770, 420, 180, 180);

            lote.end();
            shapeRenderer.end();

        }

        palco.act(Gdx.graphics.getDeltaTime());
        palco.draw();
    }



    private void criarTelaVitoria() {
        palco.clear();

        Table tabela = new Table();
        tabela.setFillParent(true);
        palco.addActor(tabela);

        Label titulo = new Label("Você Venceu!", skin);
        titulo.setFontScale(6);

        TextButton botao_menu = new TextButton("Voltar ao Menu", skin);
        botao_menu.getLabel().setFontScale(4f);

        botao_menu.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                resetarJogo();
                estado_atual = State.MENU;
                criarMenu();
            }
        });

        tabela.add(titulo).padBottom(40).row();
        tabela.add(botao_menu).fillX().uniformX().padBottom(20).row();
    }

    private void criarTelaDerrota() {
        palco.clear();

        Table tabela = new Table();
        tabela.setFillParent(true);
        palco.addActor(tabela);

        Label titulo = new Label("Você Perdeu!", skin);
        titulo.setFontScale(6);

        TextButton botao_menu = new TextButton("Voltar ao Menu", skin);
        botao_menu.getLabel().setFontScale(4f);

        botao_menu.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                resetarJogo();
                estado_atual = State.MENU;
                criarMenu();
            }
        });

        tabela.add(titulo).padBottom(40).row();
        tabela.add(botao_menu).fillX().uniformX().padBottom(20).row();
    }

    private void resetarJogo() {
        etapa_atual = 1;
        dinossauros_selecionados = new String[3];
        dinossauros_adversarios = new String[3];
        vida_jogador = 100;
        vida_adversario = 100;
        pergunta_atual = 0;
        adversario_atual = 0;
        dinossauro_atual = 0;
    }

    @Override
    public void resize(int largura, int altura) {
        palco.getViewport().update(largura, altura);
    }

    @Override
    public void dispose() {
        palco.dispose();
        skin.dispose();
        lote.dispose();
        shapeRenderer.dispose();
        textura.dispose();
    }

    private static class Pergunta {
        String texto;
        String[] opcoes;
        int resposta_correta;

        Pergunta(String texto, String[] opcoes, int resposta_correta) {
            this.texto = texto;
            this.opcoes = opcoes;
            this.resposta_correta = resposta_correta;
        }
    }
}