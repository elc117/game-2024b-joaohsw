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
        MENU, SELECAO_PERSONAGEM, COMBATE, VITORIA, DERROTA
    }

    private Stage stage;
    private Skin skin;
    private SpriteBatch sprite;
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
    private String[] especies_dinossauros;
    private Texture[] dinos;
    private Music music_background;
    private Pergunta[] perguntas = Pergunta.perguntas;

    @Override
    public void create() {
        stage = new Stage(new FitViewport(1280, 720));
        Gdx.input.setInputProcessor(stage);

        skin = new Skin(Gdx.files.internal("ui/uiskin.json"));

        sprite = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();
        textura = new Texture("background.png");
        dinos = new Texture[] {
            new Texture("dino.png"), new Texture("inimigo.png"), new Texture("dino2.png"),
            new Texture("inimigo2.png"), new Texture("dino3.png"), new Texture("inimigo3.png")
        };

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

        especies_dinossauros = new String[] {
            "Staurikosaurus pricei", "Prestosuchus chiniquensis", "Gnathovorax cabreirai",
            "Unaysaurus tolentinoi", "Saturnalia tupiniquim", "Sacisaurus agudoensis",
            "Pampadromaeus barberenai", "Bagualosaurus agudoensis", "Stahleckeria potens"
        };

        criarMenu();
    }

    private void criarMenu() {
        stage.clear();

        Table tabela = new Table();
        tabela.setFillParent(true);
        stage.addActor(tabela);

        Label titulo = new Label("DinoZ", skin);
        titulo.setFontScale(6);

        TextButton botao_jogar = new TextButton("Jogar", skin);
        botao_jogar.getLabel().setFontScale(4f);

        TextButton botao_sair = new TextButton("Sair", skin);
        botao_sair.getLabel().setFontScale(4f);

        botao_jogar.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                estado_atual = State.SELECAO_PERSONAGEM;
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
        stage.clear();

        Table tabela = new Table();
        tabela.setFillParent(true);
        stage.addActor(tabela);

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
                    estado_atual = State.COMBATE;
                    selecionarAdversarios();
                    criarTelaCOMBATEe();
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

    private void criarTelaCOMBATEe() {
        stage.clear();

        Table tabela = new Table();
        tabela.setFillParent(true);
        tabela.bottom();
        stage.addActor(tabela);

        Pergunta pergunta = perguntas[pergunta_atual];
        Label perguntaLabel = new Label("Pergunta: " + pergunta.texto, skin);
        perguntaLabel.setFontScale(1.65f);

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
                        pergunta_atual = (pergunta_atual + 1) % perguntas.length;
                        criarTelaCOMBATEe();
                    } else {
                        estado_atual = State.DERROTA;
                        criarTelaDerrota();
                    }
                } else if (vida_adversario <= 0) {
                    adversario_atual++;
                    if (adversario_atual < 3) {
                        vida_adversario = 100;
                        pergunta_atual = (pergunta_atual + 1) % perguntas.length;
                        criarTelaCOMBATEe();
                    } else {
                        estado_atual = State.VITORIA;
                        criarTelaVitoria();
                    }
                } else {
                    pergunta_atual = (pergunta_atual + 1) % perguntas.length;
                    criarTelaCOMBATEe();
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

        if (estado_atual == State.COMBATE) {
            sprite.begin();
            sprite.draw(textura, 0, 0);

            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

            shapeRenderer.setColor(0, 1, 0, 1);
            shapeRenderer.rect(275, 470, vida_jogador * 2, 20);

            shapeRenderer.setColor(0, 0, 1, 1);
            Texture textura_jogador[] = {dinos[0], dinos[2], dinos[4]};
            if(dinossauro_atual == 0) sprite.draw(textura_jogador[0], 230, 230);
            if(dinossauro_atual == 1) sprite.draw(textura_jogador[1], 230, 280);
            if(dinossauro_atual == 2) sprite.draw(textura_jogador[2], 280, 280, 180, 180);

            shapeRenderer.setColor(1, 0, 0, 1);
            shapeRenderer.rect(760, 590, vida_adversario * 2, 20);

            shapeRenderer.setColor(1, 0, 0, 1);
            Texture textura_adversario[] = {dinos[1], dinos[3], dinos[5]};
            if(adversario_atual == 0) sprite.draw(textura_adversario[0], 770, 420, 180, 180);
            if(adversario_atual == 1) sprite.draw(textura_adversario[1], 770, 420, 150, 150);
            if(adversario_atual == 2) sprite.draw(textura_adversario[2], 770, 400, 190, 190);

            sprite.end();
            shapeRenderer.end();

        }

        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }

    private void criarTelaVitoria() {
        stage.clear();

        Table tabela = new Table();
        tabela.setFillParent(true);
        stage.addActor(tabela);

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
        stage.clear();

        Table tabela = new Table();
        tabela.setFillParent(true);
        stage.addActor(tabela);

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
        stage.getViewport().update(largura, altura);
    }

    @Override
    public void dispose() {
        stage.dispose();
        skin.dispose();
        sprite.dispose();
        shapeRenderer.dispose();
        textura.dispose();
    }

}