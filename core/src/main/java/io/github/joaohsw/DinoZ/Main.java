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

    @Override
    public void create() {
        palco = new Stage(new FitViewport(1280, 720));
        Gdx.input.setInputProcessor(palco);

        skin = new Skin(Gdx.files.internal("ui/uiskin.json"));

        lote = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();
        textura = new Texture("background.png");

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
            new Pergunta("Pergunta teste resposta 0", new String[] {"0", "1", "2", "3"}, 0),
            new Pergunta("Segunda pergunta teste resposta 2", new String[] {"0", "1", "2", "3"}, 2),
            new Pergunta("Terceira pergunta teste resposta 3", new String[] {"0", "1", "2", "3"}, 3)
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

        Label titulo = new Label("Selecione seu dinossauro - Etapa " + etapa_atual, skin);
        titulo.setFontScale(4);

        TextButton personagem1 = new TextButton("Dino " + (etapa_atual * 3 - 2), skin);
        TextButton personagem2 = new TextButton("Dino " + (etapa_atual * 3 - 1), skin);
        TextButton personagem3 = new TextButton("Dino " + (etapa_atual * 3), skin);

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
            dinossauros_adversarios[i] = "Adversário " + (random.nextInt(9) + 1);
        }
        adversario_atual = 0;
        vida_adversario = 100;
    }

    private void criarTelaCombate() {
        palco.clear();

        Table tabela = new Table();
        tabela.setFillParent(true);
        palco.addActor(tabela);

        Label titulo = new Label("Combate!", skin);
        titulo.setFontScale(6);

        Label vida = new Label("Vida: Jogador " + vida_jogador + " - Adversário " + vida_adversario, skin);
        vida.setFontScale(4);

        Pergunta pergunta = perguntas[pergunta_atual];
        Label perguntaLabel = new Label("Pergunta: " + pergunta.texto, skin);
        perguntaLabel.setFontScale(4);

        TextButton resposta1 = new TextButton(pergunta.opcoes[0], skin);
        TextButton resposta2 = new TextButton(pergunta.opcoes[1], skin);
        TextButton resposta3 = new TextButton(pergunta.opcoes[2], skin);
        TextButton resposta4 = new TextButton(pergunta.opcoes[3], skin);

        resposta1.getLabel().setFontScale(3f);
        resposta2.getLabel().setFontScale(3f);
        resposta3.getLabel().setFontScale(3f);
        resposta4.getLabel().setFontScale(3f);

        ClickListener listener = new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                TextButton botao = (TextButton) event.getListenerActor();
                if (botao.getText().toString().equals(pergunta.opcoes[pergunta.resposta_correta])) {
                    vida_adversario -= 10;
                } else {
                    vida_jogador -= 10;
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

        tabela.add(titulo).padBottom(40).row();
        tabela.add(vida).padBottom(20).row();
        tabela.add(perguntaLabel).padBottom(20).row();
        tabela.add(resposta1).fillX().uniformX().padBottom(10).row();
        tabela.add(resposta2).fillX().uniformX().padBottom(10).row();
        tabela.add(resposta3).fillX().uniformX().padBottom(10).row();
        tabela.add(resposta4).fillX().uniformX();
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
    public void render() {
        ScreenUtils.clear(0f, 0f, 0f, 1f);

        if (estado_atual == State.COMBAT) {
            lote.begin();
            lote.draw(textura, 0, 0, 1280, 720);
            lote.end();

            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            shapeRenderer.setColor(0, 0, 1, 1);
            shapeRenderer.circle(300, 360, 50);
            shapeRenderer.setColor(1, 0, 0, 1);
            shapeRenderer.circle(980, 360, 50);

            shapeRenderer.setColor(0, 1, 0, 1);
            shapeRenderer.rect(200, 650, vida_jogador * 2, 20);

            shapeRenderer.setColor(1, 0, 0, 1);
            shapeRenderer.rect(880, 650, vida_adversario * 2, 20);

            shapeRenderer.end();
        }

        palco.act(Gdx.graphics.getDeltaTime());
        palco.draw();
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